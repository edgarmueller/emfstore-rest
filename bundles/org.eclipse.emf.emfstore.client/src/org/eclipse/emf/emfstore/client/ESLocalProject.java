/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller, Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.exceptions.ESProjectNotSharedException;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.ESLocalProjectId;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Represents a project in the local workspace that was checked out from a server or created locally.
 * 
 * @author emueller
 * @author wesendon
 * @author mkoegel
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESLocalProject extends ESProject, ESObjectContainer<ESModelElementId> {

	/**
	 * <p>
	 * Commits any local changes.
	 * </p>
	 * <p>
	 * <b>NOTE</b>: The commit will be executed in headless mode, so no callback can be specified. If clients would like
	 * to influence the commit behavior, they should use
	 * {@link #commit(ESLogMessage, ESCommitCallback, IProgressMonitor)}.
	 * </p>
	 * 
	 * @param monitor a progress monitor to track the progress of the commit
	 * 
	 * @return the new base version, if the commit was successful, otherwise the old base version
	 * 
	 * @throws ESProjectNotSharedException if the project hasn't been shared yet
	 * @throws ESException in case any error occurs during commit
	 */
	ESPrimaryVersionSpec commit(IProgressMonitor monitor) throws ESException;

	/**
	 * <p>
	 * Commits any local changes.
	 * </p>
	 * <p>
	 * <b>NOTE</b>: If no callback is specified the return value will be used to indicate whether the commit was
	 * successful or not. This enables the the headless execution of the commit.
	 * 
	 * @param logMessage
	 *            a message describing the changes being committed
	 * @param callback
	 *            an optional {@link ESCommitCallback}
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to track the progress of the commit
	 * 
	 * @return the new base version, if the commit was successful, otherwise the old base version
	 * 
	 * @throws ESProjectNotSharedException if the project hasn't been shared yet
	 * @throws BaseVersionOutdatedException in case the local working copy is outdated
	 * @throws ESException in case any other error occurs during commit
	 */
	ESPrimaryVersionSpec commit(String logMessage, ESCommitCallback callback, IProgressMonitor monitor)
		throws ESUpdateRequiredException, ESException;

	/**
	 * <p>
	 * Commits any local changes to a branch.
	 * </p>
	 * <p>
	 * <b>NOTE</b>: If no callback is specified the return value will be used to indicate whether the commit was
	 * successful or not. This enables the the headless execution of the commit.
	 * 
	 * @param branch
	 *            the {@link ESBranchVersionSpec} indicating the branch to commit to
	 * @param logMessage
	 *            a message describing the changes being committed
	 * @param callback
	 *            a optional {@link ESCommitCallback}
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to track the progress of the commit
	 * 
	 * @return the new base version, if the commit was successful, otherwise the old base version
	 * 
	 * @throws ESProjectNotSharedException if the project hasn't been shared yet
	 * @throws InvalidVersionSpecException in case the given {@link ESBranchVersionSpec} could not be resolved
	 * @throws ESUpdateRequiredException in case the local working copy is outdated
	 * @throws ESException in case any other error occurs during commit
	 */
	ESPrimaryVersionSpec commitToBranch(ESBranchVersionSpec branch, String logMessage, ESCommitCallback callback,
		IProgressMonitor monitor) throws InvalidVersionSpecException, ESUpdateRequiredException, ESException;

	/**
	 * <p>
	 * Updates the project to the head version from the server.
	 * </p>
	 * 
	 * @param monitor
	 *            a progress monitor to track progress
	 * 
	 * @return the new base version
	 * 
	 * @throws ESProjectNotSharedException if the project hasn't been shared yet
	 * @throws ChangeConflictException in case a conflict is detected on update
	 * @throws ESException in case update fails for any other reason
	 */
	ESPrimaryVersionSpec update(IProgressMonitor monitor) throws ChangeConflictException, ESException;

	/**
	 * <p>
	 * Updates the project to the given version from the server.
	 * </p>
	 * 
	 * @param version
	 *            the {@link ESVersionSpec} to update to
	 * @param callback
	 *            the {@link ESUpdateCallback} that will be called while the update is performing
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to track the progress of the update
	 * @return the new base version
	 * 
	 * @throws ESProjectNotSharedException if the project hasn't been shared yet
	 * @throws ChangeConflictException in case a conflict is detected on update
	 * @throws ESException in case update fails for any other reason
	 */
	ESPrimaryVersionSpec update(ESVersionSpec version, ESUpdateCallback callback, IProgressMonitor monitor)
		throws ChangeConflictException, ESException;

	/**
	 * Adds this project to the workspace.
	 * 
	 * @param progressMonitor a {@link IProgressMonitor} to track the progress while
	 *            adding the project to the workspace
	 */
	void addToWorkspace(final IProgressMonitor progressMonitor);

	/**
	 * <p>
	 * Shares this project.
	 * </p>
	 * <p>
	 * The {@link ESUsersession} used to share the project will be injected by the framework.
	 * </p>
	 * 
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while sharing the project
	 * 
	 * @throws ESException
	 *             in case an error occurs while sharing the project
	 */
	void shareProject(IProgressMonitor monitor) throws ESException;

	/**
	 * Shares this project.
	 * 
	 * @param session
	 *            the {@link ESUsersession} that should be used for sharing the project
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that is used to indicate progress while sharing the project
	 * 
	 * @return the corresponding remote project of a successful share
	 * @throws ESException
	 *             in case an error occurs while sharing the project
	 */
	ESRemoteProject shareProject(ESUsersession session, IProgressMonitor monitor) throws ESException;

	/**
	 * Whether this project has been shared.
	 * 
	 * @return {@code true}, if the project space has been shared, {@code false} otherwise
	 */
	boolean isShared();

	/**
	 * Returns the {@link ESUsersession} associated with this project, if any.
	 * 
	 * @return the user session associated with this project, or {@code null}, if no such session is available
	 */
	ESUsersession getUsersession();

	/**
	 * Returns the base version of the project.
	 * 
	 * @return the base version of the project
	 */
	ESPrimaryVersionSpec getBaseVersion();

	/**
	 * Returns the {@link Date} when the project was updated the last time.
	 * 
	 * @return the date of the last update
	 */
	Date getLastUpdated();

	/**
	 * Returns a list with copies of the most recent log messages.
	 * 
	 * @return a list with copies of the most recent log messages
	 */
	List<String> getRecentLogMessages();

	/**
	 * Undoes the last operation of the project.
	 */
	void undoLastOperation();

	/**
	 * Undo the last operation <em>n</em> operations of the project.
	 * 
	 * @param operations
	 *            the number of operations to be undone
	 * 
	 */
	void undoLastOperations(int operations);

	/**
	 * Determines whether the project is up to date, that is, whether the base
	 * revision and the head revision are equal.
	 * 
	 * @return {@code true}, if the project is up to date, {@code false} otherwise
	 * 
	 * @throws ESException
	 *             if the head revision can not be resolved
	 */
	boolean isUpdated() throws ESException;

	/**
	 * Revert all local changes in the project space. Returns the state of the
	 * project to that of the project space base version.
	 */
	void revert();

	/**
	 * Saves the project space.
	 */
	void save();

	/**
	 * Determines whether the project has unsaved changes.
	 * 
	 * @return {@code true}, if there are unsaved changes, {@code false} otherwise
	 */
	boolean hasUnsavedChanges();

	/**
	 * Determines whether the project has uncommitted changes.
	 * 
	 * @return {@code true}, if there are any uncommitted changes, {@code false} otherwise
	 */
	boolean hasUncommitedChanges();

	/**
	 * Returns the {@link ESRemoteProject} on a server this local project is associated with.
	 * 
	 * @return the remote project this project is associated with
	 * 
	 * @throws ESException in case any error occurs while retrieving the remote project
	 */
	ESRemoteProject getRemoteProject() throws ESException;

	/**
	 * Returns a locally unique ID for the project. It is only unique within the same workspace and it is not the same
	 * for different checkouts of the same {@link ESRemoteProject}. It is intended to identify a local copy of a remote
	 * project.
	 * 
	 * @return the ID of the project
	 */
	ESLocalProjectId getLocalProjectId();
}
