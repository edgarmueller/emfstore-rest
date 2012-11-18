/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.provider;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Sort alphabetically the repository browser viewer.
 * 
 * @author shterev
 */
public class ESBrowserViewerSorter extends ViewerSorter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(Viewer viewer, Object o1, Object o2) {

		if (o1 instanceof ServerInfo && o2 instanceof ServerInfo) {
			return ((ServerInfo) o1).getName().toLowerCase().compareTo(((ServerInfo) o2).getName().toLowerCase());
		} else if (o1 instanceof ProjectInfo && o2 instanceof ProjectInfo) {
			return ((ProjectInfo) o1).getName().toLowerCase().compareTo(((ProjectInfo) o2).getName().toLowerCase());
		}

		return super.compare(viewer, o1, o2);
	}
}