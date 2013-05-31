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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Filters notifications which come from datatypes that should be ignored.
 * 
 * @author emueller
 */
public class IgnoreDatatypeFilter implements ESNotificationFilter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo)
	 */
	public boolean check(ESNotificationInfo notificationInfo, ESObjectContainer container) {

		Object newValue = notificationInfo.getNewValue();
		Object oldValue = notificationInfo.getOldValue();
		Object notifier = notificationInfo.getNotifier();
		boolean ignore = false;

		if (newValue != null && newValue instanceof EObject) {
			ignore = ModelUtil.isIgnoredDatatype((EObject) newValue);
		}

		if (oldValue != null && oldValue instanceof EObject) {
			ignore = ignore || ModelUtil.isIgnoredDatatype((EObject) oldValue);
		}

		if (notifier != null && notifier instanceof EObject) {
			ignore = ignore || ModelUtil.isIgnoredDatatype((EObject) notifier);
		}

		return ignore;
	}
}
