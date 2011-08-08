package org.eclipse.emf.emfstore.client.model.connectionmanager;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.common.ExtensionPoint;
import org.eclipse.emf.emfstore.server.exceptions.SessionTimedOutException;
import org.eclipse.emf.emfstore.server.exceptions.UnknownSessionException;

/**
 * 
 * @author wesendon
 */
public class SessionManager {

	public void execute(ServerCall serverCall) {
		Usersession usersession = prepareUsersession(serverCall);

		executeCall(serverCall, usersession, true);
	}

	private void executeCall(ServerCall serverCall, Usersession usersession, boolean retry) {
		try {
			serverCall.run(usersession.getSessionId());
		} catch (SessionTimedOutException e) {
			if (retry) {
				// login & retry
				executeCall(serverCall, usersession, false);
			} else {
				serverCall.handleException(e);
			}

		} catch (UnknownSessionException e) {
			if (retry) {
				// login & retry
			} else {
				serverCall.handleException(e);
			}

		} catch (Exception e) {
			serverCall.handleException(e);
		}
	}

	private Usersession prepareUsersession(ServerCall serverCall) {
		Usersession usersession = serverCall.getUsersession();
		if (usersession == null) {
			usersession = getUsersessionFromProjectSpace(serverCall.getProjectSpace());
		}

		if (usersession == null) {
			SessionProvider sessionProvider = getSessionProvider();
			usersession = sessionProvider.getUsersession();
		}
		return usersession;
	}

	private Usersession getUsersessionFromProjectSpace(ProjectSpace projectSpace) {
		if (projectSpace != null && projectSpace.getUsersession() != null) {
			return projectSpace.getUsersession();
		}
		return null;
	}

	private void login(Usersession usersession) {
		// TODO Auto-generated method stub
	}

	private SessionProvider getSessionProvider() {
		return new ExtensionPoint(SessionProvider.ID).getClass("class", SessionProvider.class);
	}
}
