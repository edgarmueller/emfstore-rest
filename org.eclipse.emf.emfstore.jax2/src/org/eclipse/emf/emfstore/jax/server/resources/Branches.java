package org.eclipse.emf.emfstore.jax.server.resources;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.jax.common.CallParamStrings;
import org.eclipse.emf.emfstore.jax.common.CreateVersionDataTO;
import org.eclipse.emf.emfstore.jax.common.TransferUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

@Path(CallParamStrings.BRANCHES_PATH_COMPLETE)
public class Branches extends JaxrsResource {
	
	@GET
	@Produces({ MediaType.TEXT_XML })
	public Response getBranches(@PathParam("projectId") String projectIdAsString) {
		
		//create ProjectId
		ProjectId projectId = ModelFactory.eINSTANCE.createProjectId();
		projectId.setId(projectIdAsString);
		
		try {
			List<BranchInfo> branches = emfStore.getBranches(retrieveSessionId(), projectId );
			
			final StreamingOutput streamingOutput = convertEObjectsToXmlIntoStreamingOutput(branches);
			return Response.ok(streamingOutput).build();
			
		} catch (ESException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
