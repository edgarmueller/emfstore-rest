package org.eclipse.emf.emfstore.client.api;

import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;

public interface IWorkspaceProvider {

	IWorkspaceProvider INSTANCE = WorkspaceProvider.getInstance();

	IWorkspace getWorkspace();

}
