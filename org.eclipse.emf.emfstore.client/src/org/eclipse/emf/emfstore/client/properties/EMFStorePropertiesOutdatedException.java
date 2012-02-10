package org.eclipse.emf.emfstore.client.properties;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.EMFStoreProperty;

public class EMFStorePropertiesOutdatedException extends Exception {

	private static final long serialVersionUID = 2732549512392764249L;
	private final List<EMFStoreProperty> outdatedProperties;

	public EMFStorePropertiesOutdatedException(List<EMFStoreProperty> outdatedProperties) {
		this.outdatedProperties = outdatedProperties;
	}

	public List<EMFStoreProperty> getOutdatedProperties() {
		return outdatedProperties;
	}
}
