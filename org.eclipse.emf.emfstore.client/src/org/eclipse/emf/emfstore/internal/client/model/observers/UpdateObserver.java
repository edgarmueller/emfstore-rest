/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.observers;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.api.ILocalProject;
import org.eclipse.emf.emfstore.internal.common.observer.IObserver;
import org.eclipse.emf.emfstore.server.model.api.IChangePackage;

/**
 * Notifies the UI that a list of changes will be automatically merged with the current model state.
 * 
 * @emueller
 * @ovonwesen
 */
public interface UpdateObserver extends IObserver {

	/**
	 * Called to notify the observer about the changes that will be merged into the project space.
	 * 
	 * @param project
	 *            the project that should be updated
	 * @param changePackages
	 *            a list of change packages containing the update changes
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 * @return false if the observer wants to cancel the update
	 */
	boolean inspectChanges(ILocalProject project, List<IChangePackage> changePackages, IProgressMonitor monitor);

	/**
	 * Called after the changes have been applied to the project and the update is completed.
	 * 
	 * @param project
	 *            the project whose update has been completed
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 */
	void updateCompleted(ILocalProject project, IProgressMonitor monitor);

}