package org.eclipse.emf.emfstore.internal.client.model;

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