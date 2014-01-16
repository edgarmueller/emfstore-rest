/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.test;

import org.eclipse.emf.emfstore.server.accesscontrol.test.AllAccessControlTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all server related tests.
 * 
 * @author emueller
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AllAccessControlTests.class,
	BranchTests.class,
	ServerInterfaceTest.class,
	HistoryAPITests.class,
	ChangeCertificationTest.class,
	ChecksumTest.class,
	FileManagerTest.class,
	InvalidArgumentsTest.class,
	PropertiesTest.class,
	AllServerAPITests.class,
	VersionSpecTests.class,
	VersionVerifierTest.class
})
public class AllServerTests {

}
