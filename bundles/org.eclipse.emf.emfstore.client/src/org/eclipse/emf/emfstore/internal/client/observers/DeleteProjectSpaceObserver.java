/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * Shterev
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.observers;

import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;

/**
 * Receives a notification when a project is deleted from the workspace.
 * 
 * @author Shterev
 */
public interface DeleteProjectSpaceObserver extends ESObserver {

	/**
	 * Notifies that the project space has been deleted. This is a <b>PRE-DELETE</b> event.
	 * 
	 * @param projectSpace the project space
	 */
	// TODO: OTS think about pre/post
	void projectSpaceDeleted(ProjectSpace projectSpace);
}
