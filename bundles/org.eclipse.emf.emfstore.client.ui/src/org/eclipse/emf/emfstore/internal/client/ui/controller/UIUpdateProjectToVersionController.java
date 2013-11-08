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
 * Edgar Mueller - fixes, javadoc
 *******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.query.ESRangeQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * UI controller for updating a project to a specific version.
 * 
 * @author eneufeld
 * @author emueller
 */
public class UIUpdateProjectToVersionController extends
	AbstractEMFStoreUIController<ESPrimaryVersionSpec> {

	private final ESLocalProject projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param localProject
	 *            the {@link ESLocalProject} that should be updated to a certain version
	 */
	public UIUpdateProjectToVersionController(Shell shell, ESLocalProject localProject) {
		super(shell, true, true);
		projectSpace = localProject;
	}

	@Override
	public ESPrimaryVersionSpec doRun(IProgressMonitor monitor)
		throws ESException {
		final ESRangeQuery query = ESHistoryQuery.FACTORY.rangeQuery(
			projectSpace.getBaseVersion(), 20, 0, false, false, false,
			false);
		final List<ESHistoryInfo> historyInfo = projectSpace.getHistoryInfos(query, new NullProgressMonitor());
		// filter base version
		final Iterator<ESHistoryInfo> iter = historyInfo.iterator();
		while (iter.hasNext()) {
			if (projectSpace.getBaseVersion().equals(iter.next().getPrimarySpec())) {
				iter.remove();
				break;
			}
		}
		if (historyInfo.size() == 0) {
			return new UIUpdateProjectController(
				getShell(),
				projectSpace,
				ESVersionSpec.FACTORY.createHEAD(projectSpace.getBaseVersion()))
				.execute();
		}

		final ListDialog listDialog = createDialog(historyInfo);
		final Integer result = RunInUI.runWithResult(new Callable<Integer>() {
			public Integer call() throws Exception {
				return listDialog.open();
			}
		});

		if (Window.OK == result) {
			final Object[] selection = listDialog.getResult();
			final ESHistoryInfo info = (ESHistoryInfo) selection[0];
			return RunInUI.WithException.runWithResult(new Callable<ESPrimaryVersionSpec>() {
				public ESPrimaryVersionSpec call() throws Exception {
					final UIUpdateProjectController controller = new UIUpdateProjectController(
						getShell(),
						projectSpace,
						ESVersionSpec.FACTORY.createPRIMARY(
							info.getPrimarySpec().getIdentifier()));
					return controller.execute();
				}
			});
		}
		return null;
	}

	private ListDialog createDialog(List<ESHistoryInfo> historyInfo) {
		final ListDialog listDialog = new ListDialog(getShell());
		listDialog.setContentProvider(ArrayContentProvider.getInstance());
		listDialog.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {

				final ESHistoryInfo historyInfo = (ESHistoryInfo) element;

				final StringBuilder sb = new StringBuilder("Version ");
				sb.append(Integer.toString(historyInfo.getPrimarySpec()
					.getIdentifier()));
				sb.append("  -  ");
				sb.append(historyInfo.getLogMessage().getMessage());

				return sb.toString();
			}
		});
		listDialog.setInput(historyInfo);
		listDialog.setTitle("Select a Version to update to");
		listDialog
			.setMessage("The project will be updated to the selected Version");
		listDialog
			.setInitialSelections(new Object[] { historyInfo.get(0) });
		return listDialog;
	}

}