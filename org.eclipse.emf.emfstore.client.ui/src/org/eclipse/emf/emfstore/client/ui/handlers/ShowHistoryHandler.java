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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIShowHistoryController;

/**
 * Brings up the history view for a selected {@link ProjectSpace}.<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace} instance.
 * 
 * @author emueller
 * 
 */
public class ShowHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIShowHistoryController(getShell(), requireSelection(ProjectSpace.class), requireSelection(EObject.class))
			.execute();
	}

}
