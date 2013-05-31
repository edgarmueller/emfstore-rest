/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.extensionpoint;

import java.util.Comparator;


/**
 * A comparator for {@link ESExtensionElement}. This allows to sort the elements in the {@link ESExtensionPoint} in order to
 * represent priority of registed elements.
 * 
 * This comparator by default uses a field priority, which is expected to hold an priority number and then sorty by this
 * number.
 * 
 * @author wesendon
 * 
 */
public class ESPriorityComparator implements Comparator<ESExtensionElement> {

	private final String fieldname;
	private final boolean desc;

	/**
	 * Default constructor.
	 */
	public ESPriorityComparator() {
		this("priority", false);
	}

	/**
	 * Constructor which allows to config the ordering.
	 * 
	 * @param descending if true, priorities are sorted in descending order, ascending otherwise
	 */
	public ESPriorityComparator(final boolean descending) {
		this("priority", descending);
	}

	/**
	 * Constructor allows to config fieldname and ordering.
	 * 
	 * @param fieldname the attribute id of the priority field
	 * @param descending if true, priorities are sorted in descending order, ascending otherwise
	 */
	public ESPriorityComparator(final String fieldname, final boolean descending) {
		this.fieldname = fieldname;
		this.desc = descending;

	}

	/**
	 * {@inheritDoc}
	 */
	public int compare(ESExtensionElement element1, ESExtensionElement element2) {
		try {
			element1.setThrowException(true);
			element2.setThrowException(true);
			return element1.getInteger(this.fieldname).compareTo(element2.getInteger(this.fieldname)) * ((desc) ? -1 : 1);
		} catch (ESExtensionPointException e) {
			return 0;
		}
	}

}
