/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk, Edgar Mueller - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.model.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.ESModelElementId;

/**
 * The model element query is a specialization of the {@link ESRangeQuery}, which allows to additionally
 * alter the results produced by the range query on selected elements.
 * 
 * @author emueller
 * @author ovonwesen
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESModelElementQuery extends ESRangeQuery<ESModelElementQuery> {

	/**
	 * Returns all model element IDs this query is filtering for.
	 * 
	 * @return a list of model elements IDs for which this query is filtering for
	 */
	List<ESModelElementId> getModelElementIds();

	/**
	 * Adds the ID of a model element that the query should filter for.
	 * 
	 * @param id
	 *            the ID of a model element
	 */
	void addModelElementId(ESModelElementId id);

	/**
	 * Removes the ID of a model element from this query.
	 * 
	 * @param id
	 *            the ID of the model element to be removed
	 */
	void removeModelElementId(ESModelElementId id);
}
