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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;

/**
 * Abstract TestRunProvider to provide the {@link TestRun}s needed in the
 * {@link DiffGenerator}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public abstract class TestRunProvider {

	/**
	 * The config for which to provide testruns.
	 */
	private TestConfig config;

	/**
	 * @return The two {@link TestRun}s specified by the config and this
	 *         provider.
	 * @throws IOException
	 *             If there is an error reading the {@link TestRun}s.
	 */
	public abstract TestRun[] getTestRuns() throws IOException;

	/**
	 * @param config
	 *            The new {@link TestConfig} to use.
	 */
	public void setConfig(TestConfig config) {
		this.config = config;
	}

	/**
	 * @param resource
	 *            The {@link Resource} where to get a {@link TestRun} out.
	 * @return The {@link TestRun} out of the {@link Resource}.
	 */
	protected TestRun getTestRun(Resource resource) {
		for (EObject obj : resource.getContents()) {
			if (obj instanceof TestRun) {
				return (TestRun) obj;
			}
		}
		throw new RuntimeException("Could not load TestRuns from config!");
	}

	/**
	 * @return The current {@link TestConfig}.
	 */
	protected TestConfig getTestConfig() {
		return config;
	}
}
