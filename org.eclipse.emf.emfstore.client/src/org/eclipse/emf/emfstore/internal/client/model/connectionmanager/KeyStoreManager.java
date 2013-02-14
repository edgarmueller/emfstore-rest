/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.exceptions.ESCertificateStoreException;
import org.eclipse.emf.emfstore.client.exceptions.ESInvalidCertificateException;
import org.eclipse.emf.emfstore.client.model.provider.ESClientConfigurationProvider;
import org.eclipse.emf.emfstore.client.model.provider.ESKeyStoreManager;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;

/**
 * The KeyStoreManager manages the client's KeyStore in which the SSL
 * certificates for multiple EMFStore servers can be stored.
 * 
 * @author wesendon
 */
public final class KeyStoreManager implements ESKeyStoreManager {

	/**
	 * Name of keyStore file.
	 */
	public static final String KEYSTORENAME = "emfstoreClient.keystore";
	private static final String KEYSTOREPASSWORD = "654321";
	private static final String CERTIFICATE_TYPE = "X.509";
	private static final String CIPHER_ALGORITHM = "RSA";

	/**
	 * Certificate Alias for development test certificate.
	 */
	public static final String DEFAULT_CERTIFICATE = "emfstore test certificate (do not use in production!)"; // "EMFStore Test Certificate (DO NOT USE IN PRODUCTION!)";

	private static KeyStoreManager instance;

	private String defaultCertificate;
	private KeyStore keyStore;

	private KeyStoreManager() {
		defaultCertificate = null;
		setupKeys();
		loadConfiguration();
	}

	private void loadConfiguration() {
		ESClientConfigurationProvider provider = new ExtensionPoint(
			"org.eclipse.emf.emfstore.client.defaultConfigurationProvider").getClass("providerClass",
			ESClientConfigurationProvider.class);
		if (provider == null) {
			return;
		}
		provider.initDefaultCertificates(this);

	}

	/**
	 * Returns an instance of the {@link KeyStoreManager}.
	 * 
	 * @return {@link KeyStoreManager}
	 */
	public static synchronized KeyStoreManager getInstance() {
		if (instance == null) {
			instance = new KeyStoreManager();
		}
		return instance;
	}

	/**
	 * This method sets the JVM properties in order to use SSL encryption.
	 * 
	 * @throws IOException
	 */
	public void setupKeys() {
		// No changes to exception handling here, due to call nature.
		if (!keyStoreExists()) {
			// create directory ~/.emfstore/ if necessary
			File emfstoreDir = new File(Configuration.getWorkspaceDirectory());
			if (!emfstoreDir.exists()) {
				emfstoreDir.mkdir();
			}
			InputStream inputStream = getClass().getResourceAsStream(KEYSTORENAME);
			try {
				// configure file
				File clientKeyTarget = new File(Configuration.getWorkspaceDirectory() + KEYSTORENAME);
				// copy to destination
				FileUtil.copyFile(inputStream, clientKeyTarget);
			} catch (IOException e) {
				// TODO OW: exception? - now the user will be alerted to the
				// problem as soon as he tries to connect.
				// throw new ConnectionException("Couldn't find keystore.");
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO: ignore exception for now, as above
				}
			}
		}

		System.setProperty("javax.net.ssl.trustStore", getPathToKeyStore());
		System.setProperty("javax.net.ssl.keyStore", getPathToKeyStore());
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTOREPASSWORD);
	}

	/**
	 * Lists all certificates in the client's KeyStore.
	 * 
	 * @return string representation of the certificates
	 * @throws ESCertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations.
	 */
	public ArrayList<String> getCertificates() throws ESCertificateStoreException {
		loadKeyStore();
		ArrayList<String> certificates = new ArrayList<String>();
		try {
			Enumeration<String> aliases = keyStore.aliases();
			for (; aliases.hasMoreElements();) {
				String tmp = aliases.nextElement();
				certificates.add(tmp);
			}
		} catch (KeyStoreException e) {
			String message = "Loading certificates failed!";
			WorkspaceUtil.logException(message, e);
			throw new ESCertificateStoreException(message, e);
		}
		return certificates;
	}

	/**
	 * Deletes a certificate in the keystore.
	 * 
	 * @param alias
	 *            alias of certificate
	 * @throws ESCertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations.
	 */
	public void deleteCertificate(String alias) throws ESCertificateStoreException {
		if (isDefaultCertificate(alias)) {
			throw new ESCertificateStoreException("Cannot delete default certificate!");
		} else {
			loadKeyStore();
			try {
				keyStore.deleteEntry(alias);
				storeKeyStore();
			} catch (KeyStoreException e) {
				String message = "Deleting certificate failed!";
				WorkspaceUtil.logException(message, e);
				throw new ESCertificateStoreException(message, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.provider.ESKeyStoreManager#addCertificate(java.lang.String,
	 *      java.lang.String)
	 */
	public void addCertificate(String alias, String path) throws ESInvalidCertificateException, ESCertificateStoreException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(path);
			addCertificate(alias, fileInputStream);
		} catch (FileNotFoundException e) {
			String message = "Storing certificate failed!";
			WorkspaceUtil.logException(message, e);
			throw new ESCertificateStoreException(message, e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					String message = "Storing certificate failed!";
					WorkspaceUtil.logException(message, e);
					throw new ESCertificateStoreException(message, e);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.provider.ESKeyStoreManager#addCertificate(java.lang.String,
	 *      java.io.InputStream)
	 */
	public void addCertificate(String alias, InputStream certificate) throws ESInvalidCertificateException,
		ESCertificateStoreException {
		if (!isDefaultCertificate(alias)) {
			loadKeyStore();
			try {
				CertificateFactory factory = CertificateFactory.getInstance(CERTIFICATE_TYPE);
				Certificate newCertificate = factory.generateCertificate(certificate);
				keyStore.setCertificateEntry(alias, newCertificate);
				storeKeyStore();
			} catch (CertificateException e) {
				String message = "Please choose a valid certificate!";
				throw new ESInvalidCertificateException(message);
			} catch (KeyStoreException e) {
				String message = "Storing certificate failed!";
				WorkspaceUtil.logException(message, e);
				throw new ESCertificateStoreException(message, e);
			}
		}
	}

	private void storeKeyStore() throws ESCertificateStoreException {
		loadKeyStore();
		try {
			keyStore.store(new FileOutputStream(getPathToKeyStore()), KEYSTOREPASSWORD.toCharArray());
		} catch (KeyStoreException e) {
			String message = "Storing certificate failed!";
			WorkspaceUtil.logWarning(message, e);
			throw new ESCertificateStoreException(message, e);
		} catch (NoSuchAlgorithmException e) {
			String message = "Storing certificate failed!";
			WorkspaceUtil.logWarning(message, e);
			throw new ESCertificateStoreException(message, e);
		} catch (CertificateException e) {
			String message = "Storing certificate failed!";
			WorkspaceUtil.logWarning(message, e);
			throw new ESCertificateStoreException(message, e);
		} catch (FileNotFoundException e) {
			String message = "Storing certificate failed!";
			WorkspaceUtil.logWarning(message, e);
			throw new ESCertificateStoreException(message, e);
		} catch (IOException e) {
			String message = "Storing certificate failed!";
			WorkspaceUtil.logWarning(message, e);
			throw new ESCertificateStoreException(message, e);
		}
	}

	/**
	 * Reloads the keystore.
	 * 
	 * @throws ESCertificateStoreException
	 *             in case of failure
	 */
	public void reloadKeyStore() throws ESCertificateStoreException {
		keyStore = null;
		loadKeyStore();
	}

	private void loadKeyStore() throws ESCertificateStoreException {
		if (keyStore == null) {
			try {
				keyStore = KeyStore.getInstance("JKS");
				FileInputStream fileInputStream = new FileInputStream(getPathToKeyStore());
				keyStore.load(fileInputStream, KEYSTOREPASSWORD.toCharArray());
				fileInputStream.close();
			} catch (KeyStoreException e) {
				String message = "Loading certificate failed!";
				WorkspaceUtil.logWarning(message, e);
				throw new ESCertificateStoreException(message, e);
			} catch (NoSuchAlgorithmException e) {
				String message = "Loading certificate failed!";
				WorkspaceUtil.logWarning(message, e);
				throw new ESCertificateStoreException(message, e);
			} catch (CertificateException e) {
				String message = "Loading certificate failed!";
				WorkspaceUtil.logWarning(message, e);
				throw new ESCertificateStoreException(message, e);
			} catch (FileNotFoundException e) {
				String message = "Loading certificate failed!";
				WorkspaceUtil.logWarning(message, e);
				throw new ESCertificateStoreException(message, e);
			} catch (IOException e) {
				String message = "Loading certificate failed!";
				WorkspaceUtil.logWarning(message, e);
				throw new ESCertificateStoreException(message, e);
			}
		}
	}

	/**
	 * Returns a SSL Context. This is need for encryption, used by the
	 * SSLSocketFactory.
	 * 
	 * @return SSL Context
	 * @throws ESCertificateStoreException
	 *             in case of failure retrieving the context
	 */
	public SSLContext getSSLContext() throws ESCertificateStoreException {
		try {
			loadKeyStore();
			KeyManagerFactory managerFactory = KeyManagerFactory.getInstance("SunX509");
			managerFactory.init(keyStore, KEYSTOREPASSWORD.toCharArray());
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(keyStore);
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(managerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			return sslContext;
		} catch (NoSuchAlgorithmException e) {
			throw new ESCertificateStoreException("Loading certificate failed!", e);
		} catch (UnrecoverableKeyException e) {
			throw new ESCertificateStoreException("Loading certificate failed!", e);
		} catch (KeyStoreException e) {
			throw new ESCertificateStoreException("Loading certificate failed!", e);
		} catch (KeyManagementException e) {
			throw new ESCertificateStoreException("Loading certificate failed!", e);
		}
	}

	/**
	 * True if a KeyStore file exists.
	 * 
	 * @return boolean
	 */
	public boolean keyStoreExists() {
		File keyStore = new File(getPathToKeyStore());
		return keyStore.exists();
	}

	/**
	 * Returns the path to the KeyStore.
	 * 
	 * @return a path
	 */
	public String getPathToKeyStore() {
		return Configuration.getWorkspaceDirectory() + KEYSTORENAME;
	}

	/**
	 * Encrypts a password.
	 * 
	 * @param password
	 *            the password to be encrypted
	 * @param server
	 *            the server from which to fetch the public key that is used for encryption
	 * @return the encrypted password
	 */
	public String encrypt(String password, ESServer server) {
		try {
			Certificate publicKey = getCertificateForEncryption(server);
			PublicKey key = publicKey.getPublicKey();
			byte[] inpBytes = password.getBytes();
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptededByteAr = cipher.doFinal(inpBytes);
			byte[] base64EncodedByteAr = Base64.encodeBase64(encryptededByteAr);
			return new String(base64EncodedByteAr);
			// TODO: OW When new login proxy object with encryption handler is
			// implemented, handle exceptions
		} catch (NoSuchAlgorithmException e) {
			// nothing to do
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// nothing to do
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// nothing to do
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// nothing to do
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// nothing to do
			e.printStackTrace();
		} catch (ESCertificateStoreException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		WorkspaceUtil.logException("Couldn't encrypt password.", new ESCertificateStoreException(
			"Couldn't encrypt password."));
		return "";
	}

	private Certificate getCertificateForEncryption(ESServer server) throws ESCertificateStoreException {
		Certificate publicKey;
		if (server == null) {
			publicKey = getCertificate(getDefaultCertificate());
		} else {
			publicKey = getCertificate(server.getCertificateAlias());
		}
		if (publicKey == null) {
			publicKey = getCertificate(getDefaultCertificate());
			if (publicKey == null) {
				throw new ESCertificateStoreException("Unable to get certificate for password encryption.");
			}
		}
		return publicKey;
	}

	/**
	 * Test whether a given alias is the default certificate alias.
	 * 
	 * @param alias
	 *            alias under test
	 * @return true if default, false else
	 */
	public boolean isDefaultCertificate(String alias) {
		return getDefaultCertificate().equals(alias);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.provider.ESKeyStoreManager#getDefaultCertificate()
	 */
	public String getDefaultCertificate() {
		if (defaultCertificate != null) {
			return defaultCertificate;
		} else if (Configuration.isDeveloperVersion()) {
			return DEFAULT_CERTIFICATE;
		} else {
			return DEFAULT_CERTIFICATE;
		}
	}

	/**
	 * Returns true if the given alias maps to an existing certificate.
	 * 
	 * @param alias
	 *            Certificate alias
	 * @return boolean
	 * @throws ESCertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations.
	 */
	public boolean contains(String alias) throws ESCertificateStoreException {
		if (getCertificate(alias) == null) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.provider.ESKeyStoreManager#setDefaultCertificate(java.lang.String)
	 */
	public void setDefaultCertificate(String defaultCertificate) {
		this.defaultCertificate = defaultCertificate;
	}

	/**
	 * Returns the certificate mapped by the given alias. Returns null if no
	 * such certificate exists.
	 * 
	 * @param alias
	 *            String
	 * @return Certificate
	 * @throws ESCertificateStoreException
	 *             is thrown when problems occur with the CertificateStore, i.e.
	 *             illegal operations.
	 */
	public Certificate getCertificate(String alias) throws ESCertificateStoreException {
		if (alias == null) {
			return null;
		}
		loadKeyStore();
		try {
			return keyStore.getCertificate(alias);
		} catch (KeyStoreException e) {
			throw new ESCertificateStoreException("Loading certificate failed!");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.provider.ESKeyStoreManager#certificateExists(java.lang.String)
	 */
	public boolean certificateExists(String alias) throws ESCertificateStoreException {
		try {
			return getCertificate(alias) != null;
		} catch (ESCertificateStoreException e) {
			if (!(e.getCause() instanceof FileNotFoundException)) {
				throw e;
			}
		}
		return false;
	}
}