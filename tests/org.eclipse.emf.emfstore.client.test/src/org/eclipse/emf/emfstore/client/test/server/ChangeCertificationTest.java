package org.eclipse.emf.emfstore.client.test.server;

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
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESCertificateStoreException;
import org.eclipse.emf.emfstore.client.exceptions.ESInvalidCertificateException;
import org.eclipse.emf.emfstore.client.test.Activator;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.connection.ServerKeyStoreManager;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

public class ChangeCertificationTest extends ServerTests {

	@Override
	public void before() {
		SetupHelper.stopServer();
		try {
			unsetKeystoreField();
		} catch (IllegalArgumentException e1) {
			fail(e1.getMessage());
		} catch (SecurityException e1) {
			fail(e1.getMessage());
		} catch (IllegalAccessException e1) {
			fail(e1.getMessage());
		} catch (NoSuchFieldException e1) {
			fail(e1.getMessage());
		}
		try {
			replaceKeystore();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		getServer().toInternalAPI().setLastUsersession(null);
		SetupHelper.startSever();
	}

	private void unsetKeystoreField() throws IllegalArgumentException, IllegalAccessException, SecurityException,
		NoSuchFieldException {
		java.lang.reflect.Field privateKeystoreField = ServerKeyStoreManager.class.getDeclaredField("keyStore");
		privateKeystoreField.setAccessible(true);
		privateKeystoreField.set(ServerKeyStoreManager.getInstance(), null);
	}

	private void replaceKeystore() throws IOException {
		FileUtils.copyInputStreamToFile(getResource("sampleFiles/emfstoreServer.keystore"),
			new File(ServerConfiguration.getServerKeyStorePath()));
		Properties properties = new Properties();
		properties.load(getResource("sampleFiles/es.properties"));
		ServerConfiguration.setProperties(properties, false);
	}

	private InputStream getResource(String resource) throws IOException {

		URL configURL = Activator.getDefault().getBundle().getBundleContext().getBundle()
			.getEntry(resource);

		if (configURL != null) {
			InputStream input = configURL.openStream();
			return input;
		}

		return null;
	}

	private void importCertificate() throws ESInvalidCertificateException, ESCertificateStoreException, IOException {
		KeyStoreManager.getInstance().addCertificate("testAlias",
			getResource("sampleFiles/server.public-key"));

	}

	@Test
	public void exchangeCertificateAndLogin()
		throws ESInvalidCertificateException, ESCertificateStoreException, ESException, KeyStoreException, IOException {
		importCertificate();
		assertFalse(KeyStoreManager.getInstance().isDefaultCertificate("testAlias"));
		KeyStoreManager.getInstance().setDefaultCertificate("testAlias");
		assertTrue(KeyStoreManager.getInstance().isDefaultCertificate("testAlias"));
		getServer().setCertificateAlias("testAlias");
		ESUsersession login = getServer().login("super", "super");
		Certificate certificate = KeyStoreManager.getInstance().getCertificate("testAlias");
		assertNotNull(certificate);
		assertTrue(login.isLoggedIn());
	}
}
