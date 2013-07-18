/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.common;

import static org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil.isCreateDelete;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ui.ESClassFilter;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;

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

		ESExtensionPoint extensionPoint = new ESExtensionPoint("org.eclipse.emf.emfstore.client.ui.filteredTypes");

		if (extensionPoint.size() == 0) {
			return;
		}

		for (ESExtensionElement element : extensionPoint.getExtensionElements()) {
			ESClassFilter filter = element.getClass("filteredTypes", ESClassFilter.class);

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
	 * @param idToEObjectMapping
	 *            a mapping that is used to resolve the {@link EObject}s
	 *            contained in the operation
	 * @param operation
	 *            the operations to check
	 * @return true, if the operation only involves types that are considered to be filtered
	 */
	public boolean involvesOnlyFilteredEClasses(ModelElementIdToEObjectMapping idToEObjectMapping,
		AbstractOperation operation) {

		if (operation instanceof CompositeOperation) {
			CompositeOperation composite = (CompositeOperation) operation;
			for (AbstractOperation op : composite.getSubOperations()) {
				if (!involvesOnlyFilteredEClasses(idToEObjectMapping, op)) {
					return false;
				}
			}

			return true;
		}

		if (isCreateDelete(operation)) {
			CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
			for (EObject modelElement : createDeleteOperation.getEObjectToIdMap().keySet()) {
				if (modelElement != null && !isFilteredEClass(modelElement.eClass())) {
					return false;
				} else if (modelElement == null) {
					return false;
				}
			}

			return true;
		}

		ModelElementId id = operation.getModelElementId();
		EObject modelElement = idToEObjectMapping.get(id);

		if (modelElement == null) {
			return false;
		}

		if (!isFilteredEClass(modelElement.eClass())) {
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
