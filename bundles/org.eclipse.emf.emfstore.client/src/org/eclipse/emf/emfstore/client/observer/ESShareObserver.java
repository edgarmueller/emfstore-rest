/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Observer that is notified when a share completed successfully.
 * 
 * @author emueller
 */
public interface ESShareObserver extends ESObserver {

	/**
	 * Called when the share of the passed {@link ESLocalProject} completed successfully.
	 * 
	 * @param localProject
	 *            the local project that has been shared
	 */
	void shareDone(ESLocalProject localProject);
}
