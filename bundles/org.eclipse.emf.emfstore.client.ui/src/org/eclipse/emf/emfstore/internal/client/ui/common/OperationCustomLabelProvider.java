/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Michael Kagel
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.common;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.ESDisposable;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Interface for a custom LabelProvider for {@link AbstractOperation}s.
 * 
 * @author Michael Kagel
 * @author emueller
 */
public interface OperationCustomLabelProvider extends ESDisposable {

	/**
	 * ID of the label provider.
	 */
	String ID = "org.eclipse.emf.emfstore.internal.client.ui.common.operationCustomLabelProvider";

	enum CanRender {
		/**
		 * Default constant for a renderer that is able to render the given element.
		 */
		Yes,
		/**
		 * Constant for render priority meaning this provider does not want to
		 * render the given element.
		 */
		No
	}

	/**
	 * Returns the name of the given model element.
	 * 
	 * @param modelElement
	 *            the model element whose name should be determined
	 * @return the name of the given model element
	 */
	String getModelElementName(EObject modelElement);

	/**
	 * Returns the description of an operation.
	 * 
	 * @param operation
	 *            the operation which needs to be visualized by the image
	 * @return the description of the operation
	 */
	String getDescription(AbstractOperation operation);

	/**
	 * Returns the image of an operation.
	 * 
	 * @param operation
	 *            the operation which needs to be visualized by the image
	 * @return the image of the operation
	 */
	Object getImage(AbstractOperation operation);

	/**
	 * Checks if this provider can render the given operation.
	 * 
	 * @param operation
	 *            the operation which should be checked
	 * @return whether the label provider can render the given operation
	 */
	CanRender canRender(AbstractOperation operation);

}
