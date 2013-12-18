/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common;

import java.util.Map;

import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;

/**
 * Replacement for the default {@link URIHandlerImpl} that will always set a timeout.
 * 
 * @author jfaltermeier
 * 
 */
public class EMFStoreURIHandler extends URIHandlerImpl {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.URIHandlerImpl#getTimeout(java.util.Map)
	 */
	@Override
	protected int getTimeout(Map<?, ?> options) {
		return 1000;
	}

}
