/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.provider;

import java.io.InputStream;

import org.eclipse.emf.emfstore.client.exceptions.ESCertificateException;

/**
 * The EMFStore key store manager that is used to managed certificates and their aliases.
 * 
 * @author mkoegel
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESKeyStoreManager {

	/**
	 * Adds a certificate to the KeyStore.
	 * 
	 * @param alias
	 *            alias for the certificate
	 * @param path
	 *            path to the certificate file
	 * @throws ESCertificateException
	 *             if problems occur with storing, accessing or identifying the certificate
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void addCertificate(String alias, String path) throws ESCertificateException;

	/**
	 * Adds a certificate to the KeyStore.
	 * 
	 * @param alias
	 *            alias for the certificate
	 * @param certificate
	 *            {@link InputStream} delivering the certificate. Stream is used by
	 *            {@link java.security.cert.CertificateFactory#generateCertificate(InputStream)}.
	 * @throws ESCertificateException
	 *             if problems occur with storing, accessing or identifying the certificate
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void addCertificate(String alias, InputStream certificate) throws ESCertificateException;

	/**
	 * Returns the default certificate alias.
	 * 
	 * @return the alias of the default certificate
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	String getDefaultCertificate();

	/**
	 * Sets the alias for the default certificate.
	 * 
	 * @param defaultCertificate
	 *            certificate alias, use null to unset the currently set default certificate
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void setDefaultCertificate(String defaultCertificate);

	/**
	 * Checks whether a certificate for a given alias exists.
	 * 
	 * @param alias
	 *            the alias which needs to be check
	 * 
	 * @return {@code true} if a certificate with the given alias exists, {@code false} otherwise
	 * 
	 * @throws ESCertificateException in case of failure
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	boolean certificateExists(String alias) throws ESCertificateException;

}