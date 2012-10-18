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
package org.eclipse.emf.emfstore.client.model.impl;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * A callback to implement code that applies to a project.
 * 
 * @author koegel
 * 
 */
public interface IApplyChangesCallback {

	/**
	 * Apply a list of operations to a projectSpace.
	 * 
	 * @param projectSpace the project space
	 * @param operations the operations
	 * @param addOperations true if operations should be added as recorded operations
	 */
	void applyChangesIntern(ProjectSpace projectSpace, List<AbstractOperation> operations, boolean addOperations);
}
