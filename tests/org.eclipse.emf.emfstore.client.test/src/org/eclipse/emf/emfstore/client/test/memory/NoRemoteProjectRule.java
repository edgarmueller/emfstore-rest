/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.memory;

import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.rules.ExternalResource;

/**
 * JUnit Rule for ensuring no remote projects on an EMFStore.
 */
public class NoRemoteProjectRule extends ExternalResource {

	private RunningEMFStoreRule rule;

	/**
	 * Instantiates a new no remote project rule.
	 * 
	 * @param rule the EMFStore rule
	 */
	public NoRemoteProjectRule(RunningEMFStoreRule rule) {
		this.rule = rule;
	}

	@Override
	protected void before() throws IOException, FatalESException, ESException {
		deleteRemoteProjects();
	}

	@Override
	protected void after() {
		try {
			deleteRemoteProjects();
		} catch (IOException e) {
			ModelUtil.logException(e);
		} catch (FatalESException e) {
			ModelUtil.logException(e);
		} catch (ESException e) {
			ModelUtil.logException(e);
		}
	}

	private void deleteRemoteProjects() throws IOException, FatalESException, ESException {
		for (ESRemoteProject project : rule.server().getRemoteProjects()) {
			project.delete(rule.defaultSession(), new NullProgressMonitor());
		}
	}
}
