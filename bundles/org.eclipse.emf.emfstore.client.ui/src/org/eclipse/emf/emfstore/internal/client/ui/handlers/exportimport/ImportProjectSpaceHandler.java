/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers.exportimport;

import org.eclipse.emf.emfstore.internal.client.ui.controller.UIImportController;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler;

/**
 * Handler for importing a project space.
 * 
 * @author emueller
 */
public class ImportProjectSpaceHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIImportController(getShell()).importProjectSpace();
	}

}
