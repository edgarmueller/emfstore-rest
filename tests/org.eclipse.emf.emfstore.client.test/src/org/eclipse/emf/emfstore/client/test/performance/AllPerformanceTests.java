/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jsommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.performance;

import org.eclipse.emf.emfstore.client.test.FilteredSuite;
import org.eclipse.emf.emfstore.client.test.FilteredSuite.FilteredSuiteParameter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * The suite for all performance tests.
 * 
 * @author jsommerfeldt
 * 
 */
@RunWith(FilteredSuite.class)
@FilteredSuiteParameter({ "runPerfTests" })
@SuiteClasses({ PerformanceTest.class })
public class AllPerformanceTests {

}
