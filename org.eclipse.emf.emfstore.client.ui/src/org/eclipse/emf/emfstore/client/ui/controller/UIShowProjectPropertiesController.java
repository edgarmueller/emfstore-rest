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
package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.client.api.IServer;
import org.eclipse.emf.emfstore.internal.client.model.impl.RemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for showing the properties of a project via a dialog.
 * 
 * @author emueller
 * 
 */
public class UIShowProjectPropertiesController extends AbstractEMFStoreUIController<Void> {

	private final ProjectInfo projectInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that will be used to open up the properties dialog
	 * @param projectInfo
	 *            the {@link ProjectInfo} info that is needed to determine for which
	 *            project the properties should be shown
	 */
	public UIShowProjectPropertiesController(Shell shell, ProjectInfo projectInfo) {
		super(shell);
		this.projectInfo = projectInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor progressMonitor) throws EMFStoreException {
		String revision = "<unknown>";
		IPrimaryVersionSpec versionSpec;

		try {
			versionSpec = new RemoteProject((IServer) projectInfo.eContainer(), projectInfo)
				.resolveVersionSpec(Versions.createHEAD());
			revision = "" + versionSpec.getIdentifier();
		} catch (EMFStoreException e) {
			// do nothing
		}

		final String rev = revision;

		MessageDialog.openInformation(getShell(), "Project Information", "Current revision: " + rev + "\n"
			+ "ProjectId: " + projectInfo.getProjectId().getId());
		return null;
	}
}