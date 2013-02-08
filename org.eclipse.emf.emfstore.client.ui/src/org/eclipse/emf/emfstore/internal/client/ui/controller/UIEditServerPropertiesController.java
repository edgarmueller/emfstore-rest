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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.client.ui.views.emfstorebrowser.views.NewRepositoryWizard;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for editing server properties.
 * 
 * @author emueller
 * 
 */
public class UIEditServerPropertiesController extends AbstractEMFStoreUIController<Void> {

	private final ServerInfo serverInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param serverInfo
	 *            the server info that should be edited
	 */
	public UIEditServerPropertiesController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws EMFStoreException {
		NewRepositoryWizard wizard = new NewRepositoryWizard();
		wizard.setServerInfo(serverInfo);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.open();
		return null;
	}

}