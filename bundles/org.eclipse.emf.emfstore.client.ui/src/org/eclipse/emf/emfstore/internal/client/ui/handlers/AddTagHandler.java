/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * ovonwesen
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIAddTagController;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for adding a tag to a selected {@link HistoryInfo} instance.
 * It is assumed that the user previously has selected a {@link HistoryInfo} instance.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class AddTagHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);

		ESLocalProject localProject = getProjectFromHistoryView();
		ESHistoryInfo info = historyInfo.toAPI();

		// TODO
		if (localProject == null || info == null) {
			return;
		}

		new UIAddTagController(getShell(), localProject, info).execute();
		getHistoryBrowserViewFromActivePart().refresh();
	}

	private ESLocalProject getProjectFromHistoryView() {
		HistoryBrowserView historyBrowserView = getHistoryBrowserViewFromActivePart();

		if (historyBrowserView == null) {
			return null;
		}

		return historyBrowserView.getProjectSpace().toAPI();
	}

	private HistoryBrowserView getHistoryBrowserViewFromActivePart() {
		// TODO: controller currently does not work if the active workbench window is not
		// the history view
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

		if (activePage == null || !(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return null;
		}

		return (HistoryBrowserView) activePage.getActivePart();
	}

}
