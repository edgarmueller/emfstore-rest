package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.controller.ChangeSessionController;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class UIChangeSessionController extends AbstractEMFStoreUIController {

	private final ServerInfo serverInfo;

	public UIChangeSessionController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	public void changeSession() {
		try {
			new ChangeSessionController(serverInfo).execute();
		} catch (EmfStoreException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
		// TODO: remove manual update
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
					.update("org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.LoginDecorator");
			}
		});
	}
}
