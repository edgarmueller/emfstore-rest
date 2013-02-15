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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.model.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.CancelOperationException;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.CommitDialog;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessageFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UIController for branch creation. Slightly modified copy of the commit
 * controller
 * 
 * @author wesendon
 * 
 */
public class UICreateBranchController extends AbstractEMFStoreUIController<ESPrimaryVersionSpec> implements
	ICommitCallback {

	private final ProjectSpace projectSpace;
	private LogMessage logMessage;
	private int dialogReturnValue;
	private BranchVersionSpec branch;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the
	 *            creation of the branch
	 * @param projectSpace
	 *            the project space for which to create a branch for
	 */
	public UICreateBranchController(Shell shell, ProjectSpace projectSpace) {
		this(shell, projectSpace, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the
	 *            creation of the branch
	 * @param projectSpace
	 *            the project space for which to create a branch for
	 * @param branch
	 *            the branch to be committed
	 */
	public UICreateBranchController(Shell shell, ProjectSpace projectSpace, BranchVersionSpec branch) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		this.branch = branch;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback#noLocalChanges(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public void noLocalChanges(ESLocalProject projectSpace) {
		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				MessageDialog.openInformation(getShell(), null, "No local changes in your project. No need to commit.");
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback#baseVersionOutOfDate(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public boolean baseVersionOutOfDate(final ESLocalProject projectSpace, final IProgressMonitor progressMonitor) {

		final String message = "Your project is outdated, you need to update before branching. Do you want to update now?";
		return RunInUI.runWithResult(new Callable<Boolean>() {

			public Boolean call() throws Exception {
				boolean shouldUpdate = MessageDialog.openConfirm(getShell(), "Confirmation", message);
				if (shouldUpdate) {
					ESPrimaryVersionSpec baseVersion = UICreateBranchController.this.projectSpace.getBaseVersion();
					ESPrimaryVersionSpec version = new UIUpdateProjectController(getShell(), (ProjectSpace) projectSpace)
						.executeSub(progressMonitor);
					if (version.equals(baseVersion)) {
						return false;
					}

				}
				return shouldUpdate;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback#inspectChanges(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage)
	 */
	public boolean inspectChanges(ESLocalProject projectSpace, ESChangePackage changePackage,
		IModelElementIdToEObjectMapping idToEObjectMapping) {

		final CommitDialog commitDialog = new CommitDialog(getShell(), (ChangePackage) changePackage,
			(ProjectSpace) projectSpace, idToEObjectMapping);

		dialogReturnValue = RunInUI.runWithResult(new Callable<Integer>() {
			public Integer call() throws Exception {
				return commitDialog.open();
			}
		});

		if (dialogReturnValue == Dialog.OK) {
			changePackage.setLogMessage(
				LogMessageFactory.INSTANCE.createLogMessage(commitDialog.getLogText(),
					projectSpace.getUsersession().getUsername()));
			return true;
		}

		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public PrimaryVersionSpec doRun(final IProgressMonitor progressMonitor) throws ESException {
		try {
			if (branch == null) {
				branch = branchSelection(projectSpace);
			}
			// TODO OTS
			return (PrimaryVersionSpec) projectSpace.commitToBranch(branch, logMessage, UICreateBranchController.this,
				progressMonitor);
		} catch (BaseVersionOutdatedException e) {
			// project is out of date and user canceled update
			// ignore
		} catch (final ESException e) {
			if (e instanceof CancelOperationException) {
				return null;
			}
			WorkspaceUtil.logException(e.getMessage(), e);
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					MessageDialog.openError(getShell(), "Create Branch failed",
						"Create Branch failed: " + e.getMessage());
					return null;
				}
			});
		}

		return null;
	}

	private BranchVersionSpec branchSelection(final ProjectSpace projectSpace) throws ESException {
		final List<ESBranchInfo> branches = ((ProjectSpaceBase) projectSpace).getBranches(new NullProgressMonitor());

		@SuppressWarnings("static-access")
		String branch = new RunInUI.WithException().runWithResult(new Callable<String>() {

			public String call() throws Exception {
				BranchSelectionDialog.Creation dialog = new BranchSelectionDialog.Creation(getShell(), projectSpace
					.getBaseVersion(), branches);
				dialog.setBlockOnOpen(true);

				if (dialog.open() != Dialog.OK || dialog.getNewBranch() == null || dialog.getNewBranch().equals("")) {
					throw new CancelOperationException("No Branch specified");
				}
				return dialog.getNewBranch();
			}
		});

		return Versions.createBRANCH(branch);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback#checksumCheckFailed(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor) throws ESException {
		ESChecksumErrorHandler errorHandler = Configuration.getChecksumErrorHandler();
		return errorHandler.execute(projectSpace, versionSpec, monitor);
	}
}