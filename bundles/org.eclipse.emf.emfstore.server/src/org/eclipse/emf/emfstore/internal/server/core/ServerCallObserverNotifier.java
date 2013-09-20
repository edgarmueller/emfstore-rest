/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * User - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.core;

import org.eclipse.emf.emfstore.server.observer.ESServerCallObserver;

/**
 * Notification for a ServerCallObserver.
 * 
 * @author mkoegel
 * 
 */
public interface ServerCallObserverNotifier {

	/**
	 * Notify given {@link ESServerCallObserver}.
	 * 
	 * @param observer the observer to be notified
	 */
	void notify(ESServerCallObserver observer);

}
