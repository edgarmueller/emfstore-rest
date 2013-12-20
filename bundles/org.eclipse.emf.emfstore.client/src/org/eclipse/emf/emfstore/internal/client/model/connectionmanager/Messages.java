/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import org.eclipse.osgi.util.NLS;

/**
 * @author Edgar
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.model.connectionmanager.messages"; //$NON-NLS-1$
	public static String ConnectionManager_Encoding_Problem;
	public static String ConnectionManager_Incompatible_Client_Version;
	public static String ConnectionManager_Login_Refused;
	public static String ConnectionManager_Server_Could_Not_Be_Reached;
	public static String ConnectionManager_Session_Unknown;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
