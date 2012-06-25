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

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIRevertCommitController;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

/**
 * Handler for reverting a commit on a selected {@link HistoryInfo}.<br/>
 * It is assumed that the user previously has selected a {@link HistoryInfo} instance.
 * 
 * @author emueller
 * 
 */
public class RevertCommitHandler extends AbstractEMFStoreHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);
		PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());
		new UIRevertCommitController(getShell(), requireSelection(ProjectSpace.class), versionSpec).execute();
	}
}
