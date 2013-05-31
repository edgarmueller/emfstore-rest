/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.exceptions;

import java.util.List;

import org.eclipse.emf.emfstore.internal.common.model.EMFStoreProperty;

/**
 * Exception that indicates that some client-side properties are outdated.
 * 
 * @author emueller
 */
public class EMFStorePropertiesOutdatedException extends Exception {

	private static final long serialVersionUID = 2732549512392764249L;
	private final List<EMFStoreProperty> outdatedProperties;

	/**
	 * Constructor.
	 * 
	 * @param outdatedProperties
	 *            the list of out dated properties.
	 */
	public EMFStorePropertiesOutdatedException(List<EMFStoreProperty> outdatedProperties) {
		this.outdatedProperties = outdatedProperties;
	}

	/**
	 * Returns the out dated properties.
	 * 
	 * @return the out dated properties.
	 */
	public List<EMFStoreProperty> getOutdatedProperties() {
		return outdatedProperties;
	}
}
