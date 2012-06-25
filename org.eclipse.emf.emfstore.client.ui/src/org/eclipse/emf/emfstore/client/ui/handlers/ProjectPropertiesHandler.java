/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Brings up the properties dialog for a selected {@link ProjectSpace}.
 * It is assumed that the user previously has selected a {@link ProjectSpace} instance.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class ProjectPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		PreferenceDialog propertyDialog = PreferencesUtil.createPropertyDialogOn(getShell(),
			requireSelection(ProjectSpace.class).getProject(), null, null, null);
		if (propertyDialog != null) {
			propertyDialog.open();
		}
	}

}
