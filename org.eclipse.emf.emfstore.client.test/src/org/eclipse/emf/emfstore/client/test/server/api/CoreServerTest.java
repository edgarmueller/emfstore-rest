package org.eclipse.emf.emfstore.client.test.server.api;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.server.EmfStoreController;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.core.EmfStoreImpl;
import org.eclipse.emf.emfstore.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.server.model.ModelFactory;
import org.eclipse.emf.emfstore.server.model.ServerSpace;

public abstract class CoreServerTest extends WorkspaceTest {

	private EmfStoreImpl emfStore;
	private AuthControlMock authMock;
	private ServerSpace serverSpace;
	private ConnectionMock connectionMock;

	@Override
	public void beforeHook() {
		try {
			initServer();
		} catch (FatalEmfStoreException e) {
			throw new RuntimeException(e);
		}
	}

	public void initServer() throws FatalEmfStoreException {
		ServerConfiguration.setTesting(true);
		serverSpace = initServerSpace();
		EmfStoreController.getHistoryCache(serverSpace);
		authMock = new AuthControlMock();
		emfStore = new EmfStoreImpl(serverSpace, authMock);
		connectionMock = new ConnectionMock(emfStore, authMock);
	}

	private ServerSpace initServerSpace() {
		ResourceSetImpl set = new ResourceSetImpl();
		set.setResourceFactoryRegistry(new ResourceFactoryMock());
		Resource resource = set.createResource(URI.createURI(""));
		ServerSpace serverSpace = ModelFactory.eINSTANCE.createServerSpace();
		resource.getContents().add(serverSpace);
		return serverSpace;
	}

	@Override
	public ConnectionManager initConnectionManager() {
		return connectionMock;
	}

	public EmfStoreImpl getEmfStore() {
		return emfStore;
	}

	public ConnectionMock getConnectionMock() {
		return connectionMock;
	}

	public ServerSpace getServerSpace() {
		return serverSpace;
	}
}
