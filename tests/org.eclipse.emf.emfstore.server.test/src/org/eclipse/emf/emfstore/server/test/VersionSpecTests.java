/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.test;

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.addElement;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.branch;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.checkout;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.commit;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.mergeWithBranch;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.update;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestServerFactory;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestServerFactory.ServerType;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.cases.IServer;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VersionSpecTests extends ESTestWithLoggedInUser {

	private static final String THIS_IS_A_DESCRIPTION = "this is a description"; //$NON-NLS-1$
	private static final String YET_ANOTHER_ELEMENT = "yet another element"; //$NON-NLS-1$
	private static final String ELEMENT_2 = "element 2"; //$NON-NLS-1$
	private static final String OTHER_NAME = "other name"; //$NON-NLS-1$
	private static final String ELEMENT = "element"; //$NON-NLS-1$
	private static final String FOO = "foo"; //$NON-NLS-1$
	private static final String B3 = "b3"; //$NON-NLS-1$
	private static final String B2 = "b2"; //$NON-NLS-1$
	private static final String B1 = "b1"; //$NON-NLS-1$
	private static final String TRUNK = "trunk"; //$NON-NLS-1$

	static final PrimaryVersionSpec[] VERSIONS = {
		Versions.createPRIMARY(TRUNK, 0),
		Versions.createPRIMARY(TRUNK, 1),
		Versions.createPRIMARY(B1, 2),
		Versions.createPRIMARY(B2, 3),
		Versions.createPRIMARY(B1, 4),
		Versions.createPRIMARY(B2, 5),
		Versions.createPRIMARY(B3, 6),
		Versions.createPRIMARY(B3, 7) };

	static final String[] ELEMENT_NAMES = { "v0", //$NON-NLS-1$
		"v1", //$NON-NLS-1$
		"v2", //$NON-NLS-1$
		"v3", //$NON-NLS-1$
		"v4", //$NON-NLS-1$
		"v5", //$NON-NLS-1$
		"v6", //$NON-NLS-1$
		"v7" }; //$NON-NLS-1$

	static final String[] BRANCHES = { B1, B2, B3 };

	private static void rename(final ESLocalProject localProject, final int nameIndex) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				final TestElement element = (TestElement) localProject.getModelElements().get(0);
				element.setName(ELEMENT_NAMES[nameIndex]);
				return null;
			}
		});
	}

	private ESLocalProjectImpl history;

	private static IServer server;

	@BeforeClass
	public static void beforeClass() {
		server = ESTestServerFactory.create(ServerType.Mock);
		server.startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		server.stopEMFStore();
	}

	@Override
	@Before
	public void before() {

		super.before();

		// v0
		final TestElement testElement = Create.testElement(ELEMENT_NAMES[0]);
		addElement(getLocalProject(), testElement);
		try {
			share(getUsersession(), getLocalProject());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		assertEquals(VERSIONS[0].toAPI(), getLocalProject().getBaseVersion());

		// v1
		rename(getLocalProject(), 1);
		try {
			commit(getLocalProject());
		} catch (final ESException ex1) {
			fail(ex1.getMessage());
		}
		assertEquals(VERSIONS[1].toAPI(), getLocalProject().getBaseVersion());

		ESLocalProject localProject2 = null;
		try {
			checkout(getLocalProject());
			localProject2 = checkout(getLocalProject());
		} catch (final ESException ex1) {
			fail(ex1.getMessage());
		}

		// v2
		rename(getLocalProject(), 2);
		final ESLocalProject branch = branch(getLocalProject(), BRANCHES[0]);
		assertEquals(VERSIONS[2].toAPI(), branch.getBaseVersion());

		// v3
		rename(localProject2, 3);
		final ESLocalProject branch2 = branch(localProject2, BRANCHES[1]);
		assertEquals(VERSIONS[3].toAPI(), branch2.getBaseVersion());

		// v4
		rename(getLocalProject(), 4);
		try {
			commit(getLocalProject());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		assertEquals(VERSIONS[4].toAPI(), getLocalProject().getBaseVersion());

		// v5
		rename(localProject2, 5);
		try {
			commit(localProject2);
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		assertEquals(VERSIONS[5].toAPI(), localProject2.getBaseVersion());

		// v6
		final ESLocalProject localProject3 = checkout(getLocalProject(), VERSIONS[3].toAPI());
		rename(localProject3, 6);
		final ESLocalProject branch3 = branch(localProject3, BRANCHES[2]);
		assertEquals(VERSIONS[6].toAPI(), branch3.getBaseVersion());

		// v7
		mergeWithBranch(localProject3, VERSIONS[5].toAPI(), 1);
		rename(localProject3, 7);
		try {
			commit(localProject3);
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		assertEquals(VERSIONS[7].toAPI(), localProject3.getBaseVersion());

		history = (ESLocalProjectImpl) getLocalProject();
	}

	@Test
	public void resolvePrimary() throws ESException {

		assertEquals(VERSIONS[5], history.toInternalAPI().resolveVersionSpec(VERSIONS[5], new NullProgressMonitor()));
	}

	@Test
	public void resolveNearestPrimary() throws ESException {
		assertEquals(VERSIONS[5],
			history.toInternalAPI().resolveVersionSpec(Versions.createPRIMARY(B2, 6), new NullProgressMonitor()));
	}

	@Test
	public void resolvePrimaryGlobal() throws ESException {
		assertEquals(
			VERSIONS[5],
			history.toInternalAPI().resolveVersionSpec(Versions.createPRIMARY(VersionSpec.GLOBAL, 5),
				new NullProgressMonitor()));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolvePrimaryInvalid() throws ESException {
		history.toInternalAPI().resolveVersionSpec(Versions.createPRIMARY(FOO, 5), new NullProgressMonitor());
	}

	@Test
	public void resolveLocalHead() throws ESException {
		assertEquals(VERSIONS[5],
			history.toInternalAPI().resolveVersionSpec(Versions.createHEAD(B2), new NullProgressMonitor()));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolveIllegalHead() throws ESException {
		history.toInternalAPI().resolveVersionSpec(Versions.createHEAD(FOO), new NullProgressMonitor());
	}

	@Test
	public void resolveGlobalHead() throws ESException {
		assertEquals(
			VERSIONS[7],
			history.toInternalAPI().resolveVersionSpec(Versions.createHEAD(ESVersionSpec.GLOBAL),
				new NullProgressMonitor()));
	}

	@Test
	public void resolveBranch() throws ESException {
		assertEquals(VERSIONS[5],
			history.toInternalAPI().resolveVersionSpec(Versions.createBRANCH(B2), new NullProgressMonitor()));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolveIllegalBranch() throws ESException {
		history.toInternalAPI().resolveVersionSpec(Versions.createBRANCH(FOO), new NullProgressMonitor());
	}

	@Test
	public void resolvePagedUpdateVersionSpec() throws ESException {

		final PrimaryVersionSpec primary = Versions.createPRIMARY(1);
		update(history, primary.toAPI());
		// generate two changes
		final TestElement testElement = Create.testElement(ELEMENT);
		Add.toProject(history, testElement);

		Update.testElement(TestElementFeatures.name(),
			testElement, OTHER_NAME);

		final TestElement testElement2 = Create.testElement(ELEMENT_2);
		Update.testElement(TestElementFeatures.name(), testElement2, YET_ANOTHER_ELEMENT);
		Update.testElement(TestElementFeatures.description(), testElement2, THIS_IS_A_DESCRIPTION);

		Add.toProject(history, testElement2);
		commit(history);

		PagedUpdateVersionSpec pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(0), 3);
		PrimaryVersionSpec resolveVersionSpec = history.toInternalAPI().resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(3, resolveVersionSpec.getIdentifier());

		pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(0), 10);
		resolveVersionSpec = history.toInternalAPI().resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(8, resolveVersionSpec.getIdentifier());

		pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(7), 1);
		resolveVersionSpec = history.toInternalAPI().resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(8, resolveVersionSpec.getIdentifier());

		pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(8), 1);
		resolveVersionSpec = history.toInternalAPI().resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(8, resolveVersionSpec.getIdentifier());
	}
}