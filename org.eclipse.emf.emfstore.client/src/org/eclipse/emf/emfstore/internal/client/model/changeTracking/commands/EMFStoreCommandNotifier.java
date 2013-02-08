/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.emfstore.common.ISafeRunnable;
import org.eclipse.emf.emfstore.common.SafeRunner;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Notifier for Commands. Notifies Observers about command start, completion and failure.
 * 
 * @author koegel
 */
public class EMFStoreCommandNotifier {

	private List<CommandObserver> commandObservers;

	/**
	 * Default constructor.
	 */
	public EMFStoreCommandNotifier() {
		super();
		commandObservers = new ArrayList<CommandObserver>();
	}

	/**
	 * Notify all registered listeners about command start.
	 * 
	 * @param command the command
	 */
	public void notifiyListenersAboutStart(final Command command) {
		for (final CommandObserver commandObservers : this.commandObservers) {
			ISafeRunnable code = new ISafeRunnable() {

				public void run() {
					commandObservers.commandStarted(command);
				}

				public void handleException(Throwable exception) {
					ModelUtil.logWarning("Command Observer threw exception", exception);
				}
			};

			SafeRunner.run(code);

		}
	}

	/**
	 * Notify all registered listeners about command failure.
	 * 
	 * @param command the command
	 * @param exception the exception that triggered the failure
	 */
	public void notifiyListenersAboutCommandFailed(final Command command, final Exception exception) {
		for (final CommandObserver commandObservers : this.commandObservers) {
			ISafeRunnable code = new ISafeRunnable() {

				public void run() {
					commandObservers.commandFailed(command, exception);
				}

				public void handleException(Throwable exception) {
					ModelUtil.logWarning("Command Observer threw exception", exception);
				}
			};

			SafeRunner.run(code);
		}
	}

	/**
	 * Notify all registered listeners about command completion.
	 * 
	 * @param command the command
	 */
	public void notifiyListenersAboutCommandCompleted(final Command command) {
		for (final CommandObserver commandObservers : this.commandObservers) {
			ISafeRunnable code = new ISafeRunnable() {

				public void run() {
					commandObservers.commandCompleted(command);
				}

				public void handleException(Throwable exception) {
					ModelUtil.logWarning("Command Observer threw exception", exception);
				}
			};

			SafeRunner.run(code);
		}
	}

	/**
	 * Add a command stack observer.
	 * 
	 * @param observer the observer
	 */
	public void addCommandStackObserver(CommandObserver observer) {
		commandObservers.add(observer);
	}

	/**
	 * Remove COmmand stack observer.
	 * 
	 * @param observer the observer
	 */
	public void removeCommandStackObserver(CommandObserver observer) {
		commandObservers.remove(observer);
	}

}