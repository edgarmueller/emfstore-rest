/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf.diff;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.eclipse.emf.emfstore.client.test.FilteredSuite;
import org.eclipse.emf.emfstore.client.test.FilteredSuite.FilteredSuiteParameter;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Class used as junit plugin test to create
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport}s from an
 * {@link TestRunProvider}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FilteredSuite.class)
@FilteredSuiteParameter({ "createDiffs" })
@SuiteClasses({ CreateAllDiffs.class })
public class CreateAllDiffs {

	/**
	 * Creates all {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff}s
	 * from an {@link TestRunProvider}.
	 */
	@Test
	public void createAllDiffs() {

		DiffGenerator diffGenerator = new DiffGenerator();

		try {
			HudsonTestRunProvider runProvider = new HudsonTestRunProvider();
			for (TestConfig config : runProvider.getAllConfigs()) {
				runProvider.setConfig(config);
				TestRun[] runs = runProvider.getTestRuns();
				diffGenerator.createDiff(runs[0], runs[1]);
			}
		} catch (DocumentException e) {
			throw new RuntimeException("Could not create diffs.", e);
		} catch (IOException e) {
			throw new RuntimeException("Could not create diffs.", e);
		}
	}
}
