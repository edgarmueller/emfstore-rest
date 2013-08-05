/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.common;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.handler.ESRunnableContext;

/**
 * Executes the given {@link Runnable} in the UI thread.
 * 
 * @author emueller
 * 
 */
public class UIRunnableContext implements ESRunnableContext {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.handler.IRunnableContext#executeRunnable(java.lang.Runnable)
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
