/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.changetracking;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.emfstore.common.ESObserver;

/**
 * Listener for a {@link ESCommandStack}.
 * 
 * @author mkoegel
 */
public interface ESCommandObserver extends ESObserver {

	/**
	 * Called to notify listener about the start of the given command.
	 * 
	 * @param command the command
	 */
	void commandStarted(Command command);

	/**
	 * Called to notify listener about the successful completion of the given command.
	 * 
	 * @param command the command
	 */
	void commandCompleted(Command command);

	/**
	 * Called to notify listener about the failure of the given command.
	 * 
	 * @param command the command
	 * @param exception the exception that occurred
	 */
	void commandFailed(Command command, Exception exception);

}
