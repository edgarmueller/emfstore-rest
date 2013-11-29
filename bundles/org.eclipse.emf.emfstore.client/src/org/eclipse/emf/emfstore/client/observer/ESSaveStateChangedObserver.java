/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Listener for changes to the save state (project is fully saved or still dirty) of a {@link ESLocalProject}.
 * 
 * @author mkoegel
 * 
 */
public interface ESSaveStateChangedObserver extends ESObserver {

	/**
	 * Notify the listener about a save state change.
	 * 
	 * @param localProject
	 *            the {@link ESLocalProject} the notification is about
	 * @param hasUnsavedChangesNow
	 *            the new save state, {@code true} if there are unsaved changes now, {@code false} otherwise
	 */
	void saveStateChanged(ESLocalProject localProject, boolean hasUnsavedChangesNow);
}
