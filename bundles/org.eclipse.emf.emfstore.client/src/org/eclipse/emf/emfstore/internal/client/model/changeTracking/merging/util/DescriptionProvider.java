/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * Key-Value-Store for conflict descriptions.
 * 
 * @author wesendon
 * 
 */
public class DescriptionProvider {

	private static final String DOT = "."; //$NON-NLS-1$
	private static final String CONFLICTDESCRIPTION_INI_PATH = "platform:/plugin/org.eclipse.emf.emfstore.client/resources/conflictdescription.ini"; //$NON-NLS-1$
	private final Properties properties;
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
		final Properties properties = new Properties();
		URL url;
		InputStream inputStream = null;

		try {
			url = new URL(CONFLICTDESCRIPTION_INI_PATH);
			inputStream = url.openConnection().getInputStream();
			properties.load(inputStream);
		} catch (final MalformedURLException e2) {
			// ignore
		} catch (final IOException e) {
			// ignore
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					// ignore
				}
			}
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
		if (prefix == null || prefix == StringUtils.EMPTY) {
			return key;
		}
		return prefix + DOT + key;
	}

	/**
	 * Default value, if key is unknown.
	 * 
	 * @return default: empty string ""
	 */
	protected String getDefaultValue() {
		return StringUtils.EMPTY;
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
