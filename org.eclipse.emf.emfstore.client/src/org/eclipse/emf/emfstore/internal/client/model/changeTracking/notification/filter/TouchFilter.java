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
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter;

import org.eclipse.emf.emfstore.client.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo;

/**
 * Filters touch notifications, as these have no effect on the model state.
 * 
 * @author chodnick
 */
public class TouchFilter implements ESNotificationFilter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo,
	 *      org.eclipse.emf.emfstore.internal.common.model.ESObjectContainer.common.model.EObjectContainer)
	 */
	public boolean check(NotificationInfo notificationInfo, ESObjectContainer container) {

		return notificationInfo.isTouch();
	}

}