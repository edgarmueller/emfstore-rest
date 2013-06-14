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

		createPlayerAndCommit();
		update();

		Player player = (Player) getCheckedoutCopy().getModelElements().get(0);
		assertEquals(PLAYER_NAME, player.getName());
	}

}
