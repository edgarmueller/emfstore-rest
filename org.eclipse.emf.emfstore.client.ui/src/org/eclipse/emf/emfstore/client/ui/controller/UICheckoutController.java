package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.swt.widgets.Shell;

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
	}
}
