package org.eclipse.emf.emfstore.jax.server.resources;

import javax.ws.rs.core.Response;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

public interface IProjects {

	public Response getProjectList() throws ESException;
	
}
