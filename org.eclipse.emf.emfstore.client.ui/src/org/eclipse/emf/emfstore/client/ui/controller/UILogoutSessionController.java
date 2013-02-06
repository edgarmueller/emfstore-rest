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
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for logging out a given session.
 * 
 * @author emueller
 * 
 */
public class UILogoutSessionController extends AbstractEMFStoreUIController<Void> {

	private Usersession session;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during logging out the given session
	 * @param session
	 *            the session to be logged out
	 */
	public UILogoutSessionController(Shell shell, Usersession session) {
		super(shell);
		this.session = session;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor progressMonitor) throws EmfStoreException {

		if (session == null) {
			return null;
		}

		try {
			session.logout();
		} catch (EmfStoreException e) {
			MessageDialog.openWarning(getShell(), "Logout failed", "Logout failed: " + e.getMessage());
		}

		// reset the password in the RAM cache
		if (!session.isSavePassword()) {
			session.setPassword(null);
		}

		// TODO OTS auto save
		// WorkspaceProvider.getInstance().getWorkspace().save();

		return null;
	}

}