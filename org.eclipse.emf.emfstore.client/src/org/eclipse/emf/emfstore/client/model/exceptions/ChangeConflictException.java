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
package org.eclipse.emf.emfstore.client.model.exceptions;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

/**
 * Represents the exception that there are conflicting changes.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class ChangeConflictException extends WorkspaceException {

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
	 * @param projectSpace the ProjectSpace
	 * @param myChangePackage my change package
	 * @param newPackages the list of change packages that caused the exception
	 * @param conflictBucketCandidates a set of conflict candidates
	 */
	public ChangeConflictException(ProjectSpace projectSpace, List<ChangePackage> myChangePackages,
		List<ChangePackage> newPackages, Set<ConflictBucketCandidate> conflictBucketCandidates,
		IModelElementIdToEObjectMapping idToEObjectMapping) {
		super("Conflict detected on update");
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

	public IModelElementIdToEObjectMapping getIdToEObjectMapping() {
		return idToEObjectMapping;
	}

}