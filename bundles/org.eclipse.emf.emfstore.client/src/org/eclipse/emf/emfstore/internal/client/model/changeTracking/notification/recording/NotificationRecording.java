/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;

/**
 * A NotificationRecording is basically a list of EMF Notifications.
 * 
 * @author chodnick
 */
public class NotificationRecording {

	private final List<NotificationInfo> chain = new LinkedList<NotificationInfo>();
	private NotificationRecordingHint hint;
	private Date date;

	/**
	 * @return currently set hint
	 */
	public NotificationRecordingHint getHint() {
		return hint;
	}

	/**
	 * @param hint the hint to set
	 */
	public void setHint(NotificationRecordingHint hint) {
		this.hint = hint;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param n the notification to add to the recording
	 */
	public void record(Notification n) {
		chain.add(new NotificationInfo(n));
	}

	/**
	 * @return the last recorded notification or null if the recording is empty
	 */
	public NotificationInfo getLastRecorded() {
		if (empty()) {
			return null;
		}
		return chain.get(chain.size() - 1);
	}

	/**
	 * @return if the recording is empty
	 */
	public boolean empty() {
		return chain.size() == 0;
	}

	/**
	 * Use to change the contents of the recording dynamically. For example in filters.
	 * 
	 * @return a mutable internal representation of the notifcations
	 */
	public List<NotificationInfo> asMutableList() {
		return chain;
	}
}
