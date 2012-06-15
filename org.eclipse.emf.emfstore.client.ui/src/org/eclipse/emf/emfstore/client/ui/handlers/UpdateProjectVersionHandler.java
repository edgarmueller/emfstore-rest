package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public class UpdateProjectVersionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		// TODO: ask for specific version, not HEAD
		new UIUpdateProjectController(getShell(), requireSelection(ProjectSpace.class), VersionSpec.HEAD_VERSION)
			.execute();
	}

}
