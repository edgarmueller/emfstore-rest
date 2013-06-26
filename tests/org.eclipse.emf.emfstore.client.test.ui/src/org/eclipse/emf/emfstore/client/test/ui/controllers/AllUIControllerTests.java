/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for running all UI controllers tests.
 * 
 * @author Edgar
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	UIAddTagControllerTest.class,
	UICreateBranchControllerTest.class,
	UICreateRemoteProjectControllerTest.class,
	UIDeleteRemoteProjectControllerTest.class,
	// UIRevertCommitControllerTest.class,
	// UIServerControllerTest.class,
	UISessionControllerTest.class,
	UIShareProjectControllerTest.class,
	UIUpdateProjectControllerTest.class,
	UIUpdateProjectToVersionControllerTest.class,
	UIPagedUpdateProjectControllerTest.class
})
public class AllUIControllerTests {

}
