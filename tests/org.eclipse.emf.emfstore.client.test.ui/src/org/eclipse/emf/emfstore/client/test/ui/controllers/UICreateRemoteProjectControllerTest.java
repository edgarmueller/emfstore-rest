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

import org.eclipse.emf.emfstore.internal.client.ui.controller.UICreateRemoteProjectController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Test;

public class UICreateRemoteProjectControllerTest extends AbstractUIControllerTest {

	UICreateRemoteProjectController createRemoteProjectController;

	@Override
	@Test
	public void testController() throws ESException {
		final int remoteProjectsSize = server.getRemoteProjects(usersession).size();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				createRemoteProjectController = new UICreateRemoteProjectController(
					bot.getDisplay().getActiveShell(),
					usersession);
				createRemoteProjectController.execute();
			}
		});
		SWTBotText text = bot.text(0);
		text.setText("foo");
		SWTBotButton okButton = bot.button("OK");
		okButton.click();
		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return server.getRemoteProjects(usersession).size() == remoteProjectsSize + 1;
			}

			public String getFailureMessage() {
				return "Create remote project did not succeed.";
			}
		}, timeout());
		assertEquals(remoteProjectsSize + 1, server.getRemoteProjects(usersession).size());
	}
}
