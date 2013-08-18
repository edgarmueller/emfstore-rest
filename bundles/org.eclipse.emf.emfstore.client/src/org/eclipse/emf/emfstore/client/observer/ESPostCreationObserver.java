/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * <p>
 * Enables the modification of attributes of newly created model elements.
 * </p>
 * <b>IMPORTANT</b>: do not modify any references in the {@code onCreation(EObject)} method.
 * This interfaces is only intended to modify attributes of a model element.
 * 
 * @author emueller
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESPostCreationObserver extends ESObserver {

	/**
	 * <p>
	 * Called when a new model element has been created.
	 * </p>
	 * <p>
	 * Use this method to modify attributes of a newly created model element. Do <b>not</b> modify any references.
	 * </p>
	 * 
	 * @param modelElement
	 *            the model element that has been created
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void onCreation(EObject modelElement);
}
