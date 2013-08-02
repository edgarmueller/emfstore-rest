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
package org.eclipse.emf.emfstore.common;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;

/**
 * Interface for resource set provider.
 * 
 * @author jfaltermeier
 * 
 */
public interface ESResourceSetProvider {

	/**
	 * Returns ResourceSet with load and save options configured and including {@link ResourceFactoryRegistry} and
	 * {@link URIConverter}.
	 * 
	 * @return the ResourceSet
	 */
	ResourceSet getResourceSet();
}
