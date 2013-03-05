/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * Container for all local projects and available servers.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESWorkspace {

	/**
	 * Returns all local projects.
	 * 
	 * @return list of local projects
	 */
	List<ESLocalProject> getLocalProjects();

	/**
	 * Creates a new local project that is not shared with the server yet.
	 * 
	 * @param projectName
	 *            the project's name
	 * @return the unshared local project
	 */
	ESLocalProject createLocalProject(String projectName);

	/**
	 * Returns the {@link ESLocalProject} the given model element is contained in.
	 * 
	 * @param modelElement
	 *            the model element whose project should be returned
	 * @return the local project the given model element is contained in
	 */
	ESLocalProject getLocalProject(EObject modelElement);

	/**
	 * Returns all available servers.
	 * 
	 * @return a list of servers
	 */
	List<ESServer> getServers();

	/**
	 * Adds a server to the workspace.
	 * 
	 * @param server
	 *            the server to be added to the workspace
	 * 
	 */
	void addServer(ESServer server);

	/**
	 * Removes a server from the workspace.
	 * 
	 * @param server
	 *            the server to be removed from the workspace
	 */
	void removeServer(ESServer server);
}