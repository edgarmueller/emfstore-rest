/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util;

import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Provides the author for a given operation.
 * 
 * @author mkoegel
 * 
 */
public interface OperationAuthorProvider extends ESObserver {

	/**
	 * Get the author for the given operation.
	 * 
	 * @param operation the operation
	 * @return an author String
	 */
	String getAuthor(AbstractOperation operation);

}
