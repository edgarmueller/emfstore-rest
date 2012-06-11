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
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.common.MonitoredEMFStoreRequest;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThread;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithReturnValue;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract UI controller class
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @param <T> return type of the controller
 */
public abstract class AbstractEMFStoreUIController<T> extends MonitoredEMFStoreRequest<T> {

	protected Shell shell;
	private T returnValue;
	private EmfStoreException exception;

	public AbstractEMFStoreUIController(Shell shell) {
		super(false, false);
		setShell(shell);
	}

	public AbstractEMFStoreUIController(Shell shell, boolean cancelable) {
		super(false, cancelable);
		setShell(shell);
	}

	public AbstractEMFStoreUIController(Shell shell, boolean fork, boolean cancelable) {
		super(fork, cancelable);
		setShell(shell);
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Shows a confirmation dialog.
	 * 
	 * @param title
	 *            the title of the confirmation dialog
	 * @param message
	 *            the message to be shown in the dialog
	 * 
	 * @return true, if the user confirms the dialog by clicking "Yes", otherwise false
	 */
	public boolean confirm(final String title, final String message) {
		return new RunInUIThreadWithReturnValue<Boolean>(getShell()) {
			@Override
			public Boolean run(Shell shell) {
				MessageDialog dialog = new MessageDialog(shell, title, null, message, MessageDialog.QUESTION,
					new String[] { "Yes", "No" }, 0);
				int result = dialog.open();
				return result == Window.OK;
			}
		}.execute();
	}

	public void handleException(final Exception exception) {
		new RunInUIThread(getShell()) {
			@Override
			public Void run(Shell shell) {
				EMFStoreMessageDialog.showExceptionDialog(shell, exception);
				return null;
			}
		}.execute();
	}

	protected EmfStoreException getException() {
		return exception;
	}

	protected boolean hasException() {
		return exception != null;
	}

	protected void setException(EmfStoreException exception) {
		this.exception = exception;
	}
}
