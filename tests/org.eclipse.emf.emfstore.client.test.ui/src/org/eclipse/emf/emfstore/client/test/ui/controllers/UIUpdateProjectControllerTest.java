/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests:
 * UpdateProjectController
 * CheckoutController
 * 
 * @author Edgar
 * 
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class UIUpdateProjectControllerTest extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() throws ESException {

		checkout();

		createPlayerAndCommit();
		updateCopy();

		Player player = (Player) getCopy().getModelElements().get(0);
		assertEquals(PLAYER_NAME, player.getName());
	}

}
