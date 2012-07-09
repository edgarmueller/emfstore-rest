package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class MergeController extends ServerCall<Void> {

	@Override
	protected Void run() throws EmfStoreException {
		merge();
		return null;
	}

	private void merge() {
		// TODO Auto-generated method stub

	}

}
