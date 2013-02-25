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
package org.eclipse.emf.emfstore.internal.client.model;

import org.eclipse.emf.emfstore.internal.client.configuration.Behavior;
import org.eclipse.emf.emfstore.internal.client.configuration.FilePaths;
import org.eclipse.emf.emfstore.internal.client.configuration.VersioningInfo;
import org.eclipse.emf.emfstore.internal.client.configuration.XMLRPC;


/**
 * Represents the current Workspace Configuration.
 * 
 * @author koegel
 * @author wesendon
 */
public final class Configuration {

	public static final XMLRPC XML_RPC = new XMLRPC();
	public static final VersioningInfo VERSIONING = new VersioningInfo();
	public static final Behavior ClIENT_BEHAVIOR = new Behavior();
	public static final FilePaths FILE_INFO = new FilePaths();

	private Configuration() {
		// nothing to do
	}

}