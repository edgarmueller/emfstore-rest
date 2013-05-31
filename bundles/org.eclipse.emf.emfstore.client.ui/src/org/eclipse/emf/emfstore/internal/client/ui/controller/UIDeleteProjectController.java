/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for deleting a local project.
 * 
 * @author emueller
 */
public class UIDeleteProjectController extends AbstractEMFStoreUIController<Void> {

	private final ESLocalProject localProject;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that will be used during the deletion of the project
	 * @param projectSpace
	 *            the {@link ProjectSpace} containing the project that should be deleted
	 */
	public UIDeleteProjectController(Shell shell, ESLocalProject localProject) {
		super(shell);
		this.localProject = localProject;
	}

	private void deleteProject(final ESLocalProject localProject) {
		try {
			// TODO: pass monitor in & handle exceptions
			localProject.delete(new NullProgressMonitor());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ESException e) {
			e.printStackTrace();
		}
	}

	private boolean confirmation(final ESLocalProject localProject) {
		String message = "Do you really want to delete your local copy of project \"" + localProject.getProjectName()
			+ "\n";

		if (localProject.getBaseVersion() != null) {
			message += " in version " + localProject.getBaseVersion().getIdentifier();
		}

		message += " ?";

		return confirm("Confirmation", message);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {

		if (!confirmation(localProject)) {
			return null;
		}

		deleteProject(localProject);
		return null;
	}

}
