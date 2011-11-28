package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;

public class CommitController extends ServerCall<CommitCallback> {

	public CommitController(ProjectSpaceImpl projectSpace, LogMessage logMessage, CommitCallback callback,
		IProgressMonitor monitor) {
		super(projectSpace);
		setCallback(callback == null ? CommitCallback.NOCALLBACK : callback);
		setProgressMonitor(monitor);
	}

	@Override
	protected void run() throws EmfStoreException {

	}

}
