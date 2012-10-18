/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.impl;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * This interface allows to hook into the operation recorder and to modify the recorded operations. Generally this is
 * useful for combining similar operations in composite operations.
 * 
 * @author wesendon
 */
public interface OperationModificator {

	/**
	 * Allows to modify the operations.
	 * 
	 * @param operations recorded operations
	 * @param command triggering command
	 * @return new list of operations
	 */
	List<AbstractOperation> modify(List<AbstractOperation> operations, Command command);

}
