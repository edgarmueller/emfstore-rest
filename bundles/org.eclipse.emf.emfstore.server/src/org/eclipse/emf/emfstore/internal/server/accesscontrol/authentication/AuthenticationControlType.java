/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication;

/**
 * Enum for all available
 * {@link org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AuthenticationControlType}s.
 * 
 * @author emueller
 * 
 */
public enum AuthenticationControlType {

	/**
	 * A verifier that uses a simple property file for authentication.
	 */
	PropertyFile {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "spfv";
		}
	},
	/**
	 * A verifier that uses LDAP for authentication.
	 */
	LDAP {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "ldap";
		}
	},
	/**
	 * A verifier that uses the password attribute of an
	 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser} for authentication.
	 */
	Model {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "model";
		}
	}
}
