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
package org.eclipse.emf.emfstore.server.test;

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.addElement;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.branch;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.checkout;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.commit;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.defaultName;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.mergeWithBranch;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.rename;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.CreateAPI;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.query.ESHistoryQuery;
import org.eclipse.emf.emfstore.server.model.query.ESModelElementQuery;
import org.eclipse.emf.emfstore.server.model.query.ESPathQuery;
import org.eclipse.emf.emfstore.server.model.query.ESRangeQuery;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Branches for history test
 * 
 * <pre>
 *     b1    b2    b3
 * 
 * v7              o
 * 				/  |
 * v6         /    o
 * v5 	      o   / 
 * v4	o	  |  /
 * v3	|	  o
 * v2	o	 /
 * v1 	 \ o
 *     	   |
 * v0  	   o
 * </pre>
 * 
 * @author wesendon
 * 
 */
public class HistoryAPITests {

	final static public ESPrimaryVersionSpec[] versions = {
		ESVersionSpec.FACTORY.createPRIMARY("trunk", 0),
		ESVersionSpec.FACTORY.createPRIMARY("trunk", 1),
		ESVersionSpec.FACTORY.createPRIMARY("b1", 2),
		ESVersionSpec.FACTORY.createPRIMARY("b2", 3),
		ESVersionSpec.FACTORY.createPRIMARY("b1", 4),
		ESVersionSpec.FACTORY.createPRIMARY("b2", 5),
		ESVersionSpec.FACTORY.createPRIMARY("b3", 6),
		ESVersionSpec.FACTORY.createPRIMARY("b3", 7) };

	final static public String[] elementNames = { "v0", "v1", "v2", "v3", "v4", "v5", "v6", "v7" };

	final static public String[] branches = { "b1", "b2", "b3" };

	private static ESServer server;
	private static ESUsersession session;
	private static ESLocalProject readOnlyProject;

	@BeforeClass
	public static void beforeClass() {
		try {
			server = ServerUtil.startMockServer().getServer();
			session = server.login(
				ServerUtil.superUser(),
				ServerUtil.superUserPassword());
			readOnlyProject = createHistory();
		} catch (final IllegalArgumentException ex) {
			fail(ex.getMessage());
		} catch (final ESServerStartFailedException ex) {
			fail(ex.getMessage());
		} catch (final FatalESException ex) {
			fail(ex.getMessage());
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
	}

	@AfterClass
	public static void afterClass() {
		ServerUtil.stopServer();
	}

	public static ESLocalProject getProject() {
		final ProjectSpace clone = ModelUtil.clone(((ESLocalProjectImpl) readOnlyProject).toInternalAPI());
		clone.setProject(ModelUtil.clone(((ESLocalProjectImpl) readOnlyProject).toInternalAPI().getProject()));
		return clone.toAPI();
	}

	public static ESLocalProject createHistory() throws ESException {
		final ESLocalProject localProject = share(session, CreateAPI.project(defaultName()));

		// v0
		final TestElement testElement = Create.testElement(elementNames[0]);
		addElement(localProject, testElement);
		assertEquals(versions[0], share(session, localProject).getBaseVersion());

		// v1
		rename(localProject, elementNames[1]);
		assertEquals(versions[1], commit(localProject).getBaseVersion());
		final ESLocalProject localProject2 = checkout(localProject);

		// v2
		rename(localProject, elementNames[2]);
		final ESLocalProject branch2 = branch(localProject, branches[0]);
		assertEquals(versions[2], branch2.getBaseVersion());

		// v3
		rename(localProject2, elementNames[3]);
		final ESLocalProject branch3 = branch(localProject2, branches[1]);
		assertEquals(versions[3], branch3.getBaseVersion());

		// v4
		rename(localProject, elementNames[4]);
		assertEquals(versions[4], commit(localProject).getBaseVersion());

		// v5
		rename(localProject2, elementNames[5]);
		assertEquals(versions[5], commit(localProject2).getBaseVersion());

		// v6
		final ESLocalProject localProject3 = checkout(localProject, versions[3]);
		rename(localProject3, elementNames[6]);
		assertEquals(versions[6], branch(localProject3, branches[2]).getBaseVersion());

		// v7
		mergeWithBranch(localProject3, versions[5], 1);
		rename(localProject3, elementNames[7]);
		final ESLocalProject commit2 = commit(localProject3);
		assertEquals(versions[7], commit2.getBaseVersion());

		return localProject;
	}

	@Test
	public void rangeQuery() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[3], 5, 25, false, false, false,
			false);

		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(4, result.size());
		assertEquals(versions[5], result.get(0).getPrimarySpec());
		assertEquals(versions[3], result.get(1).getPrimarySpec());
		assertEquals(versions[1], result.get(2).getPrimarySpec());
		assertEquals(versions[0], result.get(3).getPrimarySpec());
	}

	@Test
	public void rangequeryAllVersions() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[3], 5, 25, true, false, false,
			false);

		final List<ESHistoryInfo> result = localProject.getHistoryInfos(rangeQuery,
			new NullProgressMonitor());

		assertEquals(8, result.size());
		assertEquals(versions[7], result.get(0).getPrimarySpec());
		assertEquals(versions[6], result.get(1).getPrimarySpec());
		assertEquals(versions[5], result.get(2).getPrimarySpec());
		assertEquals(versions[4], result.get(3).getPrimarySpec());
		assertEquals(versions[3], result.get(4).getPrimarySpec());
		assertEquals(versions[2], result.get(5).getPrimarySpec());
		assertEquals(versions[1], result.get(6).getPrimarySpec());
		assertEquals(versions[0], result.get(7).getPrimarySpec());
	}

	@Test
	public void rangequeryIncludeCp() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[3], 1,
			25, false, false, false, true);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(4, result.size());
		assertEquals(versions[5], result.get(0).getPrimarySpec());
		assertEquals(versions[3], result.get(1).getPrimarySpec());
		assertEquals(versions[1], result.get(2).getPrimarySpec());
		assertEquals(versions[0], result.get(3).getPrimarySpec());

		assertTrue(result.get(0).getChangePackage() != null);
		assertTrue(result.get(1).getChangePackage() != null);
		assertTrue(result.get(2).getChangePackage() != null);
		// version 0
		assertTrue(result.get(3).getChangePackage() == null);
	}

	@Test
	public void rangequeryNoUpper() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[5], 5,
			1, false, false, false, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(2, result.size());
		assertEquals(versions[5], result.get(0).getPrimarySpec());
		assertEquals(versions[3], result.get(1).getPrimarySpec());
	}

	@Test
	public void rangequeryNoLower() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[0], 1,
			20, false, false, false, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(2, result.size());
		assertEquals(versions[1], result.get(0).getPrimarySpec());
		assertEquals(versions[0], result.get(1).getPrimarySpec());
	}

	@Test
	public void rangequeryLimitZero() throws ESException {
		final ESLocalProject localProject = getProject();
		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[0], 0,
			0, false, false, false, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(1, result.size());
		assertEquals(versions[0], result.get(0).getPrimarySpec());
	}

	@Test
	public void rangequeryIncoming() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[7], 0,
			2, false, true, false, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(3, result.size());
		assertEquals(versions[7], result.get(0).getPrimarySpec());
		assertEquals(versions[6], result.get(1).getPrimarySpec());
		assertEquals(versions[5], result.get(2).getPrimarySpec());
	}

	@Test
	public void rangequeryOutgoing() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESRangeQuery<?> rangeQuery = ESHistoryQuery.FACTORY.rangeQuery(versions[3], 2,
			0, false, false, true, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(rangeQuery, new NullProgressMonitor());

		assertEquals(3, result.size());
		assertEquals(versions[6], result.get(0).getPrimarySpec());
		assertEquals(versions[5], result.get(1).getPrimarySpec());
		assertEquals(versions[3], result.get(2).getPrimarySpec());
	}

	@Test
	public void pathQuery() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESPathQuery pathQuery = ESHistoryQuery.FACTORY.pathQuery(versions[0], versions[5], false, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(pathQuery, new NullProgressMonitor());

		assertEquals(4, result.size());
		assertEquals(versions[0], result.get(0).getPrimarySpec());
		assertEquals(versions[1], result.get(1).getPrimarySpec());
		assertEquals(versions[3], result.get(2).getPrimarySpec());
		assertEquals(versions[5], result.get(3).getPrimarySpec());
	}

	@Test
	public void pathQueryInverse() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESPathQuery pathQuery = ESHistoryQuery.FACTORY.pathQuery(versions[5], versions[0], false, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(pathQuery, new NullProgressMonitor());

		assertEquals(4, result.size());
		assertEquals(versions[5], result.get(0).getPrimarySpec());
		assertEquals(versions[3], result.get(1).getPrimarySpec());
		assertEquals(versions[1], result.get(2).getPrimarySpec());
		assertEquals(versions[0], result.get(3).getPrimarySpec());
	}

	@Test
	public void pathQueryAllVersions() throws ESException {
		final ESLocalProject localProject = getProject();

		final ESPathQuery pathQuery = ESHistoryQuery.FACTORY.pathQuery(versions[1], versions[3], true, false);
		final List<ESHistoryInfo> result = localProject
			.getHistoryInfos(pathQuery, new NullProgressMonitor());

		assertEquals(3, result.size());
		assertEquals(versions[1], result.get(0).getPrimarySpec());
		assertEquals(versions[2], result.get(1).getPrimarySpec());
		assertEquals(versions[3], result.get(2).getPrimarySpec());
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void invalidPathQuery() throws ESException {
		final ESLocalProject ploEsLocalProject = getProject();
		final ESPathQuery pathQuery = ESHistoryQuery.FACTORY.pathQuery(versions[2], versions[3], false, false);
		ploEsLocalProject.getHistoryInfos(pathQuery, new NullProgressMonitor());
	}

	@Test
	public void meQuery() throws ESException {
		final ESLocalProject localProject = getProject();
		final EObject element = localProject.getModelElements().get(0);
		final ESModelElementId modelElementId = localProject.getModelElementId(element);

		final ESModelElementQuery modelElementQuery = ESHistoryQuery.FACTORY.modelElementQuery(versions[3],
			modelElementId, 0, 0, false, false);
		final List<ESHistoryInfo> result = localProject.getHistoryInfos(modelElementQuery,
			new NullProgressMonitor());

		assertEquals(1, result.size());
		assertEquals(versions[3], result.get(0).getPrimarySpec());
	}

	@Test
	public void meQueryLimit() throws ESException {
		final ESLocalProject localProject = getProject();
		final EObject element = localProject.getModelElements().get(0);
		final ESModelElementId id = localProject.getModelElementId(element);

		final ESModelElementQuery modelElementQuery = ESHistoryQuery.FACTORY.modelElementQuery(versions[1],
			id, 0, 1, false, false);
		final List<ESHistoryInfo> result = localProject.getHistoryInfos(modelElementQuery,
			new NullProgressMonitor());

		assertEquals(2, result.size());
		assertEquals(versions[1], result.get(0).getPrimarySpec());
		assertEquals(versions[0], result.get(1).getPrimarySpec());
	}

	@Test
	public void meQueryDifferentBranch() throws ESException {
		final ESLocalProject localProject = getProject();
		final EObject element = localProject.getModelElements().get(0);
		final ESModelElementId id = localProject.getModelElementId(element);

		final ESModelElementQuery modelelementQuery = ESHistoryQuery.FACTORY.modelElementQuery(versions[3],
			id, 1, 1, false, false);
		final List<ESHistoryInfo> result = localProject.getHistoryInfos(modelelementQuery,
			new NullProgressMonitor());

		assertEquals(3, result.size());
		assertEquals(versions[5], result.get(0).getPrimarySpec());
		assertEquals(versions[3], result.get(1).getPrimarySpec());
		assertEquals(versions[1], result.get(2).getPrimarySpec());
	}

	@Test
	public void meQueryDifferentBranchIncludeAll() throws ESException {
		final ESLocalProject localProject = getProject();
		final EObject element = localProject.getModelElements().get(0);
		final ESModelElementId id = localProject.getModelElementId(element);

		final ESModelElementQuery modelElementQuery = ESHistoryQuery.FACTORY.modelElementQuery(versions[3],
			id, 1, 1, true, false);
		final List<ESHistoryInfo> result = localProject.getHistoryInfos(modelElementQuery,
			new NullProgressMonitor());

		assertEquals(3, result.size());
		assertEquals(versions[4], result.get(0).getPrimarySpec());
		assertEquals(versions[3], result.get(1).getPrimarySpec());
		assertEquals(versions[2], result.get(2).getPrimarySpec());
	}
}
