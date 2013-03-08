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

import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;

/**
 * When querying the history API successfully, the result is a set of {@link ESHistoryInfo}.
 * Each ESHistoryInfo contains pointers to versions, in form of PrimaryVersionIdentier,
 * as well as possible tags, the log message for the given version
 * and optionally the changes for this version.
 * 
 * @author emueller
 * @author wesendon
 */
public interface ESHistoryInfo {

	/**
	 * Returns the version the history information is associated with.
	 * 
	 * @return the {@link ESPrimaryVersionSpec} of the history information
	 */
	ESPrimaryVersionSpec getPrimarySpec();

	/**
	 * Returns a list of the {@link ESPrimaryVersionSpec}s that followed this version.
	 * 
	 * @return a list containing the next {@link ESPrimaryVersionSpec}s
	 */
	List<ESPrimaryVersionSpec> getNextSpecs();

	/**
	 * Returns the {@link ESPrimaryVersionSpec} of the previous version.
	 * 
	 * @return the previous version
	 */
	ESPrimaryVersionSpec getPreviousSpec();

	/**
	 * Returns an optional list of {@link ESPrimaryVersionSpec}s that identifies the versions
	 * this version was merged from.
	 * 
	 * @return an optional list of branch versions this version was merged from
	 */
	List<ESPrimaryVersionSpec> getMergedFromSpecs();

	/**
	 * Returns an optional list of {@link ESPrimaryVersionSpec}s that identifies the versions
	 * this version was merged from.
	 * 
	 * @return an optional list of branch versions this version was merged from
	 */
	List<ESPrimaryVersionSpec> getMergedToSpecs();

	/**
	 * Returns the {@link ESLogMessage} that was specified when a commit succeeded.
	 * 
	 * @return the log message that is associated with this history information
	 */
	ESLogMessage getLogMessage();

	// TODO: javadoc
	List<ESTagVersionSpec> getTagSpecs();

	ESChangePackage getChangePackage();

}
