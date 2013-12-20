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

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Test creating an deleting elements.
 * 
 * @author koegel
 */
public class RecordingPerformanceTest extends ESTest {

	private static final String EMF_STORE_PLAIN = "EMFStore Plain"; //$NON-NLS-1$
	private static final String SAVE = "Save"; //$NON-NLS-1$
	private static final String INSERTION = "Insertion"; //$NON-NLS-1$
	private static final String ELEMENT = "Element"; //$NON-NLS-1$
	private static final String ITERATIONS2 = "Iterations"; //$NON-NLS-1$
	private static final String PARENT_TEST_ELEMENT = "parentTestElement"; //$NON-NLS-1$
	private static final String EMF_ONLY_ALL = "EMF Only All"; //$NON-NLS-1$

	private static final int ITERATIONS = 100;
	private static final int COUNT = 10000;

	@Test
	public void testElementAddIterationWithSave() {
		final StopWatch allStopWatch = new StopWatch(EMF_STORE_PLAIN);
		runIterationTestWithoutOptimization();
		allStopWatch.stop();
	}

	@Test
	public void testElementAddIterationWithoutSave() {
		final StopWatch allStopWatch = new StopWatch("EMFStore Plain without AutoSave but with Save at End"); //$NON-NLS-1$
		org.eclipse.emf.emfstore.internal.client.model.Configuration.getClientBehavior().setAutoSave(false);
		runIterationTestWithoutOptimization();
		final ProjectSpaceBase projectSpaceBase = (ProjectSpaceBase) getProjectSpace();
		final StopWatch saveTimer = new StopWatch(SAVE);
		projectSpaceBase.save();
		saveTimer.stop();
		org.eclipse.emf.emfstore.internal.client.model.Configuration.getClientBehavior().setAutoSave(true);
		allStopWatch.stop();
	}

	private void runIterationTestWithoutOptimization() {

		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);

		final StopWatch setupWatch = new StopWatch("Setup").silent(); //$NON-NLS-1$
		final AbstractCommand command = new AbstractCommand() {
			public void execute() {
				getProject().addModelElement(parentTestElement);
			}

			@Override
			protected boolean prepare() {
				return true;
			}

			@Override
			public boolean canUndo() {
				return false;
			}

			public void redo() {
				// TODO Auto-generated method stub
			}

		};
		AdapterFactoryEditingDomain.getEditingDomainFor(getProject()).getCommandStack().execute(command);
		setupWatch.stop();
		final StopWatch iterationWatch = new StopWatch(ITERATIONS2);
		final AbstractCommand command2 = new AbstractCommand() {
			public void execute() {
				for (int i = 0; i < COUNT; i++) {
					final String name = ELEMENT + i;
					final TestElement testElement = Create.testElement(name);
					parentTestElement.getContainedElements().add(testElement);
				}
			}

			public void redo() {
				// TODO Auto-generated method stub

			}

			@Override
			protected boolean prepare() {
				return true;
			}

			@Override
			public boolean canUndo() {
				return false;
			}

		};
		AdapterFactoryEditingDomain.getEditingDomainFor(getProject()).getCommandStack().execute(command2);

		final int count = COUNT;
		assertEquals(count, parentTestElement.getContainedElements().size());
		iterationWatch.stop();
	}

	@Test
	public void testElementAddIterationWithSaveOptimized() {
		final Project project2 = ModelUtil.clone(getProject());
		final String testName = "EMFStore Plain with AutoSave but Optimized"; //$NON-NLS-1$
		final StopWatch allStopWatch = new StopWatch(testName);
		for (int i = 0; i < ITERATIONS; i++) {
			final StopWatch run = new StopWatch(i + " run"); //$NON-NLS-1$
			runOptimizedIterationTest();
			run.stop();
		}
		allStopWatch.stop();
		assertEquals(ITERATIONS, getProject().getModelElements().size());
		assertEquals(ITERATIONS * COUNT + ITERATIONS, getProject().getAllModelElements().size());
		for (final AbstractOperation operation : getProjectSpace().getOperations()) {
			operation.apply(project2);
		}
		assertEquals(true, ModelUtil.areEqual(getProject(), project2));
	}

	@Test
	public void testElementAddWithoutSaveOptimized() {
		final StopWatch allStopWatch = new StopWatch(
			"EMFStore Plain without AutoSave but with Save at End and Optimized"); //$NON-NLS-1$
		org.eclipse.emf.emfstore.internal.client.model.Configuration.getClientBehavior().setAutoSave(false);
		runOptimizedIterationTest();
		final ProjectSpaceBase projectSpaceBase = (ProjectSpaceBase) getProjectSpace();
		final StopWatch saveTimer = new StopWatch(SAVE);
		projectSpaceBase.save();
		saveTimer.stop();
		org.eclipse.emf.emfstore.internal.client.model.Configuration.getClientBehavior().setAutoSave(true);
		allStopWatch.stop();
	}

	private void runOptimizedIterationTest() {

		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);

		final StopWatch iterationWatch = new StopWatch(ITERATIONS2);
		for (int i = 0; i < COUNT; i++) {
			final String name = ELEMENT + i;
			final TestElement testElement = Create.testElement(name);
			parentTestElement.getContainedElements().add(testElement);
		}

		iterationWatch.stop();

		final StopWatch insertWatch = new StopWatch(INSERTION);
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(parentTestElement);
			}
		}.run(false);
		insertWatch.stop();
		assertEquals(COUNT, parentTestElement.getContainedElements().size());
	}

	@Test
	public void testElementAddIterationWithoutEMFStore() {
		final StopWatch allStopWatch = new StopWatch(EMF_ONLY_ALL);
		final TestElement parentTestElement = Create.testElement(PARENT_TEST_ELEMENT);

		final StopWatch iterationWatch = new StopWatch(ITERATIONS2).silent();
		for (int i = 0; i < 1000; i++) {
			final String name = ELEMENT + i;
			final TestElement testElement = Create.testElement(name);
			parentTestElement.getContainedElements().add(testElement);
		}

		iterationWatch.stop();

		allStopWatch.stop();
	}
}
