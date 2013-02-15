/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author Edgar
 * 
 */
public class UIUndoLastOperationController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that will be used during the reversal of the operation
	 * @param projectSpace
	 *            the {@link ProjectSpace} upon which to reverse the last operation
	 */
	public UIUndoLastOperationController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor progressMonitor) throws ESException {
		projectSpace.getOperationManager().undoLastOperation();
		return null;
	}

}