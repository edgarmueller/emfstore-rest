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

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIAddServerController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIRemoveServerController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author emueller
 * 
 */
public class UIServerControllerTest extends AbstractUIControllerTest {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ESWorkspaceProvider.INSTANCE.getWorkspace().removeServer(
			ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().get(0));
	}

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
				final UIRemoveServerController removeServerController = new UIRemoveServerController(
					bot.getDisplay().getActiveShell(),
					serverToBeRemoved);
				removeServerController.execute();
			}
		});
		bot.button("Yes").click();
		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return howManyServers - 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size();
			}

			// END SUPRESS CATCH EXCEPTION

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
				final UIAddServerController addServerController = new UIAddServerController(
					bot.getDisplay().getActiveShell());
				addServerController.execute();
			}
		});
		bot.button("Finish").click();
		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION

			public boolean test() throws Exception {
				return howManyServers + 1 == ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size();
			}

			public String getFailureMessage() {
				return "Add server did not succeed.";
			}
			// END SUPRESS CATCH EXCEPTION

		});
		assertEquals(howManyServers + 1, ESWorkspaceProvider.INSTANCE.getWorkspace().getServers().size());
	}

}
