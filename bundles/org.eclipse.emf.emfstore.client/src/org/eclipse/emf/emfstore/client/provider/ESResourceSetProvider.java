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
package org.eclipse.emf.emfstore.client.provider;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Interface for resource set provider.
 * 
 * @author jfaltermeier
 * 
 */
public interface ESResourceSetProvider {

	/**
	 * Return fully configured ResourceSet.
	 * 
	 * @return the ResourceSet
	 */
	ResourceSet getResourceSet();
}
