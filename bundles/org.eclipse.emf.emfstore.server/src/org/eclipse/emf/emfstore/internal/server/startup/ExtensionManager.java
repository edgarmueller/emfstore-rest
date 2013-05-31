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
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.startup;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPointException;
import org.eclipse.emf.emfstore.internal.server.EMFStoreInterface;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControlImpl;
import org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;

/**
 * This class calls the startup listeners, registered at the extension point.
 * 
 * @author wesendon
 */
// TODO: internal

public final class ExtensionManager {

	private ExtensionManager() {
	}

	/**
	 * Calls the startup listeners.
	 * 
	 * @param projects list of projects
	 */
	public static void notifyStartupListener(EList<ProjectHistory> projects) {
		for (ESExtensionElement element : new ESExtensionPoint(
			"org.eclipse.emf.emfstore..server.startuplistener", true).getExtensionElements()) {
			try {
				element.getClass("class", StartupListener.class).startedUp(projects);
			} catch (ESExtensionPointException e) {
				// fail silently
			}
		}
	}

	/**
	 * Notifies the post startup listener.
	 * 
	 * @param serverspace serverspace
	 * @param accessControl accessscontrol
	 * @param connectionHandlers set of connection handlers
	 */
	public static void notifyPostStartupListener(ServerSpace serverspace, AccessControlImpl accessControl,
		Set<ConnectionHandler<? extends EMFStoreInterface>> connectionHandlers) {
		for (ESExtensionElement element : new ESExtensionPoint(
			"org.eclipse.emf.emfstore.server.poststartuplistener", true).getExtensionElements()) {
			try {
				element.getClass("class", PostStartupListener.class).postStartUp(serverspace, accessControl,
					connectionHandlers);
			} catch (ESExtensionPointException e) {
				// fail silently
			}
		}
	}

}
