package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.NewRepositoryWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class UIServerPropertiesController extends AbstractEMFStoreUIController {

	private final ServerInfo serverInfo;

	public UIServerPropertiesController(Shell shell, ServerInfo serverInfo) {
		super(shell);
		this.serverInfo = serverInfo;
	}

	public void openNewRepositoryWizard() {
		NewRepositoryWizard wizard = new NewRepositoryWizard();
		wizard.setServerInfo(serverInfo);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.open();
	}

}
