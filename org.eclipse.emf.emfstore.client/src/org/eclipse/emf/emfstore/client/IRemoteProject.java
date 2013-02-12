/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
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
package org.eclipse.emf.emfstore.client;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IVersionSpec;

/**
 * Represents a remote project on the server.
 * 
 * @author emueller
 * @author wesendon
 */
public interface IRemoteProject extends IProject {

	/**
	 * Returns the project's server.
	 * 
	 * @return server
	 */
	IServer getServer();

	/**
	 * Checkouts the project in the given version into the local workspace.
	 * 
	 * @param monitor
	 *            the progress monitor that is used during checkout in order to indicate progress
	 * 
	 * @return the checked out project
	 * 
	 * @throws EMFStoreException in case an error occurs during checkout
	 */
	ILocalProject checkout(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Checkouts the project in the given version into the local workspace.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.sessionprovider.IServerCall} to checkout the project
	 * @param monitor
	 *            the progress monitor that is used during checkout in order to indicate progress
	 * 
	 * @return the checked out project
	 * 
	 * @throws EMFStoreException in case an error occurs during checkout
	 */
	ILocalProject checkout(final IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Checkouts the project in the given version into the local workspace.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.sessionprovider.IServerCall} to checkout the project
	 * @param versionSpec
	 *            the version that should be checked out
	 * @param monitor
	 *            the progress monitor that is used during checkout in order to indicate progress
	 * 
	 * @return the checked out project
	 * 
	 * @throws EMFStoreException in case an error occurs during checkout
	 */
	ILocalProject checkout(final IUsersession usersession, IVersionSpec versionSpec, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * Resolves a {@link IVersionSpec} to a {@link IPrimaryVersionSpec} by querying the server.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.sessionprovider.IServerCall} to resolve the given
	 *            {@link IVersionSpec}
	 * @param versionSpec
	 *            the version spec to resolve
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while resolving the version
	 *            spec
	 * @return the resolved primary version
	 * 
	 * @throws EMFStoreException in case an error occurs while resolving the version
	 */
	IPrimaryVersionSpec resolveVersionSpec(IUsersession usersession, IVersionSpec versionSpec, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * Returns all branches for the current project.
	 * 
	 * @param usersession
	 *            the user session used to fetch the branch information
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the branch
	 *            information
	 * 
	 * @return a list containing information about all branches for the current project
	 * 
	 * @throws EMFStoreException in case an error occurs while retrieving the branch information for the project
	 */
	List<IBranchInfo> getBranches(IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Gets a list of history infos from the server.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.sessionprovider.IServerCall} to fetch the history information
	 * @param query
	 *            the query to be performed in order to fetch the history information
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the history
	 *            information
	 * 
	 * @return a list of history infos
	 * 
	 * @throws EMFStoreException in case an error occurs while retrieving the history information
	 */
	List<IHistoryInfo> getHistoryInfos(IUsersession usersession, IHistoryQuery query, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * Deletes the remote project on the server.
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} used to indicate progress
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs during the deletion of the project
	 */
	void delete(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Deletes the remote project on the server.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.sessionprovider.IServerCall} to delete the remote project
	 * @param monitor
	 *            an {@link IProgressMonitor} used to indicate progress
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs during the deletion of the project
	 */
	void delete(IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Returns the HEAD version of the project.
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} used to indicate progress
	 * 
	 * @return the HEAD version
	 * 
	 * @throws EMFStoreException in case an error occurs while fetching the HEAD version of the project
	 */
	IPrimaryVersionSpec getHeadVersion(IProgressMonitor monitor) throws EMFStoreException;
}
