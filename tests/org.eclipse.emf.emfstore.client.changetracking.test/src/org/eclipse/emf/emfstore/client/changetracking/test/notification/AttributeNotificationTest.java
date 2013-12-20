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

import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the notification recording for attribute features.
 * 
 * @author chodnick
 */
public class AttributeNotificationTest extends ESTest {

	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String NEW_NAME = "newName"; //$NON-NLS-1$

	/**
	 * Change an attribute and check the generated notification.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void changeAttribute() throws UnsupportedOperationException, UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		ProjectUtil.addElement(getProjectSpace().toAPI(), useCase);
		Update.testElement(TestElementFeatures.name(), useCase, NEW_NAME);

		assertEquals(NEW_NAME, useCase.getName());

		// exactly one SET notification is expected with attribute feature "name" on our useCase and newValue
		// newName
		final List<NotificationInfo> rec = getRecording().asMutableList();
		assertEquals(1, rec.size());
		final NotificationInfo n = rec.get(0);
		assertSame(useCase, n.getNotifier());
		assertTrue(n.isAttributeNotification());
		assertTrue(n.isSetEvent());
		assertEquals(n.getNewValue(), NEW_NAME);
		assertEquals(n.getAttribute().getName(), NAME);
		assertEquals(null, n.getOldValue());
	}
}
