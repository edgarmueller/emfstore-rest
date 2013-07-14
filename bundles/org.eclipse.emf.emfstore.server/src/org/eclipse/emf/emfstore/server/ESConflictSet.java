/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.server;

import java.util.Set;

/**
 * Represents conflicting changes on model objects.
 * 
 * Currently no further information is provided.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESConflictSet {

	/**
	 * Get the conflicts of the conflict set.
	 * 
	 * @return a set of conflicts
	 */
	Set<ESConflict> getConflicts();
}
