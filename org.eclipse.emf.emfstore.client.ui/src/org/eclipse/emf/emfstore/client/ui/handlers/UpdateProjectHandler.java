package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	private final ProjectSpace projectSpace;

	public UpdateProjectHandler() {
		this.projectSpace = null;
	}

	public UpdateProjectHandler(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	@Override
	public void handle() throws EmfStoreException {
		if (projectSpace == null) {
			new UIUpdateProjectController(getShell(), requireSelection(ProjectSpace.class)).execute();
		} else {
			new UIUpdateProjectController(getShell(), projectSpace).execute();
		}
	}

}
