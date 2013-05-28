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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Util;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.fuzzy.emf.MutateUtil;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutator;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorConfiguration;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test to run bigger {@link org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorConfiguration}s.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FuzzyRunner.class)
@DataProvider(EMFDataProvider.class)
public class BigMutationTest {

	@Data
	private EObject root;

	@Util
	private MutateUtil util;

	/***/
	@Test
	public void createModel() {
		System.out.println(ModelMutatorUtil.getAllObjectsCount(root));

		System.out.println("CHANGE");

		ModelMutatorConfiguration config = new ModelMutatorConfiguration(util.getEPackages(), root, 1L);
		config.setMinObjectsCount(util.getMinObjectsCount());
		ModelMutator.changeModel(config);

		System.out.println(ModelMutatorUtil.getAllObjectsCount(root));
	}
}