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

import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;

/**
 * Mapping between {@link ESChangePackage} and {@link ChangePackage}.
 * 
 * @author emueller
 * 
 */
public class ESChangePackageImpl extends AbstractAPIImpl<ESChangePackageImpl, ChangePackage> implements ESChangePackage {

	/**
	 * Constructor.
	 * 
	 * @param changePackage
	 *            the delegate
	 */
	public ESChangePackageImpl(ChangePackage changePackage) {
		super(changePackage);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESChangePackage#getLogMessage()
	 */
	public ESLogMessage getLogMessage() {
		return getInternalAPIImpl().getLogMessage().getAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESChangePackage#setLogMessage(org.eclipse.emf.emfstore.server.model.ESLogMessage)
	 */
	public void setLogMessage(ESLogMessage logMessage) {
		ESLogMessageImpl logMessageImpl = (ESLogMessageImpl) logMessage;
		getInternalAPIImpl().setLogMessage(logMessageImpl.getInternalAPIImpl());
	}

}
