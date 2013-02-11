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
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.query.IHistoryQueryFactory;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IVersionSpec;

/**
 * Represents a project in EMFStore. There are two different types of projects, {@link ILocalProject} and
 * {@link IRemoteProject}. Representing a locally checked out and a project on the server. This interface groups all
 * attributes and calls which are available on both.
 * 
 * 
 * @author emueller
 * @author wesendon
 */
public interface IProject {

	/**
	 * Return the project's id.
	 * 
	 * @return {@link IProject}
	 */
	IProjectId getProjectId();

	/**
	 * Returns the project's name.
	 * 
	 * @return name
	 */
	String getProjectName();

	/**
	 * Deletes the project. This is valid for both, {@link ILocalProject} and {@link IRemoteProject} project.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * 
	 * @throws IOException
	 *             in case the project space could not be deleted
	 */
	void delete(IProgressMonitor monitor) throws IOException, EMFStoreException;

	/**
	 * Resolves a {@link IVersionSpec} to a {@link IPrimaryVersionSpec} by querying the server.
	 * 
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @param versionSpec the spec to resolve
	 * @return the primary version
	 */
	IPrimaryVersionSpec resolveVersionSpec(IVersionSpec versionSpec) throws EMFStoreException;

	/**
	 * Returns a list of branches of the current project. Every call triggers a
	 * server call.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @return list of {@link IBranchInfo}
	 * @throws EMFStoreException
	 *             in case of an exception
	 */
	List<IBranchInfo> getBranches(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Retrieves a part of the project's version history from the server based on the given query. Use
	 * {@link IHistoryQueryFactory} to
	 * generate query obejcts.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @param query
	 *            A history query.
	 * @return a list of {@link IHistoryInfo} objects
	 */
	List<IHistoryInfo> getHistoryInfos(IHistoryQuery query) throws EMFStoreException;

	/**
	 * Adds a tag to the specified version of this project on the server.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @param versionSpec
	 *            the versionSpec
	 * @param tag
	 *            the tag
	 */
	void addTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EMFStoreException;

	/**
	 * Removes a tag to the specified version of this project on the server.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @param versionSpec
	 *            the versionSpec
	 * @param tag
	 *            the tag
	 */
	void removeTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EMFStoreException;
}
