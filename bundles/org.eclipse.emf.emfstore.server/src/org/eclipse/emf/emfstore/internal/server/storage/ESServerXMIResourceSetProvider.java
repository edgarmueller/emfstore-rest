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
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.emfstore.common.extensionpoint.ESServerResourceSetProvider;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.core.helper.EPackageHelper;

/**
 * This is the default resource set provider of EMFStore server which will be used if no extension is offered.
 * 
 * @author jfaltermeier
 * 
 */
public class ESServerXMIResourceSetProvider implements ESServerResourceSetProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.extensionpoint.ESResourceSetProvider#getResourceSet()
	 */
	public ResourceSet getResourceSet() {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		resourceSet.setURIConverter(new DefaultESServerXMIURIConverter());
		resourceSet.setURIResourceMap(new LinkedHashMap<URI, Resource>());
		resourceSet.getLoadOptions().putAll(ModelUtil.getResourceLoadOptions());
		return resourceSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.extensionpoint.ESServerResourceSetProvider#registerDynamicModels()
	 */
	public void registerDynamicModels() {
		File dir = new File(ServerConfiguration.getServerHome() + "dynamic-models");
		File[] files = null;

		files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File d, String name) {
				return name.endsWith(".ecore");
			}
		});
		if (files != null) {
			for (File file : files) {
				ResourceSet resourceSet = new ResourceSetImpl();
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put("ecore", new EcoreResourceFactoryImpl());
				Resource resource = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
				EPackage model = (EPackage) resource.getContents().get(0);
				EPackage.Registry.INSTANCE.put(model.getNsURI(), model);
				List<EPackage> packages = EPackageHelper.getAllSubPackages(model);
				for (EPackage subPkg : packages) {
					EPackage.Registry.INSTANCE.put(subPkg.getNsURI(), subPkg);
				}
				ModelUtil.logInfo("Dynamic Model \"" + model.getNsURI() + "\" loaded.");
			}
		}
	}

}
