package org.eclipse.emf.emfstore.internal.client.model;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.emfstore.client.provider.ESClientVersionProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.osgi.framework.Bundle;

public class VersioningInfo {

	private static final String CLIENT_NAME = "emfstore eclipse client";

	/**
	 * Get the client version as in the org.eclipse.emf.emfstore.internal.client manifest
	 * file.
	 * 
	 * @return the client version number
	 */
	@SuppressWarnings("cast")
	public ClientVersionInfo getClientVersion() {
		ClientVersionInfo clientVersionInfo = org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE
			.createClientVersionInfo();
		clientVersionInfo.setName(CLIENT_NAME);

		String versionId;
		ESExtensionElement version = new ESExtensionPoint("org.eclipse.emf.emfstore.client.clientVersion")
			.setThrowException(false).getFirst();

		if (version != null) {
			ESClientVersionProvider versionProvider = version.getClass("class", ESClientVersionProvider.class);
			clientVersionInfo.setName(versionProvider.getName());
			clientVersionInfo.setVersion(versionProvider.getVersion());
			return clientVersionInfo;
		}

		Bundle emfStoreBundle = Platform.getBundle("org.eclipse.emf.emfstore.client");
		versionId = (String) emfStoreBundle.getHeaders().get(org.osgi.framework.Constants.BUNDLE_VERSION);
		clientVersionInfo.setVersion(versionId);

		return clientVersionInfo;
	}

	/**
	 * Determine if this is a release version or not.
	 * 
	 * @return true if it is a release version
	 */
	public boolean isReleaseVersion() {
		return !isInternalReleaseVersion() && !getClientVersion().getVersion().endsWith("qualifier");
	}

	/**
	 * Determines if this is an internal release or not.
	 * 
	 * @return true if it is an internal release
	 */
	public boolean isInternalReleaseVersion() {
		return getClientVersion().getVersion().endsWith("internal");
	}

	/**
	 * Determines if this is an developer version or not.
	 * 
	 * @return true if it is a developer version
	 */
	public boolean isDeveloperVersion() {
		return !isReleaseVersion() && !isInternalReleaseVersion();
	}

}
