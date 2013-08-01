/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.emfstore.client.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Filter to ignore Reference Notifications to Elements outside of the project.
 * 
 * @author koegel
 */
public class IgnoreOutsideProjectReferencesFilter implements ESNotificationFilter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo,
	 *      org.eclipse.emf.emfstore.common.model.ESObjectContainer)
	 */
	@SuppressWarnings("rawtypes")
	public boolean check(ESNotificationInfo notificationInfo, ESObjectContainer<?> container) {

		// if notification is from an element disconnected from the project?s containment tree we will not try to filter
		// since we cannot derive the project then
		if (container == null) {
			return false;
		}

		// only filter reference notifications
		if (!notificationInfo.isReferenceNotification()) {
			return false;
		}

		// do not filter notifications on containment or container references
		final EReference reference = (EReference) notificationInfo.getFeature();
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
			if (!notificationInfo.wasSet() && ((List) notificationInfo.getOldValue()).size() == 0) {
				// do not filter remove notifications on empty list when unset before
				return false;
			}
			return checkOldValueList(notificationInfo, container);
		} else {
			if (!notificationInfo.wasSet()
				&& notificationInfo.getNewValue() == notificationInfo.getStructuralFeature().getDefaultValue()) {
				// do not filter notification when unset before and new value is null
				return false;
			}
			// check single reference notification
			return checkSingleReference(notificationInfo, container);
		}

	}

	private boolean checkSingleReference(ESNotificationInfo notificationInfo, ESObjectContainer<?> container) {
		if (notificationInfo.getEventType() == Notification.UNSET) {
			// do NOT filter unset notifications although old and new value are not in project
			return false;
		}

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
	private boolean checkOldValueList(ESNotificationInfo notificationInfo, ESObjectContainer<?> container) {
		for (final EObject referencedElement : (List<EObject>) notificationInfo.getOldValue()) {
			if (isOrWasInProject(container, referencedElement)) {
				return false;
			}
		}

		// all referenced elements are NOT in the project
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean checkNewValueList(ESNotificationInfo notificationInfo, ESObjectContainer<?> container) {
		for (final EObject referencedElement : (List<EObject>) notificationInfo.getNewValue()) {
			if (isOrWasInProject(container, referencedElement)) {
				return false;
			}
		}
		// all referenced elements are NOT in the project
		return true;
	}

	private boolean isOrWasInProject(ESObjectContainer<?> container, EObject referencedElement) {

		if (ModelUtil.isSingleton(referencedElement)) {
			return true;
		}

		final boolean b = container.contains(referencedElement)
			|| ((IdEObjectCollectionImpl) container).getDeletedModelElementId(referencedElement) != null;

		return b;
	}
}
