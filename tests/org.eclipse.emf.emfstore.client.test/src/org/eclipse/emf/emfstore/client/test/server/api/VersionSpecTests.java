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
package org.eclipse.emf.emfstore.client.test.server.api;

import static org.eclipse.emf.emfstore.client.test.server.api.HistoryAPITests.createHistory;
import static org.eclipse.emf.emfstore.client.test.server.api.HistoryAPITests.versions;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.Test;

public class VersionSpecTests extends CoreServerTest {

	@Test
	public void resolvePrimary() throws ESException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(versions[5], new NullProgressMonitor()));
	}

	@Test
	public void resolveNearestPrimary() throws ESException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5],
			history.resolveVersionSpec(Versions.createPRIMARY("b2", 6), new NullProgressMonitor()));
	}

	@Test
	public void resolvePrimaryGlobal() throws ESException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5],
			history.resolveVersionSpec(Versions.createPRIMARY(VersionSpec.GLOBAL, 5), new NullProgressMonitor()));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolvePrimaryInvalid() throws ESException {
		ProjectSpace history = createHistory(this);

		history.resolveVersionSpec(Versions.createPRIMARY("foo", 5), new NullProgressMonitor());
	}

	@Test
	public void resolveLocalHead() throws ESException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(Versions.createHEAD("b2"), new NullProgressMonitor()));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolveIllegalHead() throws ESException {
		ProjectSpace history = createHistory(this);

		history.resolveVersionSpec(Versions.createHEAD("foobar"), new NullProgressMonitor());
	}

	@Test
	public void resolveGlobalHead() throws ESException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[7],
			history.resolveVersionSpec(Versions.createHEAD(ESVersionSpec.GLOBAL), new NullProgressMonitor()));
	}

	@Test
	public void resolveBranch() throws ESException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(Versions.createBRANCH("b2"), new NullProgressMonitor()));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolveIllegalBranch() throws ESException {
		ProjectSpace history = createHistory(this);

		history.resolveVersionSpec(Versions.createBRANCH("foobar"), new NullProgressMonitor());
	}

	@Test
	public void resolvePagedUpdateVersionSpec() throws ESException {
		ProjectSpace ps = createHistory(this);
		PrimaryVersionSpec primary = Versions.createPRIMARY(1);
		ps.update(primary);
		// generate two changes
		TestElement testElement = getTestElement("element");
		ps.getProject().addModelElement(testElement);
		testElement.setName("other name");

		// TODO:
		TestElement testElement2 = getTestElement("element 2");
		testElement2.setName("yet another element");
		testElement2.setDescription("this is a description");
		ps.getProject().addModelElement(testElement2);
		ps.commit(new NullProgressMonitor());

		PagedUpdateVersionSpec pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(0), 3);
		PrimaryVersionSpec resolveVersionSpec = ps.resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(3, resolveVersionSpec.getIdentifier());

		pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(0), 10);
		resolveVersionSpec = ps.resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(8, resolveVersionSpec.getIdentifier());

		pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(7), 1);
		resolveVersionSpec = ps.resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(8, resolveVersionSpec.getIdentifier());

		pagedUpdateVersionSpec = Versions.createPAGEDUPDATE(Versions.createPRIMARY(8), 1);
		resolveVersionSpec = ps.resolveVersionSpec(pagedUpdateVersionSpec,
			new NullProgressMonitor());
		assertEquals(8, resolveVersionSpec.getIdentifier());
	}
}