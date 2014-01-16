/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.accesscontrol.test;

import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the missing {@link org.eclipse.emf.emfstore.internal.server.accesscontrol.PAPrivileges
 * PAPrivileges#ShareProject} privilege of a
 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole ProjectAdminRole}.
 * 
 * @author emueller
 * 
 */
public class CreateProjectMissingPrivilegTest extends ProjectAdminTest {

	@BeforeClass
	public static void beforeClass() {
		startEMFStoreWithPAProperties();
	}

	@AfterClass
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

	@Test(expected = ESException.class)
	public void shareProjectPA() throws ESException {
		makeUserPA();
		ProjectUtil.share(getUsersession(), getLocalProject());
	}
}
