/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.ACUserContainer;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.factory.AuthenticationControlFactory;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.AbstractAuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.core.MethodInvocation;
import org.eclipse.emf.emfstore.internal.server.core.MonitorProvider;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod.MethodId;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ServerAdmin;

/**
 * A simple implementation of an {@link AccessControl}.
 * 
 * @author koegel
 * @author wesendonk
 */
public class AccessControlImpl implements AccessControl {

	/**
	 * Contains possible access levels.
	 */
	private enum AccessLevel {
		PROJECT_READ, PROJECT_WRITE, PROJECT_ADMIN, SERVER_ADMIN, NONE
	}

	private final Map<SessionId, ACUserContainer> sessionUserMap;
	private final ServerSpace serverSpace;
	private EnumMap<MethodId, AccessLevel> accessMap;
	private AbstractAuthenticationControl authenticationControl;

	private void initAccessMap() {
		if (accessMap != null) {
			return;
		}
		accessMap = new EnumMap<MethodId, AccessControlImpl.AccessLevel>(MethodId.class);
		addAccessMapping(AccessLevel.PROJECT_READ, MethodId.GETPROJECT, MethodId.GETEMFPROPERTIES,
			MethodId.GETHISTORYINFO, MethodId.GETCHANGES, MethodId.RESOLVEVERSIONSPEC, MethodId.DOWNLOADFILECHUNK);

		addAccessMapping(AccessLevel.PROJECT_WRITE, MethodId.SETEMFPROPERTIES, MethodId.TRANSMITPROPERTY,
			MethodId.UPLOADFILECHUNK, MethodId.CREATEVERSION, MethodId.GETBRANCHES);

		addAccessMapping(AccessLevel.PROJECT_ADMIN, MethodId.REMOVETAG, MethodId.ADDTAG);
		addAccessMapping(AccessLevel.SERVER_ADMIN, MethodId.CREATEPROJECT, MethodId.CREATEEMPTYPROJECT,
			MethodId.DELETEPROJECT, MethodId.IMPORTPROJECTHISTORYTOSERVER, MethodId.EXPORTPROJECTHISTORYFROMSERVER,
			MethodId.REGISTEREPACKAGE);

		addAccessMapping(AccessLevel.NONE, MethodId.GETPROJECTLIST, MethodId.RESOLVEUSER);
	}

	private void addAccessMapping(AccessLevel type, MethodId... operationTypes) {
		for (final MethodId opType : operationTypes) {
			accessMap.put(opType, type);
		}
	}

	/**
	 * Default constructor.
	 * 
	 * @param serverSpace
	 *            the server space to work on
	 * @throws FatalESException
	 *             an exception
	 */
	public AccessControlImpl(ServerSpace serverSpace) throws FatalESException {
		sessionUserMap = new LinkedHashMap<SessionId, ACUserContainer>();
		this.serverSpace = serverSpace;

		authenticationControl = AuthenticationControlFactory.INSTANCE.createAuthenticationControl(
			ServerConfiguration.AUTHENTICATION_POLICY_DEFAULT);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthenticationControl#logIn(org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser,
	 *      java.lang.String, java.lang.String, org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo)
	 */
	public AuthenticationInformation logIn(String username, String password,
		ClientVersionInfo clientVersionInfo)
		throws AccessControlException {
		synchronized (MonitorProvider.getInstance().getMonitor("authentication")) {
			final ACUser user = resolveUser(username);
			final AuthenticationInformation authenticationInformation = authenticationControl.logIn(user,
				user.getName(), password, clientVersionInfo);
			sessionUserMap.put(authenticationInformation.getSessionId(), new ACUserContainer(user));
			final ACUser resolvedUser = resolveUser(authenticationInformation.getSessionId());
			authenticationInformation.setResolvedACUser(resolvedUser);
			return authenticationInformation;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl#logout(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 */
	public void logout(SessionId sessionId) throws AccessControlException {
		synchronized (MonitorProvider.getInstance().getMonitor("authentication")) {
			if (sessionId == null) {
				throw new AccessControlException("SessionId is null.");
			}
			sessionUserMap.remove(sessionId);
		}
	}

	/**
	 * Resolve a String to a user.
	 * 
	 * @param username
	 * @return the ACuser instance with the given name
	 * @throws AccessControlException
	 *             if there is no such user
	 */
	private ACUser resolveUser(String username) throws AccessControlException {

		final Boolean ignoreCase = Boolean.parseBoolean(ServerConfiguration.getProperties().getProperty(
			ServerConfiguration.AUTHENTICATION_MATCH_USERS_IGNORE_CASE, "false"));

		synchronized (MonitorProvider.getInstance().getMonitor()) {
			for (final ACUser user : serverSpace.getUsers()) {
				if (ignoreCase) {
					if (user.getName().equalsIgnoreCase(username)) {
						return user;
					}
				} else {
					if (user.getName().equals(username)) {
						return user;
					}
				}
			}
			throw new AccessControlException();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl#checkSession(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 */
	public void checkSession(SessionId sessionId) throws AccessControlException {
		if (!sessionUserMap.containsKey(sessionId)) {
			throw new SessionTimedOutException("Session ID unkown.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl#checkWriteAccess(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.emfstore.internal.server.model.ProjectId, java.util.Set)
	 */
	public void checkWriteAccess(SessionId sessionId, ProjectId projectId, Set<EObject> modelElements)
		throws AccessControlException {
		checkSession(sessionId);
		final ACUser user = getUser(sessionId);
		final List<Role> roles = new ArrayList<Role>();
		roles.addAll(user.getRoles());
		roles.addAll(getRolesFromGroups(user));
		// MK: remove access control simplification
		if (!canWrite(roles, projectId, null)) {
			throw new AccessControlException();
			// for (ModelElement modelElement : modelElements) {
			// if (!canWrite(roles, projectId, modelElement)) {
			// throw new AccessControlException();
			// }
		}
	}

	/**
	 * Check if the given list of roles can write to the model element in the
	 * project.
	 * 
	 * @param roles
	 *            a list of roles
	 * @param projectId
	 *            a project id
	 * @param modelElement
	 *            a model element
	 * @return true if one of the roles can write
	 * @throws AccessControlException
	 */
	private boolean canWrite(List<Role> roles, ProjectId projectId, EObject modelElement) throws AccessControlException {
		for (final Role role : roles) {
			if (role.canModify(projectId, modelElement) || role.canCreate(projectId, modelElement)
				|| role.canDelete(projectId, modelElement)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the given list of roles can read the model element in the
	 * project.
	 * 
	 * @param roles
	 *            a list of roles
	 * @param projectId
	 *            a project id
	 * @param modelElement
	 *            a model element
	 * @return true if one of the roles can read
	 * @throws AccessControlException
	 */
	private boolean canRead(List<Role> roles, ProjectId projectId, EObject modelElement) throws AccessControlException {
		for (final Role role : roles) {
			if (role.canRead(projectId, modelElement)) {
				return true;
			}
		}
		return false;
	}

	private List<Role> getRolesFromGroups(ACOrgUnit orgUnit) {
		final ArrayList<Role> roles = new ArrayList<Role>();
		for (final ACGroup group : getGroups(orgUnit)) {
			roles.addAll(group.getRoles());
		}
		return roles;
	}

	private List<ACGroup> getGroups(ACOrgUnit orgUnit) {
		synchronized (MonitorProvider.getInstance().getMonitor()) {
			final ArrayList<ACGroup> groups = new ArrayList<ACGroup>();
			for (final ACGroup group : serverSpace.getGroups()) {
				if (group.getMembers().contains(orgUnit)) {
					groups.add(group);
					for (final ACGroup g : getGroups(group)) {
						if (groups.contains(g)) {
							continue;
						}
						groups.add(g);
					}
				}
			}
			return groups;
		}
	}

	private ACUser getUser(ACOrgUnitId orgUnitId) throws AccessControlException {
		synchronized (MonitorProvider.getInstance().getMonitor()) {
			for (final ACUser user : serverSpace.getUsers()) {
				if (user.getId().equals(orgUnitId)) {
					return user;
				}
			}
			throw new AccessControlException("Given User doesn't exist.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl#checkReadAccess(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.emfstore.internal.server.model.ProjectId, java.util.Set)
	 */
	public void checkReadAccess(SessionId sessionId, ProjectId projectId, Set<EObject> modelElements)
		throws AccessControlException {
		checkSession(sessionId);
		final ACUser user = getUser(sessionId);
		final List<Role> roles = new ArrayList<Role>();
		roles.addAll(user.getRoles());
		roles.addAll(getRolesFromGroups(user));
		// MK: remove access control simplification
		if (!canRead(roles, projectId, null)) {
			throw new AccessControlException();
			// for (ModelElement modelElement : modelElements) {
			// if (!canRead(roles, projectId, modelElement)) {
			// throw new AccessControlException();
			// }
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl#checkProjectAdminAccess(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      org.eclipse.emf.emfstore.internal.server.model.ProjectId)
	 */
	public void checkProjectAdminAccess(SessionId sessionId, ProjectId projectId) throws AccessControlException {
		checkSession(sessionId);
		final ACUser user = getUser(sessionId);
		final List<Role> roles = new ArrayList<Role>();
		roles.addAll(user.getRoles());
		roles.addAll(getRolesFromGroups(user));
		for (final Role role : roles) {
			if (role.canAdministrate(projectId)) {
				return;
			}
		}
		throw new AccessControlException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl#checkServerAdminAccess(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 */
	public void checkServerAdminAccess(SessionId sessionId) throws AccessControlException {
		checkSession(sessionId);
		final ACUser user = getUser(sessionId);
		final List<Role> roles = new ArrayList<Role>();
		roles.addAll(user.getRoles());
		roles.addAll(getRolesFromGroups(user));
		for (final Role role : roles) {
			if (role instanceof ServerAdmin) {
				return;
			}
		}
		throw new AccessControlException();

	}

	/**
	 * {@inheritDoc}
	 */
	public ACUser resolveUser(SessionId sessionId) throws AccessControlException {
		checkSession(sessionId);
		final ACUser tmpUser = sessionUserMap.get(sessionId).getRawUser();
		return copyAndResolveUser(tmpUser);
	}

	/**
	 * {@inheritDoc}
	 */
	public ACUser resolveUser(ACOrgUnitId id) throws AccessControlException {
		final ACUser tmpUser = getUser(id);
		return copyAndResolveUser(tmpUser);
	}

	private ACUser copyAndResolveUser(ACUser tmpUser) {
		final ACUser user = ModelUtil.clone(tmpUser);
		for (final Role role : getRolesFromGroups(tmpUser)) {
			user.getRoles().add(ModelUtil.clone(role));
		}

		for (final ACGroup group : getGroups(tmpUser)) {
			if (user.getEffectiveGroups().contains(group)) {
				continue;
			}
			final ACGroup copy = ModelUtil.clone(group);
			user.getEffectiveGroups().add(copy);
			copy.getMembers().clear();
		}

		return user;
	}

	private ACUser getUser(SessionId sessionId) throws AccessControlException {
		try {
			return sessionUserMap.get(sessionId).getUser();
		} catch (final AccessControlException e) {
			sessionUserMap.remove(sessionId);
			throw e;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl#checkAccess(org.eclipse.emf.emfstore.internal.server.core.MethodInvocation)
	 */
	public void checkAccess(MethodInvocation op) throws AccessControlException {
		initAccessMap();
		final AccessLevel accessType = accessMap.get(op.getType());
		if (accessType == null) {
			// no access type means "no access"
			throw new AccessControlException("no access");
		}
		switch (accessType) {
		case PROJECT_READ:
			ProjectId projectId = getProjectIdFromParameters(op);
			checkReadAccess(op.getSessionId(), projectId, null);
			break;
		case PROJECT_WRITE:
			projectId = getProjectIdFromParameters(op);
			checkWriteAccess(op.getSessionId(), projectId, null);
			break;
		case PROJECT_ADMIN:
			projectId = getProjectIdFromParameters(op);
			checkProjectAdminAccess(op.getSessionId(), projectId);
			break;
		case SERVER_ADMIN:
			checkServerAdminAccess(op.getSessionId());
			break;
		case NONE:
			break;
		default:
			throw new AccessControlException("unknown access type");
		}
	}

	private ProjectId getProjectIdFromParameters(MethodInvocation op) {
		for (final Object obj : op.getParameters()) {
			if (obj instanceof ProjectId) {
				return (ProjectId) obj;
			}
		}
		throw new IllegalArgumentException("the operation MUST have a project id");
	}

	/**
	 * Returns the authentication control that is currently used by the access control.
	 * 
	 * @return the currently active authentication control
	 */
	public AbstractAuthenticationControl getAuthenticationControl() {
		return authenticationControl;
	}

	/**
	 * Sets the authentication control to be used by the access control.
	 * 
	 * @param authenticationControl
	 *            the authentication control to be used
	 */
	public void setAuthenticationControl(AbstractAuthenticationControl authenticationControl) {
		this.authenticationControl = authenticationControl;
	}
}
