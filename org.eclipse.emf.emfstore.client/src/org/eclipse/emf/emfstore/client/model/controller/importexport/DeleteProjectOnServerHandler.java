package org.eclipse.emf.emfstore.client.model.controller.importexport;

import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectId;

/**
 * Controller class for deleting a remote project.
 * 
 * @author emueller
 * 
 */
public class DeleteProjectOnServerHandler extends ServerCall<Void> {

	private final boolean deleteFiles;
	private final ProjectId projectId;

	/**
	 * Constructor.
	 * 
	 * @param projectId
	 *            The {@link ProjectId} of the project to be deleted remotely.
	 * @param deleteFiles
	 *            Whether files should be deleted too
	 * 
	 */
	public DeleteProjectOnServerHandler(ProjectId projectId, boolean deleteFiles) {
		this.projectId = projectId;
		this.deleteFiles = deleteFiles;
	}

	@Override
	protected Void run() throws EmfStoreException {
		Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		workspace.deleteRemoteProject(getUsersession(), projectId, deleteFiles);
		return null;
	}

}
