package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIRevertCommitController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;

public class UIRevertCommitControllerTest extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() throws ESException {
		assertEquals(0, localProject.getModelElements().size());
		final int localProjectsSize = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().size();
		final ESPrimaryVersionSpec baseVersion = localProject.getBaseVersion();
		createPlayerAndCommit();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIRevertCommitController revertCommitController = new UIRevertCommitController(
					bot.getDisplay().getActiveShell(),
					baseVersion,
					localProject);
				revertCommitController.execute();
			}
		});
		System.out.println("Waiting for shell");
		SWTBotShell shell = bot.shell("Confirmation");
		shell.bot().button("Yes").click();
		final ESLocalProject clonedProject = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()
			.get(localProjectsSize);
		System.out.println(localProjectsSize);
		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return localProjectsSize + 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().size()
					&& clonedProject.getModelElements().size() == 0;
			}

			public String getFailureMessage() {
				return "Revert did not succeed.";
			}
		}, timeout());
		assertEquals(0, clonedProject.getModelElements().size());
	}

}