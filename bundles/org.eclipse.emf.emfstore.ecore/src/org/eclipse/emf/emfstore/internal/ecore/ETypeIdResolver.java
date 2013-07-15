/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.ecore;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESSingletonIdResolver;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;

public class ETypeIdResolver implements ESSingletonIdResolver {

	private Map<String, EClass> datatypes = new LinkedHashMap<String, EClass>();

	public ETypeIdResolver() {
		// eclass stuff
		datatypes.put("EClass", EcorePackage.eINSTANCE.getEClass());
		datatypes.put("EStructuralFeature", EcorePackage.eINSTANCE.getEStructuralFeature());
	}

	public EObject getSingleton(ESModelElementId singletonId) {
		if (singletonId == null) {
			return null;
		}

		return datatypes.get(singletonId.getId());
	}

	public ESModelElementId getSingletonModelElementId(EObject singleton) {
		if (!(singleton instanceof EClass || EStructuralFeature.class.isInstance(singleton)) || singleton == null) {
			return null;
		}

		// TODO: EM, provide 2nd map for performance reasons
		for (Map.Entry<String, EClass> entry : datatypes.entrySet()) {
			if (!entry.getValue().isInstance(singleton)) {
				continue;
			}

			// TODO: don't create IDs on the fly rather put them directly into the map
			ModelElementId id = ModelFactory.eINSTANCE.createModelElementId();
			id.setId(entry.getKey());
			return id.toAPI();
		}

		return null;
	}

	public boolean isSingleton(EObject eDataType) {
		return EClass.class.isInstance(eDataType) || EStructuralFeature.class.isInstance(eDataType);
	}

}
