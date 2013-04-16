/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.controller.RevertCommitController;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for reverting a commit.
 * 
 * @author emueller
 * @author wesendon
 * 
 */
public class UIRevertCommitController extends AbstractEMFStoreUIController<Void> {

	private final ESPrimaryVersionSpecImpl versionSpec;
	private ESLocalProjectImpl localProject;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that is used during the revert
	 * @param historyInfo
	 *            the {@link HistoryInfo} that is used to determine which commit to revert
	 */
	public UIRevertCommitController(Shell shell, ESPrimaryVersionSpec versionSpec, ESLocalProject localProject) {
		super(shell, false, false);
		this.localProject = (ESLocalProjectImpl) localProject;
		this.versionSpec = (ESPrimaryVersionSpecImpl) versionSpec;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {

		MessageDialog dialog = new MessageDialog(null, "Confirmation", null,
			"Do you really want to force to revert changes of this version on project "
				+ localProject.toInternalAPI().getProjectName(), MessageDialog.QUESTION, new String[] { "Yes", "No" },
			0);
		int result = dialog.open();
		if (result == Window.OK) {
			PrimaryVersionSpec primaryVersionSpec = ModelUtil.clone(versionSpec.toInternalAPI());
			try {
				new RevertCommitController(localProject.toInternalAPI(), primaryVersionSpec, true).execute();
			} catch (ESException e) {
				e.printStackTrace();
				// TODO: no error handling?
			}
		}
		return null;
	}
}