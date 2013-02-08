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

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ui.common.MonitoredEMFStoreAction;
import org.eclipse.emf.emfstore.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract UI controller class. UI controllers are responsible for calling the controllers that do the actual work
 * and handling UI related aspects of executing them. This might be calling some information dialogs as well as handling
 * exceptions thrown by the controllers. The action executed by a controller may either be run in the UI thread or get
 * executed in its own thread. If the controller's action should be executed in its own thread you have to wrap all
 * UI calls with RunInUIThread or {@link RunInUIThreadWithResult} to avoid invalid thread access
 * exceptions.
 * 
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @param <T> return type of the controller
 */
public abstract class AbstractEMFStoreUIController<T> extends MonitoredEMFStoreAction<T> {

	private Shell shell;

	/**
	 * Constructor. The constructed UI controller will executed its action in the UI thread.
	 * 
	 * @param shell
	 *            the shell that will be used during execution of the controller's action
	 */
	public AbstractEMFStoreUIController(Shell shell) {
		super(false, false);
		setShell(shell);
	}

	/**
	 * Constructor. The constructed UI controller will executed its action in the UI thread.
	 * 
	 * @param shell
	 *            the shell that will be used during execution of the controller's action
	 * @param cancelable
	 *            whether the controller's action is cancelable
	 */
	public AbstractEMFStoreUIController(Shell shell, boolean cancelable) {
		super(false, cancelable);
		setShell(shell);
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that will be used during execution of the controller's action
	 * @param fork
	 *            whether the controller's action will fork an own thread that runs outside of the UI thread
	 * @param cancelable
	 *            whether the controller's action is cancelable
	 */
	public AbstractEMFStoreUIController(Shell shell, boolean fork, boolean cancelable) {
		super(fork, cancelable);
		setShell(shell);
	}

	/**
	 * Returns the shell used by the UI controller.
	 * 
	 * @return the shell
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * Sets the shell to be used by this UI controller.
	 * 
	 * @param newShell
	 *            the shell to be used by the UI controller
	 */
	public void setShell(Shell newShell) {
		this.shell = newShell;
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
		if (isForked()) {
			return RunInUI.runWithResult(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					MessageDialog dialog = new MessageDialog(shell, title, null, message, MessageDialog.QUESTION,
						new String[] { "Yes", "No" }, 0);
					int result = dialog.open();
					return result == Window.OK;
				}
			});
		} else {
			MessageDialog dialog = new MessageDialog(shell, title, null, message, MessageDialog.QUESTION, new String[] {
				"Yes", "No" }, 0);
			int result = dialog.open();
			return result == Window.OK;
		}
	}

	@Override
	protected void handleException(final EMFStoreException e) {
		WorkspaceUtil.logException(e.getMessage(), e);

		if (isForked()) {
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					MessageDialog.openError(getShell(), "Error", e.getMessage());
					return null;
				}
			});
		} else {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}
}