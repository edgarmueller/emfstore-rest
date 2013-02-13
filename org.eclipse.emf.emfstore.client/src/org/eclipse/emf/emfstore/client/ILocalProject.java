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
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.common.model.EObjectContainer;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.IConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.api.ILogMessage;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IVersionSpec;

/**
 * Represents a project in the local workspace that was checked out from a server or created locally.
 * 
 * @author emueller
 * @author wesendon
 * @author mkoegel
 */
public interface ILocalProject extends IProject, EObjectContainer {

	/**
	 * <p>
	 * Commits any local changes.
	 * </p>
	 * <p>
	 * If the project has not been shared yet, a {@link RuntimeException} will be thrown.
	 * </p>
	 * <p>
	 * <b>Note</b>: The commit will be executed in headless mode, so no callback can be specified. If clients would like
	 * to influence the commit behavior, they should use {@link #commit(ILogMessage, ICommitCallback, IProgressMonitor)}.
	 * 
	 * @return the new base version, if the commit was successful, otherwise the old base version
	 * 
	 * @throws EMFStoreException in case any error occurs during commit
	 */
	IPrimaryVersionSpec commit() throws EMFStoreException;

	/**
	 * <p>
	 * Commits any local changes.
	 * </p>
	 * <p>
	 * If the project has not been shared yet, a {@link RuntimeException} will be thrown.
	 * </p>
	 * <p>
	 * <b>Note</b>: If no callback is specified the return value will be used to indicate whether the commit was
	 * successful or not. This enables the the headless execution of the commit.
	 * 
	 * @param logMessage
	 *            a {@link ILogMessage} describing the changes being committed
	 * @param callback
	 *            a optional {@link ICommitCallback}
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while committing the project
	 * 
	 * @return the new base version, if the commit was successful, otherwise the old base version
	 * 
	 * @throws BaseVersionOutdatedException in case the local working copy is outdated
	 * @throws EMFStoreException in case any other error occurs during commit
	 */
	IPrimaryVersionSpec commit(ILogMessage logMessage, ICommitCallback callback, IProgressMonitor monitor)
		throws BaseVersionOutdatedException, EMFStoreException;

	/**
	 * <p>
	 * Commits any local changes to a branch.
	 * </p>
	 * <p>
	 * If the project has not been shared yet, a {@link RuntimeException} will be thrown.
	 * </p>
	 * <p>
	 * <b>Note</b>: If no callback is specified the return value will be used to indicate whether the commit was
	 * successful or not. This enables the the headless execution of the commit.
	 * 
	 * @param branch
	 *            the {@link IBranchVersionSpec} indicating the branch to commit to
	 * @param logMessage
	 *            a {@link ILogMessage} describing the changes being committed
	 * @param callback
	 *            a optional {@link ICommitCallback}
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while committing the project
	 * 
	 * @return the new base version, if the commit was successful, otherwise the old base version
	 * 
	 * @throws InvalidVersionSpecException in case the given {@link IBranchVersionSpec} could not be resolved
	 * @throws BaseVersionOutdatedException in case the local working copy is outdated
	 * @throws EMFStoreException in case any other error occurs during commit
	 */
	IPrimaryVersionSpec commitToBranch(IBranchVersionSpec branch, ILogMessage logMessage, ICommitCallback callback,
		IProgressMonitor monitor) throws InvalidVersionSpecException, BaseVersionOutdatedException, EMFStoreException;

	/**
	 * <p>
	 * Updates the project to the head version from the server.
	 * </p>
	 * <p>
	 * If the project has not been shared yet, a {@link RuntimeException} will be thrown.
	 * </p>
	 * 
	 * @return the new base version
	 * 
	 * @throws ChangeConflictException in case a conflict is detected on update
	 * @throws EMFStoreException in case update fails for any other reason
	 */
	IPrimaryVersionSpec update() throws ChangeConflictException, EMFStoreException;

	/**
	 * <p>
	 * Updates the project to the given version from the server.
	 * </p>
	 * <p>
	 * If the project has not been shared yet, a {@link RuntimeException} will be thrown.
	 * </p>
	 * 
	 * @param version
	 *            the version to update to
	 * @return the new base version
	 * 
	 * @throws ChangeConflictException in case a conflict is detected on update
	 * @throws EMFStoreException in case update fails for any other reason
	 */
	IPrimaryVersionSpec update(IVersionSpec version) throws ChangeConflictException, EMFStoreException;

	/**
	 * <p>
	 * Updates the project to the given version from the server.
	 * </p>
	 * <p>
	 * If the project has not been shared yet, a {@link RuntimeException} will be thrown.
	 * </p>
	 * 
	 * @param version
	 *            the {@link IVersionSpec} to update to
	 * @param callback
	 *            the {@link IUpdateCallback} that will be called while the update is performing
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while updating
	 * @return the new base version
	 * 
	 * @throws ChangeConflictException in case a conflict is detected on update
	 * @throws EMFStoreException in case update fails for any other reason
	 */
	IPrimaryVersionSpec update(IVersionSpec version, IUpdateCallback callback, IProgressMonitor monitor)
		throws ChangeConflictException, EMFStoreException;

	/**
	 * Performs a merge in case of a conflict.
	 * 
	 * @param target
	 *            the {@link IPrimaryVersionSpec} which is supposed to be merged
	 * @param changeConflict
	 *            the {@link IChangeConflict} containing the conflicting changes
	 * @param conflictResolver
	 *            a {@link IConflictResolver} for resolving conflicts
	 * @param callback
	 *            the {@link IUpdateCallback} that will be called while the update is performing
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while merging the branch
	 * 
	 * @return true, if the merge was successful, false otherwise
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs while merging the branch
	 */
	boolean merge(IPrimaryVersionSpec target, IChangeConflict changeConflict, IConflictResolver conflictResolver,
		IUpdateCallback callback, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Allows to merge a version from another branch into the current project.
	 * 
	 * @param branchSpec
	 *            the {@link IPrimaryVersionSpec} which is supposed to be merged
	 * @param conflictResolver
	 *            a {@link IConflictResolver} for resolving conflicts in case any conflicts occur
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while merging the branch
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs while merging the branch
	 */
	void mergeBranch(IPrimaryVersionSpec branchSpec, IConflictResolver conflictResolver, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * <p>
	 * Shares this project.
	 * </p>
	 * <p>
	 * The {@link IUsersession} used to share the project will be injected by the framework.
	 * </p>
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while sharing the project
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs while sharing the project
	 */
	void shareProject(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Shares this project.
	 * 
	 * @param session
	 *            the {@link IUsersession} that should be used for sharing the project
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while sharing the project
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs while sharing the project
	 */
	void shareProject(IUsersession session, IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Whether this project has been shared.
	 * 
	 * @return true, if the project space has been shared, false otherwise
	 */
	boolean isShared();

	/**
	 * Returns the {@link IUsersession} associated with this project, if any.
	 * 
	 * @return the user session associated with this project, or {@code null}, if no such usersession is available
	 */
	IUsersession getUsersession();

	/**
	 * Returns the base version of the project.
	 * 
	 * @return the base version of the project
	 */
	IPrimaryVersionSpec getBaseVersion();

	/**
	 * Returns the {@link Date} when the project was updated the last time.
	 * 
	 * @return the date of the project updated
	 */
	Date getLastUpdated();

	/**
	 * Returns a list with copies of the most recent log messages.
	 * 
	 * @return a list with copies of the most recent log messages
	 */
	List<String> getRecentLogMessages();

	/**
	 * Undo the last operation of the project.
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
	 * Determines whether the project has unsaved changes.
	 * 
	 * @return true, if there are unsaved changes, false otherwise
	 */
	boolean hasUnsavedChanges();

	/**
	 * Determines whether the project has uncommitted changes.
	 * 
	 * @return true, if there are any uncommitted changes, false otherwise
	 */
	boolean hasUncommitedChanges();

	/**
	 * Returns the remote project on a server this local project is associated with.
	 * 
	 * @return the remote project this project is associated with
	 * 
	 * @throws EMFStoreException in case any error occurs while retrieving the remote project
	 */
	IRemoteProject getRemoteProject() throws EMFStoreException;

	/**
	 * Imports and applies changes on this project.
	 * 
	 * @param fileName
	 *            the absolute path to the file containing the changes to be imported
	 * 
	 * @throws IOException in case importing the changes fails
	 */
	// TODO: OTS
	void importLocalChanges(String fileName) throws IOException;
}
