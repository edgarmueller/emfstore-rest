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
package org.eclipse.emf.emfstore.client.callbacks;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESChangeConflictImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Callback interface for updating a project space.
 * 
 * @author ovonwesen
 * @author emueller
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
		ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping);

	/**
	 * Called when no remote changes are available.
	 */
	void noChangesOnServer();

	/**
	 * Called when local and remote changes overlap.
	 * 
	 * @param changeConflict
	 *            the {@link ESChangeConflictImpl} containing the changes that led to the conflict
	 * @param monitor
	 *            an {@link IProgressMonitor} to report on progress
	 * @return {@code true}, if the conflict has been resolved, {@code false} otherwise
	 */
	boolean conflictOccurred(ESChangeConflict changeConflict, IProgressMonitor monitor);

	/**
	 * Called when the checksum computed for a local project differs from the one calculated on the server side.
	 * 
	 * @param project
	 *            the {@link ESLocalProject} that is corrupt
	 * @param versionSpec
	 *            the version specifier containing the correct checksum received from the server
	 * @param monitor
	 *            an {@link IProgressMonitor} to report on progress
	 * 
	 * @return {@code true}, if the checksum error has been handled successfully, {@code false}
	 * 
	 * @throws ESException in case any error occurs during the execution of the checksum error handler
	 * 
	 */
	boolean checksumCheckFailed(ESLocalProject project, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor)
		throws ESException;

	/**
	 * A default implementation of an update callback that does nothing and defaults
	 * <<<<<<<
	 * HEAD:org.eclipse.emf.emfstore.client/src/org/eclipse/emf/emfstore/internal/client/model/controller/callbacks
	 * /IUpdateCallback.java {@link IUpdateCallback#conflictOccurred(ESChangeConflictImpl)} to false and
	 * {@link IUpdateCallback#inspectChanges(ESLocalProject, List)} to true.
	 * ======= {@link ESUpdateCallback#conflictOccurred(ESChangeConflict)} to false and
	 * {@link ESUpdateCallback#inspectChanges(ESLocalProject, List)} to true.
	 * >>>>>>>
	 * 897c2ca7d066fbf6e610eabfe0a600a2a4512500:org.eclipse.emf.emfstore.client/src/org/eclipse/emf/emfstore/client
	 * /callbacks/ESUpdateCallback.java
	 */
	ESUpdateCallback NOCALLBACK = new ESUpdateCallback() {

		public boolean inspectChanges(ESLocalProject projectSpace, List<ESChangePackage> changes,
			ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
			return true;
		}

		public void noChangesOnServer() {
		}

		public boolean conflictOccurred(ESChangeConflict changeConflict, IProgressMonitor progressMonitor) {
			return false;
		}

		public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) throws ESException {
			return true;
		}
	};
}