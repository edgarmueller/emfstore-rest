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
package org.eclipse.emf.emfstore.fuzzy.emf.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Data;
import org.eclipse.emf.emfstore.fuzzy.Annotations.DataProvider;
import org.eclipse.emf.emfstore.fuzzy.Annotations.Util;
import org.eclipse.emf.emfstore.fuzzy.FuzzyRunner;
import org.eclipse.emf.emfstore.fuzzy.emf.EMFDataProvider;
import org.eclipse.emf.emfstore.fuzzy.emf.MutateUtil;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutator;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test to test the {@link ModelMutator}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
@RunWith(FuzzyRunner.class)
@DataProvider(EMFDataProvider.class)
public class MutatorTest {

	@SuppressWarnings("unused")
	@Data
	private EObject obj;

	@Util
	private MutateUtil util;

	/**
	 * Tests if two generated models are equal.
	 */
	@Test
	public void compareTwoGeneratedProjects() {

		Project project1 = ModelFactory.eINSTANCE.createProject();
		Project project2 = ModelFactory.eINSTANCE.createProject();
		ModelMutator.generateModel(getConfig(project1));
		ModelMutator.generateModel(getConfig(project2));

		ModelMutator.changeModel(getConfig(project1));
		ModelMutator.changeModel(getConfig(project2));

		Iterator<EObject> project1Iterator = project1.getAllModelElements()
			.iterator();
		Iterator<EObject> project2Iterator = project2.getAllModelElements()
			.iterator();

		while (project1Iterator.hasNext()) {
			EObject modelElement = project1Iterator.next();
			ModelElementId modelElementId = project1
				.getModelElementId(modelElement);
			if (!project2.contains(modelElementId)) {
				failed(project1, project2);
			}
		}

		TreeIterator<EObject> allContentsProject1 = project1.eAllContents();
		TreeIterator<EObject> allContentsProject2 = project2.eAllContents();

		while (allContentsProject1.hasNext()) {
			if (!allContentsProject2.hasNext()) {
				failed(project1, project2);
			}
			EObject modelElement = allContentsProject1.next();
			ModelElementId modelElementId = project1
				.getModelElementId(modelElement);
			EObject modelElement2 = allContentsProject2.next();
			ModelElementId modelElementId2 = project2
				.getModelElementId(modelElement2);
			if (!modelElementId.equals(modelElementId2)) {
				failed(project1, project2);
			}
		}

		project1Iterator = project1.getAllModelElements().iterator();
		project2Iterator = project2.getAllModelElements().iterator();

		while (project1Iterator.hasNext()) {
			EObject modelElement = project1Iterator.next();
			ModelElementId modelElementId = project1
				.getModelElementId(modelElement);
			ModelElementId modelElementId2 = project2
				.getModelElementId(project2Iterator.next());
			if (!modelElementId.equals(modelElementId2)) {
				failed(project1, project2);
			}
		}
	}

	private void failed(Project project1, Project project2) {
		util.saveEObject(project1, "original_project", true);
		util.saveEObject(project2, "own_project", true);
		Assert.assertTrue(false);
	}

	private ModelMutatorConfiguration getConfig(Project root) {
		ModelMutatorConfiguration mmc = new ModelMutatorConfiguration(
			util.getEPackages(), root, util.getSeed());
		Collection<EStructuralFeature> eStructuralFeaturesToIgnore = new HashSet<EStructuralFeature>();
		eStructuralFeaturesToIgnore
			.add(ModelPackage.Literals.PROJECT__CUT_ELEMENTS);
		mmc.seteStructuralFeaturesToIgnore(eStructuralFeaturesToIgnore);
		mmc.setMinObjectsCount(util.getMinObjectsCount());
		return mmc;
	}
}
