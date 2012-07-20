package org.eclipse.emf.emfstore.client.test.server.api.util;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.server.accesscontrol.AuthorizationControl;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.SessionId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.server.model.accesscontrol.AccesscontrolFactory;

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
}
