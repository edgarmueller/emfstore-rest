package org.eclipse.emf.emfstore.client.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.api.IServer;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.client.model.ModelFactory;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

public abstract class AllYourServerBaseRBelongToUs extends EObjectImpl
		implements IServer, ServerInfo {

	List<RemoteProject> remoteProjects;

	private IUsersession validateUsersession(IUsersession usersession)
			throws EMFStoreException {
		if (usersession == null || !this.equals(usersession.getServer())) {
			// TODO OTS custom exception
			throw new EMFStoreException("Invalid usersession for given server.");
		}
		return usersession;
	}

	public IRemoteProject createRemoteProject(IUsersession usersession,
			final String projectName, final String projectDescription,
			final IProgressMonitor progressMonitor) throws EMFStoreException {
		return new RemoteProject(this, new ServerCall<ProjectInfo>(
				validateUsersession(usersession)) {
			@Override
			protected ProjectInfo run() throws EMFStoreException {
				return getConnectionManager().createEmptyProject(
						getSessionId(), projectName, projectDescription,
						createLogmessage(getUsersession(), projectName));
			}
		}.execute());
	}

	public IRemoteProject createRemoteProject(final String projectName,
			final String projectDescription, IProgressMonitor monitor)
			throws EMFStoreException {
		return new RemoteProject(this, new ServerCall<ProjectInfo>(this) {
			@Override
			protected ProjectInfo run() throws EMFStoreException {
				return getConnectionManager().createEmptyProject(
						getSessionId(), projectName, projectDescription,
						createLogmessage(getUsersession(), projectName));
			}
		}.execute());
	}

	private LogMessage createLogmessage(IUsersession usersession,
			final String projectName) {
		final LogMessage log = VersioningFactory.eINSTANCE.createLogMessage();
		log.setMessage("Creating project '" + projectName + "'");
		log.setAuthor(usersession.getUsername());
		log.setClientDate(new Date());
		return log;
	}

	public List<RemoteProject> getRemoteProjects() throws EMFStoreException {
		if (remoteProjects == null) {
			List<IRemoteProject> remoteProjects = new ArrayList<IRemoteProject>();
			for (ProjectInfo projectInfo : getProjectInfos()) {
				RemoteProject wrapper = new RemoteProject(this, projectInfo);
				remoteProjects.add(wrapper);
			}
		}
		return remoteProjects;
	}

	public List<? extends IRemoteProject> getRemoteProjects(
			IUsersession usersession) throws EMFStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws EmfStoreException
	 * @throws AccessControlException
	 * @see org.eclipse.emf.emfstore.client.api.IServer#login(java.lang.String,
	 *      java.lang.String)
	 * @generated NOT
	 */
	public IUsersession login(String name, String password)
			throws EMFStoreException {
		Usersession usersession = ModelFactory.eINSTANCE.createUsersession();
		usersession.setUsername(name);
		usersession.setPassword(password);
		usersession.setServerInfo((ServerInfo) this);
		usersession.logIn();
		return usersession;
	}

	public boolean isLoggedIn(IUsersession usersession) {
		Usersession usersessionImpl = (Usersession) usersession;
		return usersessionImpl.isLoggedIn();
	}

	public void logout(IUsersession usersession) throws EMFStoreException {
		Usersession usersessionImpl = (Usersession) usersession;
		usersessionImpl.logout();
	}
}
