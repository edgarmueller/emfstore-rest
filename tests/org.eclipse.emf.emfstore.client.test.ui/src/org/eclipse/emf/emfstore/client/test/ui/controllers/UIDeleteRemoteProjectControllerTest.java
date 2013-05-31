package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.internal.client.ui.controller.UIDeleteRemoteProjectController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.Test;

public class UIDeleteRemoteProjectControllerTest extends AbstractUIControllerTest {

	@Override
	@Test
	public void testController() throws ESException {
		final int remoteProjectsSize = server.getRemoteProjects().size();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIDeleteRemoteProjectController deleteRemoteProjectController;
				try {
					deleteRemoteProjectController = new UIDeleteRemoteProjectController(bot.getDisplay()
						.getActiveShell(),
						usersession, localProject.getRemoteProject());
					deleteRemoteProjectController.execute();
				} catch (ESException e) {
					fail();
				}
			}
		});
		bot.button("Yes").click();
		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				return server.getRemoteProjects().size() == remoteProjectsSize - 1;
			}

			public String getFailureMessage() {
				return "Delete remote project did not succeed.";
			}
		});
		assertEquals(remoteProjectsSize - 1, server.getRemoteProjects().size());
	}

}
