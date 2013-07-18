/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Called when the workspace is initiated.
 * Use this for things which have to be initiated right at beginning.
 * It often is used to register Listeners.
 * 
 * @author emueller
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESWorkspaceInitObserver extends ESObserver {

	/**
	 * Is called when the workspace is initiated.
	 * 
	 * @param currentWorkspace workspace
	 */
	void workspaceInitComplete(ESWorkspace currentWorkspace);

}
