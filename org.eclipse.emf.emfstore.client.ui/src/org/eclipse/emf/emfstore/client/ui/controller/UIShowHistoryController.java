package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class UIShowHistoryController extends AbstractEMFStoreUIController {

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
		super(shell);
		this.projectSpace = projectSpace;
		this.modelElement = modelElement;
	}

	public void showHistory() throws EmfStoreException {
		if (projectSpace == null) {
			projectSpace = WorkspaceManager.getInstance().getCurrentWorkspace()
				.getProjectSpace(ModelUtil.getProject(modelElement));
		}

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		HistoryBrowserView historyBrowserView = null;
		String viewId = "org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView";
		try {
			historyBrowserView = (HistoryBrowserView) page.showView(viewId);
		} catch (PartInitException e) {
			EMFStoreMessageDialog.showExceptionDialog(e);
		}
		if (historyBrowserView != null) {
			historyBrowserView.setInput(projectSpace, modelElement);
		}
	}
}
