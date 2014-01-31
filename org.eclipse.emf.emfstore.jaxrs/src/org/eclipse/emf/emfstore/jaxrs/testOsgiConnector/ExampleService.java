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

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Pascal
 * 
 */
@Path("/osgi-jax-rs")
public class ExampleService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHelloPlain() {
		return "JAX-RS and OSGi are a lovely couple.";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHelloHTML() {
		return "<html> <body> <h1> JAX-RS and OSGi </h1> <p> are a lovely couple. </p> </body> </html>";
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String reply(@FormParam("arg") String arg) {

		return "Your argument was: " + arg;
	}
}
