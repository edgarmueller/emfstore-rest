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

import java.io.IOException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.IConflictResolver;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.common.model.EObjectContainer;
import org.eclipse.emf.emfstore.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.api.ILogMessage;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

/**
 * Represents a locally checkedout project.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ILocalProject extends IProject, EObjectContainer {

	IPrimaryVersionSpec commit() throws InvalidVersionSpecException, BaseVersionOutdatedException, EMFStoreException;

	IPrimaryVersionSpec commit(ILogMessage logMessage, ICommitCallback callback, IProgressMonitor monitor)
		throws InvalidVersionSpecException, BaseVersionOutdatedException, EMFStoreException;

	IPrimaryVersionSpec commitToBranch(IBranchVersionSpec branch, ILogMessage logMessage, ICommitCallback callback,
		IProgressMonitor monitor) throws InvalidVersionSpecException, BaseVersionOutdatedException, EMFStoreException;

	/**
	 * Updates the project to the head version from the server.
	 * 
	 * @return the new base version
	 * @throws EMFStoreException
	 *             if update fails
	 */
	IPrimaryVersionSpec update() throws ChangeConflictException, EMFStoreException;

	/**
	 * Updates the project to the given version from the server.
	 * 
	 * @param version
	 *            the version to update to
	 * @return the new base version
	 */
	IPrimaryVersionSpec update(IVersionSpec version) throws ChangeConflictException, EMFStoreException;

	/**
	 * Updates the project to the given version from the server.
	 * 
	 * @param version
	 *            the {@link IVersionSpec} to update to
	 * @param callback
	 *            the {@link IUpdateCallback} that will be called when the update
	 *            has been performed
	 * @param progress
	 *            an {@link IProgressMonitor} instance
	 * @return the new version spec
	 */
	IPrimaryVersionSpec update(IVersionSpec version, IUpdateCallback callback, IProgressMonitor progress)
		throws ChangeConflictException, EMFStoreException;

	/**
	 * Merge the changes from current base version to given target version with
	 * the local operations.
	 * 
	 * @param target
	 *            a target version
	 * @param conflictException
	 *            a {@link ChangeConflictException} containing the changes to be merged
	 * @param conflictResolver
	 *            a {@link IConflictResolver} that will actually perform the conflict
	 *            resolution
	 * @param callback
	 *            the {@link IUpdateCallback} that is called in case the checksum comparison fails
	 * @param progressMonitor
	 *            an {@link IProgressMonitor} to report on progress
	 * 
	 * @throws EMFStoreException
	 *             if the connection to the server fails
	 * @return true, if merge was successful, false otherwise
	 * 
	 * @see IUpdateCallback#checksumCheckFailed(ProjectSpace, PrimaryVersionSpec, IProgressMonitor)
	 * 
	 * @generated NOT
	 */
	boolean merge(IPrimaryVersionSpec target, ChangeConflictException conflictException,
		IConflictResolver conflictResolver, IUpdateCallback callback, IProgressMonitor progressMonitor)
		throws EMFStoreException;

	/**
	 * Allows to merge a version from another branch into the current project.
	 * 
	 * @param branchSpec
	 *            the version which is supposed to be merged
	 * @param conflictResolver
	 *            a {@link IConflictResolver} for conflict resolving
	 * @throws EMFStoreException
	 *             in case of an exception
	 */
	void mergeBranch(IPrimaryVersionSpec branchSpec, IConflictResolver conflictResolver) throws EMFStoreException;

	/**
	 * Shares this project space.
	 * 
	 * @throws EMFStoreException
	 *             if an error occurs during the sharing of the project
	 */
	void shareProject() throws EMFStoreException;

	/**
	 * Shares this project space.
	 * 
	 * @param session
	 *            the {@link Usersession} that should be used for sharing the
	 *            project
	 * @param monitor
	 *            an instance of an {@link IProgressMonitor}
	 * 
	 * @throws EMFStoreException
	 *             if an error occurs during the sharing of the project
	 */
	void shareProject(IUsersession session, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Whether this project space has been shared.
	 * 
	 * @return true, if the project space has been shared, false otherwise
	 * 
	 * @generated NOT
	 */
	boolean isShared();

	IUsersession getUsersession();

	IPrimaryVersionSpec getBaseVersion();

	Date getLastUpdated();

	EList<String> getRecentLogMessages();

	/**
	 * Undo the last operation of the projectSpace.
	 * 
	 * @generated NOT
	 */
	void undoLastOperation();

	/**
	 * Undo the last operation <em>n</em> operations of the projectSpace.
	 * 
	 * @param nrOperations
	 *            the number of operations to be undone
	 * 
	 * @generated NOT
	 */
	void undoLastOperations(int nrOperations);

	/**
	 * Determines whether the project is up to date, that is, whether the base
	 * revision and the head revision are equal.
	 * 
	 * @return true, if the project is up to date, false otherwise
	 * @throws EMFStoreException
	 *             if the head revision can not be resolved
	 * 
	 * @generated NOT
	 */
	boolean isUpdated() throws EMFStoreException;

	/**
	 * Revert all local changes in the project space. Returns the state of the
	 * project to that of the project space base version.
	 * 
	 * @generated NOT
	 */
	void revert();

	/**
	 * Saves the project space.
	 * 
	 * @generated NOT
	 */
	void save();

	/**
	 * Determine if the projectspace has unsave changes to any element in the project.
	 * 
	 * @return true if there is unsaved changes.
	 * 
	 * @generated NOT
	 */
	boolean hasUnsavedChanges();

	boolean hasUncommitedChanges();

	void importLocalChanges(String fileName) throws IOException;

	IRemoteProject getRemoteProject() throws EMFStoreException;
}
