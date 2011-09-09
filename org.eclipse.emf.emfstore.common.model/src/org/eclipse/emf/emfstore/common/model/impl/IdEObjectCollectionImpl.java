/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.common.model.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.ModelFactory;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;

/**
 * Implementation of a storage for mapping {@link EObject}s onto a
 * {@link ModelElementId}.
 * 
 * @author emueller
 */
public abstract class IdEObjectCollectionImpl extends EObjectImpl implements
		IdEObjectCollection {

	// Caches
	private Set<EObject> eObjectsCache;
	private Map<EObject, ModelElementId> eObjectToIdCache;
	private Map<ModelElementId, EObject> idToEObjectCache;
	protected boolean cachesInitialized;

	/**
	 * Will be used to cache all model elements of a project in order to avoid
	 * fetching those multiple times when trying to retrieve a model element ID.
	 * 
	 * @see NotifiableIdEObjectCollectionImpl#getModelElementId(EObject)
	 */
	private Set<EObject> containedModelElements;

	/**
	 * Will be used to maintain the {@link ModelElementId}s of deleted
	 * {@link EObject}s
	 */
	private Map<EObject, ModelElementId> deletedEObjectToIdMap;

	/**
	 * Will be used to assign specific {@link ModelElementId}s to newly created
	 * {@link EObject}s
	 */
	private Map<EObject, ModelElementId> newEObjectToIdMap;

	/**
	 * Constructor.
	 */
	public IdEObjectCollectionImpl() {
		eObjectsCache = new HashSet<EObject>();
		eObjectToIdCache = new HashMap<EObject, ModelElementId>();
		idToEObjectCache = new HashMap<ModelElementId, EObject>();
		containedModelElements = new HashSet<EObject>();
		deletedEObjectToIdMap = new HashMap<EObject, ModelElementId>();
		newEObjectToIdMap = new HashMap<EObject, ModelElementId>();
	}

	/**
	 * Constructor. Adds the contents of the given {@link XMIResource} as model
	 * elements to the collection. If the {@link XMIResource} also has XMI IDs
	 * assigned to the {@link EObject}s it contains, they will be used for
	 * creating the {@link ModelElementId}s within the project, if not, the
	 * {@link ModelElementId}s will get created on the fly.
	 * 
	 * @param xmiResource
	 *            a {@link XMIResource}
	 * @throws IOException
	 *             if the given {@link XMIResource} could not be loaded
	 */
	public IdEObjectCollectionImpl(XMIResource xmiResource) throws IOException {
		this();
		boolean resourceHasIds = false;
		try {
			xmiResource.load(null);
		} catch (IOException e) {
			ModelUtil.logException(
					String.format("XMIResource %s could not be loaded.",
							xmiResource.getURI()), e);
			throw e;
		}
		TreeIterator<EObject> it = xmiResource.getAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();

			if (ModelUtil.isIgnoredDatatype(eObject)) {
				continue;
			}

			String id = xmiResource.getID(eObject);
			ModelElementId eObjectId = ModelFactory.eINSTANCE
					.createModelElementId();

			if (id != null) {
				eObjectId.setId(id);
				resourceHasIds = true;
			} else {
				xmiResource.setID(eObject, eObjectId.getId());
			}

			putIntoCaches(eObject, eObjectId);
		}

		if (resourceHasIds) {
			cachesInitialized = true;
		}

		EList<EObject> contents = xmiResource.getContents();
		setModelElements(contents);
		// save, in order to write IDs back into resource
		xmiResource.save(null);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getModelElements()
	 */
	public abstract Collection<EObject> getModelElements();

	/**
	 * Sets the model elements of this collection.
	 */
	protected abstract void setModelElements(EList<EObject> modelElements);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#addModelElement(org.eclipse.emf.ecore.EObject)
	 */
	public void addModelElement(EObject eObject) {
		getModelElements().add(eObject);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#addModelElement(org.eclipse.emf.ecore.EObject,
	 *      java.util.Map)
	 */
	public void addModelElement(EObject newModelElement,
			Map<EObject, ModelElementId> map) {

		// since id is contained in map, all IDs should be cloned
		ModelElementId newModelElementId = ModelUtil.clone(map
				.get(newModelElement));

		// check whether the model element is already contained in the project
		if (contains(newModelElementId)) {
			throw new IllegalStateException("Model element ID "
					+ newModelElementId + " already contained in project.");
		}

		for (Map.Entry<EObject, ModelElementId> entry : map.entrySet()) {
			EObject modelElement = entry.getKey();
			ModelElementId modelElementId = entry.getValue();
			newEObjectToIdMap.put(modelElement, modelElementId);
		}

		getModelElements().add(newModelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#containsInstance(org.eclipse.emf.ecore.EObject)
	 */
	public boolean containsInstance(EObject modelElement) {
		return getEObjectsCache().contains(modelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getDeletedModelElementId(org.eclipse.emf.ecore.EObject)
	 */
	public ModelElementId getDeletedModelElementId(EObject deletedModelElement) {

		ModelElementId id = deletedEObjectToIdMap.get(deletedModelElement);

		return id != null ? ModelUtil.clone(id) : ModelUtil
				.getSingletonModelElementId(deletedModelElement);
	}

	/**
	 * Get the deleted model element with the given id from the collection.
	 * 
	 * @param modelElementId
	 *            a {@link ModelElementId}
	 * @return the deleted model element or null if it is not in the project
	 */
	public EObject getDeletedModelElement(ModelElementId modelElementId) {
		for (Map.Entry<EObject, ModelElementId> entry : deletedEObjectToIdMap
				.entrySet()) {
			if (entry.getValue().equals(modelElementId)) {
				return entry.getKey();
			}
		}

		return ModelUtil.getSingleton(modelElementId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getModelElementId(org.eclipse.emf.ecore.EObject)
	 */
	public ModelElementId getModelElementId(EObject eObject) {

		if (!eObjectToIdCache.containsKey(eObject) && !isCacheInitialized()) {

			if (containedModelElements == null) {
				containedModelElements = ModelUtil
						.getAllContainedModelElements(this, false);
			}

			if (!containedModelElements.contains(eObject)) {
				return null;
			}

			// EObject contained in project, load ID from resource
			try {
				Resource resource = eObject.eResource();

				// EM: is this a potential error case we have to consider?
				if (!(resource instanceof XMIResource)) {
					return null;
				}

				XMIResource xmiResource = (XMIResource) resource;
				xmiResource.load(null);
				ModelElementId modelElementId = ModelFactory.eINSTANCE
						.createModelElementId();

				String id = xmiResource.getID(eObject);
				if (id != null) {
					// change ID
					modelElementId.setId(id);
					eObjectToIdCache.put(eObject, modelElementId);
					return ModelUtil.clone(modelElementId);
				}

				// return new ID
				eObjectToIdCache.put(eObject, modelElementId);
				return ModelUtil.clone(modelElementId);

			} catch (IOException e) {
				throw new RuntimeException(
						"Couldn't load resource for model element " + eObject);
			}
		}

		ModelElementId id = eObjectToIdCache.get(eObject);

		return id != null ? ModelUtil.clone(id) : ModelUtil
				.getSingletonModelElementId(eObject);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getModelElement(org.eclipse.emf.emfstore.common.model.ModelElementId)
	 */
	public EObject getModelElement(ModelElementId modelElementId) {

		if (!isCacheInitialized()) {
			initCaches();
		}

		EObject eObject = getIdToEObjectCache().get(modelElementId);

		return eObject != null ? eObject : ModelUtil
				.getSingleton(modelElementId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#deleteModelElement(org.eclipse.emf.ecore.EObject)
	 */
	public void deleteModelElement(final EObject modelElement) {
		if (!this.containsInstance(modelElement)) {
			throw new IllegalArgumentException(
					"Cannot delete a model element that is not contained in this project.");
		}

		// remove cross references
		ModelUtil.deleteOutgoingCrossReferences(modelElement, true, false);
		ModelUtil.deleteIncomingCrossReferencesFromParent(modelElement, this,
				true, false);

		// remove containment
		EObject containerModelElement = ModelUtil
				.getContainerModelElement(modelElement);
		if (containerModelElement == null) {
			// removeModelElementAndChildrenFromCache(modelElement);
			// getEobjectsIdMap().remove(modelElement);
			this.getModelElements().remove(modelElement);
		} else {
			XMIResource res = (XMIResource) modelElement.eResource();
			EReference containmentFeature = modelElement.eContainmentFeature();
			if (containmentFeature.isMany()) {
				EList<?> containmentList = (EList<?>) containerModelElement
						.eGet(containmentFeature);
				containmentList.remove(modelElement);
			} else {
				containerModelElement.eSet(containmentFeature, null);
			}
			ModelUtil.removeModelElementAndChildrenFromResource(res,
					modelElement);
		}
	}

	/**
	 * Returns the {@link ModelElementId} for the given model element. If no
	 * such ID exists, a new one will be created.
	 * 
	 * @param modelElement
	 *            a model element to fetch a {@link ModelElementId} for
	 * @return the {@link ModelElementId} for the given model element
	 */
	private ModelElementId getIdForModelElement(EObject modelElement) {

		Resource resource = modelElement.eResource();

		if (resource != null && resource instanceof XMIResource) {
			// resource available, read ID
			XMIResource xmiResource = (XMIResource) resource;
			try {
				xmiResource.load(null);
			} catch (IOException e) {
				throw new RuntimeException("Resource of model element "
						+ modelElement + " couldn't be loaded");
			}
			String id = xmiResource.getID(modelElement);
			if (id != null) {
				ModelElementId objId = ModelFactory.eINSTANCE
						.createModelElementId();
				objId.setId(id);
				return objId;
			}
		}

		// create new ID
		return ModelFactory.eINSTANCE.createModelElementId();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getAllModelElements()
	 */
	public Set<EObject> getAllModelElements() {
		if (!isCacheInitialized()) {
			initCaches();
		}

		return eObjectsCache;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getAllModelElementsbyClass(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.common.util.EList)
	 */
	public <T extends EObject> EList<T> getAllModelElementsbyClass(
			EClass modelElementClass, EList<T> list) {
		return getAllModelElementsbyClass(modelElementClass, list, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.Project#getModelElementsByClass(org.eclipse.emf.ecore.EClass)
	 * @generated NOT
	 */
	// cast below is guarded by sanity check
	@SuppressWarnings("unchecked")
	public <T extends EObject> EList<T> getModelElementsByClass(
			EClass modelElementClass, EList<T> list) {

		for (EObject modelElement : this.getModelElements()) {
			if (modelElementClass.isInstance(modelElement)) {
				list.add((T) modelElement);
			}
		}
		return list;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getAllModelElementsbyClass(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.common.util.EList, java.lang.Boolean)
	 */
	// two casts below are guarded by initial sanity check and if statement
	@SuppressWarnings("unchecked")
	public <T extends EObject> EList<T> getAllModelElementsbyClass(
			EClass modelElementClass, EList<T> list, Boolean subclasses) {

		if (subclasses) {
			for (ModelElementId modelElementId : getIdToEObjectCache().keySet()) {
				EObject modelElement = this.getModelElement(modelElementId);
				if (modelElementClass.isInstance(modelElement)) {
					list.add((T) modelElement);
				}
			}
		} else {
			for (ModelElementId modelElementId : getIdToEObjectCache().keySet()) {
				EObject modelElement = this.getModelElement(modelElementId);
				if (modelElement.eClass() == modelElementClass) {
					list.add((T) modelElement);
				}
			}
		}

		return list;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#getAllModelElementIds()
	 */
	public Set<ModelElementId> getAllModelElementIds() {
		if (!isCacheInitialized()) {
			initCaches();
		}

		return idToEObjectCache.keySet();
	}

	/**
	 * Whether the cache has been initialized.
	 * 
	 * @return true, if the cache is initialized, false otherwise
	 */
	protected boolean isCacheInitialized() {
		return cachesInitialized;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#contains(org.eclipse.emf.emfstore.common.model.ModelElementId)
	 */
	public boolean contains(ModelElementId id) {
		if (!isCacheInitialized()) {
			initCaches();
		}
		return getIdToEObjectCache().containsKey(id);
	}

	/**
	 * Returns the cache that maps {@link ModelElementId} to model elements.
	 * 
	 * @return a map containing mappings from {@link ModelElementId}s to model
	 *         element
	 */
	protected Map<ModelElementId, EObject> getIdToEObjectCache() {
		if (!isCacheInitialized()) {
			initCaches();
		}

		return idToEObjectCache;
	}

	/**
	 * Returns the model element cache.
	 * 
	 * @return a set containing all model elements
	 */
	protected Set<EObject> getEObjectsCache() {
		if (!isCacheInitialized()) {
			initCaches();
		}

		return eObjectsCache;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#initCaches()
	 */
	public void initCaches() {

		if (isCacheInitialized()) {
			return;
		}

		for (EObject modelElement : getModelElements()) {
			// put model element into cache
			ModelElementId modelElementId = getIdForModelElement(modelElement);
			putIntoCaches(modelElement, modelElementId);

			// put children of model element into cache
			TreeIterator<EObject> it = modelElement.eAllContents();
			while (it.hasNext()) {
				EObject obj = it.next();
				ModelElementId id = getIdForModelElement(obj);
				putIntoCaches(obj, id);
			}
		}

		cachesInitialized = true;
	}

	/**
	 * Adds a model element and all its children to the caches.
	 * 
	 * @param modelElement
	 *            the model element, that should get added to the caches
	 */
	protected void addModelElementAndChildrenToCache(EObject modelElement) {

		List<ModelElementId> removableIds = new ArrayList<ModelElementId>();

		// first check whether ID should be reassigned
		ModelElementId id = newEObjectToIdMap.get(modelElement);

		if (id == null) {
			// if not, create a new ID
			id = ModelFactory.eINSTANCE.createModelElementId();
		} else {
			removableIds.add(id);
		}

		if (isCacheInitialized()) {
			putIntoCaches(modelElement, id);
		}

		for (EObject child : ModelUtil.getAllContainedModelElements(
				modelElement, false)) {
			// first check whether ID should be reassigned
			ModelElementId childId = newEObjectToIdMap.get(child);

			if (childId == null) {
				// if not, create a new ID
				childId = ModelFactory.eINSTANCE.createModelElementId();
			} else {
				removableIds.add(childId);
			}

			if (isCacheInitialized()) {
				putIntoCaches(child, childId);
			}
		}

		// remove all IDs that are in use now
		newEObjectToIdMap.values().removeAll(removableIds);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IdEObjectCollection#initCaches(java.util.Map,
	 *      java.util.Map)
	 */
	public void initCaches(Map<EObject, ModelElementId> eObjectToIdMap,
			Map<ModelElementId, EObject> idToEObjectMap) {
		cachesInitialized = true;
		eObjectToIdCache = eObjectToIdMap;
		idToEObjectCache = idToEObjectMap;
		eObjectsCache = eObjectToIdMap.keySet();
	}

	/**
	 * Creates a mapping for the given model element and the given
	 * {@link ModelElementId} within the cache.
	 * 
	 * @param modelElement
	 *            a model element
	 * @param modelElementId
	 *            a {@link ModelElementId}
	 */
	protected void putIntoCaches(EObject modelElement,
			ModelElementId modelElementId) {
		eObjectToIdCache.put(modelElement, modelElementId);
		idToEObjectCache.put(modelElementId, modelElement);
		if (!eObjectsCache.contains(modelElement)) {
			eObjectsCache.add(modelElement);
		}
	}

	/**
	 * Copies the collection.
	 * 
	 * @param <T>
	 *            a collection type
	 * @return the copied collection instance
	 */
	@SuppressWarnings("unchecked")
	public <T extends IdEObjectCollection> T copy() {
		Copier copier = new IdEObjectCollectionCopier();
		T result = (T) copier.copy(this);
		((IdEObjectCollectionImpl) result).cachesInitialized = true;
		copier.copyReferences();
		return result;
	}

	/**
	 * Removes a model element and all its children from the cache.
	 * 
	 * @param modelElement
	 *            a model element to be removed from the cache
	 */
	protected void removeModelElementAndChildrenFromCache(EObject modelElement) {

		ModelElementId id = getModelElementId(modelElement);
		deletedEObjectToIdMap.put(modelElement, id);
		newEObjectToIdMap.put(modelElement, id);

		removeFromCaches(modelElement);
		eObjectToIdCache.remove(modelElement);

		for (EObject child : ModelUtil.getAllContainedModelElements(
				modelElement, false)) {
			ModelElementId childId = getModelElementId(child);
			deletedEObjectToIdMap.put(child, childId);
			newEObjectToIdMap.put(child, childId);
			removeFromCaches(child);
			eObjectToIdCache.remove(child);
		}
	}

	/**
	 * Removes the given model element from the caches.
	 * 
	 * @param modelElement
	 *            the model element to be removed from the caches
	 */
	private void removeFromCaches(EObject modelElement) {
		if (isCacheInitialized()) {
			ModelElementId id = this.getModelElementId(modelElement);
			getEObjectsCache().remove(modelElement);
			getIdToEObjectCache().remove(id);
		}
	}
}
