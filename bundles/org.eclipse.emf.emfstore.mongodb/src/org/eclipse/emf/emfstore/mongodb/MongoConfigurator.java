/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 ******************************************************************************/
package org.eclipse.emf.emfstore.mongodb;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipselabs.mongo.IMongoProvider;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * This class is registered as an OSGi component and configures the mongoDB driver.
 * 
 * @author jfaltermeier
 * 
 */
public class MongoConfigurator {

	private ConfigurationAdmin configurationAdmin;

	/**
	 * Binds the configuration admin.
	 * 
	 * @param configurationAdmin the configuration admin
	 */
	void bindConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}

	/**
	 * Configures the mongoDB driver.
	 * 
	 * @throws IOException if config update fails
	 */
	void activate() throws IOException {
		Configuration config = configurationAdmin.createFactoryConfiguration("org.eclipselabs.mongo.provider", null);
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(IMongoProvider.PROP_URI, "mongodb://localhost/");
		config.update(properties);
	}

}
