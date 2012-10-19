/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * EMFStore Resource, inherits from XMIResource and sets Intrinsic ID to EObjectMap optimization.
 * @author User
 *
 */
public class EMFStoreResource extends XMIResourceImpl {

	/**
	 * Default constructor.
	 * @param uri the uri
	 */
	public EMFStoreResource(URI uri) {
		super(uri);
		this.setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
	}
	
	/**
	 * Initialize the id to Eobjects map and reverse map directly. The map must be consistent with each other.
	 * 
	 * @param idToEObjectMap a map from ids to eobjects in the resource
	 * @param eObjectToIdMap a map from eobjects to ids in the resource
	 */
	public void setIdToEObjectMap(Map<String, EObject> idToEObjectMap, Map<EObject, String> eObjectToIdMap) {
		this.idToEObjectMap = idToEObjectMap;
		this.eObjectToIDMap = eObjectToIdMap;
	}
}
