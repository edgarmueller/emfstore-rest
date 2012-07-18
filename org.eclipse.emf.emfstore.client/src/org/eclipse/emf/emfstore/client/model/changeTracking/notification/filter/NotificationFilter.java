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
package org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter;

import org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;

/**
 * Interface for filtering notifications.
 * 
 * @author chodnick
 * @author koegel
 */
public interface NotificationFilter {

	/**
	 * Check a notification if it should be ignored.
	 * 
	 * @param notificationInfo
	 *            the {@link NotificationInfo} to check
	 * @param collection
	 *            the collection that holds or did hold the model element the notification
	 *            is about
	 * @return true if the notification is to be ignored
	 */
	boolean check(NotificationInfo notificationInfo, IdEObjectCollection collection);

}
