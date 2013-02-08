package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.api.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.api.IProject;
import org.eclipse.emf.emfstore.internal.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.internal.client.api.IServer;
import org.eclipse.emf.emfstore.internal.client.api.IUsersession;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IVersionSpec;
import org.eclipse.swt.widgets.Shell;

public interface UIControllerFactory {

	UIControllerFactory INSTANCE = null;// UIControllerFactoryImpl.INSTANCE;

	IPrimaryVersionSpec commitProject(Shell shell, ILocalProject project);

	IPrimaryVersionSpec createBranch(Shell shell, IProject project);

	IPrimaryVersionSpec createBranch(Shell shell, IProject project, IBranchVersionSpec branch);

	ILocalProject createLocalProject(Shell shell);

	ILocalProject createLocalProject(Shell shell, String name, String description);

	IRemoteProject createRemoteProject(Shell shell);

	IRemoteProject createRemoteProject(Shell shell, IUsersession usersession);

	IRemoteProject createRemoteProject(Shell shell, IUsersession usersession, String projectName, String description);

	void deleteLocalProject(Shell shell, ILocalProject project);

	void deleteRemoteProject(Shell shell, IServer server, IProjectId projectId, boolean deleteFiles);

	void deleteRemoteProject(Shell shell, IUsersession session, IProjectId projectId, boolean deleteFiles);

	void deleteRemoteProject(Shell shell, IRemoteProject remoteProject);

	void login(Shell shell, IServer server);

	void logout(Shell shell, IUsersession usersession);

	void mergeBranch(Shell shell, ILocalProject project);

	void registerEPackage(Shell shell, IServer server);

	void removeServer(Shell shell, IServer server);

	void shareProject(Shell shell, ILocalProject project);

	void showHistoryView(Shell shell, ILocalProject project);

	void showHistoryView(Shell shell, EObject eObject);

	IPrimaryVersionSpec updateProject(Shell shell, ILocalProject project);

	IPrimaryVersionSpec updateProject(Shell shell, ILocalProject project, IVersionSpec version);

	IPrimaryVersionSpec updateProjectToVersion(Shell shell, ILocalProject project);
}
