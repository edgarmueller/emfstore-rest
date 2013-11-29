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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.waitForShell;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.observer.ESUpdateObserver;
import org.eclipse.emf.emfstore.client.test.api.ProjectChangeUtil;
import org.eclipse.emf.emfstore.client.test.ui.AllUITests;
import org.eclipse.emf.emfstore.client.ui.ESUIControllerFactory;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUpdateProjectToVersionController;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.hamcrest.Matcher;

public abstract class AbstractUIControllerTestWithCommit extends AbstractUIControllerTest {

	public static final String PLAYER_NAME = "A";
	public static final String LEAGUE_NAME = "L";
	private boolean didUpdate;

	protected void createTournamentAndCommit() {
		final Tournament tournament = ProjectChangeUtil.createTournament(true);
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(tournament);
				return null;
			}
		});
		commit(localProject);
	}

	protected void createLeagueAndCommit() {
		createLeagueAndCommit(localProject);
	}

	protected Player createPlayerAndCommit() {
		return createPlayerAndCommit(localProject);
	}

	protected Player createPlayerAndCommit(final ESLocalProject localProject) {
		final Player player = ProjectChangeUtil.createPlayer(PLAYER_NAME);
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(player);
				return null;
			}
		});
		commit(localProject);
		return player;
	}

	protected void createLeagueAndCommit(final ESLocalProject localProject) {
		final League league = ProjectChangeUtil.createLeague("L");
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(league);
				return null;
			}
		});
		commit(localProject);
	}

	protected void commit() {
		commit(localProject);
	}

	protected void commit(final ESLocalProject localProject) {
		final ESPrimaryVersionSpec baseVersion = localProject.getBaseVersion();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				ESUIControllerFactory.INSTANCE.commitProject(
					bot.getDisplay().getActiveShell(),
					localProject);
			}
		});

		final SWTBotButton buttonWithLabel = bot.button("OK");
		buttonWithLabel.click();

		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return baseVersion.getIdentifier() + 1 == localProject.getBaseVersion()
					.getIdentifier();
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Commit did not succeed.";
			}
		}, AllUITests.TIMEOUT);

		assertEquals(baseVersion.getIdentifier() + 1,
			localProject.getBaseVersion().getIdentifier());
	}

	protected void checkout() {
		UIThreadRunnable.asyncExec(new VoidResult() {

			public void run() {
				UICheckoutController checkoutController;
				try {
					checkoutController = new UICheckoutController(
						bot.getDisplay().getActiveShell(),
						localProject.getRemoteProject());
					checkedoutCopy = checkoutController.execute();
				} catch (final ESException e) {
					fail(e.getMessage());
				}
			}
		});

		bot.text().setText("checkout");
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return checkedoutCopy != null;
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Checkout did not succeed";
			}
		}, timeout());
	}

	protected ESLocalProject checkout(final ESPrimaryVersionSpec versionSpec, String checkoutName) {

		final ESLocalProject[] localProjectArr = new ESLocalProject[1];

		UIThreadRunnable.asyncExec(new VoidResult() {

			public void run() {
				UICheckoutController checkoutController;
				try {
					checkoutController = new UICheckoutController(
						bot.getDisplay().getActiveShell(),
						versionSpec,
						localProject.getRemoteProject());
					localProjectArr[0] = checkoutController.execute();
				} catch (final ESException e) {
					fail(e.getMessage());
				}
			}
		});

		bot.text().setText(checkoutName);
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {

			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return localProjectArr[0] != null;
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Checkout did not succeed";
			}
		}, timeout());

		return localProjectArr[0];
	}

	public ESLocalProject getCopy() {
		return checkedoutCopy;
	}

	protected ESPrimaryVersionSpec updateCopy() {
		SWTBotPreferences.PLAYBACK_DELAY = 100;
		didUpdate = false;

		final ESUpdateObserver updateObserver = createUpdateObserver();
		ESWorkspaceProviderImpl.getInstance();
		ESWorkspaceProviderImpl.getObserverBus().register(updateObserver);

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				final UIUpdateProjectController updateProjectController = new UIUpdateProjectController(
					bot.getDisplay().getActiveShell(),
					getCopy());
				updateProjectController.execute();
			}
		});

		final Matcher<Shell> matcher = withText("Update");
		bot.waitUntil(waitForShell(matcher));
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return didUpdate;
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Update did not succeed.";
			}
		}, timeout());

		ESWorkspaceProviderImpl.getInstance();
		ESWorkspaceProviderImpl.getObserverBus().unregister(updateObserver);

		return getCopy().getBaseVersion();
	}

	protected ESPrimaryVersionSpec updateToVersion() {
		SWTBotPreferences.PLAYBACK_DELAY = 100;
		didUpdate = false;

		final ESUpdateObserver updateObserver = createUpdateObserver();
		ESWorkspaceProviderImpl.getInstance();
		ESWorkspaceProviderImpl.getObserverBus().register(updateObserver);

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				final UIUpdateProjectToVersionController updateProjectController = new UIUpdateProjectToVersionController(
					bot.getDisplay().getActiveShell(),
					getCopy());
				updateProjectController.execute();
			}
		});

		Matcher<Shell> matcher = withText("Select a Version to update to");
		bot.waitUntil(waitForShell(matcher));
		bot.button("OK").click();

		matcher = withText("Update");
		bot.waitUntil(waitForShell(matcher));
		bot.button("OK").click();

		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return didUpdate;
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Update to version did not succeed.";
			}
		}, 600000);

		ESWorkspaceProviderImpl.getInstance();
		ESWorkspaceProviderImpl.getObserverBus().unregister(updateObserver);

		return getCopy().getBaseVersion();
	}

	private ESUpdateObserver createUpdateObserver() {
		return new ESUpdateObserver() {

			public void updateCompleted(ESLocalProject project, IProgressMonitor monitor) {
				didUpdate = true;
			}

			public boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changePackages,
				IProgressMonitor monitor) {
				return true;
			}
		};
	}

	protected ESPrimaryVersionSpec pagedUpdate() {

		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				final UIUpdateProjectController updateProjectController = new UIUpdateProjectController(
					bot.getDisplay().getActiveShell(),
					getCopy());
				updateProjectController.execute();
			}
		});

		final SWTBotButton buttonWithLabel = bot.button("OK");
		buttonWithLabel.click();

		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return getCopy().getBaseVersion().getIdentifier() ==
				localProject.getBaseVersion().getIdentifier() - 1;
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Paged Update did not succeed.";
			}
		}, timeout());

		final Matcher<Shell> matcher = withText("More updates available");
		bot.waitUntil(waitForShell(matcher));
		bot.button("OK").click(); // update notification hint
		bot.button("OK").click(); // inspect changes on update

		bot.waitUntil(new DefaultCondition() {
			// BEGIN SUPRESS CATCH EXCEPTION
			public boolean test() throws Exception {
				return getCopy().getBaseVersion().getIdentifier() ==
				localProject.getBaseVersion().getIdentifier();
			}

			// END SUPRESS CATCH EXCEPTION

			public String getFailureMessage() {
				return "Paged Update did not succeed.";
			}
		}, timeout());

		return getCopy().getBaseVersion();
	}
}
