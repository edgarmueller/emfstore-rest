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

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

public interface UpdateCallback {

	boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes);

	void noChangesOnServer();

	boolean conflictOccurred(ChangeConflictException changeConflictException);

	UpdateCallback NOCALLBACK = new UpdateCallback() {
		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes) {
			return true;
		}

		public void noChangesOnServer() {
		}

		public boolean conflictOccurred(ChangeConflictException changeConflictException) {
			return false;
		}
	};
}
