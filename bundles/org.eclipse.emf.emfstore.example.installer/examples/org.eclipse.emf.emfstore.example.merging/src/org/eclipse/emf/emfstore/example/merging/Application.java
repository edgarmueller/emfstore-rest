/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.example.merging;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.common.ESSystemOutProgressMonitor;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.ESConflict;
import org.eclipse.emf.emfstore.server.ESConflictSet;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
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
public class Application implements IApplication {

	/**
	 * {@inheritDoc}
	 */
	public Object start(IApplicationContext context) {

		try {
			// Create a client representation for a local server and start a local server.
			ESServer localServer = ESServer.FACTORY.createAndStartLocalServer();

			// Reuse the client from the hello world example. It will clean up all local and remote projects and create
			// one project with some content on the server and two checked-out copies of the project on the client.
			org.eclipse.emf.emfstore.example.helloworld.Application.runClient(localServer);

			// We run our own client code to demonstrate merging now.
			runClient(localServer);

		} catch (ESServerStartFailedException e) {
			System.out.println("Server start failed!");
			e.printStackTrace();
		} catch (ESException e) {
			// If there is a problem with the connection to the server
			// e.g. a network, a specific EMFStoreException will be thrown
			System.out.println("Connection to Server failed!");
			e.printStackTrace();
		}
		return IApplication.EXIT_OK;
	}

	public static void runClient(ESServer server) throws ESException {
		System.out.println("Client starting...");

		ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();
		ESLocalProject demoProject = workspace.getLocalProjects().get(0);
		League league = (League) demoProject.getModelElements().get(0);
		final ESLocalProject demoProjectCopy = workspace.getLocalProjects().get(1);
		League leagueCopy = (League) demoProjectCopy.getModelElements().get(0);

		// Change the name of the league in project 1,add a new player and commit the change
		league.setName("Euro-League");
		Player newPlayer = BowlingFactory.eINSTANCE.createPlayer();
		newPlayer.setName("Eugene");
		league.getPlayers().add(newPlayer);

		demoProject.commit(new ESSystemOutProgressMonitor());

		// Changing the name again value without calling update() on the copy first will cause a conflict on commit.
		// We also add one change which is non-conflicting, setting the name of the first player.
		leagueCopy.setName("EU-League");
		leagueCopy.getPlayers().get(0).setName("Johannes");

		try {
			demoProjectCopy.commit(new ESSystemOutProgressMonitor());
		} catch (ESUpdateRequiredException e) {
			// The commit failed since the other demoProject was committed first and therefore demoProjectCopy needs an
			// update
			System.out.println("\nCommit of demoProjectCopy failed.");

			// We run update in demoProjectCopy with an UpdateCallback to handle conflicts
			System.out.println("\nUpdate of demoProjectCopy with conflict resolver...");
			demoProjectCopy.update(ESVersionSpec.FACTORY.createHEAD(), new ESUpdateCallback() {
				public void noChangesOnServer() {
					// do nothing if there are no changes on the server (in this example we know
					// there are changes anyway)
				}

				public boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changes,
					ESModelElementIdToEObjectMapping idToEObjectMapping) {
					// allow update to proceed, here we could also add some UI
					return true;
				}

				public boolean conflictOccurred(ESConflictSet changeConflictSet, IProgressMonitor monitor) {

					// One or more conflicts have occured, they are delivered in a change conflict set
					// We know there is only one conflict so we grab it
					ESConflict conflict = changeConflictSet.getConflicts().iterator().next();

					// We resolve the conflict by accepting all of the conflicting local operations and rejecting all of
					// the remote
					// operations. This means that we revert the league name change of demoProject and accept the league
					// name change of demoProjectCopy. The player name change in demoProject is accepted also since it
					// was not conflicting with any other change.
					conflict.resolveConflict(conflict.getLocalOperations(), conflict.getRemoteOperations());
					// Finally we claim to have resolved all conflicts so update will try to proceed.
					return true;
				}
			}, new ESSystemOutProgressMonitor());

			// commit merge result in project 2
			System.out.println("\nCommit of merge result of demoProjectCopy");
			demoProjectCopy.commit(new ESSystemOutProgressMonitor());

			// After having merged the two projects update local project 1
			System.out.println("\nUpdate of demoProject");
			demoProject.update(new NullProgressMonitor());

			// Finally we print the league and player names of both projects
			System.out.println("\nLeague name in demoProject  is now: " + league.getName());
			System.out.println("\nLeague name in demoProjectCopy  is now: " + leagueCopy.getName());
			System.out.println("\nPlayer name in demoProject is now: " + league.getPlayers().get(0).getName());
			System.out.println("\nPlayer name in demoProjectCopy is now: " + leagueCopy.getPlayers().get(0).getName());

		}
	}

	public void stop() {
		// do nothing
	}
}
