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
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESPagedUpdateConfig;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.internal.common.APIUtil;
import org.eclipse.emf.emfstore.internal.common.ExtensionRegistry;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdToEObjectMappingImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.internal.server.impl.api.ESConflictSetImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.ESConflictSet;
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
 */
public class UIUpdateProjectController extends
	AbstractEMFStoreUIController<ESPrimaryVersionSpec> implements
	ESUpdateCallback {

	protected static final int ALL_CHANGES = -1;
	private static boolean doNotUsePagedUpdate = true;

	private final ESLocalProject localProject;
	private ESVersionSpec version;
	private int maxChanges;

	private ESPrimaryVersionSpec resolvedVersion;

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
		initPagedUpdateSize();
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
		initPagedUpdateSize();
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

	private void initPagedUpdateSize() {
		ESPagedUpdateConfig pagedUpdateConfig = ExtensionRegistry.INSTANCE.get(
			ESPagedUpdateConfig.ID,
			ESPagedUpdateConfig.class);

		if (pagedUpdateConfig != null) {
			maxChanges = pagedUpdateConfig.getNumberOfAllowedChanges();
			doNotUsePagedUpdate = false;
		}
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
	public boolean conflictOccurred(final ESConflictSet changeConflict,
		final IProgressMonitor monitor) {
		// TODO OTS
		final ProjectSpace internalProject = ((ESLocalProjectImpl) localProject).toInternalAPI();
		final ChangeConflictSet internalChangeConflict = ((ESConflictSetImpl) changeConflict).toInternalAPI();
		return new MergeProjectHandler(false).resolveConflicts(internalProject.getProject(), internalChangeConflict);
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

		List<ChangePackage> internal = APIUtil.toInternal(ChangePackage.class, changePackages);
		final UpdateDialog updateDialog = new UpdateDialog(getShell(), localProject,
			internal,
			((ESModelElementIdToEObjectMappingImpl) idToEObjectMapping).toInternalAPI());

		return RunInUI.runWithResult(new Callable<Boolean>() {
			public Boolean call() throws Exception {
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
		ESPrimaryVersionSpec newBaseVersion;

		ESPrimaryVersionSpec headVersion = localProject.resolveVersionSpec(
			ESVersionSpec.FACTORY.createHEAD(),
			monitor);

		if (doNotUsePagedUpdate) {
			resolvedVersion = headVersion;
		} else {
			ESPrimaryVersionSpecImpl oldBaseVersionImpl = (ESPrimaryVersionSpecImpl) oldBaseVersion;
			resolvedVersion = resolveVersionByChanges(maxChanges, ModelUtil.clone(oldBaseVersionImpl.toInternalAPI())
				.toAPI(), monitor);
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

		if (!doNotUsePagedUpdate && !newBaseVersion.equals(headVersion) && !newBaseVersion.equals(oldBaseVersion)) {
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

	private ESPrimaryVersionSpec resolveVersionByChanges(int maxChanges, ESPrimaryVersionSpec baseVersion,
		IProgressMonitor monitor) throws ESException {
		return localProject.resolveVersionSpec(
			ESVersionSpec.FACTORY.createPAGEDUPDATE(baseVersion, maxChanges),
			monitor);
	}
}
