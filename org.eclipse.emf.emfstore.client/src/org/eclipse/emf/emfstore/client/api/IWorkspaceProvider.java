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
package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;

/**
 * Central access point to recieve the current workspace.
 * 
 * @author emueller
 * @author wesendon
 * 
 */
public interface IWorkspaceProvider {

	/**
	 * Singleton instance of the workspace provider.
	 */
	IWorkspaceProvider INSTANCE = WorkspaceProvider.getInstance();

	/**
	 * Returns the current workspace.
	 * 
	 * @return current workspace.
	 */
	IWorkspace getWorkspace();

}
