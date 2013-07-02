/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Hodaie
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.integration.forward;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.client.test.TestProjectEnum;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Hodaie
 */
public abstract class IntegrationTest {

	private static boolean serverRunning;
	private SetupHelper setupHelper;

	/**
	 * set up test project.
	 * 
	 * @throws URISyntaxException URISyntaxException
	 */
	@BeforeClass
	public static void init() throws URISyntaxException {
		if (serverRunning) {
			return;
		}

		SetupHelper.startSever();
		serverRunning = true;
	}

	@AfterClass
	public static void tearDownAfterClass() {
		SetupHelper.stopServer();
		serverRunning = false;
	}

	/**
	 * Before every test import test project and share it on the server.
	 */
	@Before
	public void setup() {

		setupHelper = new SetupHelper(TestProjectEnum.RANDOM_3K);

		setupHelper.setupWorkSpace();

		setupHelper.setupTestProjectSpace();

		setupHelper.shareProject();

	}

	/**
	 * cleans server and workspace after tests are run.
	 * 
	 * @throws IOException if deletion fails
	 */
	@After
	public void cleanUp() throws IOException {
		SetupHelper.cleanupWorkspace();

		SetupHelper.cleanupServer();
	}

	/**
	 * @return the testProject
	 */
	public Project getTestProject() {
		return setupHelper.getTestProject();
	}

	/**
	 * Returns project to be compared with test project. This is project that lies on server after committing the
	 * changes.
	 * 
	 * @return project lying on the server
	 * @throws ESException ESException
	 */
	public Project getCompareProject() throws ESException {
		return setupHelper.getCompareProject();

	}

	/**
	 * Commits changes.
	 */
	protected void commitChanges() {
		setupHelper.commitChanges();
	}

}
