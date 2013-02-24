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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Callback interface for implementors that are interested in influencing the
 * actual commit behavior.
 * 
 * @author ovonwesen
 * @author emueller
 */
public interface ESCommitCallback {

	/**
	 * <p>
	 * Called when the project that should be updated is out of date.
	 * </p>
	 * <p>
	 * A caller may veto against updating the project space by returning {@code false}.
	 * </p>
	 * 
	 * @param project
	 *            the project being out of date
	 * @param monitor
	 *            the currently used {@link IProgressMonitor}
	 * @return {@code true}, if the caller is willing to update the project space, {@code false} otherwise
	 */
	boolean baseVersionOutOfDate(ESLocalProject project, IProgressMonitor monitor);

	/**
	 * <p>
	 * Called right before the actual commit is performed.
	 * </p>
	 * <p>
	 * Implementors may veto against the commit by returning {@code false}.
	 * </p>
	 * 
	 * @param project
	 *            the project with the local pending changes
	 * @param changePackage
	 *            the actual changes that are up to be inspected
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects and vice versa.<br/>
	 *            Contains all IDs of model elements involved in the {@link ESChangePackage}s
	 *            as well as those contained by the project in the {@link ESLocalProject}
	 * @return {@code true}, if the commit should continue, {@code false} otherwise
	 */
	boolean inspectChanges(ESLocalProject project, ESChangePackage changePackage,
		ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping);

	/**
	 * Called when there are no changes on the given project space.
	 * 
	 * @param project
	 *            the project that has no local pending changes
	 */
	void noLocalChanges(ESLocalProject project);

	/**
	 * Called when the checksum computed for a local project differs from the one calculated on the server side.
	 * 
	 * @param project
	 *            the {@link ESLocalProject} containing the corrupted project
	 * @param versionSpec
	 *            the version specifier containing the correct checksum received from the server
	 * @param monitor
	 *            an {@link IProgressMonitor} to inform about the progress
	 * 
	 * @return whether the commit should be continued, {@code true}, if so, {@code false} otherwise
	 * 
	 * @throws ESException in case any error occurs during the execution of the checksum error handler
	 */
	boolean checksumCheckFailed(ESLocalProject project, ESPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
		throws ESException;

	/**
	 * <p>
	 * Default implementation of a callback interface for commit.
	 * </p>
	 * 
	 * <p>
	 * Does not veto against updating the project in case it is out of date and returns {@code true} for
	 * {@link #inspectChanges(ESLocalProject, ESChangePackage)}, such that a commit is always performed.
	 * </p>
	 */
	ESCommitCallback NOCALLBACK = new ESCommitCallback() {

		public boolean inspectChanges(ESLocalProject project, ESChangePackage changePackage,
			ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
			return true;
		}

		public boolean baseVersionOutOfDate(ESLocalProject project, IProgressMonitor progressMonitor) {
			return false;
		}

		public void noLocalChanges(ESLocalProject project) {
			// do nothing
		}

		public boolean checksumCheckFailed(ESLocalProject project, ESPrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) {
			return true;
		}
	};
}