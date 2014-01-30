package org.eclipse.emf.emfstore.jaxrs.test;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

public class ConnectionExampleTest {

	@Test
	public void testsayHelloPlain() {
		
				
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:9090/services").path("osgi-jax-rs");
		
		Response response = target.request(MediaType.TEXT_PLAIN).get();
		
		assertEquals("JAX-RS and OSGi are a lovely couple.", response.readEntity(String.class));
	}

}
