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
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.common.ConsoleProgressMonitor;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
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

		} catch (EmfStoreException e) {
			System.out.println("No connection to server.");
			System.out.println("Did you start the server? :-)");
		}

		return IApplication.EXIT_OK;
	}

	// END SUPRESS CATCH EXCEPTION

	private void runClient() throws EmfStoreException {
		System.out.println("Client starting...");

		// The workspace is the core controler to access
		// local and remote projects
		final Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();

		// A user session stores credentials for login
		// Creates a user session with the default credentials
		final Usersession usersession = new EMFStoreCommandWithResult<Usersession>() {
			@Override
			protected Usersession doRun() {
				Usersession session = EMFStoreClientUtil.createUsersession();
				try {
					session.logIn();
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
				return session;
			}
		}.run(false);

		// Retrieves a list of existing (and accessible) projects
		// on the sever and deletes them permanently (to have a
		// clean set-up)
		List<ProjectInfo> projectList;
		projectList = workspace.getRemoteProjectList(usersession);
		for (ProjectInfo projectInfo : projectList) {
			workspace.deleteRemoteProject(usersession, projectInfo.getProjectId(), true);
		}

		// Create a project, share it with the server
		final ProjectSpace project1 = new EMFStoreCommandWithResult<ProjectSpace>() {
			@Override
			protected ProjectSpace doRun() {
				ProjectSpace project1 = workspace.createLocalProject("projectNo1", "My project");
				try {
					project1.shareProject(usersession, new ConsoleProgressMonitor());
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
				return project1;
			}
		}.run(false);

		// Create some EObjects and add them to the project
		// (To the projects containment tree)
		final League league1 = BowlingFactory.eINSTANCE.createLeague();
		league1.setName("league");
		league1.getPlayers().add(createPlayer("no. 1"));
		league1.getPlayers().add(createPlayer("no. 2"));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project1.getProject().addModelElement(league1);
			}
		}.run(false);

		// commit the changes of the project to the EMFStore
		// including a commit message
		final LogMessage logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		logMessage.setMessage("My Message");

		final ProjectSpace project2 = new EMFStoreCommandWithResult<ProjectSpace>() {
			@Override
			protected ProjectSpace doRun() {
				try {
					project1.commit(logMessage, null, new ConsoleProgressMonitor());
					System.out.println("Project 1 committed!");

					// Check-out a second, independent copy of the project
					// (simulating a second client)
					return workspace.checkout(usersession, project1.getProjectInfo(), new NullProgressMonitor());
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		final League league2 = (League) project2.getProject().getModelElements().get(0);
		System.out.println("Project 2: League name is " + league2.getName());

		// Apply changes in the second copy of the project
		// and commit
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					league2.setName("league_changed");
					project2.commit(logMessage, null, new ConsoleProgressMonitor());
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		System.out.println("Project 2 committed!");

		// Update the first copy of the project
		project1.update();
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
		player.setEMail(String.format("%s@emfstore.org", name));
		return player;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}
}