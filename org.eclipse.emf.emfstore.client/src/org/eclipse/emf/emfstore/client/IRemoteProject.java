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
	 * Checkout the project into the local workspace.
	 * 
	 * When calling this method on a remote project it is recommended to use the overloaded method which allows to
	 * specify an {@link IUsersession}.
	 * 
	 * @return local project
	 */
	ILocalProject checkout() throws EMFStoreException;

	/**
	 * Checkout the project into the local workspace.
	 * 
	 * 
	 * 
	 * @param usersession session used for server call
	 * @return local project
	 */
	ILocalProject checkout(final IUsersession usersession) throws EMFStoreException;

	/**
	 * Checkout the project into the local workspace.
	 * 
	 * @param usersession session used for server call
	 * @param progressMonitor
	 *            the progress monitor that should be used during checkout
	 * @return local project
	 */
	ILocalProject checkout(final IUsersession usersession, IProgressMonitor progressMonitor) throws EMFStoreException;

	/**
	 * Checkout the project in the given version into the local workspace.
	 * 
	 * @param usersession session used for server call
	 * @param versionSpec version which should be checked out
	 * @param progressMonitor
	 *            the progress monitor that should be used during checkout
	 * @return local project
	 */
	ILocalProject checkout(final IUsersession usersession, IVersionSpec versionSpec, IProgressMonitor progressMonitor)
		throws EMFStoreException;

	/**
	 * Resolves a {@link IVersionSpec} to a {@link IPrimaryVersionSpec} by querying the server.
	 * 
	 * 
	 * @param usersession session used for server call
	 * @param versionSpec the spec to resolve
	 * @return the primary version
	 */
	IPrimaryVersionSpec resolveVersionSpec(IUsersession usersession, IVersionSpec versionSpec) throws EMFStoreException;

	List<IBranchInfo> getBranches(IUsersession usersession) throws EMFStoreException;

	/**
	 * Gets a list of history infos from the server.
	 * 
	 * @param usersession session used for server call
	 * @param query
	 *            the query to be performed in order to fetch the history
	 *            information
	 * 
	 * @return a list of history infos
	 */
	List<IHistoryInfo> getHistoryInfos(IUsersession usersession, IHistoryQuery query) throws EMFStoreException;

	/**
	 * Deletes the remote project on the server.
	 */
	void delete(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Deletes the remote project on the server.
	 * 
	 * @param usersession session used for server call
	 */
	void delete(IUsersession usersession, IProgressMonitor monitor) throws EMFStoreException;

	IPrimaryVersionSpec getHeadVersion() throws EMFStoreException;
}
