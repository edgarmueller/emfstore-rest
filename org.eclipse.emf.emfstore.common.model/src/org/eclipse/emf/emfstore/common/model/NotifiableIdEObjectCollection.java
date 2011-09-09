/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.common.model;

import org.eclipse.emf.emfstore.common.model.util.EObjectChangeNotifier;
import org.eclipse.emf.emfstore.common.model.util.EObjectChangeObserver;

/**
 * @author emueller
 */
public interface NotifiableIdEObjectCollection extends IdEObjectCollection,
		EObjectChangeObserver {

	/**
	 * Adds an {@link EObjectChangeObserver} to the collection.
	 * 
	 * @param eObjectChangeObserver
	 *            a change observer to be added
	 */
	void addEObjectChangeObserver(EObjectChangeObserver eObjectChangeObserver);

	/**
	 * Remove an {@link EObjectChangeObserver} from the collection.
	 * 
	 * @param eObjectChangeObserver
	 *            a change observer to be removed
	 */
	void removeEObjectChangeObserver(EObjectChangeObserver eObjectChangeObserver);

	/**
	 * Returns the change notifier attached to this collection.
	 * 
	 * @return an {@link EObjectChangeNotifier}
	 */
	EObjectChangeNotifier getChangeNotifier();
}
