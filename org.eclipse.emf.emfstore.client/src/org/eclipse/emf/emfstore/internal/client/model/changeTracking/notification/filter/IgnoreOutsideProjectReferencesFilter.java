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

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.emfstore.common.model.EObjectContainer;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Filter to ignore Reference Notifications to Elements outside of the project.
 * 
 * @author koegel
 */
public class IgnoreOutsideProjectReferencesFilter implements NotificationFilter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter.NotificationFilter#check(org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo,
	 *      org.eclipse.emf.emfstore.internal.common.model.internal.common.model.EObjectContainer)
	 */
	public boolean check(NotificationInfo notificationInfo, EObjectContainer container) {

		// if notification is from an element disconnected from the project?s containment tree we will not try to filter
		// since we cannot derive the project then
		if (container == null) {
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
			return checkNewValueList(notificationInfo, container);
			// notification is about removing elements => check removed elements
		} else if (notificationInfo.getOldValue() != null && notificationInfo.getOldValue() instanceof List) {
			return checkOldValueList(notificationInfo, container);
		} else {
			// check single reference notification
			return checkSingleReference(notificationInfo, container);
		}

	}

	private boolean checkSingleReference(NotificationInfo notificationInfo, EObjectContainer container) {
		// if new value is in project then do NOT filter
		if (notificationInfo.getOldValue() != null && !notificationInfo.isMoveEvent()
			&& isOrWasInProject(container, notificationInfo.getOldModelElementValue())) {
			return false;
		}
		// if old value is in project then do NOT filter
		if (notificationInfo.getNewValue() != null
			&& isOrWasInProject(container, notificationInfo.getNewModelElementValue())) {
			return false;
		}
		// neither old nor new value are in project => filter
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean checkOldValueList(NotificationInfo notificationInfo, EObjectContainer container) {
		for (EObject referencedElement : ((List<EObject>) notificationInfo.getOldValue())) {
			if (isOrWasInProject(container, referencedElement)) {
				return false;
			}
		}
		// all referenced elements are NOT in the project
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean checkNewValueList(NotificationInfo notificationInfo, EObjectContainer container) {
		for (EObject referencedElement : ((List<EObject>) notificationInfo.getNewValue())) {
			if (isOrWasInProject(container, referencedElement)) {
				return false;
			}
		}
		// all referenced elements are NOT in the project
		return true;
	}

	private boolean isOrWasInProject(EObjectContainer container, EObject referencedElement) {
		boolean b = container.contains(referencedElement)
			|| ((IdEObjectCollectionImpl) container).getDeletedModelElementId(referencedElement) != null;
		return b;
	}
}