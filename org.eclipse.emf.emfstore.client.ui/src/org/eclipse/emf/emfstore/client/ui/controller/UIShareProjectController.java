/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Max Hohenegger (bug 371196)
 ******************************************************************************/

package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.exceptions.LoginCanceledException;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-related controller class that shares a project and displays a progress bar during the share.
 * When finished a confirmation dialog is shown.
 * 
 * @author emueller
 * 
 */
public class UIShareProjectController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell} that is used during the share
	 * @param projectSpace
	 *            the {@link ProjectSpace} that should get shared
	 */
	public UIShareProjectController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	protected Void doRun(final IProgressMonitor progressMonitor) throws EmfStoreException {

		try {
			((ProjectSpaceImpl) projectSpace).shareProject(null, progressMonitor);
		} catch (LoginCanceledException e) {
			// fail silently
		} catch (EmfStoreException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}

		new RunInUIThread(getShell()) {
			@Override
			public Void run(Shell shell) {
				MessageDialog.openInformation(getShell(), "Share completed", "The project was successfully shared.");
				return null;
			}
		}.execute();

		return null;
	}
}
