package org.eclipse.emf.emfstore.jax.server.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;

/**
 * super class for all resource classes containing an EMFStore instance and an AccessControl instance
 * @author Pascal
 *
 */
public abstract class JaxrsResource {
	
	protected EMFStore emfStore;
	protected AccessControl accessControl;

	public EMFStore getEmfStore() {
		return emfStore;
	}

	public void setEmfStore(EMFStore emfStore) {
		this.emfStore = emfStore;
	}

	public AccessControl getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(AccessControl accessControl) {
		this.accessControl = accessControl;
	}

	/**
	 * @param emfStore
	 * @param accessControl
	 */
	public JaxrsResource(EMFStore emfStore, AccessControl accessControl) {

		this.emfStore = emfStore;
		this.accessControl = accessControl;
	}

	public JaxrsResource() {
		// TODO: delete this constructor afterwards!
		emfStore = null;
		accessControl = null;
	}
	
	/**
	 * @return
	 */
	protected SessionId retrieveSessionId() {
		SessionId sessionId = ModelFactory.eINSTANCE.createSessionId();
		return sessionId;
	}

	/**
	 * @param eObjects
	 * @return
	 */
	protected StreamingOutput convertEObjectsToXmlIntoStreamingOutput(
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
	
}
