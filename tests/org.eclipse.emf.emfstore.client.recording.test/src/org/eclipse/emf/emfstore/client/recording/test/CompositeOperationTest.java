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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.handler.ESOperationModifier;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.InvalidHandleException;
import org.eclipse.emf.emfstore.internal.client.model.impl.AutoOperationWrapper;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.common.ExtensionRegistry;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the comnposite operation recording.
 * 
 * @author koegel
 */
public class CompositeOperationTest extends ESTest {

	private static final String SECTION_CREATION = "sectionCreation"; //$NON-NLS-1$
	private static final String DESCRIPTION3 = "description"; //$NON-NLS-1$
	private static final String NEW_DESCRIPTION = "newDescription"; //$NON-NLS-1$
	private static final String NEW_NAME = "newName"; //$NON-NLS-1$
	private static final String DESCRIPTION2 = "Description"; //$NON-NLS-1$
	private static final String NAME = "Name"; //$NON-NLS-1$

	private TestElement addSection() {

		final TestElement section = Create.testElement();

		return new EMFStoreCommandWithResult<TestElement>() {
			@Override
			protected TestElement doRun() {
				getProject().addModelElement(section);
				section.setName(NAME);
				section.setDescription(DESCRIPTION2);

				assertEquals(true, getProject().contains(section));
				assertEquals(NAME, section.getName());
				assertEquals(DESCRIPTION2, section.getDescription());
				assertEquals(0, section.getContainedElements().size());

				clearOperations();

				return section;
			}
		}.run(false);
	}

	/**
	 * Test the creation and completion of a composite operation.
	 */
	@Test
	public void createSmallComposite() {

		final TestElement section = addSection();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setName(NEW_NAME);
				section.setDescription(NEW_DESCRIPTION);
				section.getContainedElements().add(useCase);

				assertEquals(true, getProject().contains(useCase));
				assertEquals(getProject(), ModelUtil.getProject(useCase));
				assertEquals(useCase, section.getContainedElements().iterator().next());
				assertEquals(NEW_NAME, section.getName());
				assertEquals(NEW_DESCRIPTION, section.getDescription());

				final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);

				try {
					handle.end(SECTION_CREATION, DESCRIPTION3, sectionId);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		assertEquals(true, getProject().contains(useCase));
		assertEquals(getProject(), ModelUtil.getProject(useCase));
		assertEquals(useCase, section.getContainedElements().iterator().next());
		assertEquals(NEW_NAME, section.getName());
		assertEquals(NEW_DESCRIPTION, section.getDescription());

		assertEquals(1, getProjectSpace().getOperations().size());
		final AbstractOperation operation = getProjectSpace().getOperations().iterator().next();
		assertEquals(true, operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;
		assertEquals(4, compositeOperation.getSubOperations().size());
	}

	/**
	 * Test the creation and completion of a composite operation.
	 */
	@Test
	public void createSmallCompositeAcrossCommands() {

		final TestElement section = addSection();
		final TestElement useCase = Create.testElement();

		final CompositeOperationHandle handle = new EMFStoreCommandWithResult<CompositeOperationHandle>() {
			@Override
			protected CompositeOperationHandle doRun() {
				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setName(NEW_NAME);
				section.setDescription(NEW_DESCRIPTION);
				section.getContainedElements().add(useCase);

				assertEquals(true, getProject().contains(useCase));
				assertEquals(getProject(), ModelUtil.getProject(useCase));
				assertEquals(useCase, section.getContainedElements().iterator().next());
				assertEquals(NEW_NAME, section.getName());
				assertEquals(NEW_DESCRIPTION, section.getDescription());

				return handle;
			}
		}.run(false);

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);
		final ProjectSpace projectSpace = ModelUtil.getParent(ProjectSpace.class, section);
		assertEquals(0, projectSpace.getOperations().size());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				try {
					handle.end(SECTION_CREATION, DESCRIPTION3, sectionId);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		assertEquals(true, getProject().contains(useCase));
		assertEquals(getProject(), ModelUtil.getProject(useCase));
		assertEquals(useCase, section.getContainedElements().iterator().next());
		assertEquals(NEW_NAME, section.getName());
		assertEquals(NEW_DESCRIPTION, section.getDescription());

		assertEquals(1, getProjectSpace().getOperations().size());
		final AbstractOperation operation = getProjectSpace().getOperations().iterator().next();
		assertEquals(true, operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;
		assertEquals(4, compositeOperation.getSubOperations().size());
	}

	/**
	 * Test the creation and completion of a composite operation.
	 */
	@Test
	public void createSmallCompositeAcrossCommandsWithAutoOperationWrapper() {

		// TODO: Think about elegant solution to replace the operation modifier during a single test
		final ESOperationModifier operationModifier = ExtensionRegistry.INSTANCE.get(ESOperationModifier.ID,
			ESOperationModifier.class);

		ExtensionRegistry.INSTANCE.set(
			ESOperationModifier.ID,
			new AutoOperationWrapper());

		final TestElement section = addSection();
		final TestElement useCase = Create.testElement();

		final CompositeOperationHandle handle = new EMFStoreCommandWithResult<CompositeOperationHandle>() {
			@Override
			protected CompositeOperationHandle doRun() {
				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setName(NEW_NAME);
				section.setDescription(NEW_DESCRIPTION);
				section.getContainedElements().add(useCase);

				assertEquals(true, getProject().contains(useCase));
				assertEquals(getProject(), ModelUtil.getProject(useCase));
				assertEquals(useCase, section.getContainedElements().iterator().next());
				assertEquals(NEW_NAME, section.getName());
				assertEquals(NEW_DESCRIPTION, section.getDescription());

				return handle;
			}
		}.run(false);

		final ModelElementId sectionId = ModelUtil.getProject(section).getModelElementId(section);
		final ProjectSpace projectSpace = ModelUtil.getParent(ProjectSpace.class, section);
		assertEquals(0, projectSpace.getOperations().size());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				try {
					handle.end(SECTION_CREATION, DESCRIPTION3, sectionId);
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		assertEquals(true, getProject().contains(useCase));
		assertEquals(getProject(), ModelUtil.getProject(useCase));
		assertEquals(useCase, section.getContainedElements().iterator().next());
		assertEquals(NEW_NAME, section.getName());
		assertEquals(NEW_DESCRIPTION, section.getDescription());

		assertEquals(1, getProjectSpace().getOperations().size());
		final AbstractOperation operation = getProjectSpace().getOperations().iterator().next();
		assertEquals(true, operation instanceof CompositeOperation);
		final CompositeOperation compositeOperation = (CompositeOperation) operation;
		assertEquals(4, compositeOperation.getSubOperations().size());

		ExtensionRegistry.INSTANCE.set(
			ESOperationModifier.ID,
			operationModifier);
	}

	/**
	 * Test the creation and abort of a composite operation.
	 * 
	 * @throws InvalidHandleException if the test fails
	 * @throws IOException
	 */
	@Test
	public void abortSmallComposite() throws InvalidHandleException, IOException {

		final TestElement section = addSection();
		final TestElement useCase = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				final CompositeOperationHandle handle = getProjectSpace().beginCompositeOperation();
				section.setName(NEW_NAME);
				section.setDescription(NEW_DESCRIPTION);
				section.getContainedElements().add(useCase);

				assertEquals(true, getProject().contains(useCase));
				assertEquals(getProject(), ModelUtil.getProject(useCase));
				assertEquals(useCase, section.getContainedElements().iterator().next());
				assertEquals(NEW_NAME, section.getName());
				assertEquals(NEW_DESCRIPTION, section.getDescription());

				try {
					handle.abort();
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);

		assertEquals(true, getProject().contains(section));
		assertEquals(NAME, section.getName());
		assertEquals(DESCRIPTION2, section.getDescription());
		assertEquals(0, section.getContainedElements().size());
		assertEquals(false, getProject().contains(useCase));

		assertEquals(0, getProjectSpace().getOperations().size());

		getProjectSpace().save();

		final Project loadedProject = ModelUtil.loadEObjectFromResource(
			org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.getModelPackage().getProject(),
			getProject()
				.eResource().getURI(), false);

		assertTrue(ModelUtil.areEqual(loadedProject, getProject()));
		assertEquals(false, getProject().contains(useCase));
		assertEquals(true, getProject().contains(section));
	}

	/**
	 * Test the creation and abort of a composite operation after some elements have been added. Check if the abort
	 * reverses the last operation.
	 */
	@Test
	public void beginAndAbortEmptyCompositeAfterSimpleOperation() {
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				final TestElement section = Create.testElement();
				final TestElement workPackage = Create.testElement();
				final TestElement actionItem = Create.testElement();
				getProject().addModelElement(section);
				getProject().addModelElement(workPackage);
				getProject().addModelElement(actionItem);
				actionItem.setContainer(workPackage);
				final CompositeOperationHandle compositeOperationHandle = getProjectSpace().beginCompositeOperation();
				try {
					compositeOperationHandle.abort();
				} catch (final InvalidHandleException e) {
					throw new IllegalStateException(e);
				}

				assertEquals(workPackage, actionItem.getContainer());

			}
		}.run(false);
	}

	/**
	 * Test the creation and abort of a composite operation.
	 */
	@Test
	public void beginAndAbortEmptyComposite() {
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				CompositeOperationHandle compositeOperationHandle = getProjectSpace().beginCompositeOperation();
				try {
					compositeOperationHandle.abort();
					compositeOperationHandle = getProjectSpace().beginCompositeOperation();
					compositeOperationHandle.abort();
					compositeOperationHandle = getProjectSpace().beginCompositeOperation();
					compositeOperationHandle.abort();
				} catch (final InvalidHandleException e) {
					fail();
				}
			}
		}.run(false);
	}

	/**
	 * Test ending and starting composite operations subsequently.
	 */
	@Test
	public void beginSubsequentCompositeOperations() {
		getProjectSpace().beginCompositeOperation();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(Create.testElement());
				getProject().addModelElement(Create.testElement());
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(Create.testElement());
				getProject().addModelElement(Create.testElement());
			}
		}.run(false);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProjectSpace().getOperationManager().endCompositeOperation();
				return null;
			}
		});

		assertTrue(getProjectSpace().getOperationManager().getNotificationRecorder().isRecordingComplete());

		getProjectSpace().beginCompositeOperation();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(Create.testElement());
				getProject().addModelElement(Create.testElement());
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(Create.testElement());
				getProject().addModelElement(Create.testElement());
			}
		}.run(false);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				getProjectSpace().getOperationManager().endCompositeOperation();
				return null;
			}
		});

		assertTrue(getProjectSpace().getOperationManager().getNotificationRecorder().isRecordingComplete());
	}

}
