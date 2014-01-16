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

/**
 * Convenience class for handling {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.Role Role}s.
 * 
 * @author
 */
public final class Roles {

	private Roles() {

	}

	/**
	 * Returns the {@link EClass} of a
	 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.WriterRole WriterRole}.
	 * 
	 * @return the WriterRole EClass
	 */
	public static EClass writer() {
		return RolesPackage.eINSTANCE.getWriterRole();
	}

	/**
	 * Returns the {@link EClass} of a
	 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ReaderRole ReaderRole}.
	 * 
	 * @return the ReaderRole EClass
	 */
	public static EClass reader() {
		return RolesPackage.eINSTANCE.getReaderRole();
	}

	/**
	 * Returns the {@link EClass} of a
	 * {@link org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.ProjectAdminRole ProjectAdminRole}.
	 * 
	 * @return the ProjectAdminRole EClass
	 */
	public static EClass projectAdmin() {
		return RolesPackage.eINSTANCE.getProjectAdminRole();
	}

}
