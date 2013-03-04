/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.controller;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.exceptions.ESProjectNotSharedException;
import org.eclipse.emf.emfstore.client.observer.ESCommitObserver;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.BasicModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * The controller responsible for performing a commit.
 * 
 * @author wesendon
 */
public class CommitController extends ServerCall<PrimaryVersionSpec> {

	private LogMessage logMessage;
	private ESCommitCallback callback;
	private BranchVersionSpec branch;

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
	public CommitController(ProjectSpaceBase projectSpace, LogMessage logMessage, ESCommitCallback callback,
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
	public CommitController(ProjectSpaceBase projectSpace, BranchVersionSpec branch, LogMessage logMessage,
		ESCommitCallback callback, IProgressMonitor monitor) {
		super(projectSpace);
		this.branch = branch;
		this.logMessage = (logMessage == null) ? createLogMessage() : logMessage;
		this.callback = callback == null ? ESCommitCallback.NOCALLBACK : callback;
		setProgressMonitor(monitor);
	}

	@Override
	protected PrimaryVersionSpec run() throws ESException {
		return commit(this.logMessage, this.branch);
	}

	private PrimaryVersionSpec commit(LogMessage logMessage, final BranchVersionSpec branch)
		throws InvalidVersionSpecException, BaseVersionOutdatedException, ESException {

		if (!getProjectSpace().isShared()) {
			throw new ESProjectNotSharedException();
		}

		getProgressMonitor().beginTask("Commiting changes", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Checking changes");

		// check if there are any changes. Branch commits are allowed with no changes, whereas normal commits are not.
		if (!getProjectSpace().isDirty() && branch == null) {
			callback.noLocalChanges(getProjectSpace().getAPIImpl());
			return getProjectSpace().getBaseVersion();
		}

		getProjectSpace().cleanCutElements();

		getProgressMonitor().subTask("Resolving new version");

		checkForCommitPreconditions(branch, getProgressMonitor());

		getProgressMonitor().worked(10);
		getProgressMonitor().subTask("Gathering changes");

		final ChangePackage changePackage = getProjectSpace().getLocalChangePackage();
		changePackage.setLogMessage(logMessage);

		ESWorkspaceProviderImpl.getObserverBus().notify(ESCommitObserver.class)
			.inspectChanges(getProjectSpace().getAPIImpl(), changePackage.getAPIImpl(), getProgressMonitor());

		BasicModelElementIdToEObjectMapping idToEObjectMapping = new BasicModelElementIdToEObjectMapping(
			getProjectSpace().getProject(), changePackage);

		getProgressMonitor().subTask("Presenting Changes");
		if (!callback.inspectChanges(getProjectSpace().getAPIImpl(), changePackage.getAPIImpl(), idToEObjectMapping)
			|| getProgressMonitor().isCanceled()) {
			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Sending files to server");
		// TODO reimplement with ObserverBus and think about subtasks for commit
		getProjectSpace().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());
		getProgressMonitor().worked(30);

		getProgressMonitor().subTask("Sending changes to server");

		// check again if an update is required
		boolean updatePerformed = checkForCommitPreconditions(branch, getProgressMonitor());
		// present changes again if update was performed
		if (updatePerformed) {
			getProgressMonitor().subTask("Presenting Changes");
			if (!callback
				.inspectChanges(getProjectSpace().getAPIImpl(), changePackage.getAPIImpl(), idToEObjectMapping)
				|| getProgressMonitor().isCanceled()) {
				return getProjectSpace().getBaseVersion();
			}
		}

		// Branching case: branch specifier added
		PrimaryVersionSpec newBaseVersion;
		newBaseVersion = new UnknownEMFStoreWorkloadCommand<PrimaryVersionSpec>(getProgressMonitor()) {
			@Override
			public PrimaryVersionSpec run(IProgressMonitor monitor) throws ESException {
				return getConnectionManager().createVersion(getUsersession().getSessionId(),
					getProjectSpace().getProjectId(), getProjectSpace().getBaseVersion(), changePackage, branch,
					getProjectSpace().getMergedVersion(), changePackage.getLogMessage());
			}
		}.execute();

		// TODO reimplement with ObserverBus and think about subtasks for commit
		getProgressMonitor().worked(35);
		getProgressMonitor().subTask("Sending files to server");

		getProjectSpace().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());

		getProgressMonitor().worked(30);
		getProgressMonitor().subTask("Computing checksum");

		boolean validChecksum = true;
		try {
			validChecksum = performChecksumCheck(newBaseVersion, getProjectSpace().getProject());
		} catch (SerializationException exception) {
			WorkspaceUtil.logWarning(MessageFormat.format("Checksum computation for project {0} failed.",
				getProjectSpace().getProjectName()), exception);
		}

		if (!validChecksum) {
			getProgressMonitor().subTask("Invalid checksum.  Activating checksum error handler.");
			boolean errorHandled = callback
				.checksumCheckFailed(getProjectSpace().getAPIImpl(), newBaseVersion.getAPIImpl(), getProgressMonitor());
			if (!errorHandled) {
				throw new ESException("Commit cancelled by checksum error handler due to invalid checksum.");
			}
		}

		getProgressMonitor().subTask("Finalizing commit");

		getProjectSpace().setBaseVersion(newBaseVersion);
		getProjectSpace().getOperations().clear();
		getProjectSpace().setMergedVersion(null);
		getProjectSpace().updateDirtyState();

		ESWorkspaceProviderImpl.getObserverBus().notify(ESCommitObserver.class)
			.commitCompleted(getProjectSpace().getAPIImpl(), newBaseVersion.getAPIImpl(), getProgressMonitor());

		return newBaseVersion;
	}

	private boolean performChecksumCheck(PrimaryVersionSpec newBaseVersion, Project project)
		throws SerializationException {

		if (Configuration.getClientBehavior().isChecksumCheckActive()) {
			long computedChecksum = ModelUtil.computeChecksum(project);
			return computedChecksum == newBaseVersion.getProjectStateChecksum();
		}

		return true;
	}

	private boolean checkForCommitPreconditions(final BranchVersionSpec branch, IProgressMonitor monitor)
		throws InvalidVersionSpecException,
		ESException, BaseVersionOutdatedException {
		if (branch != null) {
			// check branch conditions
			if (StringUtils.isEmpty(branch.getBranch())) {
				throw new InvalidVersionSpecException("Empty branch name is not permitted.");
			}
			PrimaryVersionSpec potentialBranch = null;
			try {
				potentialBranch = getProjectSpace().resolveVersionSpec(branch, monitor);
			} catch (InvalidVersionSpecException e) {
				// branch doesn't exist, create.
			}
			if (potentialBranch != null) {
				throw new InvalidVersionSpecException("Branch already exists. You need to merge.");
			}

		} else {
			// check if we need to update first
			PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(
				Versions.createHEAD(getProjectSpace().getBaseVersion()), monitor);
			if (!getProjectSpace().getBaseVersion().equals(resolvedVersion)) {
				if (!callback.baseVersionOutOfDate(getProjectSpace().getAPIImpl(), getProgressMonitor())) {
					throw new BaseVersionOutdatedException();
				}
				return true;
			}
		}
		return false;
	}

	private LogMessage createLogMessage() {
		LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		String commiter = "UNKOWN";
		if (getProjectSpace().getUsersession() != null && getProjectSpace().getUsersession().getACUser() != null
			&& getProjectSpace().getUsersession().getACUser().getName() != null) {
			commiter = getProjectSpace().getUsersession().getACUser().getName();
		}
		logMessage.setAuthor(commiter);
		logMessage.setClientDate(new Date());
		logMessage.setMessage("");
		return logMessage;
	}
}