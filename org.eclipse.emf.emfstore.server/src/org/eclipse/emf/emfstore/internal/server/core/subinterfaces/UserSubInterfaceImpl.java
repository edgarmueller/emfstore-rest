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
package org.eclipse.emf.emfstore.internal.server.core.subinterfaces;

import org.eclipse.emf.emfstore.internal.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod.MethodId;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;

/**
 * This subinterfaces implements all user related functionality for the
 * {@link org.eclipse.emf.emfstore.internal.serverxxx.core.EMFStoreImpl} interface.
 * 
 * @author wesendon
 */
public class UserSubInterfaceImpl extends AbstractSubEmfstoreInterface {

	/**
	 * Default constructor.
	 * 
	 * @param parentInterface parent interface
	 * @throws FatalEmfStoreException in case of failure
	 */
	public UserSubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalEmfStoreException {
		super(parentInterface);
	}

	/**
	 * {@inheritDoc}
	 */
	@EmfStoreMethod(MethodId.RESOLVEUSER)
	public ACUser resolveUser(SessionId sessionId, ACOrgUnitId id) throws EMFStoreException {
		sanityCheckObjects(sessionId);
		synchronized (getMonitor()) {
			ACUser requestingUser = getAuthorizationControl().resolveUser(sessionId);
			if (id == null) {
				return requestingUser;
			}
			ACUser user = getAuthorizationControl().resolveUser(id);
			if (requestingUser.getId().equals(user.getId())) {
				return user;
			}
			getAuthorizationControl().checkServerAdminAccess(sessionId);
			return user;
		}
	}

}