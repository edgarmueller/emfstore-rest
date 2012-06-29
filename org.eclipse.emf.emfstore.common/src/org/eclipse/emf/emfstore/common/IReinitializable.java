/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common;

/**
 * Models an object that has a lifecycle.
 * 
 * @author emueller 
 *
 */
public interface IReinitializable extends IDisposable {
	
	/**
	 * Whether the object has been disposed.
	 * 
	 * @return true, if the object has been disposed, false otherwise
	 */
	boolean isDisposed();

	/**
	 * Reinitializes the object.
	 */
	void reinit();
}
