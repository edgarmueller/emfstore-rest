package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIUpdateProjectController;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIUpdateProjectController(getShell()).update(requireSelection(ProjectSpace.class));
	}

}
