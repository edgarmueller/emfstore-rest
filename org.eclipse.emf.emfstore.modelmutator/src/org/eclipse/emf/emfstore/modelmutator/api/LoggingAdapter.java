/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.modelmutator.api;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * Basic extension of {@link FilteredAdapter} to print out the notifications.
 * 
 * @author Julian Sommerfeldt
 *
 */
public class LoggingAdapter extends FilteredAdapter {
	
	/**
	 * @param toLogClasses The {@link EClass}es to log. If <code>null</code> every {@link EClass} is logged.
	 * @param toLogReferences The {@link EReference}es of the toLogClasses to log. If <code>null</code> every {@link EReference} is logged.
	 * @param references Log reference changes?
	 * @param attributes Log attribute changes?
	 */
	public LoggingAdapter(List<EClass> toLogClasses, List<EReference> toLogReferences, boolean references, boolean attributes) {
		super(toLogClasses, toLogReferences, references, attributes);
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		if(filter(notification)){
			return;
		}
		
		System.out.println("NOTIFY:");
		System.out.println(format(notification));
		System.out.println();
	}
	
	/**
	 * Convert a {@link Notification} to a string.
	 * 
	 * @param notification The {@link Notification} to convert.
	 * @return The string representing the {@link Notification}.
	 */
	protected String format(Notification notification){
		StringBuffer result = new StringBuffer();
		result.append("Notifier:  " + notification.getNotifier());
		result.append("\n");
		result.append("Feature:   " + notification.getFeature());
		result.append("\n");
		result.append("Position:  " + notification.getPosition());
		result.append("\n");
		result.append("EventType: " + getEventType(notification.getEventType()));
		result.append("\n");
		result.append("OldValue:  " + notification.getOldValue());
		result.append("\n");
		result.append("NewValue:  " + notification.getNewValue());
		result.append("\n");
		return result.toString();
	}
}