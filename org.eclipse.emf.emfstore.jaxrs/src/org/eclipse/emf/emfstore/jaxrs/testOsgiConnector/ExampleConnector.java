/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Pascal - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.jaxrs.testOsgiConnector;

import com.eclipsesource.jaxrs.consumer.ConsumerFactory;

/**
 * @author Pascal
 * 
 */
public class ExampleConnector {

	public static void run() {

		final ExampleService exampleService = ConsumerFactory.createConsumer(
			"http://localhost:9090/services/osgi-jax-rs", ExampleService.class);

		System.out.println(exampleService.sayHelloPlain());
	}

}
