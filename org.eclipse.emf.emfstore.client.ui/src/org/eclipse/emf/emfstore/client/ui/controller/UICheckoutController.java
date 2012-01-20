package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class UICheckoutController extends AbstractEMFStoreUIController {

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 */
	public UICheckoutController(Shell shell) {
		super(shell);
	}

	public void checkout(ServerInfo serverInfo, ProjectInfo projectInfo) throws EmfStoreException {
		checkout(serverInfo, projectInfo, null);
	}

	public void checkout(ServerInfo serverInfo, final ProjectInfo projectInfo, final PrimaryVersionSpec versionSpec)
		throws EmfStoreException {
		new ServerCall<Void>(serverInfo) {
			@Override
			protected Void run() throws EmfStoreException {
				if (versionSpec == null) {
					WorkspaceManager.getInstance().getCurrentWorkspace().checkout(getUsersession(), projectInfo);
				} else {
					WorkspaceManager.getInstance().getCurrentWorkspace()
						.checkout(getUsersession(), projectInfo, versionSpec);
				}
				return null;
			}
		}.execute();

		// TODO: register navigator as CheckoutObsrever instead of opening it programatically
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		String viewId = "org.eclipse.emf.ecp.navigator.viewer";
		try {
			page.showView(viewId);
		} catch (PartInitException e) {
			EMFStoreMessageDialog.showExceptionDialog(e);
		}

	}
}
