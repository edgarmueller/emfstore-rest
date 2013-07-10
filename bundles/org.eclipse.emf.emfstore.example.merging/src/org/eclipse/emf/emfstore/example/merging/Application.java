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

import java.util.Arrays;
import java.util.Collections;
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
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.AbstractConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.internal.server.impl.api.ESConflictSetImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.ESConflict;
import org.eclipse.emf.emfstore.server.ESConflictSet;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
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

	private void runClient(ESServer server) throws ESException {
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
			System.out.println("\nCommit of demoProjectCopy failed.");

			// run update in demoProjectCopy with an UpdateCallback to handle conflicts
			System.out.println("\nUpdate of demoProjectCopy with conflict resolver...");
			demoProjectCopy.update(ESVersionSpec.FACTORY.createHEAD(), new MyUpdateCallback(),
				new ESSystemOutProgressMonitor());
			// commit merge result in project 2
			System.out.println("\nCommit of project 2 with merge result.");
			demoProjectCopy.commit(new ESSystemOutProgressMonitor());

			// After having merged the two projects update local project 1
			System.out.println("\nUpdate of project 1 with merge result.");
			demoProject.update(new NullProgressMonitor());

			System.out.println("\nLeague name in project 1 is now:" + league.getName());
			System.out.println("Client run completed.");
		}
	}

	public void stop() {
		// TODO Auto-generated method stub

	}
}

/**
 * An UpdateCallback to drive the update and use our own conflict resolver (MyConflictResolver).
 */
class MyUpdateCallback implements ESUpdateCallback {
	public void noChangesOnServer() {
		// do nothing if there are no changes on the server (in this example we know
		// there are changes anyway)
	}

	public boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changes,
		ESModelElementIdToEObjectMapping idToEObjectMapping) {
		// allow update to proceed
		return true;
	}

	public boolean conflictOccurred(ESConflictSet changeConflictSet, IProgressMonitor monitor) {

		ESConflict conflict = changeConflictSet.getConflicts().iterator().next();

		// resolve the conflict by accepting all of the conflicting local operations and rejecting all of the remote
		// operations. This means that we revert all changes of the server that conflict with changes on the client.
		conflict.resolveConflict(conflict.getLocalOperations(), conflict.getRemoteOperations());
		return true;
	}
};

