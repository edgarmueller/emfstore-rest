package org.eclipse.emf.emfstore.client.test.config;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.emfstore.internal.server.DefaultServerWorkspaceLocationProvider;

public class ServerLocationProvider extends DefaultServerWorkspaceLocationProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.DefaultServerWorkspaceLocationProvider#getRootDirectory()
	 */
	@Override
	protected String getRootDirectory() {
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	}
}
