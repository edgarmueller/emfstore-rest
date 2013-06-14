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

import org.eclipse.emf.emfstore.client.ESPagedUpdateConfig;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionRegistry;
import org.junit.Test;

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

		assertTrue(getCheckedoutCopy().getBaseVersion().getIdentifier() == localProject.getBaseVersion()
			.getIdentifier());

	}
}
