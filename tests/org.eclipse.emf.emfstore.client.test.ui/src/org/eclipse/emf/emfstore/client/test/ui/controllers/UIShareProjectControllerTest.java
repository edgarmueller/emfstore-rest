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

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICreateLocalProjectController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIShareProjectController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Test;

/**
 * Tests: create local project controller, share controler
 * 
 * @author Edgar
 * 
 */
public class UIShareProjectControllerTest extends AbstractUIControllerTest {

	private ESLocalProject localProject;

	@Override
	@Test
	public void testController() throws ESException {
		usersession.refresh();
		createLocalProject();
		shareProject();
	}

	private void shareProject() {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIShareProjectController shareProjectController = new UIShareProjectController(
					bot.getDisplay().getActiveShell(),
					localProject);
				shareProjectController.execute();
			}
		});
		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return localProject.isShared();
			}

			public String getFailureMessage() {
				return "Share did not succeed.";
			}
		}, timeout());
		SWTBotButton confirmButton = bot.button("OK");
		confirmButton.click();
	}

	private void createLocalProject() {
		final int localProjectsSize = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().size();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UICreateLocalProjectController localProjectController = new UICreateLocalProjectController(
					bot.getDisplay().getActiveShell());
				localProject = localProjectController.execute();
			}
		});
		SWTBotText text = bot.text(0);
		text.setText("quux");
		SWTBotButton button = bot.button("OK");
		button.click();
		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				return localProjectsSize + 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().size();
			}

			public String getFailureMessage() {
				return "Create local project did not succeed.";
			}
		});
	}
}
