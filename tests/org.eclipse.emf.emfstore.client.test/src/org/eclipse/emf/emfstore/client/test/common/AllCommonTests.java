/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common;

import org.eclipse.emf.emfstore.client.test.common.extensionregistry.ExtensionRegistryTest;
import org.eclipse.emf.emfstore.client.test.common.observerbus.ObserverBusTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for common functionality used by the server as well as the client.
 * 
 * @author ovonwesen
 * @author emueller
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ObserverBusTest.class, ExtensionRegistryTest.class, ProjectCacheTest.class })
public class AllCommonTests {

}
