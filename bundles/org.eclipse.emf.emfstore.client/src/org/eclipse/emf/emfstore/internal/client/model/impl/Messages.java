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
package org.eclipse.emf.emfstore.internal.client.model.impl;

import org.eclipse.osgi.util.NLS;

/**
 * @author Edgar
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.model.impl.messages"; //$NON-NLS-1$
	public static String UsersessionImpl_Invalid_Server_URL;
	public static String UsersessionImpl_No_ServerInfo_Set;
	public static String UsersessionImpl_Username;
	public static String UsersessionImpl_Username_Or_Password_not_Set;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
