/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

/**
 * When querying the history API successfully, the result is a set of {@link IHistoryInfo}.
 * Each IHistoryInfo contains pointers to versions, in form of PrimaryVersionIdentier,
 * as well as possible tags, the log message for the given version
 * and optionally the changes for this version.
 * 
 * @author emueller
 * @author wesendon
 */
public interface IHistoryInfo {

	/**
	 * Returns the version the history information is associated with.
	 * 
	 * @return the {@link IPrimaryVersionSpec} of the history information
	 */
	IPrimaryVersionSpec getPrimarySpec();

	/**
	 * Returns a list of the {@link IPrimaryVersionSpec}s that followed this version.
	 * 
	 * @return a list containing the next {@link IPrimaryVersionSpec}s
	 */
	List<IPrimaryVersionSpec> getNextSpecs();

	/**
	 * Returns the {@link IPrimaryVersionSpec} of the previous version.
	 * 
	 * @return the previous version
	 */
	IPrimaryVersionSpec getPreviousSpec();

	/**
	 * Returns an optional list of {@link IPrimaryVersionSpec}s that identifies the versions
	 * this version was merged from.
	 * 
	 * @return an optional list of branch versions this version was merged from
	 */
	List<IPrimaryVersionSpec> getMergedFromSpecs();

	/**
	 * Returns an optional list of {@link IPrimaryVersionSpec}s that identifies the versions
	 * this version was merged from.
	 * 
	 * @return an optional list of branch versions this version was merged from
	 */
	List<IPrimaryVersionSpec> getMergedToSpecs();

	/**
	 * Returns the {@link ILogMessage} that was specified when a commit succeeded.
	 * 
	 * @return the log message that is associated with this history information
	 */
	ILogMessage getLogMessage();

}
