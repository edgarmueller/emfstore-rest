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
package org.eclipse.emf.emfstore.internal.client.model;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;

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
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ProjectInfo> getProjectInfos() throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getGroups(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @return list of groups
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACGroup> getGroups() throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getUsers(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @return list of users
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACUser> getUsers() throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getOrgUnits(org.eclipse.emf.emfstore.internal.server.model.SessionId)}
	 * 
	 * @return list of orgUnits
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACOrgUnit> getOrgUnits() throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#createGroup(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      String)
	 * @param name
	 *            new name
	 * @return ACOrgUnitId
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	ACOrgUnitId createGroup(String name) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#deleteGroup(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param group
	 *            orgUnit id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void deleteGroup(ACOrgUnitId group) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getGroups(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param user
	 *            orgUnit id
	 * @return list of groups
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACGroup> getGroups(ACOrgUnitId user) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#removeGroup(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, ACOrgUnitId)
	 * @param user
	 *            orgUnit id
	 * @param group
	 *            group id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void removeGroup(ACOrgUnitId user, ACOrgUnitId group) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#createUser(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      String)
	 * @param name
	 *            user's name
	 * @return ACOrgUnitId
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	ACOrgUnitId createUser(String name) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#deleteUser(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param user
	 *            user id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void deleteUser(ACOrgUnitId user) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getMembers(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param groupId
	 *            group id
	 * @return list of members
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACOrgUnit> getMembers(ACOrgUnitId groupId) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getOrgUnit(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId)
	 * @param orgUnitId
	 *            orgUnit id
	 * @return orgUnit
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	ACOrgUnit getOrgUnit(ACOrgUnitId orgUnitId) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#addMember(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, ACOrgUnitId)
	 * @param group
	 *            group id
	 * @param member
	 *            member id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void addMember(ACOrgUnitId group, ACOrgUnitId member) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#removeMember(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ACOrgUnitId, ACOrgUnitId)
	 * @param group
	 *            group id
	 * @param member
	 *            member id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void removeMember(ACOrgUnitId group, ACOrgUnitId member) throws EMFStoreException;

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
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void changeOrgUnit(ACOrgUnitId orgUnitId, String name, String description) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#getParticipants(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId)
	 * @param projectId
	 *            project id
	 * @return list of participating orgUnits
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	List<ACOrgUnit> getParticipants(ProjectId projectId) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#addParticipant(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId, ACOrgUnitId)
	 * @param projectId
	 *            project id
	 * @param participant
	 *            orgUnit id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void addParticipant(ProjectId projectId, ACOrgUnitId participant) throws EMFStoreException;

	/**
	 * Delegates call to method in {@link org.eclipse.emf.emfstore.internal.server.AdminEmfStore}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#removeParticipant(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      ProjectId, ACOrgUnitId)
	 * @param projectId
	 *            project id
	 * @param participant
	 *            orgUnit id
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void removeParticipant(ProjectId projectId, ACOrgUnitId participant) throws EMFStoreException;

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
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	Role getRole(ProjectId projectId, ACOrgUnitId orgUnit) throws EMFStoreException;

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
	 * @throws EMFStoreException
	 *             if an exceptions occurs on the server or on transport
	 */
	void changeRole(ProjectId projectId, ACOrgUnitId orgUnit, EClass role) throws EMFStoreException;

}