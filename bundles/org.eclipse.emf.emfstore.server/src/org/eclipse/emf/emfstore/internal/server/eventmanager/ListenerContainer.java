/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.eventmanager;

import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.events.server.ServerEvent;
import org.eclipse.emf.emfstore.internal.server.model.versioning.events.server.ServerProjectEvent;

/**
 * Container for listener.
 * 
 * @author wesendon
 * @deprecated
 */
@Deprecated
public class ListenerContainer {

	private final EMFStoreEventListener listener;
	private final ProjectId projectId;

	/**
	 * Constructor.
	 * 
	 * @param listener the listener
	 * @param projectId the project id
	 */
	public ListenerContainer(EMFStoreEventListener listener, ProjectId projectId) {
		this.listener = listener;
		this.projectId = projectId;
	}

	/**
	 * Handle a given event.
	 * 
	 * @param event the event
	 * @return true if the listener wants to keep receiving events
	 */
	public boolean handleEvent(ServerEvent event) {
		if (projectId != null && event instanceof ServerProjectEvent) {
			if (!projectId.equals(((ServerProjectEvent) event).getProjectId())) {
				return true;
			}
		}
		return listener.handleEvent(event);
	}
}
