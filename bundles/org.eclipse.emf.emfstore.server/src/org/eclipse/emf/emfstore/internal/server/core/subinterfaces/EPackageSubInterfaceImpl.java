/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tobias Verhoeven
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.core.subinterfaces;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.DanglingHREFException;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.emfstore.common.ESResourceSetProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESPriorityComparator;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.MonitorProvider;
import org.eclipse.emf.emfstore.internal.server.core.helper.EPackageHelper;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod.MethodId;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.ServerURIUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Subinterface for EPackage registration.
 * 
 * @author mkoegel
 * @author tverhoeven
 */
public class EPackageSubInterfaceImpl extends AbstractSubEmfstoreInterface {

	private static final String E_PACKAGE_REGISTRATION = "EPackage_Registration";

	/**
	 * Constructor.
	 * 
	 * @param parentInterface the parent interface
	 * @throws FatalESException if init fails
	 */
	public EPackageSubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalESException {
		super(parentInterface);
	}

	/**
	 * Register and store the given EPackage.
	 * 
	 * @param ePackage
	 *            the package to be registered
	 * @throws ESException if registration storage fails
	 */
	@EmfStoreMethod(MethodId.REGISTEREPACKAGE)
	public void registerEPackage(EPackage ePackage) throws ESException {
		synchronized (MonitorProvider.getInstance().getMonitor(E_PACKAGE_REGISTRATION)) {
			List<EPackage> packages = EPackageHelper.getAllSubPackages(ePackage);
			Set<EPackage> rmPackages = new LinkedHashSet<EPackage>();
			packages.add(ePackage);

			// check for subpackages that are already registered
			for (EPackage subPkg : packages) {
				if (EPackage.Registry.INSTANCE.getEPackage(subPkg.getNsURI()) != null) {
					rmPackages.add(subPkg);
				}
			}
			packages.removeAll(rmPackages);

			// remove subpackages that are already registered from
			// input-EPackage, the diff-package is registered and saved.
			EPackageHelper.removeSubPackages(ePackage, rmPackages);

			if (packages.isEmpty()) {
				throw new ESException(
					"Registration failed: Package(s) with supplied NsUris(s) is/are already registred!");

			}

			URI dynamicModelUri = ServerURIUtil.createDynamicModelsURI(ePackage);

			// create a resource to save the file to disc
			ESExtensionPoint extensionPoint = new ESExtensionPoint(
				"org.eclipse.emf.emfstore.server.resourceSetProvider",
				true);
			extensionPoint.setComparator(new ESPriorityComparator("priority", true));
			extensionPoint.reload();

			ESResourceSetProvider resourceSetProvider = extensionPoint.getElementWithHighestPriority().getClass(
				"class",
				ESResourceSetProvider.class);

			ResourceSet resourceSet = resourceSetProvider.getResourceSet();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("ecore", new EcoreResourceFactoryImpl());
			Resource resource = resourceSet.createResource(dynamicModelUri);
			resource.getContents().add(ePackage);
			try {
				ModelUtil.saveResource(resource, ModelUtil.getResourceLogger());
			} catch (IOException e) {
				// DanglingHREFException may be ignored, as the referenced
				// elements were either stored earlier or can still be stored later
				if (!(e.getCause() instanceof DanglingHREFException)) {
					throw new ESException("Registration failed: Could not persist .ecore!", e);
				}
			}
			// Finally register EPackages in global EPackage-registry.
			for (EPackage registerPackage : packages) {
				EPackage.Registry.INSTANCE.put(registerPackage.getNsURI(), registerPackage);
			}
			ModelUtil.logInfo("EPackage \"" + ePackage.getNsURI() + "\" registered and saved.");
		}
	}
}