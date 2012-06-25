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
package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.RevertCommitController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for reverting changes on a {@link ProjectSpace}.
 * 
 * @author emueller
 * 
 */
public class UIRevertCommitController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;
	private final PrimaryVersionSpec versionSpec;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param projectSpace
	 *            the project upon which to revert changes
	 * @param versionSpec
	 *            the version to revert to
	 */
	public UIRevertCommitController(Shell shell, final ProjectSpace projectSpace, final PrimaryVersionSpec versionSpec) {
		super(shell);
		this.projectSpace = projectSpace;
		this.versionSpec = versionSpec;
	}

	@Override
	public Void doRun(IProgressMonitor monitor) {

		if (!confirm("Confirmation",
			"Do you really want to revert changes of this version on project " + projectSpace.getProjectName())) {
			return null;
		}

		try {
			new RevertCommitController(projectSpace, versionSpec).execute();
		} catch (EmfStoreException e) {
			MessageDialog
				.openError(getShell(), "Error", "An error occurred while revert the commit: " + e.getMessage());
		}
		return null;
	}
}
