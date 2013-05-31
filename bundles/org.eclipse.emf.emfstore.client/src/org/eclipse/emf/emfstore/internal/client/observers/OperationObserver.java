/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * hodaie
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.observers;

import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Operation observers are added to a project space and informed whenever an operation is executed or undone.
 * 
 * @author hodaie
 */
public interface OperationObserver extends ESObserver {

	/**
	 * Called when an {@link AbstractOperation} has been executed.
	 * 
	 * @param operation
	 *            the executed operation
	 */
	void operationExecuted(AbstractOperation operation);

	/**
	 * Called when an {@link AbstractOperation} has been reversed.
	 * 
	 * @param operation
	 *            the operation that has been reversed.<br/>
	 *            <b>Note</b>: the given operation is not reserved. If you wish to get
	 *            the reversed operation, call {@link AbstractOperation#reverse()} on {@code operation}
	 */
	void operationUndone(AbstractOperation operation);
}
