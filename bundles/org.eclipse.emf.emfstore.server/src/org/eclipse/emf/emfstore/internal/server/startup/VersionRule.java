/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.startup;

/**
 * @author jfaltermeier
 * 
 */
public class VersionRule implements UpdateXMIAttributeRule {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.startup.UpdateXMIAttributeRule#getNewAttribute(java.lang.String)
	 */
	public String getNewAttribute(String oldAttr) {
		final String[] fragments = oldAttr.split("-"); //$NON-NLS-1$
		return "versions/" + fragments[1].split("\\.")[0] + "#/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
