/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.controller.ChangeConflict;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESChangeConflictImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.internal.common.APIUtil;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdToEObjectMappingImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for performing a paged update.
 * 
 * @author emueller
 * 
 */
public class UIUpdateProjectController extends
	AbstractEMFStoreUIController<ESPrimaryVersionSpec> implements
	ESUpdateCallback {

	protected final static int ALL_CHANGES = -1;

	private final ESLocalProject localProject;
	private ESVersionSpec version;
	private final int maxChanges;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param localProject
	 *            the {@link ESLocalProject} that should get updated
	 */
	public UIUpdateProjectController(Shell shell, ESLocalProject localProject) {
		super(shell, true, true);
		this.localProject = localProject;
		this.maxChanges = ALL_CHANGES;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param localProject
	 *            the {@link ESLocalProject} that should get updated
	 * @param maxChanges
	 *            the number of maximally allowed changes
	 */
	public UIUpdateProjectController(Shell shell, ESLocalProject localProject, int maxChanges) {
		super(shell, true, true);
		this.localProject = localProject;
		this.maxChanges = maxChanges;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 * @param localProject
	 *            the {@link ESLocalProject} that should get updated
	 * @param versionSpec
	 *            the version to update to
	 */
	public UIUpdateProjectController(Shell shell, ESLocalProject localProject, ESVersionSpec versionSpec) {
		super(shell, true, true);
		this.localProject = localProject;
		this.version = versionSpec;
		this.maxChanges = ALL_CHANGES;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#noChangesOnServer()
	 */
	public void noChangesOnServer() {
		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				MessageDialog
					.openInformation(getShell(), "No need to update",
						"Your project is up to date, you do not need to update.");
				return null;
			}
		});
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#conflictOccurred(org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException)
	 */
	public boolean conflictOccurred(final ESChangeConflict changeConflict,
		final IProgressMonitor monitor) {
		// TODO OTS
		boolean mergeSuccessful = false;
		try {
			final ProjectSpace internalProject = ((ESLocalProjectImpl) localProject).toInternalAPI();
			final ChangeConflict internalChangeConflict = ((ESChangeConflictImpl) changeConflict).toInternalAPI();

			// merge opens up a dialog
			return internalProject.merge(
				internalProject.resolveVersionSpec(Versions.createHEAD(), monitor),
				internalChangeConflict,
				new MergeProjectHandler(),
				UIUpdateProjectController.this,
				monitor);
		} catch (final ESException e) {
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					handleMergeException(e);
					return null;
				}
			});
		}

		return mergeSuccessful;
	}

	private void handleMergeException(ESException e) {
		WorkspaceUtil.logException(String.format(
			"Exception while merging the project %s!",
			localProject.getProjectName()), e);
		EMFStoreMessageDialog.showExceptionDialog(getShell(), e);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#inspectChanges(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      java.util.List)
	 */
	public boolean inspectChanges(final ESLocalProject localProject,
		final List<ESChangePackage> changePackages,
		final ESModelElementIdToEObjectMapping idToEObjectMapping) {

		return RunInUI.runWithResult(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				List<ChangePackage> internal = APIUtil.toInternal(ChangePackage.class, changePackages);
				UpdateDialog updateDialog = new UpdateDialog(getShell(), localProject,
					internal,
					((ESModelElementIdToEObjectMappingImpl) idToEObjectMapping).toInternalAPI());
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
	public ESPrimaryVersionSpec doRun(final IProgressMonitor monitor)
		throws ESException {

		ESPrimaryVersionSpec oldBaseVersion = localProject.getBaseVersion();
		ESPrimaryVersionSpec resolvedVersion;
		ESPrimaryVersionSpec newBaseVersion;

		ESPrimaryVersionSpec headVersion = localProject.resolveVersionSpec(
			ESVersionSpec.FACTORY.createHEAD(),
			monitor);

		if (maxChanges == ALL_CHANGES) {
			resolvedVersion = headVersion;
		} else {
			ESPrimaryVersionSpecImpl oldBaseVersionImpl = (ESPrimaryVersionSpecImpl) oldBaseVersion;
			resolvedVersion = localProject.resolveVersionSpec(
				ESVersionSpec.FACTORY.createPAGEDUPDATE(ModelUtil.clone(oldBaseVersionImpl.toInternalAPI()).toAPI(),
					maxChanges),
				monitor);
		}

		if (oldBaseVersion.equals(resolvedVersion)) {
			noChangesOnServer();
			return oldBaseVersion;
		}

		if (version != null) {
			newBaseVersion = localProject.update(version,
				UIUpdateProjectController.this, monitor);
		} else {
			newBaseVersion = localProject.update(resolvedVersion,
				UIUpdateProjectController.this, monitor);
		}

		if (maxChanges != ALL_CHANGES && !newBaseVersion.equals(headVersion)) {
			boolean yes = RunInUI.runWithResult(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return MessageDialog.openConfirm(getShell(), "More updates available",
						"There are more updates available on the server.  Do you want to fetch and apply them now?");
				}
			});
			if (yes) {
				return RunInUI.WithException.runWithResult(new Callable<ESPrimaryVersionSpec>() {
					public ESPrimaryVersionSpec call() throws Exception {
						return new UIUpdateProjectController(getShell(), localProject, maxChanges).executeSub(monitor);
					}
				});
			}
		}

		return newBaseVersion;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#checksumCheckFailed(org.eclipse.emf.emfstore.internal.client.model.ProjectSpace,
	 *      org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor) throws ESException {
		ESChecksumErrorHandler errorHandler = Configuration.getClientBehavior().getChecksumErrorHandler();
		return errorHandler.execute(projectSpace, versionSpec, monitor);
	}
}
