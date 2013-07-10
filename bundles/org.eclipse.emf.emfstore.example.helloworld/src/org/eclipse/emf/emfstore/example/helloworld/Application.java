/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Florian Pirchner
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.example.helloworld;

import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.exceptions.ESServerNotFoundException;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.common.ESSystemOutProgressMonitor;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;


/**
 * An application that runs the demo.<br>
 * Run a client and local server that demo the basic features of EMFStore.
 */
public class Application implements IApplication {

	/**
	 * {@inheritDoc}
	 */
	public Object start(IApplicationContext context) {

		try {
			// Create a client representation for a local server and start a local server.
			ESServer localServer = ESServer.FACTORY.createAndStartLocalServer();
			// Run a client on the local server that shows the basic features of the EMFstore
			runClient(localServer);
		} catch (ESServerStartFailedException e) {
			System.out.println("Server start failed!");
			e.printStackTrace();
		} catch (ESException e) {
			// If there is a problem with the connection to the server,
			// e.g., a network, a specific EMFStoreException will be thrown.
			System.out.println("Connection to Server failed!");
			e.printStackTrace();
		}
		return IApplication.EXIT_OK;
	}

	/**
	 * Run an EMFStore Client connecting to the given server.
	 * @param server the server 
	 * @throws ESException if the server connection fails
	 */
	public static void runClient(ESServer server) throws ESException {
		System.out.println("Client starting...");

		// The workspace is the core controller to access local and remote projects.
		// A project is a container for models and their elements (EObjects).
		// To get started, we obtain the current workspace of the client.
		ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();

		// The workspace stores all available servers that have been configured. We add the local server that has already
		// been started on the workspace.
		workspace.addServer(server);
		// Next, we remove all other existing servers
		for (ESServer existingServer : workspace.getServers()) {
			if (existingServer != server) {
				try {
					workspace.removeServer(existingServer);
				} catch (ESServerNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		// The workspace also contains a list of local projects that have either been created locally or checked out
		// from a server.
		// We create a new local project. The project new created is not yet shared with the server.
		ESLocalProject demoProject = workspace.createLocalProject("DemoProject");

		// We delete all projects from the local workspace other than the one just created.
		for (ESLocalProject existingLocalProject : workspace.getLocalProjects()) {
			if (existingLocalProject != demoProject) {
				try {
					existingLocalProject.delete(new ESSystemOutProgressMonitor());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// Next, we create a user session by logging in to the local EMFStore server with default super user credentials.
		ESUsersession usersession = server.login("super", "super");

		// Now we can share the created local project to our server.
		ESRemoteProject remoteDemoProject = demoProject.shareProject(usersession, new ESSystemOutProgressMonitor());

		// We also retrieve a list of existing (and accessible) remote projects on the server.
		// Remote projects represent a project that is currently available on the server.
		// We delete all remote projects to clean up remaining projects from previous launches.
		for (ESRemoteProject existingRemoteProject : server.getRemoteProjects(usersession)) {
			if (!existingRemoteProject.getGlobalProjectId().equals(remoteDemoProject.getGlobalProjectId())) {
				existingRemoteProject.delete(usersession, new NullProgressMonitor());
			}
		}

		// Now we are all set: we have a client workspace with one server configured and exactly one project shared to a
		// server with only this one project.

		// We check out a second, independent copy of the project (simulating a second client).
		ESLocalProject demoProjectCopy = demoProject.getRemoteProject().checkout("DemoProject Copy",
			usersession, new ESSystemOutProgressMonitor());

		// We start working now with the local project and later we will synchronize it with the copy of the project we
		// just checked out.
		// We create some EObjects and add them to the project, that is, to project’s containment tree. Everything that
		// is
		// in the project’s containment tree (spanning tree on containment references) is considered part of the
		// project. We will use an example model about bowling.

		// First we add a league and set the league name.
		League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName("Suprbowling League");

		// Next we add the league to the root of the project. The project has a containment feature called model
		// element that holds all root elements of a project. This list is comparable to the content list in EMF
		// Resources that
		// you can retrieve with getContents(). Adding something to the list will add it to the project.
		demoProject.getModelElements().add(league);

		// Then we create two players.
		Player player1 = BowlingFactory.eINSTANCE.createPlayer();
		player1.setName("Maximilian");
		Player player2 = BowlingFactory.eINSTANCE.createPlayer();
		player2.setName("Ottgar");

		// Finally, we add the players to the league. Since the league is already part of the project and League.players
		// is a containment feature, the players also become part of the project.
		league.getPlayers().add(player1);
		league.getPlayers().add(player2);

		// To synchronize the local changes of the client with the server, we will commit the project.
		demoProject.commit("My message", null,
			new ESSystemOutProgressMonitor());
		// The server is now up-to-date, but we still need to synchronize the copy of the project we checked out
		// earlier.
		demoProjectCopy.update(new ESSystemOutProgressMonitor());

		// We will now retrieve the copy of the league from the copy of the project and assert its name and player count
		// are equal with the name of the project’s league.
		League leagueCopy = (League) demoProjectCopy.getModelElements().get(0);
		if (league.getName().equals(leagueCopy.getName()) &&
			league.getPlayers().size()==leagueCopy.getPlayers().size()) {
			System.out.println("Leagues names and player count are equal.");
		}
		
		// Of course, we can also change something in the project copy and synchronize it back to the project. 
		// We change the league name to correct the type and then commit and update accordingly.
		// This time, we use the IDs assigned to every EObject of a project to identify the copy of league in the project’s copy.
		leagueCopy = (League) demoProjectCopy.getModelElement(demoProject.getModelElementId(league));
		league.setName("Superbowling League");
		demoProject.commit(new ESSystemOutProgressMonitor());
		demoProjectCopy.update(new ESSystemOutProgressMonitor());
		
		if (league.getName().equals(leagueCopy.getName()) &&
			league.getPlayers().size()==leagueCopy.getPlayers().size()) {
			System.out.println("Leagues names and player count are still equal.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}
}