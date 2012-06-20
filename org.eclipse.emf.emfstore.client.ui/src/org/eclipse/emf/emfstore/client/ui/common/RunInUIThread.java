/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH,
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.common;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A simple wrapper for UI calls that has the same effect as calling {@link Display#syncExec(Runnable)} and is
 * only provided for aesthetic reasons.
 * 
 * @author emueller
 * 
 * @see RunInUIThreadWithResult
 */
public abstract class RunInUIThread extends RunInUIThreadWithResult<Void> {

	/**
	 * Constructor.
	 * 
	 * @param display
	 *            the {@link Display} that will be used to execute the wrapped call
	 */
	public RunInUIThread(Display display) {
		super(display);
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the {@link Shelll} that will be used to execute the wrapped call
	 */
	public RunInUIThread(Shell shell) {
		super(shell);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithResult#doRun(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public abstract Void doRun(Shell shell);
}