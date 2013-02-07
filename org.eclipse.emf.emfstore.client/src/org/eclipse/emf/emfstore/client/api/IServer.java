/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.api;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.impl.ServerFactoryImpl;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;

/**
 * Represents an EMFStore server and its API. It gives access to {@link IRemoteProject} which can be used to operate on
 * existing projects.
 * 
 * @author emueller
 * @author wesendon
 */
public interface IServer {

	/**
	 * Factory for creating IServer instances.
	 */
	IServerFactory FACTORY = ServerFactoryImpl.INSTANCE;

	/**
	 * Returns the server's local name.
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * Sets the server's local name.
	 * 
	 * @param name new name
	 */
	void setName(String name);

	/**
	 * Returns the server's port.
	 * 
	 * @return port
	 */
	int getPort();

	/**
	 * Sets the server's port.
	 * 
	 * @param port new port
	 */
	void setPort(int port);

	/**
	 * Returns the server's url.
	 * 
	 * @return url
	 */
	String getUrl();

	/**
	 * Sets the server's url.
	 * 
	 * @param url new url
	 */
	void setUrl(String url);

	/**
	 * Returns the alias for the certificate used for this server.
	 * Certificates are managed by the {@link KeyStoreManager}
	 * 
	 * @return alias
	 */
	String getCertificateAlias();

	/**
	 * Sets a new certificate alias.
	 * 
	 * @param alias new alias
	 */
	void setCertificateAlias(String alias);

	/**
	 * Makes a server call in order to fetch the current list of available projects.
	 * 
	 * @param shouldRemember caches the retrieved list locally. Accessible via {@link #getRemoteProjects()}
	 * @return list of remote projects
	 * @throws EMFStoreException
	 */
	List<? extends IRemoteProject> getRemoteProjectsFromServer(boolean shouldRemember) throws EMFStoreException;

	/**
	 * Makes a server call in order to fetch the current list of available projects.
	 * 
	 * @param session executes the server call with the given session
	 * @param shouldRemember caches the retrieved list locally. Accessible via {@link #getRemoteProjects()}
	 * @return list of remote projects
	 * @throws EMFStoreException
	 */
	List<? extends IRemoteProject> getRemoteProjectsFromServer(IUsersession session, boolean shouldRemember)
		throws EMFStoreException;

	/**
	 * Returns the usersession which was used on the last call to this server.
	 * 
	 * @return usersession
	 */
	IUsersession getLastUsersession();

	/**
	 * Creates an empty project on the server.
	 * 
	 * @param projectName
	 *            The name of the project.
	 * @param projectDescription
	 *            A description of the project to be created.
	 * @param monitor
	 *            a progress monitor instance that is used to indicate progress
	 *            about creating the remote project
	 * @return a {@link IRemoteProject} object containing information about the
	 *         created project
	 * @throws EMFStoreException
	 *             If an error occurs while creating the remote project
	 */
	IRemoteProject createRemoteProject(final String projectName, final String projectDescription,
		final IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Creates an empty project on the server.
	 * 
	 * @param usersession
	 *            The {@link IUsersession} that should be used to create the
	 *            remote project.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @param projectName
	 *            The name of the project.
	 * @param projectDescription
	 *            A description of the project to be created.
	 * @param monitor
	 *            a monitor to show the progress
	 * @return a {@link IRemoteProject} object containing information about the
	 *         created project
	 * @throws EMFStoreException
	 *             If an error occurs while creating the remote project
	 */
	IRemoteProject createRemoteProject(IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Logs into this server, returing a {@link IUsersession}.
	 * 
	 * @param name username
	 * @param password password
	 * @return {@link IUsersession}
	 * @throws EMFStoreException
	 */
	IUsersession login(String name, String password) throws EMFStoreException;

}
