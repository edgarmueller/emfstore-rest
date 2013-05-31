/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * Wesendonk
 * Hodaie
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Interface for administrative services of the EMFStore. The Adminbroker
 * delegates the method calls to the server ( {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}) via
 * {@link org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager} .
 * 
 * @author Hodaie
 * @author Wesendonk
 */
public interface AdminBroker {

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getProjectInfos(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @return list of project infos
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ProjectInfo> getProjectInfos() throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getGroups(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @return list of groups
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACGroup> getGroups() throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getUsers(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @return list of users
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACUser> getUsers() throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getOrgUnits(org.eclipse.emf.emfstore.internal.server.model.SessionId)}
	 * 
	 * @return list of orgUnits
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACOrgUnit> getOrgUnits() throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#createGroup(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      String)
	 * @param name
	 *            new name
	 * @return ACOrgUnitId
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	ACOrgUnitId createGroup(String name) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#deleteGroup(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param group
	 *            orgUnit id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void deleteGroup(ACOrgUnitId group) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getGroups(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param user
	 *            orgUnit id
	 * @return list of groups
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACGroup> getGroups(ACOrgUnitId user) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#removeGroup(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, ACOrgUnitId)
	 * @param user
	 *            orgUnit id
	 * @param group
	 *            group id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void removeGroup(ACOrgUnitId user, ACOrgUnitId group) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#createUser(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      String)
	 * @param name
	 *            user's name
	 * @return ACOrgUnitId
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	ACOrgUnitId createUser(String name) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#deleteUser(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param user
	 *            user id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void deleteUser(ACOrgUnitId user) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getMembers(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param groupId
	 *            group id
	 * @return list of members
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACOrgUnit> getMembers(ACOrgUnitId groupId) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getOrgUnit(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param orgUnitId
	 *            orgUnit id
	 * @return orgUnit
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	ACOrgUnit getOrgUnit(ACOrgUnitId orgUnitId) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#addMember(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, ACOrgUnitId)
	 * @param group
	 *            group id
	 * @param member
	 *            member id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void addMember(ACOrgUnitId group, ACOrgUnitId member) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#removeMember(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, ACOrgUnitId)
	 * @param group
	 *            group id
	 * @param member
	 *            member id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void removeMember(ACOrgUnitId group, ACOrgUnitId member) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#changeOrgUnit(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, String, String)
	 * @param orgUnitId
	 *            orgUnit id
	 * @param name
	 *            new name
	 * @param description
	 *            new description
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void changeOrgUnit(ACOrgUnitId orgUnitId, String name, String description) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getParticipants(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId)
	 * @param projectId
	 *            project id
	 * @return list of participating orgUnits
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACOrgUnit> getParticipants(ProjectId projectId) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#addParticipant(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId, ACOrgUnitId)
	 * @param projectId
	 *            project id
	 * @param participant
	 *            orgUnit id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void addParticipant(ProjectId projectId, ACOrgUnitId participant) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#removeParticipant(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId, ACOrgUnitId)
	 * @param projectId
	 *            project id
	 * @param participant
	 *            orgUnit id
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void removeParticipant(ProjectId projectId, ACOrgUnitId participant) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getRole(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId, ACOrgUnitId)
	 * @param projectId
	 *            project id
	 * @param orgUnit
	 *            orgUnit id
	 * @return the role
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	Role getRole(ProjectId projectId, ACOrgUnitId orgUnit) throws ESException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#changeRole(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId, ACOrgUnitId, EClass)
	 * @param projectId
	 *            the project id
	 * @param orgUnit
	 *            the orgUnit id
	 * @param role
	 *            new role
	 * @throws ESException
	 *             if an exceptions occurs on the server or on transport
	 */
	void changeRole(ProjectId projectId, ACOrgUnitId orgUnit, EClass role) throws ESException;

}
