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
package org.eclipse.emf.emfstore.client.model.controller;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.observers.CommitObserver;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.conflictDetection.BasicModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

/**
 * The controller responsible for performing a commit.
 * 
 * @author wesendon
 */
public class CommitController extends ServerCall<PrimaryVersionSpec> {

	private LogMessage logMessage;
	private ICommitCallback callback;
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
	public CommitController(ProjectSpaceBase projectSpace, LogMessage logMessage, ICommitCallback callback,
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
		ICommitCallback callback, IProgressMonitor monitor) {
		super(projectSpace);
		this.branch = branch;
		this.logMessage = (logMessage == null) ? createLogMessage() : logMessage;
		this.callback = callback == null ? ICommitCallback.NOCALLBACK : callback;
		setProgressMonitor(monitor);
	}

	@Override
	protected PrimaryVersionSpec run() throws EmfStoreException {
		return commit(this.logMessage, this.branch);
	}

	private PrimaryVersionSpec commit(LogMessage logMessage, final BranchVersionSpec branch) throws EmfStoreException {
		getProgressMonitor().beginTask("Commiting Changes", 100);
		getProgressMonitor().worked(1);

		getProgressMonitor().subTask("Checking changes");

		// check if there are any changes. Branch commits are allowed with no changes, whereas normal committs are not.
		if (!getLocalProject().hasUncommitedChanges() && branch == null) {
			callback.noLocalChanges(getLocalProject());
			return getLocalProject().getBaseVersion();
		}
		getLocalProject().cleanCutElements();

		getProgressMonitor().subTask("Resolving new version");

		checkForCommitPreconditions(branch);

		getProgressMonitor().worked(10);
		getProgressMonitor().subTask("Gathering changes");
		final ChangePackage changePackage = getLocalProject().getLocalChangePackage();
		changePackage.setLogMessage(logMessage);

		WorkspaceProvider.getObserverBus().notify(CommitObserver.class)
			.inspectChanges(getLocalProject(), changePackage, getProgressMonitor());

		BasicModelElementIdToEObjectMapping idToEObjectMapping = new BasicModelElementIdToEObjectMapping(
			getLocalProject().getProject(), changePackage);

		getProgressMonitor().subTask("Presenting Changes");
		if (!callback.inspectChanges(getLocalProject(), changePackage, idToEObjectMapping)
			|| getProgressMonitor().isCanceled()) {
			return getLocalProject().getBaseVersion();
		}

		getProgressMonitor().subTask("Sending files to server");
		// TODO reimplement with ObserverBus and think about subtasks for commit
		getLocalProject().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());
		getProgressMonitor().worked(30);

		getProgressMonitor().subTask("Sending changes to server");

		// check again if an update is required
		boolean updatePerformed = checkForCommitPreconditions(branch);
		// present changes again if update was performed
		if (updatePerformed) {
			getProgressMonitor().subTask("Presenting Changes");
			if (!callback.inspectChanges(getLocalProject(), changePackage, idToEObjectMapping)
				|| getProgressMonitor().isCanceled()) {
				return getLocalProject().getBaseVersion();
			}
		}

		// Branching case: branch specifier added
		PrimaryVersionSpec newBaseVersion;
		newBaseVersion = new UnknownEMFStoreWorkloadCommand<PrimaryVersionSpec>(getProgressMonitor()) {
			@Override
			public PrimaryVersionSpec run(IProgressMonitor monitor) throws EmfStoreException {
				return getConnectionManager().createVersion(getUsersession().getSessionId(),
					getLocalProject().getProjectId(), getLocalProject().getBaseVersion(), changePackage, branch,
					getLocalProject().getMergedVersion(), changePackage.getLogMessage());
			}
		}.execute();
		getProgressMonitor().worked(35);

		// TODO reimplement with ObserverBus and think about subtasks for commit
		getProgressMonitor().subTask("Sending files to server");
		getLocalProject().getFileTransferManager().uploadQueuedFiles(getProgressMonitor());
		getProgressMonitor().worked(30);

		getProgressMonitor().subTask("Computing checksum");

		boolean validChecksum = true;
		try {
			validChecksum = performChecksumCheck(newBaseVersion, getLocalProject().getProject());
		} catch (SerializationException exception) {
			WorkspaceUtil.logWarning(MessageFormat.format("Checksum computation for project {0} failed.",
				getLocalProject().getProjectName()), exception);
		}

		if (!validChecksum) {
			getProgressMonitor().subTask("Invalid checksum.  Activating checksum error handler.");
			boolean errorHandled = callback
				.checksumCheckFailed(getLocalProject(), newBaseVersion, getProgressMonitor());
			if (!errorHandled) {
				throw new EmfStoreException("Commit cancelled by checksum error handler due to invalid checksum.");
			}
		}

		getProgressMonitor().subTask("Finalizing commit");

		getLocalProject().setBaseVersion(newBaseVersion);
		getLocalProject().getOperations().clear();
		getLocalProject().setMergedVersion(null);
		getLocalProject().updateDirtyState();

		WorkspaceProvider.getObserverBus().notify(CommitObserver.class)
			.commitCompleted(getLocalProject(), newBaseVersion, getProgressMonitor());

		return newBaseVersion;
	}

	private boolean performChecksumCheck(PrimaryVersionSpec newBaseVersion, Project project)
		throws SerializationException {

		if (Configuration.isChecksumCheckActive()) {
			long computedChecksum = ModelUtil.computeChecksum(project);
			return computedChecksum == newBaseVersion.getProjectStateChecksum();
		}

		return true;
	}

	private boolean checkForCommitPreconditions(final BranchVersionSpec branch) throws InvalidVersionSpecException,
		EmfStoreException, BaseVersionOutdatedException {
		if (branch != null) {
			// check branch conditions
			if (StringUtils.isEmpty(branch.getBranch())) {
				throw new InvalidVersionSpecException("Empty branch name is not permitted.");
			}
			PrimaryVersionSpec potentialBranch = null;
			try {
				potentialBranch = getLocalProject().resolveVersionSpec(branch);
			} catch (InvalidVersionSpecException e) {
				// branch doesn't exist, create.
			}
			if (potentialBranch != null) {
				throw new InvalidVersionSpecException("Branch already exists. You need to merge.");
			}

		} else {
			// check if we need to update first
			PrimaryVersionSpec resolvedVersion = getLocalProject().resolveVersionSpec(
				Versions.createHEAD(getLocalProject().getBaseVersion()));
			if (!getLocalProject().getBaseVersion().equals(resolvedVersion)) {
				if (!callback.baseVersionOutOfDate(getLocalProject(), getProgressMonitor())) {
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
		if (getLocalProject().getUsersession() != null && getLocalProject().getUsersession().getACUser() != null
			&& getLocalProject().getUsersession().getACUser().getName() != null) {
			commiter = getLocalProject().getUsersession().getACUser().getName();
		}
		logMessage.setAuthor(commiter);
		logMessage.setClientDate(new Date());
		logMessage.setMessage("");
		return logMessage;
	}
}