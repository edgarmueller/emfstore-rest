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
package org.eclipse.emf.emfstore.client.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import junit.framework.Assert;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Abstract Superclass for Workspace Tests. Provides Setup and Tear-down.
 * 
 * @author koegel
 */
public abstract class WorkspaceTest {

	private Project project;
	private ProjectSpace projectSpace;
	protected ProjectSpaceBase clonedProjectSpace;
	// private static Workspace workspace;
	private boolean compareAtEnd = true;

	private static boolean saveState;

	@BeforeClass
	public static void beforeClass() {
		saveState = Configuration.getClientBehavior().isAutoSaveEnabled();
		Configuration.getClientBehavior().setAutoSave(true);
		CommonUtil.setTesting(true);
		ServerConfiguration.setTesting(true);

		cleanEmfstoreFolders();

		ESWorkspaceProviderImpl workspaceManager = ESWorkspaceProviderImpl.getInstance();
		workspaceManager.load();
		// workspace = (Workspace) workspaceManager.getWorkspace();
	}

	private static void cleanEmfstoreFolders() {
		try {
			FileUtil.deleteDirectory(new File(Configuration.getFileInfo().getWorkspaceDirectory()), true);
			FileUtil.deleteDirectory(new File(ServerConfiguration.getLocationProvider().getWorkspaceDirectory()), true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Setup a dummy project for testing.
	 */
	@Before
	public void setupTest() {
		configureCompareAtEnd();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				ESLocalProjectImpl localProject = ESWorkspaceProviderImpl.getInstance().getWorkspace()
					.createLocalProject("testProject");
				ProjectSpace localProjectSpace = localProject.toInternalAPI();
				setProjectSpace(localProjectSpace);
				setProject(getProjectSpace().getProject());

				if (isCompareAtEnd()) {
					WorkspaceBase workspace = (WorkspaceBase) ESWorkspaceProviderImpl.getInstance().getWorkspace()
						.toInternalAPI();
					workspace.cloneProject("clonedProject", getProject());
					clonedProjectSpace = (ProjectSpaceBase) workspace.cloneProject("clonedProject", getProject());
					Assert.assertTrue(ModelUtil.areEqual(projectSpace.getProject(), clonedProjectSpace.getProject()));
				}
			}
		}.run(false);
	}

	protected void configureCompareAtEnd() {
		setCompareAtEnd(true);
	}

	/**
	 * Clean up workspace.
	 * 
	 * @throws IOException
	 * @throws SerializationException
	 */
	@After
	public void teardown() throws IOException, SerializationException, ESException {
		boolean areEqual = false;
		projectSpace.save();

		String projectString = "";
		String clonedProjectString = "";

		if (isCompareAtEnd()) {
			// ProjectSpaceBase projectSpace = (ProjectSpaceBase) WorkspaceManager.getInstance().getCurrentWorkspace()
			// .getProjectSpaces().get(0);
			clonedProjectSpace.applyOperations(projectSpace.getOperations(), true);

			projectString = ModelUtil.eObjectToString(projectSpace.getProject());
			clonedProjectString = ModelUtil.eObjectToString(clonedProjectSpace.getProject());
			areEqual = ModelUtil.areEqual(projectSpace.getProject(), clonedProjectSpace.getProject());
			clonedProjectSpace.save();
		}

		cleanProjects();

		if (isCompareAtEnd()) {
			Assert.assertTrue("Projects are not equal\n\n " + projectString + "\n\n" + clonedProjectString, areEqual);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws IOException, ESException {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				ESWorkspaceProviderImpl.getInstance().dispose();
				Configuration.getClientBehavior().setAutoSave(saveState);
				cleanEmfstoreFolders();
				return null;
			}
		});
	}

	private void cleanProjects() {
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				try {

					ESWorkspaceImpl workspace = ESWorkspaceProviderImpl.getInstance().getWorkspace();
					for (ProjectSpace projectSpace : new ArrayList<ProjectSpace>(workspace.toInternalAPI()
						.getProjectSpaces())) {
						// TODO: monitor
						projectSpace.delete(new NullProgressMonitor());
					}

					setProject(null);
					setProjectSpace(null);
					// ESWorkspaceProviderImpl.getInstance().dispose();
					// workspace = null;
					// FileUtil.deleteDirectory(new File(Configuration.getWorkspaceDirectory()), true);
				} catch (IOException e) {
					// ignore
				}
			}
		}.run(false);
	}

	/**
	 * Clean workspace.
	 * 
	 * @param ps projectSpace
	 */
	public void cleanProjectSpace(final ProjectSpace ps) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				int retried = 0;
				try {
					// TODO: monitor
					ps.delete(new NullProgressMonitor());
				} catch (IOException e) {
					if (retried++ > 2) {
						fail();
					} else {
						try {
							Thread.sleep(retried * 1000);
						} catch (InterruptedException e1) {
							// ignore
						}
						WorkspaceUtil.logWarning(e.getMessage() + " Retrying...(" + retried + " out of 3)", e);
					}
				}
			}
		}.run(false);

		setProject(null);
		setProjectSpace(null);
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param projectSpace the projectSpace to set
	 */
	protected void setProjectSpace(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * @return the projectSpace
	 */
	public ProjectSpace getProjectSpace() {
		return projectSpace;
	}

	/**
	 * Clear all operations from project space.
	 */
	protected void clearOperations() {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				if (isCompareAtEnd()) {
					clonedProjectSpace.applyOperations(getProjectSpace().getOperations(), false);
					clonedProjectSpace
						.applyOperations(getProjectSpace().getOperationManager().clearOperations(), false);
				} else {
					getProjectSpace().getOperationManager().clearOperations();
				}
				getProjectSpace().getOperations().clear();
				return null;
			}
		});

	}

	/**
	 * Creates an test element.
	 * 
	 * @param name
	 * 
	 * @return test element
	 */
	protected TestElement createTestElementWithoutTransaction(String name) {
		TestElement element = TestmodelFactory.eINSTANCE.createTestElement();
		element.setName(name);
		getProject().getModelElements().add(element);
		return element;
	}

	protected TestElement createTestElementWithoutTransaction() {
		return createTestElement("");
	}

	/**
	 * Creates an test element.
	 * 
	 * @return test element
	 */
	protected TestElement getTestElement(String name) {
		TestElement element = TestmodelFactory.eINSTANCE.createTestElement();
		if (name != null) {
			element.setName(name);
		}
		return element;
	}

	public TestElement getTestElement() {
		return getTestElement("");
	}

	public TestElement createTestElement() {
		return createTestElement(null);
	}

	/**
	 * Creates a {@link TestElement} with the given name
	 * and adds it to the test project.
	 * 
	 * @param name
	 *            the name of test element
	 * @return the created test element
	 */
	public TestElement createTestElement(final String name) {
		return new EMFStoreCommandWithResult<TestElement>() {
			@Override
			protected TestElement doRun() {
				return createTestElementWithoutTransaction(name);
			}
		}.run(false);
	}

	public TestElement createFilledTestElement(final int count) {
		final TestElement testElement = createTestElement();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (int i = 0; i < count; i++) {
					testElement.getStrings().add("value" + i);
				}
			}
		}.run(false);

		return testElement;
	}

	public <T extends AbstractOperation> T checkAndCast(AbstractOperation op, Class<T> clazz) {
		Assert.assertTrue(clazz.isInstance(op));
		return asInstanceOf(op, clazz);
	}

	public <T extends AbstractOperation> T asInstanceOf(AbstractOperation op, Class<T> clazz) {
		return clazz.cast(op);
	}

	public boolean isCompareAtEnd() {
		return compareAtEnd;
	}

	protected void setCompareAtEnd(boolean compareAtEnd) {
		this.compareAtEnd = compareAtEnd;
	}
}
