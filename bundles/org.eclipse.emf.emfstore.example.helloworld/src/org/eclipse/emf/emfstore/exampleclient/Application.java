/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Florian Pirchner
 ******************************************************************************/
package org.eclipse.emf.emfstore.exampleclient;

import java.util.List;

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
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESLogMessageFactory;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * An application that runs the demo.<br>
 * Run a client that shows the basic features of the EMFstore
 * If there is a problem with the connection to the server
 * e.g. a network, a specific EMFStoreException will be thrown
 */
public class Application implements IApplication {

	/**
	 * {@inheritDoc}
	 */
	// BEGIN SUPRESS CATCH EXCEPTION
	public Object start(IApplicationContext context) throws Exception {

		// Run a client that shows the basic features of the EMFstore
		// If there is a problem with the connection to the server
		// e.g. a network, a specific EMFStoreException will be thrown
		try {
			runClient();

		} catch (ESException e) {
			System.out.println("No connection to server.");
			System.out.println("Did you start the server? :-)");
		}

		return IApplication.EXIT_OK;
	}

	// END SUPRESS CATCH EXCEPTION

	private void runClient() throws ESException {
		System.out.println("Client starting...");

		// The workspace is the core controller to access local and remote projects
		ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();

		// A user session stores credentials for login
		// Create a user by login in to the local EMFStore server
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		ESUsersession usersession = server.login("super", "super");

		// Retrieves a list of existing (and accessible) projects on the sever and deletes them permanently (to have a
		// clean set-up)
		List<ESRemoteProject> projectList = server.getRemoteProjects(usersession);
		for (ESRemoteProject project : projectList) {
			project.delete(usersession, new NullProgressMonitor());
		}

		// Create a project, share it with the server
		final ESLocalProject projectNo1 = workspace.createLocalProject("projectNo1");
		projectNo1.shareProject(usersession, new NullProgressMonitor());

		// Create some EObjects and add them to the project (To the projects containment tree)
		final League league1 = BowlingFactory.eINSTANCE.createLeague();
		league1.setName("league");
		league1.getPlayers().add(createPlayer("no. 1"));
		league1.getPlayers().add(createPlayer("no. 2"));
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectNo1.getModelElements().add(league1);
			}
		}.run(false);
		System.out.println("Project 1: League name is " + league1.getName());

		// commit the changes of the project to the EMFStore including a commit message
		projectNo1.commit(ESLogMessageFactory.INSTANCE.createLogMessage("My message", usersession.getUsername()), null,
			new NullProgressMonitor());

		// Check-out a second, independent copy of the project (simulating a second client)
		ESLocalProject projectNo2 = projectNo1.getRemoteProject().checkout(usersession, new NullProgressMonitor());

		// Get a second copy of the league
		final League league2 = (League) projectNo2.getModelElements().get(0);
		System.out.println("Project 2: League name is " + league2.getName());

		// Apply changes in the second copy of the project ...
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				league2.setName("league_changed");
			}
		}.run(false);
		// ... and commit them
		projectNo2.commit(ESLogMessageFactory.INSTANCE.createLogMessage("My message", usersession.getUsername()), null,
			new NullProgressMonitor());
		System.out.println("Project 2: League name is " + league2.getName());
		System.out.println("Project 2 committed!");

		// Update the first copy of the project
		projectNo1.update(new NullProgressMonitor());
		System.out.println("Project 1 updated!");
		System.out.println("Project 1: League name is " + league1.getName());

		System.out.println("Client run completed.");

	}

	/**
	 * Creates a new instance of a player.
	 * 
	 * @param name
	 * @return
	 */
	private Player createPlayer(String name) {
		Player player = BowlingFactory.eINSTANCE.createPlayer();
		player.setName(String.format("Player %s", name));
		return player;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}
}