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
import java.util.List;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.client.ESServer;
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
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesFactory;
import org.eclipse.emf.emfstore.internal.server.model.dao.ACDAOFacade;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.osgi.framework.FrameworkUtil;

/**
 * 
 * @author emueller
 * 
 */
public final class ServerUtil {

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
		final ServerSpace serverSpace = initServerSpace();

		// perform initial default setup

		CommonUtil.setTesting(true);

		// copy es.properties file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getConfFile(), "es.properties",
			"Couldn't copy es.properties file to config folder.",
			"Default es.properties file was copied to config folder.");

		// copy keystore file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getServerKeyStorePath(), ServerConfiguration.SERVER_KEYSTORE_FILE,
			"Failed to copy keystore.", "Keystore was copied to server workspace.");

		return ESServer.FACTORY.createAndStartLocalServer();
	}

	public static void stopServer() {
		ESServer.FACTORY.stopLocalServer();
	}

	public static ServerMock startMockServer() throws ESServerStartFailedException,
		IllegalArgumentException,
		FatalESException {

		// AuthControlMock authMock = new AuthControlMock();
		final ServerSpace serverSpace = initServerSpace();

		// perform initial default setup

		CommonUtil.setTesting(true);

		// copy es.properties file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getConfFile(), "es.properties",
			"Couldn't copy es.properties file to config folder.",
			"Default es.properties file was copied to config folder.");

		// copy keystore file to workspace if not existent
		copyFileToWorkspace(ServerConfiguration.getServerKeyStorePath(), ServerConfiguration.SERVER_KEYSTORE_FILE,
			"Failed to copy keystore.", "Keystore was copied to server workspace.");

		ServerConfiguration.setProperties(initProperties());
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
			new AdminConnectionManagerMock(daoFacadeMock, accessControl));

		final ESServer createServer = ESServer.FACTORY.createServer("localhost",
			Integer.parseInt(ServerConfiguration.XML_RPC_PORT_DEFAULT),
			KeyStoreManager.DEFAULT_CERTIFICATE);

		return new ServerMock(createServer, emfStore, serverSpace);
	}

	public static void deleteUser(SessionId sessionId, ACOrgUnitId user) throws ESException {
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().deleteUser(sessionId, user);
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
		superUser.setLastName("user");
		superUser.setDescription("default server admin (superuser)");
		superUser.getRoles().add(RolesFactory.eINSTANCE.createServerAdmin());
		daoFacade.add(superUser);

		ModelUtil.logInfo("added superuser " + superuser);
	}

	private static Properties initProperties() {
		final File propertyFile = new File(ServerConfiguration.getConfFile());
		final Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyFile);
			properties.load(fis);
			ServerConfiguration.setProperties(properties, false);
			ModelUtil.logInfo("Property file read. (" + propertyFile.getAbsolutePath() + ")");
		} catch (final IOException e) {
			ModelUtil.logWarning("Property initialization failed, using default properties.", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (final IOException e) {
				ModelUtil.logWarning("Closing of properties file failed.", e);
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
				"org.eclipse.emf.emfstore.server.configurationResource");
			final ESExtensionElement element = extensionPoint.getFirst();

			if (element != null) {

				final String attribute = element.getAttribute(targetFile.getName());

				if (attribute != null) {
					try {
						FileUtil.copyFile(new URL("platform:/plugin/"
							+ element.getIConfigurationElement().getNamespaceIdentifier() + "/" + attribute)
							.openConnection().getInputStream(), targetFile);
						return;
					} catch (final IOException e) {
						ModelUtil.logWarning("Copy of file from " + source + " to " + target + " failed", e);
					}
				}
			}
			// Guess not, lets copy the default configuration resources
			try {
				FileUtil.copyFile(getResource("testFiles/" + source), targetFile);
			} catch (final IOException e) {
				ModelUtil.logWarning("Copy of file from " + source + " to " + target + " failed", e);
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
		final Resource resource = set.createResource(URI.createURI(""));
		final ServerSpace serverSpace = ModelFactory.eINSTANCE.createServerSpace();
		resource.getContents().add(serverSpace);
		return serverSpace;
	}

}
