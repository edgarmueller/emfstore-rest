/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * ovonwesen
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.emfstore.internal.client.ui.exceptions.RequiredSelectionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class provides helper methods that can determine the currently selected element.
 * 
 * @author ovonwesen
 * 
 */
public final class EMFStoreHandlerUtil {

	/**
	 * Private constructor.
	 */
	private EMFStoreHandlerUtil() {

	}

	/**
	 * Extracts element from event.
	 * 
	 * @param event the event
	 * @param clazz class type of the object to extract
	 * @param <T> the type of the object to extract
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSelection(ExecutionEvent event, Class<T> clazz) {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel == null) {
			sel = HandlerUtil.getActiveMenuSelection(event);
		}
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) sel;
		if (structuredSelection.isEmpty()) {
			return null;
		}

		Object selectedElement = structuredSelection.getFirstElement();
		if (!(clazz.isInstance(selectedElement))) {
			return null;
		}
		return (T) selectedElement;
	}

	/**
	 * Returns an object of the given <code>clazz</code> if it can be extracted from
	 * the current selection.
	 * 
	 * @param event
	 *            the event from which to extract the selection
	 * @param clazz
	 *            the type of the object that is requested to be extracted from the current selection
	 * @return an object of type <b>T</b> that is contained within the current selection
	 * 
	 * @throws RequiredSelectionException
	 *             if the selection is invalid, i.e. if no object of the given type is contained in the selection
	 *             or if the selection is <code>null</code>
	 * 
	 * @param <T> the type of the object to be extracted from the current selection
	 */
	public static <T> T requireSelection(ExecutionEvent event, Class<T> clazz) throws RequiredSelectionException {
		T selection = getSelection(event, clazz);
		if (selection == null) {
			throw new RequiredSelectionException();
		}
		return selection;
	}

}
