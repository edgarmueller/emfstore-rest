/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.views.scm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * Convenience class to separate operations that considered to be filtered
 * from the rest of a given input.
 * 
 * @author emueller
 * 
 */
public class FilteredOperationsResult {

	private List<Object> nonFilteredContent;
	private List<AbstractOperation> filteredOperations;

	/**
	 * Constructor.
	 */
	public FilteredOperationsResult() {
		this.nonFilteredContent = new ArrayList<Object>();
		this.filteredOperations = new ArrayList<AbstractOperation>();
	}

	/**
	 * Adds an object that is classified as non-filtered.
	 * 
	 * @param obj
	 *            the object to be added
	 */
	public void addNonFiltered(Object obj) {
		nonFilteredContent.add(obj);
	}

	/**
	 * Adds an operation that is considered as filtered.
	 * 
	 * @param operation
	 *            the operation to be added
	 */
	public void addFilteredOperation(AbstractOperation operation) {
		filteredOperations.add(operation);
	}

	/**
	 * Returns the non-filtered content.
	 * 
	 * @return all non-filtered input
	 */
	public List<Object> getNonFiltered() {
		return nonFilteredContent;
	}

	/**
	 * Returns all operations that are considered to have been filtered.
	 * 
	 * @return all filtered operations
	 */
	public List<AbstractOperation> getFilteredOperations() {
		return filteredOperations;
	}
}
