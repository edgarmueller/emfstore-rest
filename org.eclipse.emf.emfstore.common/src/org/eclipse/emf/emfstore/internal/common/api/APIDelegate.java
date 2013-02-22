/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.api;

/**
 * @author Edgar
 * @param <U>
 * @param <INNER>
 * @param <API_IMPL_CLASS>
 * @param <INNER>
 * 
 */
public interface APIDelegate<API_IMPL_CLASS> {

	API_IMPL_CLASS getAPIImpl();
	
	API_IMPL_CLASS createAPIImpl();
}
