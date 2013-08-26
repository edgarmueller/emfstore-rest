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
 * Edgar Mueller - refactorings
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.factory;

import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AuthenticationControlType;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.AbstractAuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Factory interface for creating authentication controller.
 * 
 * @author wesendon
 * @author emueller
 */
public interface AuthenticationControlFactory {

	/**
	 * The singleton instance.
	 */
	AuthenticationControlFactory INSTANCE = AuthenticationControlFactoryImpl.getInstance();

	/**
	 * Creates an authentication controller.
	 * 
	 * @param authenticationControlType
	 *            the type of authentication control being requested
	 * 
	 * @return the created authentication controller
	 * 
	 * @throws FatalESException in case of failure
	 */
	AbstractAuthenticationControl createAuthenticationControl(AuthenticationControlType authenticationControlType)
		throws FatalESException;

}