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

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICreateBranchController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;

public class UICreateBranchControllerTest extends AbstractUIControllerTest {

	@Override
	@Test
	public void testController() throws ESException {
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final int branchesSize = localProject.getBranches(monitor).size();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UICreateBranchController createBranchController = new UICreateBranchController(bot.getDisplay()
					.getActiveShell(),
					localProject);
				createBranchController.execute();
			}
		});
		SWTBotShell shell = bot.shell("Create Branch");

		shell.bot().text(0).setText("foo");
		shell.bot().button("OK").click();
		SWTBotShell commitDialogShell = bot.shell("Commit");
		commitDialogShell.bot().button("OK").click();
		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				return branchesSize + 1 == localProject.getBranches(monitor).size();
			}

			public String getFailureMessage() {
				return "Create branch did not succeed.";
			}
		}, 20000);
		assertEquals(branchesSize + 1, localProject.getBranches(monitor).size());
	}

}
