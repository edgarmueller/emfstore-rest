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
package org.eclipse.emf.emfstore.internal.client.model.impl;

import org.eclipse.emf.emfstore.internal.client.common.IRunnableContext;

/**
 * A {@link IRunnableContext} implementation that does nothing.
 * 
 * @author emueller
 * 
 */
public class DefaultRunnableContext implements IRunnableContext {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.internal.client.common.IRunnableContext#executeRunnable(java.lang.Runnable)
	 */
	public void executeRunnable(Runnable runnable) {
		runnable.run();
	}
}
