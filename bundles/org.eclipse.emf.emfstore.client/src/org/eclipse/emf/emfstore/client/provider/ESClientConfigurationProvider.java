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
package org.eclipse.emf.emfstore.client.provider;

import java.util.List;

import org.eclipse.emf.emfstore.client.ESServer;

/**
 * This provider allows to set the default {@link ESServer} and
 * initialize the {@link ESKeyStoreManager} with necessary certificates.
 * 
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESClientConfigurationProvider {

	/**
	 * <p>
	 * Returns a list of default {@link ESServer}s.
	 * </p>
	 * <p>
	 * <b>NOTE:</b> This method is called during workspace initialization, i.e. you must <b>NOT</b> use
	 * {@link org.eclipse.emf.emfstore.client.ESWorkspaceProvider#INSTANCE} in the scope of this method. This means you
	 * must not create a session while creating the default server information.
	 * </p>
	 * 
	 * @return a list of default server entries
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	List<ESServer> getDefaultServerInfos();

	/**
	 * Allows the {@link ESClientConfigurationProvider} to initialize the {@link ESKeyStoreManager}. Use
	 * {@link ESKeyStoreManager#setDefaultCertificate(String)} to set the default alias and
	 * {@link ESKeyStoreManager#addCertificate(String, java.io.InputStream)} to add your certificate.
	 * 
	 * @param keyStoreManager
	 *            the {@link ESKeyStoreManager} to be initialized
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void initDefaultCertificates(ESKeyStoreManager keyStoreManager);

}
