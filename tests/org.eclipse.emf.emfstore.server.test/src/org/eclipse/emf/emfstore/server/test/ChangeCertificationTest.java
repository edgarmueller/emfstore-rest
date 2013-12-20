/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStoreException;
import java.security.cert.Certificate;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESCertificateException;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.connection.ServerKeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChangeCertificationTest extends ESTest {

	private static final String LOCAL_SERVER = "Local Server"; //$NON-NLS-1$
	private static final String ES_PROPERTIES = "es.properties"; //$NON-NLS-1$
	private static final String SERVER_PUBLIC_KEY_PATH = "sampleFiles/server.public-key"; //$NON-NLS-1$
	private static final String ES_PROPERTIES_PATH = "sampleFiles/es.properties"; //$NON-NLS-1$
	private static final String SERVER_KEYSTORE_PATH = "sampleFiles/emfstoreServer.keystore"; //$NON-NLS-1$
	private static final String EMFSTORE_TEST_CERTIFICATE_DO_NOT_USE_IN_PRODUCTION = "emfstore test certificate (do not use in production!)"; //$NON-NLS-1$
	private static final String TEST_ALIAS = "testAlias"; //$NON-NLS-1$

	private static ESServer server;
	private EMFStoreController localEMFStoreServer;

	@Override
	@Before
	public void before() {
		super.before();
		ServerKeyStoreManager.getInstance().unloadKeyStore();
		try {
			replaceKeystore();
			server = createAndStartLocalServer();
		} catch (final IOException ex) {
			fail(ex.getMessage());
		} catch (final ESServerStartFailedException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@After
	public void after() {
		localEMFStoreServer.stopServer();
		try {
			new File(ServerConfiguration.getServerKeyStorePath()).delete();
			new File(ServerConfiguration.getConfFile()).delete();
			ServerKeyStoreManager.getInstance().unloadKeyStore();
		} catch (final IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (final SecurityException e) {
			fail(e.getMessage());
		}
		try {
			KeyStoreManager.getInstance().removeCertificate(TEST_ALIAS);
		} catch (final ESCertificateException e) {
			fail(e.getMessage());
		}
		KeyStoreManager.getInstance().setDefaultCertificate(EMFSTORE_TEST_CERTIFICATE_DO_NOT_USE_IN_PRODUCTION);
	}

	private void replaceKeystore() throws IOException {

		InputStream stream = getResource(SERVER_KEYSTORE_PATH);
		FileUtils.copyInputStreamToFile(stream,
			new File(ServerConfiguration.getServerKeyStorePath()));
		stream.close();

		stream = getResource(ES_PROPERTIES_PATH);
		FileUtils.copyInputStreamToFile(stream,
			new File(ServerConfiguration.getConfDirectory() + File.separatorChar + ES_PROPERTIES));
		stream.close();
	}

	public ESServer createAndStartLocalServer() throws ESServerStartFailedException {
		if (localEMFStoreServer == null) {
			try {
				localEMFStoreServer = EMFStoreController.runAsNewThread();
			} catch (final FatalESException e) {
				throw new ESServerStartFailedException(e);
			}
		}
		final ESServer server = ESServer.FACTORY.createServer(LOCAL_SERVER, ServerUtil.localhost(),
			Integer.parseInt(ServerConfiguration.XML_RPC_PORT_DEFAULT),
			TEST_ALIAS);
		return server;
	}

	private InputStream getResource(String resource) throws IOException {

		final URL configURL = Platform.getBundle("org.eclipse.emf.emfstore.server.test").getEntry(resource);

		if (configURL != null) {
			final InputStream input = configURL.openStream();
			return input;
		}

		return null;
	}

	private void importCertificate() throws ESCertificateException, IOException {
		final InputStream stream = getResource(SERVER_PUBLIC_KEY_PATH);
		KeyStoreManager.getInstance().addCertificate(TEST_ALIAS, stream);
		stream.close();
	}

	@Test
	public void exchangeCertificateAndLogin()
		throws ESCertificateException, ESException, KeyStoreException, IOException {
		importCertificate();
		assertFalse(KeyStoreManager.getInstance().isDefaultCertificate(TEST_ALIAS));
		KeyStoreManager.getInstance().setDefaultCertificate(TEST_ALIAS);
		assertTrue(KeyStoreManager.getInstance().isDefaultCertificate(TEST_ALIAS));
		server.setCertificateAlias(TEST_ALIAS);
		final ESUsersession login = server.login(ServerUtil.superUser(), ServerUtil.superUserPassword());
		final Certificate certificate = KeyStoreManager.getInstance().getCertificate(TEST_ALIAS);
		assertNotNull(certificate);
		assertTrue(login.isLoggedIn());
	}
}
