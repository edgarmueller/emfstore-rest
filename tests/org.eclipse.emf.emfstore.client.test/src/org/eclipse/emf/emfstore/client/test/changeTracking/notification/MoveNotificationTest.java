/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.notification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.test.model.requirement.Actor;
import org.eclipse.emf.emfstore.client.test.model.requirement.RequirementFactory;
import org.eclipse.emf.emfstore.client.test.model.requirement.UseCase;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording.NotificationRecording;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.junit.Test;

/**
 * Tests the notification recording for attribute features.
 * 
 * @author chodnick
 */
public class MoveNotificationTest extends NotificationTest {

	/**
	 * Change order within a list and check the generated notification.
	 */
	@Test
	public void changeList() {

		final Actor actor1 = RequirementFactory.eINSTANCE.createActor();
		final Actor actor2 = RequirementFactory.eINSTANCE.createActor();
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor1);
				getProject().addModelElement(actor2);

				actor1.setName("testActor1");
				actor2.setName("testActor2");
				useCase.setName("testUseCase");

				// notifications from this operations are tested
				useCase.getParticipatingActors().add(actor1);
				useCase.getParticipatingActors().add(actor2);

				// now move actor 2 to top of the list
				useCase.getParticipatingActors().move(0, actor2);
			}
		}.run(false);

		NotificationRecording recording = getProjectSpaceImpl().getNotificationRecorder().getRecording();
		List<NotificationInfo> rec = recording.asMutableList();

		// exactly one MOVE notification is expected
		assertEquals(1, rec.size());

		NotificationInfo n = rec.get(0);
		assertSame(useCase, n.getNotifier());
		assertTrue(n.isMoveEvent());
		assertEquals(n.getNewValue(), actor2);
		assertEquals(n.getReference().getName(), "participatingActors");

		assertEquals(n.getPosition(), 0);
		assertEquals(n.getOldValue(), 1);

	}

	@Test
	public void testMoveElementAndUndoOnRootLevel() {
		ESLocalProjectImpl localProject = ESWorkspaceProviderImpl.getInstance().getWorkspace()
			.createLocalProject("testProject");
		final ProjectSpace projectSpace = localProject.toInternalAPI();
		final Project project = projectSpace.getProject();

		TestIdEObjectCollectionChangeObserver observer = new TestIdEObjectCollectionChangeObserver();
		project.addIdEObjectCollectionChangeObserver(observer);

		final Tournament tournament1 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament tournament2 = BowlingFactory.eINSTANCE.createTournament();
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();

		// add tournament 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project.addModelElement(tournament1);
			}
		}.run(false);
		assertTrue(project.contains(tournament1));
		assertTrue("unexpected notidications",
			observer.resetNotifyCalled() && observer.resetAddedCalled() && !observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// add matchup to tournament 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournament1.getMatchups().add(matchup);
			}
		}.run(false);
		assertTrue(project.contains(matchup));
		assertTrue(tournament1.getMatchups().contains(matchup));
		assertTrue("unexpected notifications",
			observer.resetNotifyCalled() && observer.resetAddedCalled() && !observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// add tournament 2
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project.addModelElement(tournament2);
			}
		}.run(false);
		assertTrue(project.contains(tournament2));
		assertTrue("unexpected notifications",
			observer.resetNotifyCalled() && observer.resetAddedCalled() && !observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// move matchup from tournament 1 to 2

		// EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(tournament2);
		// Set<EObject> collection = new HashSet<EObject>();
		// collection.add(matchup);
		// domain.getCommandStack().execute(
		// DragAndDropCommand.create(domain, tournament2, 0f, 1,
		// DragAndDropCommand.DROP_MOVE, collection));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournament1.getMatchups().remove(matchup);
				tournament2.getMatchups().add(matchup);
			}
		}.run(false);
		assertTrue(!tournament1.getMatchups().contains(matchup));
		assertTrue(tournament2.getMatchups().contains(matchup));
		assertTrue("unexpected notidications",
			observer.resetNotifyCalled() && observer.resetAddedCalled() && observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// undo 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectSpace.undoLastOperation();
			}
		}.run(false);
		assertTrue(!tournament1.getMatchups().contains(matchup));
		assertTrue(!tournament2.getMatchups().contains(matchup));
		assertTrue(project.contains(matchup));
		assertTrue("unexpected notidications",
			observer.resetNotifyCalled() && observer.resetAddedCalled() && observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// undo 2
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectSpace.undoLastOperation();
			}
		}.run(false);
		assertTrue(tournament1.getMatchups().contains(matchup));
		assertTrue(!tournament2.getMatchups().contains(matchup));
		assertTrue("unexpected notidications", observer.resetNotifyCalled() && !observer.resetAddedCalled()
			&& !observer.resetRemovedCalled()
			&& !observer.resetDeletedCalled());
	}

	private class TestIdEObjectCollectionChangeObserver implements IdEObjectCollectionChangeObserver {
		private boolean notifyCalled = false;
		private boolean addedCalled = false;
		private boolean removedCalled = false;
		private boolean deletedCalled = false;

		public void notify(Notification notification, IdEObjectCollection collection, EObject modelElement) {
			notifyCalled = true;
		}

		public boolean resetNotifyCalled() {
			boolean toReturn = notifyCalled;
			notifyCalled = false;
			return toReturn;
		}

		public void modelElementAdded(IdEObjectCollection collection, EObject modelElement) {
			addedCalled = true;
		}

		public boolean resetAddedCalled() {
			boolean toReturn = addedCalled;
			addedCalled = false;
			return toReturn;
		}

		public void modelElementRemoved(IdEObjectCollection collection, EObject modelElement) {
			removedCalled = true;
		}

		public boolean resetRemovedCalled() {
			boolean toReturn = removedCalled;
			removedCalled = false;
			return toReturn;
		}

		public void collectionDeleted(IdEObjectCollection collection) {
			deletedCalled = true;
		}

		public boolean resetDeletedCalled() {
			boolean toReturn = deletedCalled;
			deletedCalled = false;
			return toReturn;
		}

	}

}
