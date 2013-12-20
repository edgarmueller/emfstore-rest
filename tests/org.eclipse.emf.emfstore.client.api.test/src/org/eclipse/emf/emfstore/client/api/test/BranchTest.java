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
package org.eclipse.emf.emfstore.client.api.test;

import static org.eclipse.emf.emfstore.client.api.test.ClientTestUtil.noCommitCallback;
import static org.eclipse.emf.emfstore.client.api.test.ClientTestUtil.noLogMessage;
import static org.eclipse.emf.emfstore.client.api.test.ClientTestUtil.noProgressMonitor;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.test.common.TestConflictResolver;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithSharedProject;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testing of branch related API.
 * 
 * @author emueller
 * 
 */
public class BranchTest extends ESTestWithSharedProject {

	private final String playerName1 = "player 1"; //$NON-NLS-1$
	private final String playerName2 = "player 2"; //$NON-NLS-1$
	private final String playerName3 = "player 3"; //$NON-NLS-1$
	private final String branchName = "mybranch"; //$NON-NLS-1$
	private final String trunkName = "trunk"; //$NON-NLS-1$

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Test
	public void branchFromTrunkMergeBackAndUpdateBranch() throws InvalidVersionSpecException,
		ESUpdateRequiredException, ESException {

		dummyChange(getLocalProject(), playerName1);
		getLocalProject().commit(noProgressMonitor());
		final ESLocalProject trunk = getLocalProject().getRemoteProject().checkout(trunkName, noProgressMonitor());

		// switch to branch
		getLocalProject().commitToBranch(
			ESVersionSpec.FACTORY.createBRANCH(branchName),
			noLogMessage(), noCommitCallback(), noProgressMonitor());

		dummyChange(getLocalProject(), playerName2);
		final ESPrimaryVersionSpec branchCommit = getLocalProject().commit(noLogMessage(), noCommitCallback(),
			noProgressMonitor());

		// merge into trunk
		// FIXME: merge API not yet available
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				((ESLocalProjectImpl) trunk).toInternalAPI().mergeBranch(
					((ESPrimaryVersionSpecImpl) branchCommit).toInternalAPI(),
					new TestConflictResolver(true, 0),
					noProgressMonitor());
				return null;
			}
		});

		trunk.commit(noProgressMonitor());

		dummyChange(getLocalProject(), playerName3);
		getLocalProject().commit(noProgressMonitor());

		assertEquals(3, getLocalProject().getModelElements().size());
	}

	@Test
	public void branchFromTrunkModifyTrunkAndMergeTrunkIntoBranch() throws InvalidVersionSpecException,
		ESUpdateRequiredException, ESException {

		// switch to branch
		getLocalProject().commitToBranch(
			ESVersionSpec.FACTORY.createBRANCH(branchName),
			noLogMessage(), noCommitCallback(), noProgressMonitor());

		final ESLocalProject trunk = getLocalProject().getRemoteProject().checkout(trunkName, noProgressMonitor());

		// modify trunk
		dummyChange(trunk, playerName1);
		final ESPrimaryVersionSpec trunkCommit = trunk.commit(noProgressMonitor());

		// merge trunk into branch
		// FIXME: merge API not yet available
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				((ESLocalProjectImpl) getLocalProject()).toInternalAPI().mergeBranch(
					((ESPrimaryVersionSpecImpl) trunkCommit).toInternalAPI(),
					new TestConflictResolver(true, 0),
					noProgressMonitor());
				return null;
			}
		});

		getLocalProject().commit(noProgressMonitor());

		assertEquals(1, getLocalProject().getModelElements().size());
	}

	private static void dummyChange(final ESLocalProject localProject, String name) {
		Add.toProject(localProject, Create.player(name));
	}
}
