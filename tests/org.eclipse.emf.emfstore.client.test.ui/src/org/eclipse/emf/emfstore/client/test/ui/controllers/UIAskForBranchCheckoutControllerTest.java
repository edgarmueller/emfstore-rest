package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.Test;

public class UIAskForBranchCheckoutControllerTest extends AbstractUIControllerTestWithCommitAndBranch {

	@Override
	@Test
	public void testController() throws ESException {

		checkout();
		createPlayerAndCommit();
		createLeagueAndCommit();
		createBranch("foo-branch");

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				try {
					UICheckoutController checkoutController = new UICheckoutController(bot.getDisplay()
						.getActiveShell(),
						(ESPrimaryVersionSpec) null,
						localProject.getRemoteProject(),
						true);
					checkoutController.execute();
				} catch (ESException e) {
					fail(e.getMessage());
				}
			}
		});

		bot.text(0).setText("checkout");
		bot.button("OK").click();

		bot.table().select(1);
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				for (ESLocalProject localProject : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
					if (localProject.getProjectName().equals("checkout")) {
						return true;
					}
				}

				return false;
			}

			public String getFailureMessage() {
				return "Checkout did not succeed";
			}
		});
	}
}
