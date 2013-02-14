/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
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

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.model.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for updating a project.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UIUpdateProjectController extends AbstractEMFStoreUIController<ESPrimaryVersionSpec> implements
	IUpdateCallback {

	private final ESLocalProject projectSpace;
	private ESVersionSpec version;

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
	public UIUpdateProjectController(Shell shell, ESLocalProject projectSpace, ESVersionSpec version) {
		super(shell);
		this.projectSpace = projectSpace;
		this.version = version;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback#noChangesOnServer()
	 */
	public void noChangesOnServer() {
		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				MessageDialog.openInformation(getShell(), "No need to update",
					"Your project is up to date, you do not need to update.");
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback#conflictOccurred(org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException)
	 */
	public boolean conflictOccurred(final ESChangeConflict changeConflict,
		final IProgressMonitor progressMonitor) {
		// TODO OTS
		boolean mergeSuccessful = false;
		try {
			final ESPrimaryVersionSpec targetVersion = projectSpace.resolveVersionSpec(ESVersionSpec.FACTORY
				.createHEAD(projectSpace
					.getBaseVersion()), new NullProgressMonitor());
			// merge opens up a dialog
			return projectSpace.merge(targetVersion, changeConflict,
				new MergeProjectHandler(), this, progressMonitor);
		} catch (final EMFStoreException e) {
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					handleMergeException(projectSpace, e);
					return null;
				}
			});
		}

		return mergeSuccessful;
	}

	private void handleMergeException(final ESLocalProject projectSpace, EMFStoreException e) {
		WorkspaceUtil.logException(
			String.format("Exception while merging the project %s!", projectSpace.getProjectName()), e);
		EMFStoreMessageDialog.showExceptionDialog(getShell(), e);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback#inspectChanges(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      java.util.List)
	 */
	public boolean inspectChanges(final ESLocalProject projectSpace,
		final List<ESChangePackage> changePackages, final IModelElementIdToEObjectMapping idToEObjectMapping) {
		return RunInUI.runWithResult(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				@SuppressWarnings("unchecked")
				UpdateDialog updateDialog = new UpdateDialog(getShell(), (ProjectSpace) projectSpace,
					(List<ChangePackage>) (List<?>) changePackages, idToEObjectMapping);
				if (updateDialog.open() == Window.OK) {
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ESPrimaryVersionSpec doRun(final IProgressMonitor monitor) throws EMFStoreException {
		ESPrimaryVersionSpec oldBaseVersion = projectSpace.getBaseVersion();

		ESPrimaryVersionSpec resolveVersionSpec = projectSpace.resolveVersionSpec(ESVersionSpec.FACTORY
			.createHEAD(oldBaseVersion), new NullProgressMonitor());

		if (oldBaseVersion.equals(resolveVersionSpec)) {
			noChangesOnServer();
			return oldBaseVersion;
		}

		PrimaryVersionSpec newBaseVersion = (PrimaryVersionSpec) projectSpace.update(version,
			UIUpdateProjectController.this, monitor);

		return newBaseVersion;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback#checksumCheckFailed(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor) throws EMFStoreException {
		ESChecksumErrorHandler errorHandler = Configuration.getChecksumErrorHandler();
		return errorHandler.execute(projectSpace, versionSpec, monitor);
	}
}