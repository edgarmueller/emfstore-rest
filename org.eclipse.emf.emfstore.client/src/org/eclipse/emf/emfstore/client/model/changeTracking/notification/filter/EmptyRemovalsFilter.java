/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter;

import java.util.Collection;

import org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;

/**
 * This class filters zero effect remove operations from a notification recording. An example of a zero effect remove is
 * a notification that [] changed to null.
 * 
 * @author chodnick
 */
public class EmptyRemovalsFilter implements NotificationFilter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter.NotificationFilter#check(org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo)
	 */
	public boolean check(NotificationInfo notificationInfo, IdEObjectCollection collection) {

		return notificationInfo.isRemoveManyEvent() && notificationInfo.getNewValue() == null
			&& notificationInfo.getOldValue() instanceof Collection<?>
			&& ((Collection<?>) notificationInfo.getOldValue()).isEmpty();
	}
}
