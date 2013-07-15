/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model;

/**
 * Represents a change operation on the contents of a project.
 * Currently the interface is empty, but it will eventualy contain methods to determine the nature of the change of the
 * operation, e.g. changed object and type of change. Also operations will be applicable to projects and reversable.
 * Currently only the internal API offers these methods.
 * 
 * 
 * @author mkoegel
 * 
 */
public interface ESOperation {

}
