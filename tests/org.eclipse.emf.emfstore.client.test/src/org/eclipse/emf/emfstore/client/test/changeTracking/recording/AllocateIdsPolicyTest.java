/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.recording;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.junit.Test;

/**
 * Test the allocate ids policy of the {@link org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection}.
 * 
 * @author jsommerfeldt
 * 
 */
public class AllocateIdsPolicyTest extends WorkspaceTest {

	/**
	 * Test using the always id allocation policy.
	 */
	@Test
	public void alwaysIdAllocation() {
		EqualComparator ecomp = new EqualComparator();
		removeAddWithCommand(true, ecomp);
		removeAddWithCommands(true, ecomp);
		removeAddWithoutCommand(true, ecomp);
	}

	/**
	 * Test using the command id policy.
	 */
	@Test
	public void commandIdAllocation() {
		removeAddWithCommand(false, new EqualComparator());
		removeAddWithCommands(false, new NotEqualComparator());
		removeAddWithoutCommand(false, new NotEqualComparator());
	}

	/**
	 * Remove and add objects wihtin one command.
	 * 
	 * @param alwaysIdAllocation the allocate id policy.
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithCommand(boolean alwaysIdAllocation, IdComparator comparator) {
		final Project project = getProject();
		project.setAlwaysIdAllocation(alwaysIdAllocation);

		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		Game game = BowlingFactory.eINSTANCE.createGame();
		project.addModelElement(matchup);
		ModelElementId matchupId1 = project.getModelElementId(matchup);
		matchup.getGames().add(game);
		ModelElementId gameId1 = project.getModelElementId(game);

		// remove and add matchup in different commands
		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				project.deleteModelElement(matchup);
				project.addModelElement(matchup);
				return null;
			}
		});

		comparator.compare(matchupId1, project.getModelElementId(matchup));
		comparator.compare(gameId1, project.getModelElementId(game));
	}

	/**
	 * Remove and add objects within several commands.
	 * 
	 * @param alwaysIdAllocation the allocate id policy.
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithCommands(boolean alwaysIdAllocation, IdComparator comparator) {

		final Project project = getProject();
		project.setAlwaysIdAllocation(alwaysIdAllocation);

		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		Game game = BowlingFactory.eINSTANCE.createGame();
		project.addModelElement(matchup);
		ModelElementId matchupId1 = project.getModelElementId(matchup);
		matchup.getGames().add(game);
		ModelElementId gameId1 = project.getModelElementId(game);

		// remove and add matchup in different commands
		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				project.deleteModelElement(matchup);
				return null;
			}
		});
		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				project.addModelElement(matchup);
				return null;
			}
		});

		comparator.compare(matchupId1, project.getModelElementId(matchup));
		comparator.compare(gameId1, project.getModelElementId(game));
	}

	/**
	 * Remove and add objects without commands.
	 * 
	 * @param alwaysIdAllocation the allocate id policy.
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithoutCommand(boolean alwaysIdAllocation, IdComparator comparator) {
		Project project = getProject();
		project.setAlwaysIdAllocation(alwaysIdAllocation);
		Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		Game game = BowlingFactory.eINSTANCE.createGame();
		project.addModelElement(matchup);
		ModelElementId matchupId1 = project.getModelElementId(matchup);
		matchup.getGames().add(game);
		ModelElementId gameId1 = project.getModelElementId(game);

		// remove and add matchup without command
		project.deleteModelElement(matchup);
		project.addModelElement(matchup);

		comparator.compare(matchupId1, project.getModelElementId(matchup));
		comparator.compare(gameId1, project.getModelElementId(game));
	}

	/**
	 * Internal interface for comparation.
	 * 
	 * @author jsommerfeldt
	 * 
	 */
	private interface IdComparator {
		void compare(ModelElementId id1, ModelElementId id2);
	}

	/**
	 * Internal equal {@link IdComparator}.
	 * 
	 * @author jsommerfeldt
	 * 
	 */
	private static class EqualComparator implements IdComparator {
		public void compare(ModelElementId id1, ModelElementId id2) {
			assertEquals(id1.getId(), id2.getId());
		}
	}

	/**
	 * Internal not equal {@link IdComparator}.
	 * 
	 * @author jsommerfeldt
	 * 
	 */
	private static class NotEqualComparator implements IdComparator {
		public void compare(ModelElementId id1, ModelElementId id2) {
			assertNotSame(id1.getId(), id2.getId());
		}
	}
}
