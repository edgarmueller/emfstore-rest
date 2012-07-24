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
package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

/**
 * Callback interface for implementors that are interested in
 * influencing the actual commit behavior.
 * 
 * @author ovonwesen
 * @author emueller
 */
public interface CommitCallback {

	/**
	 * Called when the project space that should be updated is out of date.
	 * A caller may veto against updating the project space by returning false.
	 * 
	 * @param projectSpace
	 *            the project space being out of date
	 * @return true, if the caller is willing to update the project space, false otherwise
	 */
	boolean baseVersionOutOfDate(ProjectSpace projectSpace);

	/**
	 * Called right before the actual commit is performed.
	 * Implementors may veto against the commit by returning false
	 * 
	 * @param projectSpace
	 *            the project space with the local pending changes
	 * @param changePackage
	 *            the actual changes
	 * @return true, if the commit should continue, false otherwise
	 */
	boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage);

	/**
	 * Called when there are no changes on the given project space.
	 * 
	 * @param projectSpace
	 *            the project space that has no local pending changes
	 */
	void noLocalChanges(ProjectSpace projectSpace);

	/**
	 * Default implementation of a callback interface for commit.
	 * Does not veto against updating the project space in case it is out of date
	 * and returns true for {@link #inspectChanges(ProjectSpace, ChangePackage)}, such that a
	 * commit is always performed.
	 */
	CommitCallback NOCALLBACK = new CommitCallback() {

		public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {
			return true;
		}

		public boolean baseVersionOutOfDate(ProjectSpace projectSpace) {
			return false;
		}

		public void noLocalChanges(ProjectSpace projectSpace) {
			// do nothing
		}
	};
}
