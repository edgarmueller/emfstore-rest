/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tobias Verhoeven - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.api.test;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.RunESCommand;

/**
 * Utility class to easily create bowling model instances.
 * 
 * @author Tobias Verhoeven
 */
public final class ProjectChangeUtil {

	private static final String BASIC_BOWLING_PROJECT_NAME = "BasicBowlingProject"; //$NON-NLS-1$
	public static final String LEAGUE_EUROPE_NAME = "Europe"; //$NON-NLS-1$
	public static final String LEAGUE_AMERICA_NAME = "America"; //$NON-NLS-1$

	public static final String PLAYER_ANTON_NAME = "Anton"; //$NON-NLS-1$
	public static final String PLAYER_KLAUS_NAME = "Klaus"; //$NON-NLS-1$
	public static final String PLAYER_PAUL_NAME = "Paul"; //$NON-NLS-1$
	public static final String PLAYER_HANS_NAME = "Hans"; //$NON-NLS-1$
	public static final String PLAYER_WILHELM_NAME = "Wilhelm"; //$NON-NLS-1$
	public static final String PLAYER_WALTER_NAME = "Walter"; //$NON-NLS-1$
	public static final String PLAYER_HEINRICH_NAME = "Heinrich"; //$NON-NLS-1$
	public static final String PLAYER_DEFAULT_NAME = "player"; //$NON-NLS-1$

	private ProjectChangeUtil() {
	}

	public static ESLocalProject createBasicBowlingProject() {
		final ESLocalProject localProject = ESWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject(
			BASIC_BOWLING_PROJECT_NAME);

		final League leagueA = Create.league(LEAGUE_AMERICA_NAME);
		final League leagueB = Create.league(LEAGUE_EUROPE_NAME);
		final Player playerA = Create.player(PLAYER_HANS_NAME);
		final Player playerB = Create.player(PLAYER_ANTON_NAME);
		final Player playerC = Create.player(PLAYER_PAUL_NAME);
		final Player playerD = Create.player(PLAYER_KLAUS_NAME);
		final Tournament tournamentA = Create.tournament(false);
		final Game gameA = Create.game(playerA);
		final Game gameB = Create.game(playerB);
		final Game gameC = Create.game(playerC);
		final Game gameD = Create.game(playerD);
		final Matchup matchupA = Create.matchup(gameA, gameB);
		final Matchup matchupB = Create.matchup(gameC, gameD);

		leagueA.getPlayers().add(playerA);
		leagueA.getPlayers().add(playerB);
		leagueA.getPlayers().add(playerC);
		leagueA.getPlayers().add(playerD);

		tournamentA.getMatchups().add(matchupA);
		tournamentA.getMatchups().add(matchupB);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(leagueA);
				localProject.getModelElements().add(leagueB);
				localProject.getModelElements().add(tournamentA);
				return null;
			}
		});

		return localProject;
	}
}
