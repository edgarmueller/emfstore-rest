package org.eclipse.emf.emfstore.jax.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

//TODO: refactor so that the methods in JaxrsResource and JaxrsConnectionManager aren't used, but these here are used instead
public class TransferUtil {
	
	/**
	 * @param eObjects
	 * @return
	 */
	public static StreamingOutput convertEObjectsToXmlIntoStreamingOutput(
			final Collection<? extends EObject> eObjects) {
		// convert the list into XML and write it to a StreamingOutput
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) resourceSetImpl
				.createResource(URI.createURI(fileNameURI));
		
		for(EObject e : eObjects) {
			EObject copy = EcoreUtil.copy(e);  //neccessary because add() has side effects!
			resource.getContents().add(copy);
		}

		final StreamingOutput streamingOutput = new StreamingOutput() {

			public void write(OutputStream output) throws IOException,
					WebApplicationException {

				resource.doSave(output, null);

			}
		};
		return streamingOutput;
	}
	
	/**
	 * converts an response's inputstream to a list of EObjects, for example: List<ProjectInfo>, List<BranchInfo>, ...
	 * @param 
	 * @return
	 */
	public static <T extends EObject> List<T> getEObjectListFromInputStream(
			final InputStream is) {
		//create XMLResource and read the entity
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		final String fileNameURI = "blabla";
		final XMLResourceImpl resource = (XMLResourceImpl) resourceSetImpl.createResource(URI.createURI(fileNameURI));
		
		List<T> eObjectList = new ArrayList<T>();
		try {
			//create the List<ProjectInfo> from the input stream
			resource.doLoad(is, null);   
			for(Object o : resource.getContents()) {
				eObjectList.add((T) o);
			}
			
		} catch (final IOException ex) {
			System.err.println(ex.getMessage());
		}
		return eObjectList;
	}
	
	/**
	 * converts an response's inputstream to a list of EObjects, for example: List<ProjectInfo>, List<BranchInfo>, ...
	 * @param response
	 * @return
	 */
	public static <T extends EObject> List<T> getEObjectListFromResponse(
			final Response response) {
		
		final InputStream is = response.readEntity(InputStream.class);
		
		return getEObjectListFromInputStream(is);
	}
	
}
