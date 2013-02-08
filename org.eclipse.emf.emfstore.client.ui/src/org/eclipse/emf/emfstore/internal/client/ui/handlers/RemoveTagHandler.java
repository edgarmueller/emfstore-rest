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

import org.eclipse.emf.emfstore.internal.client.ui.controller.UIRemoveTagController;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;

/**
 * Handler for removing a tag. Requires a selection of type {@link HistoryInfo}.
 * 
 * @author emueller
 * 
 */
public class RemoveTagHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIRemoveTagController(getShell(), requireSelection(HistoryInfo.class)).execute();
	}

}