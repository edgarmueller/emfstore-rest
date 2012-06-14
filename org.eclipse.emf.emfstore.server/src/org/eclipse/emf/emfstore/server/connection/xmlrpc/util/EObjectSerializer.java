/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.connection.xmlrpc.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.ws.commons.util.Base64;
import org.apache.ws.commons.util.Base64.Encoder;
import org.apache.ws.commons.util.Base64.EncoderOutputStream;
import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.common.CommonUtil;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Serializer for EObjects.
 * 
 * @author emueller
 */
public class EObjectSerializer extends TypeSerializerImpl {

	/**
	 * EObject Tag for parsing.
	 */
	public static final String EOBJECT_TAG = "EObject";
	private static final String EX_EOBJECT_TAG = "ex:" + EOBJECT_TAG;
	private static boolean hrefCheckEnabled;
	private static boolean containmentCheckEnabled;
	private static boolean serializationOptionsInitialized;

	/**
	 * {@inheritDoc}
	 */
	public void write(ContentHandler pHandler, Object pObject) throws SAXException {
		initSerializationOptions();
		pHandler.startElement("", VALUE_TAG, VALUE_TAG, ZERO_ATTRIBUTES);
		pHandler.startElement("", EOBJECT_TAG, EX_EOBJECT_TAG, ZERO_ATTRIBUTES);
		char[] buffer = new char[1024];
		Encoder encoder = new Base64.SAXEncoder(buffer, 0, null, pHandler);
		try {
			URIConverter.WriteableOutputStream uws = null;
			OutputStream ostream = new EncoderOutputStream(encoder);
			BufferedOutputStream bos = new BufferedOutputStream(ostream);
			try {
				EObject eObject = (EObject) pObject;
				if (eObject instanceof ChangePackage || eObject instanceof IdEObjectCollection) {
					OutputStreamWriter writer = new OutputStreamWriter(bos);
					Resource res = eObject.eResource();
					uws = new URIConverter.WriteableOutputStream(writer, "UTF-8");
					checkResource(res);
					res.save(uws, ModelUtil.getResourceSaveOptions());
				} else {

					Resource res = (new ResourceSetImpl()).createResource(ModelUtil.VIRTUAL_URI);
					((ResourceImpl) res).setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
					EObject copy = ModelUtil.clone(eObject);
					res.getContents().add(copy);

					StringWriter stringWriter = new StringWriter();
					uws = new URIConverter.WriteableOutputStream(stringWriter, "UTF-8");
					// save string into Stringwriter
					checkResource(res);
					res.save(uws, ModelUtil.getResourceSaveOptions());
					// get string from string writer and werite to bos
					String string = stringWriter.toString();
					hrefCheck(string);
					bos.write(string.getBytes());
				}
			} catch (SerializationException e) {
				throw new SAXException(e);
			} finally {
				bos.close();
				if (uws != null) {
					uws.close();
				}
			}
		} catch (Base64.SAXIOException e) {
			throw e.getSAXException();
		} catch (IOException e) {
			throw new SAXException(e);
		}
		pHandler.endElement("", EOBJECT_TAG, EX_EOBJECT_TAG);
		pHandler.endElement("", VALUE_TAG, VALUE_TAG);
	}

	private void checkResource(Resource resource) throws SerializationException {
		if (!containmentCheckEnabled) {
			return;
		}

		if (resource.getContents().size() != 1) {
			throw new SerializationException("Resource contains more or less than one EObject!");
		}
		EObject root = resource.getContents().get(0);
		Set<EObject> allChildEObjects = CommonUtil.getNonTransientContents(root);
		Set<EObject> allEObjects = new HashSet<EObject>(allChildEObjects);
		allEObjects.add(root);
		for (EObject eObject : allEObjects) {
			if (resource != eObject.eResource()) {
				throw new SerializationException("Resource is not self-contained!");
			}
			if (eObject.eIsProxy()) {
				throw new SerializationException("Serialization failed due to unresolved proxy detection.");
			}
		}
	}

	private static void hrefCheck(String result) throws SerializationException {
		if (!hrefCheckEnabled) {
			return;
		}
		char[] needle = "href".toCharArray();
		int pointer = 0;
		boolean insideQuotes = false;
		for (char character : result.toCharArray()) {
			if (character == '"') {
				insideQuotes = !insideQuotes;
			}
			if (!insideQuotes && character == needle[pointer]) {
				if (++pointer == needle.length) {
					throw new SerializationException("Serialization failed due to href detection.");
				}
			} else {
				pointer = 0;
			}
		}
	}

	/**
	 * Initializes the serialization options.
	 */
	private static void initSerializationOptions() {

		if (serializationOptionsInitialized) {
			return;
		}
		ExtensionElement element = new ExtensionPoint("org.eclipse.emf.emfstore.common.model.serializationoptions")
			.getFirst();

		if (element != null) {
			hrefCheckEnabled = element.getBoolean("HrefCheck");
			containmentCheckEnabled = element.getBoolean("SelfContainmentCheck");
		}

		serializationOptionsInitialized = true;
	}
}
