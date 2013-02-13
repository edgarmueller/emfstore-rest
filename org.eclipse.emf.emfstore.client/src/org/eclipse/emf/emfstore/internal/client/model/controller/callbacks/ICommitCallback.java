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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.IChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * Callback interface for implementors that are interested in influencing the
 * actual commit behavior.
 * 
 * @author ovonwesen
 * @author emueller
 */
public interface ICommitCallback {

	/**
	 * Called when the project space that should be updated is out of date. A
	 * caller may veto against updating the project space by returning false.
	 * 
	 * @param projectSpace
	 *            the project space being out of date
	 * @param progressMonitor the currently used {@link IProgressMonitor}
	 * @return true, if the caller is willing to update the project space, false
	 *         otherwise
	 */
	boolean baseVersionOutOfDate(ILocalProject project, IProgressMonitor progressMonitor);

	/**
	 * Called right before the actual commit is performed. Implementors may veto
	 * against the commit by returning false
	 * 
	 * @param projectSpace
	 *            the project space with the local pending changes
	 * @param changePackage
	 *            the actual changes
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects and vice versa.<br/>
	 *            Contains all IDs of model elements involved in the {@link ChangePackage}s
	 *            as well as those contained by the project in the {@link ProjectSpace}
	 * @return true, if the commit should continue, false otherwise
	 */
	boolean inspectChanges(ILocalProject project, IChangePackage changePackage,
		IModelElementIdToEObjectMapping idToEObjectMapping);

	/**
	 * Called when there are no changes on the given project space.
	 * 
	 * @param projectSpace
	 *            the project space that has no local pending changes
	 */
	void noLocalChanges(ILocalProject projectSpace);

	/**
	 * Called when the checksum computed for a local project differs from the one calculated on the server side.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} containing the corrupted project
	 * @param versionSpec
	 *            the version spec containing the correct checksum received from the server
	 * @param monitor
	 *            an {@link IProgressMonitor} to inform about the progress
	 * 
	 * @return whether the commit should be continued, true, if so, false otherwise
	 * 
	 * @throws EMFStoreException in case any error occurs during the execution of the checksum error handler
	 * 
	 */
	boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
		throws EMFStoreException;

	/**
	 * Default implementation of a callback interface for commit. Does not veto
	 * against updating the project space in case it is out of date and returns
	 * true for {@link #inspectChanges(ProjectSpace, ChangePackage)}, such that
	 * a commit is always performed.
	 */
	ICommitCallback NOCALLBACK = new ICommitCallback() {

		public boolean inspectChanges(ILocalProject projectSpace, IChangePackage changePackage,
			IModelElementIdToEObjectMapping idToEObjectMapping) {
			return true;
		}

		public boolean baseVersionOutOfDate(ILocalProject projectSpace, IProgressMonitor progressMonitor) {
			return false;
		}

		public void noLocalChanges(ILocalProject projectSpace) {
			// do nothing
		}

		public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) {
			return true;
		}
	};
}