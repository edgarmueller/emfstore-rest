/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.callbacks;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.ESConflictSet;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;

/**
 * Callback interface for updating a {@link ESLocalProject}.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESUpdateCallback {

	/**
	 * Called right before the changes get applied upon the project space.
	 * 
	 * @param project
	 *            the {@link ESLocalProject} being updated
	 * @param changes
	 *            a list of {@link ESChangePackage}s that will get applied upon the project
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects and vice versa. Contains
	 *            all IDs of model elements involved in the {@link ESChangePackage}s
	 *            as well as those contained by the project in the {@link ESLocalProject}
	 * @return true, if the changes should get applied upon the project space, false otherwise
	 */
	boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changes,
		ESModelElementIdToEObjectMapping idToEObjectMapping);

	/**
	 * Called when no remote changes are available.
	 */
	void noChangesOnServer();

	/**
	 * Called when local and remote changes overlap.
	 * 
	 * @param changeConflict
	 *            the {@link org.eclipse.emf.emfstore.internal.server.impl.api.ESConflictSetImpl} containing
	 *            the changes that led to the conflict
	 * @param monitor
	 *            an {@link IProgressMonitor} to report on progress
	 * @return {@code true}, if the conflict has been resolved, {@code false} otherwise
	 */
	boolean conflictOccurred(ESConflictSet changeConflict, IProgressMonitor monitor);

	/**
	 * A default implementation of an update callback that does nothing and defaults {@link
	 * this#conflictOccurred(ESChangeConflict, IProgressMonitor)} to {@code false} and {@link
	 * this#checksumCheckFailed(ESLocalProject, ESPrimaryVersionSpec, IProgressMonitor)} to {@code true}.
	 */
	ESUpdateCallback NOCALLBACK = new ESUpdateCallback() {

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#inspectChanges(org.eclipse.emf.emfstore.client.ESLocalProject,
		 *      java.util.List, org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping)
		 */
		public boolean inspectChanges(ESLocalProject projectSpace, List<ESChangePackage> changes,
			ESModelElementIdToEObjectMapping idToEObjectMapping) {
			return true;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#noChangesOnServer()
		 */
		public void noChangesOnServer() {
			// do nothing
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback#conflictOccurred(org.eclipse.emf.emfstore.server.ESConflictSet,
		 *      org.eclipse.core.runtime.IProgressMonitor)
		 */
		public boolean conflictOccurred(ESConflictSet changeConflict, IProgressMonitor progressMonitor) {
			return false;
		}
	};
}
