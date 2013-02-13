package org.eclipse.emf.emfstore.internal.server.model.impl;

import org.eclipse.emf.emfstore.server.model.api.ILocalProjectId;

public class LocalProjectIdImpl implements ILocalProjectId {

	private String id;

	public LocalProjectIdImpl(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
