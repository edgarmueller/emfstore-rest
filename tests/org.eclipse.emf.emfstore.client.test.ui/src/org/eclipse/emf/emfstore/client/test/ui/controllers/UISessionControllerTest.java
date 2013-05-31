package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.internal.client.ui.controller.UILoginSessionController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UILogoutSessionController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.junit.Test;

/**
 * Tests login/logout
 * 
 * @author Edgar
 * 
 */
public class UISessionControllerTest extends AbstractUIControllerTest {

	private UILoginSessionController loginSessionController;
	private UILogoutSessionController logoutSessionController;

	@Override
	@Test
	public void testController() throws ESException {
		logoutSession();
		login();
		logout();
	}

	private void logoutSession() throws ESException {
		usersession.logout();
	}

	private void login() {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				loginSessionController = new UILoginSessionController(
					bot.getDisplay().getActiveShell(),
					server);
				loginSessionController.execute();
			}
		});
		SWTBotButton okButton = bot.button("OK");
		okButton.click();
		assertNotNull(server.getLastUsersession());
		usersession = server.getLastUsersession();
	}

	private void logout() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				logoutSessionController =
					new UILogoutSessionController(bot.getDisplay().getActiveShell(), usersession);
			}
		});
		assertNotNull(server.getLastUsersession());
	}
}
