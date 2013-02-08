/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.common;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.internal.client.common.IRunnableContext;

/**
 * Executes the given {@link Runnable} in the UI thread.
 * 
 * @author emueller
 * 
 */
public class UIRunnableContext implements IRunnableContext {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.common.IRunnableContext#executeRunnable(java.lang.Runnable)
	 */
	public void executeRunnable(final Runnable runnable) {
		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				runnable.run();
				return null;
			}
		});
	}

}
