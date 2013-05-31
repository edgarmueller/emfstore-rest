/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.parser.ByteArrayParser;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException;

/**
 * Parser for EObjects.
 * 
 * @author emueller
 */
public class EObjectTypeParser extends ByteArrayParser {

	@Override
	public Object getResult() throws XmlRpcException {
		try {
			byte[] res = (byte[]) super.getResult();
			ByteArrayInputStream bais = new ByteArrayInputStream(res);
			BufferedReader reader = new BufferedReader(new InputStreamReader(bais, CommonUtil.getEncoding()));
			URIConverter.ReadableInputStream ris = new URIConverter.ReadableInputStream(reader,
				CommonUtil.getEncoding());
			try {
				XMIResource resource = (XMIResource) (new ResourceSetImpl()).createResource(ModelUtil.VIRTUAL_URI);
				((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
				resource.load(ris, ModelUtil.getResourceLoadOptions());
				return getResultfromResource(resource);
			} catch (UnsupportedEncodingException e) {
				throw new XmlRpcException("Couldn't parse EObject: " + e.getMessage(), e);
			} catch (IOException e) {
				throw new XmlRpcException("Couldn't parse EObject: " + e.getMessage(), e);
			} catch (SerializationException e) {
				throw new XmlRpcException("Couldn't parse EObject: " + e.getMessage(), e);
			} finally {
				ris.close();
			}
		} catch (IOException e) {
			throw new XmlRpcException("Failed to read result object: " + e.getMessage(), e);
		}
	}

	private static EObject getResultfromResource(XMIResource res) throws SerializationException {
		EObject result = res.getContents().get(0);

		if (result instanceof IdEObjectCollection) {
			IdEObjectCollection collection = (IdEObjectCollection) result;
			Map<EObject, String> eObjectToIdMap = new LinkedHashMap<EObject, String>();
			Map<String, EObject> idToEObjectMap = new LinkedHashMap<String, EObject>();

			for (EObject modelElement : collection.getAllModelElements()) {
				String modelElementId;
				if (ModelUtil.isIgnoredDatatype(modelElement)) {
					// create random ID for generic types, won't get serialized
					// anyway
					modelElementId = ModelFactory.eINSTANCE.createModelElementId().getId();
				} else {
					modelElementId = res.getID(modelElement);
				}

				if (modelElementId == null) {
					throw new SerializationException("Failed to retrieve ID for EObject contained in project: "
						+ modelElement);
				}

				eObjectToIdMap.put(modelElement, modelElementId);
				idToEObjectMap.put(modelElementId, modelElement);
			}

			collection.initMapping(eObjectToIdMap, idToEObjectMap);
		}

		EcoreUtil.resolveAll(result);
		res.getContents().remove(result);

		return result;
	}
}
