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
package org.eclipse.emf.emfstore.internal.client.provider;

import java.util.LinkedHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.common.ESResourceSetProvider;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * This is the default resource set provider of EMFStore client which will be used if no extension is offered.
 * 
 * @author jfaltermeier
 * 
 */
public class ClientXMIResourceSetProvider implements ESResourceSetProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.ESResourceSetProvider#getResourceSet()
	 */
	public ResourceSet getResourceSet() {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		resourceSet.setURIConverter(new XMIClientURIConverter());
		resourceSet.setURIResourceMap(new LinkedHashMap<URI, Resource>());
		resourceSet.getLoadOptions().putAll(ModelUtil.getResourceLoadOptions());
		return resourceSet;
	}

}
