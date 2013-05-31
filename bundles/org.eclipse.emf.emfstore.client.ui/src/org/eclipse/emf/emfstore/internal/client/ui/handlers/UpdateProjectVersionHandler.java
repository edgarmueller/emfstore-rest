/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * ovonwesen
 * eneufeld
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUpdateProjectToVersionController;

/**
 * Handler for updating a project to a specific version.<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace} instance.<br/>
 * 
 * @author ovonwesen
 * @author emueller
 * @author eneufeld
 */
public class UpdateProjectVersionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIUpdateProjectToVersionController(
			getShell(),
			requireSelection(ProjectSpace.class).toAPI()).execute();
	}
}
