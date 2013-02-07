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

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.junit.Test;

public class VersionSpecTests extends CoreServerTest {

	@Test
	public void resolvePrimary() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(versions[5]));
	}

	@Test
	public void resolveNearestPrimary() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(Versions.createPRIMARY("b2", 6)));
	}

	@Test
	public void resolvePrimaryGlobal() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(Versions.createPRIMARY(VersionSpec.GLOBAL, 5)));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolvePrimaryInvalid() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		history.resolveVersionSpec(Versions.createPRIMARY("foo", 5));
	}

	@Test
	public void resolveLocalHead() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(Versions.createHEAD("b2")));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolveIllegalHead() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		history.resolveVersionSpec(Versions.createHEAD("foobar"));
	}

	@Test
	public void resolveGlobalHead() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[7], history.resolveVersionSpec(Versions.createHEAD(IVersionSpec.GLOBAL)));
	}

	@Test
	public void resolveBranch() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		assertEquals(versions[5], history.resolveVersionSpec(Versions.createBRANCH("b2")));
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void resolveIllegalBranch() throws EMFStoreException {
		ProjectSpace history = createHistory(this);

		history.resolveVersionSpec(Versions.createBRANCH("foobar"));
	}
}