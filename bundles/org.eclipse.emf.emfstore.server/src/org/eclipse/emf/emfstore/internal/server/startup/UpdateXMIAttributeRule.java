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
package org.eclipse.emf.emfstore.internal.server.startup;

/**
 * 
 * @author jfaltermeier
 * 
 */
public interface UpdateXMIAttributeRule {

	/**
	 * Computes the the new attribute based on the old value.
	 * 
	 * @param oldAttr the attribute before migration
	 * @return the attribute to be used after migration
	 */
	String getNewAttribute(String oldAttr);

}
