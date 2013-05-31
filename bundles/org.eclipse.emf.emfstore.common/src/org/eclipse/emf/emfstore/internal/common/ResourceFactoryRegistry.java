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
package org.eclipse.emf.emfstore.internal.common;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * A Registry for EMFResources that will return itself as Factory and always provide an EMFStore Resource.
 * 
 * @author emueller
 */
// TODO: internal
public class ResourceFactoryRegistry extends XMIResourceFactoryImpl implements Resource.Factory.Registry {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Resource createResource(final URI uri) {
		return new EMFStoreResource(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory.Registry#getFactory(org.eclipse.emf.common.util.URI)
	 */
	public Factory getFactory(final URI uri) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory.Registry#getFactory(org.eclipse.emf.common.util.URI,
	 *      java.lang.String)
	 */
	public Factory getFactory(final URI uri, final String contentType) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory.Registry#getProtocolToFactoryMap()
	 */
	public Map<String, Object> getProtocolToFactoryMap() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory.Registry#getExtensionToFactoryMap()
	 */
	public Map<String, Object> getExtensionToFactoryMap() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory.Registry#getContentTypeToFactoryMap()
	 */
	public Map<String, Object> getContentTypeToFactoryMap() {
		return null;
	}
}
