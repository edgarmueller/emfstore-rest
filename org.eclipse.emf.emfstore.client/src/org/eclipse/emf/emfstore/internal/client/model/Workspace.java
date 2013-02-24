/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ProjectUrlResolutionException;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ServerUrlResolutionException;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnkownProjectException;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.url.ProjectUrlFragment;
import org.eclipse.emf.emfstore.internal.server.model.url.ServerUrl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Workspace</b></em>'.
 * 
 * @implements IAdaptable <!-- end-user-doc -->
 * 
 *             <p>
 *             The following features are supported:
 *             <ul>
 *             <li>
 *             {@link org.eclipse.emf.emfstore.client.model.Workspace#getProjectSpaces
 *             <em>Project Spaces</em>}</li>
 *             <li>
 *             {@link org.eclipse.emf.emfstore.client.model.Workspace#getServerInfos
 *             <em>Server Infos</em>}</li>
 *             <li>
 *             {@link org.eclipse.emf.emfstore.client.model.Workspace#getUsersessions
 *             <em>Usersessions</em>}</li>
 *             </ul>
 *             </p>
 * 
 * @see org.eclipse.emf.emfstore.client.model.ModelPackage#getWorkspace()
 * @model
 * @generated
 */
public interface Workspace extends EObject, IAdaptable, APIDelegate<ESWorkspaceImpl> {

	/**
	 * Creates a new local project that is not shared with the server yet.
	 * 
	 * @param projectName
	 *            the project name
	 * @param projectDescription
	 *            the project description
	 * @return the project space that the new project resides in
	 */
	ProjectSpace createLocalProject(String projectName);

	/**
	 * Exports a project space to a file.
	 * 
	 * @param projectSpace
	 *            The project space that should be exported
	 * @param file
	 *            The file to export to
	 * @throws IOException
	 *             If creating the export file fails
	 */
	void exportProjectSpace(ProjectSpace projectSpace, File file) throws IOException;

	/**
	 * Exports a project space to a file.
	 * 
	 * @param projectSpace
	 *            The project space that should be exported
	 * @param file
	 *            The file to export to
	 * @param progressMonitor
	 *            The progress monitor that should be used during the xport
	 * @throws IOException
	 *             If creating the export file fails
	 */
	void exportProjectSpace(ProjectSpace projectSpace, File file, IProgressMonitor progressMonitor) throws IOException;

	/**
	 * Exports the whole workspace.
	 * 
	 * @param file
	 *            The file to export to
	 * @throws IOException
	 *             If creating the export file fails
	 */
	void exportWorkSpace(File file) throws IOException;

	/**
	 * Exports the whole workspace to the given file.
	 * 
	 * @param file
	 *            The file to export to
	 * @param progressMonitor
	 *            The progress monitor that should be used during the export
	 * @throws IOException
	 *             If creating the export file fails
	 */
	void exportWorkSpace(File file, IProgressMonitor progressMonitor) throws IOException;

	/**
	 * Returns an {@link AdminBroker} related to the given {@link ServerInfo}.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} that should be used to retrieve the
	 *            admin broker.
	 * @return an {@link AdminBroker} related to the given server info.
	 * @throws ESException
	 *             If an error occurs while retrieving the admin broker
	 * @throws AccessControlException
	 *             If access is denied
	 * @generated NOT
	 */
	AdminBroker getAdminBroker(ServerInfo serverInfo) throws ESException, AccessControlException;

	/**
	 * Returns an {@link AdminBroker} related to the given {@link Usersession}.
	 * 
	 * @param session
	 *            The user session that should be used to retrieve the admin
	 *            broker.<br/>
	 *            If <code>null</code>, the session manager will search for a
	 *            session.
	 * @return an {@link AdminBroker} related to the given user session.
	 * @throws ESException
	 *             If an error occurs while retrieving the admin broker
	 * @throws AccessControlException
	 *             If access is denied
	 * @generated NOT
	 */
	AdminBroker getAdminBroker(Usersession session) throws ESException, AccessControlException;

	/**
	 * Return this editing domain belonging to this workspace.
	 * 
	 * @return the editing domain
	 * @generated NOT
	 */
	EditingDomain getEditingDomain();

	/**
	 * Retrieves the project space for the given project.
	 * 
	 * @param project
	 *            The project for which to retrieve the project space.
	 * @return the project space the given project is contained in
	 * @throws UnkownProjectException
	 *             If the project is not known to the workspace
	 */
	ProjectSpace getProjectSpace(Project project) throws UnkownProjectException;

	/**
	 * Returns the value of the '<em><b>Project Spaces</b></em>' containment
	 * reference list. The list contents are of type {@link org.eclipse.emf.emfstore.client.model.ProjectSpace}. It is
	 * bidirectional and its opposite is ' {@link org.eclipse.emf.emfstore.client.model.ProjectSpace#getWorkspace
	 * <em>Workspace</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Spaces</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Project Spaces</em>' containment reference
	 *         list.
	 * @see org.eclipse.emf.emfstore.client.model.ModelPackage#getWorkspace_ProjectSpaces()
	 * @see org.eclipse.emf.emfstore.client.model.ProjectSpace#getWorkspace
	 * @model opposite="workspace" containment="true" resolveProxies="true"
	 *        keys="identifier"
	 * @generated
	 */
	EList<ProjectSpace> getProjectSpaces();

	/**
	 * Returns the value of the '<em><b>Server Infos</b></em>' containment
	 * reference list. The list contents are of type {@link org.eclipse.emf.emfstore.client.model.ServerInfo}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server Infos</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Server Infos</em>' containment reference
	 *         list.
	 * @see org.eclipse.emf.emfstore.client.model.ModelPackage#getWorkspace_ServerInfos()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<ServerInfo> getServerInfos();

	/**
	 * Returns the value of the '<em><b>Usersessions</b></em>' containment
	 * reference list. The list contents are of type {@link org.eclipse.emf.emfstore.client.model.Usersession}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usersessions</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Usersessions</em>' containment reference
	 *         list.
	 * @see org.eclipse.emf.emfstore.client.model.ModelPackage#getWorkspace_Usersessions()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Usersession> getUsersessions();

	/**
	 * Imports a project into a project space.
	 * 
	 * @param project
	 *            The project to be imported
	 * @param name
	 *            The name that should be assigned to the project being
	 *            imported.
	 * @param description
	 *            A description of the project being imported
	 * @return the newly created project space in which the imported project is
	 *         contained in
	 */
	ProjectSpace importProject(Project project, String name, String description);

	/**
	 * Import an existing project from a given file. The project space
	 * containing the project will be created upon execution.
	 * 
	 * @param absoluteFileName
	 *            The absolute path to a file to import from.
	 * @return the newly created project space in which the imported project is
	 *         contained in
	 * @throws IOException
	 *             If importing the project fails
	 */
	ProjectSpace importProject(String absoluteFileName) throws IOException;

	/**
	 * Import an existing project space from a file.
	 * 
	 * @param absoluteFileName
	 *            The absolute path to a file to import from.
	 * @return the imported project space
	 * @throws IOException
	 *             If accessing the file or importing fails
	 */
	ProjectSpace importProjectSpace(String absoluteFileName) throws IOException;

	/**
	 * Initializes the workspace and its project spaces.
	 * 
	 * @generated NOT
	 */
	void init();

	/**
	 * Resolves a project URL fragment to the project space the project is in.<br/>
	 * Since a project may have been checked out multiple times, a set of
	 * project spaces is returned.
	 * 
	 * @param projectUrlFragment
	 *            the project URL fragment to resolve
	 * @return a set of matching project spaces
	 * @throws ProjectUrlResolutionException
	 *             if the project belonging to the given project URL fragment
	 *             cannot be found in workspace
	 */
	Set<ProjectSpace> resolve(ProjectUrlFragment projectUrlFragment) throws ProjectUrlResolutionException;

	/**
	 * Resolves a server URL to a server.
	 * 
	 * @param serverUrl
	 *            the server URL to be resolved
	 * @return the resolved {@link ServerInfo}
	 * @throws ServerUrlResolutionException
	 *             if no matching server info can be found
	 */
	Set<ServerInfo> resolve(ServerUrl serverUrl) throws ServerUrlResolutionException;

	/**
	 * Make the current workspace state persistent.
	 */
	void save();

	/**
	 * Returns the workspace resource set.
	 * 
	 * @return The resource set of the workspace
	 * @generated NOT
	 */
	ResourceSet getResourceSet();

	/**
	 * Set the workspace resource set.
	 * 
	 * @param resourceSet
	 *            The resource set to be set.
	 * @generated NOT
	 */
	void setResourceSet(ResourceSet resourceSet);

	/**
	 * Updates the ACUser and it roles.
	 * 
	 * @param serverInfo
	 *            The {@link ServerInfo} that is used to update the ACUser.
	 * @throws ESException
	 *             if an error occurs while updating the ACUser
	 */
	void updateACUser(ServerInfo serverInfo) throws ESException;

	/**
	 * Updates the ACUser and it roles.
	 * 
	 * @param session
	 *            The {@link Usersession} that should be used to update the
	 *            ACUser. If <code>null</code>, the session manager will search
	 *            for a session.
	 * @throws ESException
	 *             if an error occurs while updating the ACUser
	 */
	void updateACUser(Usersession session) throws ESException;

	/**
	 * Adds an server info and saves.
	 * 
	 * @param serverInfo
	 *            the server info to be added
	 */
	void addServerInfo(ServerInfo serverInfo);

	/**
	 * Removes an server info and saves.
	 * 
	 * @param serverInfo
	 *            the server info to be removed
	 */
	void removeServerInfo(ServerInfo serverInfo);

} // Workspace