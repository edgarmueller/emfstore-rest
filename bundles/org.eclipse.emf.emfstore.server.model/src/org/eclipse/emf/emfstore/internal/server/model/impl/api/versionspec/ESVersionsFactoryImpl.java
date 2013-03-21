/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec;

import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.versionspec.ESAncestorVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESHeadVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;

/**
 * Implementation for the {@link ESVersionFactory} that takes care of the mapping and delegates
 * the actual work to {@link Versions}.
 * 
 * @author emueller
 * 
 */
public class ESVersionsFactoryImpl implements ESVersionFactory {

	public static final ESVersionsFactoryImpl INSTANCE = new ESVersionsFactoryImpl();

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createPRIMARY(java.lang.String, int)
	 */
	public ESPrimaryVersionSpec createPRIMARY(String branch, int index) {
		return Versions.createPRIMARY(branch, index).toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createPRIMARY(org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec,
	 *      int)
	 */
	public ESPrimaryVersionSpec createPRIMARY(ESVersionSpec versionSpec, int index) {
		if (versionSpec instanceof ESVersionSpecImpl<?, ?>) {
			ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = ((ESVersionSpecImpl<?, ?>) versionSpec);
			PrimaryVersionSpec primaryVersionSpec = Versions.createPRIMARY(versionSpecImpl.toInternalAPI(), index);
			return primaryVersionSpec.toAPI();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createPRIMARY(int)
	 */
	public ESPrimaryVersionSpec createPRIMARY(int i) {
		return Versions.createPRIMARY(i).toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createANCESTOR(org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec)
	 */
	public ESAncestorVersionSpec createANCESTOR(ESPrimaryVersionSpec source, ESPrimaryVersionSpec target) {
		return Versions.createANCESTOR(((ESPrimaryVersionSpecImpl) source).toInternalAPI(),
			((ESPrimaryVersionSpecImpl) target).toInternalAPI()).toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createHEAD()
	 */
	public ESHeadVersionSpec createHEAD() {
		return Versions.createHEAD().toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createHEAD(java.lang.String)
	 */
	public ESHeadVersionSpec createHEAD(String branch) {
		return Versions.createHEAD(branch).toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createHEAD(org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec)
	 */
	public ESHeadVersionSpec createHEAD(ESVersionSpec versionSpec) {
		if (versionSpec instanceof ESVersionSpecImpl<?, ?>) {
			ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = ((ESVersionSpecImpl<?, ?>) versionSpec);
			HeadVersionSpec headVersionSpec = Versions.createHEAD(versionSpecImpl.toInternalAPI());
			return headVersionSpec.toAPI();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createBRANCH(java.lang.String)
	 */
	public ESBranchVersionSpec createBRANCH(String value) {
		return Versions.createBRANCH(value).toAPI();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createBRANCH(org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec)
	 */
	public ESBranchVersionSpec createBRANCH(ESVersionSpec versionSpec) {
		if (versionSpec instanceof ESVersionSpecImpl<?, ?>) {
			ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = ((ESVersionSpecImpl<?, ?>) versionSpec);
			BranchVersionSpec branchVersionSpec = Versions.createBRANCH(versionSpecImpl.toInternalAPI());
			return branchVersionSpec.toAPI();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#isSameBranch(org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec)
	 */
	public boolean isSameBranch(ESVersionSpec versionSpec, ESVersionSpec otherVersionSpec) {

		if (versionSpec instanceof ESVersionSpecImpl<?, ?> && otherVersionSpec instanceof ESVersionSpecImpl<?, ?>) {
			ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = ((ESVersionSpecImpl<?, ?>) versionSpec);
			ESVersionSpecImpl<?, ? extends VersionSpec> otherVersionSpecImpl = ((ESVersionSpecImpl<?, ?>) otherVersionSpec);
			return Versions.isSameBranch(
				versionSpecImpl.toInternalAPI(),
				otherVersionSpecImpl.toInternalAPI());
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.versionspec.ESVersionFactory#createTAG(java.lang.String,
	 *      java.lang.String)
	 */
	public ESTagVersionSpec createTAG(String tag, String branch) {
		return Versions.createTAG(tag, branch).toAPI();
	}
}
