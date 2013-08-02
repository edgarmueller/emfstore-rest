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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.ESDynamicModelProvider;

/**
 * The default Dynamic Model Provider. Resolves dynamic models from file-system.
 * 
 * @author jfaltermeier
 * 
 */
public class FileDynamicModelProvider implements ESDynamicModelProvider {

	public List<EPackage> getDynamicModels() {
		File dir = new File(ServerConfiguration.getServerHome() + "dynamic-models");
		File[] files = null;

		files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File d, String name) {
				return name.endsWith(".ecore");
			}
		});

		List<EPackage> result = null;

		if (files != null) {
			result = new ArrayList<EPackage>(files.length);

			for (File file : files) {
				ResourceSet resourceSet = new ResourceSetImpl();
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put("ecore", new EcoreResourceFactoryImpl());
				Resource resource = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
				EPackage model = (EPackage) resource.getContents().get(0);
				result.add(model);
			}
		} else {
			result = new ArrayList<EPackage>();
		}

		return result;
	}

}
