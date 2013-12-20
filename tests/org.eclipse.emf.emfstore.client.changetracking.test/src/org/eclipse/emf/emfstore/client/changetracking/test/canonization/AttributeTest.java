/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.changetracking.test.canonization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.emfstore.client.test.common.cases.ComparingESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.InvalidHandleException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationsCanonizer;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests canonization of attribute operations.
 * 
 * @author chodnick
 */
public class AttributeTest extends ComparingESTest {

	private static final String SOME_NAME = "someName"; //$NON-NLS-1$
	private static final String SOME_NEW_NAME = "some new Name"; //$NON-NLS-1$
	private static final String ORIGINAL_DESCRIPTION2 = "originalDescription2"; //$NON-NLS-1$
	private static final String ORIGINAL_NAME2 = "originalName2"; //$NON-NLS-1$
	private static final String ORIGINAL_DESCRIPTION1 = "originalDescription1"; //$NON-NLS-1$
	private static final String ORIGINAL_NAME1 = "originalName1"; //$NON-NLS-1$
	private static final String ORIGINAL_DESCRIPTION = "originalDescription"; //$NON-NLS-1$
	private static final String ORIGINAL_NAME = "originalName"; //$NON-NLS-1$
	private static final String DESCRIPTION_OF_TEST_ELEMENT2 = "DescriptionOfTestElement2"; //$NON-NLS-1$
	private static final String NAME_OF_TEST_ELEMENT2 = "NameOfTestElement2"; //$NON-NLS-1$
	private static final String DESCRIPTION_OF_TEST_ELEMENT = "DescriptionOfTestElement"; //$NON-NLS-1$
	private static final String NAME_OF_TEST_ELEMENT = "NameOfTestElement"; //$NON-NLS-1$
	private static final String DESC_2 = "desc 2"; //$NON-NLS-1$
	private static final String DESCRIPTION2 = "description"; //$NON-NLS-1$
	private static final String FINAL_DESC = "final desc"; //$NON-NLS-1$
	private static final String SOME_OTHER_DESC = "some other desc"; //$NON-NLS-1$
	private static final String SOME_DESC = "some desc";//$NON-NLS-1$
	private static final String OLD_SECTION = "oldSection";//$NON-NLS-1$
	private static final String HOME = "home";//$NON-NLS-1$
	private static final String MAGGIE = "maggie";//$NON-NLS-1$
	private static final String HOMER = "homer";//$NON-NLS-1$
	private static final String SOME_SECTION = "some section";//$NON-NLS-1$
	private static final String Y_NAME = "Y";//$NON-NLS-1$
	private static final String X_NAME = "X";//$NON-NLS-1$
	private static final String SECTION_CREATION = "sectionCreation";//$NON-NLS-1$
	private static final String DESC_1 = "desc 1";//$NON-NLS-1$
	private static final String NAME = "Name";//$NON-NLS-1$
	private static final String NEW_DESCRIPTION = "newDescription";//$NON-NLS-1$
	private static final String OLD_DESCRIPTION = "oldDescription";//$NON-NLS-1$
	private static final String A_NAME = "A"; //$NON-NLS-1$
	private static final String B_NAME = "B"; //$NON-NLS-1$
	private static final String C_NAME = "C"; //$NON-NLS-1$
	private static final String NEW_NAME = "newName"; //$NON-NLS-1$
	private static final String OLD_NAME = "oldName"; //$NON-NLS-1$

	/**
	 * Tests canonization for consecutive attribute changes on a single feature.
	 * 
	 * @throws IOException
	 */
	@Test
	public void consecutiveAttributeChangeSingleFeature() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setName(B_NAME);
				useCase.setName(C_NAME);
				useCase.setName(NEW_NAME);
			}
		}.run(false);

		assertEquals(NEW_NAME, useCase.getName());
		assertEquals(4, getProjectSpace().getOperations().size());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		assertEquals(operations.size(), 1);

		final AttributeOperation reverse = (AttributeOperation) operations.get(0).reverse();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				reverse.apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for consecutive attribute changes on a single feature.
	 * 
	 * @throws IOException
	 */
	@Test
	public void consecutiveAttributeChangeSingleFeatureToNull() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setName(B_NAME);
				useCase.setName(C_NAME);
				useCase.setName(null);
			}
		}.run(false);

		assertEquals(null, useCase.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		assertEquals(operations.size(), 1);

		final AttributeOperation reverse = (AttributeOperation) operations.get(0).reverse();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				reverse.apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

	}

	/**
	 * Tests canonization for consecutive attribute changes on a single feature.
	 * 
	 * @throws IOException
	 */
	@Test
	public void consecutiveAttributeChangeSingleFeatureNullToValue() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(null);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setName(B_NAME);
				useCase.setName(C_NAME);
			}
		}.run(false);

		assertEquals(C_NAME, useCase.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(3, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		assertEquals(operations.size(), 1);

		final AttributeOperation reverse = (AttributeOperation) operations.get(0).reverse();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				reverse.apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for consecutive attribute changes, resulting in a noop.
	 * 
	 * @throws IOException
	 */
	@Test
	public void attributeChangeNoOp() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setName(B_NAME);
				useCase.setName(C_NAME);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		assertEquals(OLD_NAME, useCase.getName());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(4, operations.size());

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		canonize(operations);

		// should not have created any operations, we were just resetting the name to its original value
		assertEquals(operations.size(), 0);

	}

	/**
	 * Tests canonization for consecutive attribute changes, resulting in a noop.
	 * 
	 * @throws IOException
	 */
	@Test
	public void attributeChangeNoOpNull() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(null);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setName(B_NAME);
				useCase.setName(C_NAME);
				useCase.setName(null);
			}
		}.run(false);

		assertEquals(null, useCase.getName());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(4, operations.size());

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// should not have created any operations, we were just resetting the name to its original value
		assertEquals(operations.size(), 0);
	}

	/**
	 * Tests canonization for consecutive attribute changes, resulting in a noop.
	 * 
	 * @throws IOException
	 */
	@Test
	public void attributeChangeMultiFeatureNoOp() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
				useCase.setDescription(OLD_DESCRIPTION);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setDescription(X_NAME);
				useCase.setName(B_NAME);
				useCase.setDescription(Y_NAME);
				useCase.setName(C_NAME);

				useCase.setDescription(OLD_DESCRIPTION);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(7, operations.size());

		canonize(operations);

		// should not have created any operations, we were just resetting everything to its original value
		assertEquals(operations.size(), 0);
	}

	/**
	 * Tests canonization for consecutive attribute changes on multiple features.
	 * 
	 * @throws IOException
	 */
	@Test
	public void consecutiveAttributeChangeMultiFeature() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(OLD_NAME);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				useCase.setDescription(OLD_DESCRIPTION);
				useCase.setName(B_NAME);
				useCase.setName(C_NAME);
				useCase.setDescription(NEW_DESCRIPTION);
				useCase.setName(NEW_NAME);
			}
		}.run(false);

		assertEquals(NEW_NAME, useCase.getName());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(6, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		assertEquals(operations.size(), 2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for mixed attribute changes on a single feature.
	 * 
	 * @throws IOException
	 */
	@Test
	public void mixedAttributeChangeSingleFeature() throws IOException {

		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement section = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);
				getProject().addModelElement(section);

				useCase.setName(OLD_NAME);
				section.setName(SOME_SECTION);
				actor.setName(HOMER);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				actor.setName(MAGGIE);
				useCase.setName(B_NAME);
				useCase.setNonContained_NTo1(actor);
				useCase.setName(C_NAME);
				section.setName(HOME);
				useCase.setName(NEW_NAME);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(7, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for mixed attribute changes on a single feature.
	 * 
	 * @throws IOException
	 */
	@Test
	public void mixedAttributeChangeMultiFeature() throws IOException {

		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();
		final TestElement section = Create.testElement();
		final TestElement oldSection = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);
				getProject().addModelElement(section);
				getProject().addModelElement(oldSection);

				useCase.setContainer(oldSection);
				actor.setContainer(oldSection);

				useCase.setName(OLD_NAME);
				oldSection.setName(OLD_SECTION);
				section.setName(SOME_SECTION);
				actor.setName(HOMER);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				useCase.setName(A_NAME);
				actor.setName(MAGGIE);
				useCase.setName(B_NAME);
				useCase.setDescription(SOME_DESC);
				useCase.setNonContained_NTo1(actor);
				useCase.setName(C_NAME);
				section.setName(HOME);
				useCase.setDescription(SOME_OTHER_DESC);
				useCase.setName(NEW_NAME);
				useCase.setDescription(FINAL_DESC);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(NEW_NAME, useCase.getName());
		assertEquals(FINAL_DESC, useCase.getDescription());
		assertEquals(HOME, section.getName());
		assertEquals(MAGGIE, actor.getName());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Test the creation and completion of a composite operation, that contains attribute changes.
	 * 
	 * @throws InvalidHandleException if the test fails
	 * @throws IOException
	 */
	@Test
	public void compositeAttributeChangesACA() throws InvalidHandleException, IOException {

		final TestElement section = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.setName(NAME);
				section.setDescription(OLD_DESCRIPTION);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				section.setDescription(DESC_1);

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setDescription(NEW_DESCRIPTION);
				final TestElement useCase = Create.testElement();
				section.getContainedElements().add(useCase);
				try {
					handle.end(SECTION_CREATION, DESCRIPTION2,
						ModelUtil.getProject(section).getModelElementId(section));
				} catch (final InvalidHandleException e) {
					fail();
				}
				section.setDescription(DESC_2);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(DESC_2, section.getDescription());
		assertEquals(3, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Test the creation and completion of a composite operation, that contains attribute changes.
	 * 
	 * @throws InvalidHandleException if the test fails
	 * @throws IOException
	 */
	@Test
	public void compositeAttributeChangesAC() throws InvalidHandleException, IOException {

		final TestElement section = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.setName(NAME);
				section.setDescription(OLD_DESCRIPTION);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				section.setDescription(DESC_1);

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setDescription(NEW_DESCRIPTION);
				final TestElement useCase = Create.testElement();
				section.getContainedElements().add(useCase);
				try {
					handle.end(SECTION_CREATION, DESCRIPTION2,
						ModelUtil.getProject(section).getModelElementId(section));
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());
		assertEquals(NEW_DESCRIPTION, section.getDescription());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Test the creation and completion of a composite operation, that contains attribute changes.
	 * 
	 * @throws InvalidHandleException if the test fails
	 * @throws IOException
	 */
	@Test
	public void compositeAttributeChangesCA() throws InvalidHandleException, IOException {

		final TestElement section = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.setName(NAME);
				section.setDescription(OLD_DESCRIPTION);
			}
		}.run(false);

		final Project expectedProject = ModelUtil.clone(getProject());
		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setDescription(NEW_DESCRIPTION);
				final TestElement useCase = Create.testElement();
				section.getContainedElements().add(useCase);
				try {
					handle.end(SECTION_CREATION, DESCRIPTION2,
						ModelUtil.getProject(section).getModelElementId(section));
				} catch (final InvalidHandleException e) {
					fail();
				}

				section.setDescription(DESC_2);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		assertEquals(2, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for create and consecutive attribute changes.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createAndChangeAttributesSimple() throws IOException {

		final Project originalProject = ModelUtil.clone(getProject());

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(NAME_OF_TEST_ELEMENT);
				useCase.setDescription(DESCRIPTION_OF_TEST_ELEMENT);
			}
		}.run(false);

		assertEquals(NAME_OF_TEST_ELEMENT, useCase.getName());

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a create and two attribute operations
		assertEquals(3, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// now expecting only the create with folded in attributes
		assertEquals(operations.size(), 1);
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);

		final CreateDeleteOperation op = (CreateDeleteOperation) operations.get(0);

		assertEquals(((TestElement) op.getModelElement()).getName(), NAME_OF_TEST_ELEMENT);
		assertEquals(((TestElement) op.getModelElement()).getDescription(), DESCRIPTION_OF_TEST_ELEMENT);

		// test if the create is reversible and re-reversible
		final Project expectedProject = ModelUtil.clone(getProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				op.reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				op.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for create and consecutive attribute changes.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createAndChangeAttributesComplex() throws IOException {

		final Project originalProject = ModelUtil.clone(getProject());

		final TestElement useCase = Create.testElement();
		final TestElement useCase2 = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				getProject().addModelElement(useCase2);

				useCase.setName(NAME_OF_TEST_ELEMENT);
				useCase.setDescription(DESCRIPTION_OF_TEST_ELEMENT);

				useCase2.setName(NAME_OF_TEST_ELEMENT2);
				useCase2.setDescription(DESCRIPTION_OF_TEST_ELEMENT2);
			}
		}.run(false);

		assertEquals(NAME_OF_TEST_ELEMENT, useCase.getName());
		assertEquals(NAME_OF_TEST_ELEMENT2, useCase2.getName());
		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expecting a create and two attribute operations per usecase
		assertEquals(6, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// now expecting only the creates with folded in attributes
		assertEquals(2, operations.size());
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);

		final CreateDeleteOperation op = (CreateDeleteOperation) operations.get(0);

		assertEquals(((TestElement) op.getModelElement()).getName(), NAME_OF_TEST_ELEMENT);
		assertEquals(((TestElement) op.getModelElement()).getDescription(), DESCRIPTION_OF_TEST_ELEMENT);

		assertTrue(operations.get(1) instanceof CreateDeleteOperation);

		final CreateDeleteOperation op2 = (CreateDeleteOperation) operations.get(1);

		assertEquals(((TestElement) op2.getModelElement()).getName(), NAME_OF_TEST_ELEMENT2);
		assertEquals(((TestElement) op2.getModelElement()).getDescription(), DESCRIPTION_OF_TEST_ELEMENT2);

		// test reversibility, too

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				op2.reverse().apply(getProject());
				op.reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));
	}

	/**
	 * Test the creation and completion of a composite operation, that contains attribute changes.
	 * 
	 * @throws InvalidHandleException if the test fails
	 * @throws IOException
	 */
	@Test
	public void createAndAttributeChangesACA() throws InvalidHandleException, IOException {

		final Project originalProject = ModelUtil.clone(getProject());

		final TestElement section = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.setName(NAME);
				section.setDescription(OLD_DESCRIPTION);

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setDescription(NEW_DESCRIPTION);
				final TestElement useCase = Create.testElement();
				section.getContainedElements().add(useCase);
				try {
					handle.end(SECTION_CREATION, DESCRIPTION2,
						ModelUtil.getProject(section).getModelElementId(section));
				} catch (final InvalidHandleException e) {
					fail();
				}

				section.setDescription(DESC_2);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		// expect create, 2 attribute ops, the composite, 1 attribute op
		assertEquals(5, operations.size());
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);
		assertTrue(operations.get(1) instanceof AttributeOperation);
		assertTrue(operations.get(2) instanceof AttributeOperation);
		assertTrue(operations.get(3) instanceof CompositeOperation);
		assertTrue(operations.get(4) instanceof AttributeOperation);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// expect create, the composite and 1 attribute op
		assertEquals(3, operations.size());
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);
		assertTrue(operations.get(1) instanceof CompositeOperation);
		assertTrue(operations.get(2) instanceof AttributeOperation);

		final Project expectedProject = ModelUtil.clone(getProject());

		// test reversibility

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));

		// test redo
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				operations.get(0).apply(getProject());
				operations.get(1).apply(getProject());
				operations.get(2).apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for consecutive attribute changes followed by a delete.
	 * 
	 * @throws IOException
	 */
	@Test
	public void changeAttributesAndDeleteSimple() throws IOException {

		final TestElement useCase = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(ORIGINAL_NAME);
				useCase.setDescription(ORIGINAL_DESCRIPTION);
				clearOperations();
			}
		}.run(false);

		final Project originalProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				useCase.setName(NAME_OF_TEST_ELEMENT);
				useCase.setDescription(DESCRIPTION_OF_TEST_ELEMENT);
				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		// expecting two attribute operations and a delete
		assertEquals(3, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// now expecting only the delete with folded in attributes
		assertEquals(1, operations.size());
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);

		final CreateDeleteOperation op = (CreateDeleteOperation) operations.get(0);

		assertTrue(op.isDelete());
		assertEquals(((TestElement) op.getModelElement()).getName(), ORIGINAL_NAME);
		assertEquals(((TestElement) op.getModelElement()).getDescription(), ORIGINAL_DESCRIPTION);

		// test if the delete is reversible and re-reversible
		final Project expectedProject = ModelUtil.clone(getProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				op.reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				op.reverse().reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for consecutive attribute changes and delete.
	 * 
	 * @throws IOException
	 */
	@Test
	public void changeAttributesAndDeleteComplex() throws IOException {

		final TestElement useCase = Create.testElement();
		final TestElement useCase2 = Create.testElement();
		final TestElement section = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.getContainedElements().add(useCase);
				section.getContainedElements().add(useCase2);

				useCase.setName(ORIGINAL_NAME1);
				useCase.setDescription(ORIGINAL_DESCRIPTION1);

				useCase2.setName(ORIGINAL_NAME2);
				useCase2.setDescription(ORIGINAL_DESCRIPTION2);
				clearOperations();
			}
		}.run(false);

		final Project originalProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				useCase.setName(NAME_OF_TEST_ELEMENT);
				useCase.setDescription(DESCRIPTION_OF_TEST_ELEMENT);

				useCase2.setName(NAME_OF_TEST_ELEMENT2);
				useCase2.setDescription(DESCRIPTION_OF_TEST_ELEMENT2);

				assertEquals(NAME_OF_TEST_ELEMENT, useCase.getName());
				assertEquals(NAME_OF_TEST_ELEMENT2, useCase2.getName());

				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().deleteModelElement(useCase2);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		// expecting two attribute operations and a delete per usecase
		assertEquals(6, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// now expecting only the deletes with folded in attributes
		assertEquals(2, operations.size());
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);

		final CreateDeleteOperation op = (CreateDeleteOperation) operations.get(0);

		assertEquals(ORIGINAL_NAME1, ((TestElement) op.getModelElement()).getName());
		assertEquals(ORIGINAL_DESCRIPTION1, ((TestElement) op.getModelElement()).getDescription());

		assertTrue(operations.get(1) instanceof CreateDeleteOperation);

		final CreateDeleteOperation op2 = (CreateDeleteOperation) operations.get(1);

		assertEquals(((TestElement) op2.getModelElement()).getName(), ORIGINAL_NAME2);
		assertEquals(((TestElement) op2.getModelElement()).getDescription(), ORIGINAL_DESCRIPTION2);

		// test reversibility, too
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				op2.reverse().apply(getProject());
				op.reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));
	}

	/**
	 * Tests canonization for consecutive attribute changes and delete on orphans.
	 */
	// commented out, orphan behaviour is irrelevant at present. This reversibility test currently fails.
	// @Test
	// public void changeAttributesAndDeleteOrphansComplex() {
	//
	// TestElement useCase = Create.testElement();
	// TestElement useCase2 = Create.testElement();
	//
	// getProject().getModelElements().add(useCase);
	// getProject().getModelElements().add(useCase2);
	//
	// useCase.setName("originalName1");
	// useCase.setDescription("originalDescription1");
	//
	// useCase2.setName("originalName2");
	// useCase2.setDescription("originalDescription2");
	//
	// Project originalProject = ModelUtil.clone(getProject());
	//
	// clearOperations();
	//
	// useCase.setName("NameOfTestElement");
	// useCase.setDescription("DescriptionOfTestElement");
	//
	// useCase2.setName("NameOfTestElement2");
	// useCase2.setDescription("DescriptionOfTestElement2");
	//
	// assertEquals("NameOfTestElement", useCase.getName());
	// assertEquals("NameOfTestElement2", useCase2.getName());
	//
	// getProject().deleteModelElement(useCase);
	// getProject().deleteModelElement(useCase2);
	//
	// List<AbstractOperation> operations = getProjectSpace().getOperations();
	//
	// // expecting two attribute operations and a delete per usecase
	// assertEquals(operations.size(), 6);
	// OperationsCanonizer.canonize(operations);
	//
	// // now expecting only the deletes with folded in attributes
	// assertEquals(operations.size(), 2);
	// assertTrue(operations.get(0) instanceof CreateDeleteOperation);
	//
	// CreateDeleteOperation op = (CreateDeleteOperation) operations.get(0);
	//
	// assertEquals(op.getModelElement().getName(), "originalName1");
	// assertEquals(op.getModelElement().getDescription(), "originalDescription1");
	//
	// assertTrue(operations.get(1) instanceof CreateDeleteOperation);
	//
	// CreateDeleteOperation op2 = (CreateDeleteOperation) operations.get(1);
	//
	// assertEquals(op2.getModelElement().getName(), "originalName2");
	// assertEquals(op2.getModelElement().getDescription(), "originalDescription2");
	//
	// // test reversibility, too
	//
	// op2.reverse().apply(getProject());
	// op.reverse().apply(getProject());
	//
	// assertTrue(ModelUtil.areEqual(getProject(), originalProject));
	//
	// }
	/**
	 * Test the creation and completion of a composite operation, that contains attribute changes.
	 * 
	 * @throws InvalidHandleException if the test fails
	 * @throws IOException
	 */
	@Test
	public void attributeChangesACAAndDelete() throws InvalidHandleException, IOException {

		final TestElement section = Create.testElement();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				section.setName(ORIGINAL_NAME);
				section.setDescription(ORIGINAL_DESCRIPTION);
			}
		}.run(false);

		final Project originalProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();
				section.setName(SOME_NEW_NAME);

				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setDescription(NEW_DESCRIPTION);
				final TestElement useCase = Create.testElement();
				section.getContainedElements().add(useCase);
				try {
					handle.end(SECTION_CREATION, DESCRIPTION2,
						ModelUtil.getProject(section).getModelElementId(section));
				} catch (final InvalidHandleException e) {
					fail();
				}

				section.setDescription(DESC_2);

				getProject().deleteModelElement(section);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();

		// expect 1 attribute op, the composite, 1 attribute op, the delete
		assertEquals(4, operations.size());
		assertTrue(operations.get(0) instanceof AttributeOperation);
		assertTrue(operations.get(1) instanceof CompositeOperation);
		assertTrue(operations.get(2) instanceof AttributeOperation);
		assertTrue(operations.get(3) instanceof CreateDeleteOperation);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// expect 1 attribute op, the composite and the delete with folded in attribute
		assertEquals(3, operations.size());
		assertTrue(operations.get(0) instanceof AttributeOperation);
		assertTrue(operations.get(1) instanceof CompositeOperation);
		assertTrue(operations.get(2) instanceof CreateDeleteOperation);

		final CreateDeleteOperation delOp = (CreateDeleteOperation) operations.get(2);
		assertTrue(delOp.isDelete());
		// not folded, interfering composite was inbeetween
		assertEquals(SOME_NEW_NAME, ((TestElement) delOp.getModelElement()).getName());
		// folded, value is oldValue from "newDescription"-> "desc 2"
		assertEquals(NEW_DESCRIPTION, ((TestElement) delOp.getModelElement()).getDescription());

		final Project expectedProject = ModelUtil.clone(getProject());

		// test reversibility

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = operations.size() - 1; i >= 0; i--) {
					final AbstractOperation reverse = operations.get(i).reverse();
					reverse.apply(getProject());
				}
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));

		// test redo
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				operations.get(0).apply(getProject());
				operations.get(1).apply(getProject());
				operations.get(2).apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), expectedProject));
	}

	/**
	 * Tests canonization for create, attribute changes and delete.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createChangeAttributeAndDelete() throws IOException {

		final Project originalProject = ModelUtil.clone(getProject());

		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase);
				useCase.setName(SOME_NAME);
				useCase.setName(NEW_NAME);
				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expect create, 2 attribute ops, delete
		assertEquals(4, operations.size());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				OperationsCanonizer.canonize(operations);
			}
		}.run(false);

		// expect attributes folding into create, and create and delete removed,
		// as they would be directly adjacent to each other
		assertEquals(operations.size(), 0);
		assertTrue(ModelUtil.areEqual(getProject(), originalProject));
	}

	/**
	 * Tests canonization for create, attribute changes and delete.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createChangeReferencesAndDelete() throws IOException {

		final TestElement useCase2 = Create.testElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(useCase2);
			}
		}.run(false);

		final Project originalProject = ModelUtil.clone(getProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();

				final TestElement useCase = Create.testElement();
				getProject().addModelElement(useCase);
				useCase.setName(SOME_NAME);
				useCase.getReferences().add(useCase2);
				getProject().deleteModelElement(useCase);
			}
		}.run(false);

		final List<AbstractOperation> operations = getProjectSpace().getOperations();
		// expect create, 1 attribute ops, 1 multiref op, the delete
		assertEquals(4, operations.size());

		canonize(operations);

		// expect attributes folding into create, the multiref and delete remain
		assertEquals(operations.size(), 3);
		assertTrue(operations.get(0) instanceof CreateDeleteOperation);
		assertTrue(operations.get(1) instanceof MultiReferenceOperation);
		assertTrue(operations.get(2) instanceof CreateDeleteOperation);

		// check the folding of the attribute
		final CreateDeleteOperation createOp = (CreateDeleteOperation) operations.get(0);
		assertEquals(SOME_NAME, ((TestElement) createOp.getModelElement()).getName());

		// check reversibility
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				operations.get(2).reverse().apply(getProject());
				operations.get(1).reverse().apply(getProject());
				operations.get(0).reverse().apply(getProject());
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getProject(), originalProject));
	}

}
