/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.client.common.IClientVersionProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.client.model.util.ConfigurationProvider;
import org.eclipse.emf.emfstore.client.model.util.DefaultWorkspaceLocationProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPointException;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.LocationProvider;
import org.eclipse.emf.emfstore.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.model.ClientVersionInfo;
import org.osgi.framework.Bundle;

/**
 * Represents the current Workspace Configuration.
 * 
 * @author koegel
 * @author wesendon
 */
public final class Configuration {

	/**
	 * The value for enabling debug mode.
	 */
	public static final String DEBUG_SWITCH_ENABLED_VALUE = "enabled";

	/**
	 * The command line option for enabling debug mode.
	 */
	public static final String DEBUG_SWITCH = "-debug";

	private static final String AUTO_SAVE_EXTENSION_POINT_ATTRIBUTE_NAME = "autoSave";

	/**
	 * Property for XML RPC connection timeout.
	 */
	private static final int XML_RPC_CONNECTION_TIMEOUT = 600000;

	/**
	 * Property for XML RPC reply timeout.
	 */
	private static final int XML_RPC_REPLY_TIMEOUT = 600000;

	private static final String CLIENT_NAME = "emfstore eclipse client";
	private static final String MODEL_VERSION_FILENAME = "modelReleaseNumber";
	private static final String UPS = ".ups";
	private static final String UOC = ".uoc";
	private static final String PROJECT_FOLDER = "project";
	private static final String PS = "ps-";
	private static final String UPF = ".upf";
	private static final String PLUGIN_BASEDIR = "pluginData";
	private static final String ERROR_DIAGNOSIS_DIR_NAME = "errorLog";

	private static Boolean autoSave;

	private static LocationProvider locationProvider;
	private static EditingDomain editingDomain;

	private static int xmlRPCConnectionTimeout = XML_RPC_CONNECTION_TIMEOUT;
	private static int xmlRPCReplyTimeout = XML_RPC_REPLY_TIMEOUT;

	private Configuration() {
		// nothing to do
	}

	/**
	 * Get the Workspace directory.
	 * 
	 * @return the workspace directory path string
	 */
	public static String getWorkspaceDirectory() {
		String workspaceDirectory = getLocationProvider().getWorkspaceDirectory();
		File workspace = new File(workspaceDirectory);
		if (!workspace.exists()) {
			workspace.mkdirs();
		}
		if (!workspaceDirectory.endsWith(File.separator)) {
			return workspaceDirectory + File.separatorChar;
		}
		return workspaceDirectory;
	}

	/**
	 * Returns the directory that is used for error logging.<br/>
	 * If the directory does not exist it will be created. Upon exit of the JVM it will be deleted.
	 * 
	 * @return the path to the error log directory
	 */
	public static String getErrorLogDirectory() {
		String workspaceDirectory = getWorkspaceDirectory();
		File errorDiagnosisDir = new File(StringUtils.join(Arrays.asList(workspaceDirectory, ERROR_DIAGNOSIS_DIR_NAME),
			File.separatorChar));

		if (!errorDiagnosisDir.exists()) {
			errorDiagnosisDir.mkdir();
			errorDiagnosisDir.deleteOnExit();
		}

		return errorDiagnosisDir.getAbsolutePath();
	}

	/**
	 * Returns the registered {@link LocationProvider} or if not existent, the {@link DefaultWorkspaceLocationProvider}.
	 * 
	 * @return workspace location provider
	 */
	public static LocationProvider getLocationProvider() {
		if (locationProvider == null) {

			try {
				// TODO EXPT PRIO
				locationProvider = new ExtensionPoint("org.eclipse.emf.emfstore.client.workspaceLocationProvider")
					.setThrowException(true).getClass("providerClass", LocationProvider.class);
			} catch (ExtensionPointException e) {
				String message = "Error while instantiating location provider or none configured, switching to default location!";
				ModelUtil.logInfo(message);
			}

			if (locationProvider == null) {
				locationProvider = new DefaultWorkspaceLocationProvider();
			}
		}

		return locationProvider;
	}

	/**
	 * Get the Workspace file path.
	 * 
	 * @return the workspace file path string
	 */
	public static String getWorkspacePath() {
		return getWorkspaceDirectory() + "workspace.ucw";
	}

	/**
	 * Returns the XML RPC connection timeout value.
	 * 
	 * @return the connection timeout value
	 */
	public static int getXMLRPCConnectionTimeout() {
		return xmlRPCConnectionTimeout;
	}

	/**
	 * Returns the XML RPC timeout value.
	 * 
	 * @return the timeout value
	 */
	public static int getXMLRPCReplyTimeout() {
		return xmlRPCReplyTimeout;
	}

	/**
	 * Get the default server info.
	 * 
	 * @return server info
	 */
	public static List<ServerInfo> getDefaultServerInfos() {
		ConfigurationProvider provider = new ExtensionPoint(
			"org.eclipse.emf.emfstore.client.defaultConfigurationProvider").getClass("providerClass",
			ConfigurationProvider.class);
		if (provider != null) {
			List<ServerInfo> defaultServerInfos = provider.getDefaultServerInfos();
			if (defaultServerInfos != null) {
				return defaultServerInfos;
			}
		}
		ArrayList<ServerInfo> result = new ArrayList<ServerInfo>();
		result.add(getLocalhostServerInfo());
		return result;
	}

	private static ServerInfo getLocalhostServerInfo() {
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
	 * Returns maximum number of model elements allowed per resource.
	 * 
	 * @return the maximum number
	 */
	public static int getMaxMECountPerResource() {
		return 1000;
	}

	/**
	 * Returns maximum size of of a resource file on expand.
	 * 
	 * @return the maximum number
	 */
	public static int getMaxResourceFileSizeOnExpand() {
		return 100000;
	}

	/**
	 * Get the client version as in the org.eclipse.emf.emfstore.client manifest
	 * file.
	 * 
	 * @return the client version number
	 */
	@SuppressWarnings("cast")
	public static ClientVersionInfo getClientVersion() {
		ClientVersionInfo clientVersionInfo = org.eclipse.emf.emfstore.server.model.ModelFactory.eINSTANCE
			.createClientVersionInfo();
		clientVersionInfo.setName(CLIENT_NAME);

		String versionId;
		ExtensionElement version = new ExtensionPoint("org.eclipse.emf.emfstore.client.version").setThrowException(
			false).getFirst();

		if (version != null) {
			IClientVersionProvider versionProvider = version.getClass("class", IClientVersionProvider.class);
			return versionProvider.getVersion();
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
	public static boolean isReleaseVersion() {
		return !isInternalReleaseVersion() && !getClientVersion().getVersion().endsWith("qualifier");
	}

	/**
	 * Determines if this is an internal release or not.
	 * 
	 * @return true if it is an internal release
	 */
	public static boolean isInternalReleaseVersion() {
		return getClientVersion().getVersion().endsWith("internal");
	}

	/**
	 * Determines if this is an developer version or not.
	 * 
	 * @return true if it is a developer version
	 */
	public static boolean isDeveloperVersion() {
		return !isReleaseVersion() && !isInternalReleaseVersion();
	}

	/**
	 * Return the file extension for project space files.
	 * 
	 * @return the file extension
	 */
	public static String getProjectSpaceFileExtension() {
		return UPS;
	}

	/**
	 * Return the file extension for operation composite files.
	 * 
	 * @return the file extension
	 */
	public static String getLocalChangePackageFileExtension() {
		return UOC;
	}

	/**
	 * Return the name of the project folder.
	 * 
	 * @return the folder name
	 */
	public static String getProjectFolderName() {
		return PROJECT_FOLDER;
	}

	/**
	 * Return the prefix of the project space directory.
	 * 
	 * @return the prefix
	 */
	public static String getProjectSpaceDirectoryPrefix() {
		return PS;
	}

	/**
	 * Return project fragement file extension.
	 * 
	 * @return the file extension
	 */
	public static String getProjectFragmentFileExtension() {
		return UPF;
	}

	/**
	 * Return the name of the model release number file. This file identifies
	 * the release number of the model in the workspace.
	 * 
	 * @return the file name
	 */
	public static String getModelReleaseNumberFileName() {
		return getWorkspaceDirectory() + MODEL_VERSION_FILENAME;
	}

	/**
	 * Return the path of the plugin data directory inside the emfstore
	 * workspace (trailing file separator included).
	 * 
	 * @return the plugin data directory absolute path as string
	 */
	public static String getPluginDataBaseDirectory() {
		return getWorkspaceDirectory() + PLUGIN_BASEDIR + File.separatorChar;
	}

	/**
	 * Retrieve the editing domain.
	 * 
	 * @return the workspace editing domain
	 */
	public static EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			WorkspaceManager.getInstance();
		}
		return Configuration.editingDomain;
	}

	/**
	 * Sets the EditingDomain.
	 * 
	 * @param editingDomain
	 *            new domain.
	 */
	public static void setEditingDomain(EditingDomain editingDomain) {
		Configuration.editingDomain = editingDomain;
	}

	/**
	 * Whether to enable the automatic saving of the workspace.
	 * If disabled, performance improves vastly, but clients have to
	 * perform the saving of the workspace manually.
	 * 
	 * @param enabled whether to enable auto save
	 */
	public static void setAutoSave(boolean enabled) {
		Configuration.autoSave = new Boolean(enabled);
	}

	/**
	 * Whether auto-save is enabled.
	 * 
	 * @return true, if auto-save is enabled, false otherwise
	 */
	public static boolean isAutoSaveEnabled() {
		if (autoSave == null) {
			autoSave = new ExtensionPoint("org.eclipse.emf.emfstore.client.recording.options").getBoolean(
				AUTO_SAVE_EXTENSION_POINT_ATTRIBUTE_NAME, true);
		}
		return autoSave;
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
}