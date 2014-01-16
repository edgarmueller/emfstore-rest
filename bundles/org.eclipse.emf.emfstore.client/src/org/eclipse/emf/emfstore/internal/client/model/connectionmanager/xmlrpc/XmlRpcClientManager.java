/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcSun15HttpTransportFactory;
import org.eclipse.emf.emfstore.client.exceptions.ESCertificateException;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.util.EObjectTypeFactory;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.xml.sax.SAXException;

/**
 * Manager for XML RPC server calls.
 * 
 * @author wesendon
 */
public class XmlRpcClientManager {

	private final String serverInterface;
	private XmlRpcClient client;

	/**
	 * Default constructor.
	 * 
	 * @param serverInterface name of interface
	 */
	public XmlRpcClientManager(String serverInterface) {
		this.serverInterface = serverInterface;
	}

	/**
	 * Initializes the connection.
	 * 
	 * @param serverInfo server info
	 * @throws ConnectionException in case of failure
	 */
	public void initConnection(ServerInfo serverInfo) throws ConnectionException {
		try {
			final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(createURL(serverInfo));
			config.setEnabledForExceptions(true);
			config.setEnabledForExtensions(true);
			config.setConnectionTimeout(Configuration.getXMLRPC().getXMLRPCConnectionTimeout());
			config.setReplyTimeout(Configuration.getXMLRPC().getXMLRPCReplyTimeout());
			config.setContentLengthOptional(true);

			client = new XmlRpcClient();
			client.setTypeFactory(new EObjectTypeFactory(client));

			final XmlRpcSun15HttpTransportFactory factory = new XmlRpcSun15HttpTransportFactory(client);

			try {
				factory.setSSLSocketFactory(KeyStoreManager.getInstance().getSSLContext().getSocketFactory());
			} catch (final ESCertificateException e) {
				throw new ConnectionException(Messages.XmlRpcClientManager_Could_Not_Load_Certificate, e);
			}
			client.setTransportFactory(factory);
			client.setConfig(config);
		} catch (final MalformedURLException e) {
			throw new ConnectionException(Messages.XmlRpcClientManager_Malformed_URL_Or_Port, e);
		}
	}

	private URL createURL(ServerInfo serverInfo) throws MalformedURLException {
		checkUrl(serverInfo.getUrl());
		return new URL("https", serverInfo.getUrl(), serverInfo.getPort(), "xmlrpc"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void checkUrl(String url) throws MalformedURLException {
		if (url != null && !url.equals(StringUtils.EMPTY)) {
			if (!(url.contains(":") || url.contains("/"))) { //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
		}
		throw new MalformedURLException();
	}

	/**
	 * Executes a server call with return value.
	 * 
	 * @param <T> return type
	 * @param methodName method name
	 * @param returnType return type
	 * @param parameters parameters
	 * @return returned object from server
	 * @throws ESException in case of failure
	 */
	public <T> T callWithResult(String methodName, Class<T> returnType, Object... parameters) throws ESException {
		return executeCall(methodName, returnType, parameters);
	}

	/**
	 * Executes a server call with list return value.
	 * 
	 * @param <T> return type
	 * @param methodName method name
	 * @param returnType list return type
	 * @param parameters parameters
	 * @return list return type
	 * @throws ESException in case of failure
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> callWithListResult(String methodName, Class<T> returnType, Object... parameters)
		throws ESException {
		final List<T> result = new ArrayList<T>();
		final Object[] callResult = executeCall(methodName, Object[].class, parameters);
		if (callResult == null) {
			return result;
		}
		for (final Object obj : callResult) {
			result.add((T) obj);
		}
		return result;
	}

	/**
	 * Executes a server call without return value.
	 * 
	 * @param methodName method name
	 * @param parameters parameters
	 * @throws ESException in case of failure
	 */
	public void call(String methodName, Object... parameters) throws ESException {
		executeCall(methodName, null, parameters);
	}

	@SuppressWarnings("unchecked")
	private <T> T executeCall(String methodName, Class<T> returnType, Object[] params) throws ESException {
		if (client == null) {
			throw new ConnectionException(ConnectionManager.REMOTE);
		}
		try {
			return (T) client.execute(serverInterface + "." + methodName, params); //$NON-NLS-1$
		} catch (final XmlRpcException e) {
			if (e.getCause() instanceof ESException) {
				throw (ESException) e.getCause();
			} else if (e.linkedException instanceof SAXException
				&& ((SAXException) e.linkedException).getException() instanceof SerializationException) {
				final SerializationException serialE = (SerializationException) ((SAXException) e.linkedException)
					.getException();
				throw new org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException(serialE);
			} else {
				throw new ConnectionException(ConnectionManager.REMOTE + e.getMessage(), e);
			}
		}
	}
}
