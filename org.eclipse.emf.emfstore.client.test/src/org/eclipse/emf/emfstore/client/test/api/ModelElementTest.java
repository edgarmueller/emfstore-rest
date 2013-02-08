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
package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.internal.client.api.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.api.IWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.junit.Test;

/**
 * ModelElementTest.
 * 
 * @author Tobias Verhoeven
 */
public class ModelElementTest {

	/**
	 * Tests adding model elements.
	 */
	@Test
	public void testAddModelElementsWithoutCommands() {
		ILocalProject localProject = IWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject(
			"Testprojekt", "");

		League leagueA = ProjectChangeUtil.createLeague("America");
		League leagueB = ProjectChangeUtil.createLeague("Europe");

		localProject.getModelElements().add(leagueA);
		localProject.getModelElements().add(leagueB);

		assertEquals(2, localProject.getAllModelElements().size());

		Player playerA = ProjectChangeUtil.createPlayer("Hans");
		Player playerB = ProjectChangeUtil.createPlayer("Anton");

		leagueA.getPlayers().add(playerA);
		leagueA.getPlayers().add(playerB);

		assertEquals(4, localProject.getAllModelElements().size());

		Player playerC = ProjectChangeUtil.createPlayer("Paul");
		Player playerD = ProjectChangeUtil.createPlayer("Klaus");

		leagueA.getPlayers().add(playerC);
		leagueA.getPlayers().add(playerD);

		assertEquals(6, localProject.getAllModelElements().size());
		assertEquals(2, localProject.getModelElements().size());

		Tournament tournamentA = ProjectChangeUtil.createTournament(false);
		localProject.getModelElements().add(tournamentA);

		Matchup matchupA = ProjectChangeUtil.createMatchup(null, null);
		Matchup matchupB = ProjectChangeUtil.createMatchup(null, null);

		Game gameA = ProjectChangeUtil.createGame(playerA);
		Game gameB = ProjectChangeUtil.createGame(playerB);
		Game gameC = ProjectChangeUtil.createGame(playerC);
		Game gameD = ProjectChangeUtil.createGame(playerD);

		tournamentA.getMatchups().add(matchupA);
		matchupA.getGames().add(gameA);
		matchupA.getGames().add(gameB);

		assertEquals(10, localProject.getAllModelElements().size());

		tournamentA.getMatchups().add(matchupB);
		matchupB.getGames().add(gameC);
		matchupB.getGames().add(gameD);

		assertEquals(13, localProject.getAllModelElements().size());
		assertEquals(3, localProject.getModelElements().size());
	}

	/**
	 * Tests adding model elements.
	 */
	@Test
	public void testAddModelElementsWithCommands() {
		final ILocalProject localProject = IWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject(
			"Testprojekt", "");

		final League leagueA = ProjectChangeUtil.createLeague("America");
		final League leagueB = ProjectChangeUtil.createLeague("Europe");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(leagueA);
				localProject.getModelElements().add(leagueB);
			}
		}.execute();

		assertEquals(2, localProject.getAllModelElements().size());

		final Player playerA = ProjectChangeUtil.createPlayer("Hans");
		final Player playerB = ProjectChangeUtil.createPlayer("Anton");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leagueA.getPlayers().add(playerA);
				leagueA.getPlayers().add(playerB);
			}
		}.execute();

		assertEquals(4, localProject.getAllModelElements().size());

		final Player playerC = ProjectChangeUtil.createPlayer("Paul");
		final Player playerD = ProjectChangeUtil.createPlayer("Klaus");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leagueA.getPlayers().add(playerC);
				leagueA.getPlayers().add(playerD);
			}
		}.execute();

		assertEquals(6, localProject.getAllModelElements().size());
		assertEquals(2, localProject.getModelElements().size());

		final Tournament tournamentA = ProjectChangeUtil.createTournament(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournamentA);
			}
		}.execute();

		final Matchup matchupA = ProjectChangeUtil.createMatchup(null, null);
		final Matchup matchupB = ProjectChangeUtil.createMatchup(null, null);

		final Game gameA = ProjectChangeUtil.createGame(playerA);
		final Game gameB = ProjectChangeUtil.createGame(playerB);
		final Game gameC = ProjectChangeUtil.createGame(playerC);
		final Game gameD = ProjectChangeUtil.createGame(playerD);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupA);
				matchupA.getGames().add(gameA);
				matchupA.getGames().add(gameB);
			}
		}.execute();

		assertEquals(10, localProject.getAllModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupB);
				matchupB.getGames().add(gameC);
				matchupB.getGames().add(gameD);
			}
		}.execute();

		assertEquals(13, localProject.getAllModelElements().size());
		assertEquals(3, localProject.getModelElements().size());
	}

	/**
	 * Test deletion of all model elements.
	 */
	@Test
	public void testDeleteAllModelElementsWithCommand() {
		final ILocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				Set<EObject> elements = localProject.getAllModelElements();
				for (EObject object : elements) {
					localProject.getModelElements().remove(object);
				}
			}
		}.execute();

		assertEquals(0, localProject.getAllModelElements());
	}

	/**
	 * Test deletion of all model elements.
	 */
	@Test
	public void testDeleteAllModelElementsWithoutCommand() {
		final ILocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();

		Set<EObject> elements = localProject.getAllModelElements();
		for (EObject object : elements) {
			localProject.getModelElements().remove(object);
		}

		assertEquals(0, localProject.getAllModelElements());
	}
}
