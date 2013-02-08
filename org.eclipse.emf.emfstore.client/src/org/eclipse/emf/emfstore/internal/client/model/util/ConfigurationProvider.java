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
package org.eclipse.emf.emfstore.internal.client.model.util;

import java.util.List;

import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;

/**
 * This provider allows to set the default {@link IServer} and initialize the {@link KeyStoreManager} with necessary
 * certificates.
 * 
 * @author wesendon
 */
public interface ConfigurationProvider {

	/**
	 * Returns a list of default {@link IServer}s.
	 * 
	 * @return a list of default server entries
	 */
	List<IServer> getDefaultServerInfos();

	/**
	 * Allows the {@link ConfigurationProvider} to initialize the {@link KeyStoreManager}. Use
	 * {@link KeyStoreManager#setDefaultCertificate(String)} to set the default alias and
	 * {@link KeyStoreManager#addCertificate(String, java.io.InputStream)} to add you certificate.
	 * 
	 * @param keyStoreManager {@link KeyStoreManager}
	 */
	void initDefaultCertificates(KeyStoreManager keyStoreManager);

}