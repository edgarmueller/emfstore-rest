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
package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * UI-related controller for adding and removing version tags.
 * 
 * @author emueller
 * 
 */
// TODO: re-package exception for more sensible error messages
public class UITagController extends AbstractEMFStoreUIController {

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 */
	public UITagController(Shell shell) {
		super(shell);
	}

	public void addTag(ProjectSpace projectSpace, HistoryInfo historyInfo) throws EmfStoreException {
		PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());
		InputDialog inputDialog = new InputDialog(getShell(), "Add tag", "Please enter the tag's name.", "", null);
		inputDialog.open();
		String str = inputDialog.getValue().trim();
		if (str != null && str.length() > 0) {
			TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
			tag.setName(str);
			projectSpace.addTag(versionSpec, tag);
		}
	}

	public void removeTag(HistoryInfo historyInfo) throws EmfStoreException {

		final LabelProvider tagLabelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((TagVersionSpec) element).getName();
			}
		};

		ElementListSelectionDialog dlg = new ElementListSelectionDialog(PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getShell(), tagLabelProvider);
		dlg.setElements(historyInfo.getTagSpecs().toArray());
		dlg.setTitle("Tag selection");
		dlg.setBlockOnOpen(true);
		dlg.setMultipleSelection(true);
		int ret = dlg.open();
		if (ret != Window.OK) {
			return;
		}

		// TODO: controller currently does not work if the active workbench window is not
		// the history view
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null || !(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return;
		}

		HistoryBrowserView historyBrowserView = (HistoryBrowserView) activePage.getActivePart();

		Object[] tags = dlg.getResult();
		for (Object tag : tags) {
			// TODO: do not fetch project space via history browser view
			historyBrowserView.getProjectSpace().removeTag(historyInfo.getPrimerySpec(), (TagVersionSpec) tag);
		}

		// TODO: remove manual refresh
		historyBrowserView.refresh();

		return;
	}
}
