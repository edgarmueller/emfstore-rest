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
import org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

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
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects and vice versa. Contains
	 *            all IDs of model elements involved in the {@link ChangePackage}s
	 *            as well as those contained by the project in the {@link ProjectSpace}
	 * @return true, if the changes should get applied upon the project space, false otherwise
	 */
	boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes,
		IModelElementIdToEObjectMapping idToEObjectMapping);

	/**
	 * Called when no remote changes are available.
	 */
	void noChangesOnServer();

	/**
	 * Called when local and remote changes overlap.
	 * 
	 * @param changeConflictException
	 *            the exception that caused the conflict between the local and the remote changes
	 * @param progressMonitor a progress monitor to report on progress
	 * @return true, if the conflict has been resolved, false otherwise
	 */
	boolean conflictOccurred(ChangeConflictException changeConflictException, IProgressMonitor progressMonitor);

	/**
	 * Called when the checksum computed for a local project differs from the one calculated on the server side.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} containing the corrupted project
	 * @param versionSpec
	 *            the version spec containing the correct checksum received from the server
	 * @param progressMonitor
	 *            an {@link IProgressMonitor} to report on progress
	 * 
	 * @return whether the checksum error has been handled successfully
	 * 
	 * @throws EmfStoreException in case any error occurs during the execution of the checksum error handler
	 * 
	 */
	boolean checksumCheckFailed(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec,
		IProgressMonitor progressMonitor) throws EmfStoreException;

	/**
	 * A default implementation of an update callback that does nothing and default
	 * {@link UpdateCallback#conflictOccurred(ChangeConflictException)} to false and
	 * {@link UpdateCallback#inspectChanges(ProjectSpace, List)} to true.
	 */
	UpdateCallback NOCALLBACK = new UpdateCallback() {
		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes,
			IModelElementIdToEObjectMapping idToEObjectMapping) {
			return true;
		}

		public void noChangesOnServer() {
		}

		public boolean conflictOccurred(ChangeConflictException changeConflictException,
			IProgressMonitor progressMonitor) {
			return false;
		}

		public boolean checksumCheckFailed(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor)
			throws EmfStoreException {
			return true;
		}
	};
}