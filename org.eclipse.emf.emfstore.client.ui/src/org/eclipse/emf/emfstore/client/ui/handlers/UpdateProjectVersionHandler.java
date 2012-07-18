/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
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
import org.eclipse.emf.emfstore.client.ui.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

/**
 * Handler for updating a project to a specific version.<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace}
 * instance.<br/>
 * <b>TODO: currently still updates to HEAD</b>
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UpdateProjectVersionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		// TODO: ask for specific version, not HEAD
		ProjectSpace ps = requireSelection(ProjectSpace.class);
		new UIUpdateProjectController(getShell(), ps, Versions.HEAD_VERSION(ps
				.getBaseVersion())).execute();
	}

}
