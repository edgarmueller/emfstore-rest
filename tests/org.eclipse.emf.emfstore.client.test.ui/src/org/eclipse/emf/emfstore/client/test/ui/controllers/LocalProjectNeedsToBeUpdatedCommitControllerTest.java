package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.client.test.ui.AllUITests;
import org.eclipse.emf.emfstore.client.ui.ESUIControllerFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.junit.Test;

public class LocalProjectNeedsToBeUpdatedCommitControllerTest extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() throws ESException {
		checkout();

		createLeagueAndCommit();
		createPlayerAndCommit();

		getCopy().getModelElements().add(BowlingFactory.eINSTANCE.createPlayer());

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				ESUIControllerFactory.INSTANCE.commitProject(
					bot.getDisplay().getActiveShell(),
					getCopy());
			}
		});

		// confirm update dialogs
		bot.shell("Confirmation");
		bot.button("OK").click();
		bot.button("OK").click();

		// confirm commit dialog
		SWTBotButton buttonWithLabel = bot.button("OK");
		buttonWithLabel.click();

		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				return getCopy().getBaseVersion().getIdentifier() == 3;
			}

			public String getFailureMessage() {
				return "Commit did not succeed.";
			}
		}, AllUITests.TIMEOUT);

		assertEquals(3, getCopy().getAllModelElements().size());
	}

}
