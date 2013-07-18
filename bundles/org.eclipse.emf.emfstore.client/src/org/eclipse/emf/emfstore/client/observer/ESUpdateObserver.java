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
package org.eclipse.emf.emfstore.client.observer;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;

/**
 * Notifies the UI that a list of changes will be automatically merged with the current model state.
 * 
 * @emueller
 * @ovonwesen
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESUpdateObserver extends ESObserver {

	/**
	 * Called to notify the observer about the changes that will be merged into the project space.
	 * 
	 * @param project
	 *            the {@link ESLocalProject} that should be updated
	 * @param changePackages
	 *            a list of {@link ESChangePackage}s containing the update changes
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 * @return {@code false} if the observer wants to cancel the update, {@code true} otherwise
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changePackages, IProgressMonitor monitor);

	/**
	 * Called after the changes have been applied to the project and the update is completed.
	 * 
	 * @param project
	 *            the {@link ESLocalProject} whose update has been completed
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void updateCompleted(ESLocalProject project, IProgressMonitor monitor);

}