/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.core.subinterfaces;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod.MethodId;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.exceptions.StorageException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.versioning.AncestorVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Version;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;

/**
 * This subinterface implements all version related functionality.
 * 
 * @author wesendon
 */
public class VersionSubInterfaceImpl extends AbstractSubEmfstoreInterface {

	/**
	 * Default constructor.
	 * 
	 * @param parentInterface
	 *            parent interface
	 * @throws FatalESException
	 *             in case of failure
	 */
	public VersionSubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalESException {
		super(parentInterface);
	}

	/**
	 * Resolves a versionSpec and delivers the corresponding primary
	 * versionSpec.
	 * 
	 * @param projectId
	 *            project id
	 * @param versionSpec
	 *            versionSpec
	 * @return primary versionSpec
	 * 
	 * @throws InvalidVersionSpecException
	 *             if the project ID is invalid
	 * @throws ESException
	 *             if versionSpec can't be resolved or other failure
	 */
	@EmfStoreMethod(MethodId.RESOLVEVERSIONSPEC)
	public PrimaryVersionSpec resolveVersionSpec(ProjectId projectId, VersionSpec versionSpec)
		throws InvalidVersionSpecException, ESException {

		sanityCheckObjects(projectId, versionSpec);

		synchronized (getMonitor()) {
			final ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);

			if (versionSpec instanceof PrimaryVersionSpec) {
				return resolvePrimaryVersionSpec(projectHistory, (PrimaryVersionSpec) versionSpec);
			} else if (versionSpec instanceof HeadVersionSpec) {
				return resolveHeadVersionSpec(projectHistory, (HeadVersionSpec) versionSpec);
			} else if (versionSpec instanceof DateVersionSpec) {
				return resolveDateVersionSpec(projectHistory, (DateVersionSpec) versionSpec);
			} else if (versionSpec instanceof TagVersionSpec) {
				return resolveTagVersionSpec(projectHistory, (TagVersionSpec) versionSpec);
			} else if (versionSpec instanceof BranchVersionSpec) {
				return resolveBranchVersionSpec(projectHistory, (BranchVersionSpec) versionSpec);
			} else if (versionSpec instanceof AncestorVersionSpec) {
				return resolveAncestorVersionSpec(projectHistory, (AncestorVersionSpec) versionSpec);
			} else if (versionSpec instanceof PagedUpdateVersionSpec) {
				return resolvePagedUpdateVersionSpec(projectHistory, (PagedUpdateVersionSpec) versionSpec);
			}

			throw new InvalidVersionSpecException();
		}
	}

	private PrimaryVersionSpec resolveAncestorVersionSpec(ProjectHistory projectHistory, AncestorVersionSpec versionSpec)
		throws InvalidVersionSpecException {

		Version currentSource = getVersion(projectHistory, versionSpec.getSource());
		Version currentTarget = getVersion(projectHistory, versionSpec.getTarget());

		if (currentSource == null || currentTarget == null) {
			throw new InvalidVersionSpecException("Specified source and/or target version invalid.");
		}

		// The goal is to find the common ancestor version of the source and
		// target version from different branches. In
		// order to find the ancestor the algorithm starts at the specified
		// version and walks down the version tree in
		// parallel for source and target until the current versions are equal
		// and the ancestor is found. In Each step
		// only one version (of target and source) is decremented. To find the
		// global ancestor it is necessary that the
		// version with the higher version number is decremented.
		while (currentSource != null && currentTarget != null) {
			if (currentSource == currentTarget) {
				return currentSource.getPrimarySpec();
			}

			// Shortcut for most common merge usecase: If you have 2 parallel
			// branches, only seperated by one level and merge several times from the one branch into the another.
			// This case is also supported by #getVersions
			if (currentSource.getMergedFromVersion().contains(currentTarget)) {
				return currentTarget.getPrimarySpec();
			}

			if (currentSource.getPrimarySpec().getIdentifier() >= currentTarget.getPrimarySpec().getIdentifier()) {
				currentSource = findNextVersion(currentSource);
			} else {
				currentTarget = findNextVersion(currentTarget);
			}

		}
		throw new InvalidVersionSpecException();
	}

	private PrimaryVersionSpec resolvePrimaryVersionSpec(ProjectHistory projectHistory, PrimaryVersionSpec versionSpec)
		throws InvalidVersionSpecException {
		final int index = versionSpec.getIdentifier();
		final String branch = versionSpec.getBranch();
		final int versions = projectHistory.getVersions().size();
		if (0 > index || index >= versions || branch == null) {
			throw new InvalidVersionSpecException("Invalid version requested. Version " + index
				+ " does not exist on server.");
		}

		if (branch.equals(VersionSpec.GLOBAL)) {
			return projectHistory.getVersions().get(index).getPrimarySpec();
		}

		// Get biggest primary version of given branch which is equal or lower
		// to the given versionSpec
		for (int i = index; i >= 0; i--) {
			final Version version = projectHistory.getVersions().get(i);
			if (branch.equals(version.getPrimarySpec().getBranch())) {
				return version.getPrimarySpec();
			}

		}
		throw new InvalidVersionSpecException();
	}

	private PrimaryVersionSpec resolveHeadVersionSpec(ProjectHistory projectHistory, HeadVersionSpec versionSpec)
		throws InvalidVersionSpecException {
		if (VersionSpec.GLOBAL.equals(versionSpec.getBranch())) {
			return projectHistory.getVersions().get(projectHistory.getVersions().size() - 1).getPrimarySpec();
		}
		final BranchInfo info = getBranchInfo(projectHistory, versionSpec);
		if (info != null) {
			return info.getHead();
		}
		throw new InvalidVersionSpecException();
	}

	private PrimaryVersionSpec resolveDateVersionSpec(ProjectHistory projectHistory, DateVersionSpec versionSpec) {
		for (final Version version : projectHistory.getVersions()) {
			final LogMessage logMessage = version.getLogMessage();
			if (logMessage == null || logMessage.getDate() == null) {
				continue;
			}
			if (versionSpec.getDate().before(logMessage.getDate())) {
				final Version previousVersion = version.getPreviousVersion();
				if (previousVersion == null) {
					return VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
				}
				return previousVersion.getPrimarySpec();
			}
		}
		return projectHistory.getLastVersion().getPrimarySpec();
	}

	private PrimaryVersionSpec resolveTagVersionSpec(ProjectHistory projectHistory, TagVersionSpec versionSpec)
		throws InvalidVersionSpecException {
		for (final Version version : projectHistory.getVersions()) {
			for (final TagVersionSpec tag : version.getTagSpecs()) {
				if (versionSpec.equals(tag)) {
					return ModelUtil.clone(version.getPrimarySpec());
				}
			}
		}
		throw new InvalidVersionSpecException();
	}

	private PrimaryVersionSpec resolveBranchVersionSpec(ProjectHistory projectHistory, BranchVersionSpec versionSpec)
		throws InvalidVersionSpecException {
		final BranchInfo branchInfo = getBranchInfo(projectHistory, versionSpec);
		if (branchInfo == null) {
			throw new InvalidVersionSpecException();
		}
		return branchInfo.getHead();
	}

	/**
	 * Create a new version.
	 * 
	 * @param sessionId
	 *            the ID of the session being used to create a new version
	 * @param projectId
	 *            the ID of the project for which a new version is created
	 * @param baseVersionSpec
	 *            the base version
	 * @param changePackage
	 *            the change package containing all changes that make up the new version
	 * @param targetBranch
	 *            the target branch for which to create the new version
	 * @param sourceVersion
	 *            the source version
	 * @param logMessage
	 *            a log message
	 * @return the new version
	 * @throws ESException in case of failure
	 */
	@EmfStoreMethod(MethodId.CREATEVERSION)
	public PrimaryVersionSpec createVersion(SessionId sessionId, ProjectId projectId,
		PrimaryVersionSpec baseVersionSpec, ChangePackage changePackage, BranchVersionSpec targetBranch,
		PrimaryVersionSpec sourceVersion, LogMessage logMessage) throws ESException {

		// final ACUser user = getAuthorizationControl().resolveUser(sessionId); //TODO: commented to get Jaxrs running
		sanityCheckObjects(sessionId, projectId, baseVersionSpec, changePackage, logMessage);
		synchronized (getMonitor()) {

			final long currentTimeMillis = System.currentTimeMillis();
			final ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);

			// Find branch
			final BranchInfo baseBranch = getBranchInfo(projectHistory, baseVersionSpec);
			final Version baseVersion = getVersion(projectHistory, baseVersionSpec);

			if (baseVersion == null || baseBranch == null) {
				throw new InvalidVersionSpecException("Branch and/or version doesn't exist.");
			}

			// defined here fore scoping reasons
			Version newVersion = null;
			BranchInfo newBranch = null;

			// copy project and apply changes
			final Project newProjectState = ((ProjectImpl) getSubInterface(ProjectSubInterfaceImpl.class).getProject(
				baseVersion)).copy();
			changePackage.apply(newProjectState);

			// normal commit
			if (targetBranch == null || baseVersion.getPrimarySpec().getBranch().equals(targetBranch.getBranch())) {

				// If branch is null or branch equals base branch, create new
				// version for specific branch
				if (!baseVersionSpec.equals(isHeadOfBranch(projectHistory, baseVersion.getPrimarySpec()))) {
					throw new ESUpdateRequiredException();
				}
				newVersion = createVersion(projectHistory, newProjectState, logMessage, /* user */null /*
																										 * TODO:
																										 * commented to
																										 * get Jaxrs
																										 * running
																										 */,
					baseVersion);
				newVersion.setPreviousVersion(baseVersion);
				baseBranch.setHead(ModelUtil.clone(newVersion.getPrimarySpec()));

				// case for new branch creation
			} else if (getBranchInfo(projectHistory, targetBranch) == null) {
				if (targetBranch.getBranch().equals("")) {
					throw new InvalidVersionSpecException("Empty branch name is not permitted.");
				}
				if (targetBranch.getBranch().equals(VersionSpec.GLOBAL)) {
					throw new InvalidVersionSpecException("Reserved branch name '" + VersionSpec.GLOBAL
						+ "' must not be used.");
				}
				// when branch does NOT exist, create new branch
				newVersion = createVersion(projectHistory, newProjectState, logMessage, /* user */null /*
																										 * TODO:
																										 * commented to
																										 * get Jaxrs
																										 * running
																										 */,
					baseVersion);
				newBranch = createNewBranch(projectHistory, baseVersion.getPrimarySpec(), newVersion.getPrimarySpec(),
					targetBranch);
				newVersion.setAncestorVersion(baseVersion);

			} else {
				// This point only can be reached with invalid input
				throw new IllegalStateException("The combination of targetSpec and/or branch are invalid.");
			}

			if (sourceVersion != null) {
				newVersion.getMergedFromVersion().add(getVersion(projectHistory, sourceVersion));
			}

			// try to save
			try {
				try {
					getResourceHelper().createResourceForProject(newProjectState,
						newVersion.getPrimarySpec(), projectHistory.getProjectId());
					getResourceHelper().createResourceForChangePackage(changePackage, newVersion.getPrimarySpec(),
						projectId);
					getResourceHelper().createResourceForVersion(newVersion, projectHistory.getProjectId());

					newVersion.setProjectStateResource(newProjectState.eResource());
					newVersion.setChangeResource(changePackage.eResource());

				} catch (final FatalESException e) {
					// try to roll back. removing version is necessary in all cases
					projectHistory.getVersions().remove(newVersion);

					if (newBranch == null) {
						// normal commit
						baseVersion.setNextVersion(null);
						baseBranch.setHead(ModelUtil.clone(baseVersion.getPrimarySpec()));
					} else {
						// branch commit
						baseVersion.getBranchedVersions().remove(newVersion);
						projectHistory.getBranches().remove(newBranch);
					}
					// TODO: delete obsolete project, changepackage and version files
					throw new StorageException(StorageException.NOSAVE, e);
				}

				// if ancestor isn't null, a new branch was created. In this
				// case we want to keep the old base project
				// state
				if (newVersion.getAncestorVersion() == null && baseVersion.getProjectState() != null) {
					// delete projectstate from last revision depending on
					// persistence policy
					deleteOldProjectSpaceAccordingToOptions(projectId, baseVersion);
				}

				save(baseVersion);
				save(projectHistory);

			} catch (final FatalESException e) {
				// roll back failed
				EMFStoreController.getInstance().shutdown(e);
				throw new ESException("Shutting down server.");
			}

			ModelUtil.logInfo("Total time for commit: " + (System.currentTimeMillis() - currentTimeMillis));
			return newVersion.getPrimarySpec();
		}
	}

	private BranchInfo createNewBranch(ProjectHistory projectHistory, PrimaryVersionSpec baseSpec,
		PrimaryVersionSpec primarySpec, BranchVersionSpec branch) {
		primarySpec.setBranch(branch.getBranch());

		final BranchInfo branchInfo = VersioningFactory.eINSTANCE.createBranchInfo();
		branchInfo.setName(branch.getBranch());
		branchInfo.setSource(ModelUtil.clone(baseSpec));
		branchInfo.setHead(ModelUtil.clone(primarySpec));

		projectHistory.getBranches().add(branchInfo);

		return branchInfo;
	}

	private Version createVersion(ProjectHistory projectHistory, Project projectState, LogMessage logMessage,
		ACUser user, Version previousVersion) throws ESException {
		final Version newVersion = VersioningFactory.eINSTANCE.createVersion();

		long computedChecksum = ModelUtil.NO_CHECKSUM;

		try {
			if (ServerConfiguration.isComputeChecksumOnCommitActive()) {
				computedChecksum = ModelUtil.computeChecksum(projectState);
			}
		} catch (final SerializationException exception) {
			// TODO: clarify what to do in case checksum computation fails + provide ext. point
			throw new ESException(MessageFormat.format("Could not compute checksum of project {0}.",
				projectHistory.getProjectName()), exception);
		}

		// newVersion.setChanges(changePackage);

		logMessage.setDate(new Date());
		// logMessage.setAuthor(user.getName()); //TODO: comment to get Jaxrs running
		newVersion.setLogMessage(logMessage);

		// latest version == getVersion.size() (version start with index 0 as
		// the list), branch from previous is used.
		newVersion.setPrimarySpec(Versions.createPRIMARY(previousVersion.getPrimarySpec(), projectHistory.getVersions()
			.size()));
		newVersion.getPrimarySpec().setProjectStateChecksum(computedChecksum);
		newVersion.setNextVersion(null);

		projectHistory.getVersions().add(newVersion);
		return newVersion;
	}

	private Version getVersion(ProjectHistory projectHistory, PrimaryVersionSpec baseVersionSpec) {
		if (0 > baseVersionSpec.getIdentifier()
			|| baseVersionSpec.getIdentifier() > projectHistory.getVersions().size() - 1) {
			return null;
		}
		final Version version = projectHistory.getVersions().get(baseVersionSpec.getIdentifier());
		if (version == null || !version.getPrimarySpec().equals(baseVersionSpec)) {
			return null;
		}
		return version;
	}

	private PrimaryVersionSpec isHeadOfBranch(ProjectHistory projectHistory, PrimaryVersionSpec versionSpec) {
		final BranchInfo branchInfo = getBranchInfo(projectHistory, versionSpec);
		if (branchInfo != null && branchInfo.getHead().equals(versionSpec)) {
			return branchInfo.getHead();
		}
		return null;
	}

	private BranchInfo getBranchInfo(ProjectHistory projectHistory, VersionSpec versionSpec) {
		for (final BranchInfo branchInfo : projectHistory.getBranches()) {
			if (branchInfo.getName().equals(versionSpec.getBranch())) {
				return branchInfo;
			}
		}
		return null;
	}

	/**
	 * Returns all branches for the project with the given ID.
	 * 
	 * @param projectId
	 *            the ID of a project
	 * @return a list containing information about each branch
	 * @throws ESException in case of failure
	 */
	@EmfStoreMethod(MethodId.GETBRANCHES)
	public List<BranchInfo> getBranches(ProjectId projectId) throws ESException {
		synchronized (getMonitor()) {
			final ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);
			final ArrayList<BranchInfo> result = new ArrayList<BranchInfo>();
			for (final BranchInfo branch : projectHistory.getBranches()) {
				result.add(ModelUtil.clone(branch));
			}
			return result;
		}
	}

	/**
	 * Deletes projectstate from last revision depending on persistence policy.
	 * 
	 * @param projectId
	 *            project id
	 * @param previousHeadVersion
	 *            last head version
	 */
	private void deleteOldProjectSpaceAccordingToOptions(ProjectId projectId, Version previousHeadVersion) {
		final String property = ServerConfiguration.getProperties().getProperty(
			ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE,
			ServerConfiguration.PROJECTSPACE_VERSION_PERSISTENCE_DEFAULT);

		if (property.equals(ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE_EVERYXVERSIONS)) {

			final int x = getResourceHelper().getXFromPolicy(
				ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE_EVERYXVERSIONS_X,
				ServerConfiguration.PROJECTSTATE_VERSION_PERSISTENCE_EVERYXVERSIONS_X_DEFAULT, false);

			// always save projecstate of first version
			final int lastVersion = previousHeadVersion.getPrimarySpec().getIdentifier();
			if (lastVersion != 0 && lastVersion % x != 0) {
				getResourceHelper().deleteProjectState(previousHeadVersion, projectId);
			}

		} else {
			getResourceHelper().deleteProjectState(previousHeadVersion, projectId);
		}
	}

	/**
	 * Returns all changes within the specified version range for a given project.
	 * 
	 * @param projectId
	 *            the ID of a project
	 * @param source
	 *            the source version
	 * @param target
	 *            the target version (inclusive)
	 * @return a list of change packages containing all the changes for the specified version range
	 * 
	 * @throws InvalidVersionSpecException
	 *             if an invalid version has been specified
	 * @throws ESException
	 *             in case of failure
	 */
	@EmfStoreMethod(MethodId.GETCHANGES)
	public List<ChangePackage> getChanges(ProjectId projectId, VersionSpec source, VersionSpec target)
		throws InvalidVersionSpecException, ESException {

		sanityCheckObjects(projectId, source, target);
		final PrimaryVersionSpec resolvedSource = resolveVersionSpec(projectId, source);
		final PrimaryVersionSpec resolvedTarget = resolveVersionSpec(projectId, target);
		// if target and source are equal return empty list
		if (resolvedSource.getIdentifier() == resolvedTarget.getIdentifier()) {
			return new ArrayList<ChangePackage>();
		}

		synchronized (getMonitor()) {

			final boolean updateForward = resolvedTarget.getIdentifier() > resolvedSource.getIdentifier();

			// Example: if you want the changes to get from version 5 to 7, you
			// need the changes contained in version 6
			// and 7. The reason is that each version holds the changes which
			// occurred from the predecessor to the
			// version itself. Version 5 holds the changes to get from version 4
			// to 5 and therefore is irrelevant.
			// For that reason the first version is removed, since getVersions
			// always sorts ascending order.
			final List<Version> versions = getVersions(projectId, resolvedSource, resolvedTarget);
			if (versions.size() > 1) {
				versions.remove(0);
			}

			List<ChangePackage> result = new ArrayList<ChangePackage>();
			for (final Version version : versions) {
				final ChangePackage changes = version.getChanges();
				if (changes != null) {
					changes.setLogMessage(ModelUtil.clone(version.getLogMessage()));
					result.add(changes);
				}
			}

			// if source is after target in time
			if (!updateForward) {
				// reverse list and change packages
				final List<ChangePackage> resultReverse = new ArrayList<ChangePackage>();
				for (final ChangePackage changePackage : result) {
					final ChangePackage changePackageReverse = changePackage.reverse();
					// copy again log message
					// reverse() created a new change package without copying
					// existent attributes
					changePackageReverse.setLogMessage(ModelUtil.clone(changePackage.getLogMessage()));
					resultReverse.add(changePackageReverse);
				}

				Collections.reverse(resultReverse);
				result = resultReverse;
			}

			return result;
		}
	}

	/**
	 * Returns the specified version of a project.
	 * 
	 * @param projectId
	 *            project id
	 * @param versionSpec
	 *            versionSpec
	 * @return the version
	 * @throws ESException
	 *             if version couldn't be found
	 */
	protected Version getVersion(ProjectId projectId, PrimaryVersionSpec versionSpec) throws ESException {
		final ProjectHistory project = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);
		return getVersion(project, versionSpec);
	}

	/**
	 * Returns a list of versions starting from source and ending with target.
	 * This method returns the version always in an ascanding order. So if you
	 * need it ordered differently you have to reverse the list.
	 * 
	 * @param projectId
	 *            project id
	 * @param source
	 *            source
	 * @param target
	 *            target
	 * @return list of versions
	 * @throws ESException
	 *             if source or target are out of range or any other problem
	 *             occurs
	 */
	protected List<Version> getVersions(ProjectId projectId, PrimaryVersionSpec source, PrimaryVersionSpec target)
		throws ESException {
		if (source.compareTo(target) < 1) {
			final ProjectHistory projectHistory = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);

			final Version sourceVersion = getVersion(projectHistory, source);
			final Version targetVersion = getVersion(projectHistory, target);

			if (sourceVersion == null || targetVersion == null) {
				throw new InvalidVersionSpecException();
			}
			final List<Version> result = new ArrayList<Version>();

			// since the introduction of branches the versions are collected
			// in different order.
			Version currentVersion = targetVersion;
			while (currentVersion != null) {
				result.add(currentVersion);
				if (currentVersion.equals(sourceVersion)) {
					break;
				}
				if (currentVersion.getPrimarySpec().compareTo(sourceVersion.getPrimarySpec()) < 0) {
					// walked too far, invalid path.
					throw new InvalidVersionSpecException("Walked too far, invalid path.");
				}
				// Shortcut for most common merge usecase: If you have 2
				// parallel branches and merge several times
				// from the one branch into the another.
				if (currentVersion.getMergedFromVersion().contains(sourceVersion)) {
					// add sourceVersion because #getChanges always removes
					// the first version
					result.add(sourceVersion);
					break;
				}

				currentVersion = findNextVersion(currentVersion);
			}
			// versions are collected in descending order, so the result has to be reversed.
			Collections.reverse(result);
			return result;
		}

		return getVersions(projectId, target, source);
	}

	/**
	 * Helper method which retrieves the next version in the history tree. This
	 * method must be used in reversed order. With the introduction of branches, the versions are organized in a tree
	 * structure. Therefore, next versions are always searched for walking up the tree.
	 * 
	 * @param currentVersion
	 *            current version
	 * @return version
	 * @throws InvalidVersionSpecException
	 *             if the path can't be followed further
	 */
	public static Version findNextVersion(Version currentVersion) throws InvalidVersionSpecException {
		// find next version
		if (currentVersion.getPreviousVersion() != null) {
			currentVersion = currentVersion.getPreviousVersion();
		} else if (currentVersion.getAncestorVersion() != null) {
			currentVersion = currentVersion.getAncestorVersion();
		} else {
			throw new InvalidVersionSpecException("Couldn't determine next version in history.");
		}
		return currentVersion;
	}

	private PrimaryVersionSpec resolvePagedUpdateVersionSpec(ProjectHistory projectHistory,
		PagedUpdateVersionSpec baseVersion) {

		int changes = 0;
		PrimaryVersionSpec resolvedSpec = baseVersion.getBaseVersionSpec();
		int maxChanges = baseVersion.getMaxChanges();

		int i = resolvedSpec.getIdentifier();

		ChangePackage cp = projectHistory.getVersions().get(i).getChanges();

		if (i == projectHistory.getVersions().size() - 1) {
			return projectHistory.getVersions().get(i).getPrimarySpec();
		}

		do {
			cp = projectHistory.getVersions().get(++i).getChanges();
		} while (cp == null && i < projectHistory.getVersions().size());

		// pull at least one change package
		if (cp.getSize() > maxChanges) {
			maxChanges = cp.getSize();
		}

		while (changes < maxChanges && i < projectHistory.getVersions().size()) {
			resolvedSpec = projectHistory.getVersions().get(i).getPrimarySpec();
			final Version version = projectHistory.getVersions().get(i);
			final ChangePackage changePackage = version.getChanges();

			if (changePackage != null) {
				final int size = changePackage.getSize();
				if (changes + size >= maxChanges) {
					resolvedSpec = projectHistory.getVersions().get(i).getPrimarySpec();
					break;
				}
				changes += size;
			}
			i += 1;
		}

		return resolvedSpec;
	}
}