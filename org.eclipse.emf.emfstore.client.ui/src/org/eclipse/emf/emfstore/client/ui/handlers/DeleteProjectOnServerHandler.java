package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.controller.UIDeleteRemoteProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class DeleteProjectOnServerHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		ProjectInfo projectInfoSelection = requireSelection(ProjectInfo.class);
		ServerInfo serverInfo = (ServerInfo) projectInfoSelection.eContainer();
		new UIDeleteRemoteProjectController(getShell(), serverInfo, projectInfoSelection.getProjectId(), false)
			.execute(false, false);
	}
}