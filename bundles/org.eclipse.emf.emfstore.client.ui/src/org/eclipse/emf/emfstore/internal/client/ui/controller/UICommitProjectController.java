/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.CommitDialog;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdToEObjectMappingImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESChangePackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessageFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-dependent commit controller for committing pending changes on a {@link ProjectSpace}.<br/>
 * The controller presents the user a dialog will all changes made before he is
 * able to confirm the commit. If no changes have been made by the user a
 * information dialog is presented that states that there are no pending changes
 * to be committed.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 */
public class UICommitProjectController extends
	AbstractEMFStoreUIController<ESPrimaryVersionSpec> implements
	ESCommitCallback {

	private final ESLocalProject localProject;
	// TODO: is never initialized
	private ESLogMessage logMessage;
	private int dialogReturnValue;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent shell that will be used during commit
	 * @param projectSpace
	 *            the {@link ProjectSpace} that contains the pending changes
	 *            that should get committed
	 */
	public UICommitProjectController(Shell shell, ESLocalProject localProject) {
		super(shell, true, true);
		this.localProject = localProject;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback#noLocalChanges(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public void noLocalChanges(ESLocalProject projectSpace) {
		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				MessageDialog.openInformation(getShell(), null,
												"No local changes in your project. No need to commit.");
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback#baseVersionOutOfDate(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace)
	 */
	public boolean baseVersionOutOfDate(final ESLocalProject projectSpace,
		IProgressMonitor progressMonitor) {

		final String message = "Your project is outdated, you need to update before commit. Do you want to update now?";
		boolean shouldUpdate = RunInUI.runWithResult(new Callable<Boolean>() {

			public Boolean call() throws Exception {
				return MessageDialog.openConfirm(getShell(), "Confirmation",
													message);
			}
		});
		if (shouldUpdate) {
			ESPrimaryVersionSpec baseVersion = UICommitProjectController.this.localProject.getBaseVersion();
			ESPrimaryVersionSpec version = new UIUpdateProjectController(getShell(), projectSpace)
				.executeSub(progressMonitor);
			if (version.equals(baseVersion)) {
				return false;
			}

		}
		return shouldUpdate;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback#inspectChanges(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage)
	 */
	public boolean inspectChanges(
		ESLocalProject localProject,
		ESChangePackage changePackage,
		ESModelElementIdToEObjectMapping idToEObjectMapping) {

		ChangePackage internalChangePackage = ((ESChangePackageImpl) changePackage).getInternalAPIImpl();
		ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).getInternalAPIImpl();

		if (internalChangePackage.getOperations().isEmpty()) {
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					MessageDialog
						.openInformation(
											getShell(),
											"No local changes",
											"No need to commit any more, there are no more changes pending for commit.\n"
												+ "This may have happened because you rejected your changes in favor for changes "
												+ "of other users in a merge.");
					return null;
				}
			});

			return false;
		}

		final CommitDialog commitDialog = new CommitDialog(
			getShell(),
			internalChangePackage,
			projectSpace,
			((ESModelElementIdToEObjectMappingImpl) idToEObjectMapping).getInternalAPIImpl());

		dialogReturnValue = RunInUI.runWithResult(new Callable<Integer>() {
			public Integer call() throws Exception {
				return commitDialog.open();
			}
		});

		if (dialogReturnValue == Dialog.OK) {

			internalChangePackage.setLogMessage(
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
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ESPrimaryVersionSpec doRun(final IProgressMonitor progressMonitor)
		throws ESException {
		try {

			ESPrimaryVersionSpec primaryVersionSpec = localProject.commit(
																			logMessage,
																			UICommitProjectController.this,
																			progressMonitor);
			return primaryVersionSpec;

		} catch (BaseVersionOutdatedException e) {
			// project is out of date and user canceled update
			// ignore
		} catch (final ESException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					MessageDialog.openError(getShell(), "Commit failed",
											e.getMessage());
					return null;
				}
			});
		}

		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback#checksumCheckFailed(org.eclipse.emf.emfstore.internal.client.ESLocalProject.ILocalProject,
	 *      org.eclipse.emf.emfstore.internal.server.model.ESPrimaryVersionSpec.versionspecs.IPrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */

	public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor) throws ESException {
		ESChecksumErrorHandler errorHandler = Configuration.getClientBehavior().getChecksumErrorHandler();
		return errorHandler.execute(projectSpace, versionSpec, monitor);
	}
}