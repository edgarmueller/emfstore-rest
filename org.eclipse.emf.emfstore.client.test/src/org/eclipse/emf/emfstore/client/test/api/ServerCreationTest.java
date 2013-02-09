package org.eclipse.emf.emfstore.client.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerCreationTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreation() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertNotNull(server);
	}

	@Test
	public void testNameGenerated() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("EMFStore (generated entry)", server.getName());
	}

	@Test
	public void testNameSet() {
		IServer server = IServer.FACTORY.getServer("MyServer", "localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("MyServer", server.getName());
	}

	@Test
	public void testNameChange() {
		IServer server = IServer.FACTORY.getServer("MyServer", "localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("MyServer", server.getName());
		server.setName("NewName");
		assertEquals("NewName", server.getName());
	}

	@Test
	public void testURL() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("localhost", server.getUrl());
	}

	@Test
	public void testURLChange() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals("localhost", server.getUrl());
		server.setUrl("127.0.0.1");
		assertEquals("127.0.0.1", server.getUrl());
	}

	@Test
	public void testPort() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(8080, server.getPort());
	}

	@Test
	public void testPortChange() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(8080, server.getPort());
		server.setPort(8081);
		assertEquals(8081, server.getPort());
	}

	@Test
	public void testCertificate() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(KeyStoreManager.DEFAULT_CERTIFICATE, server.getCertificateAlias());
	}

	@Test
	public void testCertificateChange() {
		IServer server = IServer.FACTORY.getServer("localhost", 8080, KeyStoreManager.DEFAULT_CERTIFICATE);
		assertEquals(KeyStoreManager.DEFAULT_CERTIFICATE, server.getCertificateAlias());
		server.setCertificateAlias("MyCertificate");
		assertEquals("MyCertificate", server.getCertificateAlias());
	}

}
