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
package org.eclipse.emf.emfstore.internal.server;

import org.eclipse.osgi.util.NLS;

/**
 * Common server functionality related messages.
 * 
 * @author emueller
 * 
 */
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.server.messages"; //$NON-NLS-1$
	public static String DefaultServerWorkspaceLocationProvider_InvalidRootDir;
	public static String EMFStoreController_Added_SuperUser;
	public static String EMFStoreController_Cause_For_Server_Shutdown;
	public static String EMFStoreController_Closing_Of_Properties_File_Failed;
	public static String EMFStoreController_ConnectionHandler_Stopped;
	public static String EMFStoreController_Copy_From_To;
	public static String EMFStoreController_Could_Not_Copy_Properties_File;
	public static String EMFStoreController_Could_Not_Init_XMLResource;
	public static String EMFStoreController_Creating_Initial_ServerSpace;
	public static String EMFStoreController_Default_Properties_File_Copied;
	public static String EMFStoreController_Dynamic_Model_Loaded;
	public static String EMFStoreController_EMFStore_Controller_Already_Running;
	public static String EMFStoreController_Error_During_Migration;
	public static String EMFStoreController_Failed_To_Copy_Keystore;
	public static String EMFStoreController_Init_Complete;
	public static String EMFStoreController_JVM_Max_Memory;
	public static String EMFStoreController_Keystore_Copied;
	public static String EMFStoreController_Property_Init_Failed;
	public static String EMFStoreController_PropertyFile_Read;
	public static String EMFStoreController_Serve_Forcefully_Stopped;
	public static String EMFStoreController_Server_Home;
	public static String EMFStoreController_Server_Running;
	public static String EMFStoreController_Server_Stopped;
	public static String EMFStoreController_Server_Was_Stopped;
	public static String EMFStoreController_Stopping_All_ConnectionHandlers;
	public static String EMFStoreController_Stopping_ConnectionHandler;
	public static String EMFStoreController_Waiting_For_Termination_Interrupted;
	public static String ServerConfiguration_Default_Checksum_Behavior;
	public static String ServerConfiguration_No_Location_Provider;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
