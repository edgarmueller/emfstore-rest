/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.provider;

/**
 * Determines the version identifier of the client component.
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESClientVersionProvider {

	/**
	 * Returns the version of the client component.
	 * 
	 * @return the version of the client component
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	String getVersion();

	/**
	 * Returns the name of the client component.
	 * 
	 * @return the name of the client component
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	String getName();
}