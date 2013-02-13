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

package org.eclipse.emf.emfstore.client;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.IGlobalProjectId;
import org.eclipse.emf.emfstore.server.model.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec;

/**
 * Represents a project in EMFStore. There are two different types of projects, {@link ESLocalProject} and
 * {@link IRemoteProject}. Representing a locally checked out and a project on the server. This interface groups all
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
	IGlobalProjectId getGlobalProjectId();

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
	 * specify an {@link IUsersession}.
	 * </p>
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while deleting the project
	 * 
	 * @throws IOException
	 *             in case an I/O related error occurred while deleting the project
	 * @throws EMFStoreException
	 *             in case any other error occurred while deleting the project
	 */
	void delete(IProgressMonitor monitor) throws IOException, EMFStoreException;

	/**
	 * Resolves a {@link IVersionSpec} to a {@link IPrimaryVersionSpec} by querying the server.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @param versionSpec
	 *            the {@link IVersionSpec} to resolve
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while resolving the version
	 * 
	 * @return the resolved {@link IPrimaryVersionSpec}
	 * 
	 * @throws EMFStoreException in case an error occurs while resolving the given {@link IVersionSpec}
	 */
	IPrimaryVersionSpec resolveVersionSpec(IVersionSpec versionSpec, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * <p>
	 * Returns a list of branches for the current project.
	 * </p>
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * </p>
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the branch
	 *            information
	 * 
	 * @return a list containing information about all branches for the current project
	 * 
	 * @throws EMFStoreException in case an error occurs while retrieving the branch information for the project
	 */
	List<IBranchInfo> getBranches(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * <p>
	 * Retrieves a part of the project's version history from the server based on the given query. Use
	 * {@link org.eclipse.emf.emfstore.server.model.query.IHistoryQueryFactory} to generate query objects.
	 * </p>
	 * 
	 * @param query
	 *            the {@link IHistoryQuery} to be performed in order to fetch the history information
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while fetching the history
	 *            information
	 * 
	 * @return a list containg the history information for the given query
	 * 
	 * @throws EMFStoreException in case an error occurs while retrieving the history information
	 */
	List<IHistoryInfo> getHistoryInfos(IHistoryQuery query, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * <p>
	 * Adds a tag to the specified version of this project on the server.
	 * </p>
	 * 
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * </p>
	 * 
	 * @param versionSpec
	 *            the {@link IPrimaryVersionSpec} that should be tagged
	 * @param tag
	 *            the tag being created
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while adding the tag
	 * 
	 * @throws EMFStoreException in case the given tag could not be removed
	 */
	void addTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * <p>
	 * Removes a tag from the specified version of this project on the server.
	 * </p>
	 * <p>
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * </p>
	 * 
	 * @param versionSpec
	 *            the {@link IPrimaryVersionSpec} identifying the version from which the tag should be removed
	 * @param tag
	 *            the {@link ITagVersionSpec} to be removed
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while removing the tag
	 * 
	 * @throws EMFStoreException in case the given tag could not be removed
	 */
	void removeTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag, IProgressMonitor monitor)
		throws EMFStoreException;
}
