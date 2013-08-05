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

import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test which mutates a project and then applies its changes to another (copied)
 * project. Then compares the two mutated projects.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FuzzyRunner.class)
@DataProvider(EMFDataProvider.class)
public class OperationApplyTest extends FuzzyProjectTest {

	/***/
	@Test
	public void applyTest() {

		final ProjectSpace projectSpace = getProjectSpace();
		final ModelMutatorConfiguration mmc = getModelMutatorConfiguration(projectSpace
			.getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getUtil().mutate(mmc);
			}
		}.run(false);

		final ProjectSpace copyProjectSpace = getCopyProjectSpace();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				((ProjectSpaceBase) copyProjectSpace).applyOperations(
					projectSpace.getOperations(), false);
			}
		}.run(false);

		compareIgnoreOrder(projectSpace.getProject(),
			copyProjectSpace.getProject());
	}
}
