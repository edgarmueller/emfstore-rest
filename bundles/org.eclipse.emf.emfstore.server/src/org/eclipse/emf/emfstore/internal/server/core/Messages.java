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
package org.eclipse.emf.emfstore.internal.server.core;

import org.eclipse.osgi.util.NLS;

/**
 * @author Edgar
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.server.core.messages"; //$NON-NLS-1$
	public static String AdminEmfStoreImpl_Could_Not_Find_OrgUnit;
	public static String AdminEmfStoreImpl_Group_Already_Exists;
	public static String AdminEmfStoreImpl_Group_Does_Not_Exist;
	public static String AdminEmfStoreImpl_OrgUnit_Does_Not_Exist;
	public static String AdminEmfStoreImpl_Unknown_ProjectID;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
