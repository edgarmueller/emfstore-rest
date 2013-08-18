/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation, API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model;

/**
 * Interface for representing a mapping from EObjects to their respective IDs and vice versa.
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESModelElementIdToEObjectMapping extends ESIdToEObjectMapping<ESModelElementId> {

}