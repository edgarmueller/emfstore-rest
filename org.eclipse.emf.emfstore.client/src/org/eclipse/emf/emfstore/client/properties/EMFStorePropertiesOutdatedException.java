/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.properties;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.EMFStoreProperty;

public class EMFStorePropertiesOutdatedException extends Exception {

	private static final long serialVersionUID = 2732549512392764249L;
	private final List<EMFStoreProperty> outdatedProperties;

	public EMFStorePropertiesOutdatedException(List<EMFStoreProperty> outdatedProperties) {
		this.outdatedProperties = outdatedProperties;
	}

	public List<EMFStoreProperty> getOutdatedProperties() {
		return outdatedProperties;
	}
}
