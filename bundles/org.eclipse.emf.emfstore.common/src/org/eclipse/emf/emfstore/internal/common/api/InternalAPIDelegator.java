/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.api;

/**
 * <p>
 * Interface that is meant to be implemented by all API implementation classes.
 * </p>
 * The interface enables it to navigate from the API implementation type to the internal 
 * type in order to delegate the actual work to be done.
 * 
 * @author emueller
 *
 * @param <INTERNAL_TYPE> the internal type the API implementation is mapping to
 * @param <API_IMPL_TYPE> the API implementation type
 */
public interface InternalAPIDelegator<API_IMPL_TYPE, INTERNAL_TYPE extends APIDelegate<? extends API_IMPL_TYPE>> {

	INTERNAL_TYPE getInternalAPIImpl();
	
}
