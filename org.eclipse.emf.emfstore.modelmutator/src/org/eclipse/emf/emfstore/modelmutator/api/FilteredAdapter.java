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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentAdapter;

/**
 * Adapter to log changes of model in modelmutator. Also useful to filter notifications.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public abstract class FilteredAdapter extends EContentAdapter {

	private static final String E_CLASS_SUFFIX = "Impl";
	private List<EClass> toLogClasses;
	private List<EReference> toLogReferences;
	private boolean attributes;
	private boolean references;

	/**
	 * @param toLogClasses The {@link EClass}es to log. If <code>null</code> every {@link EClass} is logged.
	 * @param toLogReferences The {@link EReference}es of the toLogClasses to log. If <code>null</code> every
	 *            {@link EReference} is logged.
	 * @param references Log reference changes?
	 * @param attributes Log attribute changes?
	 */
	public FilteredAdapter(List<EClass> toLogClasses, List<EReference> toLogReferences, boolean references,
		boolean attributes) {
		this.toLogClasses = toLogClasses;
		this.toLogReferences = toLogReferences;
		this.references = references;
		this.attributes = attributes;
	}

	/**
	 * @param notification The {@link Notification} to check.
	 * @return Filter a {@link Notification}?
	 */
	protected boolean filter(Notification notification) {
		// ignore if it is an attribute change and attributes should not be logged
		if (!attributes && notification.getFeature() instanceof EAttribute) {
			return true;
		}

		// ignore if it is a reference change and references should not be logged
		if (!references && notification.getFeature() instanceof EReference) {
			return true;
		}

		// if no log classes are configured ignore nothing
		if (toLogClasses == null) {
			return false;
		}

		// check if the notification message contains a class which should be logged
		for (EClass eClass : toLogClasses) {
			String string = notification.toString();
			if (string.contains("." + eClass.getName() + E_CLASS_SUFFIX)) {
				if (toLogReferences == null) {
					return false;
				} else {
					for (EReference ref : toLogReferences) {
						if (string.contains(ref.getName())) {
							return false;
						}
					}
				}
			}
		}

		// ignore
		return true;
	}

	/**
	 * Convert an int eventType of a notification to a {@link String}.
	 * 
	 * @param eventType The eventType to convert.
	 * @return The {@link String} representing the eventType.
	 */
	public static String getEventType(int eventType) {
		switch (eventType) {
			case Notification.SET: {
				return "SET";
			}
			case Notification.UNSET: {
				return "UNSET";
			}
			case Notification.ADD: {
				return "ADD";
			}
			case Notification.ADD_MANY: {
				return "ADD_MANY";
			}
			case Notification.REMOVE: {
				return "REMOVE";
			}
			case Notification.REMOVE_MANY: {
				return "REMOVE_MANY";
			}
			case Notification.MOVE: {
				return "MOVE";
			}
			case Notification.REMOVING_ADAPTER: {
				return "REMOVING_ADAPTER";
			}
			case Notification.RESOLVE: {
				return "RESOLVE";
			}
			default: {
				return String.valueOf(eventType);
			}
		}
	}
}