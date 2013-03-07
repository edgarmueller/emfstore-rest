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
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for reverting any changes upon a {@link ProjectSpace}.
 * 
 * @author emueller
 * 
 */
public class UIRevertOperationController extends
		AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during the revert of the
	 *            operations
	 * @param projectSpace
	 *            the {@link ProjectSpace} upon which to revert operations
	 */
	public UIRevertOperationController(Shell shell, ESLocalProject projectSpace) {
		super(shell);
		this.projectSpace = ((ESLocalProjectImpl) projectSpace)
				.getInternalAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor progressMonitor) throws ESException {

		String message = "Do you really want to revert all your changes on project "
				+ projectSpace.getProjectName() + "?";

		if (confirm("Confirmation", message)) {
			progressMonitor.beginTask("Revert project...", 100);
			progressMonitor.worked(10);
			projectSpace.revert();
			MessageDialog.openInformation(getShell(), "Revert",
					"Reverted project ");
		}

		return null;
	}
}