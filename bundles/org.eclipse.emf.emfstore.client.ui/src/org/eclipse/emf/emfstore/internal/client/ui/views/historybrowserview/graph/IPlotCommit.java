/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Aumann,Faltermeier
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.graph;

import java.util.List;

import org.eclipse.swt.graphics.Color;

/**
 * This interface represents a commit within a commit graph. It is used for drawing in the SWTPlotRenderer.
 * 
 * @author Aumann, Faltermeier
 */
public interface IPlotCommit {

	/**
	 * @return The lane of the commit in the graph.
	 */
	PlotLane getLane();

	/**
	 * Sets the lane of the commit.
	 * 
	 * @param lane The lane to set as lane for this commit.
	 */
	void setLane(PlotLane lane);

	/**
	 * Adds a passing lane to this commit.
	 * 
	 * @param lane The lane to add.
	 */
	void addPassingLane(PlotLane lane);

	/**
	 * @return All passing lanes of this commit.
	 */
	PlotLane[] getPassingLanes();

	/**
	 * Sets the parent's of this commit. Parents are commits this commit is based upon, i.e. normally parent commits
	 * came before this commit.
	 * 
	 * @param parents A list of this commit's parents.
	 */
	void setParents(List<IPlotCommit> parents);

	/**
	 * @return The number of parents of this commit.
	 */
	int getParentCount();

	/**
	 * @param parentId The index of the parent to retrieve.
	 * @return The parent of this commit at the specified index.
	 */
	IPlotCommit getParent(int parentId);

	/**
	 * Adds a child to this commit. A child of a commit is a commit that is based on this commit (but may be based on
	 * others, too). See also {@link #setParents(List)}
	 * 
	 * @param child The child to add.
	 */
	void addChild(IPlotCommit child);

	/**
	 * @return The number of children of this commit.
	 */
	int getChildCount();

	/**
	 * @param childId The index of the child to retrieve.
	 * @return The child of this commit at the specified index.
	 */
	IPlotCommit getChild(int childId);

	/**
	 * Tests whether a given commit is child of this commit.
	 * 
	 * @param commit The potential child commit.
	 * @return true if the given commit is a child of this commit, false otherwise.
	 */
	boolean isChild(IPlotCommit commit);

	/**
	 * Checks whether this commit is a real (i.e. committed to the server) commit, or just a wrapper around local change
	 * sets.
	 * 
	 * @return true if this commit is only a wrapper for local change sets, false otherwise.
	 */
	boolean isLocalHistoryOnly();

	/**
	 * Sets the 'real' status of this commit. This commit is 'real' if e.g. in a tree this commit is on the very level
	 * where its node will be painted. Unreal commits just hold the necessary information (lanes etc.) but do not have
	 * to be completely drawn (the lanes might be in a lighter color, no commit dot etc.)
	 * 
	 * 
	 * @param isReal The new status.
	 */
	void setIsRealCommit(boolean isReal);

	/**
	 * @return The 'real' status of this commit. See {@link #setIsRealCommit(boolean)}.
	 */
	boolean isRealCommit();

	/**
	 * @return The name of the branch this commit belongs to.
	 */
	String getBranch();

	/**
	 * @return The unique identifier of this commit, usually a number.
	 */
	int getId();

	/**
	 * Disposes this commit and all aquired resources.
	 */
	void dispose();

	/**
	 * Sets the color of this commit. The color determines the color of the branch label next to the commit as well as
	 * associated lane colors.
	 * 
	 * @param color The new color.
	 */
	void setColor(Color color);

	/**
	 * @return The color of this commit. See {@link #setColor(Color)}.
	 */
	Color getColor();

/**
	 * Sets the light color of this commit. It is usually a brighter version of this commit's color (see {@link #setColor(Color)). The light color is used whenever a more faded color representation for this commit is needed (e.g. label backgrounds, lane colors for expanded commit nodes).
	 * 
	 * @param color The new light color of this commit.
	 */
	void setLightColor(Color color);

	/**
	 * @return The light color of this commit. See {@link #setLightColor(Color)}.
	 */
	Color getLightColor();
}
