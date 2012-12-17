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
package org.eclipse.emf.emfstore.client.ui.common;

import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isCreateDelete;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CreateDeleteOperation;

/**
 * Utility class for determing filtered types and operations that
 * involve only such types.
 * 
 * @author emueller
 * 
 */
public final class EClassFilter {

	/**
	 * Singleton.
	 */
	public static final EClassFilter INSTANCE = new EClassFilter();

	private Set<EClass> filteredEClasses;
	private String filterLabel;

	private EClassFilter() {
		filteredEClasses = new LinkedHashSet<EClass>();
		initFilteredEClasses();
	}

	private void initFilteredEClasses() {

		ExtensionPoint extensionPoint = new ExtensionPoint("org.eclipse.emf.emfstore.client.ui.filteredTypes");

		if (extensionPoint.size() == 0) {
			return;
		}

		for (ExtensionElement element : extensionPoint.getExtensionElements()) {
			IEClassFilter filter = element.getClass("filteredTypes", IEClassFilter.class);

			if (filter != null) {
				filteredEClasses.addAll(filter.getFilteredEClasses());
			}

			if (filterLabel == null) {
				filterLabel = filter.getLabel();
			}
		}
	}

	/**
	 * Whether any {@link EClass} has been marked as filtered at all.
	 * 
	 * @return true, if at least one {@link EClass} should be filtered
	 */
	public boolean isEnabled() {
		return filteredEClasses.size() > 0;
	}

	/**
	 * Whether the given {@link EClass} is considered as filtered.
	 * 
	 * @param eClass
	 *            the class to check
	 * @return true, if the given {@link EClass} is considered as filtered
	 */
	public boolean isFilteredEClass(EClass eClass) {
		return filteredEClasses.contains(eClass);
	}

	/**
	 * Whether the given operation only involves types that are considered to be filtered.
	 * 
	 * @param collection
	 *            an {@link IdEObjectCollection} that is used to resolve the {@link EObject}s
	 *            contained in the operation
	 * @param operation
	 *            the operations to check
	 * @return true, if the operation only involves types that are considered to be filtered
	 */
	public boolean involvesOnlyFilteredEClasses(IdEObjectCollection collection, AbstractOperation operation) {

		if (operation instanceof CompositeOperation) {
			CompositeOperation composite = (CompositeOperation) operation;
			for (AbstractOperation op : composite.getSubOperations()) {
				if (!involvesOnlyFilteredEClasses(collection, op)) {
					return false;
				}
			}

			return true;
		}

		if (isCreateDelete(operation)) {
			CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
			for (EObject modelElement : createDeleteOperation.getEObjectToIdMap().keySet()) {
				if (modelElement != null && !filteredEClasses.contains(modelElement.eClass())) {
					return false;
				} else if (modelElement == null) {
					return false;
				}
			}

			return true;
		}

		ModelElementId id = operation.getModelElementId();
		EObject modelElement = collection.getModelElement(id);

		if (modelElement == null) {
			modelElement = ((IdEObjectCollectionImpl) collection).getDeletedModelElement(id);
		}

		if (modelElement == null) {
			return false;
		}

		if (!filteredEClasses.contains(modelElement.eClass())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns the label that is used to group filtered types or operations that involve
	 * only such types.
	 * 
	 * @return the label used for grouping filtered types
	 */
	public String getFilterLabel() {
		return filterLabel;
	}
}
