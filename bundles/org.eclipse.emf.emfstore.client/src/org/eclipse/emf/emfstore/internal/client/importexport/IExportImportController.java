/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * emueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.importexport;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Generic interface that is used for both export and import.
 * 
 * @author emueller
 * 
 */
public interface IExportImportController {

	/**
	 * The label that should be shown while exporting/importing.
	 * 
	 * @return a label that best describes the export/import process
	 */
	String getLabel();

	/**
	 * Returns an array of names that should be filtered in the export/import dialog.
	 * 
	 * @return an array of filtered names
	 */
	String[] getFilteredNames();

	/**
	 * Returns an array of extensions that should be filtered in the export/import dialog.
	 * 
	 * @return an array of file extensions
	 */
	String[] getFilteredExtensions();

	/**
	 * Returns the key that is used to cache the last location of the export/import performed.
	 * 
	 * @return a property key identifier
	 */
	String getParentFolderPropertyKey();

	/**
	 * Executes the controller.
	 * 
	 * @param file
	 *            the {@link File} that is either exported to or imported
	 * @param progressMonitor
	 *            an {@link IProgressMonitor} that is used to inform about the progress
	 *            of the export/import process
	 * @throws IOException
	 *             in case an error occurs during export/import
	 */
	void execute(File file, IProgressMonitor progressMonitor) throws IOException;

	/**
	 * Returns the file name that is used for export/import.
	 * 
	 * @return the file name
	 */
	String getFilename();

	/**
	 * Whether this controller is an export controller.
	 * 
	 * @return true, if this controller exports an entity of type <code>T</code>,
	 *         otherwise this controller imports an entity of type <code>T</code>
	 */
	boolean isExport();
}
