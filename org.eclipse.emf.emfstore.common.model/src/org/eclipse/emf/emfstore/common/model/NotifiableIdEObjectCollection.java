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
package org.eclipse.emf.emfstore.common.model;

import org.eclipse.emf.emfstore.common.model.util.EObjectChangeNotifier;

/**
 * @author emueller
 */
public interface NotifiableIdEObjectCollection extends IdEObjectCollection, IdEObjectCollectionChangeObserver {

	/**
	 * Adds an {@link IdEObjectCollectionChangeObserver} to the collection.
	 * 
	 * @param eObjectChangeObserver
	 *            a change observer to be added
	 */
	void addIdEObjectCollectionChangeObserver(IdEObjectCollectionChangeObserver eObjectChangeObserver);

	/**
	 * Remove an {@link IdEObjectCollectionChangeObserver} from the collection.
	 * 
	 * @param eObjectChangeObserver
	 *            a change observer to be removed
	 */
	void removeIdEObjectCollectionChangeObserver(IdEObjectCollectionChangeObserver eObjectChangeObserver);

	/**
	 * Returns the change notifier attached to this collection.
	 * 
	 * @return an {@link EObjectChangeNotifier}
	 */
	EObjectChangeNotifier getChangeNotifier();
}