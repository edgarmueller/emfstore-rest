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
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Util;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.fuzzy.emf.MutateUtil;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
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
public class ServerTest extends ESTestWithLoggedInUser {

	@Data
	private Project project;

	@Util
	private MutateUtil util;

	/**
	 * Setup the needed projectspace.
	 */
	@Before
	public void setupProjectSpace() {
		super.before();
		project = getProject();
	}

	/**
	 * @throws ESException
	 *             Problems with share, checkout, commit or update.
	 */
	@Test
	public void shareCheckoutCommitUpdate() throws ESException {

		ProjectUtil.share(getUsersession(), getLocalProject());
		// share original project
		final PrimaryVersionSpec versionSpec = getProjectSpace().getBaseVersion();

		final ESLocalProjectImpl checkout = (ESLocalProjectImpl) ProjectUtil.checkout(getLocalProject());

		// compare original and checkedout project
		FuzzyProjectTest.compareIgnoreOrder(getProject(),
			checkout.toInternalAPI().getProject(), util);

		// change & commit original project
		final ModelMutatorConfiguration mmc = FuzzyProjectTest
			.getModelMutatorConfiguration(getProject(), util);

		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				util.mutate(mmc);
			}
		});

		ProjectUtil.commit(getLocalProject());

		// update checkedout project
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				try {
					checkout.update(new NullProgressMonitor());
				} catch (final ESException e) {
					throw new RuntimeException(e);
				}
			}
		});

		// compare original and updated project
		FuzzyProjectTest.compareIgnoreOrder(getProject(), checkout.toInternalAPI().getProject(), util);
	}
}
