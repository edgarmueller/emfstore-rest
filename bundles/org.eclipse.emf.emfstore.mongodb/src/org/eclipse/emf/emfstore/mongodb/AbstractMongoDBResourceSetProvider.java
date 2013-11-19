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

import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.common.ESResourceSetProvider;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Abstract {@link ESResourceSetProvider} for Mongo DB.
 * 
 * @author jfaltermeier
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractMongoDBResourceSetProvider implements ESResourceSetProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.ESResourceSetProvider#getResourceSet()
	 */
	public ResourceSet getResourceSet() {
		try {
			ResourceSetFactoryProvider.COUNT_DOWN_LATCH.await(10, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			ModelUtil.logException("Setup of Mongo-DB ResourceSet failed", e);
		}
		final ResourceSetImpl resourceSet = (ResourceSetImpl) ResourceSetFactoryProvider.getResourceSetFactory()
			.createResourceSet();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		setURIConverter(resourceSet);
		return resourceSet;
	}

	/**
	 * Creates and adds a URIConverter to the ResourceSet.
	 * 
	 * @param resourceSet the resourceset for which the uri converter is created.
	 */
	protected abstract void setURIConverter(ResourceSetImpl resourceSet);
}
