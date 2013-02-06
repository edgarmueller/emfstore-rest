/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.impl.RemoteProject;
import org.eclipse.emf.emfstore.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.exceptions.UnknownSessionException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.OperationsFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * This TestCase tests all methods in the main {@link org.EMFStore.emfstore.EmfStore} interface.
 * 
 * @author wesendon
 */
public class ServerInterfaceTest extends ServerTests {

	/**
	 * Gets the list of all projects.
	 * 
	 * @throws EMFStoreException in case of failure
	 */
	@Test
	public void getProjectListTest() throws EMFStoreException {
		assertTrue(getServerInfo().getRemoteProjects(true).size() == getProjectsOnServerBeforeTest());
	}

	/**
	 * Gets a project from the server.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void getProjectTest() throws EMFStoreException {
		ProjectSpace projectSpace2 = new EMFStoreCommandWithResult<ProjectSpace>() {

			@Override
			protected ProjectSpace doRun() {
				try {
					return getRemoteProject().checkout(TestSessionProvider.getInstance().getDefaultUsersession());
				} catch (EMFStoreException e) {
					Assert.fail();
					return null;
				}
			}
		}.run(false);
		assertEqual(getProject(), projectSpace2.getProject());
	}

	/**
	 * Creates a project on the server and then deletes it.
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.server.model.SessionId, String, String,
	 *      org.eclipse.emf.emfstore.server.model.versioning.LogMessage)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.server.model.SessionId)
	 * @throws EmfStoreException in case of failure.
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void createEmptyProjectTest() throws EMFStoreException {
		assertEquals(getProjectsOnServerBeforeTest(), getServerInfo().getRemoteProjects(true).size());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				ProjectSpace projectSpace = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
					.createLocalProject("createEmptyProjectAndDelete", "TestProject");
				try {
					projectSpace.shareProject();
				} catch (EMFStoreException e) {
					Assert.fail();
				}
			}
		}.run(false);

		assertEquals(getProjectsOnServerBeforeTest() + 1, getServerInfo().getRemoteProjects(true).size());
	}

	/**
	 * Creates a project, shares it with the server and then deletes it.
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.server.model.SessionId, String, String,
	 *      org.eclipse.emf.emfstore.server.model.versioning.LogMessage, Project)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.server.model.SessionId)
	 * @throws EmfStoreException in case of failure.
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void shareProjectTest() throws EMFStoreException {
		assertTrue(getServerInfo().getRemoteProjects().size() == getProjectsOnServerBeforeTest());

		ProjectSpace projectSpace2 = new EMFStoreCommandWithResult<ProjectSpace>() {

			@Override
			protected ProjectSpace doRun() {
				ProjectSpace projectSpace = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
					.createLocalProject("createEmptyProjectAndDelete", "TestProject");
				try {
					projectSpace.shareProject();
					return projectSpace;
				} catch (EMFStoreException e) {
					Assert.fail();
					return null;
				}
			}
		}.run(false);

		assertTrue(getServerInfo().getRemoteProjects().size() == getProjectsOnServerBeforeTest() + 1);
		assertNotNull(getProject());
		assertEqual(getProject(), projectSpace2.getProject());
	}

	/**
	 * Deletes a project on the server.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	/**
	 * @throws EMFStoreException
	 * @throws IOException
	 */
	@Test
	public void deleteProjectTest() throws EMFStoreException, IOException {

		assertEquals(getProjectsOnServerBeforeTest(), getServerInfo().getRemoteProjects().size());

		getRemoteProject().delete();

		try {
			List<RemoteProject> remoteProjectList = getServerInfo().getRemoteProjects();
			for (RemoteProject projectInfo : remoteProjectList) {
				if (projectInfo.getProjectId() == getProjectId()) {
					assertTrue(false);
				}
			}
			assertTrue(true);
		} catch (EMFStoreException e) {
			assertTrue(true);
		}

		assertEquals(getProjectsOnServerBeforeTest() - 1, getServerInfo().getRemoteProjects().size());
	}

	/**
	 * Resolves version spec.
	 * 
	 * @throws EmfStoreException in case of failure.
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void resolveVersionSpecTest() throws EMFStoreException {

		List<RemoteProject> remoteProjectList = getServerInfo().getRemoteProjects();

		boolean sameVersionSpec = false;
		for (RemoteProject project : remoteProjectList) {
			if (project.getHeadVersion(true).equals(getProjectVersion())) {
				sameVersionSpec = true;
			}
		}

		if (sameVersionSpec) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	/**
	 * Creates a version.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void createVersionTest() throws EMFStoreException {

		PrimaryVersionSpec createdVersion = new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				getProject().addModelElement(
					org.eclipse.emf.emfstore.server.model.ModelFactory.eINSTANCE.createProjectHistory());

				try {
					// TODO: TQ cast
					return (PrimaryVersionSpec) getProjectSpace().commit(
						SetupHelper.createLogMessage("bla", "blablba"), null, null);

				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}

			}
		}.run(false);

		PrimaryVersionSpec baseVersion = getProjectSpace().getBaseVersion();

		assertTrue(baseVersion.equals(createdVersion));
	}

	/**
	 * Gets changes, which should be empty.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void getEmptyChangesTest() throws EMFStoreException {
		List<ChangePackage> changes = getProjectSpace().getChanges(SetupHelper.createPrimaryVersionSpec(0),
			getProjectVersion());
		assertTrue(changes.size() == 0);
	}

	/**
	 * Gets changes.
	 * 
	 * @throws EmfStoreException in case of failure
	 * @throws SerializationException in case of failure
	 */
	/**
	 * @throws EMFStoreException
	 * @throws SerializationException
	 */
	@Test
	public void getChangesTest() throws EMFStoreException, SerializationException {

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(
					org.eclipse.emf.emfstore.server.model.ModelFactory.eINSTANCE.createProjectHistory());
			}
		}.run(false);

		final AttributeOperation attributeOperation = OperationsFactory.eINSTANCE.createAttributeOperation();
		attributeOperation.setModelElementId(getProject().getModelElementId(
			(EObject) getProject().getAllModelElements().toArray()[0]));
		attributeOperation.setFeatureName("name");
		attributeOperation.setNewValue("nameeee");

		PrimaryVersionSpec resolvedVersionSpec = getProjectSpace().getBaseVersion();

		PrimaryVersionSpec versionSpec = new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				getProjectSpace().getOperations().add(attributeOperation);
				try {
					// TODO: TQ cast
					return (PrimaryVersionSpec) getProjectSpace().commit(
						SetupHelper.createLogMessage("bla", "blablba"), null, null);

				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}

			}
		}.run(false);
		// TODO: because commit cleared all local operations
		setCompareAtEnd(false);

		List<ChangePackage> changes = getProjectSpace().getChanges(resolvedVersionSpec, versionSpec);

		assertTrue(changes.size() == 1);
		for (ChangePackage cp : changes) {
			assertTrue(cp.getOperations().size() == 2);
			// TODO
			// assertTrue(ModelUtil.areEqual(cp.getOperations().get(1), attributeOperation));
		}

	}

	/**
	 * Gets a historyInfo.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void getHistoryInfoTest() throws EMFStoreException {
		final String logMessage = "historyInfo";
		PrimaryVersionSpec createdVersion = new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				getProject().addModelElement(
					org.eclipse.emf.emfstore.server.model.ModelFactory.eINSTANCE.createProjectHistory());

				try {
					// TODO: TQ cast
					return (PrimaryVersionSpec) getProjectSpace().commit(
						SetupHelper.createLogMessage("bla", logMessage), null, null);

				} catch (EMFStoreException e) {
					throw new RuntimeException(e);
				}

			}
		}.run(false);

		// TODO: TQ cast
		List<HistoryInfo> historyInfo = (List<HistoryInfo>) (List<?>) getProjectSpace().getHistoryInfos(
			createHistoryQuery(createdVersion, createdVersion));

		assertTrue(historyInfo.size() == 1);
		// assertTrue(historyInfo.get(0).getLogMessage().getMessage().equals(logMessage));
	}

	/**
	 * Sets a tag on the head revision.
	 * 
	 * @throws EMFStoreException in case of failure
	 */
	@Test
	public void addTagTest() throws EMFStoreException {
		String tagName = "testValue";

		TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tag.setName(tagName);

		getProjectSpace().addTag(getProjectVersion(), tag);

		// TODO: TQ cast
		List<HistoryInfo> historyInfo = (List<HistoryInfo>) (List<?>) getProjectSpace().getHistoryInfos(
			createHistoryQuery(getProjectVersion(), getProjectVersion()));

		assertTrue(historyInfo.size() == 1);
		EList<TagVersionSpec> tagSpecs = historyInfo.get(0).getTagSpecs();
		assertEquals(3, tagSpecs.size());
		assertEquals("HEAD", tagSpecs.get(0).getName());
		assertEquals("HEAD: trunk", tagSpecs.get(1).getName());
		assertEquals(tagName, tagSpecs.get(2).getName());
	}

	/**
	 * Removes a tag.
	 * 
	 * @throws EmfStoreException in case of failure.
	 */
	/**
	 * @throws EMFStoreException
	 */
	@Test
	public void removeTagTest() throws EMFStoreException {
		String tagName = "testValue";

		TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tag.setName(tagName);

		getProjectSpace().addTag(getProjectVersion(), tag);
		getProjectSpace().removeTag(getProjectVersion(), tag);

		// TODO: TQ cast
		List<HistoryInfo> historyInfo = (List<HistoryInfo>) (List<?>) getProjectSpace().getHistoryInfos(
			createHistoryQuery(getProjectVersion(), getProjectVersion()));

		assertTrue(historyInfo.size() == 1);
		EList<TagVersionSpec> tagSpecs = historyInfo.get(0).getTagSpecs();
		assertEquals(2, tagSpecs.size());
		assertEquals("HEAD", tagSpecs.get(0).getName());
		assertEquals("HEAD: trunk", tagSpecs.get(1).getName());
	}

	/**
	 * Deletes session on server and creates new one.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	// @Test
	/**
	 * @throws EMFStoreException
	 */
	public void logOutTest() throws EMFStoreException {

		Assert.assertNotNull(getConnectionManager().resolveUser(getSessionId(), null));
		getConnectionManager().logout(getSessionId());
		try {
			getConnectionManager().resolveUser(getSessionId(), null);
			// if no exception is thrown at this point, the test fails.
			Assert.assertTrue(false);
		} catch (UnknownSessionException e) {
			Assert.assertTrue(true);
		}
		// relogin for next test
		login();
		Assert.assertNotNull(getConnectionManager().resolveUser(getSessionId(), null));
	}

}