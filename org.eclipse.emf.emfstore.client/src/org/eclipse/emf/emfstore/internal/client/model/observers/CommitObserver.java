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
package org.eclipse.emf.emfstore.internal.client.model.observers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.common.IObserver;
import org.eclipse.emf.emfstore.server.model.IChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * An observer which waits for commit notifications and authorizes the commit procedure.
 * 
 * @author shterev
 * @author emueller
 */
public interface CommitObserver extends IObserver {

	/**
	 * Called before the commit proceeds. A callback method to initiate the commit dialog and allow the user to confirm
	 * the changes.
	 * 
	 * @param project
	 *            the project the commit occurs on
	 * @param changePackage
	 *            the {@link IChangePackage}
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that may be used by clients to inform
	 *            about progress
	 * @return true if the changes have been confirmed, false - otherwise.
	 */
	boolean inspectChanges(ILocalProject project, IChangePackage changePackage, IProgressMonitor monitor);

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
	void commitCompleted(ILocalProject project, IPrimaryVersionSpec newRevision, IProgressMonitor monitor);
}