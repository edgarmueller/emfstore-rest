package org.eclipse.emf.emfstore.client.api;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.IConflictResolver;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.common.model.EObjectContainer;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.api.ILogMessage;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public interface ILocalProject extends IProject, EObjectContainer {

	/**
	 * Commits all pending changes of the project space.
	 * 
	 * @throws EMFStoreException
	 *             in case the commit went wrong
	 * 
	 * @return the current version spec
	 **/
	IPrimaryVersionSpec commit() throws EMFStoreException;

	/**
	 * Commits all pending changes of the project space.
	 * 
	 * @param logMessage
	 *            a log message describing the changes to be committed
	 * @param callback
	 *            an optional callback method to be performed while the commit
	 *            is in progress, may be <code>null</code>
	 * @param monitor
	 *            an optional progress monitor to be used while the commit is in
	 *            progress, may be <code>null</code>
	 * 
	 * @return the current version spec
	 * 
	 * @throws EMFStoreException
	 *             in case the commit went wrong
	 * 
	 * @generated NOT
	 */
	IPrimaryVersionSpec commit(ILogMessage logMessage, ICommitCallback callback, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * This method allows to commit changes to a new branch. It works very
	 * similar to {@link #commit()} with the addition of a Branch specifier.
	 * Once the branch is created use {@link #commit()} for further commits.
	 * 
	 * 
	 * @param branch
	 *            branch specifier
	 * @param logMessage
	 *            optional logmessage
	 * @param callback
	 *            optional callback, passing an implementation is recommended
	 * @param monitor
	 *            optional progress monitor
	 * @return the created version
	 * @throws EMFStoreException
	 *             in case of an exception
	 */
	IPrimaryVersionSpec commitToBranch(IBranchVersionSpec branch, ILogMessage logMessage, ICommitCallback callback,
		IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * <!-- begin-user-doc --> Update the project to the head version.
	 * 
	 * @return the new base version
	 * @throws EMFStoreException
	 *             if update fails <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	IPrimaryVersionSpec update() throws EMFStoreException;

	/**
	 * <!-- begin-user-doc --> Update the project to the given version.
	 * 
	 * @param version
	 *            the version to update to
	 * @return the new base version
	 * @throws EMFStoreException
	 *             if update fails <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	IPrimaryVersionSpec update(IVersionSpec version) throws EMFStoreException;

	/**
	 * Update the workspace to the given revision.
	 * 
	 * @param version
	 *            the {@link VersionSpec} to update to
	 * @param callback
	 *            the {@link IUpdateCallback} that will be called when the update
	 *            has been performed
	 * @param progress
	 *            an {@link IProgressMonitor} instance
	 * @return the current version spec
	 * 
	 * @throws EMFStoreException
	 *             in case the update went wrong
	 * @see IUpdateCallback#updateCompleted(ProjectSpace, PrimaryVersionSpec, PrimaryVersionSpec)
	 * @generated NOT
	 */
	IPrimaryVersionSpec update(IVersionSpec version, IUpdateCallback callback, IProgressMonitor progress)
		throws EMFStoreException;

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

	EList<String> getOldLogMessages();

	/**
	 * Return the list of operations that have already been performed on the
	 * project space.
	 * 
	 * @return a list of operations
	 * @generated NOT
	 */
	List<AbstractOperation> getOperations();

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
