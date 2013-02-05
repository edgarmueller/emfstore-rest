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
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.impl.ProjectInfoToRemoteProjectWrapper;
import org.eclipse.emf.emfstore.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for checking out a specific revision of a project.<br/>
 * It is assumed that the user previously has selected a {@link HisotryInfo} instance.
 * 
 * @author emueller
 * 
 */
public class CheckoutRevisionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {

		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);
		PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());

		// TODO: remove HistoryBrowserView
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

		if (!(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return;
		}

		HistoryBrowserView view = (HistoryBrowserView) activePage.getActivePart();

		ProjectInfo projectInfo = ((ProjectInfoToRemoteProjectWrapper) view.getProjectSpace().getRemoteProject())
			.getProjectInfo();

		// FIXME: eContainer call
		new UICheckoutController(getShell(), view.getProjectSpace().getUsersession().getServerInfo(), projectInfo,
			versionSpec).execute();
	}

}