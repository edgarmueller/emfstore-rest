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
 *     b1     b2
 *     
 * v5 	      o
 * v4	o	  |
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

	final private PrimaryVersionSpec[] versions = { Versions.PRIMARY("trunk", 0), Versions.PRIMARY("trunk", 1),
		Versions.PRIMARY("b1", 2), Versions.PRIMARY("b2", 3), Versions.PRIMARY("b1", 4), Versions.PRIMARY("b2", 5), };

	final private String[] elementNames = { "v0", "v1", "v2", "v3", "v4", "v5" };

	final private String[] branches = { "b1", "b2" };

	private ProjectSpace createHistory() {
		ProjectSpace ps = getProjectSpace();
		// v0
		createTestElement(elementNames[0]);
		assertEquals(versions[0], share(ps));

		// v1
		rename(ps, 1);
		assertEquals(versions[1], commit(ps));
		ProjectSpace ps2 = reCheckout(ps);

		// v2
		rename(ps, 2);
		assertEquals(versions[2], branch(ps, branches[0]));

		// v3
		rename(ps2, 3);
		assertEquals(versions[3], branch(ps2, branches[1]));

		// v4
		rename(ps, 4);
		assertEquals(versions[4], commit(ps));

		// v5
		rename(ps2, 5);
		assertEquals(versions[5], commit(ps2));

		return ps;
	}

	private void rename(final ProjectSpace ps, final int nameIndex) {
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
		ProjectSpace ps = createHistory();

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
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[3], 5, 25, true, false,
			false, false));

		assertEquals(6, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[4], result.get(1).getPrimerySpec());
		assertEquals(versions[3], result.get(2).getPrimerySpec());
		assertEquals(versions[2], result.get(3).getPrimerySpec());
		assertEquals(versions[1], result.get(4).getPrimerySpec());
		assertEquals(versions[0], result.get(5).getPrimerySpec());
	}

	@Test
	public void rangequeryIncludeCp() throws EmfStoreException {
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[3], 5, 25, false, false,
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
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[5], 5, 1, false, false,
			false, false));

		assertEquals(2, result.size());
		assertEquals(versions[5], result.get(0).getPrimerySpec());
		assertEquals(versions[3], result.get(1).getPrimerySpec());
	}

	@Test
	public void rangequeryNoLower() throws EmfStoreException {
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[0], 1, 20, false, false,
			false, false));

		assertEquals(2, result.size());
		assertEquals(versions[1], result.get(0).getPrimerySpec());
		assertEquals(versions[0], result.get(1).getPrimerySpec());
	}

	@Test
	public void rangequeryLimitZero() throws EmfStoreException {
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[0], 0, 0, false, false,
			false, false));

		assertEquals(1, result.size());
		assertEquals(versions[0], result.get(0).getPrimerySpec());
	}

	@Test
	public void rangequeryIncoming() throws EmfStoreException {
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[0], 0, 0, false, false,
			false, false));

		// TODO implement
		assertTrue(false);
	}

	@Test
	public void rangequeryOutgoing() throws EmfStoreException {
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.rangeQuery(versions[0], 0, 0, false, false,
			false, false));

		// TODO implement
		assertTrue(false);
	}

	@Test
	public void pathQuery() throws EmfStoreException {
		ProjectSpace ps = createHistory();

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
		ProjectSpace ps = createHistory();

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
		ProjectSpace ps = createHistory();

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.pathQuery(versions[1], versions[3], true,
			false));

		assertEquals(3, result.size());
		assertEquals(versions[1], result.get(0).getPrimerySpec());
		assertEquals(versions[2], result.get(1).getPrimerySpec());
		assertEquals(versions[3], result.get(2).getPrimerySpec());
	}

	@Test(expected = InvalidVersionSpecException.class)
	public void invalidPathQuery() throws EmfStoreException {
		ProjectSpace ps = createHistory();

		ps.getHistoryInfo(HistoryQueryBuilder.pathQuery(versions[2], versions[3], false, false));
	}

	@Test
	public void meQuery() throws EmfStoreException {
		ProjectSpace ps = createHistory();
		EObject element = ps.getProject().getModelElements().get(0);
		ModelElementId id = ps.getProject().getModelElementId(element);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.modelelementQuery(versions[3], id, 0, 0,
			false, false));

		assertEquals(1, result.size());
		assertEquals(versions[3], result.get(0).getPrimerySpec());
	}

	@Test
	public void meQueryLimit() throws EmfStoreException {
		ProjectSpace ps = createHistory();
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
		ProjectSpace ps = createHistory();
		EObject element = ps.getProject().getModelElements().get(0);
		ModelElementId id = ps.getProject().getModelElementId(element);

		List<HistoryInfo> result = ps.getHistoryInfo(HistoryQueryBuilder.modelelementQuery(versions[3], id, 1, 1,
			false, false));

		assertEquals(1, result.size());
		assertEquals(versions[3], result.get(0).getPrimerySpec());
	}

	@Test
	public void meQueryDifferentBranchIncludeAll() throws EmfStoreException {
		ProjectSpace ps = createHistory();
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
