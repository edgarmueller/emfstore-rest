/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.configuration;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPointException;
import org.eclipse.emf.emfstore.internal.client.model.util.DefaultWorkspaceLocationProvider;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.ESLocationProvider;

/**
 * Contains configuration options and information about the workspace related files
 * used by the client.
 * 
 * @author emueller
 * @author ovonwesen
 * @author mkoegel
 * @author jfaltermeier
 */
public class FileInfo {

	private static ESLocationProvider locationProvider;
	private static final String PLUGIN_BASEDIR = "pluginData";
	private static final String ERROR_DIAGNOSIS_DIR_NAME = "errorLog";

	private final String projectSpaceDirectoryPrefix = "ps-";

	/**
	 * Returns the registered {@link ESLocationProvider} or if not existent, the
	 * {@link DefaultWorkspaceLocationProvider}.
	 * 
	 * @return workspace location provider
	 */
	public ESLocationProvider getLocationProvider() {
		if (locationProvider == null) {

			try {
				// TODO EXPT PRIO
				locationProvider = new ESExtensionPoint("org.eclipse.emf.emfstore.client.workspaceLocationProvider")
					.setThrowException(true).getClass("providerClass", ESLocationProvider.class);
			} catch (ESExtensionPointException e) {
				ModelUtil.logInfo(e.getMessage());
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
	 * Get the Workspace directory.
	 * 
	 * @return the workspace directory path string
	 */
	public String getWorkspaceDirectory() {
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
	 * Return the path of the plugin data directory inside the EMFStore
	 * workspace (trailing file separator included).
	 * 
	 * @return the plugin data directory absolute path as string
	 */
	public String getPluginDataBaseDirectory() {
		return getWorkspaceDirectory() + PLUGIN_BASEDIR + File.separatorChar;
	}

	/**
	 * Returns the directory that is used for error logging.<br/>
	 * If the directory does not exist it will be created. Upon exit of the JVM it will be deleted.
	 * 
	 * @return the path to the error log directory
	 */
	public String getErrorLogDirectory() {
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
	 * Return the prefix of the project space directory.
	 * 
	 * @return the prefix
	 */
	public String getProjectSpaceDirectoryPrefix() {
		return projectSpaceDirectoryPrefix;
	}
}
