/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * ovonwesen
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.observer;

import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * An observer with a priority. The higher the number the more important is the observer.
 * 
 * @author ovonwesen
 */
public interface ESPrioritizedObserver extends ESObserver {

	/**
	 * Returns the priority of this observer. The higher the number returned
	 * by this method, the more likely it is that this observer is notified before
	 * others.
	 * 
	 * @return the priority of this observer
	 */
	int getPriority();

}
