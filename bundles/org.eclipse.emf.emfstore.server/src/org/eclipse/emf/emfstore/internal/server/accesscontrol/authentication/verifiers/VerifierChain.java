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
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;

/**
 * Calls all available verifiers and tries to verify the given credentials.
 * 
 * @author wesendon
 */
public class VerifierChain extends AbstractAuthenticationControl {

	private final ArrayList<AbstractAuthenticationControl> verifiers;

	/**
	 * Constructs an empty verifier chain.
	 */
	public VerifierChain() {
		super();
		verifiers = new ArrayList<AbstractAuthenticationControl>();
	}

	/**
	 * Returns the list of verifier. can be used to add and remove verifier.
	 * 
	 * @return list of verifier
	 */
	public List<AbstractAuthenticationControl> getVerifiers() {
		return verifiers;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.AbstractAuthenticationControl#verifyPassword(org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean verifyPassword(ACUser resolvedUser, String username, String password)
		throws AccessControlException {
		for (final AbstractAuthenticationControl verifier : verifiers) {
			if (verifier.verifyPassword(resolvedUser, username, password)) {
				return true;
			}
		}
		return false;
	}

}
