/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.handler;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Interface that determines what to do in case the checksum computation on a {@link ESLocalProject} fails.
 * 
 * @author emueller
 */
public interface ESChecksumErrorHandler {

	/**
	 * Executes the error handler.
	 * 
	 * @param projectSpace
	 *            the {@link ESLocalProject} which contains the project that got in an inconsistent state
	 *            and therefore caused the failing computation of the checksum
	 * @param versionSpec
	 *            the version spec containing the correct checksum
	 * @param monitor
	 *            an {@link IProgressMonitor} instance that should be used to indicate progress
	 *            of the error handler
	 * 
	 * @return whether the error handler successfully handled the error
	 * 
	 * @throws ESException
	 *             in case any error occurs during execution of the error handler
	 */
	boolean execute(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
		throws ESException;
}
