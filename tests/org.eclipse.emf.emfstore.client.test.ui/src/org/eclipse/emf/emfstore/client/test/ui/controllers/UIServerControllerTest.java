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

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIAddServerController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIRemoveServerController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.Test;

public class UIServerControllerTest extends AbstractUIControllerTest {

	@Override
	@Test
	public void testController() throws ESException {
		addServer();
		removeServer();
	}

	private void removeServer() {
		final int howManyServers = ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size();
		final ESServer serverToBeRemoved = ESWorkspaceProvider.INSTANCE.getWorkspace().getServers()
			.get(howManyServers - 1);
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIRemoveServerController removeServerController = new UIRemoveServerController(
					bot.getDisplay().getActiveShell(),
					serverToBeRemoved);
				removeServerController.execute();
			}
		});
		bot.button("Yes").click();
		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return howManyServers - 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size();
			}

			public String getFailureMessage() {
				return "Remove server did not succeed.";
			}
		}, timeout());
		assertEquals(howManyServers - 1, ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size());
	}

	private void addServer() {
		final int howManyServers = ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				UIAddServerController addServerController = new UIAddServerController(
					bot.getDisplay().getActiveShell());
				addServerController.execute();
			}
		});
		bot.button("Finish").click();
		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return howManyServers + 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size();
			}

			public String getFailureMessage() {
				return "Add server did not succeed.";
			}
		});
		assertEquals(howManyServers + 1, ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size());
	}

}
