/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Aumann
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryBuilder;

/**
 * Class handling pagination. See constructor {@link #PaginationManager(ProjectSpace, int, int)}
 * 
 * @author Aumann
 * 
 */
public class PaginationManager {

	/**
	 * The version around which history queries are created. At initialization
	 * time this is the base version. It gets change if the user clicks on 'show
	 * next x elements'
	 */
	private PrimaryVersionSpec currentCenterVersionShown;

	private int aboveCenterCount, belowCenterCount;

	private List<HistoryInfo> currentlyPresentedInfos = new ArrayList<HistoryInfo>();

	private ProjectSpace projectSpace;

	private boolean nextPage;

	private boolean prevPage;

	private boolean showAllVersions;

	private EObject modelElement;

	private final String projectBranch;

	/**
	 * Creates a new PaginationManager with given page range around the central
	 * version. The central version is initialized to be the base version.
	 * 
	 * Note that the real number of versions shown might be smaller if there are
	 * not enough versions above (e.g. base version == head version) or below
	 * the center version (e.g. only x revisions yet, but below is larger).
	 * 
	 * @param projectSpace
	 *            The project space to operate on.
	 * @param modelElement An optional modelElement to show the history for. <code>null</code> to show the history for
	 *            the project space.
	 * @param aboveCenterCount
	 *            The number of versions shown above the central version.
	 * @param belowCenterCount
	 *            The number of versions shown below the central version.
	 */
	public PaginationManager(ProjectSpace projectSpace, EObject modelElement, int aboveCenterCount, int belowCenterCount) {
		this.aboveCenterCount = aboveCenterCount;
		this.belowCenterCount = belowCenterCount;
		this.projectSpace = projectSpace;
		this.modelElement = modelElement;
		this.projectBranch = projectSpace.getBaseVersion().getBranch();
	}

	/**
	 * @return The history info objects to be displayed on the current page.
	 * @throws EMFStoreException
	 *             If an exception gets thrown contacting the server.
	 */
	public List<HistoryInfo> retrieveHistoryInfos() throws EMFStoreException {
		PrimaryVersionSpec newCenterVersion;
		int beforeCurrent = -1;
		if ((prevPage || nextPage) && currentCenterVersionShown != null && !currentlyPresentedInfos.isEmpty()) {
			for (int i = 0; i < currentlyPresentedInfos.size(); i++) {
				if (currentlyPresentedInfos.get(i).getPrimerySpec().getIdentifier() == currentCenterVersionShown
					.getIdentifier()) {
					beforeCurrent = i;
					break;
				}
			}

			assert beforeCurrent != -1 : "The currently shown center version should be contained in the currently shown history infos, why has it vanished?";

			if (prevPage) {
				// there might be more versions, so swap page if there are
				newCenterVersion = currentlyPresentedInfos.get(0).getPrimerySpec();
			} else if (nextPage) {
				newCenterVersion = currentlyPresentedInfos.get(currentlyPresentedInfos.size() - 1).getPrimerySpec();
			} else {
				assert false;
				newCenterVersion = currentCenterVersionShown;
			}
		} else {
			newCenterVersion = currentCenterVersionShown;
		}
		HistoryQuery query = getQuery(newCenterVersion, aboveCenterCount, belowCenterCount);
		List<HistoryInfo> historyInfos = (List<HistoryInfo>) (List<?>) projectSpace.getHistoryInfos(query);

		if (newCenterVersion != null && !currentCenterVersionShown.equals(newCenterVersion)) {
			setCorrectCenterVersionAndHistory(historyInfos, newCenterVersion.getIdentifier(), beforeCurrent);
		} else {
			currentlyPresentedInfos = cutInfos(historyInfos,
				findPositionOfId(currentCenterVersionShown.getIdentifier(), historyInfos));
		}
		prevPage = false;
		nextPage = false;
		return currentlyPresentedInfos;
	}

	/**
	 * Set correct center version and displayed History infos. 1) prev page:
	 * check if there are enough previous versions if not: set centerVersion
	 * further down to retrieve more lower versions 2) next page : similiar to
	 * prev page
	 * 
	 * @param historyInfos
	 */
	private void setCorrectCenterVersionAndHistory(List<HistoryInfo> newQueryHistoryInfos, int newCenterVersionId,
		int positionOfOldCenterInCurrentDisplay) {
		int idOfCurrentVersionShown = currentlyPresentedInfos.get(positionOfOldCenterInCurrentDisplay).getPrimerySpec()
			.getIdentifier();
		int olderVersions = 0, newerVersions = 0;
		int newCenterVersionPos = -1;
		int oldCenterVersionPos = -1;
		for (int i = 0; i < newQueryHistoryInfos.size(); i++) {
			int idOfI = newQueryHistoryInfos.get(i).getPrimerySpec().getIdentifier();
			if (idOfI > newCenterVersionId) {
				++newerVersions;
			} else if (idOfI < newCenterVersionId) {
				++olderVersions;
			} else if (idOfI == newCenterVersionId) {
				// assert newCenterVersionPos == -1 : "Should not be in there twice.";
				newCenterVersionPos = i;
			} else if (idOfI == idOfCurrentVersionShown) {
				assert oldCenterVersionPos == -1 : "Should not be in there twice.";
				oldCenterVersionPos = i;
			}
		}
		if (newCenterVersionPos == -1) {
			newCenterVersionPos = Math.max(0, newerVersions - 1);
		}
		PrimaryVersionSpec newCenterVersion = newQueryHistoryInfos.get(newCenterVersionPos).getPrimerySpec();

		assert prevPage ^ nextPage;

		if (prevPage && newerVersions < aboveCenterCount) {
			List<HistoryInfo> mergedInfos = mergeHistoryInfoLists(newQueryHistoryInfos, currentlyPresentedInfos);
			int oldCenterPos = findPositionOfId(currentCenterVersionShown.getIdentifier(), mergedInfos);

			// not enough versions: go further down, but never further than the old center as this is supposed to be
			// prevPage and thus at the very least not nextPage
			int newCenterPos = Math.min(Math.min(aboveCenterCount, oldCenterPos), newQueryHistoryInfos.size() - 1);
			newCenterVersion = mergedInfos.get(newCenterPos).getPrimerySpec();
			currentlyPresentedInfos = cutInfos(mergedInfos, newCenterPos);

		} else if (nextPage && olderVersions < belowCenterCount) {
			List<HistoryInfo> mergedInfos = mergeHistoryInfoLists(newQueryHistoryInfos, currentlyPresentedInfos);
			int oldCenterPos = findPositionOfId(currentCenterVersionShown.getIdentifier(), mergedInfos);

			// not enough versions: go further up, but never further than the old center as this is supposed to be
			// nextPage and thus at the very least not prevPage
			int newCenterPos = Math.max(Math.max(mergedInfos.size() - 1 - belowCenterCount, oldCenterPos), 0);
			newCenterVersion = mergedInfos.get(newCenterPos).getPrimerySpec();
			currentlyPresentedInfos = cutInfos(mergedInfos, newCenterPos);

		} else {
			newCenterVersion = newQueryHistoryInfos.get(newCenterVersionPos).getPrimerySpec();
			currentlyPresentedInfos = cutInfos(newQueryHistoryInfos, newCenterVersionPos);
		}
		currentCenterVersionShown = newCenterVersion;

	}

	private List<HistoryInfo> cutInfos(List<HistoryInfo> mergedInfos, int newCenterPos) {
		int smallestIndexIn = Math.max(0, newCenterPos - aboveCenterCount);
		int largestIndexIn = Math.min(mergedInfos.size() - 1, newCenterPos + belowCenterCount);
		List<HistoryInfo> cut = new ArrayList<HistoryInfo>();
		for (int i = smallestIndexIn; i <= largestIndexIn; i++) {
			cut.add(mergedInfos.get(i));
		}
		return cut;
	}

	private int findPositionOfId(int identifier, List<HistoryInfo> mergedInfos) {
		return findPositionOfId(identifier, mergedInfos, true);
	}

	/**
	 * @param identifier
	 * @param infoList
	 * @param mustBeIn When true, an exception is thrown if the id is not in the list range.
	 * @return The index of the first version with id <= identifier (the first version in the list that is not newer).
	 */
	private int findPositionOfId(int identifier, List<HistoryInfo> infoList, boolean mustBeIn) {
		for (int i = 0; i < infoList.size(); i++) {
			if (getId(infoList.get(i)) <= identifier) {
				return i;
			}
		}
		if (mustBeIn) {
			throw new IllegalArgumentException("Did not find version with id " + identifier + " but should be in.");
		}
		return -1;

	}

	private List<HistoryInfo> mergeHistoryInfoLists(List<HistoryInfo> newQueryHistoryInfos,
		List<HistoryInfo> oldPresentedHistoryInfos) {
		return newMerge(newQueryHistoryInfos, oldPresentedHistoryInfos);

	}

	private List<HistoryInfo> newMerge(List<HistoryInfo> newQuery, List<HistoryInfo> olderVersions) {
		int newPos = 0, oldPos = 0;

		List<HistoryInfo> merge = new ArrayList<HistoryInfo>();
		while (newPos < newQuery.size() && oldPos < olderVersions.size()) {
			if (getId(newQuery.get(newPos)) >= getId(olderVersions.get(oldPos))) {
				merge.add(newQuery.get(newPos));
				newPos++;
			} else {
				merge.add(olderVersions.get(oldPos));
				oldPos++;
			}

		}

		// add rest
		assert newPos == newQuery.size() || oldPos == olderVersions.size();
		for (int i = newPos; i < newQuery.size(); i++) {
			merge.add(newQuery.get(i));
		}

		for (int i = oldPos; i < olderVersions.size(); i++) {
			merge.add(olderVersions.get(i));
		}

		// remove duplicates
		Iterator<HistoryInfo> iterator = merge.iterator();
		int prevId = Integer.MIN_VALUE;
		while (iterator.hasNext()) {
			int currId = getId(iterator.next());
			if (currId == prevId) {
				iterator.remove();
			}
			prevId = currId;
		}
		return merge;
	}

	private int getId(HistoryInfo info) {
		return info.getPrimerySpec().getIdentifier();
	}

	/**
	 * 
	 * @param centerVersion The query center version.
	 * @return
	 * @throws EMFStoreException
	 */
	private HistoryQuery getQuery(PrimaryVersionSpec centerVersion, int aboveCenter, int belowCenter)
		throws EMFStoreException {
		PrimaryVersionSpec version;
		if (centerVersion != null) {
			version = centerVersion;
		} else {
			version = projectSpace.getBaseVersion();
			currentCenterVersionShown = version;
		}

		HistoryQuery query;
		QueryMargins margins;
		if (centerVersion != null && !centerVersion.getBranch().equals(projectBranch)) {
			margins = getBranchAdaptedMargins(centerVersion, aboveCenter, belowCenter);
		} else {
			margins = new QueryMargins();
			margins.aboveCenter = aboveCenter;
			margins.belowCenter = belowCenter;
			margins.querySpec = version;
		}
		if (modelElement != null && !(modelElement instanceof ProjectSpace)
			&& projectSpace.getProject().contains(modelElement)) {
			query = HistoryQueryBuilder.modelelementQuery(margins.querySpec, projectSpace.getProject()
				.getModelElementId(modelElement), margins.aboveCenter, margins.belowCenter, showAllVersions, true);
		} else {
			query = HistoryQueryBuilder.rangeQuery(margins.querySpec, margins.aboveCenter, margins.belowCenter,
				showAllVersions, !showAllVersions, !showAllVersions, true);
		}

		return query;
	}

	/**
	 * Helper functions for retrieving history info when the current margin info is of the wrong branch.
	 * 
	 * @throws EMFStoreException
	 */
	private QueryMargins getBranchAdaptedMargins(PrimaryVersionSpec centerVersion, int aboveCenter, int belowCenter)
		throws EMFStoreException {
		QueryMargins margins = new QueryMargins();
		centerVersion.setBranch(projectBranch);
		margins.aboveCenter = aboveCenter;
		margins.belowCenter = belowCenter;

		// currently always the biggest primary version of given branch which is equal or lower
		// to the given versionSpec
		PrimaryVersionSpec nearestSpec = (PrimaryVersionSpec) projectSpace.resolveVersionSpec(centerVersion);
		if (nearestSpec.getIdentifier() < centerVersion.getIdentifier()) {
			margins.aboveCenter = aboveCenter + (centerVersion.getIdentifier() - nearestSpec.getIdentifier()) + 1;
		} else if (nearestSpec.getIdentifier() > centerVersion.getIdentifier()) {
			margins.belowCenter = belowCenter + (nearestSpec.getIdentifier() - centerVersion.getIdentifier() + 1);
		}
		margins.querySpec = nearestSpec;

		return margins;
	}

	/**
	 * Allows to switch between showing all history info items (across all branches) or just those relevant to the
	 * current project branch.
	 * 
	 * @param allVersions if true versions across all branches are shown, otherwise only versions for the current branch
	 *            including ancestor versions
	 */
	public void setShowAllVersions(boolean allVersions) {
		showAllVersions = allVersions;
		currentCenterVersionShown = null;
		currentlyPresentedInfos.clear();
	}

	/**
	 * Swaps to the next page. Call {@link #retrieveHistoryInfos()} to retrieve
	 * the new page.
	 */
	public void nextPage() {
		nextPage = true;
		prevPage = false;
	}

	/**
	 * Swaps to the previous page. Call {@link #retrieveHistoryInfos()} to
	 * retrieve the new page.
	 */
	public void previousPage() {
		prevPage = true;
		nextPage = false;
	}

	/**
	 * Swaps to a page containing the specified version. Call {@link #retrieveHistoryInfos()} to
	 * retrieve the new page.
	 * 
	 * @param id The identifier of the version to display.
	 * @throws EMFStoreException When an error occurs while retrieving versions from the server.
	 * 
	 * @return true if a version range surrounding the id has been found, false otherwise. Note that the range does not
	 *         necessarily contain the id, for example if only versions for a certain branch are shown.
	 */
	public boolean setVersion(int id) throws EMFStoreException {
		prevPage = false;
		nextPage = false;
		if (currentlyPresentedInfos.isEmpty() || currentCenterVersionShown == null) {
			return false;
		}

		int newestVersion = getId(currentlyPresentedInfos.get(0));
		int oldestVersion = getId(currentlyPresentedInfos.get(currentlyPresentedInfos.size() - 1));
		//
		// List<HistoryInfo> currentHistoryInfosBU = currentlyPresentedInfos;
		// PrimaryVersionSpec currentCenterBU = currentCenterVersionShown;

		if (newestVersion >= id && id >= oldestVersion) {
			return true; // already there
		}

		HistoryQuery query = getQuery(Versions.createPRIMARY(projectSpace.getBaseVersion(), id), aboveCenterCount
			+ belowCenterCount, aboveCenterCount + belowCenterCount);
		List<HistoryInfo> historyInfos = (List<HistoryInfo>) (List<?>) projectSpace.getHistoryInfos(query);
		int requestedIdPos = findPositionOfId(id, historyInfos, false);
		boolean contained = containsId(historyInfos, id);
		int newCenterPos;
		if (!contained) {
			return false;
		}
		// The id is at least in the returned range
		if (requestedIdPos != -1) {
			// the id is really in there
			newCenterPos = getPosForIdTakingAboveBelowIntoAccount(historyInfos, requestedIdPos);
		} else {
			// id in range but not in
			requestedIdPos = findPositionOfId(id, historyInfos);
			newCenterPos = getPosForIdTakingAboveBelowIntoAccount(historyInfos, requestedIdPos);
		}
		currentCenterVersionShown = historyInfos.get(newCenterPos).getPrimerySpec();
		currentlyPresentedInfos = cutInfos(historyInfos, newCenterPos);

		return true;
	}

	private int getPosForIdTakingAboveBelowIntoAccount(List<HistoryInfo> infoList, int pos) {
		int newCenterPos;
		if (pos + belowCenterCount > infoList.size()) {
			newCenterPos = Math.max(0, infoList.size() - belowCenterCount - 1);
		} else if (pos - aboveCenterCount < 0) {
			newCenterPos = Math.min(infoList.size() - 1, aboveCenterCount);
		} else {
			newCenterPos = pos;
		}
		return newCenterPos;
	}

	private boolean containsId(List<HistoryInfo> infos, int id) {
		int newestVersion = getId(infos.get(0));
		int oldestVersion = getId(infos.get(infos.size() - 1));

		if (newestVersion >= id && id >= oldestVersion) {
			return true;
		}
		return false;
	}

	/**
	 * Helper class containing parameters for history query.
	 */
	private class QueryMargins {
		private int belowCenter;
		private int aboveCenter;
		private PrimaryVersionSpec querySpec;
	}
}