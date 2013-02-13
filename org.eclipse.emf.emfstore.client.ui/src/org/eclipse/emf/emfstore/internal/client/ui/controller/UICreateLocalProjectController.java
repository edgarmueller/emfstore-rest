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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.IProject;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.client.ui.views.emfstorebrowser.views.CreateProjectDialog;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for creating a local project.
 * 
 * @author emueller
 * 
 */
public class UICreateLocalProjectController extends AbstractEMFStoreUIController<IProject> {

	private final String name;
	private final String description;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the creation of the local project
	 */
	public UICreateLocalProjectController(Shell shell) {
		super(shell);
		name = null;
		description = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that should be used during the creation of the local project
	 * @param name
	 *            the name of the local project
	 * @param description
	 *            an optional description for the project. May be <code>null</code>
	 */
	public UICreateLocalProjectController(Shell shell, String name, String description) {
		super(shell);
		this.name = name;
		this.description = description == null ? "" : description;
	}

	private ESLocalProject createLocalProject() {
		CreateProjectDialog dialog = new CreateProjectDialog(getShell());
		if (dialog.open() == Dialog.OK) {
			String projectName = dialog.getName();
			String description = dialog.getDescription();

			return createLocalProject(projectName, description);
		}

		return null;
	}

	private ESLocalProject createLocalProject(final String name, final String description) {
		return WorkspaceProvider.getInstance().getWorkspace().createLocalProject(name, description);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ESLocalProject doRun(IProgressMonitor monitor) throws EMFStoreException {
		if (name == null) {
			return createLocalProject();
		}
		return createLocalProject(name, description);
	}
}