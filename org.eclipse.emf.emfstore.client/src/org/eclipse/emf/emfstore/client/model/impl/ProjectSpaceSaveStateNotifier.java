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
package org.eclipse.emf.emfstore.client.model.impl;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.SaveStateChangedObserver;

/**
 * Notifies SaveStateChangedObservers about changes of the project space save state.
 * 
 * @author User
 * 
 */
public class ProjectSpaceSaveStateNotifier implements IDEObjectCollectionDirtyStateListener {

	private final ProjectSpaceBase projectSpace;

	/**
	 * Default constructor.
	 * 
	 * @param projectSpace the project space to notify for
	 */
	public ProjectSpaceSaveStateNotifier(ProjectSpaceBase projectSpace) {
		this.projectSpace = projectSpace;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.IDEObjectCollectionDirtyStateListener#notifyAboutDirtyStateChange()
	 */
	public void notifyAboutDirtyStateChange() {
		WorkspaceManager.getObserverBus().notify(SaveStateChangedObserver.class)
			.saveStateChanged(projectSpace, projectSpace.hasUnsavedChanges());
	}

}
