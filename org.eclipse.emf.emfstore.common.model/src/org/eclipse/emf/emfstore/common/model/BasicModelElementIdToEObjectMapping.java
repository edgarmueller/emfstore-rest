/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.common.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Edgar
 * 
 */
public class BasicModelElementIdToEObjectMapping implements IModelElementIdToEObjectMapping {

	private Map<String, EObject> idToEObjectMapping;
	private Map<EObject, String> eObjectToIdMapping;

	public BasicModelElementIdToEObjectMapping() {
		idToEObjectMapping = new LinkedHashMap<String, EObject>();
		eObjectToIdMapping = new LinkedHashMap<EObject, String>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping#getIdToEObjectMapping()
	 */
	public Map<String, EObject> getIdToEObjectMapping() {
		return idToEObjectMapping;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping#getEObjectToIdMapping()
	 */
	public Map<EObject, String> getEObjectToIdMapping() {
		return eObjectToIdMapping;
	}

	public void put(EObject eObject, ModelElementId id) {
		eObjectToIdMapping.put(eObject, id.getId());
		idToEObjectMapping.put(id.getId(), eObject);
	}

	public void putAll(IModelElementIdToEObjectMapping otherIdToEObjectMapping) {
		eObjectToIdMapping.putAll(otherIdToEObjectMapping.getEObjectToIdMapping());
		idToEObjectMapping.putAll(otherIdToEObjectMapping.getIdToEObjectMapping());
	}

}
