/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUndoLastOperationController;

/**
 * Handler for undoing the latest change on a selected {@link ProjectSpace}.<br/>
 * It is assumed that the user previously has selected a {@link ProjectSpace}
 * instance.
 * 
 * @author emueller
 * 
 */
public class UndoLastOperationHandler extends AbstractEMFStoreHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreHandler#handle()
	 */
	@Override
	public void handle() {
		new UIUndoLastOperationController(getShell(), requireSelection(
				ProjectSpace.class).toAPI()).execute();
	}

}
