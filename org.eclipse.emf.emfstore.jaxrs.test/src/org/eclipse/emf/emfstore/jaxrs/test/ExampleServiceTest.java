package org.eclipse.emf.emfstore.jaxrs.test;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

public class ExampleServiceTest {
	
	private static WebTarget target;
	
	@BeforeClass
	public static void beforeClass() {
		Client client = ClientBuilder.newClient();
		target = client.target("http://localhost:9090/services").path("osgi-jax-rs");
	}
	
	@Test
	public void testSayHelloPlain() {
	
		Response response = target.request(MediaType.TEXT_PLAIN).get();

		assertEquals("JAX-RS and OSGi are a lovely couple.", response.readEntity(String.class));
	}
	
	@Test
	public void testSayHelloHTML() {
		
		Response response = target.request(MediaType.TEXT_HTML).get();

		assertEquals("<html> <body> <h1> JAX-RS and OSGi </h1> <p> are a lovely couple. </p> </body> </html>", response.readEntity(String.class));
	}
	
	@Test
	public void testReply() {
		
		String arg = "Linkin Park";
		Form form = new Form("arg", arg);
		Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
		
		assertEquals("Your argument was: " + arg, response.readEntity(String.class));
	}

}
