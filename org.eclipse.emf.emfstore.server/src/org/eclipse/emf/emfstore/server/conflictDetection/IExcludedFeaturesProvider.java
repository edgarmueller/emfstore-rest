/*******************************************************************************
 * Copyright 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.conflictDetection;

import java.util.Set;

/**
 * Provides a common interface to determine the features that should be ignored during conflict detection.
 * 
 * @author emuelller
 * 
 */
public interface IExcludedFeaturesProvider {

	/**
	 * Returns a list with the names of the features that should be ignored during conflict detection.
	 * 
	 * @return a list of feature names
	 */
	Set<String> getExcludedFeatures();
}
