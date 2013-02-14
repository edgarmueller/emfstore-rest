package org.eclipse.emf.emfstore.internal.server.model.impl;

import org.eclipse.emf.emfstore.server.model.ESLocalProjectId;

public class LocalProjectIdImpl implements ESLocalProjectId {

	private String id;

	public LocalProjectIdImpl(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
