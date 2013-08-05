/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.ui.views.emfstorebrowser.views.NewRepositoryWizard;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
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
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shell} that will be used to display any UI controls
	 */
	public UIAddServerController(Shell shell) {
		super(shell);
	}

	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {
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