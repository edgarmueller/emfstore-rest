/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * Simplifies setting and getting of preference key/value pairs.
 * 
 * @author emueller
 * 
 */
public final class EMFStorePreferenceHelper {

	private static final String PREFERENCE_NODE = "emfstore";

	private EMFStorePreferenceHelper() {
		// nothing to do here
	}

	/**
	 * Retrieves a preference value for a specific key.
	 * 
	 * @param key
	 *            the key used to identify the preference
	 * @param defaultValue
	 *            the value that should be returned in case the preference could not be found
	 * @return the value of the preference if it exists, otherwise the <code>defaultValue</code>
	 */
	public static String getPreference(String key, String defaultValue) {
		return Platform.getPreferencesService().getRootNode().node(ConfigurationScope.SCOPE).node(PREFERENCE_NODE)
			.get(key, defaultValue);
	}

	/**
	 * Set the preference value for a specific key. Key and value must not equal <code>null</code>.
	 * 
	 * @param key
	 *            the key that is used to identify the given value
	 * @param value
	 *            the value of the preference to be set
	 */
	public static void setPreference(String key, String value) {
		if (key != null && value != null) {
			Preferences node = Platform.getPreferencesService().getRootNode().node(ConfigurationScope.SCOPE)
				.node(PREFERENCE_NODE);
			node.put(key, value);
			try {
				node.flush();
			} catch (BackingStoreException e) {
				WorkspaceUtil.handleException("Could not persist the preference change: {" + key + ", " + value + "}",
					e);
			}
		}
	}

}
