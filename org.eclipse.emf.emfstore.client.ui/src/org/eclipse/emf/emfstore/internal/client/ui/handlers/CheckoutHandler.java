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

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.impl.ESRemoteProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;

/**
 * Handler for checking out a project.
 * 
 * @author emueller
 * 
 */
public class CheckoutHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {

		ProjectInfo projectInfo = requireSelection(ProjectInfo.class);

		if (projectInfo == null || projectInfo.eContainer() == null) {
			return;
		}

		// FIXME: eContainer call
		new UICheckoutController(getShell(),
			new ESRemoteProjectImpl((ESServer) projectInfo.eContainer(), projectInfo)).execute();
	}

}