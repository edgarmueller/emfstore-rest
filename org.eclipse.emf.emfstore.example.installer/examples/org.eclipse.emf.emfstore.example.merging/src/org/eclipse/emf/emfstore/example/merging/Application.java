/**
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/

package org.eclipse.emf.emfstore.example.merging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.observers.ConflictResolver;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.BaseVersionOutdatedException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.startup.ConsoleProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * Merging example application.
 */
public class Application implements IApplication {
	
	private ProjectSpace project1;
	private League league1;
	private ProjectSpace project2;
	private League league2;
	private LogMessage logMessage;

	/**
	 * {@inheritDoc}
	 */
	// BEGIN SUPRESS CATCH EXCEPTION
	public Object start(IApplicationContext context) throws Exception {
		WorkspaceManager.init();

		// Run a client that shows the merging feature of the EMFstore
		// Please note: this is the programmatic way of merging
		// EMFStore also provides a default UI for merging
		// If there is a problem with the connection to the server
		// e.g. a network, a specific EMFStoreException will be thrown
		try {
			runClient();
		} catch (EmfStoreException e) {
			System.out.println("No connection to server.");
			System.out.println("Did you start the server? :-)");
			e.printStackTrace();

		}

		return IApplication.EXIT_OK;
	}
	// END SUPRESS CATCH EXCEPTION

	/**
	 * Runs the application.
	 * 
	 * @throws EmfStoreException
	 * 			in case any error occurs
	 */
	private void runClient() throws EmfStoreException {
		System.out.println("Client starting...");

		// Sets up the workspace by cleaning all contents
		// Create a project with some EObjects and two
		// identical copies. See hello world code example
		// for more details
		setupWorkspace();

		// Change the value of the league in project 1 and commit
		// the change
		league1.setName("New Name 1");
		league1.getPlayers().add(createPlayer("Player no. 4"));
		project1.commit(logMessage, null, new ConsoleProgressMonitor());

		// Changing the same value without calling
		// project2.update() will cause a conflict
		// also add one change which is non-conflicting
		league2.setName("New Name 2");
		league2.getPlayers().remove(0);

		try {
			project2.commit(logMessage, null, new ConsoleProgressMonitor());
		} catch (BaseVersionOutdatedException e) {

			System.out.println("\nCommit of project 2 failed.");

			// define a conflict resolver to handle the conflicting changes on
			// update
			// the resolver will accept the name change of the league of project
			// 2 to "New Name 2"
			// reject the name change of project 1 and accept all other changes
			final ConflictResolver conflictResolver = new ConflictResolver() {

				private ChangePackage myChangePackage;
				private List<ChangePackage> theirChangePackages;

				public boolean resolveConflicts(Project project, List<ChangePackage> theirChangePackages,
						ChangePackage myChangePackage, PrimaryVersionSpec baseVersion, PrimaryVersionSpec targetVersion) {

					this.theirChangePackages = theirChangePackages;
					this.myChangePackage = myChangePackage;
					// declare that resolver will be able to resolve all
					// conflicts
					return true;
				}

				public List<AbstractOperation> getRejectedTheirs() {
					// reject the first change in the change package of project
					// 1 from the server, since it is the name change of the
					// league to "New Name 1"
					return Arrays.asList(theirChangePackages.get(0)
							.getOperations().get(0));
				}

				public List<AbstractOperation> getAcceptedMine() {
					// accept all my operations in project 2, including the name
					// change to "New Name 2"
					return myChangePackage.getOperations();
				}
			};

			// create a callback object to drive the update and use our conflict
			// resolver
			UpdateCallback updateCallback = new UpdateCallback() {

				public void noChangesOnServer() {
					// do nothing if there are no changes on the server (we know
					// there are changes anyway)
				}

				public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes) {
					// accept incoming changes (we could cancel the update by
					// returning false here)
					return true;
				}

				public boolean conflictOccurred(
						ChangeConflictException changeConflictException) {

					// resolve conflicts by merging with our conflict resolver
					try {
						project2.merge(project2
								.resolveVersionSpec(VersionSpec.HEAD_VERSION),
								conflictResolver);
					} catch (EmfStoreException e) {
						// on any exceptions, declare conflicts as non-resolved
						return false;
					}

					// conflicts have been resolved
					return true;
				}
			};

			// run update in project 2 with our callback
			System.out.println("\nUpdate of project 2 with conflict resolver...");
			project2.update(VersionSpec.HEAD_VERSION, updateCallback,
					new ConsoleProgressMonitor());

			// commit merge result in project 2
			System.out.println("\nCommit of project 2 with merge result.");
			project2.commit(logMessage, null, new ConsoleProgressMonitor());
		}

		// update project 1
		System.out.println("\nUpdate of project 1 with merge result.");
		project1.update();

		System.out.println("\nLeague name in project 1 is now:"
				+ league1.getName());

		System.out.println("Client run completed.");

	}

	/**
	 * Creates a default workspace and deletes all remote projects.
	 * 
	 * @throws EmfStoreException
	 * 			in case any error occurs
	 */
	private void setupWorkspace() throws EmfStoreException {
		// The workspace is the core controler to access
		// local and remote projects
		Workspace workspace = WorkspaceManager.getInstance()
				.getCurrentWorkspace();

		// A user session stores credentials for login
		// Creates a user session with the default credentials
		Usersession usersession = EMFStoreClientUtil.createUsersession();
		usersession.logIn();

		// Retrieves a list of existing (and accessible) projects
		// on the sever and deletes them permanently (to have a
		// clean set-up)
		List<ProjectInfo> projectList;
		projectList = workspace.getRemoteProjectList(usersession);
		for (ProjectInfo projectInfo : projectList) {
			workspace.deleteRemoteProject(usersession,
					projectInfo.getProjectId(), true);
		}

		// Create a project, share it with the server
		project1 = workspace.createLocalProject("projectNo1", "My project");
		project1.shareProject(usersession, new ConsoleProgressMonitor());

		// Create some EObjects and add them to the project
		// (To the projects containment tree)
		league1 = BowlingFactory.eINSTANCE.createLeague();
		league1.setName("league");
		league1.getPlayers().add(createPlayer("no. 1"));
		league1.getPlayers().add(createPlayer("no. 2"));
		project1.getProject().addModelElement(league1);

		logMessage = VersioningFactory.eINSTANCE.createLogMessage();
		logMessage.setMessage("My Message");
		project1.commit(logMessage, null, new ConsoleProgressMonitor());
		System.out.println("Project 1 committed!");

		// Check-out a second, independent copy of the project
		// (simulating a second client)
		project2 = workspace.checkout(usersession, project1.getProjectInfo());
		league2 = (League) project2.getProject().getModelElements().get(0);
	}

	/**
	 * Handles the conflicting project2.
	 * 
	 * @param conflictingProject2 
	 * 			the conflicting project
	 */
	protected void handleConflict(ProjectSpace conflictingProject2) {
		try {
			// inspect all changes
			inspectChanges(conflictingProject2);

			// merge the project2 with the current version
			// and reject all changes from the server!
			// Only changes from project2 are accepted!
			conflictingProject2.merge(
					ModelUtil.clone(project1.getBaseVersion()),
					new ConflictResolver() {

						private ArrayList<AbstractOperation> acceptedMine;
						private ArrayList<AbstractOperation> rejectedTheirs;

						public boolean resolveConflicts(Project project,
								List<ChangePackage> theirChangePackages, ChangePackage myChangePackage,
								PrimaryVersionSpec baseVersion, PrimaryVersionSpec targetVersion) {

							// all local projects for project2 are accepted
							acceptedMine = new ArrayList<AbstractOperation>();
							acceptedMine.addAll(myChangePackage.getOperations());

							// reject all operations executed on project1
							rejectedTheirs = new ArrayList<AbstractOperation>();
							for (ChangePackage change : theirChangePackages) {
								rejectedTheirs.addAll(change.getOperations());
							}
							return true;
						}

						public List<AbstractOperation> getRejectedTheirs() {
							return rejectedTheirs;
						}

						public List<AbstractOperation> getAcceptedMine() {
							return acceptedMine;
						}
					});

		} catch (EmfStoreException e) {
			ModelUtil.logException(e);
		}
	}

	/**
	 * Inspects the changes occured.
	 * 
	 * @param conflictingProject
	 * @throws EmfStoreException
	 */
	private void inspectChanges(ProjectSpace conflictingProject)
			throws EmfStoreException {
		// access and list all changes occured
		List<ChangePackage> changes = conflictingProject.getChanges(
				conflictingProject.getBaseVersion(), project1.getBaseVersion());

		for (ChangePackage change : changes) {
			System.out.println(change.getLogMessage().toString());

			// use the elementId of the change to access the leagues of each
			// local project
			for (ModelElementId elementId : change
					.getAllInvolvedModelElements()) {
				EObject element = project1.getProject().getModelElement(
						elementId);
				if (element == null) {
					element = project2.getProject().getModelElement(elementId);
				}
				switch (element.eClass().getClassifierID()) {
				case BowlingPackage.PLAYER:
					Player playerOfProject1 = (Player) project1.getProject()
							.getModelElement(elementId);
					Player playerOfProject2 = (Player) project2.getProject()
							.getModelElement(elementId);
					if (playerOfProject1 != null) {
						System.out.println(String.format(
								"Player of project1. Name is %s",
								playerOfProject1.getName()));
					}
					if (playerOfProject2 != null) {
						System.out.println(String.format(
								"Player of project2. Name is %s",
								playerOfProject2.getName()));
					}
					break;
				case BowlingPackage.LEAGUE:
					League leagueOfProject1 = (League) project1.getProject()
							.getModelElement(elementId);
					League leagueOfProject2 = (League) project2.getProject()
							.getModelElement(elementId);
					System.out.println(String.format(
							"League of project1. Name is %s",
							leagueOfProject1.getName()));
					System.out.println(String.format(
							"League of project2. Name is %s",
							leagueOfProject2.getName()));
					break;
				default:
						break;
				}
			}
		}
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
	
}