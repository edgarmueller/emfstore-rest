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
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UICommitProjectController;

/**
 * Handler for commit a project.<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace} instance.<br/>
 * Alternatively the project space to be committed may also be passed via a constructor.
 * 
 * @author emueller
 * 
 */
public class CommitProjectHandler extends AbstractEMFStoreHandler {

	private final ProjectSpace projectSpace;

	/**
	 * Default constructor.
	 */
	public CommitProjectHandler() {
		projectSpace = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space to be committed
	 */
	public CommitProjectHandler(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		if (projectSpace == null) {
			new UICommitProjectController(getShell(), requireSelection(ProjectSpace.class)).execute();
		} else {
			new UICommitProjectController(getShell(), projectSpace).execute();
		}
	}

}