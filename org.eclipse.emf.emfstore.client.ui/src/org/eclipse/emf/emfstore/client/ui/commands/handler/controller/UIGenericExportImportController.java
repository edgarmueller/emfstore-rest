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
package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecp.common.util.DialogHandler;
import org.eclipse.emf.ecp.common.util.PreferenceHelper;
import org.eclipse.emf.emfstore.client.model.controller.importexport.IExportImportController;
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ExportImportController;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Generic and UI-specific controller class that is meant to be sub-classed by
 * actual implementations of both, import and export classes.
 * 
 * @author emueller
 * 
 */
public class UIGenericExportImportController extends AbstractEMFStoreUIController {

	private final IExportImportController controller;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param controller
	 *            the {@link IExportImportController} to be executed
	 */
	public UIGenericExportImportController(Shell shell, IExportImportController controller) {
		super(shell);
		this.controller = controller;
	}

	/**
	 * Executes the controller.
	 */
	public void execute() {

		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			controller.isExport() ? SWT.SAVE : SWT.OPEN);
		dialog.setFilterNames(controller.getFilteredNames());
		dialog.setFilterExtensions(controller.getFilteredExtensions());
		dialog.setOverwrite(true);

		if (controller.getParentFolderPropertyKey() != null) {
			String initialPath = PreferenceHelper.getPreference(controller.getParentFolderPropertyKey(),
				System.getProperty("user.home"));
			dialog.setFilterPath(initialPath);
		}

		dialog.setFileName(controller.getFilename());
		String fn = dialog.open();
		if (fn == null) {
			return;
		}

		final File file = new File(dialog.getFilterPath(), dialog.getFileName());
		PreferenceHelper.setPreference(controller.getParentFolderPropertyKey(), file.getParent());

		final ProgressMonitorDialog progress = openProgress();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					progress.open();
					progress.getProgressMonitor().beginTask("Import " + controller.getLabel() + " ...", 100);
					progress.getProgressMonitor().worked(10);
					new ExportImportController(file, getProgressMonitor()).execute(controller);
				} catch (IOException e) {
					DialogHandler.showExceptionDialog(e);
				} finally {
					progress.getProgressMonitor().done();
					progress.close();
				}

			}
		}.run();

		// TODO: include confirmation dialog messages in interface
		// MessageDialog.openInformation(null, "Import", "Imported changes from file " + file.getAbsolutePath());
	}
}
