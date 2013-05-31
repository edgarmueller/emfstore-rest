/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf.test;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.internal.modelmutator.api.LoggingAdapter;

/**
 * Extends the {@link LoggingAdapter} to print out {@link ModelElementId}s.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class ProjectLoggingAdapter extends LoggingAdapter {

	private ProjectImpl projectImpl;

	/**
	 * @param project The project containing the elements notifying.
	 * @param toLogClasses The {@link EClass}es to log. If <code>null</code> every {@link EClass} is logged.
	 * @param toLogReferences The {@link EReference}es of the toLogClasses to log. If <code>null</code> every
	 *            {@link EReference} is logged.
	 * @param references Log reference changes?
	 * @param attributes Log attribute changes?
	 */
	public ProjectLoggingAdapter(ProjectImpl project, List<EClass> toLogClasses, List<EReference> toLogReferences,
		boolean references, boolean attributes) {
		super(toLogClasses, toLogReferences, references, attributes);
		this.projectImpl = project;
	}

	@Override
	protected String format(Notification notification) {
		StringBuffer result = new StringBuffer();
		result.append("Notifier:  " + getModelElementId(projectImpl, notification.getNotifier()) + " "
			+ notification.getNotifier());
		result.append("\n");
		result.append("Feature:   " + notification.getFeature());
		result.append("\n");
		result.append("Position:  " + notification.getPosition());
		result.append("\n");
		result.append("EventType: " + getEventType(notification.getEventType()));
		result.append("\n");
		result.append("OldValue:  " + getModelElementId(projectImpl, notification.getOldValue()) + " "
			+ notification.getOldValue());
		result.append("\n");
		result.append("NewValue:  " + getModelElementId(projectImpl, notification.getNewValue()) + " "
			+ notification.getNewValue());
		result.append("\n");
		return result.toString();
	}

	private String getModelElementId(ProjectImpl project, Object o) {
		if (o == null) {
			return null;
		}

		if (!(o instanceof EObject)) {
			return null;
		}

		EObject eObject = (EObject) o;
		ModelElementId modelElementId = project.getModelElementId(eObject);
		if (modelElementId != null) {
			return modelElementId.getId();
		}
		ModelElementId deletedModelElementId = project.getDeletedModelElementId(eObject);
		return deletedModelElementId != null ? deletedModelElementId.getId() : null;
	}
}
