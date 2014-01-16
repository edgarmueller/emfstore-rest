/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.recording.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.emfstore.client.observer.ESPostCreationObserver;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.client.test.common.dsl.TestElementFeatures;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.UnsupportedNotificationException;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Test creating an deleting elements.
 * 
 * @author koegel
 */
public class CreateDeleteOperationTest extends ESTest {

	private static final String POST_CREATION_CHANGED_NAME = "postCreationChangedName"; //$NON-NLS-1$
	private static final String OLD_NAME = "oldName"; //$NON-NLS-1$
	private static final String REF_TEST_ELEMENT = "refTestElement"; //$NON-NLS-1$
	private static final String TEST_ELEMENT3 = "TestElement3"; //$NON-NLS-1$
	private static final String TEST_ELEMENT2 = "TestElement2"; //$NON-NLS-1$
	private static final String TEST_ELEMENT1 = "TestElement1"; //$NON-NLS-1$
	private static final String HELLO = "Hello"; //$NON-NLS-1$
	private static final String HALLO = "Hallo"; //$NON-NLS-1$
	private static final String TAG = "Tag"; //$NON-NLS-1$
	private static final String DAY = "Day"; //$NON-NLS-1$
	private static final String REFERENCED_TEST_ELEMENT = "referencedTestElement"; //$NON-NLS-1$
	private static final String KEY_REFEFERENCE_TEST_ELEMENT = "keyRefeferenceTestElement"; //$NON-NLS-1$
	private static final String SUB_TEST_ELEMENT = "subTestElement"; //$NON-NLS-1$
	private static final String REFERENCES = "references"; //$NON-NLS-1$
	private static final String NEW_CHILD_ELEMENT3 = "newChildElement3"; //$NON-NLS-1$
	private static final String NEW_CHILD_ELEMENT2 = "newChildElement2"; //$NON-NLS-1$
	private static final String NEW_CHILD_ELEMENT1 = "newChildElement1"; //$NON-NLS-1$
	private static final String NEW_TEST_ELEMENT = "newTestElement"; //$NON-NLS-1$
	private static final String TEST1 = "test1"; //$NON-NLS-1$
	private static final String PARENT = "parent"; //$NON-NLS-1$
	private static final String TEST2 = "test2"; //$NON-NLS-1$
	private static final String CLAZZ2 = "clazz"; //$NON-NLS-1$
	private static final String ATTRIBUTE22 = "attribute2"; //$NON-NLS-1$
	private static final String ATTRIBUTE1 = "attribute1"; //$NON-NLS-1$
	private static final String MAX_TU_DIES = "Max tu dies"; //$NON-NLS-1$
	private static final String HELMUT = "Helmut"; //$NON-NLS-1$
	private static final String REFERENCE_TEST_ELEMENT = "referenceTestElement"; //$NON-NLS-1$
	private static final String SECOND_TEST_ELEMENT = "secondTestElement"; //$NON-NLS-1$
	private static final String TEST_ELEMENT = "testElement"; //$NON-NLS-1$
	private static final String PARENT_TEST_ELEMENT = "parentTestElement"; //$NON-NLS-1$
	private static final String LEAF_SECTION = "container"; //$NON-NLS-1$
	private static final String MODEL_ELEMENTS = TestElementFeatures.containedElements().getName();
	private static final String PARTICIPATING_ACTORS = "nonContained_NToM"; //$NON-NLS-1$
	private static final String PARTICIPATED_USE_CASES = "nonContained_MToN"; //$NON-NLS-1$
	private static final String INITIATING_ACTOR = "nonContained_NTo1"; //$NON-NLS-1$
	private static final String INITIATED_USE_CASES = "nonContained_1ToN"; //$NON-NLS-1$

	/**
	 * Test element creation tracking.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void createElementTest() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(useCaseId, createDeleteOperation.getModelElementId());
		assertEquals(0, createDeleteOperation.getSubOperations().size());
		assertFalse(createDeleteOperation.isDelete());
	}

	/**
	 * Test element creation tracking.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void createElementWithPostCreationObserverTest() throws UnsupportedOperationException,
		UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		useCase.setName(OLD_NAME);
		final ESPostCreationObserver observer = new ESPostCreationObserver() {

			public void onCreation(EObject modelElement) {
				if (modelElement instanceof TestElement) {
					final TestElement useCase = (TestElement) modelElement;
					useCase.setName(POST_CREATION_CHANGED_NAME);
				}
			}
		};
		ESWorkspaceProviderImpl.getObserverBus().register(observer);
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
			}
		}.run(false);

		ESWorkspaceProviderImpl.getObserverBus().unregister(observer);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		assertEquals(useCaseId, createDeleteOperation.getModelElementId());
		assertEquals(POST_CREATION_CHANGED_NAME, ((TestElement) createDeleteOperation.getModelElement()).getName());
		assertEquals(0, createDeleteOperation.getSubOperations().size());
		assertFalse(createDeleteOperation.isDelete());
	}

	/**
	 * Test adding an element with cross references to an existing element.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void createElementwithCrossreferencesTest() throws UnsupportedOperationException,
		UnsupportedNotificationException {
		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
			}
		}.run(false);

		assertTrue(getProject().contains(useCase));
		assertEquals(getProject(), ModelUtil.getProject(useCase));

		final TestElement functionalRequirement = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();
				useCase.getNonContained_NToM().add(functionalRequirement);
			}
		}.run(false);

		assertEquals(functionalRequirement, useCase.getNonContained_NToM().get(0));
		assertEquals(useCase, functionalRequirement.getNonContained_MToN().get(0));

		assertTrue(getProject().contains(functionalRequirement));
		assertEquals(getProject(), ModelUtil.getProject(functionalRequirement));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;

		final ModelElementId funtionalRQID = ModelUtil.getProject(useCase).getModelElementId(functionalRequirement);

		assertEquals(funtionalRQID, createDeleteOperation.getModelElementId());
		assertEquals(2, createDeleteOperation.getSubOperations().size());
		assertFalse(createDeleteOperation.isDelete());

		final MultiReferenceOperation subOperation1 = (MultiReferenceOperation) createDeleteOperation
			.getSubOperations().get(
				0);
		assertEquals(functionalRequirement, getProject().getModelElement(subOperation1.getModelElementId()));
		assertEquals(useCase, getProject().getModelElement(subOperation1.getReferencedModelElements().get(0)));

		final MultiReferenceOperation subOperation2 = (MultiReferenceOperation) createDeleteOperation
			.getSubOperations().get(
				1);
		assertEquals(useCase, getProject().getModelElement(subOperation2.getModelElementId()));
		assertEquals(functionalRequirement,
			getProject().getModelElement(subOperation2.getReferencedModelElements().get(0)));
	}

	/**
	 * check element deletion tracking.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	@Test
	public void deleteElementTest() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				clearOperations();
			}
		}.run(false);

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getLocalChangePackage().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;

		assertEquals(useCaseId, createDeleteOperation.getModelElementId());
		assertEquals(0, createDeleteOperation.getSubOperations().size());
		assertTrue(createDeleteOperation.isDelete());
	}

	/**
	 * check complex element deletion tracking.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 * @throws IOException
	 */
	@Test
	// BEGIN COMPLEX CODE
	public void complexDeleteElementTest() throws UnsupportedOperationException, UnsupportedNotificationException,
		IOException {

		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement newTestElement = Create.testElement();
		final TestElement otherTestElement = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getContainedElements().add(useCase);
				section.getContainedElements().add(oldTestElement);
				getProject().addModelElement(newTestElement);
				getProject().addModelElement(otherTestElement);
				useCase.setNonContained_NTo1(oldTestElement);
				useCase.getNonContained_NToM().add(newTestElement);
				useCase.getNonContained_NToM().add(otherTestElement);
				assertTrue(getProject().contains(useCase));
				assertEquals(getProject(), ModelUtil.getProject(useCase));
				clearOperations();
			}
		}.run(false);

		final ModelElementId useCaseId = getProject().getModelElementId(useCase);
		Delete.fromProject(getLocalProject(), useCase);

		assertFalse(getProject().contains(useCase));

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());

		EList<ReferenceOperation> subOperations = checkAndCast(operations.get(0), CreateDeleteOperation.class)
			.getSubOperations();

		assertEquals(8, subOperations.size());

		MultiReferenceOperation mrSubOperation0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		SingleReferenceOperation srSubOperation1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		MultiReferenceOperation mrSubOperation2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);
		MultiReferenceOperation mrSubOperation3 = checkAndCast(subOperations.get(3), MultiReferenceOperation.class);
		MultiReferenceOperation mrSubOperation4 = checkAndCast(subOperations.get(4), MultiReferenceOperation.class);
		MultiReferenceOperation mrSubOperation5 = checkAndCast(subOperations.get(5), MultiReferenceOperation.class);
		SingleReferenceOperation srSubOperation6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		MultiReferenceOperation mrSubOperation7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);

		assertEquals(INITIATED_USE_CASES, mrSubOperation0.getFeatureName());
		assertEquals(0, mrSubOperation0.getIndex());

		final ModelElementId oldTestElementId = ModelUtil.getProject(oldTestElement).getModelElementId(oldTestElement);
		final ModelElementId otherTestElementId = ModelUtil.getProject(otherTestElement).getModelElementId(
			otherTestElement);
		final ModelElementId newTestElementId = ModelUtil.getProject(newTestElement).getModelElementId(newTestElement);
		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		assertEquals(oldTestElementId, mrSubOperation0.getModelElementId());
		assertEquals(INITIATING_ACTOR, mrSubOperation0.getOppositeFeatureName());
		assertFalse(mrSubOperation0.isAdd());
		assertTrue(mrSubOperation0.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements0 = mrSubOperation0.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements0.size());
		EList<ModelElementId> referencedModelElements0 = mrSubOperation0.getReferencedModelElements();
		assertEquals(1, referencedModelElements0.size());
		assertEquals(useCaseId, referencedModelElements0.get(0));

		assertEquals(oldTestElementId, srSubOperation1.getOldValue());
		assertEquals(null, srSubOperation1.getNewValue());
		assertEquals(INITIATING_ACTOR, srSubOperation1.getFeatureName());
		assertEquals(useCaseId, srSubOperation1.getModelElementId());
		assertEquals(INITIATED_USE_CASES, srSubOperation1.getOppositeFeatureName());
		assertTrue(srSubOperation1.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements = srSubOperation1.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(oldTestElementId, otherInvolvedModelElements.iterator().next());

		assertEquals(newTestElementId, mrSubOperation2.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation2.getFeatureName());
		assertFalse(mrSubOperation2.isAdd());
		assertEquals(1, mrSubOperation2.getReferencedModelElements().size());
		assertEquals(useCaseId, mrSubOperation2.getReferencedModelElements().get(0));

		assertEquals(useCaseId, mrSubOperation3.getModelElementId());
		assertEquals(PARTICIPATING_ACTORS, mrSubOperation3.getFeatureName());
		assertFalse(mrSubOperation3.isAdd());
		assertEquals(1, mrSubOperation3.getReferencedModelElements().size());
		assertEquals(newTestElementId, mrSubOperation3.getReferencedModelElements().get(0));

		assertEquals(otherTestElementId, mrSubOperation4.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation4.getFeatureName());
		assertFalse(mrSubOperation4.isAdd());
		assertEquals(1, mrSubOperation4.getReferencedModelElements().size());
		assertEquals(useCaseId, mrSubOperation4.getReferencedModelElements().get(0));

		assertEquals(PARTICIPATING_ACTORS, mrSubOperation5.getFeatureName());
		assertEquals(0, mrSubOperation5.getIndex());
		assertEquals(useCaseId, mrSubOperation5.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation5.getOppositeFeatureName());
		assertFalse(mrSubOperation5.isAdd());
		assertTrue(mrSubOperation5.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements2 = mrSubOperation5.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements2.size());
		EList<ModelElementId> referencedModelElements = mrSubOperation5.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(otherTestElementId, referencedModelElements.get(0));

		assertEquals(useCaseId, srSubOperation6.getModelElementId());
		assertEquals(LEAF_SECTION, srSubOperation6.getFeatureName());
		assertEquals(sectionId, srSubOperation6.getOldValue());
		assertEquals(null, srSubOperation6.getNewValue());

		assertEquals(MODEL_ELEMENTS, mrSubOperation7.getFeatureName());
		assertEquals(0, mrSubOperation7.getIndex());
		assertEquals(sectionId, mrSubOperation7.getModelElementId());
		assertEquals(LEAF_SECTION, mrSubOperation7.getOppositeFeatureName());
		assertFalse(mrSubOperation7.isAdd());
		assertTrue(mrSubOperation7.isBidirectional());
		Set<ModelElementId> otherInvolvedModelElements3 = mrSubOperation7.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements3.size());
		EList<ModelElementId> referencedModelElements3 = mrSubOperation7.getReferencedModelElements();
		assertEquals(1, referencedModelElements3.size());
		assertEquals(useCaseId, referencedModelElements3.get(0));

		getProjectSpace().save();
		final ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(
			ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace().eResource().getURI(), false);
		final Project loadedProject = loadedProjectSpace.getProject();

		assertFalse(loadedProject.contains(useCase));
		operations = loadedProjectSpace.getLocalChangePackage().getOperations();

		assertEquals(1, operations.size());
		final CreateDeleteOperation createDeleteOperation = checkAndCast(operations.get(0), CreateDeleteOperation.class);
		assertTrue(createDeleteOperation.isDelete());

		assertEquals(useCaseId, createDeleteOperation.getModelElementId());
		subOperations = createDeleteOperation.getSubOperations();

		mrSubOperation0 = checkAndCast(subOperations.get(0), MultiReferenceOperation.class);
		srSubOperation1 = checkAndCast(subOperations.get(1), SingleReferenceOperation.class);
		mrSubOperation2 = checkAndCast(subOperations.get(2), MultiReferenceOperation.class);
		mrSubOperation3 = checkAndCast(subOperations.get(3), MultiReferenceOperation.class);
		mrSubOperation4 = checkAndCast(subOperations.get(4), MultiReferenceOperation.class);
		mrSubOperation5 = checkAndCast(subOperations.get(5), MultiReferenceOperation.class);
		srSubOperation6 = checkAndCast(subOperations.get(6), SingleReferenceOperation.class);
		mrSubOperation7 = checkAndCast(subOperations.get(7), MultiReferenceOperation.class);

		assertEquals(INITIATED_USE_CASES, mrSubOperation0.getFeatureName());
		assertEquals(0, mrSubOperation0.getIndex());

		assertEquals(oldTestElementId, mrSubOperation0.getModelElementId());
		assertEquals(INITIATING_ACTOR, mrSubOperation0.getOppositeFeatureName());
		assertFalse(mrSubOperation0.isAdd());
		assertTrue(mrSubOperation0.isBidirectional());
		otherInvolvedModelElements0 = mrSubOperation0.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements0.size());
		referencedModelElements0 = mrSubOperation0.getReferencedModelElements();
		assertEquals(1, referencedModelElements0.size());
		assertEquals(useCaseId, referencedModelElements0.get(0));

		assertEquals(oldTestElementId, srSubOperation1.getOldValue());
		assertEquals(null, srSubOperation1.getNewValue());
		assertEquals(INITIATING_ACTOR, srSubOperation1.getFeatureName());
		assertEquals(useCaseId, srSubOperation1.getModelElementId());
		assertEquals(INITIATED_USE_CASES, srSubOperation1.getOppositeFeatureName());
		assertTrue(srSubOperation1.isBidirectional());
		otherInvolvedModelElements = srSubOperation1.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(oldTestElementId, otherInvolvedModelElements.iterator().next());

		assertEquals(newTestElementId, mrSubOperation2.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation2.getFeatureName());
		assertFalse(mrSubOperation2.isAdd());
		assertEquals(1, mrSubOperation2.getReferencedModelElements().size());
		assertEquals(useCaseId, mrSubOperation2.getReferencedModelElements().get(0));

		assertEquals(useCaseId, mrSubOperation3.getModelElementId());
		assertEquals(PARTICIPATING_ACTORS, mrSubOperation3.getFeatureName());
		assertFalse(mrSubOperation3.isAdd());
		assertEquals(1, mrSubOperation3.getReferencedModelElements().size());
		assertEquals(newTestElementId, mrSubOperation3.getReferencedModelElements().get(0));

		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation4.getFeatureName());
		assertEquals(0, mrSubOperation4.getIndex());
		assertEquals(otherTestElementId, mrSubOperation4.getModelElementId());
		assertEquals(PARTICIPATING_ACTORS, mrSubOperation4.getOppositeFeatureName());
		assertFalse(mrSubOperation4.isAdd());
		assertTrue(mrSubOperation4.isBidirectional());
		otherInvolvedModelElements2 = mrSubOperation4.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements2.size());
		referencedModelElements = mrSubOperation4.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));

		assertEquals(PARTICIPATING_ACTORS, mrSubOperation5.getFeatureName());
		assertEquals(0, mrSubOperation5.getIndex());
		assertEquals(useCaseId, mrSubOperation5.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation5.getOppositeFeatureName());
		assertFalse(mrSubOperation5.isAdd());
		assertTrue(mrSubOperation5.isBidirectional());
		otherInvolvedModelElements2 = mrSubOperation5.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements2.size());
		referencedModelElements = mrSubOperation5.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(otherTestElementId, referencedModelElements.get(0));

		assertEquals(useCaseId, srSubOperation6.getModelElementId());
		assertEquals(LEAF_SECTION, srSubOperation6.getFeatureName());
		assertEquals(sectionId, srSubOperation6.getOldValue());
		assertNull(srSubOperation6.getNewValue());

		assertEquals(MODEL_ELEMENTS, mrSubOperation7.getFeatureName());
		assertEquals(0, mrSubOperation7.getIndex());
		assertEquals(sectionId, mrSubOperation7.getModelElementId());
		assertEquals(LEAF_SECTION, mrSubOperation7.getOppositeFeatureName());
		assertFalse(mrSubOperation7.isAdd());
		assertTrue(mrSubOperation7.isBidirectional());
		otherInvolvedModelElements3 = mrSubOperation7.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements3.size());
		referencedModelElements3 = mrSubOperation7.getReferencedModelElements();
		assertEquals(1, referencedModelElements3.size());
		assertEquals(useCaseId, referencedModelElements3.get(0));
	}

	/**
	 * check complex element deletion tracking.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 * @throws IOException
	 */
	@Test
	public void complexDeleteElementReverseTest() throws UnsupportedOperationException,
		UnsupportedNotificationException, IOException {
		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement newTestElement = Create.testElement();
		final TestElement otherTestElement = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getContainedElements().add(useCase);
				section.getContainedElements().add(oldTestElement);
				getProject().addModelElement(newTestElement);
				getProject().addModelElement(otherTestElement);
				useCase.setNonContained_NTo1(oldTestElement);
				useCase.getNonContained_NToM().add(newTestElement);
				useCase.getNonContained_NToM().add(otherTestElement);
				assertTrue(getProject().contains(useCase));
				assertTrue(getProject().contains(oldTestElement));
				assertTrue(getProject().contains(newTestElement));
				assertTrue(getProject().contains(otherTestElement));
				assertEquals(1, oldTestElement.getNonContained_1ToN().size());
				assertEquals(1, newTestElement.getNonContained_MToN().size());
				assertEquals(1, otherTestElement.getNonContained_MToN().size());
				assertEquals(useCase, oldTestElement.getNonContained_1ToN().get(0));
				assertEquals(useCase, newTestElement.getNonContained_MToN().get(0));
				assertEquals(useCase, otherTestElement.getNonContained_MToN().get(0));

				clearOperations();
			}
		}.run(false);

		final ModelElementId useCaseId = getProject().getModelElementId(useCase);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		assertFalse(getProject().contains(useCase));
		assertEquals(0, oldTestElement.getNonContained_1ToN().size());
		assertEquals(0, newTestElement.getNonContained_MToN().size());
		assertEquals(0, otherTestElement.getNonContained_MToN().size());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);

		final AbstractOperation reverse = operation.reverse();

		assertTrue(reverse instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) reverse;
		assertFalse(createDeleteOperation.isDelete());

		assertEquals(useCaseId, createDeleteOperation.getModelElementId());
		final EList<ReferenceOperation> subOperations = createDeleteOperation.getSubOperations();

		assertEquals(8, subOperations.size());
		final AbstractOperation subOperation0 = subOperations.get(7);
		final AbstractOperation subOperation1 = subOperations.get(6);
		final AbstractOperation subOperation2 = subOperations.get(5);
		final AbstractOperation subOperation3 = subOperations.get(4);
		final AbstractOperation subOperation4 = subOperations.get(3);
		final AbstractOperation subOperation5 = subOperations.get(2);
		final AbstractOperation subOperation6 = subOperations.get(1);
		final AbstractOperation subOperation7 = subOperations.get(0);

		assertTrue(subOperation0 instanceof MultiReferenceOperation);
		assertTrue(subOperation1 instanceof SingleReferenceOperation);
		assertTrue(subOperation2 instanceof MultiReferenceOperation);
		assertTrue(subOperation3 instanceof MultiReferenceOperation);
		assertTrue(subOperation4 instanceof MultiReferenceOperation);
		assertTrue(subOperation5 instanceof MultiReferenceOperation);
		assertTrue(subOperation6 instanceof SingleReferenceOperation);
		assertTrue(subOperation7 instanceof MultiReferenceOperation);

		final MultiReferenceOperation mrSubOperation0 = (MultiReferenceOperation) subOperation0;
		final SingleReferenceOperation mrSubOperation1 = (SingleReferenceOperation) subOperation1;
		final MultiReferenceOperation mrSubOperation2 = (MultiReferenceOperation) subOperation2;
		final MultiReferenceOperation mrSubOperation3 = (MultiReferenceOperation) subOperation3;
		final MultiReferenceOperation mrSubOperation4 = (MultiReferenceOperation) subOperation4;
		final MultiReferenceOperation mrSubOperation5 = (MultiReferenceOperation) subOperation5;
		final SingleReferenceOperation mrSubOperation6 = (SingleReferenceOperation) subOperation6;
		final MultiReferenceOperation mrSubOperation7 = (MultiReferenceOperation) subOperation7;

		final ModelElementId oldTestElementId = ModelUtil.getProject(oldTestElement).getModelElementId(oldTestElement);
		final ModelElementId newTestElementId = ModelUtil.getProject(newTestElement).getModelElementId(newTestElement);
		final ModelElementId otherTestElementId = ModelUtil.getProject(otherTestElement).getModelElementId(
			otherTestElement);
		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		assertEquals(INITIATED_USE_CASES, mrSubOperation0.getFeatureName());
		assertEquals(0, mrSubOperation0.getIndex());
		assertEquals(oldTestElementId, mrSubOperation0.getModelElementId());
		assertEquals(INITIATING_ACTOR, mrSubOperation0.getOppositeFeatureName());
		assertTrue(mrSubOperation0.isAdd());
		assertTrue(mrSubOperation0.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements0 = mrSubOperation0.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements0.size());
		final EList<ModelElementId> referencedModelElements0 = mrSubOperation0.getReferencedModelElements();
		assertEquals(1, referencedModelElements0.size());
		assertEquals(useCaseId, referencedModelElements0.get(0));

		assertEquals(oldTestElementId, mrSubOperation1.getNewValue());
		assertEquals(null, mrSubOperation1.getOldValue());
		assertEquals(INITIATING_ACTOR, mrSubOperation1.getFeatureName());
		assertEquals(useCaseId, mrSubOperation1.getModelElementId());
		assertEquals(INITIATED_USE_CASES, mrSubOperation1.getOppositeFeatureName());
		assertTrue(mrSubOperation1.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements = mrSubOperation1.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements.size());
		assertEquals(oldTestElementId, otherInvolvedModelElements.iterator().next());

		assertEquals(newTestElementId, mrSubOperation2.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation2.getFeatureName());
		assertTrue(mrSubOperation2.isAdd());
		assertEquals(1, mrSubOperation2.getReferencedModelElements().size());
		assertEquals(useCaseId, mrSubOperation2.getReferencedModelElements().get(0));

		assertEquals(useCaseId, mrSubOperation3.getModelElementId());
		assertEquals(PARTICIPATING_ACTORS, mrSubOperation3.getFeatureName());
		assertTrue(mrSubOperation3.isAdd());
		assertEquals(1, mrSubOperation3.getReferencedModelElements().size());
		assertEquals(newTestElementId, mrSubOperation3.getReferencedModelElements().get(0));

		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation4.getFeatureName());
		assertEquals(0, mrSubOperation4.getIndex());
		assertEquals(otherTestElementId, mrSubOperation4.getModelElementId());
		assertEquals(PARTICIPATING_ACTORS, mrSubOperation4.getOppositeFeatureName());
		assertTrue(mrSubOperation4.isAdd());
		assertTrue(mrSubOperation4.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements2 = mrSubOperation4.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements2.size());
		final EList<ModelElementId> referencedModelElements = mrSubOperation4.getReferencedModelElements();
		assertEquals(1, referencedModelElements.size());
		assertEquals(useCaseId, referencedModelElements.get(0));

		assertEquals(PARTICIPATING_ACTORS, mrSubOperation5.getFeatureName());
		assertEquals(0, mrSubOperation5.getIndex());
		assertEquals(useCaseId, mrSubOperation5.getModelElementId());
		assertEquals(PARTICIPATED_USE_CASES, mrSubOperation5.getOppositeFeatureName());
		assertTrue(mrSubOperation5.isAdd());
		assertTrue(mrSubOperation5.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements3 = mrSubOperation5.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements3.size());
		final EList<ModelElementId> referencedModelElements2 = mrSubOperation5.getReferencedModelElements();
		assertEquals(1, referencedModelElements2.size());
		assertEquals(otherTestElementId, referencedModelElements2.get(0));

		assertEquals(useCaseId, mrSubOperation6.getModelElementId());
		assertEquals(LEAF_SECTION, mrSubOperation6.getFeatureName());
		assertEquals(sectionId, mrSubOperation6.getNewValue());
		assertEquals(null, mrSubOperation6.getOldValue());

		assertEquals(MODEL_ELEMENTS, mrSubOperation7.getFeatureName());
		assertEquals(0, mrSubOperation7.getIndex());
		assertEquals(sectionId, mrSubOperation7.getModelElementId());
		assertEquals(LEAF_SECTION, mrSubOperation7.getOppositeFeatureName());
		assertTrue(mrSubOperation7.isAdd());
		assertTrue(mrSubOperation7.isBidirectional());
		final Set<ModelElementId> otherInvolvedModelElements4 = mrSubOperation7.getOtherInvolvedModelElements();
		assertEquals(1, otherInvolvedModelElements4.size());
		final EList<ModelElementId> referencedModelElements3 = mrSubOperation7.getReferencedModelElements();
		assertEquals(1, referencedModelElements3.size());
		assertEquals(useCaseId, referencedModelElements3.get(0));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				reverse.apply(getProject());
			}
		}.run(false);

		assertTrue(getProject().contains(useCaseId));
		assertTrue(getProject().contains(oldTestElement));
		assertTrue(getProject().contains(newTestElement));
		assertTrue(getProject().contains(otherTestElement));
		assertEquals(1, oldTestElement.getNonContained_1ToN().size());
		assertEquals(1, newTestElement.getNonContained_MToN().size());
		assertEquals(1, otherTestElement.getNonContained_MToN().size());
		final EObject useCaseClone = getProject().getModelElement(useCaseId);
		assertEquals(useCaseClone, oldTestElement.getNonContained_1ToN().get(0));
		assertEquals(useCaseClone, newTestElement.getNonContained_MToN().get(0));
		assertEquals(useCaseClone, otherTestElement.getNonContained_MToN().get(0));

		getProjectSpace().save();

		final Project loadedProject = ModelUtil.loadEObjectFromResource(
			org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.getModelPackage().getProject(),
			getProject()
				.eResource().getURI(), false);

		assertTrue(ModelUtil.areEqual(loadedProject, getProject()));
		assertTrue(loadedProject.contains(useCaseId));
		assertTrue(loadedProject.contains(oldTestElementId));
		assertTrue(loadedProject.contains(newTestElementId));
		assertTrue(loadedProject.contains(otherTestElementId));
	}

	/**
	 * check complex element deletion tracking.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 * @throws IOException
	 */
	@Test
	public void complexCreateTest() throws UnsupportedOperationException, UnsupportedNotificationException, IOException {
		for (int i = 0; i < 10; i++) {
			final TestElement createCompositeSection = Create.testElement();
			createCompositeSection.setName(HELMUT + i);
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					getProject().addModelElement(createCompositeSection);
					final TestElement createLeafSection = Create.testElement();
					createCompositeSection.getContainedElements().add(createLeafSection);

					for (int j = 0; j < 10; j++) {
						final TestElement createActionItem = Create.testElement();
						createActionItem.setName(MAX_TU_DIES + j);
						createLeafSection.getContainedElements().add(createActionItem);
					}
				}
			}.run(false);
		}
		assertEquals(230, getProjectSpace().getOperations().size());

		getProjectSpace().save();
		final ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(
			ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace().eResource().getURI(), false);

		assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), loadedProjectSpace.getProject()));
	}

	/**
	 * Delete a parent with a child contained in a single reference.
	 * 
	 * @throws IOException
	 */
	@Test
	public void deleteWithSingleReferenceChildTest() throws IOException {
		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				issue.setContainedElement(solution);
				getProject().addModelElement(issue);

				assertTrue(getProject().contains(issue));
				assertTrue(getProject().contains(solution));
				assertEquals(solution, issue.getContainedElement());
				assertEquals(issue, solution.getSrefContainer());

				clearOperations();
			}
		}.run(false);

		final ModelElementId solutionId = ModelUtil.getProject(solution).getModelElementId(solution);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(solution);
			}
		}.run(false);

		assertTrue(getProject().contains(issue));
		assertFalse(getProject().contains(solution));
		assertEquals(null, issue.getContainedElement());
		assertEquals(null, solution.getSrefContainer());

		List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
		assertTrue(createDeleteOperation.isDelete());

		assertEquals(solutionId, createDeleteOperation.getModelElementId());

		final EList<ReferenceOperation> subOperations = createDeleteOperation.getSubOperations();
		assertEquals(2, subOperations.size());

		getProjectSpace().save();
		final ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(
			ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace().eResource().getURI(), false);

		// perform asserts with loaded projects
		assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), loadedProjectSpace.getProject()));
		operations = loadedProjectSpace.getOperations();
		assertEquals(1, operations.size());
		operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		createDeleteOperation = (CreateDeleteOperation) operation;
		assertTrue(createDeleteOperation.isDelete());
	}

	/**
	 * Test creation of element with cross references.
	 * 
	 * @throws UnsupportedOperationException on test fail
	 * @throws UnsupportedNotificationException on test fail
	 */
	public void createWithCrossReferencesTest() throws UnsupportedOperationException, UnsupportedNotificationException {

		final TestElement useCase = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase2);
				useCase.getReferences().add(useCase2);
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				assertTrue(getProject().contains(useCase));
				assertTrue(getProject().contains(useCase2));
				assertEquals(1, getProjectSpace().getOperations().size());
				assertTrue(getProjectSpace().getOperations().get(0) instanceof CreateDeleteOperation);
				final CreateDeleteOperation operation = (CreateDeleteOperation) getProjectSpace().getOperations()
					.get(0);
				assertEquals(getProject().getModelElementId(useCase), operation.getModelElementId());
				assertEquals(2, operation.getSubOperations().size());
				assertTrue(operation.getSubOperations().get(0) instanceof MultiReferenceOperation);
			}
		}.run(false);

	}

	/**
	 * Test creating an element in a non project containment.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createInNonProjectContainmentTest() throws IOException {
		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);

				assertTrue(getProject().contains(section));

				clearOperations();

				section.getContainedElements().add(useCase);
			}
		}.run(false);

		assertTrue(getProject().contains(useCase));
		assertTrue(getProject().contains(section));
		assertEquals(1, section.getContainedElements().size());
		assertEquals(section, useCase.getContainer());
		assertEquals(useCase, section.getContainedElements().iterator().next());

		List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(2, operations.size());

		AbstractOperation operation1 = operations.get(0);
		AbstractOperation operation2 = operations.get(1);
		assertTrue(operation1 instanceof CreateDeleteOperation);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		CreateDeleteOperation createOperation = (CreateDeleteOperation) operation1;
		MultiReferenceOperation multiReferenceOperation = (MultiReferenceOperation) operation2;
		assertFalse(createOperation.isDelete());

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

		assertEquals(useCaseId, createOperation.getModelElementId());
		assertEquals(sectionId, multiReferenceOperation.getModelElementId());

		getProjectSpace().save();
		final ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(
			ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace().eResource().getURI(), false);

		// perform asserts with loaded project
		assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), loadedProjectSpace.getProject()));
		operations = loadedProjectSpace.getOperations();
		assertEquals(2, operations.size());

		operation1 = operations.get(0);
		operation2 = operations.get(1);
		assertTrue(operation1 instanceof CreateDeleteOperation);
		assertTrue(operation2 instanceof MultiReferenceOperation);
		createOperation = (CreateDeleteOperation) operation1;
		multiReferenceOperation = (MultiReferenceOperation) operation2;
		assertFalse(createOperation.isDelete());

	}

	@Test
	public void createEAttributes() throws IOException {
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EStructuralFeature attribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EStructuralFeature attribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(ATTRIBUTE1);
		attribute2.setName(ATTRIBUTE22);
		clazz.getEStructuralFeatures().add(attribute);
		clazz.getEStructuralFeatures().add(attribute2);

		assertEquals(2, clazz.eContents().size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(clazz);
			}
		}.run(false);

		getProjectSpace().save();

		final ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(
			ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace().eResource().getURI(), false);

		// perform asserts with loaded project space
		assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), loadedProjectSpace.getProject()));
	}

	@Test
	public void checkPersistentModelElementDeletion() throws IOException {
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		clazz.setName(CLAZZ2);
		final EStructuralFeature attribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EStructuralFeature attribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(ATTRIBUTE1);
		attribute2.setName(ATTRIBUTE22);

		clazz.getEStructuralFeatures().add(attribute2);

		Add.toProject(getLocalProject(), attribute);
		Add.toProject(getLocalProject(), clazz);

		assertEquals(2, getProject().getModelElements().size()); // clazz, attribute
		assertEquals(attribute, getProject().getModelElements().get(0));
		assertEquals(clazz, getProject().getModelElements().get(1));
		assertEquals(3, getProject().getAllModelElements().size()); // clazz, attribute, attribute2

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				clearOperations();
			}
		}.run(false);

		// delete one ModelElement
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				EcoreUtil.delete(attribute);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size()); // one delete operation
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);
		final CreateDeleteOperation operation = (CreateDeleteOperation) operations.get(0);
		assertTrue(operation.isDelete());
		// assertEquals(getProject().getDeletedModelElementId(attribute), operation.getModelElementId());

		assertEquals(1, getProject().getModelElements().size()); // clazz
		assertEquals(clazz, getProject().getModelElements().get(0));
		assertEquals(2, getProject().getAllModelElements().size()); // clazz, attribute2

		// load ProjectSpace from Resource and initialize
		((ProjectSpaceImpl) getProjectSpace()).save();
		final ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(
			ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace().eResource().getURI(), false);
		loadedProjectSpace.init();

		// perform asserts with loaded project space
		assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), loadedProjectSpace.getProject()));
		final Project loadedProject = loadedProjectSpace.getProject();
		assertEquals(1, loadedProject.getModelElements().size()); // clazz
		assertEquals(getProject().getModelElementId(clazz),
			loadedProject.getModelElementId(loadedProject.getModelElements().get(0)));
		assertEquals(2, loadedProject.getAllModelElements().size()); // clazz, attribute2
	}

	@Test
	public void testECoreUtilCopyWithMeetings() {
		// create a meeting with composite and subsections including intra - cross references
		final TestElement compMeetingSection = Create.testElement();
		final TestElement issueMeeting = Create.testElement();
		final TestElement workItemMeetingSecion = Create.testElement();
		compMeetingSection.getContainedElements().add(issueMeeting);
		compMeetingSection.getContainedElements().add(workItemMeetingSecion);
		final TestElement meeting = Create.testElement();
		meeting.getContainedElements().add(compMeetingSection);
		meeting.setReference(issueMeeting);
		meeting.setOtherReference(workItemMeetingSecion);

		// copy meeting and check if the intra cross references were actually copied
		final TestElement copiedMeeting = ModelUtil.clone(meeting);
		assertFalse(copiedMeeting.getReference() == meeting.getReference());
		assertFalse(copiedMeeting.getOtherReference() == meeting.getOtherReference());

		// add original element to project
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(meeting);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());

		final AbstractOperation operation1 = operations.get(0);
		assertTrue(operation1 instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation1;
		assertFalse(createDeleteOperation.isDelete());

		final TestElement meetingSection = (TestElement) createDeleteOperation.getModelElement();
		assertFalse(meeting.getReference() == meetingSection.getReference());

	}

	@Test
	public void testCopyProject() {
		final TestElement compMeetingSection = Create.testElement();
		final TestElement issueMeeting = Create.testElement();
		final TestElement workItemMeetingSecion = Create.testElement();
		compMeetingSection.getContainedElements().add(issueMeeting);
		compMeetingSection.getContainedElements().add(workItemMeetingSecion);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(compMeetingSection);
			}
		}.run(false);

		final ModelElementId compMeetingSectionId = getProject().getModelElementId(compMeetingSection);

		final Project copiedProject = ((ProjectImpl) getProject()).copy();

		final EObject copiedMeetingSection = copiedProject.getModelElement(compMeetingSectionId);

		assertNotNull(copiedMeetingSection);
	}

	@Test
	public void testCreateWithOneIngoingReference() {

		final TestElement parentTestElement = Create.testElement(PARENT);
		final TestElement testElement = Create.testElement(TEST1);
		final TestElement testElement2 = Create.testElement(TEST2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(parentTestElement);
				testElement.getReferences().add(testElement2);
				parentTestElement.getContainedElements().add(testElement);
			}
		}.run(false);

		assertTrue(getProject().contains(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertTrue(getProject().contains(testElement));
		assertEquals(getProject(), ModelUtil.getProject(testElement));
		assertEquals(testElement, parentTestElement.getContainedElements().get(0));
		assertEquals(testElement2, testElement.getReferences().get(0));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				parentTestElement.getContainedElements().add(testElement2);
			}
		}.run(false);

		assertTrue(getProject().contains(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertTrue(getProject().contains(testElement));
		assertEquals(getProject(), ModelUtil.getProject(testElement));
		assertEquals(testElement, parentTestElement.getContainedElements().get(0));
		assertEquals(testElement2, testElement.getReferences().get(0));

		assertTrue(getProject().contains(testElement2));
		assertEquals(getProject(), ModelUtil.getProject(testElement2));
		assertEquals(testElement2, parentTestElement.getContainedElements().get(1));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;

		// ModelElementId testElementId = ModelUtil.getProject(testElement).getModelElementId(testElement);
		final ModelElementId testElement2Id = ModelUtil.getProject(testElement2).getModelElementId(testElement2);
		// ModelElementId parentTestElementId = ModelUtil.getProject(parentTestElement).getModelElementId(
		// parentTestElement);

		assertEquals(testElement2Id, createDeleteOperation.getModelElementId());
		assertEquals(1, createDeleteOperation.getSubOperations().size());
		assertFalse(createDeleteOperation.isDelete());
		assertTrue(CommonUtil.isSelfContained(createDeleteOperation, true));

		final MultiReferenceOperation subOperation1 = (MultiReferenceOperation) createDeleteOperation
			.getSubOperations().get(
				0);

		assertEquals(testElement, getProject().getModelElement(subOperation1.getModelElementId()));
		assertEquals(testElement2, getProject().getModelElement(subOperation1.getReferencedModelElements().get(0)));

		final MultiReferenceOperation operation2 = (MultiReferenceOperation) operations.get(1);

		assertEquals(parentTestElement, getProject().getModelElement(operation2.getModelElementId()));
		assertEquals(testElement2, getProject().getModelElement(operation2.getReferencedModelElements().get(0)));
	}

	@Test
	public void testCreateWithReferencesAndChildrenComplex() {

		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);
		final TestElement testElement = Create.testElement(TEST_ELEMENT);
		final TestElement newTestElement = Create.testElement(NEW_TEST_ELEMENT);
		final TestElement newChildElement1 = Create.testElement(NEW_CHILD_ELEMENT1);
		final TestElement newChildElement2 = Create.testElement(NEW_CHILD_ELEMENT2);
		final TestElement newChildElement3 = Create.testElement(NEW_CHILD_ELEMENT3);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(parentTestElement);
				parentTestElement.getContainedElements().add(testElement);
				newTestElement.getContainedElements().add(newChildElement1);
				newTestElement.getContainedElements().add(newChildElement2);
				newTestElement.getContainedElements().add(newChildElement3);
				newChildElement1.getReferences().add(newTestElement);
				newChildElement2.getReferences().add(newChildElement1);
				newChildElement2.getReferences().add(testElement);
				testElement.getReferences().add(newChildElement3);
			}
		}.run(false);

		assertTrue(getProject().contains(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertTrue(getProject().contains(testElement));
		assertEquals(getProject(), ModelUtil.getProject(testElement));
		assertFalse(getProject().contains(newTestElement));
		assertFalse(getProject().contains(newChildElement1));
		assertFalse(getProject().contains(newChildElement2));
		assertTrue(getProject().contains(newChildElement3));

		assertEquals(2, newTestElement.getContainedElements().size());
		assertEquals(newChildElement1, newTestElement.getContainedElements().get(0));
		assertEquals(newChildElement2, newTestElement.getContainedElements().get(1));

		assertEquals(newTestElement, newChildElement1.getReferences().get(0));
		assertEquals(newChildElement1, newChildElement2.getReferences().get(0));
		assertEquals(testElement, newChildElement2.getReferences().get(1));
		assertEquals(newChildElement3, testElement.getReferences().get(0));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				parentTestElement.getContainedElements().add(newTestElement);
			}
		}.run(false);

		assertTrue(getProject().contains(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertTrue(getProject().contains(testElement));
		assertEquals(getProject(), ModelUtil.getProject(testElement));
		assertTrue(getProject().contains(newTestElement));
		assertTrue(getProject().contains(newChildElement1));
		assertTrue(getProject().contains(newChildElement2));
		assertTrue(getProject().contains(newChildElement3));

		assertEquals(2, newTestElement.getContainedElements().size());
		assertEquals(newChildElement1, newTestElement.getContainedElements().get(0));
		assertEquals(newChildElement2, newTestElement.getContainedElements().get(1));

		assertEquals(newTestElement, newChildElement1.getReferences().get(0));
		assertEquals(newChildElement1, newChildElement2.getReferences().get(0));
		assertEquals(testElement, newChildElement2.getReferences().get(1));
		assertEquals(newChildElement3, testElement.getReferences().get(0));

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;

		final ModelElementId newTestElementId = ModelUtil.getProject(newTestElement).getModelElementId(newTestElement);
		final TestElement copiedNewTestElement = (TestElement) createDeleteOperation.getModelElement();
		final TestElement copiedNewChildElement1 = copiedNewTestElement.getContainedElements().get(0);
		final TestElement copiedNewChildElement2 = copiedNewTestElement.getContainedElements().get(1);

		assertEquals(2, copiedNewTestElement.getContainedElements().size());
		assertEquals(copiedNewTestElement, copiedNewChildElement1.getReferences().get(0));
		assertEquals(copiedNewChildElement1, copiedNewChildElement2.getReferences().get(0));
		assertEquals(1, copiedNewChildElement2.getReferences().size());

		assertEquals(newTestElementId, createDeleteOperation.getModelElementId());
		assertEquals(1, createDeleteOperation.getSubOperations().size());
		assertFalse(createDeleteOperation.isDelete());
		assertTrue(CommonUtil.isSelfContained(createDeleteOperation, true));

		// check sub-operations of 1st operation
		final MultiReferenceOperation subOperation1 = (MultiReferenceOperation) createDeleteOperation
			.getSubOperations().get(
				0);

		// sub-operation 1
		assertEquals(newChildElement2, getProject().getModelElement(subOperation1.getModelElementId()));
		assertEquals(REFERENCES, subOperation1.getFeatureName());
		assertEquals(testElement, getProject().getModelElement(subOperation1.getReferencedModelElements().get(0)));

		// check 2nd operation
		final MultiReferenceOperation operation2 = (MultiReferenceOperation) operations.get(1);

		assertEquals(parentTestElement, getProject().getModelElement(operation2.getModelElementId()));
		assertEquals(newTestElement, getProject().getModelElement(operation2.getReferencedModelElements().get(0)));

		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		final MultiReferenceOperation multiRefOp = (MultiReferenceOperation) operations.get(1);

		assertEquals(parentTestElement, getProject().getModelElement(multiRefOp.getModelElementId()));
		assertEquals(TestElementFeatures.containedElements().getName(), multiRefOp.getFeatureName());
		assertEquals(newTestElement, getProject().getModelElement(multiRefOp.getReferencedModelElements().get(0)));
		assertTrue(multiRefOp.isAdd());
	}

	@Test
	public void testClearContainmentTree() {
		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);
		final TestElement testElement = Create.testElement(TEST_ELEMENT);
		final TestElement subTestElement = Create.testElement(SUB_TEST_ELEMENT);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(parentTestElement);
				parentTestElement.getContainedElements_NoOpposite().add(testElement);
				testElement.getContainedElements_NoOpposite().add(subTestElement);
				clearOperations();
			}
		}.run(false);

		final ModelElementId parentElementId = getProject().getModelElementId(parentTestElement);
		final ModelElementId elementId = getProject().getModelElementId(testElement);
		final ModelElementId subElementId = getProject().getModelElementId(subTestElement);

		assertNotNull(parentElementId);
		assertNotNull(elementId);
		assertNotNull(subElementId);

		assertEquals(1, getProject().getModelElements().size());
		assertEquals(parentTestElement, getProject().getModelElements().get(0));

		assertEquals(1, parentTestElement.getContainedElements_NoOpposite().size());
		assertEquals(testElement, parentTestElement.getContainedElements_NoOpposite().get(0));
		assertEquals(1, testElement.getContainedElements_NoOpposite().size());
		assertEquals(subTestElement, testElement.getContainedElements_NoOpposite().get(0));

		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(testElement));
		assertEquals(getProject(), ModelUtil.getProject(subTestElement));

		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(testElement));
		assertTrue(getProject().contains(subTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getContainedElements_NoOpposite().clear();
				parentTestElement.getContainedElements_NoOpposite().clear();
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(4, operations.size());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProjectSpace().revert();
			}
		}.run(false);

		assertEquals(1, getProject().getModelElements().size());
		assertEquals(parentTestElement, getProject().getModelElements().get(0));

		assertEquals(1, parentTestElement.getContainedElements_NoOpposite().size());
		final TestElement copiedTestElement = parentTestElement.getContainedElements_NoOpposite().get(0);
		assertEquals(1, copiedTestElement.getContainedElements_NoOpposite().size());
		final TestElement copiedSubTestElement = copiedTestElement.getContainedElements_NoOpposite().get(0);

		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(copiedTestElement));
		assertEquals(getProject(), ModelUtil.getProject(copiedSubTestElement));

		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(copiedTestElement));
		assertTrue(getProject().contains(copiedSubTestElement));

		assertEquals(parentElementId, getProject().getModelElementId(parentTestElement));
		assertEquals(elementId, getProject().getModelElementId(copiedTestElement));
		assertEquals(subElementId, getProject().getModelElementId(copiedSubTestElement));
	}

	@Test
	public void testClearContainmentTreeReverse() {
		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);
		final TestElement testElement = Create.testElement(TEST_ELEMENT);
		final TestElement subTestElement = Create.testElement(SUB_TEST_ELEMENT);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(parentTestElement);
				parentTestElement.getContainedElements().add(testElement);
				testElement.getContainedElements().add(subTestElement);
				clearOperations();
			}
		}.run(false);

		final ModelElementId parentElementId = getProject().getModelElementId(parentTestElement);
		final ModelElementId elementId = getProject().getModelElementId(testElement);
		final ModelElementId subElementId = getProject().getModelElementId(subTestElement);

		assertNotNull(parentElementId);
		assertNotNull(elementId);
		assertNotNull(subElementId);

		assertEquals(1, getProject().getModelElements().size());
		assertEquals(parentTestElement, getProject().getModelElements().get(0));

		assertEquals(1, parentTestElement.getContainedElements().size());
		assertEquals(testElement, parentTestElement.getContainedElements().get(0));
		assertEquals(1, testElement.getContainedElements().size());
		assertEquals(subTestElement, testElement.getContainedElements().get(0));

		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(testElement));
		assertEquals(getProject(), ModelUtil.getProject(subTestElement));

		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(testElement));
		assertTrue(getProject().contains(subTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				parentTestElement.getContainedElements().clear();
				testElement.getContainedElements().clear();
			}
		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProjectSpace().revert();
			}
		}.run(false);

		assertEquals(1, getProject().getModelElements().size());
		assertEquals(parentTestElement, getProject().getModelElements().get(0));

		assertEquals(1, parentTestElement.getContainedElements().size());
		final TestElement copiedTestElement = parentTestElement.getContainedElements().get(0);
		assertEquals(1, copiedTestElement.getContainedElements().size());
		final TestElement copiedSubTestElement = copiedTestElement.getContainedElements().get(0);

		assertEquals(getProject(), ModelUtil.getProject(parentTestElement));
		assertEquals(getProject(), ModelUtil.getProject(copiedTestElement));
		assertEquals(getProject(), ModelUtil.getProject(copiedSubTestElement));

		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(copiedTestElement));
		assertTrue(getProject().contains(copiedSubTestElement));

		assertEquals(parentElementId, getProject().getModelElementId(parentTestElement));
		assertEquals(elementId, getProject().getModelElementId(copiedTestElement));
		assertEquals(subElementId, getProject().getModelElementId(copiedSubTestElement));
	}

	@Test
	public void testCreateReverse() {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(1, operations.size());
		final CreateDeleteOperation createDeleteOperation = checkAndCast(operations.get(0), CreateDeleteOperation.class);

		final ModelElementId useCaseId = getProject().getModelElementId(useCase);

		assertEquals(useCaseId, createDeleteOperation.getModelElementId());
		assertEquals(0, createDeleteOperation.getSubOperations().size());
		assertFalse(createDeleteOperation.isDelete());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				createDeleteOperation.reverse().apply(getProject());
			}
		}.run(false);

		assertNull(useCase.eContainer());
		assertNull(((IdEObjectCollectionImpl) getProject()).getDeletedModelElement(useCaseId));
	}

	@Test
	public void testDeleteReverse() {

		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();
		final TestElement oldTestElement = Create.testElement();
		final TestElement newTestElement = Create.testElement();
		final TestElement otherTestElement = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getContainedElements().add(useCase);
				section.getContainedElements().add(oldTestElement);
				getProject().addModelElement(newTestElement);
				getProject().addModelElement(otherTestElement);
				useCase.setNonContained_NTo1(oldTestElement);
				useCase.getNonContained_NToM().add(newTestElement);
				useCase.getNonContained_NToM().add(otherTestElement);
				assertTrue(getProject().contains(useCase));
				assertEquals(getProject(), ModelUtil.getProject(useCase));
				clearOperations();
			}
		}.run(false);

		final ModelElementId useCaseId = ModelUtil.getProject(useCase).getModelElementId(useCase);
		assertNotNull(useCaseId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		assertEquals(1, operations.size());
		final AbstractOperation operation = operations.get(0);
		assertTrue(operation instanceof CreateDeleteOperation);
		final CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) operation;
		assertTrue(createDeleteOperation.isDelete());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				createDeleteOperation.reverse().apply(getProject());
			}
		}.run(false);

		final EObject modelElement = getProject().getModelElement(useCaseId);
		final ModelElementId modelElementId = getProject().getModelElementId(modelElement);

		assertNotNull(modelElement);
		assertEquals(useCaseId, modelElementId);
	}

	@Test
	public void testReferenceMapDeletion() {

		final TestElement testElement = Create.testElement(TEST_ELEMENT);
		final TestElement secondTestElement = Create.testElement(SECOND_TEST_ELEMENT);
		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);
		final TestElement keyRefeferenceTestElement = Create.testElement(KEY_REFEFERENCE_TEST_ELEMENT);
		final TestElement referenceTestElement = Create.testElement(REFERENCE_TEST_ELEMENT);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				getProject().addModelElement(secondTestElement);
				getProject().addModelElement(parentTestElement);

				parentTestElement.setContainedElement(secondTestElement);
				testElement.getElementMap().put(parentTestElement, secondTestElement);
				testElement.getElementMap().put(keyRefeferenceTestElement, referenceTestElement);

			}
		}.run(false);

		assertTrue(getProject().contains(testElement));
		assertTrue(getProject().contains(secondTestElement));
		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(keyRefeferenceTestElement));
		assertTrue(getProject().contains(referenceTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(secondTestElement);
			}
		}.run(false);

		assertTrue(getProject().contains(testElement));
		assertFalse(getProject().contains(secondTestElement));
		assertNull(testElement.getElementMap().get(parentTestElement));
		assertTrue(getProject().contains(parentTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// delete value reference
				getProject().deleteModelElement(referenceTestElement);
			}
		}.run(false);

		assertNull(testElement.getElementMap().get(referenceTestElement));
		assertTrue(testElement.getElementMap().containsKey(keyRefeferenceTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// delete key reference
				getProject().deleteModelElement(keyRefeferenceTestElement);
			}
		}.run(false);

		assertFalse(testElement.getElementMap().containsKey(keyRefeferenceTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(testElement);
			}
		}.run(false);

		assertFalse(getProject().contains(testElement));
		assertTrue(getProject().contains(parentTestElement));
		assertNull(testElement.getElementMap().get(parentTestElement));
	}

	@Test
	public void testAttributeToReferenceMapDeletion() {

		final TestElement testElement = Create.testElement(TEST_ELEMENT);
		final TestElement secondTestElement = Create.testElement(SECOND_TEST_ELEMENT);
		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);
		final TestElement referenceTestElement = Create.testElement(REFERENCE_TEST_ELEMENT);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				getProject().addModelElement(secondTestElement);
				getProject().addModelElement(parentTestElement);

				parentTestElement.setContainedElement(secondTestElement);
				testElement.getStringToElementMap().put(SECOND_TEST_ELEMENT, secondTestElement);
				testElement.getStringToElementMap().put(REFERENCED_TEST_ELEMENT, referenceTestElement);

			}
		}.run(false);

		assertTrue(getProject().contains(testElement));
		assertTrue(getProject().contains(secondTestElement));
		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(referenceTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(secondTestElement);
			}
		}.run(false);

		assertTrue(getProject().contains(testElement));
		assertFalse(getProject().contains(secondTestElement));
		assertNull(testElement.getStringToElementMap().get(SECOND_TEST_ELEMENT));
		assertTrue(testElement.getStringToElementMap().containsKey(SECOND_TEST_ELEMENT));
		assertTrue(getProject().contains(parentTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// delete value reference
				getProject().deleteModelElement(referenceTestElement);
			}
		}.run(false);

		assertNull(testElement.getStringToElementMap().get(REFERENCED_TEST_ELEMENT));
		assertTrue(testElement.getStringToElementMap().containsKey(REFERENCED_TEST_ELEMENT));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(testElement);
			}
		}.run(false);

		assertFalse(getProject().contains(testElement));
		assertTrue(getProject().contains(parentTestElement));
	}

	@Test
	public void testReferenceToAttributeMapDeletion() {

		final TestElement testElement = Create.testElement(TEST_ELEMENT);
		final TestElement secondTestElement = Create.testElement(SECOND_TEST_ELEMENT);
		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);
		final TestElement referenceKeyTestElement = Create.testElement(REFERENCE_TEST_ELEMENT);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				getProject().addModelElement(secondTestElement);
				getProject().addModelElement(parentTestElement);

				parentTestElement.setContainedElement(secondTestElement);
				testElement.getElementToStringMap().put(secondTestElement, SECOND_TEST_ELEMENT);
				testElement.getElementToStringMap().put(referenceKeyTestElement, REFERENCED_TEST_ELEMENT);

			}
		}.run(false);

		assertTrue(getProject().contains(testElement));
		assertTrue(getProject().contains(secondTestElement));
		assertTrue(getProject().contains(parentTestElement));
		assertTrue(getProject().contains(referenceKeyTestElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// delete keys
				getProject().deleteModelElement(secondTestElement);
				getProject().deleteModelElement(referenceKeyTestElement);
			}
		}.run(false);

		assertFalse(getProject().contains(referenceKeyTestElement));
		assertFalse(getProject().contains(secondTestElement));

		assertFalse(testElement.getElementToStringMap().containsKey(secondTestElement));
		assertFalse(testElement.getElementToStringMap().containsKey(referenceKeyTestElement));
	}

	@Test
	public void testAttributeMapDeletion() {
		final TestElement testElement = Create.testElement(TEST_ELEMENT);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.getStringToStringMap().put(DAY, TAG);
				testElement.getStringToStringMap().put(HELLO, HALLO);

			}
		}.run(false);

		assertTrue(getProject().contains(testElement));
		assertEquals(testElement.getStringToStringMap().get(DAY), TAG);
		assertEquals(testElement.getStringToStringMap().get(HELLO), HALLO);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(testElement);
			}
		}.run(false);
		assertFalse(getProject().contains(testElement));
	}

	@Test
	public void testRemoveAndAddElementWithSavedSettingsInOneCommand() {
		final TestElement testElement1 = Create.testElement(TEST_ELEMENT1);
		final TestElement testElement2 = Create.testElement(TEST_ELEMENT2);
		final TestElement testElement3 = Create.testElement(TEST_ELEMENT3);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().addModelElement(testElement1);
				getProject().addModelElement(testElement2);
				getProject().addModelElement(testElement3);

				assertTrue(getProject().contains(testElement1));
				assertTrue(getProject().contains(testElement2));
				assertTrue(getProject().contains(testElement3));

				// test with reference as setting
				testElement2.setReference(testElement1);

				assertEquals(testElement1, testElement2.getReference());

				RemoveCommand.create(
					AdapterFactoryEditingDomain.getEditingDomainFor(getProject()),
					getProject(),
					org.eclipse.emf.emfstore.internal.common.model.ModelPackage.eINSTANCE.getProject_ModelElements(),
					testElement2).execute();

				assertFalse(getProject().contains(testElement2));
				assertEquals(testElement1, testElement2.getReference());

				getProject().addModelElement(testElement2);

				assertTrue(getProject().contains(testElement2));
				assertEquals(testElement1, testElement2.getReference());
				return null;
			}
		});
	}

	@Test
	public void testRemoveAndAddElementWithSavedButChangedSettingsInOneCommand() {
		final TestElement useCase1 = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement actor = Create.testElement();

		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {

				getProject().addModelElement(useCase1);
				getProject().addModelElement(useCase2);
				getProject().addModelElement(actor);

				assertTrue(getProject().contains(useCase1));
				assertTrue(getProject().contains(useCase2));
				assertTrue(getProject().contains(actor));

				// test with attribute as setting
				useCase1.setNonContained_NTo1(actor);

				assertEquals(actor, useCase1.getNonContained_NTo1());
				assertTrue(actor.getNonContained_1ToN().contains(useCase1));

				RemoveCommand.create(AdapterFactoryEditingDomain.getEditingDomainFor(
					getProject()),
					getProject(),
					org.eclipse.emf.emfstore.internal.common.model.ModelPackage.eINSTANCE.getProject_ModelElements(),
					actor).execute();

				assertFalse(getProject().contains(actor));
				assertEquals(actor, useCase1.getNonContained_NTo1());
				assertTrue(actor.getNonContained_1ToN().contains(useCase1));

				useCase2.setNonContained_NTo1(actor);
				getProject().addModelElement(actor);

				assertEquals(actor, useCase1.getNonContained_NTo1());
				assertEquals(actor, useCase2.getNonContained_NTo1());
				assertTrue(actor.getNonContained_1ToN().contains(useCase1));
				assertTrue(actor.getNonContained_1ToN().contains(useCase2));
				assertTrue(getProject().contains(actor));

				return null;
			}
		});
	}

	@Test
	public void testRemoveAndAddModelElementWithSavedSettingsInTwoCommands() {
		final TestElement testElement1 = Create.testElement(TEST_ELEMENT1);
		final TestElement testElement2 = Create.testElement(TEST_ELEMENT2);
		final TestElement referencedTestElement = Create.testElement(REF_TEST_ELEMENT);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProject().addModelElement(testElement1);
				getProject().addModelElement(testElement2);
				getProject().addModelElement(referencedTestElement);

				testElement1.setReference(referencedTestElement);
				testElement2.setReference(referencedTestElement);
				referencedTestElement.setReference(testElement2);
				return null;
			}
		});

		assertTrue(getProject().contains(testElement1));
		assertTrue(getProject().contains(testElement2));
		assertTrue(getProject().contains(referencedTestElement));
		assertEquals(referencedTestElement, testElement1.getReference());
		assertEquals(referencedTestElement, testElement2.getReference());
		assertEquals(testElement2, referencedTestElement.getReference());

		AdapterFactoryEditingDomain
			.getEditingDomainFor(getProject())
			.getCommandStack()
			.execute(
				RemoveCommand.create(
					AdapterFactoryEditingDomain.getEditingDomainFor(getProject()),
					getProject(),
					org.eclipse.emf.emfstore.internal.common.model.ModelPackage.eINSTANCE.getProject_ModelElements(),
					testElement2));

		assertTrue(getProject().contains(testElement1));
		assertFalse(getProject().contains(testElement2));
		assertTrue(getProject().contains(referencedTestElement));
		assertEquals(referencedTestElement, testElement1.getReference());
		assertNull(testElement2.getReference());
		assertNull(referencedTestElement.getReference());

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement2.setReference(referencedTestElement);
				getProject().addModelElement(testElement2);
				return null;
			}
		});

		assertTrue(getProject().contains(testElement1));
		assertTrue(getProject().contains(testElement2));
		assertTrue(getProject().contains(referencedTestElement));
		assertEquals(referencedTestElement, testElement1.getReference());
		assertEquals(referencedTestElement, testElement2.getReference());
		assertNull(referencedTestElement.getReference());
	}

	// commenting out, too exotic to happen
	/*
	 * @Test public void createTreeAndAddNonRootToProject() { WorkPackage root =
	 * TaskFactory.eINSTANCE.createWorkPackage(); WorkPackage child = TaskFactory.eINSTANCE.createWorkPackage();
	 * WorkPackage existing = TaskFactory.eINSTANCE.createWorkPackage(); root.getContainedWorkItems().add(child);
	 * getProject().getModelElements().add(existing); child.getContainedWorkItems().add(existing);
	 * getProject().getModelElements().add(root); assertTrue(getProject().contains(child));
	 * assertTrue(getProject().contains(root)); assertTrue(getProject().contains(existing)); assertSame(root,
	 * child.getContainingWorkpackage()); assertSame(child, existing.getContainingWorkpackage());
	 * assertEquals(getProject().getAllModelElements().size(), 3); }
	 */
}
