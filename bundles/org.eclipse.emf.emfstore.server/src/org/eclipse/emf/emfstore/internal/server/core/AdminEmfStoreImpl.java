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
package org.eclipse.emf.emfstore.internal.server.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.AdminEmfStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidInputException;
import org.eclipse.emf.emfstore.internal.server.exceptions.StorageException;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ReaderRole;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesFactory;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.internal.server.model.dao.ACDAOFacade;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Implementation of {@link AdminEmfStore} interface.
 * 
 * @author wesendon
 */
// TODO: bring this interface in new subinterface structure and refactor it
public class AdminEmfStoreImpl extends AbstractEmfstoreInterface implements AdminEmfStore {

	private final ACDAOFacade daoFacade;

	/**
	 * Default constructor.
	 * 
	 * @param daoFacade
	 *            provider facade for DAOs
	 * @param serverSpace
	 *            the server space
	 * @param authorizationControl
	 *            the authorization control
	 * @throws FatalESException
	 *             in case of failure
	 */
	public AdminEmfStoreImpl(ACDAOFacade daoFacade, ServerSpace serverSpace, AuthorizationControl authorizationControl)
		throws FatalESException {
		super(serverSpace, authorizationControl);
		this.daoFacade = daoFacade;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACGroup> getGroups(SessionId sessionId) throws ESException {
		if (sessionId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final List<ACGroup> result = new ArrayList<ACGroup>();
		for (final ACGroup group : daoFacade.getGroups()) {

			// quickfix
			final ACGroup copy = ModelUtil.clone(group);
			clearMembersFromGroup(copy);
			result.add(copy);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACGroup> getGroups(SessionId sessionId, ACOrgUnitId orgUnitId) throws ESException {
		if (sessionId == null || orgUnitId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final List<ACGroup> result = new ArrayList<ACGroup>();
		final ACOrgUnit orgUnit = getOrgUnit(orgUnitId);
		for (final ACGroup group : getServerSpace().getGroups()) {
			if (group.getMembers().contains(orgUnit)) {

				// quickfix
				final ACGroup copy = ModelUtil.clone(group);
				clearMembersFromGroup(copy);
				result.add(copy);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public ACOrgUnitId createGroup(SessionId sessionId, String name) throws ESException {
		if (sessionId == null || name == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		if (groupExists(name)) {
			throw new InvalidInputException("Group already exists.");
		}
		final ACGroup acGroup = AccesscontrolFactory.eINSTANCE.createACGroup();
		acGroup.setName(name);
		acGroup.setDescription(StringUtils.EMPTY);
		daoFacade.add(acGroup);
		save();
		return ModelUtil.clone(acGroup.getId());
	}

	private boolean groupExists(String name) {
		for (final ACGroup group : daoFacade.getGroups()) {
			if (group.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeGroup(SessionId sessionId, ACOrgUnitId user, ACOrgUnitId group) throws ESException {
		if (sessionId == null || user == null || group == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		getGroup(group).getMembers().remove(getOrgUnit(user));
		save();
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteGroup(SessionId sessionId, ACOrgUnitId group) throws ESException {
		if (sessionId == null || group == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		for (final Iterator<ACGroup> iter = daoFacade.getGroups().iterator(); iter.hasNext();) {
			final ACGroup next = iter.next();
			if (next.getId().equals(group)) {
				daoFacade.remove(next);
				EcoreUtil.delete(next);
				save();
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACOrgUnit> getMembers(SessionId sessionId, ACOrgUnitId groupId) throws ESException {
		if (sessionId == null || groupId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);

		// quickfix
		final List<ACOrgUnit> result = new ArrayList<ACOrgUnit>();
		for (final ACOrgUnit orgUnit : getGroup(groupId).getMembers()) {
			result.add(ModelUtil.clone(orgUnit));
		}
		clearMembersFromGroups(result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addMember(SessionId sessionId, ACOrgUnitId group, ACOrgUnitId member) throws ESException {
		if (sessionId == null || group == null || member == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final ACGroup acGroup = getGroup(group);
		final ACOrgUnit acMember = getOrgUnit(member);
		acGroup.getMembers().add(acMember);
		save();

	}

	/**
	 * {@inheritDoc}
	 */
	public void removeMember(SessionId sessionId, ACOrgUnitId group, ACOrgUnitId member) throws ESException {
		if (sessionId == null || group == null || member == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final ACGroup acGroup = getGroup(group);
		final ACOrgUnit acMember = getOrgUnit(member);
		if (acGroup.getMembers().contains(acMember)) {
			acGroup.getMembers().remove(acMember);
			save();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACOrgUnit> getParticipants(SessionId sessionId, ProjectId projectId) throws ESException {
		if (sessionId == null || projectId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkProjectAdminAccess(sessionId, projectId);
		final List<ACOrgUnit> result = new ArrayList<ACOrgUnit>();
		for (final ACOrgUnit orgUnit : daoFacade.getUsers()) {
			for (final Role role : orgUnit.getRoles()) {
				if (isServerAdmin(role) || role.getProjects().contains(projectId)) {
					result.add(ModelUtil.clone(orgUnit));
				}
			}
		}
		for (final ACOrgUnit orgUnit : daoFacade.getGroups()) {
			for (final Role role : orgUnit.getRoles()) {
				if (isServerAdmin(role) || role.getProjects().contains(projectId)) {
					result.add(ModelUtil.clone(orgUnit));
				}
			}
		}
		// quickfix
		clearMembersFromGroups(result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addParticipant(SessionId sessionId, ProjectId projectId, ACOrgUnitId participant)
		throws ESException {
		if (sessionId == null || projectId == null || participant == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		projectId = getProjectId(projectId);
		final ACOrgUnit orgUnit = getOrgUnit(participant);
		for (final Role role : orgUnit.getRoles()) {
			if (role.getProjects().contains(projectId)) {
				return;
			}
		}
		// check whether reader role exists
		for (final Role role : orgUnit.getRoles()) {
			if (isReader(role)) {
				role.getProjects().add(ModelUtil.clone(projectId));
				save();
				return;
			}
		}
		// else create new reader role
		final ReaderRole reader = RolesFactory.eINSTANCE.createReaderRole();
		reader.getProjects().add(ModelUtil.clone(projectId));
		orgUnit.getRoles().add(reader);
		save();
	}

	private ProjectId getProjectId(ProjectId projectId) throws ESException {
		for (final ProjectHistory projectHistory : getServerSpace().getProjects()) {
			if (projectHistory.getProjectId().equals(projectId)) {
				return projectHistory.getProjectId();
			}
		}
		throw new ESException("Unknown ProjectId.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeParticipant(SessionId sessionId, ProjectId projectId, ACOrgUnitId participant)
		throws ESException {
		if (sessionId == null || projectId == null || participant == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		projectId = getProjectId(projectId);
		final ACOrgUnit orgUnit = getOrgUnit(participant);
		for (final Role role : orgUnit.getRoles()) {
			if (role.getProjects().contains(projectId)) {
				role.getProjects().remove(projectId);
				save();
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Role getRole(SessionId sessionId, ProjectId projectId, ACOrgUnitId orgUnitId) throws ESException {
		if (sessionId == null || projectId == null || orgUnitId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		projectId = getProjectId(projectId);
		final ACOrgUnit oUnit = getOrgUnit(orgUnitId);
		for (final Role role : oUnit.getRoles()) {
			if (isServerAdmin(role) || role.getProjects().contains(projectId)) {
				return role;
			}
		}
		throw new ESException("Couldn't find given OrgUnit.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void changeRole(SessionId sessionId, ProjectId projectId, ACOrgUnitId orgUnitId, EClass roleClass)
		throws ESException {
		if (sessionId == null || projectId == null || orgUnitId == null || roleClass == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		projectId = getProjectId(projectId);
		final ACOrgUnit orgUnit = getOrgUnit(orgUnitId);
		// delete old role first
		final Role role = getRole(projectId, orgUnit);
		if (role != null) {
			role.getProjects().remove(projectId);
			if (role.getProjects().size() == 0) {
				orgUnit.getRoles().remove(role);
			}
		}
		// if server admin
		if (roleClass.getName().equals(RolesPackage.Literals.SERVER_ADMIN.getName())) {
			orgUnit.getRoles().add(RolesFactory.eINSTANCE.createServerAdmin());
			save();
			return;
		}
		// add project to role if it exists
		for (final Role role1 : orgUnit.getRoles()) {
			if (role1.eClass().getName().equals(roleClass.getName())) {
				role1.getProjects().add(ModelUtil.clone(projectId));
				save();
				return;
			}
		}
		// create role if does not exists
		final Role newRole = (Role) RolesPackage.eINSTANCE.getEFactoryInstance().create(
			(EClass) RolesPackage.eINSTANCE.getEClassifier(roleClass.getName()));
		newRole.getProjects().add(ModelUtil.clone(projectId));
		orgUnit.getRoles().add(newRole);
		save();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACUser> getUsers(SessionId sessionId) throws ESException {
		if (sessionId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final List<ACUser> result = new ArrayList<ACUser>();
		for (final ACUser user : daoFacade.getUsers()) {
			result.add(user);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ACOrgUnit> getOrgUnits(SessionId sessionId) throws ESException {
		if (sessionId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final List<ACOrgUnit> result = new ArrayList<ACOrgUnit>();
		for (final ACOrgUnit user : daoFacade.getUsers()) {
			result.add(ModelUtil.clone(user));
		}
		for (final ACOrgUnit group : daoFacade.getGroups()) {
			result.add(ModelUtil.clone(group));
		}
		// quickfix
		clearMembersFromGroups(result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ProjectInfo> getProjectInfos(SessionId sessionId) throws ESException {
		if (sessionId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final List<ProjectInfo> result = new ArrayList<ProjectInfo>();
		for (final ProjectHistory ph : getServerSpace().getProjects()) {
			result.add(getProjectInfo(ph));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public ACOrgUnitId createUser(SessionId sessionId, String name) throws ESException {
		if (sessionId == null || name == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		if (userExists(name)) {
			throw new InvalidInputException("username '" + name + "' already exists.");
		}
		final ACUser acUser = AccesscontrolFactory.eINSTANCE.createACUser();
		// acUser.setId(AccesscontrolFactory.eINSTANCE.createACOrgUnitId());
		acUser.setName(name);
		acUser.setDescription(" ");
		daoFacade.add(acUser);
		save();
		return ModelUtil.clone(acUser.getId());
	}

	private boolean userExists(String name) {
		for (final ACUser user : daoFacade.getUsers()) {
			if (user.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteUser(SessionId sessionId, ACOrgUnitId user) throws ESException {
		if (sessionId == null || user == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		for (final Iterator<ACUser> iter = daoFacade.getUsers().iterator(); iter.hasNext();) {
			final ACUser next = iter.next();
			if (next.getId().equals(user)) {
				daoFacade.remove(next);
				// TODO: move ecore delete into ServerSpace#deleteUser implementation
				EcoreUtil.delete(next);
				save();
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void changeOrgUnit(SessionId sessionId, ACOrgUnitId orgUnitId, String name, String description)
		throws ESException {
		if (sessionId == null || orgUnitId == null || name == null || description == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final ACOrgUnit ou = getOrgUnit(orgUnitId);
		ou.setName(name);
		ou.setDescription(description);
		save();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.AdminEmfStore#changeUser(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId, java.lang.String,
	 *      java.lang.String)
	 */
	public void changeUser(SessionId sessionId, ACOrgUnitId userId, String name, String password) throws ESException {
		if (sessionId == null || userId == null || name == null || password == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		final ACUser user = (ACUser) getOrgUnit(userId);
		user.setName(name);
		user.setPassword(password);
		save();
	}

	/**
	 * {@inheritDoc}
	 */
	public ACOrgUnit getOrgUnit(SessionId sessionId, ACOrgUnitId orgUnitId) throws ESException {
		if (sessionId == null || orgUnitId == null) {
			throw new InvalidInputException();
		}
		getAuthorizationControl().checkServerAdminAccess(sessionId);
		// quickfix
		final ACOrgUnit orgUnit = ModelUtil.clone(getOrgUnit(orgUnitId));
		clearMembersFromGroup(orgUnit);
		return orgUnit;
	}

	/**
	 * This method is used as fix for the containment issue of group.
	 */
	private void clearMembersFromGroups(Collection<ACOrgUnit> orgUnits) {
		for (final ACOrgUnit orgUnit : orgUnits) {
			clearMembersFromGroup(orgUnit);
		}
	}

	/**
	 * This method is used as fix for the containment issue of group.
	 */
	private void clearMembersFromGroup(ACOrgUnit orgUnit) {
		if (orgUnit instanceof ACGroup) {
			((ACGroup) orgUnit).getMembers().clear();
		}
	}

	private boolean isServerAdmin(Role role) {
		return role.eClass().getName().equals(RolesPackage.Literals.SERVER_ADMIN.getName());
	}

	private boolean isReader(Role role) {
		return role.eClass().getName().equals(RolesPackage.Literals.READER_ROLE.getName());
	}

	private ProjectInfo getProjectInfo(ProjectHistory project) {
		final ProjectInfo info = ModelFactory.eINSTANCE.createProjectInfo();
		info.setName(project.getProjectName());
		info.setDescription(project.getProjectDescription());
		info.setProjectId(ModelUtil.clone(project.getProjectId()));
		info.setVersion(project.getLastVersion().getPrimarySpec());
		return info;
	}

	private ACGroup getGroup(ACOrgUnitId orgUnitId) throws ESException {
		for (final ACGroup group : daoFacade.getGroups()) {
			if (group.getId().equals(orgUnitId)) {
				return group;
			}
		}
		throw new ESException("Given group doesn't exist.");
	}

	private ACOrgUnit getOrgUnit(ACOrgUnitId orgUnitId) throws ESException {
		for (final ACOrgUnit unit : daoFacade.getUsers()) {
			if (unit.getId().equals(orgUnitId)) {
				return unit;
			}
		}
		for (final ACOrgUnit unit : daoFacade.getGroups()) {
			if (unit.getId().equals(orgUnitId)) {
				return unit;
			}
		}
		throw new ESException("Given OrgUnit doesn't exist.");
	}

	private Role getRole(ProjectId projectId, ACOrgUnit orgUnit) {
		for (final Role role : orgUnit.getRoles()) {
			if (isServerAdmin(role) || role.getProjects().contains(projectId)) {
				// return (Role) ModelUtil.clone(role);
				return role;
			}
		}
		return null;
	}

	private void save() throws ESException {
		try {
			daoFacade.save();
		} catch (final IOException e) {
			throw new StorageException(StorageException.NOSAVE, e);
		} catch (final NullPointerException e) {
			throw new StorageException(StorageException.NOSAVE, e);
		}
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.core.AbstractEmfstoreInterface#initSubInterfaces()
	 */
	@Override
	protected void initSubInterfaces() throws FatalESException {
	}
}
