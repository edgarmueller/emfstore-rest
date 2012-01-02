package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;

public class RemoveTagController extends ServerCall<Void> {

	private ProjectSpace projectSpace;
	private TagVersionSpec tag;
	private PrimaryVersionSpec versionSpec;

	public RemoveTagController(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec, TagVersionSpec tag) {
		this.projectSpace = projectSpace;
		this.versionSpec = versionSpec;
		this.tag = tag;
	}

	@Override
	protected Void run() throws EmfStoreException {
		projectSpace.removeTag(versionSpec, tag);
		return null;
	}

}
