/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.emfstore.internal.client.ui.controller.UIImportController;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;

/**
 * Handler for importing a project history.
 * 
 * @author emueller
 * 
 */
public class ImportProjectHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIImportController(getShell()).importProjectHistory(requireSelection(ProjectInfo.class));
	}

}
