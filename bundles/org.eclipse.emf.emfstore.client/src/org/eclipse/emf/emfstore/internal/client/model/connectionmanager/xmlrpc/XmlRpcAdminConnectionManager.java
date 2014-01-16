/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AbstractConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.XmlRpcAdminConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Connection manager for EMFStore that enables admin capabilities.
 * 
 * @author wesendon
 */
public class XmlRpcAdminConnectionManager extends AbstractConnectionManager<XmlRpcClientManager> implements
	AdminConnectionManager {

	private static final String ASSIGN_ROLE = "assignRole"; //$NON-NLS-1$
	private static final String REMOVE_PARTICIPANT = "removeParticipant"; //$NON-NLS-1$
	private static final String REMOVE_MEMBER = "removeMember"; //$NON-NLS-1$
	private static final String REMOVE_GROUP = "removeGroup"; //$NON-NLS-1$
	private static final String GET_USERS = "getUsers"; //$NON-NLS-1$
	private static final String GET_ROLE = "getRole"; //$NON-NLS-1$
	private static final String GET_PROJECT_INFOS = "getProjectInfos"; //$NON-NLS-1$
	private static final String GET_PARTICIPANTS = "getParticipants"; //$NON-NLS-1$
	private static final String GET_ORG_UNITS = "getOrgUnits"; //$NON-NLS-1$
	private static final String GET_ORG_UNIT = "getOrgUnit"; //$NON-NLS-1$
	private static final String GET_MEMBERS = "getMembers"; //$NON-NLS-1$
	private static final String GET_GROUPS = "getGroups"; //$NON-NLS-1$
	private static final String DELETE_USER = "deleteUser"; //$NON-NLS-1$
	private static final String DELETE_GROUP = "deleteGroup"; //$NON-NLS-1$
	private static final String CREATE_USER = "createUser"; //$NON-NLS-1$
	private static final String CREATE_GROUP = "createGroup"; //$NON-NLS-1$
	private static final String CHANGE_ROLE = "changeRole"; //$NON-NLS-1$
	private static final String CHANGE_USER = "changeUser"; //$NON-NLS-1$
	private static final String CHANGE_ORG_UNIT = "changeOrgUnit"; //$NON-NLS-1$
	private static final String ADD_MEMBER = "addMember"; //$NON-NLS-1$
	private static final String ADD_PARTICIPANT = "addParticipant"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	public void initConnection(ServerInfo serverInfo, SessionId id) throws ConnectionException {
		final XmlRpcClientManager clientManager = new XmlRpcClientManager(XmlRpcAdminConnectionHandler.ADMINEMFSTORE);
		clientManager.initConnection(serverInfo);
		addConnectionProxy(id, clientManager);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addMember(SessionId sessionId, ACOrgUnitId group, ACOrgUnitId member) throws ESException {
		getConnectionProxy(sessionId).call(ADD_MEMBER, sessionId, group, member);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addParticipant(SessionId sessionId, ProjectId projectId, ACOrgUnitId participant, EClass role)
		throws ESException {
		getConnectionProxy(sessionId).call(ADD_PARTICIPANT, sessionId, projectId, participant, role);
	}

	/**
	 * {@inheritDoc}
	 */
	public void changeOrgUnit(SessionId sessionId, ACOrgUnitId orgUnitId, String name, String description)
		throws ESException {
		getConnectionProxy(sessionId).call(CHANGE_ORG_UNIT, sessionId, orgUnitId, name, description);
	}

	/**
	 * {@inheritDoc}
	 */
	public void changeUser(SessionId sessionId, ACOrgUnitId userId, String name, String password)
		throws ESException {
		getConnectionProxy(sessionId).call(CHANGE_USER, sessionId, userId, name, password);
	}

	/**
	 * {@inheritDoc}
	 */
	public void changeRole(SessionId sessionId, ProjectId projectId, ACOrgUnitId orgUnit, EClass role)
		throws ESException {
		getConnectionProxy(sessionId).call(CHANGE_ROLE, sessionId, projectId, orgUnit, role);
	}

	/**
	 * {@inheritDoc}
	 */
	public ACOrgUnitId createGroup(SessionId sessionId, String name) throws ESException {
		return getConnectionProxy(sessionId).callWithResult(CREATE_GROUP, ACOrgUnitId.class, sessionId, name);
	}

	/**
	 * {@inheritDoc}
	 */
	public ACOrgUnitId createUser(SessionId sessionId, String name) throws ESException {
		return getConnectionProxy(sessionId).callWithResult(CREATE_USER, ACOrgUnitId.class, sessionId, name);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteGroup(SessionId sessionId, ACOrgUnitId group) throws ESException {
		getConnectionProxy(sessionId).call(DELETE_GROUP, sessionId, group);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteUser(SessionId sessionId, ACOrgUnitId user) throws ESException {
		getConnectionProxy(sessionId).call(DELETE_USER, sessionId, user);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACGroup> getGroups(SessionId sessionId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_GROUPS, ACGroup.class, sessionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACGroup> getGroups(SessionId sessionId, ACOrgUnitId user) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_GROUPS, ACGroup.class, sessionId, user);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACOrgUnit> getMembers(SessionId sessionId, ACOrgUnitId groupId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_MEMBERS, ACOrgUnit.class, sessionId, groupId);
	}

	/**
	 * {@inheritDoc}
	 */
	public ACOrgUnit getOrgUnit(SessionId sessionId, ACOrgUnitId orgUnitId) throws ESException {
		return getConnectionProxy(sessionId).callWithResult(GET_ORG_UNIT, ACOrgUnit.class, sessionId, orgUnitId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACOrgUnit> getOrgUnits(SessionId sessionId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_ORG_UNITS, ACOrgUnit.class, sessionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACOrgUnit> getParticipants(SessionId sessionId, ProjectId projectId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_PARTICIPANTS, ACOrgUnit.class, sessionId,
			projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ProjectInfo> getProjectInfos(SessionId sessionId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_PROJECT_INFOS, ProjectInfo.class, sessionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Role getRole(SessionId sessionId, ProjectId projectId, ACOrgUnitId orgUnit) throws ESException {
		return getConnectionProxy(sessionId).callWithResult(GET_ROLE, Role.class, sessionId, projectId, orgUnit);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACUser> getUsers(SessionId sessionId) throws ESException {
		return getConnectionProxy(sessionId).callWithListResult(GET_USERS, ACUser.class, sessionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeGroup(SessionId sessionId, ACOrgUnitId user, ACOrgUnitId group) throws ESException {
		getConnectionProxy(sessionId).call(REMOVE_GROUP, sessionId, user, group);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeMember(SessionId sessionId, ACOrgUnitId group, ACOrgUnitId member) throws ESException {
		getConnectionProxy(sessionId).call(REMOVE_MEMBER, sessionId, group, member);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeParticipant(SessionId sessionId, ProjectId projectId, ACOrgUnitId participant)
		throws ESException {
		getConnectionProxy(sessionId).call(REMOVE_PARTICIPANT, sessionId, projectId, participant);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#assignRole(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId, org.eclipse.emf.ecore.EClass)
	 */
	public void assignRole(SessionId sessionId, ACOrgUnitId orgUnitId, EClass roleClass) throws ESException {
		getConnectionProxy(sessionId).call(ASSIGN_ROLE, sessionId, orgUnitId, roleClass);
	}

}
