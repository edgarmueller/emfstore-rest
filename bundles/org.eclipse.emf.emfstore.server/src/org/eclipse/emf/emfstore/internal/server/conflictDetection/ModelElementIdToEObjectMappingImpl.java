/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import static org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil.isCreateDelete;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdToEObjectMappingImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;

/**
 * @author emueller
 * @author koegel
 */
public class ModelElementIdToEObjectMappingImpl implements ModelElementIdToEObjectMapping {

	private Map<String, EObject> idToEObjectMapping;
	private ModelElementIdToEObjectMapping delegateMapping;
	private ESModelElementIdToEObjectMappingImpl apiImpl;

	/**
	 * Constructor.
	 * 
	 * @param mapping
	 *            an initial mapping from {EObject}s to their {@link ModelElementId}s
	 */
	public ModelElementIdToEObjectMappingImpl(ModelElementIdToEObjectMapping mapping) {
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
	public ModelElementIdToEObjectMappingImpl(ModelElementIdToEObjectMapping mapping,
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
	 *            an initial mapping from {EObject}s to their {@link ESModelElementId}s
	 * @param changePackage
	 *            a {@link ChangePackage}s whose involved model elements should
	 *            be added to the mapping
	 */
	public ModelElementIdToEObjectMappingImpl(ModelElementIdToEObjectMapping mapping,
		List<AbstractOperation> operationsList1, List<AbstractOperation> operationsList2) {
		this(mapping);
		this.put(operationsList1);
		this.put(operationsList2);
	}

	public ModelElementIdToEObjectMappingImpl(Project project, ChangePackage changePackage) {
		this(project);
		this.put(changePackage);
	}

	/**
	 * Adds all model elements that are involved in operations contained in the {@link ChangePackage} and their
	 * respective IDs into the mapping.
	 * 
	 * @param changePackage
	 *            the {@link ChangePackage} whose model elements should be added to the mapping
	 */
	public void put(List<ChangePackage> changePackages) {
		for (ChangePackage changePackage : changePackages) {
			put(changePackage);
		}
	}

	/**
	 * Adds all model elements that are involved in operations contained in the {@link ChangePackage} and their
	 * respective IDs into the mapping.
	 * 
	 * @param changePackage
	 *            the {@link ChangePackage} whose model elements should be added to the mapping
	 */
	public void put(ChangePackage changePackage) {
		put(changePackage.getOperations());
	}

	public void put(Collection<AbstractOperation> operations) {
		for (AbstractOperation op : operations) {
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
	 * @see org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping#get(org.eclipse.emf.emfstore.internal.common.model.ModelElementId)
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

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	public ESModelElementIdToEObjectMappingImpl toAPI() {

		if (apiImpl == null) {
			apiImpl = createAPI();
		}

		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	public ESModelElementIdToEObjectMappingImpl createAPI() {
		return new ESModelElementIdToEObjectMappingImpl(this);
	}
}
