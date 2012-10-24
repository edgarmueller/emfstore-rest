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
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

/**
 * This interface represents classes that are able to create graphically representable plot commits for
 * {@link HistoryInfo} objects.
 * 
 * @author Aumann, Faltermeier
 * 
 */
public interface IPlotCommitProvider {

	/**
	 * Gets a plot commit for the provided history info.
	 * 
	 * @param info The history info for which the plot commit shall be retrieved.
	 * @param isReal Determines the 'real' status of the returned commit. See
	 *            {@link IPlotCommit#setIsRealCommit(boolean)}
	 * @return The requested plot commit.
	 */
	IPlotCommit getCommitFor(HistoryInfo info, boolean isReal);

	/**
	 * Sets a new list of {@link HistoryInfo} objects for the same input. This keeps e.g. branch colors etc.
	 * 
	 * @param newInfos The update {@link HistoryInfo} list.
	 */
	void refresh(List<HistoryInfo> newInfos);

}