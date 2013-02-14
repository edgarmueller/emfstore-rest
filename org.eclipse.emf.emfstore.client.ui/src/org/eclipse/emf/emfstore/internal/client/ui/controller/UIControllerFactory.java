package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.server.model.ESGlobalProjectId;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.swt.widgets.Shell;

public interface UIControllerFactory {

	UIControllerFactory INSTANCE = null;// UIControllerFactoryImpl.INSTANCE;

	ESPrimaryVersionSpec commitProject(Shell shell, ESLocalProject project);

	ESPrimaryVersionSpec createBranch(Shell shell, ESProject project);

	ESPrimaryVersionSpec createBranch(Shell shell, ESProject project, ESBranchVersionSpec branch);

	ESLocalProject createLocalProject(Shell shell);

	ESLocalProject createLocalProject(Shell shell, String name, String description);

	ESRemoteProject createRemoteProject(Shell shell);

	ESRemoteProject createRemoteProject(Shell shell, ESUsersession usersession);

	ESRemoteProject createRemoteProject(Shell shell, ESUsersession usersession, String projectName, String description);

	void deleteLocalProject(Shell shell, ESLocalProject project);

	void deleteRemoteProject(Shell shell, ESServer server, ESGlobalProjectId projectId, boolean deleteFiles);

	void deleteRemoteProject(Shell shell, ESUsersession session, ESGlobalProjectId projectId, boolean deleteFiles);

	void deleteRemoteProject(Shell shell, ESRemoteProject remoteProject);

	void login(Shell shell, ESServer server);

	void logout(Shell shell, ESUsersession usersession);

	void mergeBranch(Shell shell, ESLocalProject project);

	void registerEPackage(Shell shell, ESServer server);

	void removeServer(Shell shell, ESServer server);

	void shareProject(Shell shell, ESLocalProject project);

	void showHistoryView(Shell shell, ESLocalProject project);

	void showHistoryView(Shell shell, EObject eObject);

	ESPrimaryVersionSpec updateProject(Shell shell, ESLocalProject project);

	ESPrimaryVersionSpec updateProject(Shell shell, ESLocalProject project, ESVersionSpec version);

	ESPrimaryVersionSpec updateProjectToVersion(Shell shell, ESLocalProject project);
}
