/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.exceptions.ESProjectNotSharedException;
import org.eclipse.emf.emfstore.client.observer.ESCommitObserver;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ModelElementIdToEObjectMappingImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;

/**
 * The controller responsible for performing a commit.
 * 
 * @author wesendon
 */
public class CommitController extends ServerCall<PrimaryVersionSpec> {

	private final String logMessage;
	private final ESCommitCallback callback;
	private final BranchVersionSpec branch;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space whose pending changes should be commited
	 * @param logMessage
	 *            a log message documenting the commit
	 * @param callback
	 *            an callback that will be called during and at the end of the
	 *            commit. May be <code>null</code>.
	 * @param monitor
	 *            an {@link IProgressMonitor} that will be used to inform
	 *            clients about the commit progress. May be <code>null</code>.
	 */
	public CommitController(ProjectSpaceBase projectSpace, String logMessage, ESCommitCallback callback,
		IProgressMonitor monitor) {
		this(projectSpace, null, logMessage, callback, monitor);
	}

	/**
	 * Branching Constructor.
	 * 
	 * @param projectSpace
	 *            the project space whose pending changes should be commited
	 * @param branch
	 *            Specification of the branch to which the changes should be
	 *            commited.
	 * @param logMessage
	 *            a log message documenting the commit
	 * @param callback
	 *            an callback that will be called during and at the end of the
	 *            commit. May be <code>null</code>.
	 * @param monitor
	 *            an {@link IProgressMonitor} that will be used to inform
	 *            clients about the commit progress. May be <code>null</code>.
	 */
	public CommitController(ProjectSpaceBase projectSpace, BranchVersionSpec branch, String logMessage,
		ESCommitCallback callback, IProgressMonitor monitor) {
		super(projectSpace);
		this.branch = branch;
		this.logMessage = logMessage == null ? "<NO MESSAGE>" : logMessage;
		this.callback = callback == null ? ESCommitCallback.NOCALLBACK : callback;
		setProgressMonitor(monitor);
	}

	@Override
	protected PrimaryVersionSpec run() throws ESException {
		return commit(logMessage, branch);
	}

	private PrimaryVersionSpec commit(final String logMessage, final BranchVersionSpec branch)
		throws InvalidVersionSpecException, ESUpdateRequiredException, ESException {

		if (!getProjectSpace().isShared()) {
			throw new ESProjectNotSharedException();
		}

		getProgressMonitor().beginTask("Commiting changes", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Checking changes");

		// check if there are any changes. Branch commits are allowed with no changes, whereas normal commits are not.
		if (!getProjectSpace().isDirty() && branch == null) {
			callback.noLocalChanges(getProjectSpace().toAPI());
			return getProjectSpace().getBaseVersion();
		}

		getProjectSpace().cleanCutElements();

		getProgressMonitor().subTask("Resolving new version");

		checkForCommitPreconditions(branch, getProgressMonitor());

		getProgressMonitor().worked(10);
		getProgressMonitor().subTask("Gathering changes");

		final ChangePackage changePackage = getProjectSpace().getLocalChangePackage();

		setLogMessage(logMessage, changePackage);

		ESWorkspaceProviderImpl.getObserverBus().notify(ESCommitObserver.class)
			.inspectChanges(getProjectSpace().toAPI(), changePackage.toAPI(), getProgressMonitor());

		final ModelElementIdToEObjectMappingImpl idToEObjectMapping = new ModelElementIdToEObjectMappingImpl(
			getProjectSpace().getProject(), changePackage);

		getProgressMonitor().subTask("Presenting Changes");
		if (!callback.inspectChanges(getProjectSpace().toAPI(),
			changePackage.toAPI(),
			idToEObjectMapping.toAPI())
			|| getProgressMonitor().isCanceled()) {

			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Sending files to server");
		// TODO reimplement with ObserverBus and think about subtasks for commit
		getProjectSpace().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());
		getProgressMonitor().worked(30);

		getProgressMonitor().subTask("Sending changes to server");

		// check again if an update is required
		final boolean updatePerformed = checkForCommitPreconditions(branch, getProgressMonitor());
		// present changes again if update was performed
		if (updatePerformed) {
			getProgressMonitor().subTask("Presenting Changes");
			if (!callback.inspectChanges(getProjectSpace().toAPI(),
				changePackage.toAPI(),
				idToEObjectMapping.toAPI())
				|| getProgressMonitor().isCanceled()) {
				return getProjectSpace().getBaseVersion();
			}
		}

		final PrimaryVersionSpec newBaseVersion = performCommit(branch, changePackage);

		// TODO reimplement with ObserverBus and think about subtasks for commit
		getProgressMonitor().worked(35);
		getProgressMonitor().subTask("Sending files to server");

		getProjectSpace().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());

		getProgressMonitor().worked(30);
		getProgressMonitor().subTask("Computing checksum");

		handleChecksumProcessing(newBaseVersion);

		getProgressMonitor().subTask("Finalizing commit");

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProjectSpace().setBaseVersion(newBaseVersion);
				getProjectSpace().getOperations().clear();
				getProjectSpace().setMergedVersion(null);
				getProjectSpace().updateDirtyState();
				return null;
			}
		});

		ESWorkspaceProviderImpl.getObserverBus().notify(ESCommitObserver.class)
			.commitCompleted(getProjectSpace().toAPI(), newBaseVersion.toAPI(), getProgressMonitor());

		return newBaseVersion;
	}

	private void handleChecksumProcessing(final PrimaryVersionSpec newBaseVersion) throws ESException {
		boolean validChecksum = true;
		try {
			validChecksum = performChecksumCheck(newBaseVersion, getProjectSpace().getProject());
		} catch (final SerializationException exception) {
			WorkspaceUtil.logWarning(MessageFormat.format("Checksum computation for project {0} failed.",
				getProjectSpace().getProjectName()), exception);
		}

		if (!validChecksum) {
			getProgressMonitor().subTask("Invalid checksum.  Activating checksum error handler.");
			final boolean errorHandled = Configuration.getClientBehavior().getChecksumErrorHandler()
				.execute(getProjectSpace().toAPI(), newBaseVersion.toAPI(), getProgressMonitor());
			if (!errorHandled) {
				throw new ESException("Commit cancelled by checksum error handler due to invalid checksum.");
			}
		}
	}

	private PrimaryVersionSpec performCommit(final BranchVersionSpec branch, final ChangePackage changePackage)
		throws ESException {
		// Branching case: branch specifier added
		final PrimaryVersionSpec newBaseVersion = new UnknownEMFStoreWorkloadCommand<PrimaryVersionSpec>(
			getProgressMonitor()) {
			@Override
			public PrimaryVersionSpec run(IProgressMonitor monitor) throws ESException {
				return getConnectionManager().createVersion(
					getUsersession().getSessionId(),
					getProjectSpace().getProjectId(),
					getProjectSpace().getBaseVersion(),
					changePackage,
					branch,
					getProjectSpace().getMergedVersion(),
					changePackage.getLogMessage());
			}
		}.execute();
		return newBaseVersion;
	}

	private void setLogMessage(final String logMessage, final ChangePackage changePackage) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				final LogMessage logMessageObject = VersioningFactory.eINSTANCE.createLogMessage();
				logMessageObject.setMessage(logMessage);
				logMessageObject.setClientDate(new Date());
				logMessageObject.setAuthor(getProjectSpace().getUsersession().getUsername());
				changePackage.setLogMessage(logMessageObject);
				return null;
			}
		});
	}

	private boolean performChecksumCheck(PrimaryVersionSpec newBaseVersion, Project project)
		throws SerializationException {

		if (Configuration.getClientBehavior().isChecksumCheckActive()) {
			final long computedChecksum = ModelUtil.computeChecksum(project);
			return computedChecksum == newBaseVersion.getProjectStateChecksum();
		}

		return true;
	}

	private boolean checkForCommitPreconditions(final BranchVersionSpec branch, IProgressMonitor monitor)
		throws InvalidVersionSpecException,
		ESException, ESUpdateRequiredException {
		if (branch != null) {
			// check branch conditions
			if (StringUtils.isEmpty(branch.getBranch())) {
				throw new InvalidVersionSpecException("Empty branch name is not permitted.");
			}
			PrimaryVersionSpec potentialBranch = null;
			try {
				potentialBranch = getProjectSpace().resolveVersionSpec(branch, monitor);
			} catch (final InvalidVersionSpecException e) {
				// branch doesn't exist, create.
			}
			if (potentialBranch != null) {
				throw new InvalidVersionSpecException("Branch already exists. You need to merge.");
			}

		} else {
			// check if we need to update first
			final PrimaryVersionSpec resolvedVersion = getProjectSpace()
				.resolveVersionSpec(
					Versions.createHEAD(getProjectSpace().getBaseVersion()), monitor);
			if (!getProjectSpace().getBaseVersion().equals(resolvedVersion)) {
				if (!callback.baseVersionOutOfDate(getProjectSpace().toAPI(), getProgressMonitor())) {
					throw new ESUpdateRequiredException();
				}
				return true;
			}
		}
		return false;
	}
}
