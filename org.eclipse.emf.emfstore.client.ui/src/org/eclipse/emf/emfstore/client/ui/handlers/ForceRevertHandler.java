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

import org.eclipse.emf.emfstore.client.ui.controller.UIForceRevertCommitController;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

/**
 * Handler for forcing the revert of a commit.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 */
public class ForceRevertHandler extends AbstractEMFStoreHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		new UIForceRevertCommitController(getShell(), requireSelection(HistoryInfo.class)).execute();
	}

}
