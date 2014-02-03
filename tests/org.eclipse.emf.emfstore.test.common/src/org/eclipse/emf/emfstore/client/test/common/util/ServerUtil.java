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
package org.eclipse.emf.emfstore.client.test.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.client.test.common.mocks.AdminConnectionManagerMock;
import org.eclipse.emf.emfstore.client.test.common.mocks.ConnectionMock;
import org.eclipse.emf.emfstore.client.test.common.mocks.DAOFacadeMock;
import org.eclipse.emf.emfstore.client.test.common.mocks.ResourceFactoryMock;
import org.eclipse.emf.emfstore.client.test.common.mocks.ServerMock;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControlImpl;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AuthenticationControlType;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.factory.AuthenticationControlFactory;
import org.eclipse.emf.emfstore.internal.server.core.EMFStoreImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnit;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesFactory;
import org.eclipse.emf.emfstore.internal.server.model.dao.ACDAOFacade;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESSessionIdImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.osgi.framework.FrameworkUtil;

/**
 * 
 * @author emueller
 * 
 */
public final class ServerUtil {

	private static final String ES_PROPERTIES_FILE = "es.properties"; //$NON-NLS-1$

	private ServerUtil() {

	}

	private static final String SUPER = "super"; //$NON-NLS-1$

	/**
	 * Returns the default port of EMFStore which is 8888.
	 * 
	 * @return the default port of EMFStore
	 */
	public static int defaultPort() {
		return 8080;
	}

	/**
	 * Returns the default port of EMFStore which is 8888.
	 * 
	 * @return the default port of EMFStore
	 */
	public static String localhost() {
		return "localhost"; //$NON-NLS-1$
	}

	public static String superUserPassword() {
		return SUPER;
	}

	public static String superUser() {
		return SUPER;
	}

	public static ESServer startServer() throws ESServerStartFailedException {
		// AuthControlMock authMock = new AuthControlMock();
		initServerSpace();

		// perform initial default setup

		CommonUtil.setTesting(true);

		// copy es.properties file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getConfFile(),
			ES_PROPERTIES_FILE,
			Messages.ServerUtil_Could_Not_Copy_Properties_File_To_Config_Folder,
			Messages.ServerUtil_Default_Properties_File_Copied_To_Config_Folder);

		// copy keystore file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getServerKeyStorePath(),
			ServerConfiguration.SERVER_KEYSTORE_FILE,
			Messages.ServerUtil_Failed_To_Copy_Keystore,
			Messages.ServerUtil_Keystore_Copied_To_Server_Workspace);

		return ESServer.FACTORY.createAndStartLocalServer();
	}

	public static void stopServer() {
		ESServer.FACTORY.stopLocalServer();
	}

	public static ServerMock startMockServer() throws IllegalArgumentException, ESServerStartFailedException,
		FatalESException {
		return startMockServer(Collections.<String, String> emptyMap());
	}

	public static ServerMock startMockServer(Map<String, String> properties) throws ESServerStartFailedException,
		IllegalArgumentException,
		FatalESException {

		// AuthControlMock authMock = new AuthControlMock();
		final ServerSpace serverSpace = initServerSpace();

		// perform initial default setup

		CommonUtil.setTesting(true);

		// copy es.properties file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getConfFile(), ES_PROPERTIES_FILE,
			Messages.ServerUtil_Could_Not_Copy_Properties_File_To_Config_Folder,
			Messages.ServerUtil_Default_Properties_File_Copied_To_Config_Folder);

		// copy keystore file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getServerKeyStorePath(), ServerConfiguration.SERVER_KEYSTORE_FILE,
			Messages.ServerUtil_Failed_To_Copy_Keystore, Messages.ServerUtil_Keystore_Copied_To_Server_Workspace);

		ServerConfiguration.setProperties(initProperties(properties));
		final DAOFacadeMock daoFacadeMock = new DAOFacadeMock();
		setSuperUser(daoFacadeMock);
		final AccessControl accessControl = new AccessControlImpl(daoFacadeMock);
		accessControl.setAuthenticationControl(AuthenticationControlFactory.INSTANCE
			.createAuthenticationControl(AuthenticationControlType.model));

		// AdminConnectionManagerMock adminConnectionManagerMock = new AdminConnectionManagerMock(accessControl);

		final EMFStore emfStore = EMFStoreImpl.createInterface(serverSpace, accessControl);
		ESWorkspaceProviderImpl.getInstance().setConnectionManager(
			new ConnectionMock(emfStore, accessControl));
		ESWorkspaceProviderImpl.getInstance().setAdminConnectionManager(
			new AdminConnectionManagerMock(daoFacadeMock, accessControl, serverSpace));

		final ESServer createServer = ESServer.FACTORY.createServer("localhost", //$NON-NLS-1$
			Integer.parseInt(ServerConfiguration.XML_RPC_PORT_DEFAULT),
			KeyStoreManager.DEFAULT_CERTIFICATE);

		return new ServerMock(createServer, emfStore, serverSpace);
	}

	public static void deleteUser(SessionId sessionId, ACOrgUnitId user) throws ESException {
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().deleteUser(sessionId, user);
	}

	/**
	 * Convenience method for deleting a group by name instead of its ID.
	 * 
	 * @param session
	 *            the {@link ESUsersession} that is used to connection to the admin connection manager
	 * @param groupName
	 *            the name of the group to be deleted. Case is ignored
	 * 
	 * @return {@code true}, if the groups has been deleted successfully, {@code false} otherwise
	 * 
	 * @throws ESException
	 *             in case the delete fails
	 */
	public static boolean deleteGroup(ESUsersession session, String groupName) throws ESException {

		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());

		final List<ACGroup> groups = ESWorkspaceProviderImpl
			.getInstance()
			.getAdminConnectionManager()
			.getGroups(sessionId.toInternalAPI());

		ACOrgUnitId id = null;

		for (final ACGroup acGroup : groups) {
			if (acGroup.getName().equalsIgnoreCase(groupName)) {
				id = acGroup.getId();
				break;
			}
		}

		if (id != null) {
			ESWorkspaceProviderImpl
				.getInstance()
				.getAdminConnectionManager()
				.deleteGroup(sessionId.toInternalAPI(), id);
			return true;
		}

		return false;
	}

	public static ACOrgUnitId createUser(ESUsersession session, String name) throws ESException {
		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());
		return ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.createUser(sessionId.toInternalAPI(), name);
	}

	public static ACOrgUnitId createGroup(ESUsersession session, String name) throws ESException {
		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());
		return ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.createGroup(sessionId.toInternalAPI(), name);
	}

	// TODO: user parameter is not needed
	public static void changePassword(ESUsersession session, ACOrgUnitId userId, String user, String password)
		throws ESException {
		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.changeUser(sessionId.toInternalAPI(), userId, user, password);
	}

	public static ACOrgUnitId createUser(SessionId sessionId, String name) throws ESException {
		return ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().createUser(sessionId, name);
	}

	public static void changeUser(SessionId sessionId, ACOrgUnitId userId, String userName, String password)
		throws ESException {
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.changeUser(sessionId, userId, userName, password);
	}

	public static ACUser getUser(ESUsersession session, String name) throws ESException {
		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());
		final List<ACUser> users = ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.getUsers(sessionId.toInternalAPI());
		for (final ACUser acUser : users) {
			if (acUser.getName().equals(name)) {
				return acUser;
			}
		}

		return null;
	}

	public static ACUser getUser(ESUsersession session, ACOrgUnitId userId) throws ESException {
		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());
		final List<ACUser> users = ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.getUsers(sessionId.toInternalAPI());
		for (final ACUser acUser : users) {
			if (acUser.getId().getId().equals(userId.getId())) {
				return acUser;
			}
		}

		return null;
	}

	public static ACUser getParticipant(ESUsersession session, ProjectId projectId, ACOrgUnitId userId)
		throws ESException {
		final ESSessionIdImpl sessionId = ESSessionIdImpl.class.cast(session.getSessionId());
		final List<ACOrgUnit> users = ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.getParticipants(sessionId.toInternalAPI(), projectId);
		for (final ACOrgUnit acUser : users) {
			if (acUser.getId().getId().equals(userId.getId()) && acUser instanceof ACUser) {
				return (ACUser) acUser;
			}
		}

		return null;
	}

	public static ACUser getUser(SessionId sessionId, String name) throws ESException {
		final List<ACUser> users = ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager()
			.getUsers(sessionId);
		for (final ACUser acUser : users) {
			if (acUser.getName().equals(name)) {
				return acUser;
			}
		}

		return null;
	}

	private static void setSuperUser(ACDAOFacade daoFacade) throws FatalESException {
		final String superuser = ServerConfiguration.getProperties().getProperty(ServerConfiguration.SUPER_USER,
			ServerConfiguration.SUPER_USER_DEFAULT);
		for (final ACUser user : daoFacade.getUsers()) {
			if (user.getName().equals(superuser)) {
				return;
			}
		}
		final ACUser superUser = AccesscontrolFactory.eINSTANCE.createACUser();
		superUser.setName(superuser);
		superUser.setFirstName(SUPER);
		superUser.setLastName("user"); //$NON-NLS-1$
		superUser.setDescription(Messages.ServerUtil_ServerAdmin_Description);
		superUser.getRoles().add(RolesFactory.eINSTANCE.createServerAdmin());
		daoFacade.add(superUser);

		ModelUtil.logInfo(Messages.ServerUtil_Added_Superuser + superuser);
	}

	public static Properties initProperties(Map<String, String> additionalProperties) {
		final File propertyFile = new File(ServerConfiguration.getConfFile());
		final Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyFile);
			properties.load(fis);
			ServerConfiguration.setProperties(properties, false);
			ModelUtil.logInfo("Property file read. (" + propertyFile.getAbsolutePath() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			for (final Map.Entry<String, String> entry : additionalProperties.entrySet()) {
				if (StringUtils.isNotEmpty(entry.getValue())) {
					properties.setProperty(entry.getKey(), entry.getValue());
				}
			}
		} catch (final IOException e) {
			ModelUtil.logWarning(Messages.ServerUtil_Property_Init_Failed, e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (final IOException e) {
				ModelUtil.logWarning(Messages.ServerUtil_Could_Not_Close_File, e);
			}
		}

		return properties;
	}

	private static void copyFileToWorkspace(String target, String source, String failure, String success) {

		final File targetFile = new File(target);

		if (!targetFile.exists()) {
			// check if the custom configuration resources are provided and if,
			// copy them to place
			final ESExtensionPoint extensionPoint = new ESExtensionPoint(
				"org.eclipse.emf.emfstore.server.configurationResource"); //$NON-NLS-1$
			final ESExtensionElement element = extensionPoint.getFirst();

			if (element != null) {

				final String attribute = element.getAttribute(targetFile.getName());

				if (attribute != null) {
					try {
						FileUtil.copyFile(new URL("platform:/plugin/" //$NON-NLS-1$
							+ element.getIConfigurationElement().getNamespaceIdentifier() + "/" + attribute) //$NON-NLS-1$
							.openConnection().getInputStream(), targetFile);
						return;
					} catch (final IOException e) {
						ModelUtil.logWarning("Copy of file from " + source + " to " + target + " failed", e); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
				}
			}
			// Guess not, lets copy the default configuration resources
			try {
				FileUtil.copyFile(getResource("testFiles/" + source), targetFile); //$NON-NLS-1$
			} catch (final IOException e) {
				ModelUtil.logWarning("Copy of file from " + source + " to " + target + " failed", e); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}

	}

	// TODO: duplicate method
	private static InputStream getResource(String resource) throws IOException {

		final URL configURL =
			FrameworkUtil.getBundle(ServerUtil.class).getEntry(resource);
		// Activator.getDefault().getBundle().getBundleContext().getBundle()
		// .getEntry(resource);

		if (configURL != null) {
			final InputStream input = configURL.openStream();
			return input;
		}

		return null;
	}

	private static ServerSpace initServerSpace() {
		final ResourceSetImpl set = new ResourceSetImpl();
		set.setResourceFactoryRegistry(new ResourceFactoryMock());
		final Resource resource = set.createResource(URI.createURI("")); //$NON-NLS-1$
		final ServerSpace serverSpace = ModelFactory.eINSTANCE.createServerSpace();
		resource.getContents().add(serverSpace);
		return serverSpace;
	}

}
