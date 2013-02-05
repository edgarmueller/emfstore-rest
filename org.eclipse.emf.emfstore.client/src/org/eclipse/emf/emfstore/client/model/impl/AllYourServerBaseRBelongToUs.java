package org.eclipse.emf.emfstore.client.model.impl;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.client.api.IHistoryInfo;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.api.ServerInfo;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.IProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;

public abstract class AllYourServerBaseRBelongToUs extends EObjectImpl implements ServerInfo {

	public List<? extends IProjectInfo> getProjectInfos() {
		// TODO Auto-generated method stub
		return null;
	}

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

	public List<? extends IRemoteProject> getRemoteProjectList() throws EmfStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<? extends IRemoteProject> getRemoteProjectList(IUsersession usersession) throws EmfStoreException {
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
