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
package org.eclipse.emf.emfstore.mongodb.client;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.mongodb.AbstractMongoDBResourceSetProvider;

/**
 * MongoDB ResourceSet provider for EMFStore Client.
 * 
 * @author jfaltermeier
 * 
 */
public class MongoDBClientResourceSetProvider extends AbstractMongoDBResourceSetProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.mongodb.AbstractMongoDBResourceSetProvider#setURIConverter(org.eclipse.emf.ecore.resource.impl.ResourceSetImpl)
	 */
	@Override
	protected void setURIConverter(ResourceSetImpl resourceSet) {
		// reuse uri handlers set up by resourcesetfactory
		EList<URIHandler> uriHandler = resourceSet.getURIConverter().getURIHandlers();
		URIConverter uriConverter = new MongoClientURIConverter();
		uriConverter.getURIHandlers().clear();
		uriConverter.getURIHandlers().addAll(uriHandler);
		resourceSet.setURIConverter(uriConverter);
	}

}
