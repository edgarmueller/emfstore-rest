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
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.dialogs.CompareProjectsDialog;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;

/**
 * This handler shows compare projects dialog.
 * 
 * @author Hodaie
 * @author wesendon
 */
public class CompareProjectsHandler extends AbstractEMFStoreHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.commands.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		// TODO: Controller?
		CompareProjectsDialog compareDialog = new CompareProjectsDialog(getShell(),
			requireSelection(ProjectSpace.class));
		compareDialog.create();
		compareDialog.open();

	}

}