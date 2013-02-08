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
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.NewRepositoryWizard;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * UI controller for adding a server (also called repository).
 * 
 * @author emueller
 */
public class UIAddServerController extends AbstractEMFStoreUIController<Void> {

	/**
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used during the update
	 */
	public UIAddServerController(Shell shell) {
		super(shell);
	}

	@Override
	public Void doRun(IProgressMonitor monitor) throws EMFStoreException {
		NewRepositoryWizard wizard = new NewRepositoryWizard();
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		wizard.init(activeWorkbenchWindow.getWorkbench(), (IStructuredSelection) activeWorkbenchWindow
			.getSelectionService().getSelection());
		WizardDialog dialog = new WizardDialog(activeWorkbenchWindow.getShell(), wizard);
		dialog.create();
		dialog.open();
		return null;
	}

}