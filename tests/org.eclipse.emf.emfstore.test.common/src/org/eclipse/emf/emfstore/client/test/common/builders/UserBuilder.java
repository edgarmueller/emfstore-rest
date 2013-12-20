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
package org.eclipse.emf.emfstore.client.test.common.builders;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.test.common.builders.BOOL.FALSE;
import org.eclipse.emf.emfstore.client.test.common.builders.BOOL.TRUE;

// TODO: make builder type safe
public final class UserBuilder<A, B, C> {

	private final ESServer server;
	private final String userName;
	private final String password;

	private UserBuilder(String name, String password, ESServer server) {
		userName = name;
		this.password = password;
		this.server = server;
	}

	public ESServer getServer() {
		return server;
	}

	public String getUsername() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public static UserBuilder<FALSE, FALSE, FALSE> create() {
		return new UserBuilder<FALSE, FALSE, FALSE>(null, null, null);
	}

	public <XB, XC> UserBuilder<TRUE, XB, XC> withName(String name) {
		return new UserBuilder<TRUE, XB, XC>(name, password, server);
	}

	public <XA, XC> UserBuilder<XA, TRUE, XC> withPassword(String password) {
		return new UserBuilder<XA, TRUE, XC>(userName, password, server);
	}

	// public <XA, XC, XD> UserBuilder<XA, TRUE, XC, XD> withRole(EClass role) {
	// return new UserBuilder<XA, TRUE, XC, XD>(this.userName, role, this.id, this.server);
	// }

	// public <XA, XB, XD> UserBuilder<XA, XB, TRUE, XD> forProject(ProjectId projectId) {
	// return new UserBuilder<XA, XB, TRUE, XD>(this.userName, this.role, projectId, this.server);
	// }

	public <XA, XB> UserBuilder<XA, XB, TRUE> onServer(ESServer server) {
		return new UserBuilder<XA, XB, TRUE>(this.userName, password, server);
	}
}