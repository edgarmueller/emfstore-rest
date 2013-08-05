/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 * Maximilian Koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.configuration;

/**
 * Contains XML RPC configuration options.
 * 
 * @author emueller
 * @author ovonwesen
 * @author mkoegel
 */
public class XMLRPC {

	/**
	 * Property for XML RPC connection timeout.
	 */
	private static final int XML_RPC_CONNECTION_TIMEOUT = 600000;

	/**
	 * Property for XML RPC reply timeout.
	 */
	private static final int XML_RPC_REPLY_TIMEOUT = 600000;

	private static int xmlRPCConnectionTimeout = XML_RPC_CONNECTION_TIMEOUT;
	private static int xmlRPCReplyTimeout = XML_RPC_REPLY_TIMEOUT;

	/**
	 * Returns the XML RPC connection timeout value.
	 * 
	 * @return the connection timeout value
	 */
	public int getXMLRPCConnectionTimeout() {
		return xmlRPCConnectionTimeout;
	}

	/**
	 * Returns the XML RPC timeout value.
	 * 
	 * @return the timeout value
	 */
	public int getXMLRPCReplyTimeout() {
		return xmlRPCReplyTimeout;
	}
}