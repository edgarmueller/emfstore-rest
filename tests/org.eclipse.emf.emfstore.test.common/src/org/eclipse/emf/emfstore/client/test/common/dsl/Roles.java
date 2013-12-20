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
package org.eclipse.emf.emfstore.client.test.common.dsl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;

public class Roles {

	public static final EClass writer = RolesPackage.eINSTANCE.getWriterRole();
	public static final EClass reader = RolesPackage.eINSTANCE.getReaderRole();
}
