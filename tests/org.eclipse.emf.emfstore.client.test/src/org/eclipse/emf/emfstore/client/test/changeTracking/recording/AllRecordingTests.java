/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * jsommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.recording;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for all recording tests.
 * 
 * @author jsommerfeldt
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ AllocateIdsPolicyTest.class, OperationRecorderTest.class })
public class AllRecordingTests {

}
