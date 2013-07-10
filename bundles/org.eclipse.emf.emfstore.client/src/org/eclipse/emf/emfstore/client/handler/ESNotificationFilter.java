/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Slawomir Chodnicki, Maximilian Koegel - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.handler;

import org.eclipse.emf.emfstore.common.model.ESObjectContainer;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;

/**
 * Interface for filtering notifications.
 * 
 * @author chodnick
 * @author koegel
 */
public interface ESNotificationFilter {

	/**
	 * Check whether a notification should be ignored.
	 * 
	 * @param notificationInfo
	 *            the {@link ESNotificationInfo} to be checked
	 * @param container
	 *            the container that holds or did hold the model element the notification
	 *            is about
	 * @return {@code true} if the notification is to be ignored
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	boolean check(ESNotificationInfo notificationInfo, ESObjectContainer<?> container);

}
