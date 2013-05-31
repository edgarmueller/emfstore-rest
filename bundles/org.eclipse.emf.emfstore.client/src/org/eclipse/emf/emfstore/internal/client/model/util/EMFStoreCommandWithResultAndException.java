/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.util;

/**
 * Command that can buffer a result for later retrieval.
 * 
 * @author koegel
 * @param <T> result type
 */
public abstract class EMFStoreCommandWithResultAndException<T, E> extends EMFStoreCommandWithResult<T> {

	private E excpetion;

	public E getExcpetion() {
		return excpetion;
	}

	public void setExcpetion(E excpetion) {
		this.excpetion = excpetion;
	}

	public boolean hasException() {
		return excpetion != null;
	}
}
