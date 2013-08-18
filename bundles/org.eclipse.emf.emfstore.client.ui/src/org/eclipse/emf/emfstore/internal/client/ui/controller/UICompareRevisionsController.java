/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Julian Sommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESRemoteProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryCompare;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.swt.widgets.Shell;

/**
 * Compare two {@link HistoryInfo}s of a {@link ProjectSpace}.
 * 
 * @author jsommerfeldt
 * 
 */
public class UICompareRevisionsController extends AbstractEMFStoreUIController<Void> {

	private ESLocalProject localProject;
	private ESPrimaryVersionSpec versionSpec1;
	private ESPrimaryVersionSpec versionSpec2;

	public UICompareRevisionsController(Shell shell, ESPrimaryVersionSpec versionSpec1,
		ESPrimaryVersionSpec versionSpec2, ESLocalProject localProject) {
		super(shell);
		this.versionSpec1 = versionSpec1;
		this.versionSpec2 = versionSpec2;
		this.localProject = localProject;
	}

	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {
		ESRemoteProjectImpl remoteProject = (ESRemoteProjectImpl) localProject.getRemoteProject();
		ESLocalProjectImpl project1 = remoteProject.fetch(localProject.getProjectName(),
			localProject.getUsersession(), versionSpec1, monitor);

		ESLocalProjectImpl project2 = remoteProject.fetch(localProject.getProjectName(),
			localProject.getUsersession(), versionSpec2, monitor);

		if (HistoryCompare.hasRegisteredExtensions()) {
			HistoryCompare.handleRegisteredExtensions(project1.toInternalAPI().getProject(), project2
				.toInternalAPI().getProject());
		}

		return null;
	}
}
