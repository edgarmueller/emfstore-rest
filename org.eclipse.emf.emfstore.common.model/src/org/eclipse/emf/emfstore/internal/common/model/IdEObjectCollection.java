/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.common.model.ESObjectContainer;

// import org.eclipse.emf.emfstore.common.model.ESModelElementId;

/**
 * A collection of {@link EObject}s where each one can be identified via a {@link ModelElementId}. {@link EObject}s can
 * be added and deleted and checked
 * whether they are part of the collection.
 * 
 * @author emueller
 * 
 */

public interface IdEObjectCollection extends EObject, ESObjectContainer<ModelElementId>,
	ESModelElementIdToEObjectMapping<ModelElementId> {

	/**
	 * Adds the given model element to the collection.
	 * 
	 * @param modelElement
	 *            the model element that should be added to the collection
	 */
	void addModelElement(EObject modelElement);

	/**
	 * Allocates certain IDs for the given model elements in the mapping.
	 * If any {@link EObject} contained in the mapping is added to this collection
	 * its {@link ModelElementId} will be determined using the ID in the mapping.
	 * 
	 * @param modelElementToIdMap
	 *            A map containing {@link ModelElementId}s for the model element
	 *            and its children
	 */
	void allocateModelElementIds(Map<EObject, ModelElementId> modelElementToIdMap);

	/**
	 * Removes any allocated ID entries from this collection that are contained
	 * in the given set of {@link ModelElementId}s.
	 * 
	 * @param modelElementIds
	 *            the set of model element IDs to be released
	 */
	void disallocateModelElementIds(Set<ModelElementId> modelElementIds);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#contains(org.eclipse.emf.ecore.EObject)
	 */
	boolean contains(EObject modelElement);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#contains(org.eclipse.emf.emfstore.common.model.ESModelElementId)
	 */
	boolean contains(ModelElementId eObjectId);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#getModelElementId(org.eclipse.emf.ecore.EObject)
	 */
	ModelElementId getModelElementId(EObject modelElement);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#getModelElement(org.eclipse.emf.emfstore.common.model.ESModelElementId)
	 */
	EObject getModelElement(ModelElementId modelElementId);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#getModelElements()
	 */
	EList<EObject> getModelElements();

	/**
	 * Deletes the given model element from the collection.
	 * 
	 * @param modelElement
	 *            the model element that should get deleted
	 */
	void deleteModelElement(EObject modelElement);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#getAllModelElements()
	 */
	Set<EObject> getAllModelElements();

	/**
	 * Retrieve a list of model elements of a certain type in the collection
	 * that are directly contained in the collection.
	 * 
	 * @param <T>
	 *            a sub-type of model element
	 * @param modelElementClass
	 *            the {@link EClass}
	 * @param list
	 *            a list of model elements, can be empty, but must be of the
	 *            same type as the <code>modelElementClass</code> indicates.
	 * @return a list of model elements of the given type
	 */
	<T extends EObject> EList<T> getModelElementsByClass(EClass modelElementClass, EList<T> list);

	/**
	 * /**
	 * Initializes the ID caches of the project with the given mappings.
	 * 
	 * @param eObjectToIdMap
	 *            a mapping from EObjects to IDs
	 * @param idToEObjectMap
	 *            the reverse mapping of <code>eObjectToIdMap</code>
	 */
	void initMapping(Map<EObject, String> eObjectToIdMap, Map<String, EObject> idToEObjectMap);

	/**
	 * Initializes the ID caches of the collection, i.e. the collection will
	 * call {@link IdEObjectCollection#getModelElements()} and for each model
	 * element the {@link ModelElementId} is fetched via {@link IdEObjectCollection#getModelElementId(EObject)}. Then a
	 * mapping
	 * between the model element and its {@link ModelElementId} is created
	 * within the cache.
	 */
	void initMapping();

	/**
	 * Returns a copy of the ID/EObject mapping where IDs are represented as strings.
	 * This method is mainly provided for convenience and performance reasons,
	 * where the ID must be a string.
	 * 
	 * @return the ID/EObject mapping
	 */
	Map<String, EObject> getIdToEObjectMapping();

	/**
	 * Returns a copy of the EObject/ID mapping where IDs are represented as strings.
	 * This method is mainly provided for convenience and performance reasons,
	 * where the ID must be a string.
	 * 
	 * @return the EObject/ID mapping
	 */
	Map<EObject, String> getEObjectToIdMapping();
}