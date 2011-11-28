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
package org.eclipse.emf.emfstore.server.model.provider;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

/**
 * Interface for a custom LabelProvider for {@link AbstractOperation}s.
 * 
 * @author Michael Kagel
 * @author emueller
 */
public abstract class AbstractOperationCustomLabelProvider {

	/**
	 * Constant for render priority meaning this provider does not want to render the given element.
	 */
	protected static final int CANNOT_RENDER = 0;

	/**
	 * Default constant for a rendered that is able to render the given element.
	 */
	protected static final int CAN_RENDER_DEFAULT = 1;

	private Map<ModelElementId, EObject> modelElementMap;

	/**
	 * Returns the description of an operation.
	 * 
	 * @param operation for description
	 * @return The description of the operation
	 */
	public abstract String getDescription(AbstractOperation operation);

	/**
	 * Returns the image of an operation.
	 * 
	 * @param operation for image
	 * @return The image of the operation
	 */
	public abstract Object getImage(AbstractOperation operation);

	/**
	 * Checks if this provider can render the given operation.
	 * 
	 * @param operation which should be checked.
	 * @return a priority for rendering this element. The higher the priority the more likely this provider will render
	 *         the element. Returning 0 means this provider does not want to render this element.
	 */
	public abstract int canRender(AbstractOperation operation);

	/**
	 * Returns the name of the given model element.
	 * 
	 * @param modelElement a model element whose name should be returned
	 * @return the name of the model element
	 */
	protected abstract String getModelElementName(EObject modelElement);

	/**
	 * Returns the name of the {@link EObject} with the given {@link ModelElementId}.
	 * 
	 * @param modelElementId a {@link ModelElementId}
	 * @return the name of the {@link EObject}
	 */
	public String getModelElementName(ModelElementId modelElementId) {
		return getModelElementName(modelElementMap.get(modelElementId));
	}

	/**
	 * Returns the model element/ID mapping. The map contains all the
	 * model elements and their IDs that are involved with this operation.
	 * 
	 * @return the model element/ID mapping
	 */
	protected Map<ModelElementId, EObject> getModelElementMap() {
		return modelElementMap;
	}

	/**
	 * Sets the model element/ID mapping. The <code>modelElementMap</code> must
	 * contain all the model elements and their IDs that are involved with this operation.
	 * 
	 * @param modelElementMap the model element/ID mapping to be used
	 */
	public void setModelElementMap(Map<ModelElementId, EObject> modelElementMap) {
		this.modelElementMap = modelElementMap;
	}

}
