/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

import junit.framework.Assert;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.junit.Test;

/**
 * Test creating an deleting elements.
 * 
 * @author koegel
 */
public class RecordingPerformanceTest extends WorkspaceTest {

	private static final int ITERATIONS = 100;
	private static final int COUNT = 10000;

	@Test
	public void testElementAddIterationWithSave() {
		StopWatch allStopWatch = new StopWatch("EMFStore Plain");
		runIterationTestWithoutOptimization();
		allStopWatch.stop();
	}

	@Test
	public void testElementAddIterationWithoutSave() {
		StopWatch allStopWatch = new StopWatch("EMFStore Plain without AutoSave but with Save at End");
		org.eclipse.emf.emfstore.client.model.Configuration.setAutoSave(false);
		runIterationTestWithoutOptimization();
		ProjectSpaceBase projectSpaceBase = (ProjectSpaceBase) getProjectSpace();
		StopWatch saveTimer = new StopWatch("Save");
		projectSpaceBase.save();
		saveTimer.stop();
		org.eclipse.emf.emfstore.client.model.Configuration.setAutoSave(true);
		allStopWatch.stop();
	}

	private void runIterationTestWithoutOptimization() {

		final TestElement parentTestElement = getTestElement("parentTestElement");

		StopWatch setupWatch = new StopWatch("Setup").silent();
		AbstractCommand command = new AbstractCommand() {
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
		StopWatch iterationWatch = new StopWatch("Iterations");
		AbstractCommand command2 = new AbstractCommand() {
			public void execute() {
				for (int i = 0; i < COUNT; i++) {
					final String name = "Element" + i;
					TestElement testElement = getTestElement(name);
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

		int count = COUNT;
		Assert.assertEquals(count, parentTestElement.getContainedElements().size());
		iterationWatch.stop();
	}

	@Test
	public void testElementAddIterationWithSaveOptimized() {
		Project project2 = ModelUtil.clone(getProject());
		String testName = "EMFStore Plain with AutoSave but Optimized";
		StopWatch allStopWatch = new StopWatch(testName);
		for (int i = 0; i < ITERATIONS; i++) {
			StopWatch run = new StopWatch(i + " run");
			runOptimizedIterationTest();
			run.stop();
		}
		allStopWatch.stop();
		Assert.assertEquals(ITERATIONS, getProject().getModelElements().size());
		Assert.assertEquals(ITERATIONS * COUNT + ITERATIONS, getProject().getAllModelElements().size());
		for (AbstractOperation operation : getProjectSpace().getOperations()) {
			operation.apply(project2);
		}
		Assert.assertEquals(true, ModelUtil.areEqual(project, project2));
	}

	@Test
	public void testElementAddWithoutSaveOptimized() {
		StopWatch allStopWatch = new StopWatch("EMFStore Plain without AutoSave but with Save at End and Optimized");
		org.eclipse.emf.emfstore.client.model.Configuration.setAutoSave(false);
		runOptimizedIterationTest();
		ProjectSpaceBase projectSpaceBase = (ProjectSpaceBase) getProjectSpace();
		StopWatch saveTimer = new StopWatch("Save");
		projectSpaceBase.save();
		saveTimer.stop();
		org.eclipse.emf.emfstore.client.model.Configuration.setAutoSave(true);
		allStopWatch.stop();
	}

	private void runOptimizedIterationTest() {

		final TestElement parentTestElement = getTestElement("parentTestElement");

		StopWatch iterationWatch = new StopWatch("Iterations");
		for (int i = 0; i < COUNT; i++) {
			final String name = "Element" + i;
			TestElement testElement = getTestElement(name);
			parentTestElement.getContainedElements().add(testElement);
		}

		iterationWatch.stop();

		StopWatch insertWatch = new StopWatch("Insertion");
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(parentTestElement);
			}
		}.run(false);
		insertWatch.stop();
		Assert.assertEquals(COUNT, parentTestElement.getContainedElements().size());
	}

	@Test
	public void testElementAddIterationWithoutEMFStore() {
		StopWatch allStopWatch = new StopWatch("EMF Only All");
		final TestElement parentTestElement = getTestElement("parentTestElement");

		StopWatch iterationWatch = new StopWatch("Iterations").silent();
		for (int i = 0; i < 1000; i++) {
			final String name = "Element" + i;
			TestElement testElement = getTestElement(name);
			parentTestElement.getContainedElements().add(testElement);
		}

		iterationWatch.stop();

		allStopWatch.stop();
	}
}
