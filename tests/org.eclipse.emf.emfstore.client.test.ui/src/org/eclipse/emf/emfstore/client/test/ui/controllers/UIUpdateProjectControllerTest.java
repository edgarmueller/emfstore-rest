package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

/**
 * Tests:
 * UpdateProjectController
 * CheckoutController
 * 
 * @author Edgar
 * 
 */
public class UIUpdateProjectControllerTest extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() throws ESException {

		checkout();

		bot.text().setText("foo");
		bot.button("OK").click();

		createPlayerAndCommit();
		update();

		Player player = (Player) getCheckedoutCopy().getModelElements().get(0);
		assertEquals(PLAYER_NAME, player.getName());
	}

}
