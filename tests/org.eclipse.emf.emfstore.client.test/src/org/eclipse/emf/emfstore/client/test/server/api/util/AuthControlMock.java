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
package org.eclipse.emf.emfstore.client.test.server.api.util;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl;
import org.eclipse.emf.emfstore.internal.server.core.MethodInvocation;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolFactory;

public class AuthControlMock implements AuthorizationControl {

	public void checkSession(SessionId sessionId) throws AccessControlException {
		// TODO Auto-generated method stub

	}

	public void checkProjectAdminAccess(SessionId sessionId, ProjectId projectId) throws AccessControlException {
		// TODO Auto-generated method stub

	}

	public void checkServerAdminAccess(SessionId sessionId) throws AccessControlException {
		// TODO Auto-generated method stub

	}

	public void checkReadAccess(SessionId sessionId, ProjectId projectId, Set<EObject> modelElements)
		throws AccessControlException {
		// TODO Auto-generated method stub

	}

	public void checkWriteAccess(SessionId sessionId, ProjectId projectId, Set<EObject> modelElements)
		throws AccessControlException {
		// TODO Auto-generated method stub

	}

	public ACUser resolveUser(SessionId sessionId) throws AccessControlException {
		ACUser dummy = AccesscontrolFactory.eINSTANCE.createACUser();
		dummy.setName("asdf");
		return dummy;
	}

	public ACUser resolveUser(ACOrgUnitId orgUnitId) throws AccessControlException {
		ACUser dummy = AccesscontrolFactory.eINSTANCE.createACUser();
		dummy.setName("asdf");
		return dummy;
	}

	public void checkAccess(MethodInvocation op) throws AccessControlException {
		// TODO Auto-generated method stub

	}
}