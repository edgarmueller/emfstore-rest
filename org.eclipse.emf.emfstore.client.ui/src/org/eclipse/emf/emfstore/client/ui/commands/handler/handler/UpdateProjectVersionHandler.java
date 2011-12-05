package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIUpdateProjectController;

public class UpdateProjectVersionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIUpdateProjectController(getShell()).askForVersionAndUpdate(requireSelection(ProjectSpace.class));
	}

}
