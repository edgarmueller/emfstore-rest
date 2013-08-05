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
package org.eclipse.emf.emfstore.internal.client.model.impl;

import org.eclipse.emf.emfstore.client.handler.ESRunnableContext;

/**
 * A {@link ESRunnableContext} implementation that does nothing.
 * 
 * @author emueller
 * 
 */
public class DefaultRunnableContext implements ESRunnableContext {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESRunnableContext#executeRunnable(java.lang.Runnable)
	 */
	public void executeRunnable(Runnable runnable) {
		runnable.run();
	}
}
