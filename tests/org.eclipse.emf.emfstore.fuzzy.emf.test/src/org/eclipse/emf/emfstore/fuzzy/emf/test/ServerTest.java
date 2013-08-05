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
import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Util;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.fuzzy.emf.MutateUtil;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
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
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				setProjectSpace(((WorkspaceImpl) ESWorkspaceProvider.INSTANCE
					.getWorkspace()).importProject(project, "", ""));
			}
		}.run(false);
		setProject(project);
	}

	/**
	 * @throws EmfStoreException
	 *             Problems with share, checkout, commit or update.
	 */
	@Test
	public void shareCheckoutCommitUpdate() throws ESException {

		ProjectSpace projectSpace = getProjectSpace();

		// share original project
		PrimaryVersionSpec versionSpec = share(projectSpace);

		// checkout project
		final ProjectSpace psCheckedout = checkout(projectSpace.toAPI()
			.getRemoteProject(), versionSpec);

		// compare original and checkedout project
		FuzzyProjectTest.compareIgnoreOrder(projectSpace.getProject(),
			psCheckedout.getProject(), util);

		// change & commit original project
		final ModelMutatorConfiguration mmc = FuzzyProjectTest
			.getModelMutatorConfiguration(projectSpace.getProject(), util);
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				util.mutate(mmc);
			}
		}.run(false);

		commit(projectSpace);

		// update checkedout project
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					psCheckedout.update(new NullProgressMonitor());
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// compare original and updated project
		FuzzyProjectTest.compareIgnoreOrder(projectSpace.getProject(),
			psCheckedout.getProject(), util);
	}
}
