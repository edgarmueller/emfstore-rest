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
package org.eclipse.emf.emfstore.internal.client.model.controller;

import org.eclipse.osgi.util.NLS;

/**
 * @author Edgar
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.model.controller.messages"; //$NON-NLS-1$
	public static String ShareController_Finalizing_Share;
	public static String ShareController_Initial_Commit;
	public static String ShareController_Preparing_Share;
	public static String ShareController_Project_At;
	public static String ShareController_Settings_Attributes;
	public static String ShareController_Sharing_Project;
	public static String ShareController_Sharing_Project_With_Server;
	public static String ShareController_Uploading_Files;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
