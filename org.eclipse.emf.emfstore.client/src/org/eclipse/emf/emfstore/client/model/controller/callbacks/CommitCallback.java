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

public interface CommitCallback {

	boolean baseVersionOutOfDate(ProjectSpace projectSpace);

	boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage);

	void noLocalChanges(ProjectSpace projectSpace);

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
