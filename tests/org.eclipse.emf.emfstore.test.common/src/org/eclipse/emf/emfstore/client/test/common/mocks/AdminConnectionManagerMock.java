/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.mocks;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc.XmlRpcClientManager;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthorizationControl;
import org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.XmlRpcAdminConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.core.AdminEmfStoreImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.model.ServerSpace;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.dao.ACDAOFacade;

public class AdminConnectionManagerMock extends AdminEmfStoreImpl implements AdminConnectionManager {

	private final Map<SessionId, Object> map;

	public AdminConnectionManagerMock(ACDAOFacade daoFacade, AuthorizationControl authorizationControl,
		ServerSpace serverSpace)
		throws FatalESException {
		super(daoFacade, serverSpace, authorizationControl);

		map = new LinkedHashMap<SessionId, Object>();
	}

	public void initConnection(ServerInfo serverInfo, SessionId id) throws ConnectionException {
		final XmlRpcClientManager clientManager = new XmlRpcClientManager(XmlRpcAdminConnectionHandler.ADMINEMFSTORE);
		clientManager.initConnection(serverInfo);
		map.put(id, clientManager);
	}

}
