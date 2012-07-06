package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UICreateBranchController;

public class CreateBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UICreateBranchController(getShell(), requireSelection(ProjectSpace.class)).execute();
	}

}
