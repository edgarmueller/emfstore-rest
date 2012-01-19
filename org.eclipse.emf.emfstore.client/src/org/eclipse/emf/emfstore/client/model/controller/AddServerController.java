package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;

/**
 * Controller for adding a server to the local {@link Workspace}.
 * 
 * @author emueller
 * 
 */
public class AddServerController {

	private ServerInfo serverInfo;

	/**
	 * Constructor.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} to be added to the local {@link Workspace}
	 */
	public AddServerController(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	/**
	 * Executes the handler.
	 */
	public void execute() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// save serverInfo to workspace
				Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
				workspace.getServerInfos().add(serverInfo);
				workspace.save();
			}
		}.run();
	}
}
