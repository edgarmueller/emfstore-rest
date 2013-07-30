/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model;

import org.eclipse.emf.emfstore.internal.client.configuration.Behavior;
import org.eclipse.emf.emfstore.internal.client.configuration.FileInfo;
import org.eclipse.emf.emfstore.internal.client.configuration.VersioningInfo;
import org.eclipse.emf.emfstore.internal.client.configuration.XMLRPC;

/**
 * Represents the current Workspace Configuration.
 * 
 * @author koegel
 * @author wesendon
 */
public final class Configuration {

	private static XMLRPC xmlRpc = new XMLRPC();
	private static VersioningInfo versioningInfo = new VersioningInfo();
	private static Behavior clientBehaviour = new Behavior();
	private static FileInfo fileInfo = new FileInfo();

	private Configuration() {
		// nothing to do
	}

	/**
	 * Get the client behavior configuration.
	 * 
	 * @return the configuration
	 */
	public static Behavior getClientBehavior() {
		if (clientBehaviour == null) {
			clientBehaviour = new Behavior();
		}
		return clientBehaviour;
	}

	/**
	 * Get the file configuration.
	 * 
	 * @return the configuration
	 */
	public static FileInfo getFileInfo() {
		if (fileInfo == null) {
			fileInfo = new FileInfo();
		}
		return fileInfo;
	}

	/**
	 * Get the version configuration.
	 * 
	 * @return the configuration
	 */
	public static VersioningInfo getVersioningInfo() {
		if (versioningInfo == null) {
			versioningInfo = new VersioningInfo();
		}
		return versioningInfo;
	}

	/**
	 * Get the XML RPC configuration.
	 * 
	 * @return the configuration.
	 */
	public static XMLRPC getXMLRPC() {
		if (xmlRpc == null) {
			xmlRpc = new XMLRPC();
		}
		return xmlRpc;
	}
}
