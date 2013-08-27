/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.api;

/**
 * Maps the implementing type onto an externally available API type.
 * 
 * @author emueller
 * 
 * @param <API> the API class which maps onto this internal type
 */
public interface APIDelegate<API> {

	/**
	 * Returns the API interface of this class.
	 * 
	 * @return the API interface of this class
	 */
	API toAPI();

	/**
	 * Creates the API interface for this class.
	 * 
	 * @return the API interface of this class
	 */
	API createAPI();
}
