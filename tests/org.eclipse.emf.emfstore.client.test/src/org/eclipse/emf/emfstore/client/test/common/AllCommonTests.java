package org.eclipse.emf.emfstore.client.test.common;

import org.eclipse.emf.emfstore.client.test.common.extensionregistry.ExtensionRegistryTest;
import org.eclipse.emf.emfstore.client.test.common.observerbus.ObserverBusTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ObserverBusTest.class, ExtensionRegistryTest.class })
public class AllCommonTests {

}
