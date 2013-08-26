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
 * Edgar Mueller - refactorings and singleton access
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.factory;

import java.util.Properties;

import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AuthenticationControlType;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.AbstractAuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.EMFModelAuthenticationVerifier;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.LDAPVerifier;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.SimplePropertyFileVerifier;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.VerifierChain;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidPropertyException;

/**
 * Default authentication control factory.
 * 
 * @author wesendon
 */
public final class AuthenticationControlFactoryImpl implements AuthenticationControlFactory {

	private static AuthenticationControlFactory instance = new AuthenticationControlFactoryImpl();

	private AuthenticationControlFactoryImpl() {
		// private ctor
	}

	/**
	 * The singleton instance.
	 * 
	 * @return the singleton instance
	 */
	public static AuthenticationControlFactory getInstance() {
		return instance;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.factory.AuthenticationControlFactory#createAuthenticationControl(org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AuthenticationControlType)
	 */
	public AbstractAuthenticationControl createAuthenticationControl(AuthenticationControlType authenticationControlType)
		throws FatalESException {

		if (authenticationControlType.equals(AuthenticationControlType.ldap)) {
			final VerifierChain chain = new VerifierChain();
			final Properties properties = ServerConfiguration.getProperties();
			int count = 1;
			while (count != -1) {

				final String ldapUrl = properties.getProperty(ServerConfiguration.AUTHENTICATION_LDAP_PREFIX + "."
					+ count
					+ "." + ServerConfiguration.AUTHENTICATION_LDAP_URL);
				final String ldapBase = properties.getProperty(ServerConfiguration.AUTHENTICATION_LDAP_PREFIX + "."
					+ count
					+ "." + ServerConfiguration.AUTHENTICATION_LDAP_BASE);
				final String searchDn = properties.getProperty(ServerConfiguration.AUTHENTICATION_LDAP_PREFIX + "."
					+ count
					+ "." + ServerConfiguration.AUTHENTICATION_LDAP_SEARCHDN);
				final String authUser = properties.getProperty(ServerConfiguration.AUTHENTICATION_LDAP_PREFIX + "."
					+ count
					+ "." + ServerConfiguration.AUTHENTICATION_LDAP_AUTHUSER);
				final String authPassword = properties.getProperty(ServerConfiguration.AUTHENTICATION_LDAP_PREFIX + "."
					+ count + "." + ServerConfiguration.AUTHENTICATION_LDAP_AUTHPASS);

				if (ldapUrl != null && ldapBase != null && searchDn != null) {
					final LDAPVerifier ldapVerifier = new LDAPVerifier(ldapUrl, ldapBase, searchDn, authUser,
						authPassword);
					chain.getVerifiers().add(ldapVerifier);
					count++;
				} else {
					count = -1;
				}
			}

			return chain;

		} else if (authenticationControlType.equals(AuthenticationControlType.spfv)) {

			return new SimplePropertyFileVerifier(ServerConfiguration.getProperties().getProperty(
				ServerConfiguration.AUTHENTICATION_SPFV_FILEPATH, ServerConfiguration.getDefaultSPFVFilePath()));

		} else if (authenticationControlType.equals(AuthenticationControlType.model)) {
			return new EMFModelAuthenticationVerifier();

		} else {
			throw new InvalidPropertyException();
		}
	}

}
