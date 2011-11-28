package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.CreateProjectDialog;
import org.eclipse.ui.PlatformUI;

public class UICreateLocalProjectController extends AbstractEMFStoreUIController {

	public void createLocalProject() {
		CreateProjectDialog dialog = new CreateProjectDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
			null);
		dialog.open();
	}

}
