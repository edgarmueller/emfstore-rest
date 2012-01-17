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
package org.eclipse.emf.emfstore.client.ui.commands.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.AddTagController;
import org.eclipse.emf.emfstore.client.ui.commands.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-related controller for adding a version tag.
 * 
 * @author emueller
 * 
 */
public class UIAddTagController extends AbstractEMFStoreUIController {

	private final ProjectSpace projectSpace;
	private final HistoryInfo historyInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 */
	public UIAddTagController(Shell shell, ProjectSpace projectSpace, HistoryInfo historyInfo) {
		super(shell);
		this.projectSpace = projectSpace;
		this.historyInfo = historyInfo;
	}

	public void addTag() {
		PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());
		InputDialog inputDialog = new InputDialog(getShell(), "Add tag", "Please enter the tag's name.", "", null);
		inputDialog.open();
		String str = inputDialog.getValue().trim();
		if (str != null && !(str.length() != 0)) {
			TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
			tag.setName(str);
			try {
				new AddTagController(projectSpace, versionSpec, tag).execute();
			} catch (EmfStoreException e) {
				MessageDialog.openError(getShell(), "Error",
					"An error occurred during the creation of the tag: " + e.getMessage());
			}
		}
	}
}
