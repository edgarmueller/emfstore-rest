/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.scm;

import java.util.List;

/**
 * Marker class to be used in the {@link SCMContentProvider} and {@link SCMLabelProvider} that can hold arbitrary
 * content.
 * 
 * @param <T> the actual type of content stored by the node
 * 
 * @author emueller
 */
public class VirtualNode<T> {

	private final List<T> content;

	/**
	 * 
	 * @param content
	 *            the content stored by this node
	 */
	public VirtualNode(List<T> content) {
		this.content = content;
	}

	/**
	 * Returns the content stored by this node.
	 * 
	 * @return the content stored by node
	 */
	public List<T> getContent() {
		return content;
	}
}
