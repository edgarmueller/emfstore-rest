/*******************************************************************************
 * Copyright 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.controller.UIAddTagController;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

/**
 * Handler for adding a tag to a selected {@link HistoryInfo} instance.
 * It is assumed that the user previously has selected a {@link HistoryInfo} instance.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class AddTagHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);
		new UIAddTagController(getShell(), historyInfo).execute();
	}
}
