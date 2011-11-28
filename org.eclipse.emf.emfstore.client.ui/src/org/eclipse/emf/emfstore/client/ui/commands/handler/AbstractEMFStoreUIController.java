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
package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.ecp.common.util.DialogHandler;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract controller class that supports a {@link ProgressMonitorDialog}.
 * 
 * @author wesendon
 */
public abstract class AbstractEMFStoreUIController {

	private Shell shell;
	private ProgressMonitorDialog progressDialog;

	/**
	 * Returns the {@link ProgressMonitorDialog}.
	 * 
	 * @return the progress monitor dialog
	 */
	protected ProgressMonitorDialog getProgressMonitorDialog() {
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(getShell());
		progressDialog.open();
		progressDialog.setCancelable(true);
		return progressDialog;
	}

	/**
	 * Closes the progress monitor associated with this controller.
	 */
	protected void closeProgress() {
		progressDialog.close();
	}

	/**
	 * Returns the shell that is used by this controller.
	 * 
	 * @return the shell used
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * Sets the shell that will be used by this controller.
	 * 
	 * @param shell the shell to be used
	 */
	public void setShell(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Handles the given exception and closes the progress dialog.
	 * 
	 * @param exception the exception to be handled
	 */
	public void handleException(Exception exception) {
		DialogHandler.showExceptionDialog(exception);
		closeProgress();
	}

}
