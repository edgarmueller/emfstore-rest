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
package org.eclipse.emf.emfstore.internal.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIExportController;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler;

/**
 * Handler for exporting a {@link ProjectSpace}.
 * 
 * @author emueller
 * 
 */
public class ExportProjectSpaceHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIExportController(getShell()).exportProjectSpace(requireSelection(ProjectSpace.class));
	}

}
