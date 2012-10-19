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

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;

/**
 * Filter to ignore Reference Notifications to Elements outside of the project.
 * 
 * @author koegel
 */
public class IgnoreOutsideProjectReferencesFilter implements NotificationFilter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.notification.filter.NotificationFilter#check(org.eclipse.emf.emfstore.client.model.changeTracking.notification.NotificationInfo)
	 */
	public boolean check(NotificationInfo notificationInfo, IdEObjectCollection collection) {

		// if notification is from an element disconnected from the project�s containment tree we will not try to filter
		// since we cannot derive the project then
		if (collection == null) {
			return false;
		}

		// only filter reference notifications
		if (!notificationInfo.isReferenceNotification()) {
			return false;
		}

		// do not filter notificatiosn on containment or container references
		EReference reference = (EReference) notificationInfo.getFeature();
		if (reference.isContainer() || reference.isContainment()) {
			return false;
		}

		// we have a cross-reference feature notification
		if (notificationInfo.getEventType() == Notification.MOVE) {
			return ModelUtil.getProject(notificationInfo.getNewModelElementValue()) == null;
		}

		// notification is about adding elements => check added elements
		if (notificationInfo.getNewValue() != null && notificationInfo.getNewValue() instanceof List) {
			return checkNewValueList(notificationInfo, collection);
			// notification is about removing elements => check removed elements
		} else if (notificationInfo.getOldValue() != null && notificationInfo.getOldValue() instanceof List) {
			return checkOldValueList(notificationInfo, collection);
		} else {
			// check single reference notification
			return checkSingleReference(notificationInfo, collection);
		}

	}

	private boolean checkSingleReference(NotificationInfo notificationInfo, IdEObjectCollection collection) {
		// if new value is in project then do NOT filter
		if (notificationInfo.getOldValue() != null && !notificationInfo.isMoveEvent()
			&& isOrWasInProject(collection, notificationInfo.getOldModelElementValue())) {
			return false;
		}
		// if old value is in project then do NOT filter
		if (notificationInfo.getNewValue() != null
			&& isOrWasInProject(collection, notificationInfo.getNewModelElementValue())) {
			return false;
		}
		// neither old nor new value are in project => filter
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean checkOldValueList(NotificationInfo notificationInfo, IdEObjectCollection project) {
		for (EObject referencedElement : ((List<EObject>) notificationInfo.getOldValue())) {
			if (isOrWasInProject(project, referencedElement)) {
				return false;
			}
		}
		// all referenced elements are NOT in the project
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean checkNewValueList(NotificationInfo notificationInfo, IdEObjectCollection project) {
		for (EObject referencedElement : ((List<EObject>) notificationInfo.getNewValue())) {
			if (isOrWasInProject(project, referencedElement)) {
				return false;
			}
		}
		// all referenced elements are NOT in the project
		return true;
	}

	private boolean isOrWasInProject(IdEObjectCollection collection, EObject referencedElement) {
		boolean b = collection.containsInstance(referencedElement)
			|| ((IdEObjectCollectionImpl) collection).getDeletedModelElementId(referencedElement) != null;
		return b;
	}
}
