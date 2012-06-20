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

import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * UI-related controller for adding and removing version tags.
 * 
 * @author emueller
 * 
 */
// TODO: re-package exception for more sensible error messages
public abstract class UITagController extends AbstractEMFStoreUIController<Void> {

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 */
	public UITagController(Shell shell) {
		super(shell);
	}

	protected HistoryBrowserView getHistoryBrowserViewFromActivePart() {
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
