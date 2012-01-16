package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.common.ExtensionPoint;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.server.exceptions.UnknownSessionException;

/**
 * 
 * @author wesendon
 */
public class SessionManager {

	public void execute(ServerCall<?> serverCall) throws EmfStoreException {
		Usersession usersession = prepareUsersession(serverCall);
		loginUsersession(usersession, false);
		executeCall(serverCall, usersession, true);
	}

	private void loginUsersession(Usersession usersession, boolean force) throws EmfStoreException {
		if (usersession == null) {
			// TODO create exception
			throw new RuntimeException("Ouch.");
		}
		if (!isLoggedIn(usersession) || force) {
			if (!(usersession.getUsername() == null || usersession.getUsername().equals(""))
				&& usersession.getPassword() != null) {
				try {
					// if login fails, let the session provider handle the rest
					usersession.logIn();
					return;
				} catch (EmfStoreException e) {
				}
			}
			getSessionProvider().loginSession(usersession);
		}
	}

	private boolean isLoggedIn(Usersession usersession) {
		ConnectionManager connectionManager = WorkspaceManager.getInstance().getConnectionManager();
		return usersession.isLoggedIn() && connectionManager.isLoggedIn(usersession.getSessionId());
	}

	private void executeCall(ServerCall<?> serverCall, Usersession usersession, boolean retry) throws EmfStoreException {
		try {
			serverCall.run(usersession.getSessionId());
		} catch (EmfStoreException e) {
			if (retry && (e instanceof SessionTimedOutException || e instanceof UnknownSessionException)) {
				// login & retry
				loginUsersession(usersession, true);
				executeCall(serverCall, usersession, false);
			} else {
				throw e;
			}
		}
	}

	private Usersession prepareUsersession(ServerCall<?> serverCall) {
		Usersession usersession = serverCall.getUsersession();
		if (usersession == null) {
			usersession = getUsersessionFromProjectSpace(serverCall.getProjectSpace());
		}

		if (usersession == null) {
			SessionProvider sessionProvider = getSessionProvider();
			// serverinfo hint
			usersession = sessionProvider.provideUsersession(serverCall.getServerInfo());
		}
		serverCall.setUsersession(usersession);
		return usersession;
	}

	private Usersession getUsersessionFromProjectSpace(ProjectSpace projectSpace) {
		if (projectSpace != null && projectSpace.getUsersession() != null) {
			return projectSpace.getUsersession();
		}
		return null;
	}

	private SessionProvider getSessionProvider() {
		return new ExtensionPoint(SessionProvider.ID).getClass("class", SessionProvider.class);
	}
}
