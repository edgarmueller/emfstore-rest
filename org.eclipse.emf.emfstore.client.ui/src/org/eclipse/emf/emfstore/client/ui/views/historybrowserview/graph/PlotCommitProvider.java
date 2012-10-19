/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * This class manages the creation of PlotCommits from HistoryInfos. See {@link IPlotCommitProvider} for
 * interface details.
 * 
 * Major parts of graph lane logic taken from org.eclipse.jgit.revplot.PlotCommitList.
 * 
 * @author Aumann, Faltermeier
 * 
 */
public class PlotCommitProvider implements IPlotCommitProvider {

	private IPlotCommit[] commits;
	private TreeSet<Integer> freePositions;
	private HashSet<PlotLane> activeLanes;
	private int positionsAllocated;
	private Map<HistoryInfo, IPlotCommit> commitForHistory = new HashMap<HistoryInfo, IPlotCommit>();
	private Map<Integer, IPlotCommit> commitForID;
	private int nextBranchColorIndex;
	private Map<String, Integer> colorForBranch = new HashMap<String, Integer>();

	private static List<Color> createdSaturatedColors = new LinkedList<Color>();
	private static List<Color> createdLightColors = new LinkedList<Color>();
	private static Color[] saturatedColors;
	private static Color[] lightColors;

	private static final Color[] COLORS_TRUNK = new Color[] { Display.getDefault().getSystemColor(SWT.COLOR_BLACK),
		createLightColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY)) };

	static {
		// if this should ever become non-static, make sure to dispose colors in created(Light/Saturated)Colors
		setUpSaturatedColors();
		setUpLightColors();
	}

	/**
	 * Creates a new PlotCommitProvider from a list of {@linkplain HistoryInfo} objects.
	 * 
	 */
	public PlotCommitProvider() {
		reset(null);
	}

	public void reset(List<HistoryInfo> infos) {
		this.nextBranchColorIndex = 0;
		this.freePositions = new TreeSet<Integer>();
		this.activeLanes = new HashSet<PlotLane>(32);
		this.commitForHistory = new HashMap<HistoryInfo, IPlotCommit>();
		if (infos != null) {
			refresh(infos);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph.IPlotCommitProvider#refresh(java.util.List)
	 */
	public void refresh(List<HistoryInfo> newInfos) {
		this.positionsAllocated = 0;
		this.commits = new PlotCommit[newInfos.size()];
		this.freePositions.clear();
		this.activeLanes.clear();
		this.commitForHistory.clear();
		this.dummyParentForId.clear();

		for (int i = 0; i < newInfos.size(); i++) {
			commits[i] = new PlotCommit(newInfos.get(i));
			commitForHistory.put(newInfos.get(i), commits[i]);
			Color[] branchColors = getColorsForBranch(commits[i].getBranch());
			commits[i].setColor(branchColors[0]);
			commits[i].setLightColor(branchColors[1]);
		}

		setupCommitIdLookUp();

		setupParents(newInfos);

		commits = insertDummyParents(dummyParentForId.values().toArray(new IPlotCommit[0]), commits);

		for (int i = 0; i < commits.length; i++) {
			initCommit(i, commits[i]);
		}
	}

	private IPlotCommit[] insertDummyParents(IPlotCommit[] dummyParents, IPlotCommit[] realCommits) {
		IPlotCommit[] wholeArray = new IPlotCommit[dummyParents.length + realCommits.length];

		for (int i = 0; i < realCommits.length; i++) {
			wholeArray[i] = realCommits[i];
		}

		int offsetForDummyParents = realCommits.length;

		for (int i = 0; i < dummyParents.length; i++) {
			wholeArray[i + offsetForDummyParents] = dummyParents[i];
		}

		Arrays.sort(wholeArray, new Comparator<IPlotCommit>() {

			public int compare(IPlotCommit arg0, IPlotCommit arg1) {
				if (arg0.getId() != -1 && arg1.getId() != -1) {
					return arg1.getId() - arg0.getId();
				} else if (arg0.getId() == -1) {
					// keep local to the top
					return -1;
				} else {
					return 1;
				}
			}
		});

		return wholeArray;
	}

	private void setupCommitIdLookUp() {
		commitForID = new HashMap<Integer, IPlotCommit>();
		for (IPlotCommit commit : commits) {
			commitForID.put(commit.getId(), commit);
		}
	}

	private static void setUpSaturatedColors() {
		saturatedColors = new Color[8];
		saturatedColors[0] = getSysColor(SWT.COLOR_BLUE);
		saturatedColors[1] = getSysColor(SWT.COLOR_RED);
		saturatedColors[2] = getSysColor(SWT.COLOR_GREEN);
		saturatedColors[3] = getSysColor(SWT.COLOR_CYAN);
		saturatedColors[4] = getSysColor(SWT.COLOR_YELLOW);

		Color orange = new Color(Display.getDefault(), 255, 148, 0);
		Color violet = new Color(Display.getDefault(), 128, 0, 128);
		Color brown = new Color(Display.getDefault(), 148, 64, 0);
		saturatedColors[5] = orange;
		saturatedColors[6] = violet;
		saturatedColors[7] = brown;

		createdSaturatedColors.add(orange);
		createdSaturatedColors.add(violet);
		createdSaturatedColors.add(brown);
	}

	private static void setUpLightColors() {
		lightColors = new Color[saturatedColors.length];
		for (int i = 0; i < saturatedColors.length; i++) {
			lightColors[i] = createLightColor(saturatedColors[i]);
		}

	}

	private static Color getSysColor(int color) {
		return Display.getDefault().getSystemColor(color);
	}

	private static Color createLightColor(Color color) {
		float[] hsbColor = java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		hsbColor[1] = hsbColor[1] * 0.2f;
		hsbColor[2] = hsbColor[2] * 1.1f;

		if (hsbColor[2] > 1f) {
			hsbColor[2] = 1f;
		}

		int lightColorRGB = java.awt.Color.HSBtoRGB(hsbColor[0], hsbColor[1], hsbColor[2]);

		java.awt.Color lightColor = new java.awt.Color(lightColorRGB);

		Color lightColorSWT = new Color(Display.getDefault(), lightColor.getRed(), lightColor.getGreen(),
			lightColor.getBlue());
		createdLightColors.add(lightColorSWT);
		return lightColorSWT;
	}

	private Color[] getColorsForBranch(String branch) {
		String trunkIdentifier = "trunk";
		if (trunkIdentifier.equals(branch)) {
			return COLORS_TRUNK;
		}

		Integer colorIndex = colorForBranch.get(branch);
		if (colorIndex == null) {
			colorIndex = nextBranchColorIndex;
			colorForBranch.put(branch, colorIndex);
			nextBranchColorIndex = (nextBranchColorIndex + 1) % saturatedColors.length;
		}
		Color[] retColors = new Color[2];
		retColors[0] = saturatedColors[colorIndex];
		retColors[1] = lightColors[colorIndex];
		return retColors;
	}

	private void setupParents(List<HistoryInfo> historyInfos) {
		for (int i = 0; i < historyInfos.size(); i++) {
			HistoryInfo currInfo = historyInfos.get(i);

			// check if this historyinfo element is a merge
			EList<PrimaryVersionSpec> mergedFrom = currInfo.getMergedFrom();
			ArrayList<IPlotCommit> parents = new ArrayList<IPlotCommit>();
			if (mergedFrom != null && mergedFrom.size() >= 1) {
				for (PrimaryVersionSpec mergeParent : mergedFrom) {
					if (commitForID.containsKey(mergeParent.getIdentifier())) {
						parents.add(commitForID.get(mergeParent.getIdentifier()));
					} else {
						// out of page range
						parents.add(getDummyParent(mergeParent.getIdentifier(), mergeParent.getBranch()));
					}
				}
				commits[i].setParents(parents);

			}
			// we only have one parent or none
			PrimaryVersionSpec parentSpec = currInfo.getPreviousSpec();
			if (parentSpec != null) {
				if (commitForID.containsKey(parentSpec.getIdentifier())) {
					parents.add(commitForID.get(parentSpec.getIdentifier()));
				} else {
					// out of page range
					parents.add(getDummyParent(parentSpec.getIdentifier(), parentSpec.getBranch()));
				}
			}

			if (!parents.isEmpty()) {
				commits[i].setParents(parents);
			}
		}
	}

	private void initCommit(int index, IPlotCommit currCommit) {
		setupChildren(currCommit);

		final int nChildren = currCommit.getChildCount();
		if (nChildren == 0) {
			return;
		}

		if (nChildren == 1 && currCommit.getChild(0).getParentCount() < 2
			&& !currCommit.getChild(0).getBranch().equals(currCommit.getBranch())) {
			// Only one child, child has only us as their parent.
			// Stay in the same lane as the child.
			final IPlotCommit c = currCommit.getChild(0);
			if (c.getLane() == null) {
				// Hmmph. This child must be the first along this lane.
				PlotLane lane = nextFreeLane();
				lane.setSaturatedColor(c.getColor());
				lane.setLightColor(c.getLightColor());
				c.setLane(lane);
				activeLanes.add(c.getLane());
			}
			for (int r = index - 1; r >= 0; r--) {
				final IPlotCommit rObj = commits[r];
				if (rObj == c) {
					break;
				}
				rObj.addPassingLane(c.getLane());
			}

			currCommit.setLane(c.getLane());
			handleBlockedLanes(index, currCommit, nChildren);
		} else {
			// More than one child, or our child is a merge.
			// Use a different lane.

			// Process all our children. Especially important when there is more
			// than one child (e.g. a commit is processed where other branches
			// fork out). For each child the following is done
			// 1. If no lane was assigned to the child a new lane is created and
			// assigned
			// 2. The lane of the child is closed. If this frees a position,
			// this position will be added freePositions list.
			// If we have multiple children which where previously not on a lane
			// each such child will get his own new lane but all those new lanes
			// will be on the same position. We have to take care that not
			// multiple newly created (in step 1) lanes occupy that position on
			// which the
			// parent's lane will be on. Therefore we delay closing the lane
			// with the parents position until all children are processed.

			// The lane on that position the current commit will be on
			PlotLane reservedLane = null;

			for (int i = 0; i < nChildren; i++) {
				final IPlotCommit c = currCommit.getChild(i);
				// don't forget to position all of your children if they are
				// not already positioned.
				if (c.getLane() == null) {
					PlotLane lane = nextFreeLane();
					lane.setSaturatedColor(c.getColor());
					lane.setLightColor(c.getLightColor());
					c.setLane(lane);
					activeLanes.add(c.getLane());
					if (reservedLane != null) {
						closeLane(c.getLane());
					} else {
						reservedLane = c.getLane();
					}
				} else if (reservedLane == null && activeLanes.contains(c.getLane())) {
					reservedLane = c.getLane();
				} else {
					closeLane(c.getLane());
				}
			}

			// finally all children are processed. We can close the lane on that
			// position our current commit will be on.
			if (reservedLane != null) {
				closeLane(reservedLane);
			}

			PlotLane lane = nextFreeLane();
			lane.setSaturatedColor(currCommit.getColor());
			lane.setLightColor(currCommit.getLightColor());
			currCommit.setLane(lane);
			activeLanes.add(currCommit.getLane());

			handleBlockedLanes(index, currCommit, nChildren);
		}
	}

	private void setupChildren(IPlotCommit currCommit) {
		int nParents = currCommit.getParentCount();
		for (int i = 0; i < nParents; i++) {
			currCommit.getParent(i).addChild(currCommit);
		}
	}

	private PlotLane nextFreeLane() {
		final PlotLane p = new PlotLane();
		if (freePositions.isEmpty()) {
			p.setPosition(positionsAllocated++);
		} else {
			final Integer min = freePositions.first();
			p.setPosition(min.intValue());
			freePositions.remove(min);
		}
		return p;
	}

	private void handleBlockedLanes(final int index, final IPlotCommit commit, final int nChildren) {
		// take care:
		int remaining = nChildren;
		BitSet blockedPositions = new BitSet();
		for (int r = index - 1; r >= 0; r--) {
			final IPlotCommit rObj = commits[r];
			if (commit.isChild(rObj)) {
				if (--remaining == 0) {
					break;
				}
			}
			if (rObj != null) {
				PlotLane lane = rObj.getLane();
				if (lane != null) {
					blockedPositions.set(lane.getPosition());
				}
				rObj.addPassingLane(commit.getLane());
			}
		}
		// Now let's check whether we have to reposition the lane
		if (blockedPositions.get(commit.getLane().getPosition())) {
			int newPos = -1;
			for (Integer pos : freePositions) {
				if (!blockedPositions.get(pos.intValue())) {
					newPos = pos.intValue();
					break;
				}
			}
			if (newPos == -1) {
				newPos = positionsAllocated++;
			}
			freePositions.add(Integer.valueOf(commit.getLane().getPosition()));
			activeLanes.remove(commit.getLane());
			commit.getLane().setPosition(newPos);
			activeLanes.add(commit.getLane());
		}
	}

	private void closeLane(PlotLane lane) {
		if (activeLanes.remove(lane)) {
			freePositions.add(Integer.valueOf(lane.getPosition()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph.IPlotCommitProvider#getCommitFor(org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo,
	 *      boolean)
	 */
	public IPlotCommit getCommitFor(HistoryInfo info, boolean onlyAChildRequest) {
		IPlotCommit comForInfo = commitForHistory.get(info);
		comForInfo.setIsRealCommit(!onlyAChildRequest);
		return comForInfo;
	}

	private Map<Integer, IPlotCommit> dummyParentForId = new HashMap<Integer, IPlotCommit>();

	private IPlotCommit getDummyParent(int id, String parentBranch) {
		if (!dummyParentForId.containsKey(id)) {
			IPlotCommit dummyParent = new PlotCommit(id, parentBranch);
			Color[] colors = getColorsForBranch(parentBranch);
			// set light color for saturated color to hint at "parent out of page"
			dummyParent.setColor(colors[1]);
			dummyParent.setLightColor(colors[1]);
			dummyParentForId.put(id, dummyParent);

		}
		return dummyParentForId.get(id);
	}

}
