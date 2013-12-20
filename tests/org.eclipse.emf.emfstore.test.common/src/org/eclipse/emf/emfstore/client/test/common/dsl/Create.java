/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.dsl;

import java.util.Date;
import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.bowling.TournamentType;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.TestSessionProvider2;
import org.eclipse.emf.emfstore.client.test.common.builders.BOOL.TRUE;
import org.eclipse.emf.emfstore.client.test.common.builders.UserBuilder;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidInputException;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.AncestorVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelFactory;

public final class Create {

	private Create() {
		// util class
	}

	// TODO: Move to Create class
	public static ESLocalProject project(final String projectName) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				return ESWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject(projectName);
			}
		});
	}

	public static ACOrgUnitId user(UserBuilder<TRUE, TRUE, TRUE> userBuilder) throws ESException {
		try {
			final AdminConnectionManager adminConnectionManager = ESWorkspaceProviderImpl.getInstance()
				.getAdminConnectionManager();
			final SessionId sessionId = TestSessionProvider2.getInstance().getDefaultUsersession().getSessionId();
			final ESServerImpl s = ESServerImpl.class.cast(userBuilder.getServer());
			adminConnectionManager.initConnection(s.toInternalAPI(), sessionId);

			final ACOrgUnitId orgUnitId = adminConnectionManager.createUser(sessionId, userBuilder.getUsername());

			// adminConnectionManager.initConnection(s.toInternalAPI(), sessionId);
			// adminConnectionManager.changeRole(sessionId, userBuilder.getProjectId(), orgUnitId,
			// userBuilder.getRole());
			adminConnectionManager.changeUser(sessionId, orgUnitId, userBuilder.getUsername(),
				userBuilder.getPassword());

			// SetupHelper.setUsersRole(orgUnitId, role, getProjectId());
			return orgUnitId;
		} catch (final InvalidInputException e) {
			throw new RuntimeException(e);
		}
	}

	public static PrimaryVersionSpec primaryVersionSpec(int identifier) {
		return primaryVersionSpec(identifier, "trunk");
	}

	public static PrimaryVersionSpec primaryVersionSpec(int identifier, String branch) {
		final PrimaryVersionSpec primaryVersionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		primaryVersionSpec.setIdentifier(identifier);
		primaryVersionSpec.setBranch(branch);
		return primaryVersionSpec;
	}

	public static BranchVersionSpec branchVersionSpec() {
		return branchVersionSpec("trunk");
	}

	public static BranchVersionSpec branchVersionSpec(String branch) {
		final BranchVersionSpec branchVersionSpec = VersioningFactory.eINSTANCE.createBranchVersionSpec();
		branchVersionSpec.setBranch(branch);
		return branchVersionSpec;
	}

	public static ProjectId projectId() {
		return ModelFactory.eINSTANCE.createProjectId();
	}

	public static LogMessage logMessage() {
		return VersioningFactory.eINSTANCE.createLogMessage();
	}

	public static TestElement testElement() {
		return TestmodelFactory.eINSTANCE.createTestElement();
	}

	public static Player player() {
		return BowlingFactory.eINSTANCE.createPlayer();
	}

	public static Player player(String playerName) {
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		player.setName(playerName);
		return player;
	}

	public static TestElement testElement(String name) {
		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		testElement.setName(name);
		return testElement;
	}

	public static PagedUpdateVersionSpec pagedUpdateVersionSpec(ESPrimaryVersionSpec baseVersion,
		int maxChanges) {
		final PagedUpdateVersionSpec pagedUpdateVersionSpec = VersioningFactory.eINSTANCE
			.createPagedUpdateVersionSpec();
		pagedUpdateVersionSpec.setBaseVersionSpec(
			Create.primaryVersionSpec(baseVersion.getIdentifier(), "trunk"));
		pagedUpdateVersionSpec.setBranch("trunk");
		return pagedUpdateVersionSpec;
	}

	public static Usersession session(ServerInfo server, String username, String password) {
		final Usersession usersession = org.eclipse.emf.emfstore.internal.client.model.ModelFactory.eINSTANCE
			.createUsersession();
		usersession.setServerInfo(server);
		usersession.setUsername(username);
		usersession.setPassword(password);

		return usersession;
	}

	public static TagVersionSpec tagVersionSpec(String branchName, String tagName) {
		final TagVersionSpec tagVersionSpec = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tagVersionSpec.setBranch(branchName);
		tagVersionSpec.setName(tagName);
		return tagVersionSpec;
	}

	public static AncestorVersionSpec ancestorVersionSpec(PrimaryVersionSpec source, PrimaryVersionSpec target) {
		// String targetBranch) {
		final AncestorVersionSpec ancestorVersionSpec = VersioningFactory.eINSTANCE.createAncestorVersionSpec();
		ancestorVersionSpec.setSource(source);
		ancestorVersionSpec.setTarget(target);
		// ancestorVersionSpec.setBranch(targetBranch);
		return ancestorVersionSpec;
	}

	public static DateVersionSpec dateVersionSpec(Date date) {
		final DateVersionSpec dateVersionSpec = VersioningFactory.eINSTANCE.createDateVersionSpec();
		dateVersionSpec.setDate(date);
		return dateVersionSpec;
	}

	public static League league(String name) {
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName(name);
		return league;
	}

	public static Game game(Player player) {
		final Game game = BowlingFactory.eINSTANCE.createGame();
		game.setPlayer(player);
		return game;
	}

	public static Tournament tournament(boolean professional) {
		final Tournament tournament = BowlingFactory.eINSTANCE.createTournament();
		if (professional) {
			tournament.setType(TournamentType.PRO);
		} else {
			tournament.setType(TournamentType.AMATEUR);
		}
		return tournament;
	}

	public static Matchup matchup(Game firstGame, Game secondGame) {
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();

		if (firstGame != null) {
			matchup.getGames().add(firstGame);
		}

		if (secondGame != null) {
			matchup.getGames().add(firstGame);
		}
		return matchup;
	}

}
