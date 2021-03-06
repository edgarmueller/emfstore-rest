/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIControllerFactoryImpl;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.swt.widgets.Shell;

/**
 * UI Controller factory.
 * 
 * @author eneufeld
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESUIControllerFactory {

	ESUIControllerFactory INSTANCE = UIControllerFactoryImpl.INSTANCE;

	ESPrimaryVersionSpec commitProject(Shell shell, ESLocalProject project);

	ESPrimaryVersionSpec createBranch(Shell shell, ESProject project);

	ESPrimaryVersionSpec createBranch(Shell shell, ESProject project, ESBranchVersionSpec branch);

	ESLocalProject createLocalProject(Shell shell);

	ESLocalProject createLocalProject(Shell shell, String name);

	ESRemoteProject createRemoteProject(Shell shell, ESUsersession usersession);

	ESRemoteProject createRemoteProject(Shell shell, ESUsersession usersession, String projectName);

	void deleteLocalProject(Shell shell, ESLocalProject project);

	void deleteRemoteProject(Shell shell, ESRemoteProject remoteProject, ESUsersession usersession);

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
