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

import java.util.List;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording.NotificationRecording;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the notification recording for attribute features.
 * 
 * @author chodnick
 */
public class ContainmentNotificationTest extends ESTest {

	private static final String CONTAINED_ELEMENTS2 = "containedElements2"; //$NON-NLS-1$
	private static final String CONTAINER2 = "container2"; //$NON-NLS-1$
	private static final String CONTAINER = "container"; //$NON-NLS-1$
	private static final String CONTAINED_ELEMENTS = "containedElements"; //$NON-NLS-1$
	private static final String TEST_USE_CASE = "testUseCase"; //$NON-NLS-1$

	/**
	 * Change order within a list and check the generated notification.
	 */
	@Test
	public void moveOnSameFeature() {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(section2);
				getProject().addModelElement(useCase);

				useCase.setName(TEST_USE_CASE);
				section1.getContainedElements().add(useCase);

				// reattach usecase to another leaf section
				// section2.getModelElements().add(useCase);
				useCase.setContainer(section2);
			}
		}.run(false);

		final NotificationRecording recording = ((ProjectSpaceImpl) getProjectSpace()).getNotificationRecorder()
			.getRecording();
		final List<NotificationInfo> rec = recording.asMutableList();

		// exactly one SET notification is expected, resetting the leaf section
		// and one ADD
		assertEquals(3, rec.size());

		NotificationInfo n = rec.get(0);
		assertSame(section1, n.getNotifier());
		assertTrue(n.isRemoveEvent());
		assertSame(n.getOldValue(), useCase);
		assertEquals(CONTAINED_ELEMENTS, n.getReference().getName());

		n = rec.get(1);
		assertSame(section2, n.getNotifier());
		assertTrue(n.isReferenceNotification());
		assertTrue(n.isAddEvent());
		assertSame(n.getNewValue(), useCase);
		assertEquals(CONTAINED_ELEMENTS, n.getReference().getName());

		n = rec.get(2);
		assertSame(useCase, n.getNotifier());
		assertTrue(n.isSetEvent());
		assertEquals(n.getOldValue(), section1);
		assertEquals(n.getNewValue(), section2);
		assertEquals(CONTAINER, n.getReference().getName());

	}

	/**
	 * Change order within a list and check the generated notification.
	 */
	@Test
	public void moveOnDifferentFeatures() {

		final TestElement section = Create.testElement();
		final TestElement req = Create.testElement();
		final TestElement child = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				getProject().addModelElement(req);
				getProject().addModelElement(child);

				section.getContainedElements().add(child);

				// reattach child to a functional requirement
				// req.getRefiningRequirements().add(child);
				child.setContainer2(req);
			}
		}.run(false);

		final List<NotificationInfo> rec = getRecording().asMutableList();

		// one REMOVE and two SET notification are expected, resetting the leaf section to "null" and the refined req to
		// "req"
		assertEquals(4, rec.size());

		// check index maintaining remove
		final NotificationInfo n0 = rec.get(0);
		assertSame(section, n0.getNotifier());
		assertTrue(n0.isRemoveEvent());
		assertEquals(CONTAINED_ELEMENTS, n0.getReference().getName());
		assertSame(child, n0.getOldValue());
		assertEquals(0, n0.getPosition());

		final NotificationInfo n1 = rec.get(1);
		assertSame(req, n1.getNotifier());
		assertTrue(n1.isReferenceNotification());
		assertTrue(n1.isAddEvent());
		assertSame(n1.getNewValue(), child);
		assertEquals(CONTAINED_ELEMENTS2, n1.getReference().getName());

		// check first set
		final NotificationInfo n2 = rec.get(2);
		assertSame(child, n2.getNotifier());
		assertTrue(n2.isSetEvent());
		assertEquals(n2.getReference().getName(), CONTAINER);
		assertEquals(n2.getOldValue(), section);
		assertNull(n2.getNewValue());

		// check second set
		final NotificationInfo n3 = rec.get(3);
		assertSame(child, n3.getNotifier());
		assertTrue(n3.isSetEvent());
		assertEquals(CONTAINER2, n3.getReference().getName());
		assertNull(n3.getOldValue());
		assertEquals(n3.getNewValue(), req);

	}

}
