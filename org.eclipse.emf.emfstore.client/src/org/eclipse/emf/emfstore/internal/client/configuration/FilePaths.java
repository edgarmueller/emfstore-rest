package org.eclipse.emf.emfstore.internal.client.configuration;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPointException;
import org.eclipse.emf.emfstore.internal.client.model.util.DefaultWorkspaceLocationProvider;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.ESLocationProvider;

public class FilePaths {

	private static ESLocationProvider locationProvider;
	private static final String PLUGIN_BASEDIR = "pluginData";
	private static final String ERROR_DIAGNOSIS_DIR_NAME = "errorLog";
	private static final String MODEL_VERSION_FILENAME = "modelReleaseNumber";

	public final String ProjectSpaceFileExtension = ".ups";
	public final String LocalChangePackageExtension = ".uoc";
	public final String ProjectFragmentExtension = ".upf";
	public final String ProjectSpaceDirectoryPrefix = "ps-";
	public final String ProjectFolderName = "project";

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
	 * Return the path of the plugin data directory inside the emfstore
	 * workspace (trailing file separator included).
	 * 
	 * @return the plugin data directory absolute path as string
	 */
	public String getPluginDataBaseDirectory() {
		return getWorkspaceDirectory() + PLUGIN_BASEDIR + File.separatorChar;
	}

	/**
	 * Get the Workspace file path.
	 * 
	 * @return the workspace file path string
	 */
	public String getWorkspacePath() {
		return getWorkspaceDirectory() + "workspace.ucw";
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
	 * Return the name of the model release number file. This file identifies
	 * the release number of the model in the workspace.
	 * 
	 * @return the file name
	 */
	public String getModelReleaseNumberFileName() {
		return getWorkspaceDirectory() + MODEL_VERSION_FILENAME;
	}

	/**
	 * Return the file extension for project space files.
	 * 
	 * @return the file extension
	 */
	public String getProjectSpaceFileExtension() {
		return ProjectSpaceFileExtension;
	}

	/**
	 * Return the file extension for operation composite files.
	 * 
	 * @return the file extension
	 */
	public String getLocalChangePackageFileExtension() {
		return LocalChangePackageExtension;
	}

	/**
	 * Return the prefix of the project space directory.
	 * 
	 * @return the prefix
	 */
	public String getProjectSpaceDirectoryPrefix() {
		return ProjectSpaceDirectoryPrefix;
	}

	/**
	 * Return project fragement file extension.
	 * 
	 * @return the file extension
	 */
	public String getProjectFragmentFileExtension() {
		return ProjectFragmentExtension;
	}
}
