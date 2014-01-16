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
package org.eclipse.emf.emfstore.client.test.common.cases;

/**
 * Factory for creating test server instances.
 * 
 * @author emueller
 * 
 */
public final class ESTestServerFactory {

	private ESTestServerFactory() {
	}

	public enum ServerType {
		Mock,
		EMFStore
	}

	public static IServer create(ServerType type) {
		if (type.equals(ServerType.Mock)) {
			return new MockServer();
		}
		return new Server();
	}

}
