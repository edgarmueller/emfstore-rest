package org.eclipse.emf.emfstore.client.api;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public interface IWorkspace {

	void addServerInfo(IServerInfo serverInfo);

	void removeServerInfo(IServerInfo serverInfo);

	IProject checkout(final IUsersession usersession, final IProjectInfo projectInfo) throws EmfStoreException;

	IProject checkout(final IUsersession usersession, final IProjectInfo projectInfo, IProgressMonitor progressMonitor)
		throws EmfStoreException;

	IProjectInfo createEmptyRemoteProject(final IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor progressMonitor) throws EmfStoreException;

	IProject createLocalProject(String projectName, String projectDescription);

	IProjectInfo createRemoteProject(IServerInfo serverInfo, final String projectName, final String projectDescription,
		final IProgressMonitor monitor) throws EmfStoreException;

	IProjectInfo createRemoteProject(IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EmfStoreException;

	void deleteRemoteProject(IServerInfo serverInfo, final IProjectId projectId, final boolean deleteFiles)
		throws EmfStoreException;

	void deleteRemoteProject(final IUsersession usersession, final IProjectId projectId, final boolean deleteFiles)
		throws EmfStoreException;

	List<IBranchInfo> getBranches(IServerInfo serverInfo, final IProjectId projectId) throws EmfStoreException;

	List<IHistoryInfo> getHistoryInfo(IServerInfo serverInfo, final IProjectId projectId, final IHistoryQuery query)
		throws EmfStoreException;

	List<IHistoryInfo> getHistoryInfo(final IUsersession usersession, final IProjectId projectId,
		final IHistoryQuery query) throws EmfStoreException;

	List<IProjectInfo> getRemoteProjectList(IServerInfo serverInfo) throws EmfStoreException;

	List<IProjectInfo> getRemoteProjectList(IUsersession usersession) throws EmfStoreException;

	IPrimaryVersionSpec resolveVersionSpec(ServerInfo serverInfo, final VersionSpec versionSpec,
		final ProjectId projectId) throws EmfStoreException;

	IPrimaryVersionSpec resolveVersionSpec(final IUsersession usersession, final IVersionSpec versionSpec,
		final IProjectId projectId) throws EmfStoreException;

	void save();
}
