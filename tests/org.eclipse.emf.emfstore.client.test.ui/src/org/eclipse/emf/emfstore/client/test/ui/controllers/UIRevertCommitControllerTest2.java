/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.test.api.ProjectChangeUtil;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIRevertCommitController;
import org.eclipse.emf.emfstore.server.ESConflictSet;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;

public class UIRevertCommitControllerTest2 extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() throws ESException {
		assertEquals(0, localProject.getModelElements().size());
		ESUpdateCallback updateCallback = new MyUpdateCallback();
		IProgressMonitor monitor = new NullProgressMonitor();

		// create checkout
		checkout();
		createPlayerAndTournamentAndCommit();
		final ESPrimaryVersionSpec baseVersion = localProject.getBaseVersion();

		// update checkout
		getCheckedoutCopy().update(localProject.getBaseVersion(), updateCallback, monitor);

		// delete player
		deleteTournamentAndCommit();
		assertEquals(1, localProject.getModelElements().size());

		// update checkout
		getCheckedoutCopy().update(localProject.getBaseVersion(), updateCallback, monitor);
		assertEquals(1, getCheckedoutCopy().getModelElements().size());

		// revert to version where tournament has been created
		revertAndCommit(baseVersion);

		// update checkout
		getCheckedoutCopy().update(ESVersionSpec.FACTORY.createHEAD(), updateCallback, monitor);
		assertEquals(2, getCheckedoutCopy().getModelElements().size());
		Tournament tournament = getCheckedoutCopy().getAllModelElementsByClass(Tournament.class).iterator().next();
		assertEquals(new Integer(32), tournament.getPlayerPoints().values().iterator().next());

		// revert again, should have no effect
		revertAndCommit(baseVersion);
		getCheckedoutCopy().update(ESVersionSpec.FACTORY.createHEAD(), updateCallback, monitor);

		assertEquals(2, getCheckedoutCopy().getModelElements().size());
	}

	private void createPlayerAndTournamentAndCommit() {
		final Player player = ProjectChangeUtil.createPlayer("player");
		final Tournament tournament = BowlingFactory.eINSTANCE.createTournament();
		tournament.getPlayerPoints().put(player, 32);

		final ESPrimaryVersionSpec baseVersion = localProject.getBaseVersion();
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(player);
				localProject.getModelElements().add(tournament);
				return null;
			}
		});
		commit();
	}

	private void revertAndCommit(final ESPrimaryVersionSpec baseVersion) throws ESException {

		final int localProjectsSize = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().size();

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIRevertCommitController revertCommitController = new UIRevertCommitController(
					bot.getDisplay().getActiveShell(),
					baseVersion,
					localProject);
				revertCommitController.execute();
			}
		});

		SWTBotShell shell = bot.shell("Confirmation");
		shell.bot().button("OK").click();

		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return localProjectsSize + 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().size();
			}

			public String getFailureMessage() {
				return "Revert did not succeed.";
			}
		}, timeout());

		List<ESLocalProject> localProjects = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects();
		ESLocalProject localProject = localProjects.get(localProjects.size() - 1);
		localProject.commit(new NullProgressMonitor());

	}

	protected void deleteTournamentAndCommit() {
		assertEquals(2, localProject.getModelElements().size());
		final ESPrimaryVersionSpec baseVersion = localProject.getBaseVersion();
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				Tournament tournament = localProject.getAllModelElementsByClass(Tournament.class).iterator().next();
				localProject.getModelElements().remove(tournament);
				return null;
			}
		});
		commit();
		assertEquals(1, localProject.getModelElements().size());
	}

	private class MyUpdateCallback implements ESUpdateCallback {

		public boolean inspectChanges(ESLocalProject projectSpace, List<ESChangePackage> changes,
			ESModelElementIdToEObjectMapping idToEObjectMapping) {
			return ESUpdateCallback.NOCALLBACK.inspectChanges(projectSpace, changes, idToEObjectMapping);
		}

		public void noChangesOnServer() {
			ESUpdateCallback.NOCALLBACK.noChangesOnServer();
		}

		public boolean conflictOccurred(ESConflictSet changeConflictException,
			IProgressMonitor progressMonitor) {
			return ESUpdateCallback.NOCALLBACK.conflictOccurred(changeConflictException, progressMonitor);
		}
	}

}
