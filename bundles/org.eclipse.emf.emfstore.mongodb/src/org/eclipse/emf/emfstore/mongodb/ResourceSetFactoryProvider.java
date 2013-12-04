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
package org.eclipse.emf.emfstore.mongodb;

import java.util.concurrent.CountDownLatch;

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

	private static IResourceSetFactory resourceSetFactory;

	/**
	 * Returns the {@link IResourceSetFactory} or <code>null</code> if not binded.
	 * 
	 * @return the resource set factory
	 */
	public static IResourceSetFactory getResourceSetFactory() {
		return resourceSetFactory;
	}

	/**
	 * Binds the resource set factory.
	 * 
	 * @param resourceSetFactory the resource set factory
	 */
	void bindResourceSetFactory(IResourceSetFactory resourceSetFactory) {
		ResourceSetFactoryProvider.resourceSetFactory = resourceSetFactory;
		COUNT_DOWN_LATCH.countDown();
	}

}
