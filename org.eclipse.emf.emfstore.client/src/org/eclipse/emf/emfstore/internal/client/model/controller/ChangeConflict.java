/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.controller;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESChangeConflictImpl;
import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;

/**
 * The actual implementation of an {@link ESChangeConflictImpl} containing
 * the changes that caused the conflict.
 * 
 * @author wesendon
 * @author emueller
 */
public class ChangeConflict implements APIDelegate<ESChangeConflict> {

	private List<ChangePackage> myChangePackages;
	private List<ChangePackage> newPackages;
	private ProjectSpace projectSpace;
	private Set<ConflictBucketCandidate> conflictBucketCandidates;
	private final ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping;
	private ESChangeConflictImpl apiImpl;

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
		ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
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
	public ESModelElementIdToEObjectMapping<ESModelElementId> getIdToEObjectMapping() {
		return idToEObjectMapping;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#getAPIImpl()
	 */
	public ESChangeConflict getAPIImpl() {
		if (apiImpl == null) {
			apiImpl = createAPIImpl();
		}
		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPIImpl()
	 */
	public ESChangeConflictImpl createAPIImpl() {
		return new ESChangeConflictImpl(this);
	}

}