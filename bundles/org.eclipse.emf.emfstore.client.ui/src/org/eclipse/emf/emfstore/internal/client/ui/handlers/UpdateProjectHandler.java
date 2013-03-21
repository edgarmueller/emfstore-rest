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
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUpdateProjectController;

/**
 * Updates a {@link ProjectSpace} to the most recent version (HEAD).<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace} instance.<br/>
 * Alternatively, you may pass in the project space to be updated via a constructor.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	private final ProjectSpace projectSpace;

	/**
	 * Default constructor.
	 */
	public UpdateProjectHandler() {
		this.projectSpace = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} to be updated
	 */
	public UpdateProjectHandler(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		if (projectSpace == null) {
			new UIUpdateProjectController(
				getShell(),
				requireSelection(ProjectSpace.class).toAPI()).execute();
		} else {
			new UIUpdateProjectController(
				getShell(),
				projectSpace.toAPI()).execute();
		}
	}

}