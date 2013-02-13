/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.versionspec;

import java.util.Date;

/**
 * Represents a version specifier that is based on a date.
 * 
 * @author emueller
 * @author wesendon
 */
public interface IDateVersionSpec extends IVersionSpec {

	/**
	 * Returns the date this version specifier is based on.
	 * 
	 * @return the date this version specifier is based on
	 */
	Date getDate();

}
