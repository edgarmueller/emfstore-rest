/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tobias Verhoeven - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.junit.Test;

/**
 * ModelElementTest.
 * 
 * @author Tobias Verhoeven
 */
public class ModelElementTest extends ESTest {

	/**
	 * Tests adding model elements.
	 */
	@Test
	public void testAddModelElementsWithCommands() {

		final League leagueA = Create.league(ProjectChangeUtil.LEAGUE_AMERICA_NAME);
		final League leagueB = Create.league(ProjectChangeUtil.LEAGUE_EUROPE_NAME);

		ProjectUtil.addElement(getLocalProject(), leagueA);
		ProjectUtil.addElement(getLocalProject(), leagueB);

		assertEquals(2, getLocalProject().getAllModelElements().size());

		final Player playerA = Create.player(
			ProjectChangeUtil.PLAYER_HANS_NAME);
		final Player playerB = Create.player(
			ProjectChangeUtil.PLAYER_ANTON_NAME);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leagueA.getPlayers().add(playerA);
				leagueA.getPlayers().add(playerB);
			}
		}.run(false);

		assertEquals(4, getLocalProject().getAllModelElements().size());

		final Player playerC = Create.player(
			ProjectChangeUtil.PLAYER_PAUL_NAME);
		final Player playerD = Create.player(
			ProjectChangeUtil.PLAYER_KLAUS_NAME);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leagueA.getPlayers().add(playerC);
				leagueA.getPlayers().add(playerD);
			}
		}.run(false);

		assertEquals(6, getLocalProject().getAllModelElements().size());
		assertEquals(2, getLocalProject().getModelElements().size());

		final Tournament tournamentA = Create.tournament(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournamentA);
			}
		}.run(false);

		final Matchup matchupA = Create.matchup(null, null);
		final Matchup matchupB = Create.matchup(null, null);

		final Game gameA = Create.game(playerA);
		final Game gameB = Create.game(playerB);
		final Game gameC = Create.game(playerC);
		final Game gameD = Create.game(playerD);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupA);
				matchupA.getGames().add(gameA);
				matchupA.getGames().add(gameB);
			}
		}.run(false);

		assertEquals(10, getLocalProject().getAllModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupB);
				matchupB.getGames().add(gameC);
				matchupB.getGames().add(gameD);
			}
		}.run(false);

		assertEquals(13, getLocalProject().getAllModelElements().size());
		assertEquals(3, getLocalProject().getModelElements().size());
	}

	/**
	 * adds and deletes model element and undos the deletion.
	 */
	@Test
	public void testDeleteUndoWithCommand() {
		final Player player = Create.player(
			ProjectChangeUtil.PLAYER_HEINRICH_NAME);
		final int size = getLocalProject().getAllModelElements().size();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(player);
			}
		}.run(false);

		assertTrue(getLocalProject().getAllModelElements().contains(player));
		assertTrue(getLocalProject().contains(player));
		final ESModelElementId id = getLocalProject().getModelElementId(player);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().remove(player);
			}
		}.run(false);

		assertEquals(size, getLocalProject().getAllModelElements().size());
		assertFalse(getLocalProject().getAllModelElements().contains(player));
		assertFalse(getLocalProject().contains(player));
		assertNull(getLocalProject().getModelElement(id));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().undoLastOperation();
			}
		}.run(false);

		assertEquals(size + 1, getLocalProject().getAllModelElements().size());
		assertFalse(getLocalProject().getAllModelElements().contains(player));
		assertFalse(getLocalProject().contains(player));
		assertNotNull(getLocalProject().getModelElement(id));
	}

	@Test
	public void testReferenceDeletionWithCommand() {
		final Tournament tournament = Create.tournament(true);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournament);
			}
		}.run(false);

		final Player player = Create.player(ProjectChangeUtil.PLAYER_HEINRICH_NAME);
		final Player player2 = Create.player(ProjectChangeUtil.PLAYER_WALTER_NAME);
		final Player player3 = Create.player(ProjectChangeUtil.PLAYER_WILHELM_NAME);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournament.getPlayers().add(player);
				tournament.getPlayers().add(player2);
				tournament.getPlayers().add(player3);
			}
		}.run(false);

		assertEquals(3, tournament.getPlayers().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().remove(player2);
			}
		}.run(false);

		assertEquals(2, tournament.getPlayers().size());
		assertTrue(getLocalProject().contains(player));
		assertTrue(getLocalProject().contains(player3));
		assertFalse(getLocalProject().contains(player2));
	}

	@Test
	public void testMultiReferenceRevertWithCommand() {
		final Tournament tournament = Create.tournament(true);
		final int numTrophies = 40;
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++) {
					tournament.getReceivesTrophy().add(false);
				}
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testMultiReferenceDeleteRevertWithCommand() {
		final Tournament tournament = Create.tournament(true);
		final int numTrophies = 40;
		final int numDeletes = 10;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++) {
					tournament.getReceivesTrophy().add(false);
				}
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numDeletes; i++) {
					tournament.getReceivesTrophy().remove(i);
				}
			}
		}.run(false);

		assertEquals(numTrophies - numDeletes, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testMultiReferenceRemoveRevertWithCommand() {
		final Tournament tournament = Create.tournament(true);
		final int numTrophies = 40;
		final int numDeletes = 10;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++) {
					tournament.getReceivesTrophy().add(false);
				}
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(tournament);
		for (int i = 0; i < numDeletes; i++) {
			domain.getCommandStack().execute(
				RemoveCommand.create(domain,
					tournament, BowlingPackage.eINSTANCE.getTournament_ReceivesTrophy(), Boolean.FALSE));
		}

		assertEquals(numTrophies - numDeletes, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testMultiReferenceMoveRevertWithCommand() {
		final Tournament tournament = Create.tournament(true);
		final int numTrophies = 40;
		final int numDeletes = 10;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++) {
					tournament.getReceivesTrophy().add(Boolean.FALSE);
				}
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(tournament);
		for (int i = 10; i < numDeletes; i++) {
			domain.getCommandStack().execute(
				MoveCommand.create(domain,
					tournament, BowlingPackage.eINSTANCE.getTournament_ReceivesTrophy(), Boolean.FALSE,
					i - 1));
		}

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testUndoAddOperation() {
		final Tournament tournamentA = Create.tournament(true);
		final Tournament tournamentB = Create.tournament(true);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournamentA);
				getLocalProject().getModelElements().add(tournamentB);
			}
		}.run(false);

		final Matchup matchupA = Create.matchup(null, null);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupA);
			}
		}.run(false);

		assertEquals(1, tournamentA.getMatchups().size());
		assertTrue(tournamentA.getMatchups().contains(matchupA));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentB.getMatchups().add(matchupA);
			}
		}.run(false);

		assertEquals(1, tournamentB.getMatchups().size());
		assertTrue(tournamentB.getMatchups().contains(matchupA));
		assertEquals(0, tournamentA.getMatchups().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().undoLastOperation();
			}
		}.run(false);

		assertEquals(0, tournamentB.getMatchups().size());

		assertEquals(1, tournamentA.getMatchups().size());
		assertTrue(tournamentA.getMatchups().contains(matchupA));
	}

	@Test
	public void testUndoMoveOperation() {

		final Tournament tournamentA = Create.tournament(true);
		final Tournament tournamentB = Create.tournament(true);
		final Matchup matchupA = Create.matchup(null, null);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournamentA);

			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupA);
			}
		}.run(false);

		final ESModelElementId matchupID = getLocalProject().getModelElementId(matchupA);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().getModelElements().add(tournamentB);
			}
		}.run(false);

		assertEquals(1, tournamentA.getMatchups().size());
		assertTrue(tournamentA.getMatchups().contains(matchupA));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().remove(matchupA);
			}
		}.run(false);

		assertEquals(2, getLocalProject().getModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentB.getMatchups().add(matchupA);
			}
		}.run(false);

		assertEquals(1, tournamentB.getMatchups().size());
		assertTrue(tournamentB.getMatchups().contains(matchupA));
		assertEquals(0, tournamentA.getMatchups().size());
		assertEquals(2, getLocalProject().getModelElements().size());

		// undos move from root to container
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().undoLastOperation();
			}
		}.run(false);

		assertEquals(0, tournamentB.getMatchups().size());
		assertEquals(0, tournamentA.getMatchups().size());
		assertEquals(3, getLocalProject().getModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().undoLastOperation();
			}
		}.run(false);

		assertEquals(0, tournamentA.getMatchups().size());
		assertEquals(2, getLocalProject().getModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getLocalProject().undoLastOperation();
			}
		}.run(false);

		assertEquals(1, tournamentA.getMatchups().size());
		assertEquals(2, getLocalProject().getModelElements().size());
		assertEquals(matchupID, getLocalProject().getModelElementId(tournamentA.getMatchups().get(0)));
	}
}
