package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIDeleteProjectController;

public class DeleteProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIDeleteProjectController(getShell()).deleteProject(requireSelection(ProjectSpace.class));

	}
}
