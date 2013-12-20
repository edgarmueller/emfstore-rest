/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.changetracking.test.notification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording.NotificationRecording;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the notification recording for attribute features.
 * 
 * @author chodnick
 */
public class MoveNotificationTest extends ESTest {

	private static final String NON_CONTAINED_N_TO_M = "nonContained_NToM"; //$NON-NLS-1$
	private static final String UNEXPECTED_NOTIFICATIONS = "unexpected notifications"; //$NON-NLS-1$
	private static final String TEST_PROJECT = "testProject"; //$NON-NLS-1$
	private static final String TEST_USE_CASE = "testUseCase"; //$NON-NLS-1$
	private static final String TEST_TEST_ELEMENT2 = "testTestElement2"; //$NON-NLS-1$
	private static final String TEST_TEST_ELEMENT1 = "testTestElement1"; //$NON-NLS-1$

	/**
	 * Change order within a list and check the generated notification.
	 */
	@Test
	public void changeList() {

		final TestElement actor1 = Create.testElement();
		final TestElement actor2 = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor1);
				getProject().addModelElement(actor2);

				actor1.setName(TEST_TEST_ELEMENT1);
				actor2.setName(TEST_TEST_ELEMENT2);
				useCase.setName(TEST_USE_CASE);

				// notifications from this operations are tested
				useCase.getNonContained_NToM().add(actor1);
				useCase.getNonContained_NToM().add(actor2);

				// now move actor 2 to top of the list
				useCase.getNonContained_NToM().move(0, actor2);
			}
		}.run(false);

		final NotificationRecording recording = ((ProjectSpaceImpl) getProjectSpace()).getNotificationRecorder()
			.getRecording();
		final List<NotificationInfo> rec = recording.asMutableList();

		// exactly one MOVE notification is expected
		assertEquals(1, rec.size());

		final NotificationInfo n = rec.get(0);
		assertSame(useCase, n.getNotifier());
		assertTrue(n.isMoveEvent());
		assertEquals(n.getNewValue(), actor2);
		assertEquals(NON_CONTAINED_N_TO_M, n.getReference().getName());

		assertEquals(0, n.getPosition());
		assertEquals(1, n.getOldValue());
	}

	@Test
	public void testMoveElementAndUndoOnRootLevel() {
		final ESLocalProjectImpl localProject = ESWorkspaceProviderImpl.getInstance().getWorkspace()
			.createLocalProject(TEST_PROJECT);
		final ProjectSpace projectSpace = localProject.toInternalAPI();
		final Project project = projectSpace.getProject();

		final TestIdEObjectCollectionChangeObserver observer = new TestIdEObjectCollectionChangeObserver();
		project.addIdEObjectCollectionChangeObserver(observer);

		final TestElement tournament1 = Create.testElement();
		final TestElement tournament2 = Create.testElement();
		final TestElement matchup = Create.testElement();

		// add tournament 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				project.addModelElement(tournament1);
			}
		}.run(false);
		assertTrue(project.contains(tournament1));
		assertTrue(UNEXPECTED_NOTIFICATIONS,
			observer.resetNotifyCalled() && observer.resetAddedCalled() && !observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// add matchup to tournament 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				tournament1.getContainedElements().add(matchup);
			}
		}.run(false);
		assertTrue(project.contains(matchup));
		assertTrue(tournament1.getContainedElements().contains(matchup));
		assertTrue(UNEXPECTED_NOTIFICATIONS,
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
		assertTrue(UNEXPECTED_NOTIFICATIONS,
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
				tournament1.getContainedElements().remove(matchup);
				tournament2.getContainedElements().add(matchup);
			}
		}.run(false);
		assertTrue(!tournament1.getContainedElements().contains(matchup));
		assertTrue(tournament2.getContainedElements().contains(matchup));
		assertTrue(UNEXPECTED_NOTIFICATIONS,
			observer.resetNotifyCalled() && observer.resetAddedCalled() && observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// undo 1
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectSpace.undoLastOperation();
			}
		}.run(false);
		assertTrue(!tournament1.getContainedElements().contains(matchup));
		assertTrue(!tournament2.getContainedElements().contains(matchup));
		assertTrue(project.contains(matchup));
		assertTrue(UNEXPECTED_NOTIFICATIONS,
			observer.resetNotifyCalled() && observer.resetAddedCalled() && observer.resetRemovedCalled()
				&& !observer.resetDeletedCalled());

		// undo 2
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectSpace.undoLastOperation();
			}
		}.run(false);
		assertTrue(tournament1.getContainedElements().contains(matchup));
		assertTrue(!tournament2.getContainedElements().contains(matchup));
		assertTrue(UNEXPECTED_NOTIFICATIONS,
			observer.resetNotifyCalled() && !observer.resetAddedCalled()
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
			final boolean toReturn = notifyCalled;
			notifyCalled = false;
			return toReturn;
		}

		public void modelElementAdded(IdEObjectCollection collection, EObject modelElement) {
			addedCalled = true;
		}

		public boolean resetAddedCalled() {
			final boolean toReturn = addedCalled;
			addedCalled = false;
			return toReturn;
		}

		public void modelElementRemoved(IdEObjectCollection collection, EObject modelElement) {
			removedCalled = true;
		}

		public boolean resetRemovedCalled() {
			final boolean toReturn = removedCalled;
			removedCalled = false;
			return toReturn;
		}

		public void collectionDeleted(IdEObjectCollection collection) {
			deletedCalled = true;
		}

		public boolean resetDeletedCalled() {
			final boolean toReturn = deletedCalled;
			deletedCalled = false;
			return toReturn;
		}

	}

}
