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
package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;

/**
 * Controller class for adding a tag to a version.
 * 
 */
public class AddTagController extends ServerCall<Void> {

	private final ProjectSpace projectSpace;
	private final PrimaryVersionSpec versionSpec;
	private final TagVersionSpec tag;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the {@link ProjectSpace} being versioned
	 * @param versionSpec
	 *            the version to be tagged
	 * @param tag
	 *            the tag
	 */
	public AddTagController(ProjectSpace projectSpace, final PrimaryVersionSpec versionSpec, final TagVersionSpec tag) {
		this.projectSpace = projectSpace;
		this.versionSpec = versionSpec;
		this.tag = tag;
	}

	@Override
	protected Void run() throws EmfStoreException {
		projectSpace.addTag(versionSpec, tag);
		return null;
	}
}
