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
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThreadWithResult;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.CreateProjectDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for creating a remote project.
 * 
 * @author emueller
 * 
 */
public class UICreateRemoteProjectController extends AbstractEMFStoreUIController<ProjectInfo> {

	private Usersession session;
	private final String projectName;
	private String description;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during the creation of the remote project
	 */
	public UICreateRemoteProjectController(Shell shell) {
		super(shell, true, false);
		session = null;
		projectName = null;
		description = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during the creation of the remote project
	 * @param session
	 *            the session to be used to create the project
	 */
	public UICreateRemoteProjectController(Shell shell, Usersession session) {
		super(shell);
		this.session = session;
		projectName = null;
		description = "";
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during the creation of the remote project
	 * @param session
	 *            the session to be used to create the project
	 * @param projectName
	 *            the name of the project to be created
	 * @param description
	 *            an optional description of the project. May be <code>null</code>
	 */
	public UICreateRemoteProjectController(Shell shell, Usersession session, String projectName, String description) {
		super(shell);
		this.session = session;
		this.projectName = projectName;
		this.description = description == null ? "" : description;
	}

	private ProjectInfo createRemoteProject(IProgressMonitor monitor) throws EmfStoreException {
		return createRemoteProject(monitor);
	}

	private ProjectInfo createRemoteProject(Usersession usersession, IProgressMonitor monitor) throws EmfStoreException {
		String[] ret = new RunInUIThreadWithResult<String[]>(getShell()) {

			@Override
			public String[] doRun(Shell shell) {
				CreateProjectDialog dialog = new CreateProjectDialog(getShell());
				if (dialog.open() == Dialog.OK) {
					return new String[] { dialog.getName(), dialog.getDescription() };
				}
				return null;
			}

		}.execute();

		String projectName = ret[0];
		String description = ret[1];

		return createRemoteProject(usersession, projectName, description, monitor);
	}

	private ProjectInfo createRemoteProject(final Usersession usersession, final String name, final String description,
		IProgressMonitor monitor) throws EmfStoreException {
		return WorkspaceManager.getInstance().getCurrentWorkspace()
			.createRemoteProject(usersession, name, description, monitor);
	}

	@Override
	public ProjectInfo doRun(IProgressMonitor monitor) throws EmfStoreException {
		if (session == null) {
			return createRemoteProject(monitor);
		}

		if (projectName == null) {
			return createRemoteProject(session, monitor);
		}

		if (projectName == null) {
			throw new IllegalArgumentException("Project name must not be null.");
		}

		return createRemoteProject(session, projectName, description, monitor);
	}

}
