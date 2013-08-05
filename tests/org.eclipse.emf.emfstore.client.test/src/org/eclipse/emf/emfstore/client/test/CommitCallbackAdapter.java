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
package org.eclipse.emf.emfstore.client.test;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;

public class CommitCallbackAdapter implements ESCommitCallback {

	public boolean baseVersionOutOfDate(ESLocalProject project, IProgressMonitor progressMonitor) {
		return true;
	}

	public boolean inspectChanges(ESLocalProject project, ESChangePackage changePackage,
		ESModelElementIdToEObjectMapping idToEObjectMapping) {
		return true;
	}

	public void noLocalChanges(ESLocalProject projectSpace) {
		// do nothing
	}
}
