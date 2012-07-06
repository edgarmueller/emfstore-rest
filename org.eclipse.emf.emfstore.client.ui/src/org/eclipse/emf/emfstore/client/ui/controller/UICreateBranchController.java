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
package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.CommitDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author wesendon
 * 
 */
public class UICreateBranchController extends AbstractEMFStoreUIController<PrimaryVersionSpec> implements
	CommitCallback {

	private final ProjectSpace projectSpace;
	private LogMessage logMessage;
	private int dialogReturnValue;
	private BranchVersionSpec branch;

	public UICreateBranchController(Shell shell, ProjectSpace projectSpace) {
		this(shell, projectSpace, null);
	}

	public UICreateBranchController(Shell shell, ProjectSpace projectSpace, BranchVersionSpec branch) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		this.branch = branch;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#noLocalChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public void noLocalChanges(ProjectSpace projectSpace) {
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
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#baseVersionOutOfDate(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public boolean baseVersionOutOfDate(final ProjectSpace projectSpace) {

		final String message = "Your project is outdated, you need to update before branching. Do you want to update now?";
		return RunInUI.runWithResult(new Callable<Boolean>() {

			public Boolean call() throws Exception {
				boolean shouldUpdate = MessageDialog.openConfirm(getShell(), "Confirmation", message);
				if (shouldUpdate) {
					PrimaryVersionSpec baseVersion = UICreateBranchController.this.projectSpace.getBaseVersion();
					PrimaryVersionSpec version = new UIUpdateProjectController(getShell(), projectSpace).execute();
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
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#inspectChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.server.model.versioning.ChangePackage)
	 */
	public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {

		final CommitDialog commitDialog = new CommitDialog(getShell(), changePackage, projectSpace);

		dialogReturnValue = RunInUI.runWithResult(new Callable<Integer>() {
			public Integer call() throws Exception {
				return commitDialog.open();
			}
		});

		if (dialogReturnValue == Dialog.OK) {
			changePackage.getLogMessage().setMessage(commitDialog.getLogText());
			return true;
		}

		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public PrimaryVersionSpec doRun(final IProgressMonitor progressMonitor) throws EmfStoreException {
		try {
			if (branch == null) {
				branch = branchSelection(projectSpace);
			}
			return projectSpace.commitToBranch(branch, logMessage, UICreateBranchController.this, progressMonitor);
		} catch (BaseVersionOutdatedException e) {
			// project is out of date and user canceled update
			// ignore
		} catch (final EmfStoreException e) {
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

	private BranchVersionSpec branchSelection(final ProjectSpace projectSpace) throws EmfStoreException {
		final List<BranchInfo> branches = ((ProjectSpaceBase) projectSpace).getBranches();

		@SuppressWarnings("static-access")
		String branch = new RunInUI.WithException().runWithResult(new Callable<String>() {

			public String call() throws Exception {
				BranchSelectionDialog.Creation dialog = new BranchSelectionDialog.Creation(getShell(), projectSpace
					.getBaseVersion(), branches);
				dialog.setBlockOnOpen(true);

				if (dialog.open() != Dialog.OK || dialog.getNewBranch() == null || dialog.getNewBranch().equals("")) {
					throw new EmfStoreException("No Branch specified");
				}
				return dialog.getNewBranch();
			}
		});

		return Versions.BRANCH(branch);
	}
}
