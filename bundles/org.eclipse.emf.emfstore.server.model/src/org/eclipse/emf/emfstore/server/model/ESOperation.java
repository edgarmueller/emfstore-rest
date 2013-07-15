/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model;

/**
 * <p>
 * Represents a change operation on the contents of a project.
 * </p>
 * <p>
 * <b>NOTE</b>: Currently the interface is empty, but it will eventually contain methods to determine the nature of the
 * change of the operation, e.g. changed object and type of change. Also operations will be applicable to projects and
 * reversable. At the moment of writing only the internal API offers these methods.
 * </p>
 * 
 * 
 * @author mkoegel
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESOperation {

}
