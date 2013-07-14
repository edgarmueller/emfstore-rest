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

	private static XMLRPC XML_RPC = new XMLRPC();
	private static VersioningInfo VERSIONING_INFO = new VersioningInfo();
	private static Behavior ClIENT_BEHAVIOR = new Behavior();
	private static FileInfo FILE_INFO = new FileInfo();

	private Configuration() {
		// nothing to do
	}

	public static Behavior getClientBehavior() {
		if (ClIENT_BEHAVIOR == null) {
			ClIENT_BEHAVIOR = new Behavior();
		}
		return ClIENT_BEHAVIOR;
	}

	public static FileInfo getFileInfo() {
		if (FILE_INFO == null) {
			FILE_INFO = new FileInfo();
		}
		return FILE_INFO;
	}

	public static VersioningInfo getVersioningInfo() {
		if (VERSIONING_INFO == null) {
			VERSIONING_INFO = new VersioningInfo();
		}
		return VERSIONING_INFO;
	}

	public static XMLRPC getXMLRPC() {
		if (XML_RPC == null) {
			XML_RPC = new XMLRPC();
		}
		return XML_RPC;
	}
}
