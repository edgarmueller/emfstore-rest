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
package org.eclipse.emf.emfstore.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ImportChangesController;
import org.eclipse.emf.emfstore.client.ui.controller.UIGenericExportImportController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * CheckoutHandler for importing changes that will be applied upon the selected {@link ProjectSpace}.
 * 
 * @author emueller
 * 
 */
public class ImportChangesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		new UIGenericExportImportController(getShell(), new ImportChangesController(
			requireSelection(ProjectSpace.class))).execute();
	}

}
