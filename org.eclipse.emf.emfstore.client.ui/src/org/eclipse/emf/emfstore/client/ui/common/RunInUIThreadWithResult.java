/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.common;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A simple wrapper for UI calls that has the same effect as calling {@link Display#syncExec(Runnable)} but
 * can return a value.
 * 
 * @author emueller
 * 
 * @param <T> the return type of the wrapped call
 * @see RunInUIThread
 */
public abstract class RunInUIThreadWithResult<T> {

	private T returnValue;
	private final Shell shell;
	private final Display display;

	/**
	 * Constructor.
	 * 
	 * @param display
	 *            the {@link Display} that will be used to execute the wrapped call
	 */
	public RunInUIThreadWithResult(Display display) {
		this.display = display;
		this.shell = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shelll} that will be used to execute the wrapped call
	 */
	public RunInUIThreadWithResult(Shell shell) {
		this.shell = shell;
		this.display = shell.getDisplay();
	}

	/**
	 * Executes the wrapper.
	 * 
	 * @return the return value of the wrapped call
	 */
	public T execute() {

		returnValue = null;

		display.syncExec(new Runnable() {
			public void run() {
				returnValue = RunInUIThreadWithResult.this.doRun(shell == null ? display.getActiveShell() : shell);
			}
		});

		return returnValue;
	}

	/**
	 * Invokes the wrapped call and must be implemented by clients.
	 * 
	 * @param shell
	 *            the shell that is used during the execution
	 * @return an optional return value that may be returned by clients
	 */
	public abstract T doRun(Shell shell);
}