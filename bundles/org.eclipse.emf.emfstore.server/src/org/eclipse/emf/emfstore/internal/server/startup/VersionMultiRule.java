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
 * @author jfaltermeier
 * 
 */
public class VersionMultiRule implements UpdateXMIAttributeRule {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.startup.UpdateXMIAttributeRule#getNewAttribute(java.lang.String)
	 */
	public String getNewAttribute(String oldAttr) {
		String result = ""; //$NON-NLS-1$
		final String[] versions = oldAttr.split(" "); //$NON-NLS-1$
		for (int i = 0; i < versions.length; i++) {
			final String versionOld = versions[i];
			final String id = versionOld.split("-")[1].split("\\.")[0]; //$NON-NLS-1$ //$NON-NLS-2$
			final String versionNew = id + "#/ "; //$NON-NLS-1$
			result = result + versionNew;
		}

		return result.substring(0, result.length() - 1);
	}

}
