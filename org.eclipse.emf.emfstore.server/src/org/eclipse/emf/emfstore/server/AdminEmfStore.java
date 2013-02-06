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
package org.eclipse.emf.emfstore.server;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.roles.Role;

/**
 * AdminEmfStore offers an interface for administrating the users and their rights.
 * 
 * @author Hodaie
 * @author Wesendonk
 */
public interface AdminEmfStore extends EmfStoreInterface {

	/**
	 * Returns a list of available project.
	 * 
	 * @param sessionId the session id for authentication
	 * @return list of project infos
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	List<ProjectInfo> getProjectInfos(SessionId sessionId) throws EMFStoreException;

	/**
	 * Returns all groups on the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @return list of groups
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	List<ACGroup> getGroups(SessionId sessionId) throws EMFStoreException;

	/**
	 * Returns all users on the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @return list of user
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	List<ACUser> getUsers(SessionId sessionId) throws EMFStoreException;

	/**
	 * Returns all orgUnits on the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @return list of orgUnits
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	List<ACOrgUnit> getOrgUnits(SessionId sessionId) throws EMFStoreException;

	/**
	 * Returns an orgUnit with the specified orgUnitId.
	 * 
	 * @param orgUnitId the orgUnitId
	 * @param sessionId the session id for authentication
	 * @return an orgUnit
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	ACOrgUnit getOrgUnit(SessionId sessionId, ACOrgUnitId orgUnitId) throws EMFStoreException;

	/**
	 * Creates a group on the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @param name the name for the group
	 * @return ACOrgUnitId
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	ACOrgUnitId createGroup(SessionId sessionId, String name) throws EMFStoreException;

	/**
	 * Deletes a group on the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @param group orgUnitId of the group
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void deleteGroup(SessionId sessionId, ACOrgUnitId group) throws EMFStoreException;

	/**
	 * Returns a list of all groups in which the specified user is member of.
	 * 
	 * @param sessionId the session id for authentication
	 * @param user the users orgUnitId
	 * @return a list of groups
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	List<ACGroup> getGroups(SessionId sessionId, ACOrgUnitId user) throws EMFStoreException;

	/**
	 * Removes a user from a group.
	 * 
	 * @param sessionId the session id for authentication
	 * @param user the user's orgUnitId
	 * @param group the group's orgUnitId
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void removeGroup(SessionId sessionId, ACOrgUnitId user, ACOrgUnitId group) throws EMFStoreException;

	/**
	 * Returns all members from a group.
	 * 
	 * @param sessionId the session id for authentication
	 * @param groupId the group's orgUnitId
	 * @return a list of orgUnits
	 * @throws EMFStoreException if any error in the EmfStore occurs.
	 */
	List<ACOrgUnit> getMembers(SessionId sessionId, ACOrgUnitId groupId) throws EMFStoreException;

	/**
	 * Adds an orgUnit to a group.
	 * 
	 * @param sessionId the session id for authentication
	 * @param group the group's orgUnitId
	 * @param member the members orgUnitId
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void addMember(SessionId sessionId, ACOrgUnitId group, ACOrgUnitId member) throws EMFStoreException;

	/**
	 * Removes a orgUnit from a group.
	 * 
	 * @param sessionId the session id for authentication
	 * @param group the group's orgUnitId
	 * @param member the members orgUnitId
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void removeMember(SessionId sessionId, ACOrgUnitId group, ACOrgUnitId member) throws EMFStoreException;

	/**
	 * Creates a user on the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @param name the user's name
	 * @return ACOrgUnitId
	 * @throws EMFStoreException if any in the EmfStore occurs
	 */
	ACOrgUnitId createUser(SessionId sessionId, String name) throws EMFStoreException;

	/**
	 * Deletes a user from the server.
	 * 
	 * @param sessionId the session id for authentication
	 * @param user the user's orgUnitId
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void deleteUser(SessionId sessionId, ACOrgUnitId user) throws EMFStoreException;

	/**
	 * Changes the orgUnit's name and description.
	 * 
	 * @param sessionId the session id for authentication.
	 * @param orgUnitId the orgUnitId
	 * @param name the new name
	 * @param description the new description
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void changeOrgUnit(SessionId sessionId, ACOrgUnitId orgUnitId, String name, String description)
		throws EMFStoreException;

	/**
	 * Returns all orgUnits which are attached to the given project.
	 * 
	 * @param sessionId the session id for authentication
	 * @param projectId project's id
	 * @return a list of orgUnits
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	List<ACOrgUnit> getParticipants(SessionId sessionId, ProjectId projectId) throws EMFStoreException;

	/**
	 * Adds an orgUnit to a project.
	 * 
	 * @param sessionId the session id for authentication
	 * @param projectId the project's id
	 * @param participant the orgUnit's id
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void addParticipant(SessionId sessionId, ProjectId projectId, ACOrgUnitId participant) throws EMFStoreException;

	/**
	 * Removes an orgUnits from a project.
	 * 
	 * @param sessionId the session id for authentication
	 * @param projectId the project's id
	 * @param participant the orgUnit's id
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	void removeParticipant(SessionId sessionId, ProjectId projectId, ACOrgUnitId participant) throws EMFStoreException;

	/**
	 * Returns an orgUnit's role for a specified project.
	 * 
	 * @param sessionId the session id for authentication
	 * @param projectId the project's id
	 * @param orgUnit the orgUnit's id
	 * @return a role the user's role
	 * @throws EMFStoreException if any error in the EmfStore occurs
	 */
	Role getRole(SessionId sessionId, ProjectId projectId, ACOrgUnitId orgUnit) throws EMFStoreException;

	/**
	 * Changes the role for an orgUnit in a specified project.
	 * 
	 * @param sessionId the session id for authentication
	 * @param projectId the project's id
	 * @param orgUnit the orgUnit
	 * @param role new role for orgUnit
	 * @throws EMFStoreException if any error in the EmfStore occurs.
	 */
	void changeRole(SessionId sessionId, ProjectId projectId, ACOrgUnitId orgUnit, EClass role)
		throws EMFStoreException;
}