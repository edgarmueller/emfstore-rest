/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.Test;

public class UICheckoutControllerTest extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() throws ESException {

		checkout();
		createPlayerAndCommit();
		createLeagueAndCommit();

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				try {
					final UICheckoutController checkoutController = new UICheckoutController(bot.getDisplay()
						.getActiveShell(),
						ESVersionSpec.FACTORY.createPRIMARY(1),
						localProject.getRemoteProject());
					checkoutController.execute();
				} catch (final ESException e) {
					fail(e.getMessage());
				}
			}
		});

		bot.text(0).setText("checkout");
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {

			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				for (final ESLocalProject localProject : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
					if (localProject.getProjectName().equals("checkout")) {
						if (localProject.getBaseVersion().getIdentifier() == 1) {
							return true;
						}
					}
				}

				return false;
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Checkout did not succeed";
			}
		});
	}
}
