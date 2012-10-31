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
package org.eclipse.emf.emfstore.client.model.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.observers.UpdateObserver;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CreateDeleteOperation;

/**
 * Controller class for updating a project space.
 * 
 * @author ovonwesen
 * @author emueller
 */
public class UpdateController extends ServerCall<PrimaryVersionSpec> {

	private VersionSpec version;
	private UpdateCallback callback;
	private Set<String> ignoredClasses;
	private Map<EObject, List<AbstractOperation>> theirFilteredOperations;
	private Map<EObject, List<AbstractOperation>> myFilteredOperations;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space to be updated
	 * @param version
	 *            the target version
	 * @param callback
	 *            an optional update callback instance
	 * @param progress
	 *            a progress monitor that is used to indicate the progress of the update
	 */
	public UpdateController(ProjectSpaceBase projectSpace, VersionSpec version, UpdateCallback callback,
		IProgressMonitor progress) {
		super(projectSpace);

		// SANITY CHECKS
		if (version == null) {
			version = Versions.createHEAD(projectSpace.getBaseVersion());
		}
		if (callback == null) {
			callback = UpdateCallback.NOCALLBACK;
		}

		this.version = version;
		this.callback = callback;
		this.myFilteredOperations = new LinkedHashMap<EObject, List<AbstractOperation>>();
		this.theirFilteredOperations = new LinkedHashMap<EObject, List<AbstractOperation>>();
		setProgressMonitor(progress);
	}

	public UpdateController(ProjectSpaceBase projectSpace, VersionSpec version, UpdateCallback callback,
		IProgressMonitor progress, Set<String> ignoredClasses) {
		this(projectSpace, version, callback, progress);
		this.ignoredClasses = ignoredClasses;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall#run()
	 */
	@Override
	protected PrimaryVersionSpec run() throws EmfStoreException {
		return doUpdate(version);
	}

	private PrimaryVersionSpec doUpdate(VersionSpec version) throws EmfStoreException {
		getProgressMonitor().beginTask("Updating Project...", 100);
		getProgressMonitor().worked(1);
		getProgressMonitor().subTask("Resolving new version");
		final PrimaryVersionSpec resolvedVersion = getProjectSpace().resolveVersionSpec(version);
		if (resolvedVersion.compareTo(getProjectSpace().getBaseVersion()) == 0) {
			return resolvedVersion;
		}
		getProgressMonitor().worked(5);

		if (getProgressMonitor().isCanceled()) {
			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Fetching changes from server");
		List<ChangePackage> changes = new UnknownEMFStoreWorkloadCommand<List<ChangePackage>>(getProgressMonitor()) {
			@Override
			public List<ChangePackage> run(IProgressMonitor monitor) throws EmfStoreException {
				return getConnectionManager().getChanges(getSessionId(), getProjectSpace().getProjectId(),
					getProjectSpace().getBaseVersion(), resolvedVersion);
			}
		}.execute();

		ChangePackage localChanges = getProjectSpace().getLocalChangePackage(false);
		getProgressMonitor().worked(65);

		if (getProgressMonitor().isCanceled()) {
			return getProjectSpace().getBaseVersion();
		}

		getProgressMonitor().subTask("Checking for conflicts");

		ConflictDetector conflictDetector = new ConflictDetector();

		boolean potentialConflictsDetected = false;

		if (ignoredClassesExist()) {
			getProgressMonitor().subTask("Filtering ignored changes...");
			changes = filterIgnoredChanges(changes, true);
			localChanges = filterIgnoredChanges(Arrays.asList(localChanges), false).get(0);
		}

		if (getProjectSpace().getOperations().size() > 0) {
			Set<ConflictBucketCandidate> conflictBucketCandidates = conflictDetector.calculateConflictCandidateBuckets(
				Collections.singletonList(localChanges), changes);
			potentialConflictsDetected = conflictDetector.containsConflictingBuckets(conflictBucketCandidates);
			if (potentialConflictsDetected) {
				getProgressMonitor().subTask("Conflicts detected, calculating conflicts");
				ChangeConflictException conflictException = new ChangeConflictException(getProjectSpace(),
					localChanges, changes, conflictBucketCandidates);
				if (callback.conflictOccurred(conflictException, getProgressMonitor())) {
					if (ignoredClassesExist()) {
						getProgressMonitor().subTask("Reapply ignored changes...");
						applyFilteredOperations(getMyFilteredOperations());
						applyFilteredOperations(getTheirFilteredOperations());
					}

					return getProjectSpace().getBaseVersion();
				}
				throw conflictException;
			}
		}

		getProgressMonitor().worked(15);
		// TODO ASYNC review this cancel
		if (getProgressMonitor().isCanceled() || !callback.inspectChanges(getProjectSpace(), changes)) {
			return getProjectSpace().getBaseVersion();
		}

		WorkspaceManager.getObserverBus().notify(UpdateObserver.class).inspectChanges(getProjectSpace(), changes);

		// add ignored changes back into changepackages
		if (ignoredClassesExist()) {
			getProgressMonitor().subTask("Reapply filtered changes...");
			addFilteredChanges(Arrays.asList(localChanges), false);
			addFilteredChanges(changes, true);
		}

		getProgressMonitor().subTask("Applying changes");

		getProjectSpace().applyChanges(resolvedVersion, changes, localChanges);

		getTheirFilteredOperations().clear();
		getMyFilteredOperations().clear();

		WorkspaceManager.getObserverBus().notify(UpdateObserver.class).updateCompleted(getProjectSpace());

		return getProjectSpace().getBaseVersion();
	}

	private boolean ignoredClassesExist() {
		return ignoredClasses.size() > 0;
	}

	private void applyFilteredOperations(Map<EObject, List<AbstractOperation>> filteredOperations) {

		for (EObject key : filteredOperations.keySet()) {
			for (AbstractOperation operation : filteredOperations.get(key)) {

				if (operation == null) {
					continue;
				}

				getProjectSpace().applyOperations(Arrays.asList(operation), true);
			}
		}
	}

	private void addFilteredChanges(List<ChangePackage> changes, boolean theirs) {

		for (ChangePackage changePackage : changes) {

			List<AbstractOperation> newOperations = addFilteredOperations(changePackage, changePackage.getOperations(),
				theirs);

			changePackage.getOperations().clear();
			changePackage.getOperations().addAll(newOperations);
		}
	}

	private List<AbstractOperation> addFilteredOperations(EObject key, List<AbstractOperation> operations,
		boolean theirs) {

		List<AbstractOperation> newOps = new ArrayList<AbstractOperation>();
		int i = 0;

		Map<EObject, List<AbstractOperation>> filteredOperations = theirs ? getTheirFilteredOperations()
			: getMyFilteredOperations();

		for (AbstractOperation op : filteredOperations.get(key)) {
			if (op == null && operations.size() > 0) {
				AbstractOperation o = operations.get(i);
				if (o instanceof CompositeOperation) {
					CompositeOperation c = (CompositeOperation) o;
					List<AbstractOperation> injectOperations = addFilteredOperations(c, c.getSubOperations(), theirs);
					c.getSubOperations().clear();
					c.getSubOperations().addAll(injectOperations);
				}
				newOps.add(o);
				i++;
			} else {
				newOps.add(op);
			}
		}

		return newOps;
	}

	private List<ChangePackage> filterIgnoredChanges(List<ChangePackage> changes, boolean theirs) {

		for (ChangePackage changePackage : changes) {

			List<AbstractOperation> filteredOperations = filterOperations(changePackage, changePackage.getOperations(),
				theirs);

			changePackage.getOperations().clear();
			changePackage.getOperations().addAll(filteredOperations);
		}

		return changes;
	}

	private List<AbstractOperation> filterOperations(EObject key, List<AbstractOperation> operations, boolean theirs) {

		List<AbstractOperation> acceptedOperations = new LinkedList<AbstractOperation>();
		Map<EObject, List<AbstractOperation>> filteredOperations = theirs ? getTheirFilteredOperations()
			: getMyFilteredOperations();
		filteredOperations.put(key, new ArrayList<AbstractOperation>());

		for (AbstractOperation op : operations) {

			if (op instanceof CompositeOperation) {
				CompositeOperation comp = (CompositeOperation) op;
				List<AbstractOperation> newOperations = filterOperations(comp, comp.getSubOperations(), theirs);
				comp.getSubOperations().clear();
				comp.getSubOperations().addAll(newOperations);
				acceptedOperations.add(comp);
				filteredOperations.get(key).add(null);
			} else {
				ModelElementId modelElementId = op.getModelElementId();
				EObject eObject = resolveId(modelElementId);

				if (eObject == null && op instanceof CreateDeleteOperation) {
					CreateDeleteOperation createDeleteOp = (CreateDeleteOperation) op;
					eObject = createDeleteOp.getModelElement();
				}

				if (eObject == null) {
					filteredOperations.get(key).add(op);
					continue;
				}

				if (!ignoredClasses.contains(eObject.getClass().getCanonicalName())) {
					acceptedOperations.add(op);
					filteredOperations.get(key).add(null);
				} else {
					filteredOperations.get(key).add(op);
				}
			}
		}

		return acceptedOperations;
	}

	private EObject resolveId(ModelElementId modelElementId) {
		EObject modelElement = getProjectSpace().getProject().getIdToEObjectMapping().get(modelElementId.getId());
		return modelElement;
	}

	private Map<EObject, List<AbstractOperation>> getTheirFilteredOperations() {
		return theirFilteredOperations;
	}

	private Map<EObject, List<AbstractOperation>> getMyFilteredOperations() {
		return myFilteredOperations;
	}
}