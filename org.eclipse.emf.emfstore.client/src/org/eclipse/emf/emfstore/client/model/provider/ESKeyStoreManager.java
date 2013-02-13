package org.eclipse.emf.emfstore.client.model.provider;

import java.io.InputStream;
import java.security.cert.CertificateFactory;

import org.eclipse.emf.emfstore.internal.client.model.exceptions.CertificateStoreException;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.InvalidCertificateException;

public interface ESKeyStoreManager {

	/**
	 * Adds a certificate to the KeyStore.
	 * 
	 * @param alias
	 *            alias for the certificate
	 * @param path
	 *            path to the certificate file
	 * @throws InvalidCertificateException
	 *             certificate cannot be found, accessed or identified
	 * @throws CertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations.
	 */
	public abstract void addCertificate(String alias, String path) throws InvalidCertificateException,
		CertificateStoreException;

	/**
	 * Adds a certificate to the KeyStore.
	 * 
	 * @param alias
	 *            alias for the certificate
	 * @param certificate
	 *            inputstream delivering the certificate. Stream is used by
	 *            {@link CertificateFactory#generateCertificate(InputStream)}.
	 * @throws InvalidCertificateException
	 *             certificate cannot be found, accessed or identified
	 * @throws CertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations
	 */
	public abstract void addCertificate(String alias, InputStream certificate) throws InvalidCertificateException,
		CertificateStoreException;

	/**
	 * Returns the default certificate alias.
	 * 
	 * @return alias
	 */
	public abstract String getDefaultCertificate();

	/**
	 * Sets the alias for the default certificate.
	 * 
	 * @param defaultCertificate
	 *            certificate alias, use null to unset
	 */
	public abstract void setDefaultCertificate(String defaultCertificate);

	/**
	 * Checks whether a certificate for a given alias exists.
	 * 
	 * @param alias
	 *            to check
	 * @return true if exists
	 * @throws CertificateStoreException
	 *             in case of failure
	 */
	public abstract boolean certificateExists(String alias) throws CertificateStoreException;

}