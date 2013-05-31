/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model.util;

import org.eclipse.emf.common.notify.Notification;

/**
 * Abstract listener class for inner class declarations.
 * 
 * @author koegel
 * @deprecated
 */
@Deprecated
public abstract class ModelElementChangeListenerImpl implements ModelElementChangeListener {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.ModelElementChangeListener#onChange(org.eclipse.emf.common.notify.Notification)
	 */
	public abstract void onChange(Notification notification);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.ModelElementChangeListener#onRuntimeExceptionInListener(java.lang.RuntimeException)
	 */
	public abstract void onRuntimeExceptionInListener(RuntimeException exception);

}
