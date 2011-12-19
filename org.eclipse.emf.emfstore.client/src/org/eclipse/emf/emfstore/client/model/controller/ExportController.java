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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.export.ChangesExportController;
import org.eclipse.emf.emfstore.client.model.controller.export.IExportController;
import org.eclipse.emf.emfstore.client.model.controller.export.ProjectExportController;
import org.eclipse.emf.emfstore.client.model.controller.export.ProjectSpaceExportController;
import org.eclipse.emf.emfstore.client.model.controller.export.WorkspaceExportController;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;

public class ExportController {

	private IExportController export;
	private File file;
	private final IProgressMonitor monitor;
	private IOException exportError;

	public ExportController(IExportController export, File file, IProgressMonitor monitor) {
		this.export = export;
		this.file = file;
		this.monitor = monitor;
	}

	public void export() throws IOException {

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				monitor.beginTask("Export " + export.getLabel() + "...", 100);
				// TODO: let export controllers set worked state
				monitor.worked(10);
				try {
					export.export(file, monitor);
				} catch (IOException e) {
					exportError = e;
				}
				monitor.worked(30);
				monitor.worked(60);
				monitor.done();
			}
		}.run(false);

		if (exportError != null) {
			throw exportError;
		}
	}

	public static ProjectExportController getProjectExportController(ProjectSpace projectSpace) {
		return new ProjectExportController(projectSpace);
	}

	public static ProjectSpaceExportController getProjectSpaceExportController(ProjectSpace projectSpace) {
		return new ProjectSpaceExportController(projectSpace);
	}

	public static ChangesExportController getChangesExportController(ProjectSpace projectSpace) {
		return new ChangesExportController(projectSpace);
	}

	public static WorkspaceExportController getWorkspaceExportController() {
		return new WorkspaceExportController();
	}
}
