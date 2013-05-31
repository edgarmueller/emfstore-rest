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
package org.eclipse.emf.emfstore.client.test.changeTracking.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.NotificationToOperationConverter;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@link NotificationToOperationConverter}.
 * 
 * @author jsommerfeldt
 * 
 */
@SuppressWarnings("restriction")
public class NotificationToOperationConverterTest extends NotificationTest {

	private NotificationToOperationConverter converter;

	/**
	 * Init converter.
	 */
	@Before
	public void setup() {
		converter = new NotificationToOperationConverter((IdEObjectCollectionImpl) getProject());
	}

	/**
	 * Remove all attributes in a many attribute.
	 */
	@Test
	public void removeAllAttributes() {
		final TestElement element = getTestElement();
		List<String> children = new ArrayList<String>();
		children.add("eclipse");
		children.add("hudson");
		element.getStrings().addAll(children);
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().addModelElement(element);
				return null;
			}
		});

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				element.getStrings().clear();
				return null;
			}
		});

		AbstractOperation operation = getProjectSpace().getOperations().get(1);
		Assert.assertTrue(operation instanceof MultiAttributeOperation);
		List<Integer> indices = ((MultiAttributeOperation) operation).getIndexes();
		Assert.assertEquals(indices.get(0), (Integer) 0);
		Assert.assertEquals(indices.get(0), (Integer) 0);
	}

	/**
	 * Test an invalid event type.
	 */
	@Test
	public void invalidEventType() {
		Assert.assertNull(converter.convert(new NotificationInfo(new NotificationImpl(Integer.MIN_VALUE, null, null))));

	}

	/**
	 * Test a notification which is only touch.
	 */
	@Test
	public void onlyTouch() {
		Assert.assertNull(converter
			.convert(new NotificationInfo(new NotificationImpl(Notification.RESOLVE, null, null))));
	}
}
