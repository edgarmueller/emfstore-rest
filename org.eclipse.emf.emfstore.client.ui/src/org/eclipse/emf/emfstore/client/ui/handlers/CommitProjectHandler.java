package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UICommitProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class CommitProjectHandler extends AbstractEMFStoreHandler {

	private final ProjectSpace projectSpace;

	public CommitProjectHandler() {
		projectSpace = null;
	}

	public CommitProjectHandler(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	@Override
	public void handle() throws EmfStoreException {
		if (projectSpace == null) {
			new UICommitProjectController(getShell(), requireSelection(ProjectSpace.class)).execute();
		} else {
			new UICommitProjectController(getShell(), projectSpace).execute();
		}
	}

}
