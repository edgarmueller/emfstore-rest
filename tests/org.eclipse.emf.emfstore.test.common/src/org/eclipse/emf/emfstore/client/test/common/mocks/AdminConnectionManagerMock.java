package org.eclipse.emf.emfstore.client.test.common.mocks;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc.XmlRpcClientManager;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl;
import org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.XmlRpcAdminConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.core.AdminEmfStoreImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.dao.ACDAOFacade;

public class AdminConnectionManagerMock extends AdminEmfStoreImpl implements AdminConnectionManager {

	private Map<SessionId, Object> map; 

	public AdminConnectionManagerMock(ACDAOFacade daoFacade, AuthorizationControl authorizationControl) throws FatalESException {
		super(daoFacade, new ServerSpace() {
			
			public void remove(ProjectHistory history) {
				// TODO Auto-generated method stub
				
			}
			
			public void add(ProjectHistory history) {
				// TODO Auto-generated method stub
				
			}
			
			public void remove(ACGroup group) {
				// TODO Auto-generated method stub
				
			}
			
			public void add(ACGroup group) {
				// TODO Auto-generated method stub
				
			}
			
			public void remove(ACUser group) {
				// TODO Auto-generated method stub
				
			}
			
			public void add(ACUser user) {
				// TODO Auto-generated method stub
				
			}
			
			public void eSetDeliver(boolean deliver) {
				// TODO Auto-generated method stub
				
			}
			
			public void eNotify(Notification notification) {
				// TODO Auto-generated method stub
				
			}
			
			public boolean eDeliver() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public EList<Adapter> eAdapters() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void eUnset(EStructuralFeature feature) {
				// TODO Auto-generated method stub
				
			}
			
			public void eSet(EStructuralFeature feature, Object newValue) {
				// TODO Auto-generated method stub
				
			}
			
			public Resource eResource() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean eIsSet(EStructuralFeature feature) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean eIsProxy() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object eGet(EStructuralFeature feature, boolean resolve) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object eGet(EStructuralFeature feature) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EList<EObject> eCrossReferences() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EList<EObject> eContents() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EReference eContainmentFeature() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EStructuralFeature eContainingFeature() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EObject eContainer() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EClass eClass() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public TreeIterator<EObject> eAllContents() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void setResource(Resource resource) {
				// TODO Auto-generated method stub
				
			}
			
			public void save() throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			public EList<ACUser> getUsers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EList<ProjectHistory> getProjects() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EList<SessionId> getOpenSessions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EList<ACGroup> getGroups() {
				// TODO Auto-generated method stub
				return null;
			}
		}, authorizationControl);
		
		map = new LinkedHashMap<SessionId, Object>();
	}

	public void initConnection(ServerInfo serverInfo, SessionId id) throws ConnectionException {
		final XmlRpcClientManager clientManager = new XmlRpcClientManager(XmlRpcAdminConnectionHandler.ADMINEMFSTORE);
		clientManager.initConnection(serverInfo);
		map.put(id, clientManager);
	}

}
