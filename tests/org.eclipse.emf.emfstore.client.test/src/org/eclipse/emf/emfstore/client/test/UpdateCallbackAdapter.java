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
package org.eclipse.emf.emfstore.client.test;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

public class UpdateCallbackAdapter implements ESUpdateCallback {

	public boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changes,
		ESModelElementIdToEObjectMapping idToEObjectMapping) {
		return true;
	}

	public void noChangesOnServer() {

	}

	public boolean conflictOccurred(ESChangeConflict changeConflict, IProgressMonitor progressMonitor) {
		return true;
	}

	public boolean checksumCheckFailed(ESLocalProject project, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor progressMonitor) throws ESException {
		return true;
	}

}
