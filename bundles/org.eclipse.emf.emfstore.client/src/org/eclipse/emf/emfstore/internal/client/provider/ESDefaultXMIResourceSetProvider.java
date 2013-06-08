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

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.emfstore.client.provider.ESResourceSetProvider;

/**
 * This is the default resource set provider of EMFStore which will be used if no extension is offered.
 * 
 * @author jfaltermeier
 * 
 */
public class ESDefaultXMIResourceSetProvider implements ESResourceSetProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.provider.ESResourceSetProvider#getResourceSet()
	 */
	public ResourceSet getResourceSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
