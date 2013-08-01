/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel, Mueller Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;

/**
 * Filter notifications from elements outside of the project.
 * 
 * @author mkoegel
 * @author emueller
 */
public class IgnoreNotificationsOutsideProject implements ESNotificationFilter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo,
	 *      org.eclipse.emf.emfstore.common.model.ESObjectContainer)
	 */
	public boolean check(ESNotificationInfo notificationInfo, ESObjectContainer<?> container) {

		// do not filter notifications from (deleted) elements in project
		final EObject notifierModelElement = notificationInfo.getNotifierModelElement();
		final IdEObjectCollectionImpl containerImpl = (IdEObjectCollectionImpl) container;

		if (container.getModelElementId(notifierModelElement) != null
			|| containerImpl.getDeletedModelElementId(notifierModelElement) != null) {
			return false;
		}

		return true;
	}

}