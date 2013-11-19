/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.mongodb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;

/**
 * Represents the configuration of the EMFStore MongoDB plugin.
 * 
 * @author jfaltermeier
 * 
 */
public final class MongoDBConfiguration {

	/**
	 * The instance.
	 */
	public static final MongoDBConfiguration INSTANCE = new MongoDBConfiguration();

	/**
	 * Constant for the hostname of the server running MongoDB.
	 */
	public static final String HOST_NAME = "emfstore.mongodb.hostName"; //$NON-NLS-1$

	/**
	 * Constant for the port MongoDB is listening on (optional).
	 */
	public static final String PORT = "emfstore.mongodb.port"; //$NON-NLS-1$

	private static Properties properties;

	private static final String PROPERTIES_FILE_NAME = "mongodb.properties"; //$NON-NLS-1$

	private MongoDBConfiguration() {
		properties = initProperties();
	}

	private Properties initProperties() {
		final File propertyFile = new File(ServerConfiguration.getConfDirectory() + File.separator
			+ PROPERTIES_FILE_NAME);

		if (!propertyFile.exists()) {
			if (!dropPropertiesFileToHardDrive(propertyFile)) {
				ModelUtil.logWarning("MongoDB property initialization failed, using default properties.");
				return null;
			}
		}

		final Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyFile);
			properties.load(fis);
		} catch (final IOException e) {
			ModelUtil.logWarning("MongoDB property initialization failed, using default properties.");
			return null;
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (final IOException e) {
			}
		}
		return properties;
	}

	/**
	 * @param targetFile
	 * @return <code>true</code> if successful, <code>false</code> otherwise
	 */
	private boolean dropPropertiesFileToHardDrive(File targetFile) {
		try {
			FileUtil.copyFile(getClass().getResourceAsStream("mongodb.properties"), targetFile); //$NON-NLS-1$
		} catch (final IOException e) {
			ModelUtil.logWarning("Copying MongoDB property file to configuration folder failed");
			return false;
		}
		return true;
	}

	/**
	 * Returns the prefix for MongoDB URIs. <br>
	 * e.g. <code>mongodb://localhost/</code> </br>
	 * 
	 * @return the prefix
	 */
	public String getMongoURIPrefix() {
		if (properties == null) {
			return getDefaultMongoURIPrefix();
		}

		final String hostName = properties.getProperty(HOST_NAME);
		final String port = properties.getProperty(PORT);
		if (hostName == null || hostName.equals("")) { //$NON-NLS-1$
			return getDefaultMongoURIPrefix();
		}
		String prefix = "mongodb://" + hostName; //$NON-NLS-1$
		if (port != null && !port.equals("")) { //$NON-NLS-1$
			prefix = prefix + ":" + port; //$NON-NLS-1$
		}
		prefix = prefix + "/"; //$NON-NLS-1$
		return prefix;
	}

	private String getDefaultMongoURIPrefix() {
		return "mongodb://localhost/"; //$NON-NLS-1$
	}

}
