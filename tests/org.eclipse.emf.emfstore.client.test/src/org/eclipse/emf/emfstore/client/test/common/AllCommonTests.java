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
package org.eclipse.emf.emfstore.client.test.common;

import org.eclipse.emf.emfstore.client.test.common.extensionregistry.ExtensionRegistryTest;
import org.eclipse.emf.emfstore.client.test.common.observerbus.ObserverBusTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ObserverBusTest.class, ExtensionRegistryTest.class })
public class AllCommonTests {

}
