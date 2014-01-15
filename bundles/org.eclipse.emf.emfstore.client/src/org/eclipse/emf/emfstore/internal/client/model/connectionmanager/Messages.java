/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import org.eclipse.osgi.util.NLS;

/**
 * Connection manager related messages.
 * 
 * @author emueller
 * 
 */
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.model.connectionmanager.messages"; //$NON-NLS-1$
	public static String BasicSessionProvider_No_Usersession_Found;
	public static String BasicSessionProvider_Usersession_Not_Logged_In;
	public static String ConnectionManager_Encoding_Problem;
	public static String ConnectionManager_Incompatible_Client_Version;
	public static String ConnectionManager_Login_Refused;
	public static String ConnectionManager_Server_Could_Not_Be_Reached;
	public static String ConnectionManager_Session_Unknown;
	public static String KeyStoreManager_29;
	public static String KeyStoreManager_34;
	public static String KeyStoreManager_Cannot_Delete_Default_Certificate;
	public static String KeyStoreManager_Choose_Valid_Certificate;
	public static String KeyStoreManager_Could_Not_Encrypt_Password;
	public static String KeyStoreManager_Deleting_Certificate_Failed;
	public static String KeyStoreManager_Keystore_Not_Initialized;
	public static String KeyStoreManager_Loading_Certificate_Failed;
	public static String KeyStoreManager_Storing_Certificate_Failed;
	public static String KeyStoreManager_Unable_To_Get_Password;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
