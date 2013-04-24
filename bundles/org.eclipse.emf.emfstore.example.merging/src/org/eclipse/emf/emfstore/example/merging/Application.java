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
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.AbstractConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.controller.ChangeConflict;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESChangeConflictImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.common.ConsoleProgressMonitor;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
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

	private ESLocalProject project1;
	private League league1;
	private ESLocalProject project2;
	private League league2;
	private ESLogMessage logMessage;

	/**
	 * {@inheritDoc}
	 */
	// BEGIN SUPRESS CATCH EXCEPTION
	public Object start(IApplicationContext context) throws Exception {
		// END SUPRESS CATCH EXCEPTION

		// Run a client that shows the merging feature of the EMFstore
		// Please note: this is the programmatic way of merging
		// EMFStore also provides a default UI for merging
		// If there is a problem with the connection to the server
		// e.g. a network, a specific ESException will be thrown
		try {
			runClient();
		} catch (ESException e) {
			System.out.println("No connection to server.");
			System.out.println("Did you start the server? :-)");
			e.printStackTrace();

		}

		return IApplication.EXIT_OK;
	}

	private void runClient() throws ESException {
		System.out.println("Client starting...");
		// Sets up the workspace by cleaning all contents
		// Create a project with some EObjects and two
		// identical copies. See hello world code example
		// for more details
		setupWorkspace();

		// Change the name of the league in project 1,add a new player and commit the change
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				league1.setName("New Name 1");
				league1.getPlayers().add(createPlayer("Player no. 4"));
			}
		}.run(false);
		new EMFStoreCommand() {
			@SuppressWarnings("restriction")
			@Override
			protected void doRun() {
				try {
					project1.commit(logMessage, null, new ConsoleProgressMonitor());
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// Changing the name again value without calling
		// project2.update() will cause a conflict on commit.
		// also add one change which is non-conflicting
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				league2.setName("New Name 2");
				league2.getPlayers().remove(0);
			}
		}.run(false);

		new EMFStoreCommand() {
			@SuppressWarnings("restriction")
			@Override
			protected void doRun() {
				try {
					project2.commit(logMessage, null, new ConsoleProgressMonitor());
				} catch (BaseVersionOutdatedException e) {
					System.out.println("\nCommit of project 2 failed.");

					// run update in project 2 with our own updateCallback.
					System.out.println("\nUpdate of project 2 with conflict resolver...");
					try {
						project2.update(Versions.createHEAD().toAPI(), new MyUpdateCallback(),
							new ConsoleProgressMonitor());
						// commit merge result in project 2
						System.out.println("\nCommit of project 2 with merge result.");
						project2.commit(logMessage, null, new ConsoleProgressMonitor());
					} catch (ESException e1) {
						throw new RuntimeException(e);
					}
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// After having merged the two projects update local project 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					System.out.println("\nUpdate of project 1 with merge result.");
					project1.update(new NullProgressMonitor());
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		System.out.println("\nLeague name in project 1 is now:" + league1.getName());
		System.out.println("Client run completed.");
	}

	/**
	 * Creates a default workspace and deletes all remote projects.
	 * 
	 * @throws ESException
	 */
	@SuppressWarnings("restriction")
	private void setupWorkspace() throws ESException {
		// The workspace is the core controler to access
		// local and remote projects
		final ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();

		// A user session stores credentials for login
		// Creates a user session with the default credentials
		final ESUsersession usersession = new EMFStoreCommandWithResult<ESUsersession>() {
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

		// Create a project, share it with the server
		project1 = new EMFStoreCommandWithResult<ESLocalProject>() {
			@Override
			protected ESLocalProject doRun() {
				ESLocalProject project1 = workspace.createLocalProject("projectNo1");
				try {
					project1.shareProject(usersession, new ConsoleProgressMonitor());
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
				return project1;
			}
		}.run(false);

		// Create some EObjects and add them to the project
		// (To the projects containment tree)
		league1 = BowlingFactory.eINSTANCE.createLeague();
		league1.setName("league");
		league1.getPlayers().add(createPlayer("no. 1"));
		league1.getPlayers().add(createPlayer("no. 2"));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project1.getModelElements().add(league1);
			}
		}.run(false);

		logMessage = new EMFStoreCommandWithResult<ESLogMessage>() {
			@Override
			protected ESLogMessage doRun() {
				try {
					LogMessage result = VersioningFactory.eINSTANCE.createLogMessage();
					result.setMessage("My Message");
					project1.commit(result.toAPI(), null, new ConsoleProgressMonitor());
					System.out.println("Project 1 committed!");
					return result.toAPI();
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// Check-out a second, independent copy of the project
		// (simulating a second client)
		project2 = new EMFStoreCommandWithResult<ESLocalProject>() {
			@Override
			protected ESLocalProject doRun() {
				try {
					return project1.getRemoteProject().checkout(
						usersession, new NullProgressMonitor(), "My checkout");
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		league2 = (League) project2.getModelElements().get(0);
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

	/**
	 * An UpdateCallback to drive the update and use our own conflict resolver (MyConflictResolver).
	 */
	private class MyUpdateCallback implements ESUpdateCallback {
		public void noChangesOnServer() {
			// do nothing if there are no changes on the server (in this example we know
			// there are changes anyway)
		}

		public boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changes,
			ESModelElementIdToEObjectMapping idToEObjectMapping) {
			return true;
		}

		@SuppressWarnings("restriction")
		public boolean conflictOccurred(ESChangeConflict changeConflict, IProgressMonitor monitor) {
			// resolve conflicts by merging with our conflict resolver
			try {
				// Merging currently is only available on the internal API
				ProjectSpace projectSpace = ((ESLocalProjectImpl) project2).toInternalAPI();
				ChangeConflict cc = ((ESChangeConflictImpl) changeConflict).toInternalAPI();
				
				projectSpace.merge(
					projectSpace.resolveVersionSpec(Versions.createHEAD(), monitor),
					cc,
					new MyConflictResolver(false), 
					this, 
					monitor);
			} catch (ESException e) {
				// on any exceptions, declare conflicts as non-resolved
				return false;
			}
			// conflicts have been resolved
			return true;
		}

		public boolean checksumCheckFailed(ESLocalProject project, ESPrimaryVersionSpec versionSpec,
			IProgressMonitor monitor) throws ESException {
			return true;
		}
	};

	/**
	 * The MyConflictResolver will accept the name change of the league of project 2 to "New Name 2"
	 * reject the name change of project 1 and accept all other changes.
	 */
	@SuppressWarnings("restriction")
	private class MyConflictResolver extends AbstractConflictResolver {
		/**
		 * Instantiates a new MyConflictResolver.
		 * 
		 * @param isBranchMerge the is branch merge
		 */
		public MyConflictResolver(boolean isBranchMerge) {
			super(isBranchMerge);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.emf.emfstore.client.model.changeTracking.merging.AbstractConflictResolver#controlDecisionManager
		 * (org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager)
		 */
		@Override
		protected boolean controlDecisionManager(DecisionManager decisionManager) {
			return true;
		}

		private ChangePackage myChangePackage;
		private List<ChangePackage> theirChangePackages;

		@Override
		public boolean resolveConflicts(Project project, ChangeConflict changeConflict, PrimaryVersionSpec base,
			PrimaryVersionSpec target) {
			this.theirChangePackages = changeConflict.getNewPackages();
			this.myChangePackage = changeConflict.getMyChangePackages().get(0);
			// declare that resolver will be able to resolve all conflicts
			return true;
		}

		@Override
		public List<AbstractOperation> getRejectedTheirs() {
			// reject the first change in the change package of project
			// 1 from the server, since it is the name change of the
			// league to "New Name 1"
			return Arrays.asList(theirChangePackages.get(0).getOperations().get(0));
		}

		@Override
		public List<AbstractOperation> getAcceptedMine() {
			// accept all my operations in project 2, including the name change to "New Name 2"
			return myChangePackage.getOperations();
		}
	}
}