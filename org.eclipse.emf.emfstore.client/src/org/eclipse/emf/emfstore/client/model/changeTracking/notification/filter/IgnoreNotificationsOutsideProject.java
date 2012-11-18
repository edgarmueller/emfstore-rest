/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter;

import org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.impl.IdEObjectCollectionImpl;

/**
 * Filter notifications from elements outside of the project.
 * 
 */
public class IgnoreNotificationsOutsideProject implements NotificationFilter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter.NotificationFilter#check(org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo)
	 */
	public boolean check(NotificationInfo notificationInfo, IdEObjectCollection collection) {

		// do not filter notifications from (deleted) elements in project
		if (collection.getModelElementId(notificationInfo.getNotifierModelElement()) != null
			|| ((IdEObjectCollectionImpl) collection).getDeletedModelElementId(notificationInfo
				.getNotifierModelElement()) != null) {
			return false;
		}

		return true;
	}

}