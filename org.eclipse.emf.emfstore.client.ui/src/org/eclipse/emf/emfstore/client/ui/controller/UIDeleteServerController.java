package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.controller.DeleteServerController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UIDeleteServerController extends AbstractEMFStoreUIController {

	private final ServerInfo serverInfo;

	/**
	 * 
	 * @param shell
	 */
	public UIDeleteServerController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	public void deleteServer() {
		if (!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm deletion",
			"Are you sure you want to delete \'" + serverInfo.getName() + "\'")) {
			return;
		}

		try {
			new DeleteServerController(serverInfo).deleteServer();
		} catch (IllegalStateException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}

}
