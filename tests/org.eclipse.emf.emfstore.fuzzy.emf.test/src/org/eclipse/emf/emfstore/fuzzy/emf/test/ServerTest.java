/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf.test;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.server.api.CoreServerTest;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Util;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.fuzzy.emf.MutateUtil;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorConfiguration;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Fuzzy Server test to test share, checkout, commit and update.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FuzzyRunner.class)
@DataProvider(EMFDataProvider.class)
public class ServerTest extends CoreServerTest {

	@Data
	private Project project;

	@Util
	private MutateUtil util;

	/**
	 * Setup the needed projectspace.
	 */
	@Before
	public void setupProjectSpace() {
		CommonUtil.setTesting(true);
		Configuration.getClientBehavior().setAutoSave(false);

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				final ESWorkspaceImpl workspaceImpl = (ESWorkspaceImpl) ESWorkspaceProvider.INSTANCE.getWorkspace();
				setProjectSpace(workspaceImpl.toInternalAPI().importProject(project, "", ""));
			}
		});

		setProject(project);
	}

	/**
	 * @throws ESException
	 *             Problems with share, checkout, commit or update.
	 */
	@Test
	public void shareCheckoutCommitUpdate() throws ESException {

		final ProjectSpace projectSpace = getProjectSpace();

		// share original project
		final PrimaryVersionSpec versionSpec = share(projectSpace);

		// checkout project
		final ProjectSpace psCheckedout = checkout(projectSpace.toAPI()
			.getRemoteProject(), versionSpec);

		// compare original and checkedout project
		FuzzyProjectTest.compareIgnoreOrder(projectSpace.getProject(),
			psCheckedout.getProject(), util);

		// change & commit original project
		final ModelMutatorConfiguration mmc = FuzzyProjectTest
			.getModelMutatorConfiguration(projectSpace.getProject(), util);

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				util.mutate(mmc);
			}
		});

		commit(projectSpace);

		// update checkedout project
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				try {
					psCheckedout.update(new NullProgressMonitor());
				} catch (final ESException e) {
					throw new RuntimeException(e);
				}
			}
		});

		// compare original and updated project
		FuzzyProjectTest.compareIgnoreOrder(projectSpace.getProject(),
			psCheckedout.getProject(), util);
	}
}
