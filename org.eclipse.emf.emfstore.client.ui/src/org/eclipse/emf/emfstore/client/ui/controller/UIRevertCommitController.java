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
package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.internal.client.model.controller.RevertCommitController;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * UI controller for reverting a commit.
 * 
 * @author emueller
 * @author wesendon
 * 
 */
public class UIRevertCommitController extends AbstractEMFStoreUIController<Void> {

	private final HistoryInfo historyInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that is used during the revert
	 * @param historyInfo
	 *            the {@link HistoryInfo} that is used to determine which commit to revert
	 */
	public UIRevertCommitController(Shell shell, HistoryInfo historyInfo) {
		super(shell);
		this.historyInfo = historyInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws EMFStoreException {
		// TODO: remove HistoryBrowserView
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

		if (activePage == null || !(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return null;
		}

		HistoryBrowserView view = (HistoryBrowserView) activePage.getActivePart();

		MessageDialog dialog = new MessageDialog(null, "Confirmation", null,
			"Do you really want to force to revert changes of this version on project "
				+ view.getProjectSpace().getProjectName(), MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
		int result = dialog.open();
		if (result == Window.OK) {
			PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());
			try {
				new RevertCommitController(view.getProjectSpace(), versionSpec, true).execute();
			} catch (EMFStoreException e) {
				// TODO: no error handling?
			}
		}
		return null;
	}
}