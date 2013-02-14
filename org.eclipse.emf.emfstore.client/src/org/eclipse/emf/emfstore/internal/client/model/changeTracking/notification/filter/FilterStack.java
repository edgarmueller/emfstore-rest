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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPointException;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;

/**
 * This class filters a notification recording according to predefined stacks of
 * filters. Available stacks are created as static properties for easy
 * reference.
 * 
 * @author chodnick
 */
public final class FilterStack implements ESNotificationFilter {

	private static final ESNotificationFilter[] DEFAULT_STACK = { new TouchFilter(), new TransientFilter(),
		new UnknownEventTypeFilter(), new EmptyRemovalsFilter(), new IgnoreDatatypeFilter(),
		new IgnoreOutsideProjectReferencesFilter(), new IgnoreNullFeatureNotificationsFilter(),
		new NotifiableIdEObjectCollectionFilter(), new IgnoreNotificationsOutsideProject() };

	/**
	 * The default filter stack.
	 */
	public static final ESNotificationFilter DEFAULT = new FilterStack(DEFAULT_STACK);

	private List<ESNotificationFilter> filterList;

	/**
	 * Constructor.
	 * 
	 * @param filters the filter the filter stack should consists of.
	 */
	public FilterStack(ESNotificationFilter[] filters) {
		filterList = new LinkedList<ESNotificationFilter>();
		Collections.addAll(filterList, filters);
		collectExtensionPoints();

	}

	private void collectExtensionPoints() {
		for (ExtensionElement element : new ExtensionPoint(
			"org.eclipse.emf.emfstore.client.notificationFilter", true)
			.getExtensionElements()) {
			try {
				filterList.add(element.getClass("class", ESNotificationFilter.class));
			} catch (ExtensionPointException e) {
				WorkspaceUtil.logException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.NotificationInfo,
	 *      org.eclipse.emf.emfstore.internal.common.model.ESObjectContainer.common.model.EObjectContainer)
	 */
	public boolean check(NotificationInfo notificationInfo, ESObjectContainer container) {
		for (ESNotificationFilter f : filterList) {
			if (f.check(notificationInfo, container)) {
				return true;
			}
		}
		return false;
	}

}