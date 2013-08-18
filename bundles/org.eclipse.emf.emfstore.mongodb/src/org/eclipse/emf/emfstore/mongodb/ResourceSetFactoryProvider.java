/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.mongodb;

import java.util.concurrent.CountDownLatch;

import org.eclipse.emf.emfstore.mongodb.client.MongoDBClientResourceSetProvider;
import org.eclipse.emf.emfstore.mongodb.server.MongoDBDynamicModelProvider;
import org.eclipse.emf.emfstore.mongodb.server.MongoDBServerResourceSetProvider;
import org.eclipselabs.mongo.emf.ext.IResourceSetFactory;

/**
 * Helper class for binding the resource set factory.
 * 
 * @author jfaltermeier
 * 
 */
public class ResourceSetFactoryProvider {

	/**
	 * Synchronization tool for ResourceSetFactory-Service.
	 */
	public static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

	/**
	 * Binds the resource set factory.
	 * 
	 * @param resourceSetFactory the resource set factory
	 */
	void bindResourceSetFactory(IResourceSetFactory resourceSetFactory) {
		MongoDBClientResourceSetProvider.setResourceSetFactory(resourceSetFactory);
		MongoDBServerResourceSetProvider.setResourceSetFactory(resourceSetFactory);
		MongoDBDynamicModelProvider.setResourceSetFactory(resourceSetFactory);
		COUNT_DOWN_LATCH.countDown();
	}

}
