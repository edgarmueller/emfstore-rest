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
package org.eclipse.emf.emfstore.client.changetracking.test.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.NotificationToOperationConverter;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@link NotificationToOperationConverter}.
 * 
 * @author jsommerfeldt
 * 
 */
public class NotificationToOperationConverterTest extends ESTest {

	private static final String HUDSON = "hudson"; //$NON-NLS-1$
	private static final String ECLIPSE = "eclipse"; //$NON-NLS-1$
	private NotificationToOperationConverter converter;

	/**
	 * Init converter.
	 */
	@Override
	@Before
	public void before() {
		super.before();
		converter = new NotificationToOperationConverter((IdEObjectCollectionImpl) getProject());
	}

	/**
	 * Remove all attributes in a many attribute.
	 */
	@Test
	public void removeAllAttributes() {
		final TestElement element = Create.testElement();
		final List<String> children = new ArrayList<String>();
		children.add(ECLIPSE);
		children.add(HUDSON);
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

		final AbstractOperation operation = getProjectSpace().getOperations().get(1);
		Assert.assertTrue(operation instanceof MultiAttributeOperation);
		final List<Integer> indices = ((MultiAttributeOperation) operation).getIndexes();
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
