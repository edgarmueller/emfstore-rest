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
package org.eclipse.emf.emfstore.internal.server.impl.api;

import java.util.Set;

import org.eclipse.emf.emfstore.internal.common.APIUtil;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.server.ESConflict;
import org.eclipse.emf.emfstore.server.ESConflictSet;

/**
 * <p>
 * Mapping between {@link ESConflictSet} and {@link ChangeConflictSet}.
 * </p>
 * <p>
 * Note that this class does not inherit from {@link AbstractAPIImpl} since {@link ChangeConflictSet} is not a modeled
 * class.
 * </p>
 * 
 * @author emueller
 * 
 */
public class ESConflictSetImpl implements ESConflictSet, InternalAPIDelegator<ESConflictSet, ChangeConflictSet> {

	private ChangeConflictSet changeConflict;

	/**
	 * Constructor.
	 * 
	 * @param changeConflict
	 *            the delegate
	 */
	public ESConflictSetImpl(ChangeConflictSet changeConflict) {
		this.changeConflict = changeConflict;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator#toInternalAPI()
	 */
	public ChangeConflictSet toInternalAPI() {
		return changeConflict;
	}

	public Set<ESConflict> getConflicts() {
		Set<ESConflict> conflicts = APIUtil
			.toExternal(toInternalAPI().getConflictBuckets());
		return conflicts;

	}
}
