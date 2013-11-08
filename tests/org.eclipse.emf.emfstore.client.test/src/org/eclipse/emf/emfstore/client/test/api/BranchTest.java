/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.api;

import static org.eclipse.emf.emfstore.client.test.api.ClientTestUtil.noCommitCallback;
import static org.eclipse.emf.emfstore.client.test.api.ClientTestUtil.noLogMessage;
import static org.eclipse.emf.emfstore.client.test.api.ClientTestUtil.noProgressMonitor;
import static org.eclipse.emf.emfstore.client.test.api.ClientTestUtil.noUpdateCallback;
import static org.junit.Assert.assertEquals;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.test.server.api.util.TestConflictResolver;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.Test;

/**
 * @author emueller
 * 
 */
public class BranchTest extends BaseSharedProjectTest {

	@Test
	public void branchFromTrunkMergeBackAndUpdateBranch() throws InvalidVersionSpecException,
		ESUpdateRequiredException, ESException {

		dummyChange(localProject, "element 1");
		localProject.commit(noProgressMonitor());
		final ESLocalProject trunk = localProject.getRemoteProject().checkout("trunk", noProgressMonitor());

		// switch to branch
		localProject.commitToBranch(
			ESVersionSpec.FACTORY.createBRANCH("mybranch"),
			noLogMessage(), noCommitCallback(), noProgressMonitor());

		dummyChange(localProject, "element 2");
		final ESPrimaryVersionSpec branchCommit = localProject.commit(noLogMessage(), noCommitCallback(),
			noProgressMonitor());

		// merge into trunk
		// FIXME: merge API not yet available
		((ESLocalProjectImpl) trunk).toInternalAPI().mergeBranch(
			((ESPrimaryVersionSpecImpl) branchCommit).toInternalAPI(),
			new TestConflictResolver(true, 0),
			noProgressMonitor());

		trunk.commit(noProgressMonitor());

		assertEquals(2, localProject.getModelElements().size());

		dummyChange(localProject, "element 3");
		localProject.commit(noProgressMonitor());

		localProject.update(ESVersionSpec.FACTORY.createHEAD("mybranch"), noUpdateCallback(), noProgressMonitor());
	}

	@Test
	public void branchFromTrunkModifyTrunkAndMergeTrunkIntoBranch() throws InvalidVersionSpecException,
		ESUpdateRequiredException, ESException {

		// switch to branch
		localProject.commitToBranch(
			ESVersionSpec.FACTORY.createBRANCH("mybranch"),
			noLogMessage(), noCommitCallback(), noProgressMonitor());

		final ESLocalProject trunk = localProject.getRemoteProject().checkout("trunk", noProgressMonitor());

		// modify trunk
		dummyChange(trunk, "element 1");
		final ESPrimaryVersionSpec trunkCommit = trunk.commit(noProgressMonitor());

		// merge trunk into branch
		// FIXME: merge API not yet available
		((ESLocalProjectImpl) localProject).toInternalAPI().mergeBranch(
			((ESPrimaryVersionSpecImpl) trunkCommit).toInternalAPI(),
			new TestConflictResolver(true, 0),
			noProgressMonitor());

		localProject.commit(noProgressMonitor());

		assertEquals(1, localProject.getModelElements().size());

		localProject.update(noProgressMonitor());
	}

	private static void dummyChange(ESLocalProject localProject, String name) {
		final TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
		testElement.setName(name);
		localProject.getModelElements().add(testElement);
	}
}
