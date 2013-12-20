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

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the notification recording for attribute features.
 * 
 * @author chodnick
 */
public class MultiReferenceNotificationTest extends ESTest {

	private static final String NON_CONTAINED_M_TO_N = "nonContained_MToN"; //$NON-NLS-1$
	private static final String NON_CONTAINED_N_TO_M = "nonContained_NToM"; //$NON-NLS-1$
	private static final String NON_CONTAINED_1_TO_N = "nonContained_1ToN"; //$NON-NLS-1$
	private static final String NON_CONTAINED_N_TO1 = "nonContained_NTo1"; //$NON-NLS-1$
	private static final String TEST_TEST_ELEMENT3 = "testTestElement3"; //$NON-NLS-1$
	private static final String TEST_TEST_ELEMENT2 = "testTestElement2"; //$NON-NLS-1$
	private static final String TEST_TEST_ELEMENT1 = "testTestElement1"; //$NON-NLS-1$
	private static final String TEST_ACTOR = "testActor"; //$NON-NLS-1$

	/**
	 * Add multiple references.
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void addReferences1toN() {

		final TestElement actor = Create.testElement();
		final TestElement useCase1 = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();
		final TestElement[] useCases = { useCase1, useCase2, useCase3 };

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase1);
				getProject().addModelElement(useCase2);
				getProject().addModelElement(useCase3);
				getProject().addModelElement(actor);

				actor.setName(TEST_ACTOR);
				useCase1.setName(TEST_TEST_ELEMENT1);
				useCase2.setName(TEST_TEST_ELEMENT2);
				useCase3.setName(TEST_TEST_ELEMENT3);

				// notifications from this operations are tested
				actor.getNonContained_1ToN().addAll(Arrays.asList(useCases));
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one ADD_MANY notification is expected
		// and 3 SET
		assertEquals(4, rec.size());

		for (int i = 0; i < 3; i++) {
			final NotificationInfo set = rec.get(i);

			assertSame(useCases[i], set.getNotifier());
			assertTrue(set.isReferenceNotification());
			assertTrue(set.isSetEvent());
			assertEquals(set.getNewValue(), actor);
			assertEquals(NON_CONTAINED_N_TO1, set.getReference().getName());
		}

		final NotificationInfo addMany = rec.get(3);

		assertSame(actor, addMany.getNotifier());
		assertTrue(addMany.isReferenceNotification());
		assertTrue(addMany.isAddManyEvent());
		assertEquals(((EList<TestElement>) addMany.getNewValue()).size(), 3);
		assertEquals(NON_CONTAINED_1_TO_N, addMany.getReference().getName());

	}

	/**
	 * Add references on a n:n relationship and check results.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void addReferencesNtoN() {

		final TestElement actor = Create.testElement();
		final TestElement useCase1 = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();
		final TestElement[] useCases = { useCase1, useCase2, useCase3 };

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase1);
				getProject().addModelElement(useCase2);
				getProject().addModelElement(useCase3);
				getProject().addModelElement(actor);

				actor.setName(TEST_ACTOR);
				useCase1.setName(TEST_TEST_ELEMENT1);
				useCase2.setName(TEST_TEST_ELEMENT2);
				useCase3.setName(TEST_TEST_ELEMENT3);

				// notifications from this operations are tested
				actor.getNonContained_NToM().addAll(Arrays.asList(useCases));
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one ADD_MANY notification is expected
		// and three ADD
		assertEquals(4, rec.size());

		for (int i = 0; i < 3; i++) {
			final NotificationInfo set = rec.get(i);

			assertSame(useCases[i], set.getNotifier());
			assertTrue(set.isReferenceNotification());
			assertTrue(set.isAddEvent());
			assertEquals(set.getNewValue(), actor);
			assertEquals(NON_CONTAINED_M_TO_N, set.getReference().getName());
		}

		final NotificationInfo addMany = rec.get(3);

		assertSame(actor, addMany.getNotifier());
		assertTrue(addMany.isReferenceNotification());
		assertTrue(addMany.isAddManyEvent());
		assertEquals(((EList<TestElement>) addMany.getNewValue()).size(), 3);
		assertEquals(NON_CONTAINED_N_TO_M, addMany.getReference().getName());

	}

	/**
	 * Remove multiple references and check the generated notification.
	 */

	@Test
	public void removeReferences1ToN() {

		final TestElement actor = Create.testElement();
		final TestElement useCase1 = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();
		final TestElement[] useCasesIn = { useCase1, useCase2, useCase3 };
		final TestElement[] useCasesOut = { useCase1, useCase3 };

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase1);
				getProject().addModelElement(useCase2);
				getProject().addModelElement(useCase3);
				getProject().addModelElement(actor);

				actor.setName(TEST_ACTOR);
				useCase1.setName(TEST_TEST_ELEMENT1);
				useCase2.setName(TEST_TEST_ELEMENT2);
				useCase3.setName(TEST_TEST_ELEMENT3);

				// notifications from this operations are tested
				actor.getNonContained_1ToN().addAll(Arrays.asList(useCasesIn));
				actor.getNonContained_1ToN().removeAll(Arrays.asList(useCasesOut));
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one REMOVE_MANY notification is expected
		// and two SET
		assertEquals(3, rec.size());

		for (int i = 0; i < 2; i++) {
			final NotificationInfo set = rec.get(i);

			assertSame(useCasesOut[i], set.getNotifier());
			assertTrue(set.isReferenceNotification());
			assertTrue(set.isSetEvent());
			assertEquals(set.getNewValue(), null);
			assertEquals(set.getOldValue(), actor);
			assertEquals(NON_CONTAINED_N_TO1, set.getReference().getName());
		}

		final NotificationInfo removeMany = rec.get(2);

		assertSame(actor, removeMany.getNotifier());
		assertTrue(removeMany.isReferenceNotification());
		assertTrue(removeMany.isRemoveManyEvent());
		assertEquals(((int[]) removeMany.getNewValue()).length, 2);
		assertEquals(NON_CONTAINED_1_TO_N, removeMany.getReference().getName());

	}

	/**
	 * Remove references on a n:n relationship and check results.
	 */

	@Test
	public void removeReferencesNtoN() {

		final TestElement actor = Create.testElement();
		final TestElement useCase1 = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement useCase3 = Create.testElement();
		final TestElement[] useCasesIn = { useCase1, useCase2, useCase3 };
		final TestElement[] useCasesOut = { useCase1, useCase3 };

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(useCase1);
				getProject().addModelElement(useCase2);
				getProject().addModelElement(useCase3);
				getProject().addModelElement(actor);

				actor.setName(TEST_ACTOR);
				useCase1.setName(TEST_TEST_ELEMENT1);
				useCase2.setName(TEST_TEST_ELEMENT2);
				useCase3.setName(TEST_TEST_ELEMENT3);

				// notifications from this operations are tested
				actor.getNonContained_NToM().addAll(Arrays.asList(useCasesIn));
				actor.getNonContained_NToM().removeAll(Arrays.asList(useCasesOut));

			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// exactly one REMOVE_MANY notification is expected
		// and 2 REMOVE
		assertEquals(3, rec.size());

		for (int i = 0; i < 2; i++) {
			final NotificationInfo set = rec.get(i);

			assertSame(useCasesOut[i], set.getNotifier());
			assertTrue(set.isReferenceNotification());
			assertTrue(set.isRemoveEvent());
			assertEquals(set.getOldValue(), actor);
			assertEquals(set.getNewValue(), null);
			assertEquals(NON_CONTAINED_M_TO_N, set.getReference().getName());
		}

		final NotificationInfo removeMany = rec.get(2);

		assertSame(actor, removeMany.getNotifier());
		assertTrue(removeMany.isReferenceNotification());
		assertTrue(removeMany.isRemoveManyEvent());
		assertEquals(((int[]) removeMany.getNewValue()).length, 2);
		assertEquals(NON_CONTAINED_N_TO_M, removeMany.getReference().getName());

	}
}
