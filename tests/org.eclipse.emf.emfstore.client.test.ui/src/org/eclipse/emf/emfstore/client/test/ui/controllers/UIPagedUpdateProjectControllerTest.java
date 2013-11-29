/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.client.ESPagedUpdateConfig;
import org.eclipse.emf.emfstore.internal.common.ExtensionRegistry;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class UIPagedUpdateProjectControllerTest extends AbstractUIControllerTestWithCommit {

	@Override
	@Test
	public void testController() {

		checkout();

		ExtensionRegistry.INSTANCE.set(ESPagedUpdateConfig.ID,
			new ESPagedUpdateConfig() {

				public boolean isEnabled() {
					return true;
				}

				public int getNumberOfAllowedChanges() {
					return 1;
				}
			});

		createTournamentAndCommit();
		createLeagueAndCommit();

		pagedUpdate();

		assertTrue(getCopy().getBaseVersion().getIdentifier() == localProject.getBaseVersion()
			.getIdentifier());

	}
}
