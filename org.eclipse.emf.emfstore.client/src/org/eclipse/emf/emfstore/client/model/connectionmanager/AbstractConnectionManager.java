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
package org.eclipse.emf.emfstore.client.model.connectionmanager;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.emfstore.server.exceptions.UnknownSessionException;
import org.eclipse.emf.emfstore.server.model.SessionId;

/**
 * Superclass for all connection managers which map {@link SessionId}s
 * to the relative connection simply by using a generic map.
 * 
 * @param <T> type of connection client
 * @author wesendon
 */
public abstract class AbstractConnectionManager<T> {

	private Map<SessionId, T> map;

	/**
	 * Default constructor.
	 */
	public AbstractConnectionManager() {
		map = new LinkedHashMap<SessionId, T>();
	}

	/**
	 * Adds a connection proxy.
	 * 
	 * @param id session id as key
	 * @param connectionProxy connection proxy
	 */
	protected void addConnectionProxy(SessionId id, T connectionProxy) {
		map.put(id, connectionProxy);
	}

	/**
	 * Removes connection proxy.
	 * 
	 * @param id sessionid
	 */
	protected void removeConnectionProxy(SessionId id) {
		map.remove(id);
	}

	/**
	 * Returns the connection proxy attached to the session id.
	 * 
	 * @param id
	 *            the session ID
	 * @return a connection proxy
	 * @throws UnknownSessionException
	 *             If the given session id has no connection proxy attached
	 */
	protected T getConnectionProxy(SessionId id) throws UnknownSessionException {
		T connectionProxy = map.get(id);
		if (connectionProxy == null) {
			throw new UnknownSessionException(ConnectionManager.LOGIN_FIRST);
		}
		return connectionProxy;
	}

	/**
	 * Returns the map.
	 * 
	 * @return the map
	 */
	protected Map<SessionId, T> getConnectionProxyMap() {
		return map;
	}

	/**
	 * Checks whether there is a connection proxy for the given {@link SessionId}.
	 * 
	 * @param id
	 *            a session ID
	 * @return true if there is a connection proxy available for the given ID, false otherwise
	 */
	public boolean hasConnectionProxy(SessionId id) {
		return map.get(id) != null;
	}
}