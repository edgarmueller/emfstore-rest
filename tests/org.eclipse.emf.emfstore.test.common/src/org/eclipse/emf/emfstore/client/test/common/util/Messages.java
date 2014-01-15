/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.util;

import org.eclipse.osgi.util.NLS;

/**
 * @author Edgar
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.client.test.common.util.messages"; //$NON-NLS-1$
	public static String ServerUtil_Added_Superuser;
	public static String ServerUtil_Could_Not_Close_File;
	public static String ServerUtil_Could_Not_Copy_Properties_File_To_Config_Folder;
	public static String ServerUtil_Default_Properties_File_Copied_To_Config_Folder;
	public static String ServerUtil_Failed_To_Copy_Keystore;
	public static String ServerUtil_Keystore_Copied_To_Server_Workspace;
	public static String ServerUtil_Property_Init_Failed;
	public static String ServerUtil_ServerAdmin_Description;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
