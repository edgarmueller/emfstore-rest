/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf.test;

import org.eclipse.emf.emfstore.client.test.FilteredSuite;
import org.eclipse.emf.emfstore.client.test.FilteredSuite.FilteredSuiteParameter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for all fuzzy tests.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FilteredSuite.class)
@FilteredSuiteParameter({ "runFuzzyTests" })
@Suite.SuiteClasses({ OperationApplyTest.class })
public class AllFuzzyTests {

}