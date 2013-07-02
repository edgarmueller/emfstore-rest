/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException;
import org.eclipse.emf.emfstore.internal.server.exceptions.UnknownSessionException;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.query.ESHistoryQueryImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
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
	 * @throws ESException in case of failure
	 */
	@Test
	public void getProjectListTest() throws ESException {
		assertTrue(getServer().getRemoteProjects().size() == getProjectsOnServerBeforeTest());
	}

	/**
	 * Gets a project from the server.
	 * 
	 * @throws ESException in case of failure
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void getProjectTest() throws ESException {
		ProjectSpace projectSpace2 = new EMFStoreCommandWithResult<ProjectSpace>() {

			@Override
			protected ProjectSpace doRun() {
				try {
					ESLocalProject checkout = getRemoteProject().checkout("testCheckout", new NullProgressMonitor());
					return ((ESLocalProjectImpl) checkout).toInternalAPI();
				} catch (ESException e) {
					Assert.fail(e.getMessage());
					return null;
				}
			}
		}.run(false);
		assertEqual(getProject(), projectSpace2.getProject());
	}

	/**
	 * Creates a project on the server and then deletes it.
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      String, String, org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @throws ESException in case of failure.
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void createEmptyProjectTest() throws ESException {
		assertEquals(getProjectsOnServerBeforeTest(), getServer().getRemoteProjects().size());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				ESWorkspaceImpl workspaceImpl = ESWorkspaceProviderImpl.getInstance().getWorkspace();
				ProjectSpace projectSpace = workspaceImpl.createLocalProject("createEmptyProjectAndDelete")
					.toInternalAPI();
				try {
					projectSpace.shareProject(new NullProgressMonitor());
				} catch (ESException e) {
					Assert.fail();
				}
			}
		}.run(false);

		assertEquals(getProjectsOnServerBeforeTest() + 1, getServer().getRemoteProjects().size());
	}

	/**
	 * Creates a project, shares it with the server and then deletes it.
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.internal.server.model.SessionId,
	 *      String, String, org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage, Project)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.internal.server.model.SessionId)
	 * @throws ESException in case of failure.
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void shareProjectTest() throws ESException {
		assertTrue(getServer().getRemoteProjects().size() == getProjectsOnServerBeforeTest());

		ProjectSpace projectSpace2 = new EMFStoreCommandWithResult<ProjectSpace>() {

			@Override
			protected ProjectSpace doRun() {
				ESWorkspaceImpl workspaceImpl = ESWorkspaceProviderImpl.getInstance().getWorkspace();
				ProjectSpace projectSpace = workspaceImpl.createLocalProject("createEmptyProjectAndDelete")
					.toInternalAPI();
				try {
					projectSpace.shareProject(new NullProgressMonitor());
					return projectSpace;
				} catch (ESException e) {
					Assert.fail();
					return null;
				}
			}
		}.run(false);

		assertTrue(getServer().getRemoteProjects().size() == getProjectsOnServerBeforeTest() + 1);
		assertNotNull(getProject());
		assertEqual(getProject(), projectSpace2.getProject());
	}

	/**
	 * Deletes a project on the server.
	 * 
	 * @throws ESException in case of failure
	 */
	@Test
	public void deleteProjectTest() throws ESException, IOException {

		assertEquals(getProjectsOnServerBeforeTest(), getServer().getRemoteProjects().size());
		getRemoteProject().delete(new NullProgressMonitor());

		try {
			List<ESRemoteProject> remoteProjectList = getServer().getRemoteProjects();
			for (ESRemoteProject projectInfo : remoteProjectList) {
				if (projectInfo.getGlobalProjectId() == getProjectId()) {
					assertTrue(false);
				}
			}
			assertTrue(true);
		} catch (ESException e) {
			fail(e.getMessage());
		}

		assertEquals(getProjectsOnServerBeforeTest() - 1, getServer().getRemoteProjects().size());
	}

	/**
	 * Resolves version spec.
	 * 
	 * @throws ESException in case of failure.
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void resolveVersionSpecTest() throws ESException {

		List<ESRemoteProject> remoteProjectList = getServer().getRemoteProjects();
		NullProgressMonitor monitor = new NullProgressMonitor();
		boolean sameVersionSpec = false;
		for (ESRemoteProject project : remoteProjectList) {
			PrimaryVersionSpec internalAPIImpl = ((ESPrimaryVersionSpecImpl) project.getHeadVersion(monitor))
				.toInternalAPI();
			if (internalAPIImpl.equals(getProjectVersion())) {
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
	 * @throws ESException in case of failure
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void createVersionTest() throws ESException {

		PrimaryVersionSpec createdVersion = new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				getProject().addModelElement(
					org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE.createProjectHistory());

				try {
					// TODO: TQ cast
					return getProjectSpace().commit(
						SetupHelper.createLogMessage("bla", "blablba"), null, null);

				} catch (ESException e) {
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
	 * @throws ESException in case of failure
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void getEmptyChangesTest() throws ESException {
		List<ChangePackage> changes = getProjectSpace().getChanges(SetupHelper.createPrimaryVersionSpec(0),
			getProjectVersion());
		assertTrue(changes.size() == 0);
	}

	/**
	 * Gets changes.
	 * 
	 * @throws ESException in case of failure
	 * @throws SerializationException in case of failure
	 */
	/**
	 * @throws ESException
	 * @throws SerializationException
	 */
	/**
	 * @throws ESException
	 * @throws SerializationException
	 */
	@Test
	public void getChangesTest() throws ESException, SerializationException {

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(
					org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE.createProjectHistory());
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
					return getProjectSpace().commit(
						SetupHelper.createLogMessage("bla", "blablba"), null, null);

				} catch (ESException e) {
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
	 * @throws ESException in case of failure
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void getHistoryInfoTest() throws ESException {
		final String logMessage = "historyInfo";
		PrimaryVersionSpec createdVersion = new EMFStoreCommandWithResult<PrimaryVersionSpec>() {
			@Override
			protected PrimaryVersionSpec doRun() {
				getProject().addModelElement(
					org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE.createProjectHistory());

				try {
					// TODO: TQ cast
					return getProjectSpace().commit(
						SetupHelper.createLogMessage("bla", logMessage), null, null);

				} catch (ESException e) {
					throw new RuntimeException(e);
				}

			}
		}.run(false);

		HistoryQuery<ESHistoryQueryImpl<?, ?>> historyQuery = createHistoryQuery(createdVersion, createdVersion);
		List<ESHistoryInfo> historyInfos = getProjectSpace().toAPI().getHistoryInfos(
			historyQuery.toAPI(),
			new NullProgressMonitor());

		assertTrue(historyInfos.size() == 1);
		// TODO
		// assertTrue(historyInfo.get(0).getLogMessage().getMessage().equals(logMessage));
	}

	/**
	 * Sets a tag on the head revision.
	 * 
	 * @throws ESException in case of failure
	 */
	@Test
	public void addTagTest() throws ESException {
		String tagName = "testValue";
		NullProgressMonitor monitor = new NullProgressMonitor();
		TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tag.setName(tagName);

		// TOOD: add monitor parameter to internal API
		getProjectSpace().addTag(getProjectVersion(), tag);// , monitor);

		// TODO: TQ cast
		HistoryQuery<ESHistoryQueryImpl<?, ?>> historyQuery = createHistoryQuery(getProjectVersion(),
			getProjectVersion());
		List<ESHistoryInfo> historyInfos = getProjectSpace().toAPI().getHistoryInfos(
			historyQuery.toAPI(),
			new NullProgressMonitor());
		assertTrue(historyInfos.size() == 1);

		List<ESTagVersionSpec> tagSpecs = historyInfos.get(0).getTagSpecs();
		assertEquals(3, tagSpecs.size());
		assertEquals("HEAD", tagSpecs.get(0).getName());
		assertEquals("HEAD: trunk", tagSpecs.get(1).getName());
		assertEquals(tagName, tagSpecs.get(2).getName());
	}

	/**
	 * Removes a tag.
	 * 
	 * @throws ESException in case of failure.
	 */
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	@Test
	public void removeTagTest() throws ESException {
		String tagName = "testValue";

		TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
		tag.setName(tagName);
		NullProgressMonitor monitor = new NullProgressMonitor();

		getProjectSpace().addTag(getProjectVersion(), tag);
		getProjectSpace().removeTag(getProjectVersion(), tag);

		HistoryQuery<ESHistoryQueryImpl<?, ?>> historyQuery = createHistoryQuery(getProjectVersion(),
			getProjectVersion());
		List<ESHistoryInfo> historyInfos = getProjectSpace().toAPI().getHistoryInfos(
			historyQuery.toAPI(),
			new NullProgressMonitor());

		assertTrue(historyInfos.size() == 1);
		ESHistoryInfo historyInfo = historyInfos.get(0);
		List<ESTagVersionSpec> tagSpecs = historyInfo.getTagSpecs();
		assertEquals(2, tagSpecs.size());
		assertEquals("HEAD", tagSpecs.get(0).getName());
		assertEquals("HEAD: trunk", tagSpecs.get(1).getName());
	}

	/**
	 * Deletes session on server and creates new one.
	 * 
	 * @throws ESException in case of failure
	 */
	// @Test
	/**
	 * @throws ESException
	 */
	/**
	 * @throws ESException
	 */
	public void logOutTest() throws ESException {

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
