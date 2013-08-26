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
 * Edgar Mueller - refactorings
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers;

import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.connection.ServerKeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.ClientVersionOutOfDateException;
import org.eclipse.emf.emfstore.internal.server.exceptions.ServerKeyStoreException;
import org.eclipse.emf.emfstore.internal.server.model.AuthenticationInformation;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;

/**
 * Abstract class for authentication.
 * 
 * @author wesendonk
 */
public abstract class AbstractAuthenticationControl implements AuthenticationControl {

	private final String superuser;
	private final String superuserpw;

	/**
	 * Default constructor.
	 */
	public AbstractAuthenticationControl() {
		superuser = ServerConfiguration.getProperties().getProperty(ServerConfiguration.SUPER_USER,
			ServerConfiguration.SUPER_USER_DEFAULT);
		superuserpw = ServerConfiguration.getProperties().getProperty(ServerConfiguration.SUPER_USER_PASSWORD,
			ServerConfiguration.SUPER_USER_PASSWORD_DEFAULT);
	}

	/**
	 * Tries to login the given user.
	 * 
	 * @param resolvedUser
	 *            the user instance as resolved by the user
	 * @param username
	 *            the username as determined by the client
	 * @param password
	 *            the password as entered by the client
	 * @param clientVersionInfo
	 *            the version of the client
	 * @return an {@link AuthenticationInformation} instance holding information about the
	 *         logged-in session
	 * 
	 * @throws AccessControlException in case the login fails
	 */
	public AuthenticationInformation logIn(ACUser resolvedUser, String username, String password,
		ClientVersionInfo clientVersionInfo)
		throws AccessControlException {

		checkClientVersion(clientVersionInfo);
		password = preparePassword(password);

		if (verifySuperUser(username, password) || verifyPassword(resolvedUser, username, password)) {
			return createAuthenticationInfo();
		}

		throw new AccessControlException();
	}

	/**
	 * Creates a new {@link AuthenticationInformation} with a {@link SessionId} set.
	 * 
	 * @return a new instance of an {@link AuthenticationInformation} with an already
	 *         set session ID
	 */
	protected AuthenticationInformation createAuthenticationInfo() {
		final AuthenticationInformation authenticationInformation = ModelFactory.eINSTANCE
			.createAuthenticationInformation();
		authenticationInformation.setSessionId(ModelFactory.eINSTANCE.createSessionId());
		return authenticationInformation;
	}

	/**
	 * Prepares password before it is used for authentication. Normally this includes decrypting the password
	 * 
	 * @param password password
	 * @return prepared password
	 * @throws ServerKeyStoreException in case of an exception
	 */
	protected String preparePassword(String password) throws ServerKeyStoreException {
		return ServerKeyStoreManager.getInstance().decrypt(password);
	}

	/**
	 * Check username and password against superuser.
	 * 
	 * @param username username
	 * @param password password
	 * @return true if super user
	 */
	protected boolean verifySuperUser(String username, String password) {
		return username.equals(superuser) && password.equals(superuserpw);
	}

	/**
	 * {@inheritDoc}
	 */
	public void logout(SessionId sessionId) throws AccessControlException {
	}

	/**
	 * This method must be implemented by subclasses in order to verify a pair of username and password.
	 * When using authentication you should use {@link AuthenticationControl#logIn(String, String)} in order to gain a
	 * session id.
	 * 
	 * @param resolvedUser
	 *            the user instance that has been resolved by the user
	 * @param username
	 *            the username as entered by the client; may differ from the user name of the {@code resolvedUser}
	 * @param password
	 *            the password as entered by the client
	 * @return boolean {@code true} if authentication was successful, {@code false} if not
	 * @throws AccessControlException
	 *             if an exception occurs during the verification process
	 */
	protected abstract boolean verifyPassword(ACUser resolvedUser,
		String username, String password) throws AccessControlException;

	/**
	 * Checks whether the given client version is valid.
	 * If not, throws an exception
	 * 
	 * @param clientVersionInfo
	 *            the client version to be checked
	 * @throws ClientVersionOutOfDateException
	 *             in case the given client version is not valid
	 */
	// TODO include client name in verification
	protected void checkClientVersion(ClientVersionInfo clientVersionInfo) throws ClientVersionOutOfDateException {

		if (clientVersionInfo == null) {
			throw new ClientVersionOutOfDateException("No client version recieved.");
		}

		final String[] versions = ServerConfiguration.getSplittedProperty(ServerConfiguration.ACCEPTED_VERSIONS);

		if (versions == null) {
			final String msg = "No server versions supplied";
			ModelUtil.logWarning(msg, new ClientVersionOutOfDateException(msg));
			return;
		}

		for (final String str : versions) {
			if (str.equals(clientVersionInfo.getVersion()) || str.equals(ServerConfiguration.ACCEPTED_VERSIONS_ANY)) {
				return;
			}
		}

		final StringBuffer version = new StringBuffer();

		for (final String str : versions) {
			if (versions.length == 1) {
				version.append(str + ". ");
			} else {
				version.append(str + ", ");
			}
		}

		version.replace(version.length() - 2, version.length(), ".");

		throw new ClientVersionOutOfDateException("Client version: " + clientVersionInfo.getVersion()
			+ " - Accepted versions: " + version);
	}
}
