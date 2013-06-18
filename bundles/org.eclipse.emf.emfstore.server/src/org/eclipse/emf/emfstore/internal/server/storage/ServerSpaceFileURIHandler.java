/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.storage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.FileURIHandlerImpl;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;

/**
 * Handler for serverspace file URIs. Adds functionality for also deleting project folder.
 * 
 * @author jfaltermeier
 * 
 */
public class ServerSpaceFileURIHandler extends FileURIHandlerImpl {

	@Override
	public boolean canHandle(URI uri) {
		String extension = "." + uri.fileExtension();
		if (extension.equals(ServerConfiguration.FILE_EXTENSION_CHANGEPACKAGE) ||
			extension.equals(ServerConfiguration.FILE_EXTENSION_PROJECTHISTORY) ||
			extension.equals(ServerConfiguration.FILE_EXTENSION_PROJECTSTATE) ||
			extension.equals(ServerConfiguration.FILE_EXTENSION_VERSION)) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(URI uri, Map<?, ?> options) throws IOException
	{
		// TODO check options
		File file = new File(uri.toFileString());
		File parent = file.getParentFile();
		file.delete();

		if (parent != null && parent.exists() && parent.listFiles().length == 0) {
			FileUtils.deleteDirectory(parent);
		}
	}
}
