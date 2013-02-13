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
package org.eclipse.emf.emfstore.internal.client.model.controller.callbacks;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.IChangeConflict;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.IChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * Callback interface for updating a project space.
 * 
 * @author ovonwesen
 * @author emueller
 */
public interface IUpdateCallback {

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
	boolean inspectChanges(ILocalProject project, List<? extends IChangePackage> changes,
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
	boolean conflictOccurred(IChangeConflict changeConflict, IProgressMonitor progressMonitor);

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
	 * @throws EMFStoreException in case any error occurs during the execution of the checksum error handler
	 * 
	 */
	boolean checksumCheckFailed(ILocalProject project, IPrimaryVersionSpec versionSpec, IProgressMonitor progressMonitor)
		throws EMFStoreException;

	/**
	 * A default implementation of an update callback that does nothing and default
	 * {@link IUpdateCallback#conflictOccurred(ChangeConflictException)} to false and
	 * {@link IUpdateCallback#inspectChanges(ProjectSpace, List)} to true.
	 */
	IUpdateCallback NOCALLBACK = new IUpdateCallback() {
		public boolean inspectChanges(ILocalProject projectSpace, List<? extends IChangePackage> changes,
			IModelElementIdToEObjectMapping idToEObjectMapping) {
			return true;
		}

		public void noChangesOnServer() {
		}

		public boolean conflictOccurred(IChangeConflict changeConflict, IProgressMonitor progressMonitor) {
			return false;
		}

		public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) throws EMFStoreException {
			return true;
		}
	};
}