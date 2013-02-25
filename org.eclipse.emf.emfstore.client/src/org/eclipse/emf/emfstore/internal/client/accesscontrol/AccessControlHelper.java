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
package org.eclipse.emf.emfstore.internal.client.accesscontrol;

import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ServerAdmin;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESGlobalProjectIdImpl;
import org.eclipse.emf.emfstore.server.model.ESGlobalProjectId;

/**
 * Helper class for access control checks.
 * 
 * @author koegel
 */
public class AccessControlHelper {

	private ACUser user;
	private Usersession usersession;

	/**
	 * Default constructor.
	 * 
	 * @param usersession the user session that needs to be checked
	 */
	public AccessControlHelper(Usersession usersession) {
		this.usersession = usersession;
		this.user = usersession.getACUser();
	}

	/**
	 * Check if user has read access to given project id.
	 * 
	 * @param projectId the project id
	 * @throws AccessControlException if access is not permitted.
	 */
	public void checkReadAccess(ESGlobalProjectId projectId) throws AccessControlException {
		ESGlobalProjectIdImpl id = (ESGlobalProjectIdImpl) projectId;
		for (Role role : user.getRoles()) {
			if (role.canRead(id.getInternalAPIImpl(), null)) {
				return;
			}
		}
		throw new AccessControlException();
	}

	/**
	 * Check write access for the given project.
	 * 
	 * @param globalId
	 *            the global project id
	 * @throws AccessControlException if access is denied
	 */
	public void checkWriteAccess(ESGlobalProjectId globalId) throws AccessControlException {

		ProjectId projectId = ((ESGlobalProjectIdImpl) globalId).getInternalAPIImpl();

		for (Role role : user.getRoles()) {
			if (role.canDelete(projectId, null) || role.canCreate(projectId, null) || role.canModify(projectId, null)) {
				return;
			}
		}
		throw new AccessControlException();
	}

	/**
	 * Check project admin access for the given project.
	 * 
	 * @param projectId the project id
	 * @throws AccessControlException if access is denied.
	 */
	public void checkProjectAdminAccess(ESGlobalProjectId projectId) throws AccessControlException {
		ESGlobalProjectIdImpl id = (ESGlobalProjectIdImpl) projectId;
		for (Role role : user.getRoles()) {
			if (role.canAdministrate(id.getInternalAPIImpl())) {
				return;
			}
		}
		throw new AccessControlException();
	}

	/**
	 * Check the server admin access.
	 * 
	 * @throws AccessControlException if access is denied.
	 */
	public void checkServerAdminAccess() throws AccessControlException {
		for (Role role : user.getRoles()) {
			if (role instanceof ServerAdmin) {
				return;
			}
		}
		throw new AccessControlException();
	}

	/**
	 * @return usersession
	 */
	public Usersession getUsersession() {
		return usersession;
	}
}