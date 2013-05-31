/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Zardosth Hodaie
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.observers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.observer.ESCommitObserver;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Clients (like GUI classes) who are not interested if an operation has been run forward or backward (i.e. undone) can
 * use this add this listener to project space.
 * 
 * @author hodaie
 */
public abstract class SimpleOperationObserver implements OperationObserver, ESCommitObserver {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESCommitObserver#commitCompleted(org.eclipse.emf.emfstore.client.ESLocalProject,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void commitCompleted(ESLocalProject localProject, ESPrimaryVersionSpec newRevision,
		IProgressMonitor monitor) {
		operationPerformed(null);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESCommitObserver#inspectChanges(org.eclipse.emf.emfstore.client.ESLocalProject,
	 *      org.eclipse.emf.emfstore.server.model.ESChangePackage, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean inspectChanges(ESLocalProject localProject, ESChangePackage changePackage,
		IProgressMonitor monitor) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.observers.OperationObserver#operationExecuted(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public void operationExecuted(AbstractOperation operation) {
		operationPerformed(operation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.observers.OperationObserver#operationUndone(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public void operationUndone(AbstractOperation operation) {
		operationPerformed(operation.reverse());
	}

	/**
	 * Called when an operation has been executed, either forward or backwards (i.e. undo).
	 * 
	 * @param operation
	 *            the executed operation
	 */
	public abstract void operationPerformed(AbstractOperation operation);

}