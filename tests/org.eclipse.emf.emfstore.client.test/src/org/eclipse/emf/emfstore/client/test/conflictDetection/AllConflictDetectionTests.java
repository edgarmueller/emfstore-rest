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
package org.eclipse.emf.emfstore.client.test.conflictDetection;

import org.eclipse.emf.emfstore.client.test.conflictDetection.merging.AllMergeTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for running all tests of workspace.
 * 
 * @author koegel
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AttributeConflictTest.class, ConflictDetectionAttributeTest.class,
	ConflictDetectionReferenceTest.class, ConflictDetectionDeleteTest.class, ConflictDetectionMultiReferenceTest.class,
	ConflictDetectionRequiredRelationTest.class, ConflictDetectionMultiAttributeTest.class, AllMergeTests.class

})
public class AllConflictDetectionTests {

}
