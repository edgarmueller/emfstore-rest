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
package org.eclipse.emf.emfstore.client.model.util;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * Interface that determines what to do in case the checksum computation on a {@link ProjectSpace} fails.
 * 
 * @author emueller
 */
public interface IChecksumErrorHandler {

	/**
	 * Executes the error handler.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} which contains the project that got in an inconsistent state
	 *            and therefore caused the failing computation of the checksums
	 * 
	 * @return the possibly modified project space
	 * 
	 * @throws EmfStoreException
	 *             in case any error occurs during execution of the error handler
	 */
	ProjectSpace execute(ProjectSpace projectSpace) throws EmfStoreException;

	/**
	 * Whether to continue after the error handler has been executed, e.g. whether to
	 * finalize update or commit, even in case an error occurred.
	 * 
	 * @return true, if the caller of the error handler should continue regularly, false otherwise
	 */
	boolean shouldContinue();
}
