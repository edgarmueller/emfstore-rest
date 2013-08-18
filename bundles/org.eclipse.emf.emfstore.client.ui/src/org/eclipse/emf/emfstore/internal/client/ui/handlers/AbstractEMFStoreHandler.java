/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * ovonwesen
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

/**
 * Handlers are the top level abstraction that clients should use execute actions within the EMFStore
 * workspace. They are responsible for calling the UI controllers and therefore provide
 * helper methods that can determine the currently selected element, if needed.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @see AbstractEMFStoreHandlerWithResult
 */
public abstract class AbstractEMFStoreHandler extends AbstractEMFStoreHandlerWithResult<Object> {

	@Override
	public Object handleWithResult() {
		handle();
		return null;
	}

	/**
	 * Executes the handler.
	 */
	public abstract void handle();

}
