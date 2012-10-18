/*******************************************************************************
 * Copyright 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.dialogs.merge.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider;

/**
 * Default label provider for merges.
 * 
 * @author ovonwesen
 * 
 */
public class DefaultMergeLabelProvider implements MergeLabelProvider {

	private AdapterFactoryLabelProvider adapterFactory;

	/**
	 * Default constructor.
	 */
	public DefaultMergeLabelProvider() {
		adapterFactory = UIDecisionUtil.getAdapterFactory();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider#getPriority()
	 */
	public int getPriority() {
		return 10;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider#getText(org.eclipse.emf.ecore.EObject)
	 */
	public String getText(EObject modelElement) {
		return adapterFactory.getText(modelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider#dispose()
	 */
	public void dispose() {
		adapterFactory.dispose();
	}
}