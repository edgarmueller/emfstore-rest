package org.eclipse.emf.emfstore.jax.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.emf.emfstore.jax.common.CallParamStrings;

@Path(CallParamStrings.BRANCHES_PATH)
public class Branches extends JaxrsResource {
	
	@GET
	public Response getTest() {
		return Response.ok("It works!").build();
	}

}
