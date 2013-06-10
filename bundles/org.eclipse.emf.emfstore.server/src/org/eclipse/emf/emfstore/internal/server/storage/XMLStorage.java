/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.storage;

import java.io.IOException;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.emfstore.common.URIUtil;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESResourceSetProvider;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Implementation of a {@link ResourceStorage} backed by an XMLResource.
 * 
 * @author koegel
 */
// TODO: internal
public class XMLStorage implements ResourceStorage {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.storage.ResourceStorage#init(java.util.Properties)
	 */
	public URI init(Properties properties) throws FatalESException {

		ESResourceSetProvider resourceSetProvider = new ESExtensionPoint(
			"org.eclipse.emf.emfstore.server.resourceSetProvider")
			.getClass("class",
				ESResourceSetProvider.class);

		if (resourceSetProvider == null) {
			resourceSetProvider = new ESServerXMIResourceSetProvider();
		}

		ResourceSet resourceSet = resourceSetProvider.getResourceSet();

		URI serverspaceURI = URIUtil.createServerSpaceURI();
		if (!resourceSet.getURIConverter().exists(serverspaceURI, null)) {
			try {
				Resource resource = resourceSet.createResource(serverspaceURI);
				ModelUtil.saveResource(resource, ModelUtil.getResourceLogger());
			} catch (IOException e) {
				throw new FatalESException("Could not init XMLRessource", e);
			}
		}
		return serverspaceURI;
	}
}
