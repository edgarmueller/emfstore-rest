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
package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

/**
 * Callback interface for updating a project space.
 * 
 * @author ovonwesen
 * @author emueller
 */
public interface UpdateCallback {

	/**
	 * Called right before the changes get applied upon the project space.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} being updated
	 * @param changes
	 *            the changes that will get applied upon the project space
	 * @return true, if the changes should get applied upon the project space, false otherwise
	 */
	boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes);

	/**
	 * Called when no remote changes are available.
	 */
	void noChangesOnServer();

	/**
	 * Called when local and remote changes overlap.
	 * 
	 * @param changeConflictException
	 *            the exception that caused the conflict between the local and the remote changes
	 * @return true, if the conflict has been resolved, false otherwise
	 */
	boolean conflictOccurred(ChangeConflictException changeConflictException, IProgressMonitor progressMonitor);

	/**
	 * A default implementation of an update callback that does nothing and default
	 * {@link UpdateCallback#conflictOccurred(ChangeConflictException)} to false and
	 * {@link UpdateCallback#inspectChanges(ProjectSpace, List)} to true.
	 */
	UpdateCallback NOCALLBACK = new UpdateCallback() {
		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes) {
			return true;
		}

		public void noChangesOnServer() {
		}

		public boolean conflictOccurred(ChangeConflictException changeConflictException,
			IProgressMonitor progressMonitor) {
			return false;
		}
	};
}
