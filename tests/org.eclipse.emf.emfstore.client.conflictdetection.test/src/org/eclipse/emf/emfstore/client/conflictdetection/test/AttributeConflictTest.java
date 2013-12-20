/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.conflictdetection.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Test conflict detection for attributes.
 * 
 * @author koegel
 */
public class AttributeConflictTest extends ConflictDetectionTest {

	private static final String OLDE_VALUE = "oldeValue"; //$NON-NLS-1$
	private static final String OLD_VALUE = "oldValue"; //$NON-NLS-1$
	private static final String ID1 = "id1"; //$NON-NLS-1$
	private static final String SAME_FEATURE = "same Feature"; //$NON-NLS-1$

	/**
	 * Test for conflicts on two attribute Operations.
	 */
	@Test
	public void testAttributeWithAttributeConflict() {
		final TestElement testElement = Create.testElement();

		Add.toProject(getLocalProject(), testElement);

		final String featureName = SAME_FEATURE;
		final AttributeOperation attributeOperation1 = OperationsFactory.eINSTANCE.createAttributeOperation();
		attributeOperation1.setClientDate(new Date());
		attributeOperation1.setFeatureName(featureName);
		attributeOperation1.setIdentifier(ID1);
		attributeOperation1.setModelElementId(getProject().getModelElementId(testElement));
		attributeOperation1.setOldValue(OLD_VALUE);
		attributeOperation1.setNewValue(OLDE_VALUE);

		final AttributeOperation attributeOperation2 = OperationsFactory.eINSTANCE.createAttributeOperation();
		attributeOperation2.setClientDate(new Date());
		attributeOperation2.setFeatureName(featureName);
		attributeOperation2.setIdentifier(ID1);
		attributeOperation2.setModelElementId(org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE
			.createModelElementId());
		attributeOperation2.setOldValue(OLD_VALUE);
		attributeOperation2.setNewValue(OLDE_VALUE);

		assertEquals(false, doConflict(attributeOperation1, attributeOperation2));

		attributeOperation2.setModelElementId(getProject().getModelElementId(testElement));
		attributeOperation2.setFeatureName(featureName + "2"); //$NON-NLS-1$

		assertEquals(false, doConflict(attributeOperation1, attributeOperation2));

		attributeOperation2.setFeatureName(featureName);

		assertEquals(true, doConflict(attributeOperation1, attributeOperation2));
	}
}
