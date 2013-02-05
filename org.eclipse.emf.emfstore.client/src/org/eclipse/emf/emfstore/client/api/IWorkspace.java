package org.eclipse.emf.emfstore.client.api;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.IProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public interface IWorkspace {

	/**
	 * Adds an server info and saves.
	 * 
	 * @param serverInfo
	 *            the server info to be added
	 */
	void addServerInfo(IServerInfo serverInfo);

	/**
	 * Removes an server info and saves.
	 * 
	 * @param serverInfo
	 *            the server info to be removed
	 */
	void removeServerInfo(IServerInfo serverInfo);

	List<? extends IServerInfo> getServerInfos();

	List<? extends IProject> getProjects();

	/**
	 * Checkout a project to the workspace in a given version.
	 * 
	 * @param usersession
	 *            The user session that should be used to checkout the project.
	 * @param projectInfo
	 *            An {@link ProjectInfo} instance describing the project and its
	 *            version.
	 * @throws EmfStoreException
	 *             If an error occurs during the checkout.
	 * @return the project space containing the project
	 * @model
	 * @generated NOT
	 */
	IProject checkout(final IUsersession usersession, final IProjectInfo projectInfo) throws EmfStoreException;

	/**
	 * Checkout a project to the workspace in a given version.
	 * 
	 * @param usersession
	 *            The user session that should be used to checkout the project.
	 * @param projectInfo
	 *            An {@link ProjectInfo} instance describing the project and its
	 *            version.
	 * @param progressMonitor
	 *            the progress monitor that should be used during checkout
	 * @throws EmfStoreException
	 *             If an error occurs during the checkout.
	 * @return the project space containing the project
	 * @model
	 * @generated NOT
	 */
	IProject checkout(final IUsersession usersession, final IProjectInfo projectInfo, IProgressMonitor progressMonitor)
		throws EmfStoreException;

	/**
	 * Checkout a project to the workspace in a given version.
	 * 
	 * @param usersession
	 *            The user session that should be used to checkout the project.
	 * @param projectInfo
	 *            An {@link ProjectInfo} instance describing the project and its
	 *            version.
	 * @param targetSpec
	 *            The target version.
	 * @param progressMonitor
	 *            the progress monitor that should be used during checkout
	 * @throws EmfStoreException
	 *             If an error occurs during the checkout.
	 * @return the project space containing the project
	 * @model
	 * @generated NOT
	 */
	IProject checkout(final IUsersession usersession, final IProjectInfo projectInfo, IVersionSpec versionSpec,
		IProgressMonitor progressMonitor) throws EmfStoreException;

	IProjectInfo createEmptyRemoteProject(final IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor progressMonitor) throws EmfStoreException;

	/**
	 * Creates a new local project that is not shared with the server yet.
	 * 
	 * @param projectName
	 *            the project name
	 * @param projectDescription
	 *            the project description
	 * @return the project space that the new project resides in
	 */
	IProject createLocalProject(String projectName, String projectDescription);

	/**
	 * Creates an empty project on the server.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} that contains information about the
	 *            server on which the project should be created.
	 * @param projectName
	 *            The name of the project.
	 * @param projectDescription
	 *            A description of the project to be created.
	 * @param monitor
	 *            a progress monitor instance that is used to indicate progress
	 *            about creating the remote project
	 * @return a {@link ProjectInfo} object containing information about the
	 *         created project
	 * @throws EmfStoreException
	 *             If an error occurs while creating the remote project
	 */
	IProjectInfo createRemoteProject(IServerInfo serverInfo, final String projectName, final String projectDescription,
		final IProgressMonitor monitor) throws EmfStoreException;

	/**
	 * Creates an empty project on the server.
	 * 
	 * @param usersession
	 *            The {@link Usersession} that should be used to create the
	 *            remote project.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @param projectName
	 *            The name of the project.
	 * @param projectDescription
	 *            A description of the project to be created.
	 * @param monitor a monitor to show the progress
	 * @return a {@link ProjectInfo} object containing information about the
	 *         created project
	 * @throws EmfStoreException
	 *             If an error occurs while creating the remote project
	 */
	IProjectInfo createRemoteProject(IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor monitor) throws EmfStoreException;

	/**
	 * Deletes a project on the server.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo}, that contains the information on which
	 *            server the project is located on.
	 * @param projectId
	 *            The ID of the project.
	 * @param deleteFiles
	 *            Whether files should be deleted too
	 * @throws EmfStoreException
	 *             If an error occurs while deleting the project.
	 */
	void deleteRemoteProject(IServerInfo serverInfo, final IProjectId projectId, final boolean deleteFiles)
		throws EmfStoreException;

	/**
	 * Deletes a project on the server.
	 * 
	 * @param usersession
	 *            The {@link Usersession} that should be used to delete the
	 *            project.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @param projectId
	 *            The ID of the project.
	 * @param deleteFiles
	 *            Whether files should be deleted too
	 * @throws EmfStoreException
	 *             If an error occurs while deleting the project.
	 */
	void deleteRemoteProject(final IUsersession usersession, final IProjectId projectId, final boolean deleteFiles)
		throws EmfStoreException;

	/**
	 * List all branches of the specified project. Every call triggers a server
	 * call.
	 * 
	 * @param serverInfo
	 *            server specification
	 * @param projectId
	 *            project id
	 * @return list of {@link BranchInfo}
	 * @throws EmfStoreException
	 *             in case of an exception
	 */
	List<? extends IBranchInfo> getBranches(IServerInfo serverInfo, final IProjectId projectId)
		throws EmfStoreException;

	/**
	 * Retrieves history information for a project.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} that contains information about the
	 *            server the project is located on
	 * @param projectId
	 *            The ID of a project
	 * @param query
	 *            A history query.
	 * @return a list of {@link HistoryInfo} instances
	 * @throws EmfStoreException
	 *             If an error occurs while retrieving the history information
	 * @generated NOT
	 */
	List<? extends IHistoryInfo> getHistoryInfo(IServerInfo serverInfo, final IProjectId projectId,
		final IHistoryQuery query) throws EmfStoreException;

	/**
	 * Retrieves history information for a project.
	 * 
	 * @param usersession
	 *            The {@link Usersession} that should be used to retrieve the
	 *            history information. If <code>null</code>, the session manager
	 *            will search for a session.
	 * @param projectId
	 *            The ID of a project
	 * @param query
	 *            A history query.
	 * @return a list of {@link HistoryInfo} instances
	 * @throws EmfStoreException
	 *             If an error occurs while retrieving the history information
	 * @generated NOT
	 */
	List<? extends IHistoryInfo> getHistoryInfo(final IUsersession usersession, final IProjectId projectId,
		final IHistoryQuery query) throws EmfStoreException;

	/**
	 * Get the list of remotely available projects.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} that should be used to retrieve the
	 *            information about remote projects.<br/>
	 * @return a list containing the information about each remote project
	 * @throws EmfStoreException
	 *             If an error occurs while retrieving the remote project list.
	 * @generated NOT
	 */
	List<? extends IProjectInfo> getRemoteProjectList(IServerInfo serverInfo) throws EmfStoreException;

	/**
	 * Get the list of remotely available projects.
	 * 
	 * @param usersession
	 *            The {@link Usersession} that should be used to retrieve the
	 *            remote project list.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @return a list containing the information about each remote project
	 * @throws EmfStoreException
	 *             If an error occurs while retrieving the remote project list.
	 * @generated NOT
	 */
	List<? extends IProjectInfo> getRemoteProjectList(IUsersession usersession) throws EmfStoreException;

	/**
	 * Resolves a {@link VersionSpec} to a {@link PrimaryVersionSpec}.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} that should be used to resolve the
	 *            given version specification.
	 * @param versionSpec
	 *            The specification to resolve.
	 * @param projectId
	 *            The ID of a project.
	 * @return the {@link PrimaryVersionSpec}
	 * @throws EmfStoreException
	 *             If an error occurs while resolving the {@link VersionSpec}
	 * @model
	 * @generated NOT
	 */
	IPrimaryVersionSpec resolveVersionSpec(IServerInfo serverInfo, final IVersionSpec versionSpec,
		final IProjectId projectId) throws EmfStoreException;

	/**
	 * Resolves a {@link VersionSpec} to a {@link PrimaryVersionSpec}.
	 * 
	 * @param session
	 *            The {@link Usersession} that should be used to resolve the
	 *            given {@link VersionSpec}.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @param versionSpec
	 *            The specification to resolve.
	 * @param projectId
	 *            The ID of a project.
	 * @return the {@link PrimaryVersionSpec}
	 * @throws EmfStoreException
	 *             If an error occurs while resolving the {@link VersionSpec}
	 * @model
	 * @generated NOT
	 */
	IPrimaryVersionSpec resolveVersionSpec(final IUsersession usersession, final IVersionSpec versionSpec,
		final IProjectId projectId) throws EmfStoreException;

}
