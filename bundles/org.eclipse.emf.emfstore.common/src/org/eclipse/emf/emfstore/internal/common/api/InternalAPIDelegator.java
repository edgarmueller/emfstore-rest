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
 * <p>
 * Interface that is meant to be implemented by all API implementation classes.
 * </p>
 * The interface enables it to navigate from the API type to the internal
 * type in order to delegate the actual work to be done.
 * 
 * @author emueller
 * 
 * @param <INTERNAL> the internal type the API is mapping onto
 * @param <API> the API type
 */
public interface InternalAPIDelegator<API, INTERNAL extends APIDelegate<? extends API>> {

	/**
	 * Returns the internal interface of this class.
	 * 
	 * @return the internal interface of this class
	 */
	INTERNAL toInternalAPI();

}
