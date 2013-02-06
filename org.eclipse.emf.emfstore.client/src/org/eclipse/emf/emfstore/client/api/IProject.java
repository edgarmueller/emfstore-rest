package org.eclipse.emf.emfstore.client.api;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

public interface IProject {

	IProjectId getProjectId();

	String getProjectName();

	String getProjectDescription();

	/**
	 * Deletes the project space.
	 * 
	 * @generated NOT
	 * 
	 * @throws IOException
	 *             in case the project space could not be deleted
	 */
	void delete() throws IOException, EmfStoreException;

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
	List<? extends IHistoryInfo> getHistoryInfos(final IHistoryQuery query) throws EmfStoreException;

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
