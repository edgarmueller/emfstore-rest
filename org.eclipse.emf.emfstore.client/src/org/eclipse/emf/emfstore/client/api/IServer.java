package org.eclipse.emf.emfstore.client.api;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public interface IServer {

	String getName();

	int getPort();

	String getUrl();

	String getCertificateAlias();

	List<? extends IRemoteProject> getRemoteProjects();

	IUsersession getLastUsersession();

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
	public abstract List<? extends IRemoteProject> getRemoteProjectList() throws EmfStoreException;

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
	public abstract List<? extends IRemoteProject> getRemoteProjectList(IUsersession usersession)
		throws EmfStoreException;

	public abstract IRemoteProject createEmptyRemoteProject(final IUsersession usersession, final String projectName,
		final String projectDescription, final IProgressMonitor progressMonitor) throws EmfStoreException;

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
	public abstract IRemoteProject createRemoteProject(final String projectName, final String projectDescription,
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
	public abstract IRemoteProject createRemoteProject(IUsersession usersession, final String projectName,
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
	public abstract void deleteRemoteProject(final IProjectId projectId, final boolean deleteFiles)
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
	public abstract void deleteRemoteProject(final IUsersession usersession, final IProjectId projectId,
		final boolean deleteFiles) throws EmfStoreException;
}
