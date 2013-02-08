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
package org.eclipse.emf.emfstore.client.ui.dialogs;

import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Provides a standardized way of opening message dialogs within EMFStore.
 * 
 * @author emueller
 * 
 */
public final class EMFStoreMessageDialog {

	/**
	 * Private constructor.
	 */
	private EMFStoreMessageDialog() {

	}

	/**
	 * Opens a standard error message dialog displaying the given
	 * exception to the user.
	 * 
	 * @param cause
	 *            the exception to be shown
	 * 
	 */
	public static void showExceptionDialog(Exception cause) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		showExceptionDialog(shell, cause);
	}

	/**
	 * Opens a standard error message dialog displaying the given
	 * message and exception to the user.
	 * 
	 * @param message
	 *            the message to be shown
	 * @param cause
	 *            the exception to be shown
	 * 
	 */
	public static void showExceptionDialog(String message, Exception cause) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		showExceptionDialog(shell, message, cause);
	}

	/**
	 * Opens a standard error message dialog displaying the given
	 * message and exception to the user.
	 * 
	 * @param shell
	 *            the shell that is used to show the exception dialog
	 * @param message
	 *            the message to be shown
	 * @param cause
	 *            the exception to be shown
	 * 
	 */
	public static void showExceptionDialog(Shell shell, String message, Exception cause) {
		createAndShowErrorDialog(shell, cause, new StringBuilder(message + ": "));
	}

	/**
	 * Opens a standard error message dialog displaying the given
	 * exception to the user.
	 * 
	 * @param shell
	 *            the shell that is used to show the exception dialog
	 * @param cause
	 *            the exception to be shown
	 * 
	 */
	public static void showExceptionDialog(Shell shell, Exception cause) {
		createAndShowErrorDialog(shell, cause, new StringBuilder());
	}

	private static void createAndShowErrorDialog(Shell shell, Exception cause, StringBuilder stringBuilder) {
		String title = "Error";

		if (cause != null) {
			stringBuilder.append(cause.getMessage());
			title = cause.getClass().getName();
		}

		MessageDialog.openError(shell, title, stringBuilder.toString());
		WorkspaceUtil.handleException("An unexpected error in a EMFStore plugin occured.", cause);
	}
}