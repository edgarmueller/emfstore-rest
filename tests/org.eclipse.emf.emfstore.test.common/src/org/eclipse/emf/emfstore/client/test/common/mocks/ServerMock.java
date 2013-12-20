package org.eclipse.emf.emfstore.client.test.common.mocks;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;

public class ServerMock {

	private EMFStore emfStore;
	private ESServer server;
	private ServerSpace serverSpace;

	public ServerMock(ESServer server, EMFStore emfstore, ServerSpace serverSpace) {
		this.server = server;
		this.emfStore = emfstore;
		this.serverSpace = serverSpace;
	}

	public ESServer getServer() {
		return server;
	}

	public void setServer(ESServer server) {
		this.server = server;
	}
	
	public ProjectHistory getHistory(ESLocalProject localProject) {
		ESLocalProjectImpl projectImpl = ESLocalProjectImpl.class.cast(localProject);
		final ProjectId id = projectImpl.toInternalAPI().getProjectId();
		for (final ProjectHistory history : serverSpace.getProjects()) {
			if (history.getProjectId().equals(id)) {
				return history;
			}
		}
		throw new RuntimeException("Project History not found");
	}

	public EMFStore getEMFStore() {
		return emfStore;
	}

	public void setEmfStore(EMFStore emfStore) {
		this.emfStore = emfStore;
	}
}
