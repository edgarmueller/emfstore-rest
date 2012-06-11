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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThread;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithReturnValue;
import org.eclipse.emf.emfstore.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-dependent controller for updating a project.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UIUpdateProjectController extends AbstractEMFStoreUIController<PrimaryVersionSpec> implements
	UpdateCallback {

	private final ProjectSpace projectSpace;
	private VersionSpec version;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param projectSpace
	 *            the {@link ProjectSpace} that should get updated
	 */
	public UIUpdateProjectController(Shell shell, ProjectSpace projectSpace) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		version = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param projectSpace
	 *            the {@link ProjectSpace} that should get updated
	 * @param version
	 *            the version to update to
	 */
	public UIUpdateProjectController(Shell shell, ProjectSpace projectSpace, VersionSpec version) {
		super(shell);
		this.projectSpace = projectSpace;
		this.version = version;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback#noChangesOnServer()
	 */
	public void noChangesOnServer() {
		new RunInUIThread(getShell()) {
			@Override
			public Void run(Shell shell) {
				MessageDialog.openInformation(getShell(), "No need to update",
					"Your project is up to date, you do not need to update.");
				return null;
			}
		}.execute();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback#conflictOccurred(org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException)
	 */
	public boolean conflictOccurred(ChangeConflictException conflictException) {
		ProjectSpace projectSpace = conflictException.getProjectSpace();
		boolean mergeSuccessful = false;
		try {
			PrimaryVersionSpec targetVersion = projectSpace.resolveVersionSpec(VersionSpec.HEAD_VERSION);
			mergeSuccessful = projectSpace.merge(targetVersion, new MergeProjectHandler(conflictException));
		} catch (EmfStoreException e) {
			WorkspaceUtil.logException(
				String.format("Exception while merging the project %s!", projectSpace.getProjectName()), e);
			handleException(e);
		}
		return mergeSuccessful;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback#inspectChanges(org.eclipse.emf.emfstore.client.model.ProjectSpace,
	 *      java.util.List)
	 */
	public boolean inspectChanges(final ProjectSpace projectSpace, final List<ChangePackage> changePackages) {
		return new RunInUIThreadWithReturnValue<Boolean>(getShell()) {
			@Override
			public Boolean run(Shell shell) {
				UpdateDialog updateDialog = new UpdateDialog(getShell(), projectSpace, changePackages);
				if (updateDialog.open() == Window.OK) {
					return true;
				}
				return false;
			}
		}.execute();

	}

	public void updateCompleted(ProjectSpace projectSpace, PrimaryVersionSpec oldVersion, PrimaryVersionSpec newVersion) {

	}

	@Override
	public PrimaryVersionSpec doRun(final IProgressMonitor pm) throws EmfStoreException {
		PrimaryVersionSpec oldBaseVersion = projectSpace.getBaseVersion();
		PrimaryVersionSpec newBaseVersion = new RunInUIThreadWithReturnValue<PrimaryVersionSpec>(getShell()) {
			@Override
			public PrimaryVersionSpec run(Shell shell) {
				try {
					return projectSpace.update(version, UIUpdateProjectController.this, pm);
				} catch (EmfStoreException e) {
					setException(e);
				}

				return null;
			}
		}.execute();

		if (hasException()) {
			throw getException();
		}

		if (oldBaseVersion.equals(newBaseVersion)) {
			noChangesOnServer();
			return oldBaseVersion;
		}

		return newBaseVersion;
	}
}
