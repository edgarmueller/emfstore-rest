package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.IProject;
import org.eclipse.emf.emfstore.client.IRemoteProject;
import org.eclipse.emf.emfstore.client.IServer;
import org.eclipse.emf.emfstore.client.IUsersession;
import org.eclipse.emf.emfstore.server.model.IGlobalProjectId;
import org.eclipse.emf.emfstore.server.model.versionspec.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.IVersionSpec;
import org.eclipse.swt.widgets.Shell;

public interface UIControllerFactory {

	UIControllerFactory INSTANCE = null;// UIControllerFactoryImpl.INSTANCE;

	IPrimaryVersionSpec commitProject(Shell shell, ESLocalProject project);

	IPrimaryVersionSpec createBranch(Shell shell, IProject project);

	IPrimaryVersionSpec createBranch(Shell shell, IProject project, IBranchVersionSpec branch);

	ESLocalProject createLocalProject(Shell shell);

	ESLocalProject createLocalProject(Shell shell, String name, String description);

	IRemoteProject createRemoteProject(Shell shell);

	IRemoteProject createRemoteProject(Shell shell, IUsersession usersession);

	IRemoteProject createRemoteProject(Shell shell, IUsersession usersession, String projectName, String description);

	void deleteLocalProject(Shell shell, ESLocalProject project);

	void deleteRemoteProject(Shell shell, IServer server, IGlobalProjectId projectId, boolean deleteFiles);

	void deleteRemoteProject(Shell shell, IUsersession session, IGlobalProjectId projectId, boolean deleteFiles);

	void deleteRemoteProject(Shell shell, IRemoteProject remoteProject);

	void login(Shell shell, IServer server);

	void logout(Shell shell, IUsersession usersession);

	void mergeBranch(Shell shell, ESLocalProject project);

	void registerEPackage(Shell shell, IServer server);

	void removeServer(Shell shell, IServer server);

	void shareProject(Shell shell, ESLocalProject project);

	void showHistoryView(Shell shell, ESLocalProject project);

	void showHistoryView(Shell shell, EObject eObject);

	IPrimaryVersionSpec updateProject(Shell shell, ESLocalProject project);

	IPrimaryVersionSpec updateProject(Shell shell, ESLocalProject project, IVersionSpec version);

	IPrimaryVersionSpec updateProjectToVersion(Shell shell, ESLocalProject project);
}
