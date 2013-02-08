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
package org.eclipse.emf.emfstore.internal.server.core.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;

/**
 * The EPackageHelper provides additional methods for handling EPackages.
 * 
 * @author Tobias Verhoeven
 */
// TODO: internal
public final class EPackageHelper {

	private EPackageHelper() {
	}

	/**
	 * Gets the all sub packages.
	 * 
	 * @param ePackage the e package
	 * @return the all sub packages
	 */
	public static List<EPackage> getAllSubPackages(EPackage ePackage) {
		List<EPackage> resultList = new ArrayList<EPackage>();
		extractAllSubPackages(ePackage, resultList);
		return resultList;
	}

	private static void extractAllSubPackages(EPackage pkg, List<EPackage> packages) {
		packages.addAll(pkg.getESubpackages());
		for (EPackage subPkg : pkg.getESubpackages()) {
			extractAllSubPackages(subPkg, packages);
		}
	}

	/**
	 * Removes subpackages from an EPackage .
	 * 
	 * @param ePackage the EPackage
	 * @param subPackagesRm the subpackages to be removed.
	 */
	public static void removeSubPackages(EPackage ePackage, Set<EPackage> subPackagesRm) {
		if (subPackagesRm == null || subPackagesRm.isEmpty()) {
			return;
		}
		ePackage.getESubpackages().removeAll(subPackagesRm);
		for (EPackage pkg : ePackage.getESubpackages()) {
			removeSubPackages(pkg, subPackagesRm);
		}

	}
}