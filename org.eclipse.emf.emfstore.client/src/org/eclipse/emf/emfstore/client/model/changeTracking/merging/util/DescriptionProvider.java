/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.changeTracking.merging.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.emfstore.client.model.Activator;
import org.osgi.framework.Bundle;

/**
 * Key-Value-Store for conflict descriptions.
 * 
 * @author wesendon
 * 
 */
public class DescriptionProvider {

	private Properties properties;
	private String prefix;

	/**
	 * Default constructor.
	 * 
	 * @param prefix
	 *            prefix for all keys
	 */
	public DescriptionProvider(String prefix) {
		properties = load();
		this.prefix = prefix;
	}

	/**
	 * Default constructor.
	 */
	public DescriptionProvider() {
		this(null);
	}

	private Properties load() {
		Properties properties = new Properties();

		try {
			Bundle bundle = Activator.getDefault().getBundle();
			URL fileURL = bundle.getEntry("resources/conflictdescription.ini");
			properties.load(new FileInputStream(new File(FileLocator.resolve(
					fileURL).toURI())));
		} catch (URISyntaxException e1) {
		} catch (IOException e1) {
		}

		return properties;
	}

	/**
	 * Returns a description for given key.
	 * 
	 * @param key
	 *            key
	 * @return description
	 */
	public String getDescription(String key) {
		return properties.getProperty(getKey(key), getDefaultValue());
	}

	private String getKey(String key) {
		if (prefix == null || prefix == "") {
			return key;
		}
		return prefix + "." + key;
	}

	/**
	 * Default value, if key is unknown.
	 * 
	 * @return default: empty string ""
	 */
	protected String getDefaultValue() {
		return "";
	}

	/**
	 * Set a prefix for all keys.
	 * 
	 * @param prefix
	 *            prefix, can be null
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
