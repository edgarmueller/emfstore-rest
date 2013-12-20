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
package org.eclipse.emf.emfstore.client.recording.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for running all tests of workspace.
 * 
 * @author koegel
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AttributeOperationTest.class,
	CompositeOperationTest.class,
	CreateDeleteOperationTest.class,
	MultiAttributeTest.class,
	MultiReferenceMoveOperationTest.class,
	MultiReferenceOperationTest.class,
	MultiAttributeSetTest.class,
	MultiReferenceSetOperationTest.class,
	MultiAttributeMoveOperationTest.class,
	SingleReferenceOperationTest.class,
	SimpleOperationObserverTest.class,
	OperationRecorderTest.class,
	AllocateIdsPolicyTest.class,
	DuplicateOperationsTest.class
})
public class AllRecordingTests {

}
