/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.accesscontrol.test;

import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Edgar
 * 
 */
public class CreateUserMissingPrivilegTests extends ProjectAdminTest {

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties();
	}

	public static void afterClass() {
		stopEMFStore();
	}

	@Override
	@After
	public void after() {
		super.after();
	}

	@Override
	@Before
	public void before() {
		super.before();
	}

	@Test(expected = AccessControlException.class)
	public void createUser() throws ESException {
		makeUserPA();
		ServerUtil.createUser(getUsersession(), getNewUsername());
	}
}