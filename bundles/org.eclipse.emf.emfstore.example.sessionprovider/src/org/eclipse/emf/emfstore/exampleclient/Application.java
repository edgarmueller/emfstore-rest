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
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * An application that runs the demo.<br>
 * Run a client that shows the merging feature of the EMFstore
 * Please note: this is the programmatic way of merging
 * EMFStore also provides a default UI for merging
 * If there is a problem with the connection to the server
 * e.g. a network, a specific ESException will be thrown
 */
@SuppressWarnings("restriction")
public class Application implements IApplication {

	private ESUsersession usersession;

	/**
	 * {@inheritDoc}
	 */
	// BEGIN SUPRESS CATCH EXCEPTION
	public Object start(IApplicationContext context) throws Exception {

		// run a client that shows the basic features of the emf store
		runClient();

		return IApplication.EXIT_OK;
	}

	// END SUPRESS CATCH EXCEPTION

	private void runClient() throws ESException {
		System.out.println("Client starting...");

		// The workspace is the core controller to access
		// local and remote projects
		final ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();

		// Sets up the workspace by cleaning all contents
		setupWorkspace();

		try {

			/*
			 * Create a project, share it with the server, add a model element
			 * to it and commit the changes.
			 */

			// create a new local project
			// and share it with the server
			ESLocalProject project1 = workspace.createLocalProject("projectNo1");
			project1.shareProject(usersession, null);

			// create a league
			// and add 2 players to it
			League league1 = BowlingFactory.eINSTANCE.createLeague();
			league1.setName("league no. 1");
			league1.getPlayers().add(createPlayer("no. 1"));
			league1.getPlayers().add(createPlayer("no. 2"));
			project1.getModelElements().add(league1);

			// commit the changes of the project to the EMF
			// Store
			project1.commit(null, null, null);
			System.out.println("Project 1: The \"league no. 1\" was sent to the server!");

			/*
			 * Now lets checkout the same project twice, modify the element and
			 * commit the changes to the server.
			 */
			ESLocalProject project2 = project1.getRemoteProject().checkout("My checkout",
				usersession, new NullProgressMonitor());
			League league2 = (League) project2.getModelElements().get(0);
			System.out.println(String.format("Project 2: League \"%s\" was checked out twice!", league1.getName()));
			league2.setName("league no. 1 - changed");
			// now lets try to commit
			project2.commit(null, null, null);

			/*
			 * Lets check the value of the league contained in project 1. Then
			 * we update the values from the server and check the contents
			 * again.
			 */
			System.out.println(String.format("Project 1: Name of league is \"%s\"", league1.getName()));

			// update from server
			project1.update(new NullProgressMonitor());

			System.out.println(String.format("Project 1: Name of league is \"%s\" after update from server",
				league1.getName()));

			/*
			 * Now lets create a conflict!
			 */
			System.out.println("Creating a conflict");
			league1.setName("Not conflicting change");
			project1.commit(null, null, null);

			try {
				league2.setName("Uuups - conflicting change");
				project2.commit(null, null, null);
			} catch (ESException e) {
				System.out.println("That was a conflict since league1#name was changed and commited earlier!");

				/*
				 * Now lets revert the changes, update the project and change it
				 * afterwards
				 */
				project2.revert();
				project2.update(new NullProgressMonitor());
				league2.setName("Not conflicting anymore");
				project2.commit(null, null, null);
			}

			System.out.println("Client run completed.");
		} catch (ESException e) {
			ModelUtil.logException(e);
		}
	}

	/**
	 * Creates a default workspace and deletes all remote projects.
	 * 
	 * @throws ESException
	 */
	private void setupWorkspace() throws ESException {
		// A user session stores credentials for login
		// Creates a user session with the default credentials
		usersession = new EMFStoreCommandWithResult<ESUsersession>() {
			@Override
			protected ESUsersession doRun() {
				Usersession session = EMFStoreClientUtil.createUsersession();
				try {
					session.logIn();
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
				return session.toAPI();
			}
		}.run(false);

		// Retrieves a list of existing (and accessible) projects
		// on the sever and deletes them permanently (to have a
		// clean set-up)
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					List<ESRemoteProject> projectList = usersession.getServer().getRemoteProjects();
					for (ESRemoteProject projectInfo : projectList) {
						projectInfo.delete(usersession, new NullProgressMonitor());
					}
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);
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