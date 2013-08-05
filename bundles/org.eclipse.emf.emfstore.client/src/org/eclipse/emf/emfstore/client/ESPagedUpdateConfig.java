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
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

/**
 * Configuration class for influencing the paged update config.
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESPagedUpdateConfig {

	/**
	 * The ID of an page update configuration class.
	 */
	String ID = "org.eclipse.emf.emfstore.client.pagedUpdate.config";

	/**
	 * Whether paged update should be used at all.
	 * 
	 * @return {@code true} if paged update should be used, {@code false} otherwise.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	boolean isEnabled();

	/**
	 * Returns the overall number of changes that constitute a page.
	 * 
	 * @return the number of allowed changes per page
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	int getNumberOfAllowedChanges();
}
