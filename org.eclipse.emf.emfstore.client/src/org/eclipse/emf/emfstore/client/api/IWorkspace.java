package org.eclipse.emf.emfstore.client.api;

import java.util.List;

public interface IWorkspace {

	List<? extends ILocalProject> getLocalProjects();

	/**
	 * Creates a new local project that is not shared with the server yet.
	 * 
	 * @param projectName
	 *            the project name
	 * @param projectDescription
	 *            the project description
	 * @return the project space that the new project resides in
	 */
	ILocalProject createLocalProject(String projectName, String projectDescription);

	List<? extends IServer> getServers();

	IServer getServerByUsersession(IUsersession session);

	/**
	 * Adds an server info and saves.
	 * 
	 * @param serverInfo
	 *            the server info to be added
	 */
	void addServer(IServer serverInfo);

	/**
	 * Removes an server info and saves.
	 * 
	 * @param serverInfo
	 *            the server info to be removed
	 */
	void removeServer(IServer serverInfo);
}