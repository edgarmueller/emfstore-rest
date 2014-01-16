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
package org.eclipse.emf.emfstore.internal.server.accesscontrol;

/**
 * Determines what actions the
 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole
 * ProjectAdminRole} is allowed to perform.
 * 
 * @author emueller
 */
public enum PAPrivileges {

	/**
	 * Whether the project admin is allowed to assign a role.
	 */
	AssignRoleToOrgUnit,

	/**
	 * Whether the project admin role is allowed to create a new project.
	 */
	ShareProject,

	/**
	 * Whether the project admin role is allowed to create a new user.
	 */
	CreateUser,

	/**
	 * Whether the project admin role is allowed to create a new group.
	 */
	CreateGroup,

	/**
	 * Whether the project admin role is allowed to delete organizational units.
	 */
	DeleteOrgUnit,

	/**
	 * Whether the project admin role is allowed to change the password of a user.
	 */
	ChangeUserPassword,

	/**
	 * Whether the project admin role is allowed to change the assignments of organizational units
	 * to groups.
	 */
	ChangeAssignmentsOfOrgUnits
}
