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
package org.eclipse.emf.emfstore.server;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;

/**
 * Interface for dynamic model provider.
 * 
 * @author jfaltermeier
 * 
 */
public interface ESDynamicModelProvider {

	/**
	 * Returns a list of all dynamic models which shall be added to the server's EPackage-Registry.
	 * 
	 * @return a list of all dynamic models
	 */
	List<EPackage> getDynamicModels();
}
