/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.mongodb.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.mongodb.ResourceSetFactoryProvider;
import org.eclipse.emf.emfstore.server.ESDynamicModelProvider;
import org.eclipse.emf.emfstore.server.ESServerURIUtil;
import org.eclipselabs.mongo.emf.ext.ECollection;

/**
 * MongoDB Dynamic Model Provider.
 * 
 * @author jfaltermeier
 * 
 */
@SuppressWarnings("restriction")
public class MongoDBDynamicModelProvider implements ESDynamicModelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.ESDynamicModelProvider#getDynamicModels()
	 */
	public List<EPackage> getDynamicModels() {
		final URI dynamicModelsURI = URI
			.createURI(MongoServerURIConverter.getMongoURIPrefix(ESServerURIUtil.getProfile())
				+ "dynamic-models/ecore"); //$NON-NLS-1$
		try {
			ResourceSetFactoryProvider.COUNT_DOWN_LATCH.await(10, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			ModelUtil.logException("Setup of Mongo-DB ResourceSet failed", e);
		}
		final ResourceSet resourceSet = ResourceSetFactoryProvider.getResourceSetFactory().createResourceSet();
		final Resource resource = resourceSet.getResource(dynamicModelsURI, true);

		final List<EPackage> result = new ArrayList<EPackage>();

		final EList<EObject> contents = resource.getContents();
		if (contents != null && contents.size() > 0) {
			final EObject object = contents.get(0);
			if (object instanceof EPackage) {
				result.add((EPackage) object);
			} else if (object instanceof ECollection) {
				final ECollection collection = (ECollection) object;
				for (final EObject o : collection.getValues()) {
					result.add((EPackage) o);
				}
			}
		}
		return result;
	}
}
