/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.provider;

import java.io.InputStream;

import org.eclipse.emf.emfstore.client.exceptions.ESCertificateStoreException;
import org.eclipse.emf.emfstore.client.exceptions.ESInvalidCertificateException;

/**
 * The EMFStore key store manager that is used to managed certificates and their aliases.
 * 
 * @author mkoegel
 */
public interface ESKeyStoreManager {

	/**
	 * Adds a certificate to the KeyStore.
	 * 
	 * @param alias
	 *            alias for the certificate
	 * @param path
	 *            path to the certificate file
	 * @throws ESInvalidCertificateException
	 *             certificate cannot be found, accessed or identified
	 * @throws ESCertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations.
	 */
	void addCertificate(String alias, String path) throws ESInvalidCertificateException,
		ESCertificateStoreException;

	/**
	 * Adds a certificate to the KeyStore.
	 * 
	 * @param alias
	 *            alias for the certificate
	 * @param certificate
	 *            inputstream delivering the certificate. Stream is used by
	 *            {@link java.security.cert.CertificateFactory#generateCertificate(InputStream)}.
	 * @throws ESInvalidCertificateException
	 *             certificate cannot be found, accessed or identified
	 * @throws ESCertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations
	 */
	void addCertificate(String alias, InputStream certificate) throws ESInvalidCertificateException,
		ESCertificateStoreException;

	/**
	 * Returns the default certificate alias.
	 * 
	 * @return alias
	 */
	String getDefaultCertificate();

	/**
	 * Sets the alias for the default certificate.
	 * 
	 * @param defaultCertificate
	 *            certificate alias, use null to unset
	 */
	void setDefaultCertificate(String defaultCertificate);

	/**
	 * Checks whether a certificate for a given alias exists.
	 * 
	 * @param alias
	 *            the alias which needs to be check
	 * @return {@code true} if a certificate with the given alias exists, {@code false otherwise}
	 * @throws ESCertificateStoreException in case of failure
	 */
	boolean certificateExists(String alias) throws ESCertificateStoreException;

}