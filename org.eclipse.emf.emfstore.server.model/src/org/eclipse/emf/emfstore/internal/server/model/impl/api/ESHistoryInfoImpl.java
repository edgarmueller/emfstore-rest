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
package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.internal.common.APIUtil;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;

/**
 * Mapping between {@link ESHistoryInfo} and {@link HistoryInfo}.
 * 
 * @author emueller
 * 
 */
public class ESHistoryInfoImpl extends AbstractAPIImpl<ESHistoryInfoImpl, HistoryInfo> implements ESHistoryInfo {

	/**
	 * Constructor.
	 * 
	 * @param historyInfo
	 *            the delegate
	 */
	public ESHistoryInfoImpl(HistoryInfo historyInfo) {
		super(historyInfo);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getPrimarySpec()
	 */
	public ESPrimaryVersionSpec getPrimarySpec() {
		return getInternalAPIImpl().getPrimarySpec().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getNextSpecs()
	 */
	public List<ESPrimaryVersionSpec> getNextSpecs() {
		EList<PrimaryVersionSpec> nextSpec = getInternalAPIImpl().getNextSpec();
		return APIUtil.mapToAPI(ESPrimaryVersionSpec.class, nextSpec);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getPreviousSpec()
	 */
	public ESPrimaryVersionSpec getPreviousSpec() {
		return getInternalAPIImpl().getPreviousSpec().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getMergedFromSpecs()
	 */
	public List<ESPrimaryVersionSpec> getMergedFromSpecs() {
		return APIUtil.mapToAPI(ESPrimaryVersionSpec.class, getInternalAPIImpl().getMergedFrom());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getMergedToSpecs()
	 */
	public List<ESPrimaryVersionSpec> getMergedToSpecs() {
		return APIUtil.mapToAPI(ESPrimaryVersionSpec.class, getInternalAPIImpl().getMergedTo());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getLogMessage()
	 */
	public ESLogMessage getLogMessage() {
		return getInternalAPIImpl().getLogMessage().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESHistoryInfo#getTagSpecs()
	 */
	public List<ESTagVersionSpec> getTagSpecs() {
		return APIUtil.mapToAPI(ESTagVersionSpec.class, getInternalAPIImpl().getTagSpecs());
	}

	public ESChangePackage getChangePackage() {

		if (getInternalAPIImpl().getChangePackage() == null) {
			return null;
		}

		return getInternalAPIImpl().getChangePackage().getAPIImpl();
	}

}
