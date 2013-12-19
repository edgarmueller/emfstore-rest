/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Julian Sommerfeldt - initial API and implementation
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
 * Class used as JUnit plugin test to create {@link org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport DiffReport}s
 * from a {@link TestRunProvider}.
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

		final DiffGenerator diffGenerator = new DiffGenerator();

		try {
			final HudsonTestRunProvider runProvider = new HudsonTestRunProvider();
			for (final TestConfig config : runProvider.getAllConfigs()) {
				runProvider.setConfig(config);
				final TestRun[] runs = runProvider.getTestRuns();
				diffGenerator.createDiff(runs[0], runs[1]);
			}
		} catch (final DocumentException e) {
			throw new RuntimeException(Messages.CouldNotCreateDiffs, e);
		} catch (final IOException e) {
			throw new RuntimeException(Messages.CouldNotCreateDiffs, e);
		}
	}
}
