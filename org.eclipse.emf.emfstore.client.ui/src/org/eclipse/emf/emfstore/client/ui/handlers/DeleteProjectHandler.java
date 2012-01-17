package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIDeleteProjectController;

public class DeleteProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIDeleteProjectController(getShell()).deleteProject(requireSelection(ProjectSpace.class));

	}
}
