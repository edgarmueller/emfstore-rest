/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Aleksandar Shterev
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * An observer which waits for commit notifications and authorizes the commit procedure.
 * 
 * @author shterev
 * @author emueller
 */
public interface ESCommitObserver extends ESObserver {

	/**
	 * Called before the commit proceeds. A callback method to initiate the commit dialog and allow the user to confirm
	 * the changes.
	 * 
	 * @param project
	 *            the project the commit occurs on
	 * @param changePackage
	 *            the {@link ESChangePackage}
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 * @return true if the changes have been confirmed, false - otherwise.
	 */
	boolean inspectChanges(ESLocalProject project, ESChangePackage changePackage, IProgressMonitor monitor);

	/**
	 * Called after the commit is completed.
	 * 
	 * @param project
	 *            the project on which the commit has completed
	 * @param newRevision
	 *            the new revision that was created by the commit
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 */
	void commitCompleted(ESLocalProject project, ESPrimaryVersionSpec newRevision, IProgressMonitor monitor);
}