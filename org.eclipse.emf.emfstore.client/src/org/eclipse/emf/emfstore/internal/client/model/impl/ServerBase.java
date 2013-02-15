package org.eclipse.emf.emfstore.internal.client.model.impl;

import static org.eclipse.emf.emfstore.internal.common.ListUtil.copy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

public abstract class ServerBase extends EObjectImpl implements ESServer, ServerInfo {

	private ESUsersession validateUsersession(ESUsersession usersession) throws ESException {
		if (usersession == null || !this.equals(usersession.getServer())) {
			// TODO OTS custom exception
			throw new ESException("Invalid usersession for given server.");
		}
		return usersession;
	}

	public ESRemoteProject createRemoteProject(ESUsersession usersession, final String projectName,
		final IProgressMonitor progressMonitor) throws ESException {
		return new RemoteProject(this, new ServerCall<ProjectInfo>(validateUsersession(usersession)) {
			@Override
			protected ProjectInfo run() throws ESException {
				return getConnectionManager().createEmptyProject(getSessionId(), projectName, "",
					createLogmessage(getUsersession(), projectName));
			}
		}.execute());
	}

	public ESRemoteProject createRemoteProject(final String projectName,
		IProgressMonitor monitor) throws ESException {
		return new RemoteProject(this, new ServerCall<ProjectInfo>(this) {
			@Override
			protected ProjectInfo run() throws ESException {
				// TODO OTS change remote call too?
				return getConnectionManager().createEmptyProject(getSessionId(), projectName, "",
					createLogmessage(getUsersession(), projectName));
			}
		}.execute());
	}

	private LogMessage createLogmessage(ESUsersession usersession, final String projectName) {
		final LogMessage log = VersioningFactory.eINSTANCE.createLogMessage();
		log.setMessage("Creating project '" + projectName + "'");
		log.setAuthor(usersession.getUsername());
		log.setClientDate(new Date());
		return log;
	}

	public List<ESRemoteProject> getRemoteProjects(ESUsersession usersession)
		throws ESException {

		List<ProjectInfo> projectInfos = new ServerCall<List<ProjectInfo>>(usersession) {
			@Override
			protected List<ProjectInfo> run() throws ESException {
				return getConnectionManager().getProjectList(getSessionId());
			}
		}.execute();

		return copy(mapToRemoteProject(projectInfos));
	}

	private List<RemoteProject> mapToRemoteProject(List<ProjectInfo> projectInfos) {
		List<RemoteProject> remoteProjects = new ArrayList<RemoteProject>();
		for (ProjectInfo projectInfo : projectInfos) {
			RemoteProject wrapper = new RemoteProject(this, projectInfo);
			remoteProjects.add(wrapper);
		}
		return remoteProjects;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws EmfStoreException
	 * @throws AccessControlException
	 * @see org.eclipse.emf.emfstore.internal.client.ESServer.IServer#login(java.lang.String, java.lang.String)
	 * @generated NOT
	 */
	public Usersession login(String name, String password) throws ESException {
		Usersession usersession = ModelFactory.eINSTANCE.createUsersession();
		usersession.setUsername(name);
		usersession.setPassword(password);
		usersession.setServerInfo(this);
		usersession.logIn();
		return usersession;
	}

	public List<ESRemoteProject> getRemoteProjects() throws ESException {
		return getRemoteProjects(null);
	}
}
