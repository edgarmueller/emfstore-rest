/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.junit.Test;

public class ServerCreationTest {

	@Test
	public void testCreation() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertNotNull(server);
	}

	@Test
	public void testNameSet() {
		ESServer server = ESServer.FACTORY
			.getServer("MyServer", "localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("MyServer", server.getName());
	}

	@Test
	public void testNameChange() {
		ESServer server = ESServer.FACTORY
			.getServer("MyServer", "localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("MyServer", server.getName());
		server.setName("NewName");
		assertEquals("NewName", server.getName());
	}

	@Test
	public void testURL() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("localhost", server.getURL());
	}

	@Test
	public void testURLChange() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("localhost", server.getURL());
		server.setURL("127.0.0.1");
		assertEquals("127.0.0.1", server.getURL());
	}

	@Test
	public void testPort() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(8080, server.getPort());
	}

	@Test
	public void testPortChange() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(8080, server.getPort());
		server.setPort(8081);
		assertEquals(8081, server.getPort());
	}

	@Test
	public void testCertificate() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(KeyStoreManager.DEFAULT_CERTIFICATE, server.getCertificateAlias());
	}

	@Test
	public void testCertificateChange() {
		ESServer server = ESServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(KeyStoreManager.DEFAULT_CERTIFICATE, server.getCertificateAlias());
		server.setCertificateAlias("MyCertificate");
		assertEquals("MyCertificate", server.getCertificateAlias());
	}

}
