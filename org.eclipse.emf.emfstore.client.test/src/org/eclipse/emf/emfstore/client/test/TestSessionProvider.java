package org.eclipse.emf.emfstore.client.test;

import org.eclipse.emf.emfstore.client.model.ModelFactory;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class TestSessionProvider extends AbstractSessionProvider {

	private Usersession session;

	public TestSessionProvider() {
		// ServerInfo serverInfo = SetupHelper.getServerInfo();
		session = ModelFactory.eINSTANCE.createUsersession();
		// session.setServerInfo(serverInfo);
		session.setUsername("super");
		session.setPassword("super");
		session.setSavePassword(true);

		Workspace currentWorkspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		// currentWorkspace.getServerInfos().add(serverInfo);
		currentWorkspace.getUsersessions().add(session);
		currentWorkspace.save();
	}

	@Override
	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {
		if (serverInfo != null && serverInfo.getLastUsersession() != null) {
			return serverInfo.getLastUsersession();
		}

		return session;
	}

	@Override
	public void login(Usersession usersession) throws EmfStoreException {
		// do nothing
	}

}
