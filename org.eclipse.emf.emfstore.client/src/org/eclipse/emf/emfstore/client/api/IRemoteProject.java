package org.eclipse.emf.emfstore.client.api;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;

public interface IRemoteProject extends IProject {

	IServer getServer();

	ILocalProject checkout() throws EmfStoreException;

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
	ILocalProject checkout(final IUsersession usersession) throws EmfStoreException;

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
	ILocalProject checkout(final IUsersession usersession, IProgressMonitor progressMonitor) throws EmfStoreException;

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
	ILocalProject checkout(final IUsersession usersession, IVersionSpec versionSpec, IProgressMonitor progressMonitor)
		throws EmfStoreException;

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
	List<? extends IHistoryInfo> getHistoryInfos(IUsersession usersession, IHistoryQuery query)
		throws EmfStoreException;

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
	IPrimaryVersionSpec resolveVersionSpec(final IUsersession usersession, final IVersionSpec versionSpec)
		throws EmfStoreException;

}
