/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.ecore;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESSingletonIdResolver;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;

/**
 * An implementation of a {@link ESSingletonIdResolver} that treats all {@link EDataType}s as singletons.
 * 
 * @author emueller
 * 
 */
public class EDatatypeIdResolver implements ESSingletonIdResolver {

	private Map<String, EDataType> datatypes = new LinkedHashMap<String, EDataType>();

	/**
	 * Default constructor.
	 */
	public EDatatypeIdResolver() {
		datatypes.put("Literal", EcorePackage.eINSTANCE.getEString());
		// String
		datatypes.put("String", EcorePackage.eINSTANCE.getEString());
		datatypes.put("EString", EcorePackage.eINSTANCE.getEString());
		// Date
		datatypes.put("Date", EcorePackage.eINSTANCE.getEDate());
		datatypes.put("EDate", EcorePackage.eINSTANCE.getEDate());
		// integer
		datatypes.put("Int", EcorePackage.eINSTANCE.getEInt());
		datatypes.put("EInt", EcorePackage.eINSTANCE.getEInt());
		datatypes.put("Integer", EcorePackage.eINSTANCE.getEIntegerObject());
		datatypes.put("EInteger", EcorePackage.eINSTANCE.getEIntegerObject());
		datatypes.put("EIntegerObject", EcorePackage.eINSTANCE.getEIntegerObject());
		// double
		datatypes.put("Double", EcorePackage.eINSTANCE.getEDouble());
		datatypes.put("EDouble", EcorePackage.eINSTANCE.getEDouble());
		datatypes.put("EDoubleObject", EcorePackage.eINSTANCE.getEDoubleObject());
		// long
		datatypes.put("Long", EcorePackage.eINSTANCE.getELong());
		datatypes.put("ELong", EcorePackage.eINSTANCE.getELong());
		datatypes.put("ELongObject", EcorePackage.eINSTANCE.getELongObject());
		// float
		datatypes.put("Float", EcorePackage.eINSTANCE.getEFloat());
		datatypes.put("EFloat", EcorePackage.eINSTANCE.getEFloat());
		datatypes.put("EFloatObject", EcorePackage.eINSTANCE.getEFloatObject());
		// short
		datatypes.put("Short", EcorePackage.eINSTANCE.getEShort());
		datatypes.put("EShort", EcorePackage.eINSTANCE.getEShort());
		datatypes.put("EShortObject", EcorePackage.eINSTANCE.getEShortObject());
		// boolean
		datatypes.put("Boolean", EcorePackage.eINSTANCE.getEBoolean());
		datatypes.put("EBoolean", EcorePackage.eINSTANCE.getEBoolean());
		datatypes.put("EBooleanObject", EcorePackage.eINSTANCE.getEBooleanObject());
		// byte
		datatypes.put("Byte", EcorePackage.eINSTANCE.getEByte());
		datatypes.put("EByte", EcorePackage.eINSTANCE.getEByte());
		datatypes.put("EByteObject", EcorePackage.eINSTANCE.getEByteObject());
		datatypes.put("EByteArray", EcorePackage.eINSTANCE.getEByteArray());
		// char
		datatypes.put("EChar", EcorePackage.eINSTANCE.getEChar());
		datatypes.put("ECharacterObject", EcorePackage.eINSTANCE.getECharacterObject());
		datatypes.put("EBigDecimal", EcorePackage.eINSTANCE.getEBigDecimal());
		datatypes.put("EBigInteger", EcorePackage.eINSTANCE.getEBigInteger());
	}

	/**
	 * {@inheritDoc}
	 */
	public EObject getSingleton(ESModelElementId singletonId) {

		if (singletonId == null) {
			return null;
		}

		return datatypes.get(singletonId.getId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the {@link ESModelElementId} of the the singleton object or <code>null</code> if the given {@link EObject}
	 *         is not a singleton, is not an instance of {@link EDataType} or is <code>null</code>
	 */
	public ESModelElementId getSingletonModelElementId(EObject singleton) {

		if (!(singleton instanceof EDataType) || singleton == null) {
			return null;
		}

		// TODO: EM, provide 2nd map for performance reasons
		for (Map.Entry<String, EDataType> entry : datatypes.entrySet()) {
			if (entry.getValue() != singleton) {
				continue;
			}

			// TODO: don't create IDs on the fly rather put them directly into the map
			ModelElementId id = ModelFactory.eINSTANCE.createModelElementId();
			id.setId(entry.getKey());
			return id.toAPI();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSingleton(EObject eDataType) {

		if (!(eDataType instanceof EDataType) || eDataType == null) {
			return false;
		}

		return datatypes.containsValue(eDataType);
	}
}
