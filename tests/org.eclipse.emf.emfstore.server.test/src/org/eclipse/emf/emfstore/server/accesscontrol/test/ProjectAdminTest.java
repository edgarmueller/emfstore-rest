/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.accesscontrol.test;

import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.AdminBroker;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.AdminBrokerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.PAPrivileges;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

/**
 * 
 * <b>NOTE</b>: Remember to start the server by calling {@code startEMFStore(Map)} within an {@code @BeforeClass} block.
 * 
 * @author emueller
 * 
 */
public class ProjectAdminTest extends ESTestWithLoggedInUser {

	private static final String USERNAME = "Hans"; //$NON-NLS-1$
	private static final String GROUP = "Group"; //$NON-NLS-1$
	private static final String OTHER_GROUP = "OtherGroup"; //$NON-NLS-1$

	private static final String PASSWORD = "secret"; //$NON-NLS-1$

	private static final String NEW_USER_NAME = "quux"; //$NON-NLS-1$

	private static AdminBroker adminBroker;
	private static AdminBroker superAdminBroker;

	public static void startEMFStoreWithPAProperties(PAPrivileges... projectAdminPrivileges) {
		final Map<String, String> properties = new LinkedHashMap<String, String>();
		properties.put(ServerConfiguration.PROJECT_ADMIN_PRIVILEGES_KEY,
			StringUtils.join(projectAdminPrivileges, ServerConfiguration.MULTI_PROPERTY_SEPERATOR));
		startEMFStore(properties);
	}

	/**
	 * Returns the admin broker that is bound to the {@link #getUsersession()}.
	 * 
	 * @return the admin broker that is bound to the {@link #getUsersession()}
	 */
	public static AdminBroker getAdminBroker() {
		return adminBroker;
	}

	/**
	 * @return the superAdminBroker
	 */
	public static AdminBroker getSuperAdminBroker() {
		return superAdminBroker;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser#getUser()
	 */
	@Override
	public String getUser() {
		return USERNAME;
	}

	public static String getNewUsername() {
		return NEW_USER_NAME;
	}

	public static String getNewGroupName() {
		return GROUP;
	}

	public static String getNewOtherGroupName() {
		return OTHER_GROUP;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return PASSWORD;
	}

	@Override
	@After
	public void after() {
		ACUser user;
		try {
			user = ServerUtil.getUser(((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId(),
				NEW_USER_NAME);
			if (user != null) {
				ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().deleteUser(
					((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId(),
					user.getId());
			}
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
		super.after();
	}

	@Override
	@Before
	public void before() {
		super.before();
		try {
			adminBroker = new AdminBrokerImpl(
				((ESServerImpl) getServer()).toInternalAPI(),
				((ESUsersessionImpl) getUsersession()).toInternalAPI().getSessionId());
			superAdminBroker = new AdminBrokerImpl(
				((ESServerImpl) getServer()).toInternalAPI(),
				((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId());
		} catch (final ConnectionException ex) {
			fail(ex.getMessage());
		}
	}

	public SessionId getSessionId() {
		return ESUsersessionImpl.class.cast(getUsersession()).toInternalAPI().getSessionId();
	}

	/**
	 * Uses the {@link #getSuperUsersession()} to assign the {@link #getUser()} a
	 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole
	 * ProjectAdminRole}.
	 * 
	 * @throws ESException
	 */
	public void makeUserPA() throws ESException {
		final ACUser user = ServerUtil.getUser(getSuperUsersession(), getUser());
		final AdminBroker broker = new AdminBrokerImpl(
			((ESServerImpl) getServer()).toInternalAPI(),
			((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId());
		broker.assignRole(user.getId(), RolesPackage.eINSTANCE.getProjectAdminRole());
	}

	public void makeUserSA() throws ESException {
		final ACUser user = ServerUtil.getUser(getSuperUsersession(), getUser());
		final AdminBroker broker = new AdminBrokerImpl(
			((ESServerImpl) getServer()).toInternalAPI(),
			((ESUsersessionImpl) getSuperUsersession()).toInternalAPI().getSessionId());
		broker.assignRole(user.getId(), RolesPackage.eINSTANCE.getServerAdmin());
	}

	public static boolean hasProjectAdminRole(ACUser user) throws ESException {
		return hasRole(user, RolesPackage.eINSTANCE.getProjectAdminRole());
	}

	public static boolean hasProjectAdminRole(ACUser user, ProjectId projectId) throws ESException {
		final ACOrgUnit orgUnit = getSuperAdminBroker().getOrgUnit(user.getId());

		for (final Role role : orgUnit.getRoles()) {
			if (role.eClass().equals(RolesPackage.eINSTANCE.getProjectAdminRole())) {
				final ProjectAdminRole projectAdminRole = ProjectAdminRole.class.cast(role);
				return projectAdminRole.getProjects().contains(projectId);
			}
		}

		return false;

	}

	public static boolean hasReaderRole(ACUser user) throws ESException {
		return hasRole(user, RolesPackage.eINSTANCE.getReaderRole());
	}

	public static boolean hasWriterRole(ACUser user) throws ESException {
		return hasRole(user, RolesPackage.eINSTANCE.getWriterRole());
	}

	public static boolean hasRole(ACUser user, EClass expectedRole) throws ESException {

		final ACOrgUnit orgUnit = getSuperAdminBroker().getOrgUnit(user.getId());

		for (final Role role : orgUnit.getRoles()) {
			if (role.eClass().equals(expectedRole)) {
				return true;
			}
		}

		return false;
	}
}
