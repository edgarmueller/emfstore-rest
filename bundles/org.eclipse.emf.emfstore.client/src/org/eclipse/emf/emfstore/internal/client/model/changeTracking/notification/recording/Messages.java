/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording;

import org.eclipse.osgi.util.NLS;

/**
 * Recording related messages.
 * 
 * @author emueller
 * 
 */
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording.messages"; //$NON-NLS-1$
	public static String NotificationRecorder_Empty_Recording;
	public static String NotificationRecorder_Notification_Followups_Available;
	public static String NotificationRecorder_Uncomplete_Chain_Present;
	public static String NotificationRecorder_Unfinished_Recording;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
