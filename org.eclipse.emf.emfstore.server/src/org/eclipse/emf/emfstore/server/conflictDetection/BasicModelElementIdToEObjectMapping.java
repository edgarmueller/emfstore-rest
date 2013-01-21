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
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.conflictDetection;

import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isCreateDelete;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CreateDeleteOperation;

/**
 * @author emueller
 * @author koegel
 */
public class BasicModelElementIdToEObjectMapping implements IModelElementIdToEObjectMapping {

	private Map<String, EObject> idToEObjectMapping;
	private IModelElementIdToEObjectMapping delegateMapping;

	/**
	 * Constructor.
	 * 
	 * @param mapping
	 *            an initial mapping from {EObject}s to their {@link ModelElementId}s
	 */
	public BasicModelElementIdToEObjectMapping(IModelElementIdToEObjectMapping mapping) {
		this.delegateMapping = mapping;
		this.idToEObjectMapping = new LinkedHashMap<String, EObject>();
	}

	/**
	 * Constructor.
	 * 
	 * @param mapping
	 *            an initial mapping from {EObject}s to their {@link ModelElementId}s
	 * @param changePackages
	 *            a list of {@link ChangePackage}s whose involved model elements should
	 *            be added to the mapping
	 */
	public BasicModelElementIdToEObjectMapping(IModelElementIdToEObjectMapping mapping,
		List<ChangePackage> changePackages) {
		this(mapping);
		for (ChangePackage changePackage : changePackages) {
			this.put(changePackage);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param mapping
	 *            an initial mapping from {EObject}s to their {@link ModelElementId}s
	 * @param changePackage
	 *            a {@link ChangePackage}s whose involved model elements should
	 *            be added to the mapping
	 */
	public BasicModelElementIdToEObjectMapping(IModelElementIdToEObjectMapping mapping, ChangePackage changePackage) {
		this(mapping);
		this.put(changePackage);
	}

	/**
	 * Adds all model elements that are involved in operations contained in the {@link ChangePackage} and their
	 * respective IDs into the mapping.
	 * 
	 * @param changePackage
	 *            the {@link ChangePackage} whose model elements should be added to the mapping
	 */
	public void put(ChangePackage changePackage) {
		for (AbstractOperation op : changePackage.getOperations()) {
			scanOperationIntoMapping(op);
		}
	}

	private void scanOperationIntoMapping(AbstractOperation operation) {

		if (operation instanceof CompositeOperation) {
			CompositeOperation composite = (CompositeOperation) operation;
			for (AbstractOperation subOp : composite.getSubOperations()) {
				scanOperationIntoMapping(subOp);
			}
			return;
		}

		if (isCreateDelete(operation)) {
			CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
			for (EObject modelElement : createDeleteOperation.getEObjectToIdMap().keySet()) {
				idToEObjectMapping.put(createDeleteOperation.getEObjectToIdMap().get(modelElement).toString(),
					modelElement);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping#get(org.eclipse.emf.emfstore.common.model.ModelElementId)
	 */
	public EObject get(ModelElementId modelElementId) {
		EObject eObject = delegateMapping.get(modelElementId);
		if (eObject != null) {
			return eObject;
		}
		if (modelElementId == null) {
			return null;
		}
		return idToEObjectMapping.get(modelElementId.toString());

	}
}
