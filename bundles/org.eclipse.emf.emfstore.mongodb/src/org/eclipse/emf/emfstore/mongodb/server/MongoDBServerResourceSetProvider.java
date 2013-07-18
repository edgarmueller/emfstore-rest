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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.common.extensionpoint.ESServerResourceSetProvider;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipselabs.mongo.emf.ext.IResourceSetFactory;

/**
 * MongoDB ResourceSet provider for EMFStore Server.
 * 
 * @author jfaltermeier
 * 
 */
public class MongoDBServerResourceSetProvider implements ESServerResourceSetProvider {

	private static IResourceSetFactory resourceSetFactory;

	/**
	 * Sets the resource set factory.
	 * 
	 * @param factory the factory
	 */
	public static void setResourceSetFactory(IResourceSetFactory factory) {
		MongoDBServerResourceSetProvider.resourceSetFactory = factory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.provider.ESResourceSetProvider#getResourceSet()
	 */
	public ResourceSet getResourceSet() {
		// resourceSetFactory may not be binded yet
		int runs = 0;
		while (resourceSetFactory == null) {
			try {
				if (runs == 20) {
					return null;
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// do nothing
			}
			runs++;
		}
		ResourceSetImpl resourceSet = (ResourceSetImpl) resourceSetFactory.createResourceSet();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		resourceSet.setURIConverter(createURIConverter(resourceSet));
		// resourceSet.setURIResourceMap(new LinkedHashMap<URI, Resource>());
		return resourceSet;
	}

	private URIConverter createURIConverter(ResourceSetImpl resourceSet) {
		// reuse uri handlers set up by resourcesetfactory
		EList<URIHandler> uriHandler = resourceSet.getURIConverter().getURIHandlers();
		URIConverter uriConverter = new MongoServerURIConverter();
		uriConverter.getURIHandlers().clear();
		uriConverter.getURIHandlers().addAll(uriHandler);
		return uriConverter;
	}

	public void registerDynamicModels() {
		// // put ecore in db
		// ResourceSet resourceSet = new ResourceSetImpl();
		// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
		// .put("ecore", new EcoreResourceFactoryImpl());
		// Resource resource = resourceSet.getResource(
		// URI.createFileURI(new File("D:" + File.separator + "Repositories" + File.separator
		// + "eclipse-platform-evaluation" + File.separator + "bundles" + File.separator + "org.openetcs.model"
		// + File.separator + "model" + File.separator + "model.ecore").getAbsolutePath()), true);
		// EPackage model = (EPackage) resource.getContents().get(0);
		//
		// // TODO
		// URI dynamicModelsURI = URI.createURI("mongodb://localhost/esserver/dynamic-models/");
		// Resource resource2 = getResourceSet().getResource(dynamicModelsURI, true);
		//
		// resource2.getContents().add(model);
		// try {
		// resource2.save(null);
		// } catch (IOException e) {
		//
		// }
		//
		// URI dynamicModelsURI = URI.createURI("mongodb://localhost/esserver/dynamic-models/");
		// ResourceSet resourceSet = getResourceSet();
		// resourceSet.getURIConverter().exists(dynamicModelsURI, null);
		// Resource resource = resourceSet.getResource(dynamicModelsURI, true);
	}
}
