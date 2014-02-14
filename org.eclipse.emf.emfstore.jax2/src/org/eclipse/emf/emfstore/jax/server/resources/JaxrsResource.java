package org.eclipse.emf.emfstore.jax.server.resources;

import org.eclipse.emf.emfstore.internal.server.EMFStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AccessControl;

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
	
}
