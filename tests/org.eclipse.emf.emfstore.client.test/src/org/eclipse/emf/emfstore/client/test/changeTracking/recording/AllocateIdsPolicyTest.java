/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jsommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.recording;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.client.test.server.ServerTests;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.EMFStoreBasicCommandStack;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the allocate ids policy of the {@link org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection}.
 * 
 * @author jsommerfeldt
 * 
 */
public class AllocateIdsPolicyTest extends ServerTests {

	private IdEObjectCollectionImpl collection;

	/**
	 * Set up collection.
	 */
	@Before
	public void initCollection() {
		collection = ((IdEObjectCollectionImpl) getProject());
	}

	/**
	 * Check clear after server actions.
	 * 
	 * @throws ESException if there is a problem during a server action.
	 */
	@SuppressWarnings("restriction")
	@Test
	public void clearAfterServerAction() throws ESException {
		TestElement element = getTestElement();

		addRemoveObject(element);
		getProjectSpace().commit(new NullProgressMonitor());
		assertNull(collection.getDeletedModelElementId(element));

		addRemoveObject(element);
		getProjectSpace().toAPI().shareProject(new NullProgressMonitor());
		assertNull(collection.getDeletedModelElementId(element));
	}

	private void addRemoveObject(final EObject object) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				collection.addModelElement(object);
				collection.deleteModelElement(object);
				return null;
			}
		});
		assertNull(collection.getDeletedModelElementId(object));
	}

	/**
	 * Test using the command id policy.
	 */
	@Test
	public void commandIdAllocation() {
		removeAddWithCommand(new EqualComparator());
		removeAddWithCommands(new NotEqualComparator());
		if (ESWorkspaceProviderImpl.getInstance().getEditingDomain().getCommandStack() instanceof EMFStoreBasicCommandStack) {
			removeAddWithoutCommand(new NotEqualComparator());
			removeAddWithoutCommand2(new NotEqualComparator());
		}
	}

	/**
	 * Remove and add objects wihtin one command.
	 * 
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithCommand(IdComparator comparator) {
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final Game game = BowlingFactory.eINSTANCE.createGame();
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				collection.addModelElement(matchup);
				return null;
			}
		});
		ModelElementId matchupId1 = collection.getModelElementId(matchup);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				matchup.getGames().add(game);
				return null;
			}
		});
		ModelElementId gameId1 = collection.getModelElementId(game);

		// remove and add matchup in different commands
		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				collection.deleteModelElement(matchup);
				collection.addModelElement(matchup);
				return null;
			}
		});

		comparator.compare(matchupId1, collection.getModelElementId(matchup));
		comparator.compare(gameId1, collection.getModelElementId(game));
	}

	/**
	 * Remove and add objects within several commands.
	 * 
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithCommands(IdComparator comparator) {
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final Game game = BowlingFactory.eINSTANCE.createGame();
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				collection.addModelElement(matchup);
				return null;
			}
		});
		ModelElementId matchupId1 = collection.getModelElementId(matchup);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				matchup.getGames().add(game);
				return null;
			}
		});

		ModelElementId gameId1 = collection.getModelElementId(game);

		// remove and add matchup in different commands
		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				collection.deleteModelElement(matchup);
				return null;
			}
		});
		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				collection.addModelElement(matchup);
				return null;
			}
		});

		comparator.compare(matchupId1, collection.getModelElementId(matchup));
		comparator.compare(gameId1, collection.getModelElementId(game));
	}

	/**
	 * Remove and add objects without commands.
	 * 
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithoutCommand(IdComparator comparator) {
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final Game game = BowlingFactory.eINSTANCE.createGame();
		collection.addModelElement(matchup);
		ModelElementId matchupId1 = collection.getModelElementId(matchup);

		matchup.getGames().add(game);

		ModelElementId gameId1 = collection.getModelElementId(game);

		// remove and add matchup without command
		collection.deleteModelElement(matchup);
		collection.addModelElement(matchup);

		comparator.compare(matchupId1, collection.getModelElementId(matchup));
		comparator.compare(gameId1, collection.getModelElementId(game));
	}

	/**
	 * Remove an element by setting the reference to null and add it again- All without commands.
	 * 
	 * @param comparator The {@link IdComparator} to compare ids.
	 */
	public void removeAddWithoutCommand2(IdComparator comparator) {
		Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		Game game = BowlingFactory.eINSTANCE.createGame();
		collection.addModelElement(matchup);
		matchup.getGames().add(game);
		ModelElementId gameId1 = collection.getModelElementId(game);

		// remove and add matchup without command
		game.setMatchup(null);
		matchup.getGames().add(game);

		comparator.compare(gameId1, collection.getModelElementId(game));
	}

	/**
	 * Internal interface for comparison.
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
