/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 ******************************************************************************/
package org.eclipse.emf.emfstore.server;

import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.AbstractAuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Factory interface for creating authentication controller.
 * 
 * @author wesendon
 */
public interface AuthenticationControlFactory {

	/**
	 * Creates an authentication controller.
	 * 
	 * @return the created authentication controller
	 * @throws FatalESException in case of failure
	 */
	AbstractAuthenticationControl createAuthenticationControl() throws FatalESException;

}