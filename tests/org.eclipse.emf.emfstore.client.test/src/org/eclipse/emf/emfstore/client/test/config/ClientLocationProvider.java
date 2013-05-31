package org.eclipse.emf.emfstore.client.test.config;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.emfstore.internal.client.model.util.DefaultWorkspaceLocationProvider;

public class ClientLocationProvider extends DefaultWorkspaceLocationProvider {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.util.DefaultWorkspaceLocationProvider#getRootDirectory()
	 */
	@Override
	protected String getRootDirectory() {
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	}

}
