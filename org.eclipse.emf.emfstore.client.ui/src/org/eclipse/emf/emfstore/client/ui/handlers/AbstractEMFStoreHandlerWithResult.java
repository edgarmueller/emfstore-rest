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
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.ui.exceptions.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreHandlerUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Handlers are the top level abstraction that clients should use execute actions within the EMFStore
 * workspace. They are responsible for calling the UI controllers and therefore provide
 * helper methods that can determine the currently selected element, if needed.
 * This handler is capable of returning a result.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @param <T> the return type of the handler
 * 
 * @see AbstractEMFStoreHandler
 */
public abstract class AbstractEMFStoreHandlerWithResult<T> extends AbstractHandler {

	private ExecutionEvent event;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public T execute(ExecutionEvent event) throws ExecutionException {
		this.event = event;

		new EMFStoreCommandWithResult<T>() {
			@Override
			protected T doRun() {
				return handleWithResult();
			}
		}.run(false);

		this.event = null;
		return null;
	}

	/**
	 * Executes the handler and returns a result.
	 * 
	 * @return a return value of type <b>T</b>
	 */
	public abstract T handleWithResult();

	/**
	 * Returns the event which was responsible for executing this handler.
	 * 
	 * @return the event that executed this handler
	 */
	protected ExecutionEvent getEvent() {
		return event;
	}

	/**
	 * Returns an object of the given <code>clazz</code> if it can be extracted from
	 * the current selection.
	 * 
	 * @param clazz
	 *            the type of the object that is requested to be extracted from the current selection
	 * @return an object of type <b>T</b> that is contained within the current selection
	 * 
	 * @throws RequiredSelectionException
	 *             if the selection is invalid, i.e. if no object of the given type is contained in the selection
	 *             or if the selection is <code>null</code>
	 * 
	 * @param <U> the type of the object to be extracted from the current selection
	 */
	public <U> U requireSelection(Class<U> clazz) throws RequiredSelectionException {
		return EMFStoreHandlerUtil.requireSelection(getEvent(), clazz);
	}

	/**
	 * Returns the currently active shell.
	 * 
	 * @return the active shell
	 */
	public Shell getShell() {
		return Display.getCurrent().getActiveShell();
	}
}