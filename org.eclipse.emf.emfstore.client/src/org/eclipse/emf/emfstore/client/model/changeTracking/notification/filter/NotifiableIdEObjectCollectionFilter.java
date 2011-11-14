package org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter;

import org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.common.model.NotifiableIdEObjectCollection;

public class NotifiableIdEObjectCollectionFilter implements NotificationFilter {

	public boolean check(NotificationInfo notificationInfo) {
		return (notificationInfo.getNotifier() instanceof NotifiableIdEObjectCollection);
	}

}
