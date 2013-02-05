package org.eclipse.emf.emfstore.client.api;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public interface IProject {

	IProjectId getProjectId();

	String getProjectName();

	String getProjectDescription();

	/**
	 * Returns a list of branches of the current project. Every call triggers a
	 * server call.
	 * 
	 * @return list of {@link IBranchInfo}
	 * @throws EmfStoreException
	 *             in case of an exception
	 */
	List<? extends IBranchInfo> getBranches() throws EmfStoreException;

	/**
	 * <!-- begin-user-doc --> Resolve a version spec to a primary version spec.
	 * 
	 * @param versionSpec
	 *            the spec to resolve
	 * @return the primary version spec <!-- end-user-doc -->
	 * @throws EmfStoreException
	 *             if resolving fails
	 * @model
	 * @generated NOT
	 */
	IPrimaryVersionSpec resolveVersionSpec(IVersionSpec versionSpec) throws EmfStoreException;

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
	public abstract IPrimaryVersionSpec resolveVersionSpec(final IUsersession usersession,
		final IVersionSpec versionSpec) throws EmfStoreException;

	/**
	 * Gets a list of history infos.
	 * 
	 * @param query
	 *            the query to be performed in order to fetch the history
	 *            information
	 * 
	 * @see Workspace
	 * @return a list of history infos
	 * @throws EmfStoreException
	 *             if server the throws an exception
	 * @generated NOT
	 */
	List<? extends IHistoryInfo> getHistoryInfos(IHistoryQuery query) throws EmfStoreException;

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
	public abstract List<? extends IHistoryInfo> getHistoryInfo(final IUsersession usersession,
		final IHistoryQuery query) throws EmfStoreException;

	/**
	 * Adds a tag to the specified version of this project.
	 * 
	 * @param versionSpec
	 *            the versionSpec
	 * @param tag
	 *            the tag
	 * @throws EmfStoreException
	 *             if exception occurs on the server
	 * 
	 * @generated NOT
	 */
	void addTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EmfStoreException;

	/**
	 * Removes a tag to the specified version of this project.
	 * 
	 * @param versionSpec
	 *            the versionSpec
	 * @param tag
	 *            the tag
	 * @throws EmfStoreException
	 *             if exception occurs on the server
	 * 
	 * @generated NOT
	 */
	void removeTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EmfStoreException;
}
