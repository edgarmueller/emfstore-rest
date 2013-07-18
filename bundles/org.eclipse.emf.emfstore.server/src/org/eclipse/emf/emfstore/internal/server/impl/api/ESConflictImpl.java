/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.impl.api;

import java.util.Set;

import org.eclipse.emf.emfstore.internal.common.APIUtil;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.ESConflict;
import org.eclipse.emf.emfstore.server.model.ESOperation;

public class ESConflictImpl extends AbstractAPIImpl<ESConflict, ConflictBucket> implements ESConflict {

	public ESConflictImpl(ConflictBucket internalType) {
		super(internalType);
	}

	public Set<ESOperation> getLocalOperations() {
		return APIUtil.toExternal(toInternalAPI().getMyOperations());
	}

	public Set<ESOperation> getRemoteOperations() {
		return APIUtil.toExternal(toInternalAPI().getTheirOperations());
	}

	public void resolveConflict(Set<ESOperation> acceptedLocalOperations, Set<ESOperation> rejectedRemoteOperations) {
		Set<AbstractOperation> internalAcceptedLocalOperations = APIUtil.toInternal(acceptedLocalOperations);
		Set<AbstractOperation> internalRejectedRemoteOperations = APIUtil.toInternal(rejectedRemoteOperations);
		toInternalAPI().resolveConflict(internalAcceptedLocalOperations,
			internalRejectedRemoteOperations);
	}

}
