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
package org.eclipse.emf.emfstore.client.test.server;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class TransmissionTests extends ServerTests {

	private static ProjectSpace projectSpace1;
	private static ProjectSpace projectSpace2;
	private static Usersession usersession1;
	private static Usersession usersession2;
	private ACOrgUnitId user1;
	private ACOrgUnitId user2;

	@Before
	public void setUpTests() throws ESException {
		user1 = setupUsers("writer1", RolesPackage.eINSTANCE.getWriterRole());
		user2 = setupUsers("writer2", RolesPackage.eINSTANCE.getWriterRole());
		usersession1 = setUpUsersession("writer1", "foo");
		usersession2 = setUpUsersession("writer2", "foo");
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				NullProgressMonitor monitor = new NullProgressMonitor();
				ESWorkspaceImpl workspaceImpl = ESWorkspaceProviderImpl.getInstance().getWorkspace();
				Workspace workspace = workspaceImpl.getInternalAPIImpl();
				workspace.getServerInfos().add(getServer().getInternalAPIImpl());
				workspace.getUsersessions().add(usersession1);
				workspace.getUsersessions().add(usersession2);
				workspace.save();

				try {
					usersession1.logIn();
					usersession2.logIn();
					ESLocalProjectImpl localProjectImpl = getRemoteProject().checkout(usersession1.getAPIImpl(),
						monitor);
					ESLocalProjectImpl localProjectImpl2 = getRemoteProject().checkout(usersession2.getAPIImpl(),
						monitor);
					setProjectSpace1(localProjectImpl.getInternalAPIImpl());
					setProjectSpace2(localProjectImpl2.getInternalAPIImpl());
				} catch (AccessControlException e) {
					throw new RuntimeException(e);
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

	}

	@After
	public void tearDownUsers() throws ESException {
		SetupHelper.deleteUserOnServer(user1);
		SetupHelper.deleteUserOnServer(user2);

	}

	protected static ProjectSpace getProjectSpace1() {
		return projectSpace1;
	}

	protected static void setProjectSpace1(ProjectSpace projectSpace1) {
		TransmissionTests.projectSpace1 = projectSpace1;
	}

	protected static ProjectSpace getProjectSpace2() {
		return projectSpace2;
	}

	protected static void setProjectSpace2(ProjectSpace projectSpace2) {
		TransmissionTests.projectSpace2 = projectSpace2;
	}
}