/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerFactoryImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Represents an EMFStore server and its API. It gives access to {@link ESRemoteProject} which can be used to operate on
 * existing projects.
 * 
 * @author emueller
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESServer {

	/**
	 * Factory for creating ESServer instances.
	 */
	ESServerFactory FACTORY = ESServerFactoryImpl.INSTANCE;

	/**
	 * Returns the local name of the server.
	 * 
	 * @return the name of the server
	 */
	String getName();

	/**
	 * Sets the local name of the server.
	 * 
	 * @param name the new local name of th server
	 */
	void setName(String name);

	/**
	 * Returns the port of the server where EMFStore is listening on.
	 * 
	 * @return the port of the server EMFStore is listing on
	 */
	int getPort();

	/**
	 * Sets the port of the server where EMFStore is listening on.
	 * 
	 * @param port
	 *            the port of the server EMFStore is listing on
	 */
	void setPort(int port);

	/**
	 * Returns the URL of the server.
	 * 
	 * @return the URL of the server as a string
	 */
	String getURL();

	/**
	 * Sets the URL of the server.
	 * 
	 * @param url
	 *            the URL of the server as a string
	 */
	void setURL(String url);

	/**
	 * Returns the alias for the certificate used by this server.
	 * Certificates are managed by the
	 * {@link org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager}
	 * 
	 * @return the certificate alias
	 */
	String getCertificateAlias();

	/**
	 * Sets a new certificate alias.
	 * 
	 * @param alias the alias of the the certificate to be set
	 */
	void setCertificateAlias(String alias);

	/**
	 * Returns a list with all remote projects hosted on this server.
	 * 
	 * @return a list with all remote project
	 * 
	 * @throws ESException in case an error occurs while retrieving the list of remote projects
	 */
	List<ESRemoteProject> getRemoteProjects() throws ESException;

	/**
	 * Returns a list with all remote projects hosted on this server.
	 * 
	 * @param usersession
	 *            the {@link ESUsersession} that should be used to fetch the remote projects.<br/>
	 *            If <code>null</code>, the session manager will try to inject a session.
	 * 
	 * @return a list with all remote project
	 * 
	 * @throws ESException in case an error occurs while retrieving the list of remote projects
	 */
	List<ESRemoteProject> getRemoteProjects(ESUsersession usersession) throws ESException;

	/**
	 * Returns the {@link ESUsersession} which was used on the last call to this server.
	 * 
	 * @return the lastly used session
	 */
	ESUsersession getLastUsersession();

	/**
	 * Creates an empty project on the server.
	 * 
	 * @param projectName
	 *            The name of the project to be created
	 * @param monitor
	 *            a {@link IProgressMonitor} instance that is used to indicate progress
	 *            about creating the remote project
	 * @return a {@link ESRemoteProject} object containing information about the
	 *         created project
	 * @throws ESException
	 *             If an error occurs while creating the remote project
	 */
	ESRemoteProject createRemoteProject(String projectName, final IProgressMonitor monitor)
		throws ESException;

	/**
	 * Creates an empty project on the server.
	 * 
	 * @param usersession
	 *            the {@link ESUsersession} that should be used to create the
	 *            remote project.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @param projectName
	 *            the name of the project
	 * @param monitor
	 *            a monitor to show the progress
	 * @return a {@link ESRemoteProject} object containing information about the
	 *         created project
	 * @throws ESException
	 *             If an error occurs while creating the remote project
	 */
	ESRemoteProject createRemoteProject(ESUsersession usersession, String projectName, IProgressMonitor monitor)
		throws ESException;

	/**
	 * Logs into this server, returning a {@link ESUsersession}.
	 * 
	 * @param name
	 *            the name of the user
	 * @param password
	 *            the cleartext password of the user
	 * 
	 * @return a logged in {@link ESUsersession}
	 * @throws ESException in case an error occurs while creating and logging in the session for the given user
	 */
	ESUsersession login(String name, String password) throws ESException;

}
