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
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIMergeController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.Test;

public class UIMergeControllerTest extends AbstractUIControllerTestWithCommitAndBranch {

	@Override
	@Test
	public void testController() throws ESException {

		checkout();
		createBranch("foo-branch");
		createPlayerAndCommit();
		createLeagueAndCommit();

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIMergeController mergeController = null;
				mergeController = new UIMergeController(bot.getDisplay().getActiveShell(), getCopy());
				mergeController.execute();
			}
		});

		bot.table().select(0);
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				for (final ESLocalProject project : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
					if (project == getCopy()) {
						if (project.getModelElements().size() == 2) {
							return true;
						}
					}
				}
				return false;
			}

			public String getFailureMessage() {
				return "Branch checkout did not succeed";
			}
		}, timeout());

		assertEquals(2, getCopy().getModelElements().size());
	}

}
