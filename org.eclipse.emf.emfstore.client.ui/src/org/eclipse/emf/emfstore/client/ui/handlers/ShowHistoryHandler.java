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
import org.eclipse.emf.emfstore.client.ui.controller.UIShowHistoryController;

/**
 * Brings up the history view for a selected {@link EObject}<br/>
 * If the {@link EObject} is a ProjectSpace the complete history is shown.
 * 
 * @author emueller
 * @author wesendon
 * 
 */
public class ShowHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIShowHistoryController(getShell(), requireSelection(EObject.class))
				.execute();
	}

}
