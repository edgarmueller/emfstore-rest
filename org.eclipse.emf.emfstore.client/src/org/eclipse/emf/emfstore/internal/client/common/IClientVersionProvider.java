/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.common;

import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;

/**
 * Determines the version identifier of the client component.
 * 
 * @author emueller
 */
public interface IClientVersionProvider {

	/**
	 * Returns the version of the client.
	 * 
	 * @return the client version
	 */
	ClientVersionInfo getVersion();
}