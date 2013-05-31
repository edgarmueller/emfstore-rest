/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * TobiasVerhoeven
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
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
	public void testAddModelElementsWithCommands() {
		final ESLocalProject localProject = ESWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject(
			"Test project");

		final League leagueA = ProjectChangeUtil.createLeague("America");
		final League leagueB = ProjectChangeUtil.createLeague("Europe");

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(leagueA);
				localProject.getModelElements().add(leagueB);
				return null;
			}
		});

		assertEquals(2, localProject.getAllModelElements().size());

		final Player playerA = ProjectChangeUtil.createPlayer("Hans");
		final Player playerB = ProjectChangeUtil.createPlayer("Anton");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leagueA.getPlayers().add(playerA);
				leagueA.getPlayers().add(playerB);
			}
		}.run(false);

		assertEquals(4, localProject.getAllModelElements().size());

		final Player playerC = ProjectChangeUtil.createPlayer("Paul");
		final Player playerD = ProjectChangeUtil.createPlayer("Klaus");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				leagueA.getPlayers().add(playerC);
				leagueA.getPlayers().add(playerD);
			}
		}.run(false);

		assertEquals(6, localProject.getAllModelElements().size());
		assertEquals(2, localProject.getModelElements().size());

		final Tournament tournamentA = ProjectChangeUtil.createTournament(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournamentA);
			}
		}.run(false);

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
		}.run(false);

		assertEquals(10, localProject.getAllModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupB);
				matchupB.getGames().add(gameC);
				matchupB.getGames().add(gameD);
			}
		}.run(false);

		assertEquals(13, localProject.getAllModelElements().size());
		assertEquals(3, localProject.getModelElements().size());
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testDeleteAllModelElementsWithCommand() {

		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				Set<EObject> elements = localProject.getAllModelElements();
				for (EObject object : elements) {
					localProject.getModelElements().remove(object);
				}
			}
		}.run(false);

		// assertEquals(0, loca lProject.getAllModelElements().size());
	}

	/**
	 * adds and deletes model element and undos the deletion.
	 */
	@Test
	public void testDeleteUndoWithCommand() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Player player = ProjectChangeUtil.createPlayer("Heinrich");
		final int SIZE = localProject.getAllModelElements().size();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(player);
			}
		}.run(false);

		assertTrue(localProject.getAllModelElements().contains(player));
		assertTrue(localProject.contains(player));
		ESModelElementId id = localProject.getModelElementId(player);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().remove(player);
			}
		}.run(false);

		assertEquals(SIZE, localProject.getAllModelElements().size());
		assertFalse(localProject.getAllModelElements().contains(player));
		assertFalse(localProject.contains(player));
		assertNull(localProject.getModelElement(id));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.undoLastOperation();
			}
		}.run(false);

		assertEquals(SIZE + 1, localProject.getAllModelElements().size());
		assertFalse(localProject.getAllModelElements().contains(player));
		assertFalse(localProject.contains(player));
		assertNotNull(localProject.getModelElement(id));
	}

	@Test
	public void testReferenceDeletionWithCommand() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Tournament tournament = ProjectChangeUtil.createTournament(true);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournament);
			}
		}.run(false);

		final Player player = ProjectChangeUtil.createPlayer("Heinrich");
		final Player player2 = ProjectChangeUtil.createPlayer("Walter");
		final Player player3 = ProjectChangeUtil.createPlayer("Wilhelm");

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
				localProject.getModelElements().remove(player2);
			}
		}.run(false);

		assertEquals(2, tournament.getPlayers().size());
		assertTrue(localProject.contains(player));
		assertTrue(localProject.contains(player3));
		assertFalse(localProject.contains(player2));
	}

	@Test
	public void testMultiReferenceRevertWithCommand() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Tournament tournament = ProjectChangeUtil.createTournament(true);
		final int numTrophies = 40;
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++)
					tournament.getReceivesTrophy().add(false);
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testMultiReferenceDeleteRevertWithCommand() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Tournament tournament = ProjectChangeUtil.createTournament(true);
		final int numTrophies = 40;
		final int numDeletes = 10;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++)
					tournament.getReceivesTrophy().add(false);
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numDeletes; i++)
					tournament.getReceivesTrophy().remove(i);
			}
		}.run(false);

		assertEquals(numTrophies - numDeletes, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testMultiReferenceRemoveRevertWithCommand() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Tournament tournament = ProjectChangeUtil.createTournament(true);
		final int numTrophies = 40;
		final int numDeletes = 10;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++)
					tournament.getReceivesTrophy().add(false);
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(tournament);
		for (int i = 0; i < numDeletes; i++)

			domain.getCommandStack().execute(
				RemoveCommand.create(domain,
					tournament, BowlingPackage.eINSTANCE.getTournament_ReceivesTrophy(), Boolean.FALSE));

		assertEquals(numTrophies - numDeletes, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testMultiReferenceMoveRevertWithCommand() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Tournament tournament = ProjectChangeUtil.createTournament(true);
		final int numTrophies = 40;
		final int numDeletes = 10;

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournament);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < numTrophies; i++)
					tournament.getReceivesTrophy().add(Boolean.FALSE);
			}
		}.run(false);

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(tournament);
		for (int i = 10; i < numDeletes; i++)

			domain.getCommandStack().execute(
				MoveCommand.create(domain,
					tournament, BowlingPackage.eINSTANCE.getTournament_ReceivesTrophy(), Boolean.FALSE,
					i - 1));

		assertEquals(numTrophies, tournament.getReceivesTrophy().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.revert();
			}
		}.run(false);

		assertEquals(0, tournament.getReceivesTrophy().size());
	}

	@Test
	public void testUndoAddOperation() {
		final ESLocalProject localProject = ProjectChangeUtil.createBasicBowlingProject();
		final Tournament tournamentA = ProjectChangeUtil.createTournament(true);
		final Tournament tournamentB = ProjectChangeUtil.createTournament(true);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournamentA);
				localProject.getModelElements().add(tournamentB);
			}
		}.run(false);

		final Matchup matchupA = ProjectChangeUtil.createMatchup(null, null);

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
				localProject.undoLastOperation();
			}
		}.run(false);

		assertEquals(0, tournamentB.getMatchups().size());

		assertEquals(1, tournamentA.getMatchups().size());
		assertTrue(tournamentA.getMatchups().contains(matchupA));
	}

	@Test
	public void testUndoMoveOperation() {
		final ESLocalProject localProject = ESWorkspaceProvider.INSTANCE.getWorkspace().createLocalProject(
			"SimpleEmptyProject");

		final Tournament tournamentA = ProjectChangeUtil.createTournament(true);
		final Tournament tournamentB = ProjectChangeUtil.createTournament(true);
		final Matchup matchupA = ProjectChangeUtil.createMatchup(null, null);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournamentA);

			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentA.getMatchups().add(matchupA);
			}
		}.run(false);

		ESModelElementId matchupID = localProject.getModelElementId(matchupA);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.getModelElements().add(tournamentB);
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

		assertEquals(2, localProject.getModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournamentB.getMatchups().add(matchupA);
			}
		}.run(false);

		assertEquals(1, tournamentB.getMatchups().size());
		assertTrue(tournamentB.getMatchups().contains(matchupA));
		assertEquals(0, tournamentA.getMatchups().size());
		assertEquals(2, localProject.getModelElements().size());

		// undos move from root to container
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.undoLastOperation();
			}
		}.run(false);

		assertEquals(0, tournamentB.getMatchups().size());
		assertEquals(0, tournamentA.getMatchups().size());
		assertEquals(3, localProject.getModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.undoLastOperation();
			}
		}.run(false);

		assertEquals(0, tournamentA.getMatchups().size());
		assertEquals(2, localProject.getModelElements().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				localProject.undoLastOperation();
			}
		}.run(false);

		assertEquals(1, tournamentA.getMatchups().size());
		assertEquals(2, localProject.getModelElements().size());
		assertEquals(matchupID, localProject.getModelElementId(tournamentA.getMatchups().get(0)));
	}
}
