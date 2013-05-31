/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * mkoegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl;

import java.util.List;

import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Listener for operations recorded by operation recorder.
 * 
 * @author mkoegel
 * 
 */
public interface OperationRecorderListener extends ESObserver {

	/**
	 * Notify observer about recorded operations.
	 * 
	 * @param operations a list of operations
	 */
	void operationsRecorded(List<? extends AbstractOperation> operations);

}
