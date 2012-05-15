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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.controller.RunInUIThread;
import org.eclipse.emf.emfstore.client.ui.controller.RunInUIThreadWithReturnValue;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

/**
 * Abstract UI controller class
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @param <T> return type of the controller
 */
public abstract class AbstractEMFStoreUIController<T> {

	protected Shell shell;
	private T returnValue;
	private EmfStoreException exception;

	public AbstractEMFStoreUIController(Shell shell) {
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

	public T execute(boolean fork, boolean cancelable) throws EmfStoreException {

		IWorkbench workbench = PlatformUI.getWorkbench();
		IProgressService progressService = workbench.getProgressService();
		exception = null;

		try {
			progressService.run(fork, cancelable, new IRunnableWithProgress() {
				public void run(final IProgressMonitor pm) {
					try {
						returnValue = doRun(pm);
					} catch (EmfStoreException e) {
						exception = e;
					}
				}
			});
		} catch (InvocationTargetException e) {
			WorkspaceUtil.logException("Error during excuting an EMFStore UI controller: " + e.getMessage(), e);
		} catch (InterruptedException e) {
			WorkspaceUtil.logException("Error during excuting an EMFStore UI controller: " + e.getMessage(), e);
		}

		if (exception != null) {
			throw exception;
		}

		return returnValue;
	}

	protected abstract T doRun(IProgressMonitor pm) throws EmfStoreException;
}
