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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * UI controller responsible for opening up the history view.
 * 
 * @author emueller
 * 
 */
public class UIShowHistoryController extends AbstractEMFStoreUIController<Void> {

	private final EObject modelElement;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param modelElement
	 *            the model element whose history should be queried
	 */
	public UIShowHistoryController(Shell shell, EObject modelElement) {
		super(shell, true, true);
		this.modelElement = modelElement;
	}

	public UIShowHistoryController(Shell shell, ESLocalProject localProject) {
		super(shell, false, true);
		this.modelElement = ((ESLocalProjectImpl) localProject).toInternalAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {

		final HistoryBrowserView view = RunInUI.runWithResult(new Callable<HistoryBrowserView>() {
			public HistoryBrowserView call() throws Exception {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				// TODO: remove hard-coded reference
				String viewId = "org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView";

				try {
					return (HistoryBrowserView) page.showView(viewId);
				} catch (PartInitException e) {
					EMFStoreMessageDialog.showExceptionDialog(getShell(), e);
				}
				return null;
			}
		});

		if (view != null) {
			try {

				view.setInput(modelElement);

			} catch (Exception e) {
				System.out.println(e);
			}
		}

		return null;
	}
}
