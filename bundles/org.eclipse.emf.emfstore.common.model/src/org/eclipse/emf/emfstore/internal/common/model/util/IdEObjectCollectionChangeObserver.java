/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;

/**
 * An observer interface for tracking changes upon an {@link IdEObjectCollection}.
 * 
 * @author emueller
 * 
 */
public interface IdEObjectCollectionChangeObserver extends ESObserver {

	/**
	 * A notification on a model element of the collection occurred.
	 * See {@link Notification} documentation for details on the notifications.
	 * This method will be called even if the given notification results from an add
	 * or remove of a model element of the project.
	 * 
	 * @param notification
	 *            the notification
	 * @param collection
	 *            the collection holding the <code>modelElement</code>
	 * @param modelElement
	 *            the model element the notification originates from
	 */
	void notify(Notification notification, IdEObjectCollection collection, EObject modelElement);

	/**
	 * Called when the {@code eObject} has been added to the {@code collection}.
	 * 
	 * @param collection
	 *            the {@link IdEObjectCollection} to which the {@code eObject} was added
	 * @param eObject
	 *            the {@link EObject} that has been added to the {@code collection}
	 */
	void modelElementAdded(IdEObjectCollection collection, EObject eObject);

	/**
	 * Called when the {@code eObject} and its siblings have been removed from
	 * the {@code collection}.
	 * 
	 * <b>NOTE</b>: Note that you will NOT receive a separate notification for each
	 * sibling.
	 * 
	 * @param collection
	 *            the {@link IdEObjectCollection} to which the {@code eObject} was added
	 * @param eObject
	 *            the {@link EObject} that has been added to the {@code collection}
	 */
	void modelElementRemoved(final IdEObjectCollection collection, final EObject eObject);

	/**
	 * If the {@link IdEObjectCollection} is deleted.
	 * 
	 * @param collection the {@link IdEObjectCollection} that has been deleted
	 */
	void collectionDeleted(IdEObjectCollection collection);
}
