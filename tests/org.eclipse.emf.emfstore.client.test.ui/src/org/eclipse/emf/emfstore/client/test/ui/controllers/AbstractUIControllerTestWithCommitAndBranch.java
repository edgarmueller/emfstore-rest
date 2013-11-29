/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.ui.controllers;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

public abstract class AbstractUIControllerTestWithCommitAndBranch extends AbstractUIControllerTestWithCommit {

	protected ESPrimaryVersionSpec createBranch(final String branchName) throws InvalidVersionSpecException,
		ESUpdateRequiredException,
		ESException {
		return localProject.commitToBranch(
			ESVersionSpec.FACTORY.createBRANCH(branchName),
			"Created branch " + branchName,
			ESCommitCallback.NOCALLBACK,
			new NullProgressMonitor());
	}
}
