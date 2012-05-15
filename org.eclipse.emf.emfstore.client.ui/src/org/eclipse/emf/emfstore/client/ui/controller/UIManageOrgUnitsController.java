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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.AdminBroker;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.ManageOrgUnitsDialog;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * UI controller for managing org units.
 * 
 * @author emueller
 * 
 */
public class UIManageOrgUnitsController extends AbstractEMFStoreUIController<Void> {

	private final Usersession session;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used
	 * @param session
	 *            the session to be used for managing the org units
	 */
	public UIManageOrgUnitsController(Shell shell, Usersession session) {
		super(shell);
		this.session = session;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected Void doRun(IProgressMonitor progressMonitor) throws EmfStoreException {
		try {
			final AdminBroker adminBroker = WorkspaceManager.getInstance().getCurrentWorkspace()
				.getAdminBroker(session);
			new RunInUIThread(getShell()) {
				@Override
				public Void run(Shell shell) {
					ManageOrgUnitsDialog dialog = new ManageOrgUnitsDialog(PlatformUI.getWorkbench().getDisplay()
						.getActiveShell(), adminBroker);
					dialog.create();
					dialog.open();
					return null;
				}
			}.execute();
		} catch (final AccessControlException e) {
			new RunInUIThread(getShell()) {
				@Override
				public Void run(Shell shell) {
					MessageDialog.openError(getShell(), "Access denied ", e.getMessage());
					return null;
				}
			}.execute();
		} catch (final EmfStoreException e) {
			new RunInUIThread(getShell()) {
				@Override
				public Void run(Shell shell) {
					MessageDialog.openError(getShell(), "Error while retrieving the admin broker", e.getMessage());
					return null;
				}
			}.execute();
		}

		return null;
	}

}
