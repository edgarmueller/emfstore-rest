/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.common.observer.IObserver;

/**
 * Listener for changes to the save state (project is fully saved or still dirty) of a project space.
 * 
 * @author mkoegel
 * 
 */
public interface SaveStateChangedObserver extends IObserver {

	/**
	 * Notify the listener about a save state change.
	 * 
	 * @param projectSpace the project space the notification is about
	 * @param hasUnsavedChangesNow the new save state, true if there are unsaved changes now
	 */
	void saveStateChanged(ProjectSpace projectSpace, boolean hasUnsavedChangesNow);
}
