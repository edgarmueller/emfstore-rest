/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.handler;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * This interface allows to hook into the operation recorder and to modify the recorded operations.
 * Generally this is useful for combining similar operations into composite operations.
 * 
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESOperationModifier {

	/**
	 * ID of the {@link ESOperationModifier} option.
	 */
	String ID = "org.eclipse.emf.emfstore.client.handler.operationModifier"; //$NON-NLS-1$

	/**
	 * <p>
	 * Allows to modify the recorded operations.
	 * </p>
	 * <p>
	 * <b>NOTE:</b> This method is only called if commands are used.
	 * </p>
	 * 
	 * @param operations
	 *            the recorded operations that may be modified
	 * @param command
	 *            the triggering command
	 * @return a possibly modified list of operations
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	List<AbstractOperation> modify(List<AbstractOperation> operations, Command command);

}
