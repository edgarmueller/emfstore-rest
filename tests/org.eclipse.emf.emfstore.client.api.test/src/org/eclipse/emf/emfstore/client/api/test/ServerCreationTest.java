/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.junit.Test;

public class ServerCreationTest {

	private static final String CERTIFICATE_ALIAS = "MyCertificate"; //$NON-NLS-1$
	private static final String LOCALHOST_IP = "127.0.0.1"; //$NON-NLS-1$
	private static final String SERVER_NEW_NAME = "NewName"; //$NON-NLS-1$
	private static final String SERVER_URL = "localhost"; //$NON-NLS-1$
	private static final String SERVER_NAME = "MyServer"; //$NON-NLS-1$

	@Test
	public void testCreation() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertNotNull(server);
	}

	@Test
	public void testNameSet() {
		final ESServer server = ESServer.FACTORY
			.createServer(SERVER_NAME, SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(SERVER_NAME, server.getName());
	}

	@Test
	public void testNameChange() {
		final ESServer server = ESServer.FACTORY
			.createServer(SERVER_NAME, SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(SERVER_NAME, server.getName());
		server.setName(SERVER_NEW_NAME);
		assertEquals(SERVER_NEW_NAME, server.getName());
	}

	@Test
	public void testURL() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(SERVER_URL, server.getURL());
	}

	@Test
	public void testURLChange() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(SERVER_URL, server.getURL());
		server.setURL(LOCALHOST_IP);
		assertEquals(LOCALHOST_IP, server.getURL());
	}

	@Test
	public void testPort() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(8080, server.getPort());
	}

	@Test
	public void testPortChange() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(8080, server.getPort());
		server.setPort(8081);
		assertEquals(8081, server.getPort());
	}

	@Test
	public void testCertificate() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(KeyStoreManager.DEFAULT_CERTIFICATE, server.getCertificateAlias());
	}

	@Test
	public void testCertificateChange() {
		final ESServer server = ESServer.FACTORY.createServer(SERVER_URL, 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(KeyStoreManager.DEFAULT_CERTIFICATE, server.getCertificateAlias());
		server.setCertificateAlias(CERTIFICATE_ALIAS);
		assertEquals(CERTIFICATE_ALIAS, server.getCertificateAlias());
	}

}
