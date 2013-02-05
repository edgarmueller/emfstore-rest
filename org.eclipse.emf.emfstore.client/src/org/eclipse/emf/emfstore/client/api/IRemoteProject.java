package org.eclipse.emf.emfstore.client.api;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.api.IProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;

public interface IRemoteProject extends IProject {

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
	ILocalProject checkout(final IUsersession usersession, final IProjectInfo projectInfo) throws EmfStoreException;

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
	ILocalProject checkout(final IUsersession usersession, final IProjectInfo projectInfo,
		IProgressMonitor progressMonitor) throws EmfStoreException;

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
	ILocalProject checkout(final IUsersession usersession, final IProjectInfo projectInfo, IVersionSpec versionSpec,
		IProgressMonitor progressMonitor) throws EmfStoreException;

}
