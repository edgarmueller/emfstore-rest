package org.eclipse.emf.emfstore.client.test.common.extensionregistry;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.emfstore.client.sessionprovider.AbstractSessionProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionRegistry;
import org.junit.Test;

public class ExtensionRegistryTest {

	@Test
	public void testGetExistingExtensionPoin() {
		AbstractSessionProvider sessionProvider = ExtensionRegistry.INSTANCE.getType(
			"org.eclipse.emf.emfstore.client.usersessionProvider",
			"class",
			AbstractSessionProvider.class);
		assertNotNull(sessionProvider);
	}

}
