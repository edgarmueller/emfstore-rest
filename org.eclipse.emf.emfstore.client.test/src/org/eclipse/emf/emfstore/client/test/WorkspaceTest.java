/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */

package org.eclipse.emf.emfstore.client.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.CommonUtil;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.FileUtil;
import org.junit.After;
import org.junit.Before;

/**
 * Abstract Superclass for Workspace Tests. Provides Setup and Tear-down.
 * 
 * @author koegel
 */
public abstract class WorkspaceTest {
	protected Project project;
	protected ProjectSpace projectSpace;
	private Workspace workspace;

	/**
	 * Setup a dummy project for testing.
	 */
	@Before
	public void setupTest() {
		beforeHook();
		CommonUtil.setTesting(true);
		WorkspaceManager workspaceManager = WorkspaceManager.getInstance();
		ConnectionManager connectionManager = initConnectionManager();
		if (connectionManager != null) {
			workspaceManager.setConnectionManager(connectionManager);
		}
		workspace = workspaceManager.getCurrentWorkspace();
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				ProjectSpace localProjectSpace = workspace.createLocalProject("testProject", "test Project");
				setProjectSpace(localProjectSpace);
				setProject(getProjectSpace().getProject());
			}
		}.run(false);

	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void beforeHook() {
	}

	public ConnectionManager initConnectionManager() {
		return null;
	}

	/**
	 * Clean up workspace.
	 * 
	 * @throws IOException
	 */
	@After
	public void teardown() throws IOException {
		WorkspaceManager.getInstance().dispose();
		setProject(null);
		setProjectSpace(null);
		workspace = null;
		FileUtil.deleteDirectory(new File(Configuration.getWorkspaceDirectory()), true);
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
					WorkspaceManager.getInstance().getCurrentWorkspace().deleteProjectSpace(ps);
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
	private void setProjectSpace(ProjectSpace projectSpace) {
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
		getProjectSpace().getOperations().clear();
		getProjectSpace().getOperationManager().clearOperations();
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
}
