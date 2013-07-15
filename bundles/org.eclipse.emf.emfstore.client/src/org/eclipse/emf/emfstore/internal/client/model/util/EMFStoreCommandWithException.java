/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.util;

public abstract class EMFStoreCommandWithException<T> extends EMFStoreCommand {

	private T excpetion;

	public T getException() {
		return excpetion;
	}

	public void setException(T excpetion) {
		this.excpetion = excpetion;
	}

	public boolean hasException() {
		return excpetion != null;
	}
}
