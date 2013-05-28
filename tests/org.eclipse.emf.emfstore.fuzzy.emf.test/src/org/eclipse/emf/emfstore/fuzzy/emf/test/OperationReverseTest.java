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

import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Fuzzy Test for the reverse functionality of operations.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FuzzyRunner.class)
@DataProvider(EMFDataProvider.class)
public class OperationReverseTest extends FuzzyProjectTest {

	/***/
	@Test
	public void reverseTest() {
		final ProjectSpaceBase projectSpace = (ProjectSpaceBase) getProjectSpace();
		final ModelMutatorConfiguration mmc = getModelMutatorConfiguration(projectSpace.getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getUtil().mutate(mmc);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectSpace.getLocalChangePackage().reverse().apply(projectSpace.getProject());
			}
		}.run(false);

		compareIgnoreOrder(getCopyProjectSpace().getProject(), projectSpace.getProject());
	}
}