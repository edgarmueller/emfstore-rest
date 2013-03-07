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

import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIShareProjectController;

/**
 * Handler for sharing a {@link ProjectSpace}.<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace} instance.<br/>
 * Alternatively, you may pass in the project space to be shared via a constructor.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 */
public class ShareProjectHandler extends AbstractEMFStoreHandler implements IHandler {

	private final ProjectSpace projectSpace;

	/**
	 * Default constructor.
	 */
	public ShareProjectHandler() {
		projectSpace = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space to be shared
	 */
	public ShareProjectHandler(ProjectSpace projectSpace) {
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
			new UIShareProjectController(getShell(), requireSelection(ProjectSpace.class).getAPIImpl()).execute();
		} else {
			new UIShareProjectController(getShell(), projectSpace.getAPIImpl()).execute();
		}
	}
}