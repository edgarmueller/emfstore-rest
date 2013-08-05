/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.alltests;

import org.eclipse.emf.emfstore.client.test.api.AllAPITests;
import org.eclipse.emf.emfstore.client.test.changeTracking.AllChangeTrackingTests;
import org.eclipse.emf.emfstore.client.test.ui.AllUITests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for all SWT, API and ChangeTracking Tests. Used for testing with
 * transactional editing domain on build server.
 * 
 * @author jfaltermeier
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ AllAPITests.class, AllUITests.class, AllChangeTrackingTests.class })
public class TransactionalEditingDomainTests {

}
