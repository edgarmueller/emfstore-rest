package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.observers.CheckoutObserver;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;

public class CheckoutController extends ServerCall<ProjectSpace> {

	private final ProjectInfo projectInfo;

	public CheckoutController(ProjectInfo projectInfo) {
		// FIXME
		super((ServerInfo) projectInfo.eContainer());
		this.projectInfo = projectInfo;
	}

	@Override
	protected ProjectSpace run() throws EmfStoreException {
		ProjectSpace checkout = WorkspaceManager.getInstance().getCurrentWorkspace()
			.checkout(getUsersession(), projectInfo);
		WorkspaceManager.getObserverBus().notify(CheckoutObserver.class).checkoutDone(checkout);
		return checkout;
	}

}
