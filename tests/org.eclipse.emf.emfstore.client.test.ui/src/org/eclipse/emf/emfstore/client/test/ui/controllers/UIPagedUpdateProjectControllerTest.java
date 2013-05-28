package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.emf.emfstore.client.ESPagedUpdateConfig;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionRegistry;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
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

		ESPrimaryVersionSpec update = update();
		bot.button("OK").click();
		bot.button("OK").click();

		// assertFalse(update.getIdentifier() == localProject.getBaseVersion().getIdentifier());
		// final ESPrimaryVersionSpec update2 = update();
		bot.waitUntil(new DefaultCondition() {

			public boolean test() throws Exception {
				return getCheckedoutCopy().getBaseVersion().getIdentifier() == localProject.getBaseVersion()
					.getIdentifier();
			}

			public String getFailureMessage() {
				return "Update did not succeed";
			}
		}, timeout());
		assertTrue(getCheckedoutCopy().getBaseVersion().getIdentifier() == localProject.getBaseVersion()
			.getIdentifier());

	}
}
