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
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.provider;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Interface for providing an {@link EditingDomain}.
 * 
 * @author wesendon
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ESEditingDomainProvider {

	/**
	 * <p>
	 * Returns an {@link EditingDomain}.
	 * </p>
	 * <p>
	 * <b>NOTE</b>: When creating the {@link EditingDomain} clients have to guarantee that an implementation of an
	 * {@link org.eclipse.emf.emfstore.client.changetracking.ESCommandStack ESCommandStack} is used.
	 * </p>
	 * 
	 * @param resourceSet
	 *            the {@link ResourceSet} for which to retrieve an {@link EditingDomain}
	 * 
	 * @return the editing domain for the given resource set
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	EditingDomain getEditingDomain(ResourceSet resourceSet);
}