/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 * Otto von Wesendonk
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter;

import org.eclipse.emf.emfstore.common.model.EObjectContainer;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo;

/**
 * Filter to ignore notifications with the illegal state of having no value for
 * the feature member.
 * 
 * @author mkoegel
 * 
 */
public class IgnoreNullFeatureNotificationsFilter implements NotificationFilter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.internal.client.model.changeTracking.notification.filter.NotificationFilter#check(org.eclipse.emf.emfstore.internal.internal.client.model.changeTracking.notification.NotificationInfo,
	 *      org.eclipse.emf.emfstore.internal.internal.common.model.EObjectContainer)
	 */
	public boolean check(NotificationInfo notificationInfo, EObjectContainer container) {
		return (notificationInfo.getFeature() == null);
	}

}