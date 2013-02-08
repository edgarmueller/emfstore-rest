/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.common;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;

/**
 * A filter interface that is used to separate specific types and mark them
 * as 'filtered' in the UI. Filtered types are considered as non-critical for
 * an understanding of the problem domain and therefore are treated special in the UI,
 * e.g. by grouping all operations involving only filtered type as is the case in the
 * update, commit and merge details dialog.
 * 
 * @author emueller
 */
public interface IEClassFilter {

	/**
	 * The {@link EClass}es that are considered as filtered.
	 * 
	 * @return the filtered {@link EClass}es
	 */
	Set<EClass> getFilteredEClasses();

	/**
	 * A label that groups all filtered EClasses.
	 * 
	 * @return the label used for grouping the filtered types
	 */
	String getLabel();
}
