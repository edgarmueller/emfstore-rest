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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.exceptions.UnkownProjectException;
import org.eclipse.emf.emfstore.client.ui.common.RunInUIThread;
import org.eclipse.emf.emfstore.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.jface.dialogs.MessageDialog;
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

	private ProjectSpace projectSpace;
	private final EObject modelElement;

	/**
	 * Constructor.
	 * 
	 * @param shell the parent {@link Shell}
	 * @param projectSpace the {@link ProjectSpace} the <code>modelElement</code> is contained in
	 * @param modelElement the model element whose history should be queried
	 */
	public UIShowHistoryController(Shell shell, ProjectSpace projectSpace, EObject modelElement) {
		super(shell, true, true);
		this.projectSpace = projectSpace;
		this.modelElement = modelElement;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) {

		if (projectSpace == null) {
			try {
				projectSpace = WorkspaceManager.getInstance().getCurrentWorkspace()
					.getProjectSpace(ModelUtil.getProject(modelElement));
			} catch (UnkownProjectException e) {
				MessageDialog.openWarning(getShell(), "Unknown project", e.getMessage());
				return null;
			}
		}

		new RunInUIThread(getShell()) {

			@Override
			public Void doRun(Shell shell) {

				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				HistoryBrowserView historyBrowserView = null;
				// TODO: remove hard-coded reference
				String viewId = "org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView";

				try {
					historyBrowserView = (HistoryBrowserView) page.showView(viewId);
				} catch (PartInitException e) {
					EMFStoreMessageDialog.showExceptionDialog(shell, e);
				}

				if (historyBrowserView != null) {
					historyBrowserView.setInput(projectSpace, modelElement);
				}

				return null;
			}
		}.execute();

		return null;
	}
}
