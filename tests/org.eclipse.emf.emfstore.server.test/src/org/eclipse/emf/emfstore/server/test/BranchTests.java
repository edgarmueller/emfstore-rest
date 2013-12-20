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
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.getMergedVersion;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.mergeWithBranch;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.nullProgressMonitor;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Version;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BranchTests extends ESTestWithLoggedInUser {

	private static final String JERRY = "Jerry"; //$NON-NLS-1$
	private static final String GUENTHER = "Guenther"; //$NON-NLS-1$
	private static final String TOM = "Tom"; //$NON-NLS-1$
	private static final String MYTAG = "mytag"; //$NON-NLS-1$
	private static final String TEST = "test"; //$NON-NLS-1$
	private static final String JUERGEN = "Juergen"; //$NON-NLS-1$
	private static final String TRUNK = "trunk"; //$NON-NLS-1$
	private static final String HORST = "Horst"; //$NON-NLS-1$
	private static final String B1 = "b1"; //$NON-NLS-1$
	private static final String B2 = "b2"; //$NON-NLS-1$

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Override
	@Before
	public void before() {
		super.before();
	}

	@Override
	@After
	public void after() {
		super.after();
	}

	@Test
	public void createBranchFromTrunk() throws ESException {

		addElement(getLocalProject(), Create.testElement(HORST));
		share(getUsersession(), getLocalProject());
		branch(getLocalProject(), B1);

		final ProjectHistory history = getHistory(getLocalProject());
		assertEquals(2, history.getVersions().size());

		final Version initialVersion = history.getVersions().get(0);
		final Version branchedVersion = history.getVersions().get(1);

		assertEquals(TRUNK, initialVersion.getPrimarySpec().getBranch());
		assertEquals(B1, branchedVersion.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertNull(branchedVersion.getNextVersion());
		assertNull(branchedVersion.getPreviousVersion());
		assertNull(initialVersion.getNextVersion());
		assertNull(initialVersion.getPreviousVersion());

	}

	@Test
	public void createBranchFromTrunkWithChanges() throws ESException {

		final TestElement testElement = Create.testElement(HORST);

		addElement(getLocalProject(), testElement);
		share(getUsersession(), getLocalProject());

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);

		branch(getLocalProject(), B1);

		final ProjectHistory history = getHistory(getLocalProject());
		assertEquals(2, history.getVersions().size());

		final Version initialVersion = history.getVersions().get(0);
		final Version branchedVersion = history.getVersions().get(1);

		assertEquals(TRUNK, initialVersion.getPrimarySpec().getBranch());
		assertEquals(B1, branchedVersion.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertNull(branchedVersion.getNextVersion());
	}

	@Test
	public void createBranchFromBranch() throws ESException {

		addElement(getLocalProject(), Create.testElement(HORST));
		share(getUsersession(), getLocalProject());

		branch(getLocalProject(), B1);
		branch(getLocalProject(), B2);

		final ProjectHistory history = getHistory(getLocalProject());
		assertEquals(3, history.getVersions().size());

		final Version initialVersion = history.getVersions().get(0);
		final Version branchedVersion = history.getVersions().get(1);
		final Version branchOfBranch = history.getVersions().get(2);

		assertEquals(TRUNK, initialVersion.getPrimarySpec().getBranch());
		assertEquals(B1, branchedVersion.getPrimarySpec().getBranch());
		assertEquals(B2, branchOfBranch.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertTrue(branchOfBranch.getAncestorVersion() == branchedVersion);
		assertNull(branchedVersion.getNextVersion());
		assertNull(branchOfBranch.getNextVersion());
	}

	@Test
	public void commitToBranch() throws ESException {

		final TestElement testElement = Create.testElement(HORST);
		addElement(getLocalProject(), testElement);
		share(getUsersession(), getLocalProject());

		branch(getLocalProject(), B1);

		// FIXME: update class?
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.setName(JUERGEN);
				return null;
			}
		});

		commit(getLocalProject());

		final ProjectHistory history = getHistory(getLocalProject());
		assertEquals(3, history.getVersions().size());

		final Version initialVersion = history.getVersions().get(0);
		final Version branchedVersion = history.getVersions().get(1);
		final Version commit = history.getVersions().get(2);

		assertEquals(TRUNK, initialVersion.getPrimarySpec().getBranch());
		assertEquals(B1, branchedVersion.getPrimarySpec().getBranch());
		assertEquals(B1, commit.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertTrue(branchedVersion.getNextVersion() == commit);
		assertTrue(commit.getAncestorVersion() == null);
		assertTrue(commit.getPreviousVersion() == branchedVersion);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void updateFromBranch() throws InvalidVersionSpecException, ESException {
		final ESLocalProject project = Create.project(TEST);
		final TestElement testElement = Create.testElement(HORST);
		addElement(project, testElement);
		share(getUsersession(), project);

		branch(project, B1);

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);

		commit(project);

		final ProjectHistory history = getHistory(project);
		assertEquals(3, history.getVersions().size());
		final Version commit = history.getVersions().get(2);

		// create version
		final PrimaryVersionSpec newVersion = ModelUtil.clone(getEMFStore().createVersion(
			ModelUtil.clone(
				ESLocalProjectImpl.class.cast(project).toInternalAPI().getUsersession().getSessionId()),
			ModelUtil.clone(ESLocalProjectImpl.class.cast(project).toInternalAPI().getProjectId()),
			ModelUtil.clone(commit.getPrimarySpec()), ModelUtil.clone(commit.getChanges()), null, null,
			ModelUtil.clone(commit.getLogMessage())));

		assertEquals(4, history.getVersions().size());

		assertFalse(newVersion.equals(project.getBaseVersion()));

		ProjectUtil.update(project);
		assertEquals(newVersion,
			((ESVersionSpecImpl<ESPrimaryVersionSpec, PrimaryVersionSpec>) project.getBaseVersion()).toInternalAPI());
	}

	@Test
	public void tagBranch() throws ESException {

		final TestElement testElement = Create.testElement(HORST);
		addElement(getLocalProject(), testElement);
		share(getUsersession(), getLocalProject());

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);

		final ESPrimaryVersionSpec branch = branch(getLocalProject(), B1).getBaseVersion();

		getLocalProject().addTag(branch,
			ESVersionSpec.FACTORY.createTAG(MYTAG, branch.getBranch()),
			nullProgressMonitor());

		final ProjectHistory history = getHistory(getLocalProject());
		assertEquals(2, history.getVersions().size());

		final Version initialVersion = history.getVersions().get(0);
		final Version branchedVersion = history.getVersions().get(1);

		assertEquals(TRUNK, initialVersion.getPrimarySpec().getBranch());
		assertEquals(B1, branchedVersion.getPrimarySpec().getBranch());
		assertEquals(1, branchedVersion.getTagSpecs().size());
		assertEquals(MYTAG, branchedVersion.getTagSpecs().get(0).getName());
	}

	@Test
	public void untagBranch() throws ESException {

		final TestElement testElement = Create.testElement(HORST);
		share(getUsersession(), getLocalProject());

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);

		final ESLocalProject branch = branch(getLocalProject(), B1);

		final ProjectHistory history = getHistory(getLocalProject());
		assertEquals(2, history.getVersions().size());

		final Version branchedVersion = history.getVersions().get(1);

		branchedVersion.getTagSpecs().add(Versions.createTAG(MYTAG, B1));

		assertEquals(1, branchedVersion.getTagSpecs().size());
		assertEquals(MYTAG, branchedVersion.getTagSpecs().get(0).getName());

		getLocalProject().removeTag(branch.getBaseVersion(),
			ESVersionSpec.FACTORY.createTAG(MYTAG, B1), nullProgressMonitor());

		assertEquals(0, branchedVersion.getTagSpecs().size());
	}

	@Test
	public void backmerge() throws ESException {

		final TestElement testElement = Create.testElement(HORST);
		addElement(getLocalProject(), testElement);
		share(getUsersession(), getLocalProject());

		// checkout second trunk project
		final ESLocalProject trunk = checkout(getLocalProject());
		branch(getLocalProject(), B1);

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);

		final ESPrimaryVersionSpec latestOnBranch = commit(getLocalProject()).getBaseVersion();

		final TestElement test1 = (TestElement) trunk.getModelElement(
			getLocalProject().getModelElementId(testElement));

		assertTrue(test1 != null);
		assertEquals(HORST, test1.getName());

		// merge
		mergeWithBranch(trunk, latestOnBranch, 1);

		final TestElement test2 = (TestElement) trunk.getModelElement(
			getLocalProject().getModelElementId(testElement));

		assertTrue(test2 != null);
		assertEquals(JUERGEN, test2.getName());
	}

	@Test
	public void backmergeMultipleCP() throws ESException {

		final TestElement testElement = Create.testElement(HORST);
		addElement(getLocalProject(), testElement);
		share(getUsersession(), getLocalProject());

		// checkout second trunk project
		final ESLocalProject trunk = checkout(getLocalProject());

		branch(getLocalProject(), B1);

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);

		commit(getLocalProject());

		Update.testElement(TestElementFeatures.name(), testElement, TOM);

		final ESPrimaryVersionSpec latestOnBranch = commit(getLocalProject()).getBaseVersion();

		final TestElement test1 = (TestElement) trunk.getModelElement(
			getLocalProject().getModelElementId(testElement));

		assertTrue(test1 != null);
		assertEquals(HORST, test1.getName());

		// merge
		mergeWithBranch(trunk, latestOnBranch, 1);

		final TestElement test2 = (TestElement) trunk.getModelElement(
			getLocalProject().getModelElementId(testElement));

		assertTrue(test2 != null);
		assertEquals(TOM, test2.getName());
	}

	@Test
	public void backmergeWithShortCut() throws ESException {

		final TestElement branchElement = Create.testElement(HORST);
		addElement(getLocalProject(), branchElement);
		share(getUsersession(), getLocalProject());

		// checkout second trunk project
		final ESLocalProject trunk = checkout(getLocalProject());

		final TestElement trunkElement = (TestElement) trunk.getModelElement(
			getLocalProject().getModelElementId(branchElement));

		Update.testElement(TestElementFeatures.name(), branchElement, JUERGEN);

		ESPrimaryVersionSpec latestOnBranch = branch(getLocalProject(), B1).getBaseVersion();

		Update.testElement(TestElementFeatures.name(), trunkElement, GUENTHER);

		commit(trunk);

		ProjectUtil.mergeWithBranch(trunk, latestOnBranch, 1);
		mergeWithBranch(trunk, latestOnBranch, 1);

		assertEquals(latestOnBranch, getMergedVersion(trunk));
		assertEquals(JUERGEN, trunkElement.getName());

		// commit merged changes
		final ESPrimaryVersionSpec mergedToVersion = commit(trunk).getBaseVersion();

		final ProjectHistory projectHistory = getHistory(trunk);
		final Version version = projectHistory.getVersions().get(mergedToVersion.getIdentifier());
		assertEquals(1, version.getMergedFromVersion().size());

		assertTrue(getMergedVersion(trunk) == null);
		Update.testElement(TestElementFeatures.name(), branchElement, TOM);

		latestOnBranch = commit(getLocalProject()).getBaseVersion();

		// because of the shortcut there should be no conflict. Due to missing canonization however, there is a
		// conflict.
		mergeWithBranch(trunk, latestOnBranch, 1);

		assertEquals(TOM, trunkElement.getName());
	}

	// disabled, Otto please fix: @Test
	public void backmergeWithShortCutMultipleCp() throws ESException {
		final ESLocalProject branch = Create.project(TEST);
		final TestElement branchElement = Create.testElement(HORST);

		share(getUsersession(), branch);

		// checkout second trunk project
		final ESLocalProject trunk = checkout(branch);

		final TestElement trunkElement = (TestElement) trunk.getModelElement(
			branch.getModelElementId(branchElement));

		Update.testElement(TestElementFeatures.name(), branchElement, JUERGEN);

		ESPrimaryVersionSpec latestOnBranch = branch(branch, B1).getBaseVersion();

		Update.testElement(TestElementFeatures.name(), trunkElement, GUENTHER);

		commit(trunk);

		mergeWithBranch(trunk, latestOnBranch, 1);

		// commit merge
		commit(trunk);

		Update.testElement(TestElementFeatures.name(), branchElement, JERRY);

		latestOnBranch = commit(branch).getBaseVersion();

		Update.testElement(TestElementFeatures.name(), branchElement, TOM);

		latestOnBranch = commit(branch).getBaseVersion();

		// because of the shortcut there should be no conflict. Due to missing canonization however, there is a
		// conflict.
		mergeWithBranch(trunk, latestOnBranch, 1);

		assertEquals(TOM, trunkElement.getName());
	}

	@Test
	public void backmergeBranchToBranch() throws ESException {
		final ESLocalProject outterBranch = Create.project(TEST);
		final TestElement outterBranchElement = Create.testElement(HORST);
		addElement(outterBranch, outterBranchElement);

		share(getUsersession(), outterBranch);

		branch(outterBranch, B1);

		// checkout second trunk project
		final ESLocalProject innerBranch = checkout(outterBranch);

		final TestElement innerBranchElement = (TestElement) innerBranch.getModelElement(
			outterBranch.getModelElementId(outterBranchElement));

		Update.testElement(TestElementFeatures.name(), outterBranchElement, JUERGEN);

		final ESPrimaryVersionSpec latestOnBranch = branch(outterBranch, B2).getBaseVersion();

		Update.testElement(TestElementFeatures.name(), innerBranchElement, JERRY);

		commit(innerBranch);

		// merge
		mergeWithBranch(innerBranch, latestOnBranch, 1);

		assertEquals(JUERGEN, innerBranchElement.getName());
	}

	@Test
	public void checkoutBranch() throws ESException {

		final TestElement testElement = Create.testElement(HORST);
		addElement(getLocalProject(), testElement);
		share(getUsersession(), getLocalProject());

		branch(getLocalProject(), B1);

		Update.testElement(TestElementFeatures.name(), testElement, JUERGEN);
		final ESLocalProject lastFromBranch = commit(getLocalProject());

		final ESLocalProject secondProjectSpace = checkout(getLocalProject());

		final TestElement test = (TestElement) secondProjectSpace.getModelElement(
			getLocalProject().getModelElementId(testElement));

		assertTrue(test != null);
		assertEquals(JUERGEN, test.getName());
		assertEquals(secondProjectSpace.getBaseVersion(), lastFromBranch.getBaseVersion());
	}
}