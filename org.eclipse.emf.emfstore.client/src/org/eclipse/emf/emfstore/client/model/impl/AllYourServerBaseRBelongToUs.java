package org.eclipse.emf.emfstore.client.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.api.IServer;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;

public abstract class AllYourServerBaseRBelongToUs extends EObjectImpl implements IServer, ServerInfo {

	public IRemoteProject createEmptyRemoteProject(IUsersession usersession, String projectName,
		String projectDescription, IProgressMonitor progressMonitor) throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public IRemoteProject createRemoteProject(String projectName, String projectDescription, IProgressMonitor monitor)
		throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public IRemoteProject createRemoteProject(IUsersession usersession, String projectName, String projectDescription,
		IProgressMonitor monitor) throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteRemoteProject(IProjectId projectId, boolean deleteFiles) throws EmfStoreException {
		// TODO Auto-generated method stub

	}

	public void deleteRemoteProject(IUsersession usersession, IProjectId projectId, boolean deleteFiles)
		throws EmfStoreException {
		// TODO Auto-generated method stub

	}

	public List<? extends IHistoryInfo> getHistoryInfo(ServerInfo serverInfo, IProjectId projectId, IHistoryQuery query)
		throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<? extends IHistoryInfo> getHistoryInfo(IUsersession usersession, IProjectId projectId,
		IHistoryQuery query) throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<? extends IRemoteProject> getRemoteProjects() throws EmfStoreException {
		List<IRemoteProject> remoteProjects = new ArrayList<IRemoteProject>();
		for (ProjectInfo projectInfo : getProjectInfos()) {
			RemoteProject wrapper = new RemoteProject(projectInfo);
			remoteProjects.add(wrapper);
		}
		return remoteProjects;
	}

	public List<? extends IRemoteProject> getRemoteProjects(IUsersession usersession) throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public IPrimaryVersionSpec resolveVersionSpec(IVersionSpec versionSpec, IProjectId projectId)
		throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public IPrimaryVersionSpec resolveVersionSpec(IUsersession usersession, IVersionSpec versionSpec,
		IProjectId projectId) throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}
}
