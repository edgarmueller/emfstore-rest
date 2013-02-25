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
package org.eclipse.emf.emfstore.internal.client.ui.testers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.emfstore.internal.client.accesscontrol.AccessControlHelper;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;

/**
 * Checks if the user has admin access to the server.
 * 
 * @author Shterev
 */
public class IsServerAdminTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, final Object expectedValue) {
		if ((receiver instanceof ServerInfo || receiver instanceof ProjectInfo) && expectedValue instanceof Boolean) {

			ServerInfo serverInfo = null;

			if (receiver instanceof ServerInfo) {
				serverInfo = (ServerInfo) receiver;
			} else if (receiver instanceof ProjectInfo) {
				ProjectInfo projectInfo = (ProjectInfo) receiver;
				serverInfo = findServerInfo(projectInfo);
			}

			if (serverInfo == null) {
				return false;
			}

			// TODO OTS
			final ServerInfo finalServerInfo = serverInfo;
			EMFStoreCommandWithResult<Boolean> command = new EMFStoreCommandWithResult<Boolean>() {
				@Override
				protected Boolean doRun() {
					Usersession usersession = finalServerInfo.getLastUsersession();
					boolean isAdmin = false;
					if (usersession != null && usersession.getACUser() != null) {
						AccessControlHelper accessControlHelper = new AccessControlHelper(usersession);
						try {
							accessControlHelper.checkServerAdminAccess();
							isAdmin = true;
						} catch (AccessControlException e) {
						}
					}

					return new Boolean(isAdmin).equals(expectedValue);
				}
			};
			Boolean result = command.run(false);
			return result;

		}
		return true;
	}

	private ServerInfo findServerInfo(ProjectInfo projectInfo) {
		for (ServerInfo serverInfo : ((Workspace) WorkspaceProvider.getInstance().getWorkspace()).getServerInfos()) {
			if (projectInfo.eContainer() != null && projectInfo.eContainer().equals(serverInfo)) {
				return serverInfo;
			}
			// for (ProjectInfo info : WorkspaceManager.getInstance().getCurrentWorkspace()
			// .getRemoteProjectList(serverInfo)) {
			// if (info.getProjectId().equals(projectInfo.getProjectId())) {
			// return serverInfo;
			// }
			// }
		}

		return null;
	}
}