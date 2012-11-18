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
package org.eclipse.emf.emfstore.client.test.server.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryBuilder;
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
public class HistoryAPITests extends CoreServerTest {

	final static public PrimaryVersionSpec[] versions = { Versions.createPRIMARY("trunk", 0),
		Versions.createPRIMARY("trunk", 1), Versions.createPRIMARY("b1", 2), Versions.createPRIMARY("b2", 3),
		Versions.createPRIMARY("b1", 4), Versions.createPRIMARY("b2", 5), Versions.createPRIMARY("b3", 6),
		Versions.createPRIMARY("b3", 7) };

	final static public String[] elementNames = { "v0", "v1", "v2", "v3", "v4", "v5", "v6", "v7" };

	final static public String[] branches = { "b1", "b2", "b3" };

	public static ProjectSpace createHistory(CoreServerTest p) {
		ProjectSpace ps = p.getProjectSpace();
		// v0
		p.createTestElement(elementNames[0]);
		assertEquals(versions[0], p.share(ps));

		// v1
		rename(ps, 1);
		assertEquals(versions[1], p.commit(ps));
		ProjectSpace ps2 = p.reCheckout(ps);

		// v2
		rename(ps, 2);
		assertEquals(versions[2], p.branch(ps, branches[0]));

		// v3
		rename(ps2, 3);
		assertEquals(versions[3], p.branch(ps2, branches[1]));

		// v4
		rename(ps, 4);
		assertEquals(versions[4], p.commit(ps));

		// v5
		rename(ps2, 5);
		assertEquals(versions[5], p.commit(ps2));

		// v6
		ProjectSpace thirdBranch = p.checkout(ps.getProjectInfo(), versions[3]);
		rename(thirdBranch, 6);
		assertEquals(versions[6], p.branch(thirdBranch, branches[2]));

		// v7
		p.mergeWithBranch(thirdBranch, versions[5], 1);
		rename(thirdBranch, 7);
		assertEquals(versions[7], p.commit(thirdBranch));

		return ps;
	}

	private static void rename(final ProjectSpace ps, final int nameIndex) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				TestElement element = (TestElement) ps.getProject().getModelElements().get(0);
				element.setName(elementNames[nameIndex]);
			}
		}.run(false);
	}

	@Test
	public void rangequery() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[3], 5, 25, false, false,
			false, false));

		assertEquals(4, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
		assertEquals(versions[1], result.get(2).getPrimerySpec());
		assertEquals(versions[0], result.get(3).getPrimerySpec());
	}

	@Test
	public void rangequeryAllVersions() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[3], 5, 25, true, false,
			false, false));

		assertEquals(8, result.size());
		assertEquals(versions[7], result.get(0).getPrimerySpec());
		assertEquals(versions[6], result.get(1).getPrimerySpec());
		assertEquals(versions[5], result.get(2).getPrimerySpec());
		assertEquals(versions[4], result.get(3).getPrimerySpec());
		assertEquals(versions[3], result.get(4).getPrimerySpec());
		assertEquals(versions[2], result.get(5).getPrimerySpec());
		assertEquals(versions[1], result.get(6).getPrimerySpec());
		assertEquals(versions[0], result.get(7).getPrimerySpec());
	}

	@Test
	public void rangequeryIncludeCp() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[3], 1, 25, false, false,
			false, true));

		assertEquals(4, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
		assertEquals(versions[1], result.get(2).getPrimerySpec());
		assertEquals(versions[0], result.get(3).getPrimerySpec());

		assertTrue(result.get(0).getChangePackage() != null);
		assertTrue(result.get(1).getChangePackage() != null);
		assertTrue(result.get(2).getChangePackage() != null);
		// version 0
		assertTrue(result.get(3).getChangePackage() == null);
	}

	@Test
	public void rangequeryNoUpper() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[5], 5, 1, false, false,
			false, false));

		assertEquals(2, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
	}

	@Test
	public void rangequeryNoLower() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[0], 1, 20, false, false,
			false, false));

		assertEquals(2, result.size());
		assertEquals(versions[1], result.get(0).getPrimerySpec());
		assertEquals(versions[0], result.get(1).getPrimerySpec());
	}

	@Test
	public void rangequeryLimitZero() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[0], 0, 0, false, false,
			false, false));

		assertEquals(1, result.size());
		assertEquals(versions[0], result.get(0).getPrimerySpec());
	}

	@Test
	public void rangequeryIncoming() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[7], 0, 2, false, true,
			false, false));

		assertEquals(3, result.size());
		assertEquals(versions[7], result.get(0).getPrimerySpec());
		assertEquals(versions[6], result.get(1).getPrimerySpec());
		assertEquals(versions[5], result.get(2).getPrimerySpec());
	}

	@Test
	public void rangequeryOutgoing() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[3], 2, 0, false, false,
			true, false));

		assertEquals(3, result.size());
		assertEquals(versions[6], result.get(0).getPrimerySpec());
		assertEquals(versions[5], result.get(1).getPrimerySpec());
		assertEquals(versions[3], result.get(2).getPrimerySpec());
	}

	@Test
	public void pathQuery() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.pathQuery(versions[0], versions[5], false,
			false));

		assertEquals(4, result.size());
		assertEquals(versions[0], result.get(0).getPrimerySpec());
		assertEquals(versions[1], result.get(1).getPrimerySpec());
		assertEquals(versions[3], result.get(2).getPrimerySpec());
		assertEquals(versions[5], result.get(3).getPrimerySpec());
	}

	@Test
	public void pathQueryInverse() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.pathQuery(versions[5], versions[0], false,
			false));

		assertEquals(4, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
		assertEquals(versions[1], result.get(2).getPrimerySpec());
		assertEquals(versions[0], result.get(3).getPrimerySpec());
	}

	@Test
	public void pathQueryAllVersions() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.pathQuery(versions[1], versions[3], true,
			false));

		assertEquals(3, result.size());
		assertEquals(versions[1], result.get(0).getPrimerySpec());
		assertEquals(versions[2], result.get(1).getPrimerySpec());
		assertEquals(versions[3], result.get(2).getPrimerySpec());
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void invalidPathQuery() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);

		ps.getHistoryInfo(HistoryQueryBuilder.pathQuery(versions[2], versions[3], false, false));
	}

	@Test
	public void meQuery() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);
		EObject element = ps.getProject().getModelElements().get(0);
		ModelElementId id = ps.getProject().getModelElementId(element);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.modelelementQuery(versions[3], id, 0, 0,
			false, false));

		assertEquals(1, result.size());
		assertEquals(versions[3], result.get(0).getPrimerySpec());
	}

	@Test
	public void meQueryLimit() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);
		EObject element = ps.getProject().getModelElements().get(0);
		ModelElementId id = ps.getProject().getModelElementId(element);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.modelelementQuery(versions[1], id, 0, 1,
			false, false));

		assertEquals(2, result.size());
		assertEquals(versions[1], result.get(0).getPrimerySpec());
		assertEquals(versions[0], result.get(1).getPrimerySpec());
	}

	@Test
	public void meQueryDifferentBranch() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);
		EObject element = ps.getProject().getModelElements().get(0);
		ModelElementId id = ps.getProject().getModelElementId(element);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.modelelementQuery(versions[3], id, 1, 1,
			false, false));

		assertEquals(3, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
		assertEquals(versions[1], result.get(2).getPrimerySpec());
	}

	@Test
	public void meQueryDifferentBranchIncludeAll() throws EmfStoreException {
		ProjectSpace ps = createHistory(this);
		EObject element = ps.getProject().getModelElements().get(0);
		ModelElementId id = ps.getProject().getModelElementId(element);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.modelelementQuery(versions[3], id, 1, 1, true,
			false));

		assertEquals(3, result.size());
		assertEquals(versions[4], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
		assertEquals(versions[2], result.get(2).getPrimerySpec());
	}
}