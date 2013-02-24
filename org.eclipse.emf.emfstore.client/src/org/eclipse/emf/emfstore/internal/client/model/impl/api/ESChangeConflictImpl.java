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
package org.eclipse.emf.emfstore.internal.client.model.impl.api;

import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.internal.client.model.controller.ChangeConflict;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator;

/**
 * <p>
 * Mapping between {@link ESChangeConflict} and {@link ChangeConflict}.
 * </p>
 * <p>
 * Note that this class does not inherit from {@link AbstractAPIImpl} since {@link ChangeConflict} is not a modeled
 * class.
 * </p>
 * 
 * @author emueller
 * 
 */
public class ESChangeConflictImpl implements ESChangeConflict, InternalAPIDelegator<ESChangeConflict, ChangeConflict> {

	private ChangeConflict changeConflict;

	/**
	 * Constructor.
	 * 
	 * @param changeConflict
	 *            the delegate
	 */
	public ESChangeConflictImpl(ChangeConflict changeConflict) {
		this.changeConflict = changeConflict;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator#getInternalAPIImpl()
	 */
	public ChangeConflict getInternalAPIImpl() {
		return changeConflict;
	}

}
