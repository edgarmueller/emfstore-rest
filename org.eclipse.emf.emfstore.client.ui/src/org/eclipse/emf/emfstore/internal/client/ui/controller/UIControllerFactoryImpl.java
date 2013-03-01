package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ui.ESUIControllerFactory;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.swt.widgets.Shell;

public final class UIControllerFactoryImpl implements ESUIControllerFactory {

	public static UIControllerFactoryImpl INSTANCE = new UIControllerFactoryImpl();

	private UIControllerFactoryImpl() {
	}

	public ESPrimaryVersionSpec commitProject(Shell shell,
			ESLocalProject project) {
		return new UICommitProjectController(shell, project).execute();
	}

	public ESPrimaryVersionSpec createBranch(Shell shell, ESProject project) {
		// TODO Auto-generated method stub
		return null;
	}

	public ESPrimaryVersionSpec createBranch(Shell shell, ESProject project,
			ESBranchVersionSpec branch) {
		// TODO Auto-generated method stub
		return null;
	}

	public ESLocalProject createLocalProject(Shell shell) {
		return new UICreateLocalProjectController(shell).execute();
	}

	public ESLocalProject createLocalProject(Shell shell, String name) {
		return new UICreateLocalProjectController(shell, name).execute();
	}

	public ESRemoteProject createRemoteProject(Shell shell) {
		return new UICreateRemoteProjectController(shell).execute();
	}

	public ESRemoteProject createRemoteProject(Shell shell,
			ESUsersession usersession) {
		return new UICreateRemoteProjectController(shell, usersession)
				.execute();
	}

	public ESRemoteProject createRemoteProject(Shell shell,
			ESUsersession usersession, String projectName) {
		return new UICreateRemoteProjectController(shell, usersession,
				projectName).execute();
	}

	public void deleteLocalProject(Shell shell, ESLocalProject project) {
		new UIDeleteProjectController(shell, project).execute();
	}

	public void deleteRemoteProject(Shell shell, ESRemoteProject remoteProject,
			ESUsersession usersession) {
		new UIDeleteRemoteProjectController(shell, usersession, remoteProject)
				.execute();
	}

	public void login(Shell shell, ESServer server) {
		new UILoginSessionController(shell, server).execute();
	}

	public void logout(Shell shell, ESUsersession usersession) {
		new UILogoutSessionController(shell, usersession).execute();
	}

	public void mergeBranch(Shell shell, ESLocalProject project) {
		new UIMergeController(shell, project).execute();
	}

	public void registerEPackage(Shell shell, ESServer server) {
		new UIRegisterEPackageController(shell, server).execute();
	}

	public void removeServer(Shell shell, ESServer server) {
		new UIRemoveServerController(shell, server).execute();
	}

	public void shareProject(Shell shell, ESLocalProject project) {
		new UIShareProjectController(shell, project).execute();
	}

	public void showHistoryView(Shell shell, ESLocalProject project) {
		new UIShowHistoryController(shell, project).execute();
	}

	public void showHistoryView(Shell shell, EObject eObject) {
		new UIShowHistoryController(shell, eObject).execute();
	}

	public ESPrimaryVersionSpec updateProject(Shell shell,
			ESLocalProject project) {
		return new UIUpdateProjectController(shell, project).execute();
	}

	public ESPrimaryVersionSpec updateProject(Shell shell,
			ESLocalProject project, ESVersionSpec version) {
		return new UIUpdateProjectController(shell, project, version).execute();
	}

	public ESPrimaryVersionSpec updateProjectToVersion(Shell shell,
			ESLocalProject project) {
		return new UIUpdateProjectToVersionController(shell, project).execute();
	}

}
