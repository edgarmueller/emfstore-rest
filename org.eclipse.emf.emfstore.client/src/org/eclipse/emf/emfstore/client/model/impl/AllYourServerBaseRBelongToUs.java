package org.eclipse.emf.emfstore.client.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.api.IServer;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;

public abstract class AllYourServerBaseRBelongToUs extends EObjectImpl implements IServer, ServerInfo {

	List<IRemoteProject> remoteProjects;

	private IUsersession validateUsersession(IUsersession usersession) throws EmfStoreException {
		if (usersession == null || !this.equals(usersession.getServer())) {
			// TODO OTS custom exception
			throw new EmfStoreException("Invalid usersession for given server.");
		}
		return usersession;
	}

	public IRemoteProject createRemoteProject(IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor progressMonitor) throws EmfStoreException {
		return new RemoteProject(this, new ServerCall<ProjectInfo>(validateUsersession(usersession)) {
			@Override
			protected ProjectInfo run() throws EmfStoreException {
				return getConnectionManager().createEmptyProject(getSessionId(), projectName, projectDescription,
					createLogmessage(getUsersession(), projectName));
			}
		}.execute());
	}

	public IRemoteProject createRemoteProject(final String projectName, final String projectDescription,
		IProgressMonitor monitor) throws EmfStoreException {
		return new RemoteProject(this, new ServerCall<ProjectInfo>(this) {
			@Override
			protected ProjectInfo run() throws EmfStoreException {
				return getConnectionManager().createEmptyProject(getSessionId(), projectName, projectDescription,
					createLogmessage(getUsersession(), projectName));
			}
		}.execute());
	}

	private LogMessage createLogmessage(IUsersession usersession, final String projectName) {
		final LogMessage log = VersioningFactory.eINSTANCE.createLogMessage();
		log.setMessage("Creating project '" + projectName + "'");
		log.setAuthor(usersession.getUsername());
		log.setClientDate(new Date());
		return log;
	}

	public List<? extends IRemoteProject> getRemoteProjects() throws EmfStoreException {
		if (remoteProjects == null) {
			List<IRemoteProject> remoteProjects = new ArrayList<IRemoteProject>();
			for (ProjectInfo projectInfo : getProjectInfos()) {
				RemoteProject wrapper = new RemoteProject(this, projectInfo);
				remoteProjects.add(wrapper);
			}
		}
		return remoteProjects;
	}

	public List<? extends IRemoteProject> getRemoteProjects(IUsersession usersession) throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}
}
