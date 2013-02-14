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
package org.eclipse.emf.emfstore.internal.client.model.controller;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;

public class ChangeConflict implements ESChangeConflict {

	private List<ChangePackage> myChangePackages;
	private List<ChangePackage> newPackages;
	private ProjectSpace projectSpace;
	private Set<ConflictBucketCandidate> conflictBucketCandidates;
	private final IModelElementIdToEObjectMapping idToEObjectMapping;

	/**
	 * Retrieve the list of change packages that caused the exception.
	 * 
	 * @return the list
	 */
	public List<ChangePackage> getNewPackages() {
		return newPackages;
	}

	/**
	 * Constructor.
	 * 
	 * 
	 * @param projectSpace
	 *            the ProjectSpace
	 * @param myChangePackages
	 *            my change package
	 * @param newPackages
	 *            the list of change packages that caused the exception
	 * @param conflictBucketCandidates
	 *            a set of conflict candidates
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects and vice versa.<br/>
	 *            Contains all IDs of model elements involved in the {@link ChangePackage}s
	 *            as well as those contained by the project in the {@link ProjectSpace}
	 */
	public ChangeConflict(ProjectSpace projectSpace, List<ChangePackage> myChangePackages,
		List<ChangePackage> newPackages, Set<ConflictBucketCandidate> conflictBucketCandidates,
		IModelElementIdToEObjectMapping idToEObjectMapping) {
		this.myChangePackages = myChangePackages;
		this.newPackages = newPackages;
		this.projectSpace = projectSpace;
		this.conflictBucketCandidates = conflictBucketCandidates;
		this.idToEObjectMapping = idToEObjectMapping;
	}

	/**
	 * @return the ProjectSpace.
	 */
	public ProjectSpace getProjectSpace() {
		return projectSpace;
	}

	/**
	 * @return the conflict candidates
	 */
	public Set<ConflictBucketCandidate> getConflictBucketCandidates() {
		return conflictBucketCandidates;
	}

	/**
	 * @return my change package
	 */
	public List<ChangePackage> getMyChangePackages() {
		return myChangePackages;
	}

	/**
	 * Returns the mapping from IDs to EObjects and vice versa.<br/>
	 * The mapping contains all IDs of model elements involved in the {@link ChangePackage}s
	 * as well as those contained by the project in the {@link ProjectSpace}
	 * 
	 * @return the mapping from IDs to EObjects and vice versa
	 */
	public IModelElementIdToEObjectMapping getIdToEObjectMapping() {
		return idToEObjectMapping;
	}

}