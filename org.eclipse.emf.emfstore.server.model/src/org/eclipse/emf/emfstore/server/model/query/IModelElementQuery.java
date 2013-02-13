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
package org.eclipse.emf.emfstore.server.model.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.IModelElementId;

/**
 * The ModelElementQuery is a
 * specialization of the range query, which allows to additionally lter the results produced
 * by the range query on selected elements. All queries can include the respective change
 * operations and can be used globally or branch speci c.
 * 
 * @author Edgar
 * 
 */
public interface IModelElementQuery extends IRangeQuery {

	List<IModelElementId> getModelElementIds();

	void addModelElementId(IModelElementId id);

	void removeModelElementId(IModelElementId id);
}
