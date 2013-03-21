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

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESGlobalProjectId;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Represents a project in EMFStore. There are two different types of projects, {@link ESLocalProject} and
 * {@link ESRemoteProject}. Representing a locally checked out and a project on the server. This interface groups all
 * attributes and calls which are available on both.
 * 
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESProject {

	/**
	 * Return the global ID of the project. This ID is globally unique even among different server and client nodes.
	 * 
	 * @return the global ID of the project
	 */
	ESGlobalProjectId getGlobalProjectId();

	/**
	 * Returns the project's name.
	 * 
	 * @return the name of the project
	 */
	String getProjectName();

	/**
	 * <p>
	 * Deletes the project.
	 * </p>
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link ESUsersession}.
	 * </p>
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while deleting the project
	 * 
	 * @throws IOException
	 *             in case an I/O related error occurred while deleting the project
	 * @throws ESException
	 *             in case any other error occurred while deleting the project
	 */
	void delete(IProgressMonitor monitor) throws IOException, ESException;

	/**
	 * Resolves a {@link ESVersionSpec} to a {@link ESPrimaryVersionSpec} by querying the server.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link ESUsersession}.
	 * 
	 * @param versionSpec
	 *            the {@link ESVersionSpec} to resolve
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while resolving the version
	 * 
	 * @return the resolved {@link ESPrimaryVersionSpec}
	 * 
	 * @throws ESException in case an error occurs while resolving the given {@link ESVersionSpec}
	 */
	ESPrimaryVersionSpec resolveVersionSpec(ESVersionSpec versionSpec, IProgressMonitor monitor)
		throws ESException;

	/**
	 * <p>
	 * Returns a list of branches for the current project.
	 * </p>
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link ESUsersession}.
	 * </p>
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the branch
	 *            information
	 * 
	 * @return a list containing information about all branches for the current project
	 * 
	 * @throws ESException in case an error occurs while retrieving the branch information for the project
	 */
	List<ESBranchInfo> getBranches(IProgressMonitor monitor) throws ESException;

	/**
	 * <p>
	 * Retrieves a part of the project's version history from the server based on the given query. Use
	 * {@link org.eclipse.emf.emfstore.server.model.query.ESHistoryQueryFactory} to generate query objects.
	 * </p>
	 * 
	 * @param query
	 *            the {@link ESHistoryQuery} to be performed in order to fetch the history information
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the history
	 *            information
	 * 
	 * @return a list containing the history information for the given query
	 * 
	 * @throws ESException in case an error occurs while retrieving the history information
	 */
	<U extends ESHistoryQuery<?>> List<ESHistoryInfo> getHistoryInfos(U query, IProgressMonitor monitor)
		throws ESException;

	/**
	 * <p>
	 * Adds a tag to the specified version of this project on the server.
	 * </p>
	 * 
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link ESUsersession}.
	 * </p>
	 * 
	 * @param versionSpec
	 *            the {@link ESPrimaryVersionSpec} that should be tagged
	 * @param tag
	 *            the tag being created
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while adding the tag
	 * 
	 * @throws ESException in case the given tag could not be removed
	 */
	void addTag(ESPrimaryVersionSpec versionSpec, ESTagVersionSpec tag, IProgressMonitor monitor)
		throws ESException;

	/**
	 * <p>
	 * Removes a tag from the specified version of this project on the server.
	 * </p>
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link ESUsersession}.
	 * </p>
	 * 
	 * @param versionSpec
	 *            the {@link ESPrimaryVersionSpec} identifying the version from which the tag should be removed
	 * @param tag
	 *            the {@link ESTagVersionSpec} to be removed
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while removing the tag
	 * 
	 * @throws ESException in case the given tag could not be removed
	 */
	void removeTag(ESPrimaryVersionSpec versionSpec, ESTagVersionSpec tag, IProgressMonitor monitor)
		throws ESException;
}
