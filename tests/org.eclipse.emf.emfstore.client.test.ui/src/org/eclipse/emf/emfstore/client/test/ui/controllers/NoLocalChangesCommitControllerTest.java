package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.client.ui.ESUIControllerFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.junit.Test;

public class NoLocalChangesCommitControllerTest extends AbstractUIControllerTest {

	@Override
	@Test
	public void testController() throws ESException {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				ESUIControllerFactory.INSTANCE.commitProject(
					bot.getDisplay().getActiveShell(),
					localProject);
			}
		});

		bot.shell("No local changes");
		bot.button("OK").click();
	}

}
