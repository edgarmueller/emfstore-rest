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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests the notification recording for attribute features.
 * 
 * @author chodnick
 */
public class ReferenceNotificationTest extends ESTest {

	private static final String TEST_USE_CASE = "testUseCase"; //$NON-NLS-1$
	private static final String TEST_ACTOR = "testActor"; //$NON-NLS-1$
	private static final String TEST_TEST_ELEMENT = "testTestElement"; //$NON-NLS-1$

	/**
	 * Change an reference and check the generated notification.
	 */
	@Test
	public void changeReference1toN() {
		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				actor.setName(TEST_TEST_ELEMENT);
				useCase.setName(TEST_TEST_ELEMENT);

				// notifications from this operations are tested
				useCase.setNonContained_NTo1(actor);
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one SET notification is expected with attribute feature "initiatingTestElement" on our useCase and
		// newValue
		// actor

		// due to refactoring and removing the bidirectional filter two notifications are expected.
		assertEquals(2, rec.size());

		final NotificationInfo addNotification = rec.get(0);
		final NotificationInfo setNotification = rec.get(1);

		assertSame(actor, addNotification.getNotifier());
		assertTrue(addNotification.isReferenceNotification());
		assertTrue(addNotification.isAddEvent());
		assertSame(addNotification.getNewValue(), useCase);
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), addNotification.getReference().getName());
		assertNull(addNotification.getOldValue());

		assertSame(useCase, setNotification.getNotifier());
		assertTrue(setNotification.isReferenceNotification());
		assertTrue(setNotification.isSetEvent());
		assertSame(setNotification.getNewValue(), actor);
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), setNotification.getReference().getName());
		assertNull(setNotification.getOldValue());

	}

	/**
	 * Change an reference and check the generated notification.
	 */
	@Ignore
	public void changeReference1to1() {
		// TODO
		fail("FIXME: MK where do we have 1:1 non-containment references?"); //$NON-NLS-1$
	}

	/**
	 * Add an reference and check the generated notification.
	 */

	@Test
	public void addReferenceNto1() {

		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				actor.setName(TEST_TEST_ELEMENT);
				useCase.setName(TEST_TEST_ELEMENT);

				// notifications from this operations are tested
				actor.getNonContained_1ToN().add(useCase);

			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one ADD notification is expected
		// after refactoring: additional SET is expected
		assertEquals(2, rec.size());

		final NotificationInfo setNotification = rec.get(0);
		final NotificationInfo addNotification = rec.get(1);

		assertSame(useCase, setNotification.getNotifier());
		assertTrue(setNotification.isReferenceNotification());
		assertTrue(setNotification.isSetEvent());
		assertSame(setNotification.getNewValue(), actor);
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), setNotification.getReference().getName());
		assertNull(setNotification.getOldValue());

		assertSame(actor, addNotification.getNotifier());
		assertTrue(addNotification.isReferenceNotification());
		assertTrue(addNotification.isAddEvent());
		assertSame(addNotification.getNewValue(), useCase);
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), addNotification.getReference().getName());

	}

	/**
	 * Add a reference and check the generated notification.
	 */

	@Test
	public void addReferenceNtoN() {

		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				actor.setName(TEST_ACTOR);
				useCase.setName(TEST_USE_CASE);

				// notifications from this operations are tested
				useCase.getNonContained_NToM().add(actor);
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly two ADD notification is expected
		assertEquals(2, rec.size());

		final NotificationInfo actorAdd = rec.get(0);
		final NotificationInfo useCaseAdd = rec.get(1);

		assertSame(actor, actorAdd.getNotifier());
		assertTrue(actorAdd.isReferenceNotification());
		assertTrue(actorAdd.isAddEvent());
		assertSame(actorAdd.getNewValue(), useCase);
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), actorAdd.getReference().getName());

		assertSame(useCase, useCaseAdd.getNotifier());
		assertTrue(useCaseAdd.isReferenceNotification());
		assertTrue(useCaseAdd.isAddEvent());
		assertSame(useCaseAdd.getNewValue(), actor);
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), useCaseAdd.getReference().getName());

	}

	/**
	 * Remove a reference and check the generated notification.
	 */

	@Test
	public void removeReferenceNto1() {

		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				actor.setName(TEST_TEST_ELEMENT);
				useCase.setName(TEST_TEST_ELEMENT);

				// notifications from this operations are tested
				actor.getNonContained_1ToN().add(useCase);
				actor.getNonContained_1ToN().remove(useCase);
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one REMOVE notification is expected from the actor

		assertEquals(2, rec.size());

		NotificationInfo n = rec.get(0);
		assertSame(useCase, n.getNotifier());
		assertTrue(n.isReferenceNotification());
		assertTrue(n.isSetEvent());
		assertSame(n.getOldValue(), actor);
		assertNull(n.getNewValue());
		assertEquals(TestElementFeatures.nonContainedNTo1().getName(), n.getReference().getName());

		n = rec.get(1);
		assertSame(actor, n.getNotifier());
		assertTrue(n.isReferenceNotification());
		assertTrue(n.isRemoveEvent());
		assertSame(n.getOldValue(), useCase);
		assertEquals(TestElementFeatures.nonContained1ToN().getName(), n.getReference().getName());

	}

	/**
	 * Remove a reference and check the generated notification.
	 */

	@Test
	public void removeReferenceNtoN() {

		final TestElement actor = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				actor.setName(TEST_ACTOR);
				useCase.setName(TEST_USE_CASE);

				// notifications from this operations are tested
				useCase.getNonContained_NToM().add(actor);
				useCase.getNonContained_NToM().remove(actor);
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly two REMOVE notifications are expected
		// actor loses its useCase (message needed to retain index of useCase)
		// useCase loses its actor (message needed to retain index of actor)
		assertEquals(2, rec.size());

		NotificationInfo n = rec.get(0);
		assertSame(actor, n.getNotifier());
		assertTrue(n.isReferenceNotification());
		assertTrue(n.isRemoveEvent());
		assertSame(n.getOldValue(), useCase);
		assertEquals(TestElementFeatures.nonContainedMToN().getName(), n.getReference().getName());
		assertEquals(n.getPosition(), 0);

		n = rec.get(1);
		assertSame(useCase, n.getNotifier());
		assertTrue(n.isReferenceNotification());
		assertTrue(n.isRemoveEvent());
		assertSame(n.getOldValue(), actor);
		assertEquals(TestElementFeatures.nonContainedNToM().getName(), n.getReference().getName());
		assertEquals(n.getPosition(), 0);

	}

}
