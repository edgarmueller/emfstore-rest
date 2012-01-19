package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.controller.CheckoutController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class UICheckoutController extends AbstractEMFStoreUIController {

	private final ProjectInfo projectInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param projectInfo
	 *            the project to be checked out
	 */
	public UICheckoutController(Shell shell, ProjectInfo projectInfo) {
		super(shell);
		this.projectInfo = projectInfo;
	}

	public void checkout() {
		try {
			new CheckoutController(projectInfo).execute();
			// TODO: register navigator as CheckoutObsrever instead of opening it programatically
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			String viewId = "org.eclipse.emf.ecp.navigator.viewer";
			try {
				page.showView(viewId);
			} catch (PartInitException e) {
				EMFStoreMessageDialog.showExceptionDialog(e);
			}
		} catch (EmfStoreException e1) {
			e1.printStackTrace();
		}

	}
}
