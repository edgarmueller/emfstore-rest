/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Slawomir Chodnicki - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.filter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.emfstore.client.handler.ESNotificationFilter;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPointException;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;

/**
 * This class filters a notification recording according to predefined stacks of
 * filters. Available stacks are created as static properties for easy
 * reference.
 * 
 * @author chodnick
 */
public final class FilterStack implements ESNotificationFilter {

	private static final String NOTIFICATION_FILTER_EXTENSION_ID = "org.eclipse.emf.emfstore.client.notificationFilter";

	private static final ESNotificationFilter[] DEFAULT_STACK = { new TouchFilter(), new TransientFilter(),
		new UnknownEventTypeFilter(), new EmptyRemovalsFilter(), new IgnoreDatatypeFilter(),
		new IgnoreOutsideProjectReferencesFilter(), new IgnoreNullFeatureNotificationsFilter(),
		new NotifiableIdEObjectCollectionFilter(), new IgnoreNotificationsOutsideProject() };

	/**
	 * The default filter stack.
	 */
	public static final ESNotificationFilter DEFAULT = new FilterStack(DEFAULT_STACK);

	private final List<ESNotificationFilter> filterList;

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
		final ESExtensionPoint extensionPoint = new ESExtensionPoint(NOTIFICATION_FILTER_EXTENSION_ID, true);
		for (final ESExtensionElement element : extensionPoint.getExtensionElements()) {
			try {
				filterList.add(element.getClass("class", ESNotificationFilter.class));
			} catch (final ESExtensionPointException e) {
				WorkspaceUtil.logException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.handler.ESNotificationFilter#check(org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo,
	 *      org.eclipse.emf.emfstore.common.model.ESObjectContainer)
	 */
	public boolean check(ESNotificationInfo notificationInfo, ESObjectContainer<?> container) {
		for (final ESNotificationFilter filter : filterList) {
			if (filter.check(notificationInfo, container)) {
				return true;
			}
		}
		return false;
	}

}
