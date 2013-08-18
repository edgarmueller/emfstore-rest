/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.config;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.emfstore.internal.client.model.util.DefaultWorkspaceLocationProvider;

public class ClientLocationProvider extends DefaultWorkspaceLocationProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.util.DefaultWorkspaceLocationProvider#getRootDirectory()
	 */
	@Override
	protected String getRootDirectory() {
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	}

}
