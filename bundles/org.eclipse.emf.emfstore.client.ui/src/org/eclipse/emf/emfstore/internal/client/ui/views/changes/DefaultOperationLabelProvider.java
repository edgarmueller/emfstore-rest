/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.views.changes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.internal.client.ui.common.OperationCustomLabelProvider;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;

/**
 * Default label provider for operations.
 * 
 * @author emueller
 * 
 */
public class DefaultOperationLabelProvider implements OperationCustomLabelProvider {

	/**
	 * The label to be shown for unknown element.
	 */
	protected static final String UNKOWN_ELEMENT = "(Unkown Element)";

	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;
	private ComposedAdapterFactory adapterFactory;

	/**
	 * Constructor.
	 */
	public DefaultOperationLabelProvider() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.AbstractOperationCustomLabelProvider#getDescription(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public String getDescription(AbstractOperation operation) {

		if (operation instanceof CompositeOperation) {
			CompositeOperation compositeOperation = (CompositeOperation) operation;
			// artificial composite because of opposite reference,
			// take description of main operation
			if (compositeOperation.getMainOperation() != null) {
				return getDescription(compositeOperation.getMainOperation());
			}
		}

		return adapterFactoryLabelProvider.getText(operation);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.AbstractOperationCustomLabelProvider#getImage(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public Object getImage(AbstractOperation operation) {
		return adapterFactoryLabelProvider.getImage(operation);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.AbstractOperationCustomLabelProvider#canRender(org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation)
	 */
	public CanRender canRender(AbstractOperation operation) {
		return CanRender.Yes;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.AbstractOperationCustomLabelProvider#getModelElementName(org.eclipse.emf.ecore.EObject)
	 */
	public String getModelElementName(EObject modelElement) {

		if (modelElement == null) {
			return UNKOWN_ELEMENT;
		}

		// TODO: provide sensible label for given model element
		return trim(adapterFactoryLabelProvider.getText(modelElement));
	}

	private String trim(Object object) {
		String string = object.toString();
		String result = string.trim();

		if (result.length() == 0) {
			return "(empty name)";
		}

		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.AbstractOperationCustomLabelProvider#dispose()
	 */
	public void dispose() {
		if (adapterFactory != null) {
			adapterFactory.dispose();
		}
		if (adapterFactoryLabelProvider != null) {
			adapterFactoryLabelProvider.dispose();
		}
	}

	/**
	 * Returns the label provider.
	 * 
	 * @return the label provider
	 */
	protected AdapterFactoryLabelProvider getAdapterFactoryLabelProvider() {
		return adapterFactoryLabelProvider;
	}
}
