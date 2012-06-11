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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithReturnValue;
import org.eclipse.emf.emfstore.client.ui.dialogs.CommitDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-dependent commit controller for committing pending changes on a {@link ProjectSpace}.<br/>
 * The controller presents the user a dialog will all changes made before he is able to confirm the commit.
 * If no changes have been made by the user a information dialog is presented that states that there are no
 * pending changes to be committed.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 */
public class UICommitProjectController extends AbstractEMFStoreUIController<PrimaryVersionSpec> implements
	CommitCallback {

	private final ProjectSpace projectSpace;
	private LogMessage logMessage;
	private int dialogReturnValue;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent shell that will be used during commit
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the pending changes that should get committed
	 */
	public UICommitProjectController(Shell shell, ProjectSpace projectSpace) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#noLocalChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public void noLocalChanges(ProjectSpace projectSpace) {
		MessageDialog.openInformation(shell, null, "No local changes in your project. No need to commit.");
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#baseVersionOutOfDate(org.eclipse.emf.emfstore.client.model.ProjectSpace)
	 */
	public boolean baseVersionOutOfDate(ProjectSpace projectSpace) {

		final String message = "Your project is outdated, you need to update before commit. Do you want to update now?";

		boolean shouldUpdate = MessageDialog.openConfirm(shell, "Confirmation", message);

		if (shouldUpdate) {
			try {
				new UIUpdateProjectController(getShell(), projectSpace).execute();
			} catch (EmfStoreException e) {
				handleException(e);
			}
		}

		return shouldUpdate;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback#inspectChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.server.model.versioning.ChangePackage)
	 */
	public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {

		if (changePackage.getOperations().isEmpty()) {
			// Display.getDefault().syncExec(new Runnable() {
			// public void run() {
			MessageDialog.openInformation(getShell(), "No local changes",
				"Your local changes were mutually exclusive.\n" + "There are no changes pending for commit.");
			// }
			// });
			return false;
		}

		final CommitDialog commitDialog = new CommitDialog(getShell(), changePackage, projectSpace);

		// Display.getDefault().syncExec(new Runnable() {
		// public void run() {
		dialogReturnValue = commitDialog.open();
		// }
		// });

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

		setException(null);

		PrimaryVersionSpec version = new RunInUIThreadWithReturnValue<PrimaryVersionSpec>(getShell()) {
			@Override
			public PrimaryVersionSpec run(Shell shell) {
				try {
					return projectSpace.commit(logMessage, UICommitProjectController.this, progressMonitor);
				} catch (EmfStoreException e) {
					setException(e);
				}

				return null;
			}
		}.execute();

		if (getException() != null) {
			throw getException();
		}

		return version;
	}
}
