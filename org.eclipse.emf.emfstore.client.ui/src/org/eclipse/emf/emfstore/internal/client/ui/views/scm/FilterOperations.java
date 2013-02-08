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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.ui.common.EClassFilter;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * A class that divides an given input into operations that may be filtered via
 * the {@link EClassFilter}.
 * 
 * @author emueller
 * 
 */
public class FilterOperations {

	private final IModelElementIdToEObjectMapping idToEObjectMapping;
	private final Class<? extends EObject> ignoredClass;

	/**
	 * Constructor.
	 * 
	 * @param idToEObjectMapping
	 *            a mapping from {@link EObject}s to their IDs. Used to resolve {@link EObject}s involved within
	 *            operations
	 */
	public FilterOperations(IModelElementIdToEObjectMapping idToEObjectMapping) {
		this.idToEObjectMapping = idToEObjectMapping;
		this.ignoredClass = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param idToEObjectMapping
	 *            a mapping from {@link EObject}s to their IDs. Used to resolve {@link EObject}s involved within
	 *            operations
	 * @param ignoredClass
	 *            a type that is completely ignored while filtering, i.e.
	 *            instances of this type are ignored by the {@link FilteredOperationsResult} type
	 */
	public FilterOperations(IModelElementIdToEObjectMapping idToEObjectMapping, Class<? extends EObject> ignoredClass) {
		this.idToEObjectMapping = idToEObjectMapping;
		this.ignoredClass = ignoredClass;
	}

	/**
	 * Filters the given input according to the filtering rules of the {@link EClassFilter}.
	 * 
	 * @param input
	 *            the input to be filtered
	 * @return a {@link FilteredOperationsResult} containing the filtered and non-filtered types
	 * 
	 */
	public FilteredOperationsResult filter(Object[] input) {

		FilteredOperationsResult result = new FilteredOperationsResult();

		for (Object object : input) {

			if (ignoredClass != null && ignoredClass.isInstance(object)) {
				continue;
			}

			if (object instanceof AbstractOperation) {
				AbstractOperation operation = (AbstractOperation) object;
				if (eClassFilterEnabled()
					&& EClassFilter.INSTANCE.involvesOnlyFilteredEClasses(idToEObjectMapping, operation)) {
					result.addFilteredOperation(operation);
				} else {
					result.addNonFiltered(operation);
				}
			} else {
				result.addNonFiltered(object);
			}
		}

		return result;
	}

	private boolean eClassFilterEnabled() {
		return EClassFilter.INSTANCE.isEnabled() && idToEObjectMapping != null;
	}

}
