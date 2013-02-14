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
package org.eclipse.emf.emfstore.internal.common.model.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.common.ESDisposable;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdGenerator;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Implementation of an ID based storage mechanism for {@link EObject}s.
 * 
 * @author emueller
 */
public abstract class IdEObjectCollectionImpl extends EObjectImpl implements IdEObjectCollection, ESDisposable,
	IModelElementIdToEObjectMapping {

	/**
	 * The extension point id to configure the {@link ESModelElementIdGenerator}.
	 */
	public static final String MODELELEMENTID_GENERATOR_EXTENSIONPOINT = "org.eclipse.emf.emfstore.common.model.modelelementIdGenerator";

	/**
	 * The attribute identifying the class of the {@link ESModelElementIdGenerator} extension point.
	 */
	public static final String MODELELEMENTID_GENERATOR_CLASS_ATTRIBUTE = "class";

	// Caches
	private Map<EObject, String> eObjectToIdMap;
	private Map<String, EObject> idToEObjectMap;

	// These caches will be used to assign specific IDs to newly created EObjects.
	// Additionally, IDs of deleted model elements will also be put into these caches, in case
	// the deleted elements will be restored during a command.
	private Map<EObject, String> allocatedEObjectToIdMap;
	private Map<String, EObject> allocatedIdToEObjectMap;

	private boolean cachesInitialized;

	/**
	 * A {@link ESModelElementIdGenerator} for other plugins to register a special ID generation.
	 */
	private ESModelElementIdGenerator modelElementIdGenerator;

	/**
	 * Constructor.
	 */
	public IdEObjectCollectionImpl() {
		eObjectToIdMap = new LinkedHashMap<EObject, String>();
		idToEObjectMap = new LinkedHashMap<String, EObject>();
		allocatedEObjectToIdMap = new LinkedHashMap<EObject, String>();
		allocatedIdToEObjectMap = new LinkedHashMap<String, EObject>();

		ESExtensionElement element = new ESExtensionPoint(MODELELEMENTID_GENERATOR_EXTENSIONPOINT)
			.getElementWithHighestPriority();
		if (element != null) {
			modelElementIdGenerator = element.getClass(MODELELEMENTID_GENERATOR_CLASS_ATTRIBUTE,
				ESModelElementIdGenerator.class);
		}
	}

	/**
	 * Constructor. Adds the contents of the given {@link XMIResource} as model
	 * elements to the collection. If the {@link XMIResource} also has XMI IDs
	 * assigned to the {@link EObject}s it contains, they will be used for
	 * creating the {@link ModelElementId}s within the project, if not, the {@link ModelElementId}s will get created on
	 * the fly.
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
			if (!xmiResource.isLoaded()) {
				ModelUtil.loadResource(xmiResource, ModelUtil.getResourceLogger());
			}
		} catch (IOException e) {
			ModelUtil.logException(String.format("XMIResource %s could not be loaded.", xmiResource.getURI()), e);
			throw e;
		}
		TreeIterator<EObject> it = xmiResource.getAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();

			if (ModelUtil.isIgnoredDatatype(eObject)) {
				continue;
			}

			String id = xmiResource.getID(eObject);
			ModelElementId eObjectId = getNewModelElementID();

			if (id != null) {
				eObjectId.setId(id);
				resourceHasIds = true;
			} else {
				xmiResource.setID(eObject, eObjectId.getId());
			}

			putIntoCaches(eObject, eObjectId.getId());
		}

		if (resourceHasIds) {
			cachesInitialized = true;
		}

		EList<EObject> contents = xmiResource.getContents();
		setModelElements(contents);

		if (!resourceHasIds) {
			// save, in order to write IDs back into resource
			ModelUtil.saveResource(xmiResource, ModelUtil.getResourceLogger());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getModelElements()
	 */
	public abstract EList<EObject> getModelElements();

	/**
	 * Sets the model elements of this collection.
	 * 
	 * @param modelElements
	 *            the new list of model elements the collection should hold
	 */
	protected abstract void setModelElements(EList<EObject> modelElements);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#addModelElement(org.eclipse.emf.ecore.EObject)
	 */
	public void addModelElement(EObject eObject) {
		getModelElements().add(eObject);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#containsInstance(org.eclipse.emf.ecore.EObject)
	 */
	public boolean contains(EObject modelElement) {
		return getEObjectsCache().contains(modelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#contains(org.eclipse.emf.emfstore.internal.common.model.ModelElementId)
	 */
	public boolean contains(ESModelElementId id) {
		if (!isCacheInitialized()) {
			initMapping();
		}
		return getIdToEObjectCache().containsKey(id);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getDeletedModelElementId(org.eclipse.emf.ecore.EObject)
	 */
	public ModelElementId getDeletedModelElementId(EObject deletedModelElement) {

		String id = allocatedEObjectToIdMap.get(deletedModelElement);

		if (id != null) {
			ModelElementId modelElementId = ModelFactory.eINSTANCE.createModelElementId();
			modelElementId.setId(id);
			return modelElementId;
		}

		return ModelUtil.getSingletonModelElementId(deletedModelElement);
	}

	/**
	 * Get the deleted model element with the given id from the collection.
	 * 
	 * @param modelElementId
	 *            a {@link ModelElementId}
	 * @return the deleted model element or null if it is not in the project
	 */
	public EObject getDeletedModelElement(ModelElementId modelElementId) {

		if (modelElementId == null) {
			return null;
		}

		EObject eObject = allocatedIdToEObjectMap.get(modelElementId);
		return eObject != null ? eObject : ModelUtil.getSingleton(modelElementId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getModelElementId(org.eclipse.emf.ecore.EObject)
	 */
	public ModelElementId getModelElementId(EObject eObject) {

		if (!eObjectToIdMap.containsKey(eObject) && !isCacheInitialized()) {

			// EObject contained in project, load ID from resource
			try {
				Resource resource = eObject.eResource();

				// EM: is this a potential error case we have to consider?
				if (!(resource instanceof XMIResource)) {
					return null;
				}

				XMIResource xmiResource = (XMIResource) resource;
				ModelUtil.loadResource(xmiResource, ModelUtil.getResourceLogger());
				ModelElementId modelElementId = getNewModelElementID();
				String id = xmiResource.getID(eObject);

				if (id != null) {
					// change generated ID if one has been found in the resource
					modelElementId.setId(id);
				}

				eObjectToIdMap.put(eObject, modelElementId.getId());
				return modelElementId;

			} catch (IOException e) {
				throw new RuntimeException("Couldn't load resource for model element " + eObject);
			}
		}

		String id = eObjectToIdMap.get(eObject);
		ModelElementId modelElementId = getNewModelElementID();
		modelElementId.setId(id);

		return id != null ? modelElementId : ModelUtil.getSingletonModelElementId(eObject);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getModelElement(org.eclipse.emf.emfstore.internal.common.model.ModelElementId)
	 */
	public EObject getModelElement(ESModelElementId modelElementId) {

		if (modelElementId == null) {
			return null;
		}

		if (!isCacheInitialized()) {
			initMapping();
		}

		EObject eObject = getIdToEObjectCache().get(((ModelElementId) modelElementId).getId());

		return eObject != null ? eObject : ModelUtil.getSingleton(modelElementId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#deleteModelElement(org.eclipse.emf.ecore.EObject)
	 */
	public void deleteModelElement(final EObject modelElement) {
		if (!this.contains(modelElement)) {
			throw new IllegalArgumentException("Cannot delete a model element that is not contained in this project.");
		}

		// remove cross references
		ModelUtil.deleteOutgoingCrossReferences(this, modelElement);
		Collection<Setting> crossReferences = UsageCrossReferencer.find(modelElement, this);
		ModelUtil.deleteIncomingCrossReferencesFromParent(crossReferences, modelElement);

		// remove containment
		EObject containerModelElement = ModelUtil.getContainerModelElement(modelElement);
		if (containerModelElement == null) {
			// removeModelElementAndChildrenFromCache(modelElement);
			// getEobjectsIdMap().remove(modelElement);
			this.getModelElements().remove(modelElement);
		} else {
			EReference containmentFeature = modelElement.eContainmentFeature();
			if (containmentFeature.isMany()) {
				EList<?> containmentList = (EList<?>) containerModelElement.eGet(containmentFeature);
				containmentList.remove(modelElement);
			} else {
				containerModelElement.eSet(containmentFeature, null);
			}

			removeModelElementAndChildrenFromResource(modelElement);
		}
	}

	/**
	 * Removes the the given {@link EObject} and all its contained children from
	 * their respective {@link XMIResource}s.
	 * 
	 * @param eObject
	 *            the {@link EObject} to remove
	 */
	public void removeModelElementAndChildrenFromResource(EObject eObject) {
		Set<EObject> children = ModelUtil.getAllContainedModelElements(eObject, false);
		for (EObject child : children) {
			removeModelElementFromResource(child);
		}
		removeModelElementFromResource(eObject);

	}

	/**
	 * Removes the the given {@link EObject} from its {@link XMIResource}.
	 * 
	 * @param xmiResource
	 *            the {@link EObject}'s resource
	 * @param eObject
	 *            the {@link EObject} to remove
	 */
	private void removeModelElementFromResource(EObject eObject) {

		if (!(eObject.eResource() instanceof XMIResource)) {
			return;
		}

		XMIResource xmiResource = (XMIResource) eObject.eResource();

		if (xmiResource.getURI() == null) {
			return;
		}

		xmiResource.setID(eObject, null);

		try {
			ModelUtil.saveResource(xmiResource, ModelUtil.getResourceLogger());
		} catch (IOException e) {
			throw new RuntimeException("XMI Resource for model element " + eObject + " could not be saved. "
				+ "Reason: " + e.getMessage());
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
				ModelUtil.loadResource(xmiResource, ModelUtil.getResourceLogger());
			} catch (IOException e) {
				throw new RuntimeException("Resource of model element " + modelElement + " couldn't be loaded");
			}
			String id = xmiResource.getID(modelElement);
			if (id != null) {
				ModelElementId objId = getNewModelElementID();
				objId.setId(id);
				return objId;
			}
		}

		// create new ID
		return getNewModelElementID();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getAllModelElements()
	 */
	public Set<EObject> getAllModelElements() {
		if (!isCacheInitialized()) {
			initMapping();
		}

		return Collections.unmodifiableSet(eObjectToIdMap.keySet());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getAllModelElementsByClass(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.common.util.EList)
	 */
	public <T extends EObject> EList<T> getAllModelElementsByClass(EClass modelElementClass, EList<T> list) {
		return getAllModelElementsByClass(modelElementClass, list, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.Project#getModelElementsByClass(org.eclipse.emf.ecore.EClass)
	 * @generated NOT
	 */
	// cast below is guarded by sanity check
	@SuppressWarnings("unchecked")
	public <T extends EObject> EList<T> getModelElementsByClass(EClass modelElementClass, EList<T> list) {

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
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getAllModelElementsByClass(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.common.util.EList, java.lang.Boolean)
	 */
	// two casts below are guarded by initial sanity check and if statement
	@SuppressWarnings("unchecked")
	public <T extends EObject> EList<T> getAllModelElementsByClass(EClass modelElementClass, EList<T> list,
		Boolean subclasses) {

		if (subclasses) {
			for (EObject modelElement : getAllModelElements()) {
				if (modelElementClass.isInstance(modelElement)) {
					list.add((T) modelElement);
				}
			}
		} else {
			for (EObject modelElement : getAllModelElements()) {
				if (modelElement.eClass() == modelElementClass) {
					list.add((T) modelElement);
				}
			}
		}

		return list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#getAllModelElementsByClass(java.lang.Class)
	 */
	public <T extends EObject> Set<T> getAllModelElementsByClass(Class<T> modelElementClass) {
		return getAllModelElementsByClass(modelElementClass, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.ESObjectContainer#getAllModelElementsByClass(java.lang.Class,
	 *      java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> Set<T> getAllModelElementsByClass(Class<T> modelElementClass, Boolean includeSubclasses) {
		LinkedHashSet<T> result = new LinkedHashSet<T>();
		if (includeSubclasses) {
			for (EObject modelElement : getAllModelElements()) {
				if (modelElementClass.isInstance(modelElement)) {
					result.add((T) modelElement);
				}
			}
		} else {
			for (EObject modelElement : getAllModelElements()) {
				if (modelElement.getClass() == modelElementClass) {
					result.add((T) modelElement);
				}
			}
		}
		return result;
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
	 * Returns the cache that maps {@link ModelElementId} to model elements.
	 * 
	 * @return a map containing mappings from {@link ModelElementId}s to model
	 *         element
	 */
	protected Map<String, EObject> getIdToEObjectCache() {
		if (!isCacheInitialized()) {
			initMapping();
		}

		return idToEObjectMap;
	}

	/**
	 * Returns the model element cache.
	 * 
	 * @return a set containing all model elements
	 */
	protected Set<EObject> getEObjectsCache() {
		if (!isCacheInitialized()) {
			initMapping();
		}

		return eObjectToIdMap.keySet();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#initMapping()
	 */
	public void initMapping() {

		if (isCacheInitialized()) {
			return;
		}

		for (EObject modelElement : getModelElements()) {
			putModelElementIntoCache(modelElement);
		}

		cachesInitialized = true;
	}

	/**
	 * Puts the given model element into the collections' caches.
	 * 
	 * @param modelElement
	 *            the model element which should be added to the caches
	 */
	protected void putModelElementIntoCache(EObject modelElement) {

		// put model element into cache
		ModelElementId modelElementId = getIdForModelElement(modelElement);
		putIntoCaches(modelElement, modelElementId.getId());

		// put children of model element into cache
		TreeIterator<EObject> it = modelElement.eAllContents();

		while (it.hasNext()) {
			EObject obj = it.next();
			ModelElementId id = getIdForModelElement(obj);
			putIntoCaches(obj, id.getId());
		}
	}

	/**
	 * Adds a model element and all its children to the caches.
	 * 
	 * @param modelElement
	 *            the model element, that should get added to the caches
	 */
	protected void addModelElementAndChildrenToCache(EObject modelElement) {

		HashSet<String> removableIds = new LinkedHashSet<String>();

		Set<EObject> containedModelElements = ModelUtil.getAllContainedModelElements(modelElement, false);
		containedModelElements.add(modelElement);

		for (EObject child : containedModelElements) {

			// first check whether ID should be reassigned
			String childId = allocatedEObjectToIdMap.get(child);

			if (childId == null) {
				// if not, create a new ID
				childId = getNewModelElementID().getId();
			} else {
				removableIds.add(childId);
			}

			if (isCacheInitialized()) {
				putIntoCaches(child, childId);
			}
		}

		// remove all IDs that are in use now
		for (String modelElementId : removableIds) {
			EObject eObject = allocatedIdToEObjectMap.get(modelElementId);
			allocatedEObjectToIdMap.remove(eObject);
		}

		allocatedIdToEObjectMap.keySet().removeAll(removableIds);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#initCaches(java.util.Map, java.util.Map)
	 */
	public void initMapping(Map<EObject, String> eObjectToIdMap, Map<String, EObject> idToEObjectMap) {
		cachesInitialized = true;
		this.eObjectToIdMap = eObjectToIdMap;
		this.idToEObjectMap = idToEObjectMap;
	}

	/**
	 * Creates a mapping for the given model element and the given {@link ModelElementId} within the cache.
	 * 
	 * @param modelElement
	 *            a model element
	 * @param modelElementId
	 *            a {@link ModelElementId}
	 */
	protected void putIntoCaches(EObject modelElement, String modelElementId) {
		eObjectToIdMap.put(modelElement, modelElementId);
		idToEObjectMap.put(modelElementId, modelElement);
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#dispose()
	 */
	public void dispose() {
		eObjectToIdMap.clear();
		idToEObjectMap.clear();
		clearAllocatedCaches();
		cachesInitialized = false;
	}

	/**
	 * Removes a model element and all its children from the cache.
	 * 
	 * @param modelElement
	 *            a model element to be removed from the cache
	 */
	protected void removeModelElementAndChildrenFromCache(EObject modelElement) {

		if (allocatedEObjectToIdMap.containsKey(modelElement)) {
			return;
		}

		removeFromCaches(modelElement);

		for (EObject child : ModelUtil.getAllContainedModelElements(modelElement, false)) {
			removeFromCaches(child);
		}
	}

	/**
	 * Removes the given model element from the caches.
	 * 
	 * @param modelElement
	 *            t#he model element to be removed from the caches
	 */
	private void removeFromCaches(EObject modelElement) {
		if (isCacheInitialized()) {
			ModelElementId id = this.getModelElementId(modelElement);

			allocatedEObjectToIdMap.put(modelElement, id.getId());
			allocatedIdToEObjectMap.put(id.getId(), modelElement);

			getEObjectsCache().remove(modelElement);
			getIdToEObjectCache().remove(id.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#allocateModelElementIds(java.util.Map)
	 */
	public void allocateModelElementIds(Map<EObject, ModelElementId> eObjectToIdMapping) {
		for (Map.Entry<EObject, ModelElementId> entry : eObjectToIdMapping.entrySet()) {
			EObject modelElement = entry.getKey();
			ModelElementId modelElementId = entry.getValue();

			Boolean isAlreadyContained = getModelElement(modelElementId) != null;

			if (isAlreadyContained) {
				eObjectToIdMap.put(modelElement, modelElementId.getId());
				idToEObjectMap.put(modelElementId.getId(), modelElement);
			}

			// do this even if the model element is already contained;
			// this is the case when a copied instance of the model element gets
			// added again
			allocatedEObjectToIdMap.put(modelElement, modelElementId.getId());
			allocatedIdToEObjectMap.put(modelElementId.getId(), modelElement);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#disallocateModelElementIds(java.util.Set)
	 */
	public void disallocateModelElementIds(Set<ModelElementId> modelElementIds) {
		for (ModelElementId modelElementId : modelElementIds) {
			allocatedIdToEObjectMap.remove(modelElementId.getId());
			allocatedEObjectToIdMap.values().remove(modelElementId.getId());
		}
	}

	/**
	 * Clear all caches.
	 */
	public void clearAllocatedCaches() {
		allocatedEObjectToIdMap.clear();
		allocatedIdToEObjectMap.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping#getEObjectId(org.eclipse.emf.ecore.EObject)
	 */
	public String getEObjectId(EObject eObject) {
		ModelElementId modelElementId = getModelElementId(eObject);

		if (modelElementId != null) {
			return modelElementId.getId();
		}

		return null;
	}

	private ModelElementId getNewModelElementID() {
		// if there is registered modelElementIdGenerator, use it
		if (modelElementIdGenerator != null) {
			return modelElementIdGenerator.generateModelElementId(this);
		}

		// else create it via ModelFactory
		return ModelFactory.eINSTANCE.createModelElementId();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping#get(org.eclipse.emf.emfstore.internal.common.model.ModelElementId)
	 */
	public EObject get(ModelElementId modelElementId) {
		EObject modelElement = getModelElement(modelElementId);
		if (modelElement != null) {
			return modelElement;
		}
		return getDeletedModelElement(modelElementId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getIdToEObjectMapping()
	 */
	public Map<String, EObject> getIdToEObjectMapping() {
		Map<String, EObject> mapping = new LinkedHashMap<String, EObject>(idToEObjectMap);
		mapping.putAll(new LinkedHashMap<String, EObject>(allocatedIdToEObjectMap));
		return mapping;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection#getEObjectToIdMapping()
	 */
	public Map<EObject, String> getEObjectToIdMapping() {
		Map<EObject, String> mapping = new LinkedHashMap<EObject, String>(eObjectToIdMap);
		mapping.putAll(new LinkedHashMap<EObject, String>(allocatedEObjectToIdMap));
		return mapping;
	}
}