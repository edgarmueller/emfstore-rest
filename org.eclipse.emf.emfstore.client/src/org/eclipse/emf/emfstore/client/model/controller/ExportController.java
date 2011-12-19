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
package org.eclipse.emf.emfstore.client.model.controller;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.export.ChangeExport;
import org.eclipse.emf.emfstore.client.model.controller.export.IExport;
import org.eclipse.emf.emfstore.client.model.controller.export.ProjectExport;
import org.eclipse.emf.emfstore.client.model.controller.export.ProjectSpaceExport;
import org.eclipse.emf.emfstore.client.model.controller.export.WorkspaceExport;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class ExportController extends ServerCall<Void> {

	private IExport export;
	private File file;

	public ExportController(IExport export, File file) {
		this.export = export;
		this.file = file;
	}

	@Override
	protected Void run() throws EmfStoreException {
		getProgressMonitor().beginTask("Export " + export.getLabel() + "...", 100);
		// TODO: let export controllers set worked state
		getProgressMonitor().worked(10);
		try {
			export.export(file, getProgressMonitor());
		} catch (IOException e) {
			throw new EmfStoreException("An error occured while exporting.", e);
		}
		getProgressMonitor().worked(30);
		getProgressMonitor().worked(60);
		getProgressMonitor().done();
		return null;
	}

	public static ProjectExport getProjectExportController(ProjectSpace projectSpace) {
		return new ProjectExport(projectSpace);
	}

	public static ProjectSpaceExport getProjectSpaceExportController(ProjectSpace projectSpace) {
		return new ProjectSpaceExport(projectSpace);
	}

	public static ChangeExport getChangesExportController(ProjectSpace projectSpace) {
		return new ChangeExport(projectSpace);
	}

	public static WorkspaceExport getWorkspaceExportController() {
		return new WorkspaceExport();
	}
}
