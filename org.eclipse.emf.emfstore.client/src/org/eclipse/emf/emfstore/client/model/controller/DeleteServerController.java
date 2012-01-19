package org.eclipse.emf.emfstore.client.model.controller;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;

public class DeleteServerController {

	private final ServerInfo serverInfo;

	public DeleteServerController(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public void deleteServer() throws IllegalStateException {
		EList<ProjectSpace> projectSpaces = WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces();
		ArrayList<ProjectSpace> usedSpaces = new ArrayList<ProjectSpace>();
		for (ProjectSpace projectSpace : projectSpaces) {
			if (projectSpace.getUsersession() != null
				&& projectSpace.getUsersession().getServerInfo().equals(serverInfo)) {
				usedSpaces.add(projectSpace);
			}
		}
		if (usedSpaces.size() > 0) {
			String message = "";
			for (ProjectSpace pSpace : usedSpaces) {
				message += "\n" + pSpace.getProjectName();
			}
			throw new IllegalStateException("Cannot delete \'" + serverInfo.getName()
				+ "\' because it is currently used by the following projects: \n" + message);
		} else {
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					WorkspaceManager.getInstance().getCurrentWorkspace().getServerInfos().remove(serverInfo);
					EcoreUtil.delete(serverInfo);
					WorkspaceManager.getInstance().getCurrentWorkspace().save();
				};
			}.run(false);
		}
	}
}
