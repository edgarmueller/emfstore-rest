package org.eclipse.emf.emfstore.client.test.server;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.AbstractSessionProvider;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.junit.Assert;

public class TestSessionProvider extends AbstractSessionProvider {

	private static Usersession usersession;
	private static TestSessionProvider instance;

	public static TestSessionProvider getInstance() {
		if (instance == null) {
			instance = new TestSessionProvider();
		}
		return instance;
	}

	public Usersession getDefaultUsersession() throws AccessControlException, EmfStoreException {
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				try {
					usersession.logIn();
				} catch (AccessControlException e) {
					Assert.fail();
				} catch (EmfStoreException e) {
					Assert.fail();
				}
			}
		}.run(false);
		return usersession;
	}

	private TestSessionProvider() {
		Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		usersession = org.eclipse.emf.emfstore.client.model.ModelFactory.eINSTANCE.createUsersession();
		usersession.setServerInfo(SetupHelper.getServerInfo());
		usersession.setUsername("super");
		usersession.setPassword("super");
		workspace.getUsersessions().add(usersession);
		workspace.save();
	}

	@Override
	public Usersession provideUsersession(ServerInfo serverInfo) throws EmfStoreException {
		return usersession;
	}

	@Override
	public void login(Usersession usersession) throws EmfStoreException {
		usersession.logIn();
	}

}
