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
package org.eclipse.emf.emfstore.client.ui.commands.handler;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.exceptions.NoChangesOnServerException;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.commands.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Controller class that updates a project.
 * 
 * @author wesendon
 * 
 */
public class UpdateProjectUIController extends AbstractEMFStoreUIController implements UpdateCallback {

	/**
	 * Constructor.
	 * 
	 * @param shell the shell to be used by this controller.
	 */
	public UpdateProjectUIController(Shell shell) {
		this.setShell(shell);
	}

	/**
	 * Updates the given {@link ProjectSpace} to the given version.
	 * 
	 * @param projectSpace the project space to be updated
	 * @param version the revision
	 */
	public void update(ProjectSpace projectSpace, VersionSpec version) {
		projectSpace.update(version, this, getProgressMonitorDialog().getProgressMonitor());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController#handleException(java.lang.Exception)
	 */
	@Override
	public void handleException(Exception exception) {
		if (exception instanceof NoChangesOnServerException) {
			handleNoChangesException();
		} else if (exception instanceof ChangeConflictException) {
			handleChangeConflictException((ChangeConflictException) exception);
		} else {
			super.handleException(exception);
		}
	}

	/**
	 * Handles the case in which the project is already updated.
	 */
	protected void handleNoChangesException() {
		closeProgress();
		MessageDialog.openInformation(getShell(), "No need to update",
			"Your project is up to date, you do not need to update.");
	}

	/**
	 * Handles the case in which overlapping changes occur while updating the project.
	 * 
	 * @param conflictException the {@link ChangeConflictException}
	 */
	protected void handleChangeConflictException(ChangeConflictException conflictException) {
		closeProgress();
		ProjectSpace projectSpace = conflictException.getProjectSpace();
		try {
			PrimaryVersionSpec targetVersion = projectSpace.resolveVersionSpec(VersionSpec.HEAD_VERSION);
			projectSpace.merge(targetVersion, new MergeProjectHandler(conflictException));
		} catch (EmfStoreException e) {
			WorkspaceUtil.logException("Exception when merging the project!", e);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.UpdateCallback#inspectChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      java.util.List)
	 */
	public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changePackages) {
		UpdateDialog updateDialog = new UpdateDialog(getShell(), projectSpace, changePackages);
		if (updateDialog.open() == Window.OK) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.UpdateCallback#updateCompleted(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec)
	 */
	public void updateCompleted(ProjectSpace projectSpace, PrimaryVersionSpec oldVersion, PrimaryVersionSpec newVersion) {
		WorkspaceUtil.logUpdate(projectSpace, oldVersion, newVersion);
		// explicitly refresh the decorator since no simple attribute has
		// been changed (as opposed to committing where the dirty property is being set)
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
					.update("org.eclipse.emf.emfstore.client.ui.decorators.VersionDecorator");
			}
		});
		closeProgress();
	}

}
