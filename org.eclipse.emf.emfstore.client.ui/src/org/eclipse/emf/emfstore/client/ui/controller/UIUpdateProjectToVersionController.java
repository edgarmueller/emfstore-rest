/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryBuilder;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * UI controller for updating a project.
 * 
 * @author eneufeld
 */
public class UIUpdateProjectToVersionController extends AbstractEMFStoreUIController<PrimaryVersionSpec> {

	private final ProjectSpace projectSpace;

	public UIUpdateProjectToVersionController(Shell shell, ProjectSpace projectSpace) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
	}

	@Override
	public PrimaryVersionSpec doRun(IProgressMonitor monitor) throws EmfStoreException {
		RangeQuery query = HistoryQueryBuilder.rangeQuery(projectSpace.getBaseVersion(), 20, 0, false, false, false,
			false);
		try {
			List<HistoryInfo> historyInfo = projectSpace.getHistoryInfos(query);
			// filter base version
			Iterator<HistoryInfo> iter = historyInfo.iterator();
			while (iter.hasNext()) {
				if (projectSpace.getBaseVersion().equals(iter.next().getPrimerySpec())) {
					iter.remove();
					break;
				}
			}
			if (historyInfo.size() == 0) {
				return new UIUpdateProjectController(getShell(), projectSpace, Versions.createHEAD(projectSpace
					.getBaseVersion())).execute();
			}

			ListDialog listDialog = new ListDialog(getShell());
			listDialog.setContentProvider(ArrayContentProvider.getInstance());
			listDialog.setLabelProvider(new LabelProvider() {

				@Override
				public String getText(Object element) {

					HistoryInfo historyInfo = (HistoryInfo) element;

					StringBuilder sb = new StringBuilder("Version ");
					sb.append(Integer.toString(historyInfo.getPrimerySpec().getIdentifier()));
					sb.append("  -  ");
					sb.append(historyInfo.getLogMessage().getMessage());

					return sb.toString();

				}

			});
			listDialog.setInput(historyInfo);
			listDialog.setTitle("Select a Version to update to");
			listDialog.setMessage("The project will be updated to the selected Version");
			listDialog.setInitialSelections(new Object[] { historyInfo.get(0) });
			int result = listDialog.open();
			if (Dialog.OK == result) {
				Object[] selection = listDialog.getResult();
				HistoryInfo info = (HistoryInfo) selection[0];

				return new UIUpdateProjectController(getShell(), projectSpace, Versions.createPRIMARY(info
					.getPrimerySpec().getIdentifier())).execute();
			}
		} catch (EmfStoreException e) {

		}
		return null;
	}

}