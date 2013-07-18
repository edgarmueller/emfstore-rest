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
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.emfstore.client.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;

/**
 * A notification filter that filters all notifications that have an unknow type.
 * All notification with an event type >= {@link Notification#EVENT_TYPE_COUNT} are considered to be unknown.
 * 
 * @author emueller
 * 
 */
public class UnknownEventTypeFilter implements ESNotificationFilter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo,
	 *      org.eclipse.emf.emfstore.internal.common.model.ESObjectContainer.common.model.EObjectContainer)
	 */
	public boolean check(ESNotificationInfo notificationInfo, ESObjectContainer container) {
		return notificationInfo.getEventType() >= Notification.EVENT_TYPE_COUNT;
	}

}
