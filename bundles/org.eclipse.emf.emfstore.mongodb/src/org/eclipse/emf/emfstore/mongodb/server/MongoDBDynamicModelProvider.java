/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 ******************************************************************************/
package org.eclipse.emf.emfstore.mongodb.server;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.emfstore.server.ESDynamicModelProvider;
import org.eclipse.emf.emfstore.server.ServerURIUtil;
import org.eclipselabs.mongo.emf.ext.ECollection;

/**
 * MongoDB Dynamic Model Provider.
 * 
 * @author jfaltermeier
 * 
 */
public class MongoDBDynamicModelProvider implements ESDynamicModelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.ESDynamicModelProvider#getDynamicModels()
	 */
	public List<EPackage> getDynamicModels() {
		URI dynamicModelsURI = URI.createURI(MongoServerURIConverter.getMongoURIPrefix(ServerURIUtil.getProfile())
			+ "dynamic-models/ecore");
		ResourceSet resourceSet = MongoDBServerResourceSetProvider.resourceSetFactory.createResourceSet();
		Resource resource = resourceSet.getResource(dynamicModelsURI, true);

		List<EPackage> result = new ArrayList<EPackage>();

		EList<EObject> contents = resource.getContents();
		if (contents != null && contents.size() > 0) {
			EObject object = contents.get(0);
			if (object instanceof EPackage) {
				result.add((EPackage) object);
			} else if (object instanceof ECollection) {
				ECollection collection = (ECollection) object;
				for (EObject o : collection.getValues()) {
					result.add((EPackage) o);
				}
			}
		}
		return result;
	}
}
