package org.eclipse.emf.emfstore.internal.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.client.observer.ESCheckoutObserver;
import org.eclipse.emf.emfstore.client.observer.ESCommitObserver;
import org.eclipse.emf.emfstore.client.observer.ESShareObserver;
import org.eclipse.emf.emfstore.client.observer.ESUpdateObserver;
import org.eclipse.emf.emfstore.client.provider.ESClientConfigurationProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.ChecksumErrorHandler;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

public class Behavior implements ESCommitObserver, ESUpdateObserver, ESShareObserver, ESCheckoutObserver {

	/**
	 * The value for enabling debug mode.
	 */
	public static final String DEBUG_SWITCH_ENABLED_VALUE = "enabled";
	public static final String DEBUG_SWITCH = "-debug";
	private static final String AUTO_SAVE_EXTENSION_POINT_ATTRIBUTE_NAME = "autoSave";

	private static EditingDomain editingDomain;
	private static Boolean autoSave;

	/**
	 * The command line option for enabling debug mode.
	 */
	/**
	 * The checksum value that is used in case no checksum should be computed.
	 */
	public static final long NO_CHECKSUM = -1;

	private ESChecksumErrorHandler checksumErrorHandler;

	public Behavior() {
		ESWorkspaceProviderImpl.getObserverBus().register(this);
	}

	/**
	 * Whether to enable the automatic saving of the workspace.
	 * If disabled, performance improves vastly, but clients have to
	 * perform the saving of the workspace manually.
	 * 
	 * @param enabled whether to enable auto save
	 */
	public void setAutoSave(boolean enabled) {
		autoSave = new Boolean(enabled);
	}

	/**
	 * Whether auto-save is enabled.
	 * 
	 * @return true, if auto-save is enabled, false otherwise
	 */
	public static boolean isAutoSaveEnabled() {
		// return ExtensionRegistry.INSTANCE.getBoolean("org.eclipse.emf.emfstore.client.recordingOptions",
		// AUTO_SAVE_EXTENSION_POINT_ATTRIBUTE_NAME, true);
		if (autoSave == null) {
			autoSave = new ESExtensionPoint("org.eclipse.emf.emfstore.client.recordingOptions")
				.getBoolean(
							AUTO_SAVE_EXTENSION_POINT_ATTRIBUTE_NAME, false);
		}
		return autoSave;
	}

	/**
	 * Whether the checksum check is active. If true, and checksum comparison fails, an {@link ESChecksumErrorHandler}
	 * will be active.
	 * 
	 * @return true, if the checksum comparison is activated, false otherwise
	 */
	public boolean isChecksumCheckActive() {
		ESExtensionPoint extensionPoint = new ESExtensionPoint("org.eclipse.emf.emfstore.client.checksumErrorHandler");
		return extensionPoint.getBoolean("isActive", true);
	}

	/**
	 * Returns the active {@link ESChecksumErrorHandler}. The default is {@link ChecksumErrorHandler#AUTOCORRECT}.
	 * 
	 * @return the active checksum error handler
	 */
	public ESChecksumErrorHandler getChecksumErrorHandler() {

		if (checksumErrorHandler == null) {

			ESExtensionPoint extensionPoint = new ESExtensionPoint(
				"org.eclipse.emf.emfstore.client.checksumErrorHandler");

			ESExtensionElement elementWithHighestPriority = extensionPoint.getElementWithHighestPriority();

			if (elementWithHighestPriority != null) {
				ESChecksumErrorHandler errorHandler = elementWithHighestPriority
					.getClass("errorHandler",
								ESChecksumErrorHandler.class);

				if (errorHandler != null) {
					checksumErrorHandler = errorHandler;
				}
			}

			if (checksumErrorHandler == null) {
				checksumErrorHandler = ChecksumErrorHandler.LOG;
			}
		}

		return checksumErrorHandler;
	}

	/**
	 * Set the active {@link ESChecksumErrorHandler}.
	 * 
	 * @param errorHandler
	 *            the error handler to be set
	 */
	public void setChecksumErrorHandler(ESChecksumErrorHandler errorHandler) {
		checksumErrorHandler = errorHandler;
	}

	/**
	 * Whether debug mode is enabled.
	 * 
	 * @return true, if debug mode is enabled, false otherwise
	 */
	public static boolean isDebugMode() {
		String startArgument = ServerConfiguration.getStartArgument(DEBUG_SWITCH);

		if (startArgument != null && startArgument.equals(DEBUG_SWITCH_ENABLED_VALUE)) {
			return true;
		}

		return false;
	}

	/**
	 * Retrieve the editing domain.
	 * 
	 * @return the workspace editing domain
	 */
	public EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			ESWorkspaceProviderImpl.getInstance();
		}
		return editingDomain;
	}

	/**
	 * Sets the EditingDomain.
	 * 
	 * @param editingDomain
	 *            new domain.
	 */
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * Get the default server info.
	 * 
	 * @return server info
	 */
	public List<ServerInfo> getDefaultServerInfos() {
		ESClientConfigurationProvider provider = new ESExtensionPoint(
			"org.eclipse.emf.emfstore.client.defaultConfigurationProvider")
			.getClass("providerClass",
						ESClientConfigurationProvider.class);
		ArrayList<ServerInfo> result = new ArrayList<ServerInfo>();
		if (provider != null) {
			List<ESServer> defaultServerInfos = provider.getDefaultServerInfos();

			for (ESServer server : defaultServerInfos) {
				result.add(((ESServerImpl) server).getInternalAPIImpl());
			}

			return result;
		}
		result.add(getLocalhostServerInfo());
		return result;
	}

	private ServerInfo getLocalhostServerInfo() {
		ServerInfo serverInfo = ModelFactory.eINSTANCE.createServerInfo();
		serverInfo.setName("Localhost Server");
		serverInfo.setPort(8080);
		serverInfo.setUrl("localhost");
		serverInfo.setCertificateAlias(KeyStoreManager.DEFAULT_CERTIFICATE);

		Usersession superUsersession = ModelFactory.eINSTANCE.createUsersession();
		superUsersession.setServerInfo(serverInfo);
		superUsersession.setPassword("super");
		superUsersession.setSavePassword(true);
		superUsersession.setUsername("super");
		serverInfo.setLastUsersession(superUsersession);

		return serverInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESCheckoutObserver#checkoutDone(org.eclipse.emf.emfstore.client.ESLocalProject)
	 */
	public void checkoutDone(ESLocalProject project) {
		flushCommandStack();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESShareObserver#shareDone(org.eclipse.emf.emfstore.client.ESLocalProject)
	 */
	public void shareDone(ESLocalProject localProject) {
		flushCommandStack();
	}

	public void flushCommandStack() {
		editingDomain.getCommandStack().flush();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESUpdateObserver#inspectChanges(org.eclipse.emf.emfstore.client.ESLocalProject,
	 *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean inspectChanges(ESLocalProject project, List<ESChangePackage> changePackages, IProgressMonitor monitor) {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESUpdateObserver#updateCompleted(org.eclipse.emf.emfstore.client.ESLocalProject,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateCompleted(ESLocalProject project, IProgressMonitor monitor) {
		flushCommandStack();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESCommitObserver#inspectChanges(org.eclipse.emf.emfstore.client.ESLocalProject,
	 *      org.eclipse.emf.emfstore.server.model.ESChangePackage, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean inspectChanges(ESLocalProject project, ESChangePackage changePackage, IProgressMonitor monitor) {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.observer.ESCommitObserver#commitCompleted(org.eclipse.emf.emfstore.client.ESLocalProject,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void commitCompleted(ESLocalProject project, ESPrimaryVersionSpec newRevision, IProgressMonitor monitor) {
		flushCommandStack();
	}
}
