/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model.impl;

import org.eclipse.emf.emfstore.common.model.util.IdEObjectCollectionChangeObserver;

/**
 * Command for notifying about changes of an EObject.
 * 
 * @author koegel
 */
public interface EObjectChangeObserverNotificationCommand {

	/**
	 * Run the command on an {@link IdEObjectCollectionChangeObserver}.
	 * 
	 * @param observer the observer
	 */
	void run(IdEObjectCollectionChangeObserver observer);
}
