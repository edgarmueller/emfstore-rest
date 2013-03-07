/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.util.EMFStoreHandlerUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Handler to save the currently selected project space.
 * 
 * @author mkoegel
 * 
 */
public class SaveProjectSpaceHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		EObject eObject = EMFStoreHandlerUtil.requireSelection(getEvent(), EObject.class);
		ProjectSpace projectSpace = ModelUtil.getParent(ProjectSpace.class, eObject);
		if (projectSpace == null) {
			MessageDialog.openError(getShell(), "Saving Project failed", "No Project is selected.");
			return;
		}
		projectSpace.save();
	}

}