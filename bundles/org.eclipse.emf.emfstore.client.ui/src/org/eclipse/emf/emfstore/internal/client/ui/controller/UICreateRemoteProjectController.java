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

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.views.emfstorebrowser.views.CreateProjectDialog;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for creating a remote project.
 * 
 * @author emueller
 * 
 */
public class UICreateRemoteProjectController extends AbstractEMFStoreUIController<ESRemoteProject> {

	private Usersession session;
	private final String projectName;
	private String description;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} to be used during the creation of the remote project
	 */
	private UICreateRemoteProjectController(Shell shell) {
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
	public UICreateRemoteProjectController(Shell shell, ESUsersession session) {
		super(shell);
		this.session = ((ESUsersessionImpl) session).toInternalAPI();
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
	public UICreateRemoteProjectController(Shell shell, ESUsersession session, String projectName) {
		super(shell);
		this.session = ((ESUsersessionImpl) session).toInternalAPI();
		this.projectName = projectName;
		this.description = description == null ? "" : description;
	}

	private ESRemoteProject createRemoteProject(IProgressMonitor monitor) throws ESException {
		String[] ret = RunInUI.runWithResult(new Callable<String[]>() {

			public String[] call() throws Exception {
				CreateProjectDialog dialog = new CreateProjectDialog(getShell());
				if (dialog.open() == Dialog.OK) {
					return new String[] { dialog.getName(), dialog.getDescription() };
				}
				return null;
			}
		});

		String projectName = ret[0];
		String description = ret[1];

		return createRemoteProject(session, projectName, description, monitor);
	}

	private ESRemoteProject createRemoteProject(final Usersession usersession, final String name,
		final String description, IProgressMonitor monitor) throws ESException {
		return usersession.toAPI().getServer().createRemoteProject(name, monitor);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ESRemoteProject doRun(IProgressMonitor monitor) throws ESException {
		try {
			if (session == null) {
				throw new IllegalArgumentException("Session must not be null.");
			}

			if (projectName == null) {
				return createRemoteProject(monitor);
			}

			return createRemoteProject(session, projectName, description, monitor);

		} catch (final ESException e) {
			RunInUI.run(new Callable<Void>() {
				public Void call() throws Exception {
					MessageDialog.openError(getShell(), "Create project failed", "Creation of remote project failed: "
						+ e.getMessage());
					return null;
				}
			});
		}

		return null;
	}

}