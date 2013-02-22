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
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Represents a remote project on the server.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESRemoteProject extends ESProject {

	/**
	 * Returns the project's server.
	 * 
	 * @return server
	 */
	ESServer getServer();

	/**
	 * <p>
	 * Checkouts the project in its HEAD version into the local workspace.
	 * </p>
	 * <p>
	 * The {@link ESUsersession} used for checking out the project will be injected.
	 * </p>
	 * 
	 * @param monitor
	 *            the progress monitor that is used during checkout in order to indicate progress
	 * 
	 * @return the checked out project
	 * 
	 * @throws ESException in case an error occurs during checkout
	 */
	ESLocalProject checkout(IProgressMonitor monitor) throws ESException;

	/**
	 * <p>
	 * Checkouts the project in the given version into the local workspace.
	 * </p>
	 * <p>
	 * The caller must provide the {@link ESUsersession} used for checking out the project, e.g. by specifying the
	 * session that was lastly used in an {@link ESServer}.
	 * </p>
	 * <p>
	 * The caller also must specify which specific {@link ESVersionSpec} to checkout. If the HEAD version should get
	 * checked out, the {@link org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory} must be used, which
	 * can be fetched via {@link ESVersionSpec#FACTORY}.
	 * </p>
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.provider.ESServerCall} to checkout the project
	 * @param versionSpec
	 *            the version that should be checked out.
	 * @param monitor
	 *            the {@link IProgressMonitor} that is used during checkout in order to indicate progress
	 * 
	 * @return the checked out project
	 * 
	 * @throws ESException in case an error occurs during checkout
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory
	 * @see ESServer#getLastUsersession()
	 */
	ESLocalProject checkout(final ESUsersession usersession, ESVersionSpec versionSpec, IProgressMonitor monitor)
		throws ESException;

	/**
	 * Resolves a {@link ESVersionSpec} to a {@link ESPrimaryVersionSpec} by querying the server.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.provider.ESServerCall} to resolve the given
	 *            {@link ESVersionSpec}
	 * @param versionSpec
	 *            the version specifier to resolve
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while resolving the version
	 *            specifier
	 * @return the resolved primary version
	 * 
	 * @throws ESException in case an error occurs while resolving the version
	 */
	ESPrimaryVersionSpec resolveVersionSpec(ESUsersession usersession, ESVersionSpec versionSpec,
		IProgressMonitor monitor)
		throws ESException;

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
	 * @throws ESException in case an error occurs while retrieving the branch information for the project
	 */
	List<ESBranchInfo> getBranches(ESUsersession usersession, IProgressMonitor monitor) throws ESException;

	/**
	 * Retrieves a part of the project's version history from the server based on the given query. Use
	 * {@link org.eclipse.emf.emfstore.server.model.query.ESHistoryQueryFactory} to generate query objects.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.provider.ESServerCall} to fetch the history information
	 * @param query
	 *            the query to be performed in order to fetch the history information
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the history
	 *            information
	 * 
	 * @return a list of history infos
	 * 
	 * @throws ESException in case an error occurs while retrieving the history information
	 */
	List<ESHistoryInfo> getHistoryInfos(ESUsersession usersession, ESHistoryQuery query, IProgressMonitor monitor)
		throws ESException;

	/**
	 * Deletes the remote project on the server.
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} used to indicate progress
	 * 
	 * @throws ESException
	 *             in case an error occurs during the deletion of the project
	 */
	void delete(IProgressMonitor monitor) throws ESException;

	/**
	 * Deletes the remote project on the server.
	 * 
	 * @param usersession
	 *            the user session that will be used by the
	 *            {@link org.eclipse.emf.emfstore.client.provider.ESServerCall} to delete the remote project
	 * @param monitor
	 *            an {@link IProgressMonitor} used to indicate progress
	 * 
	 * @throws ESException
	 *             in case an error occurs during the deletion of the project
	 */
	void delete(ESUsersession usersession, IProgressMonitor monitor) throws ESException;

	/**
	 * Returns the HEAD version of the project.
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} used to indicate progress
	 * 
	 * @return the HEAD version
	 * 
	 * @throws ESException in case an error occurs while fetching the HEAD version of the project
	 */
	ESPrimaryVersionSpec getHeadVersion(IProgressMonitor monitor) throws ESException;
}
