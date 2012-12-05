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
package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isAttribute;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isComposite;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isCompositeRef;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isDelete;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isDiagramLayout;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isMultiAtt;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isMultiAttMove;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isMultiAttSet;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isMultiRef;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isMultiRefSet;
import static org.eclipse.emf.emfstore.server.model.versioning.operations.util.OperationUtil.isSingleRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.AttributeConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.CompositeConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.DeletionConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.DiagramLayoutConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeMoveConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeMoveSetConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeSetConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeSetSetConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetSetConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetSingleConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSingleConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.ReferenceConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.SingleReferenceConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.impl.ChangePackageImpl;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiAttributeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceOperation;

/**
 * DecisionManager is the controller for the merge dialog and therefore it's
 * main component. It calculates the conflicts from incoming changes and can
 * execute resolved conflicts.
 * 
 * @author wesendon
 */
public class DecisionManager {

	private final Project project;
	private final List<ChangePackage> myChangePackages;
	private final List<ChangePackage> theirChangePackages;
	private ConflictDetector conflictDetector;

	private ArrayList<Conflict> conflicts;
	private Set<AbstractOperation> notInvolvedInConflict;
	private ArrayList<AbstractOperation> acceptedMine;
	private ArrayList<AbstractOperation> rejectedTheirs;
	private final PrimaryVersionSpec baseVersion;
	private final PrimaryVersionSpec targetVersion;
	private List<ConflictHandler> conflictHandler;
	private final boolean isBranchMerge;
	private ChangeConflictException conflictException;
	// TODO: extract mapping to its own class
	private Map<ModelElementId, EObject> idToEObjectMapping;

	/**
	 * Default constructor.
	 * 
	 * @param project
	 *            the related project
	 * @param myChangePackages
	 *            my changes
	 * @param theirChangePackages
	 *            incoming changes
	 * @param baseVersion
	 *            baseversion
	 * @param targetVersion
	 *            new target version
	 * @param isBranchMerge
	 *            allows to specify whether two branches are merged, opposed to
	 *            changes from the same branch. Has an effect on the wording of
	 *            conflictions
	 * @param conflictException a conflict exception with preliminary results
	 */
	public DecisionManager(Project project, List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages, PrimaryVersionSpec baseVersion, PrimaryVersionSpec targetVersion,
		boolean isBranchMerge, ChangeConflictException conflictException) {
		this.project = project;
		this.myChangePackages = myChangePackages;
		this.theirChangePackages = theirChangePackages;
		this.baseVersion = baseVersion;
		this.targetVersion = targetVersion;
		this.isBranchMerge = isBranchMerge;
		this.conflictException = conflictException;
		this.conflictHandler = initConflictHandlers();
		this.conflictDetector = new ConflictDetector();
		this.idToEObjectMapping = new LinkedHashMap<ModelElementId, EObject>();
		initMapping(myChangePackages, theirChangePackages);
		init();
	}

	private void initMapping(List<ChangePackage> myChangePackages, List<ChangePackage> theirChangePackages) {

		for (ChangePackage changePackage : myChangePackages) {
			addToIdToEObjectMapping(changePackage.getCopyOfOperations());
		}

		for (ChangePackage changePackage : theirChangePackages) {
			addToIdToEObjectMapping(changePackage.getCopyOfOperations());
		}
	}

	private void addToIdToEObjectMapping(List<AbstractOperation> operations) {
		for (AbstractOperation op : operations) {

			if (op instanceof CompositeOperation) {
				addToIdToEObjectMapping(((CompositeOperation) op).getSubOperations());
				continue;
			}

			if (!(op instanceof CreateDeleteOperation)) {
				continue;
			}

			CreateDeleteOperation createDeleteOperation = (CreateDeleteOperation) op;
			for (Map.Entry<EObject, ModelElementId> entry : createDeleteOperation.getEObjectToIdMap()) {
				idToEObjectMapping.put(entry.getValue(), entry.getKey());
			}
		}
	}

	private List<ConflictHandler> initConflictHandlers() {
		ArrayList<ConflictHandler> result = new ArrayList<ConflictHandler>();
		for (ExtensionElement element : new ExtensionPoint("org.eclipse.emf.emfstore.client.merge.conflictHandler")
			.getExtensionElements()) {
			ConflictHandler handler = element.getClass("class", ConflictHandler.class);
			if (handler != null) {
				result.add(handler);
			}
		}
		return result;
	}

	private void init() {
		// flatten operations

		acceptedMine = new ArrayList<AbstractOperation>();
		rejectedTheirs = new ArrayList<AbstractOperation>();

		conflicts = new ArrayList<Conflict>();
		notInvolvedInConflict = new LinkedHashSet<AbstractOperation>();

		Set<ConflictBucketCandidate> conflictBucketCandidates;
		if (conflictException != null) {
			conflictBucketCandidates = conflictException.getConflictBucketCandidates();
		} else {
			conflictBucketCandidates = new ConflictDetector().calculateConflictCandidateBuckets(myChangePackages,
				theirChangePackages);
		}
		Set<ConflictBucket> conflictBucketsSet = conflictDetector.calculateConflictBucketsFromConflictCandidateBuckets(
			conflictBucketCandidates, notInvolvedInConflict);

		createConflicts(conflictBucketsSet);
	}

	/**
	 * BEGIN FACTORY TODO EXTRACT FACTORY CLASS.
	 */

	// BEGIN COMPLEX CODE
	private void createConflicts(Set<ConflictBucket> ConflictBucket) {
		// Create Conflicts from ConflictBucket
		for (ConflictBucket conf : ConflictBucket) {

			AbstractOperation my = conf.getMyOperation();
			AbstractOperation their = conf.getTheirOperation();
			Conflict conflict = null;

			if (isDiagramLayout(my) && isDiagramLayout(their)) {
				conflict = createDiagramLayoutDecision(conf);

			} else if (isAttribute(my) && isAttribute(their)) {
				conflict = createAttributeAttributeDecision(conf);

			} else if (isSingleRef(my) && isSingleRef(their)) {
				conflict = createSingleSingleConflict(conf);

			} else if (isMultiRef(my) && isMultiRef(their)) {
				conflict = createMultiMultiConflict(conf);

			} else if ((isMultiRef(my) && isSingleRef(their)) || (isMultiRef(their) && isSingleRef(my))) {
				conflict = createMultiSingle(conf);

			} else if (isCompositeRef(my) && isCompositeRef(their)) {
				conflict = createReferenceConflict(conf);

			} else if ((isCompositeRef(my) && (isMultiRef(their) || isSingleRef(their)))
				|| ((isMultiRef(my) || isSingleRef(my)) && isCompositeRef(their))) {
				conflict = createReferenceCompVSSingleMulti(conf);

			} else if ((isMultiRef(my) && isMultiRefSet(their)) || (isMultiRef(their) && isMultiRefSet(my))) {
				conflict = createMultiRefMultiSet(conf);

			} else if (isMultiRefSet(my) && isMultiRefSet(their)) {
				conflict = createMultiRefSetSet(conf);

			} else if ((isMultiRefSet(my) && isSingleRef(their)) || (isMultiRefSet(their) && isSingleRef(my))) {
				conflict = createMultiSetSingle(conf);

			} else if (isMultiAtt(my) && isMultiAtt(their)) {
				conflict = createMultiAtt(conf);

			} else if ((isMultiAtt(my) && isMultiAttSet(their)) || (isMultiAtt(their) && isMultiAttSet(my))) {
				conflict = createMultiAttSet(conf);

			} else if ((isMultiAtt(my) && isMultiAttMove(their)) || (isMultiAtt(their) && isMultiAttMove(my))) {
				conflict = createMultiAttMove(conf);

			} else if ((isMultiAttSet(my) && isMultiAttMove(their)) || (isMultiAttSet(their) && isMultiAttMove(my))) {
				conflict = createMultiAttMoveSet(conf);

			} else if (isMultiAttSet(my) && isMultiAttSet(their)) {
				conflict = createMultiAttSetSet(conf);

			} else if (isComposite(my) || isComposite(their)) {
				conflict = createCompositeConflict(conf);

			} else if (isDelete(my) || isDelete(their)) {
				conflict = createDeleteOtherConflict(conf);
			}

			if (conflict != null) {
				conflict = notifyConflictHandlers(conflict);
				addConflict(conflict);
			} else {
				WorkspaceUtil.log(
					"A created conflict has been ignored (does not apply to any existing conflict rule).",
					IStatus.WARNING);
			}
		}
	}

	private Conflict notifyConflictHandlers(Conflict conflict) {
		// pass conflict through all handlers
		for (ConflictHandler handler : this.conflictHandler) {
			conflict = handler.handle(conflict, idToEObjectMapping);
		}
		return conflict;
	}

	private void addConflict(Conflict conflict) {
		if (conflict == null) {
			return;
		}
		conflicts.add(conflict);
	}

	// END COMPLEX CODE
	private Conflict createMultiRefMultiSet(ConflictBucket conf) {
		if (isMultiRef(conf.getMyOperation())) {
			return new MultiReferenceSetConflict(conf.getMyOperations(), conf.getTheirOperations(),
				conf.getMyOperation(), conf.getTheirOperation(), this, true);
		} else {
			return new MultiReferenceSetConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);
		}
	}

	private Conflict createMultiSetSingle(ConflictBucket conf) {
		if (isMultiRefSet(conf.getMyOperation())) {
			return new MultiReferenceSetSingleConflict(conf.getMyOperations(), conf.getTheirOperations(),
				conf.getMyOperation(), conf.getTheirOperation(), this, true);
		} else {
			return new MultiReferenceSetSingleConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);
		}
	}

	private Conflict createMultiSingle(ConflictBucket conf) {
		if (isMultiRef(conf.getMyOperation())) {
			return new MultiReferenceSingleConflict(conf.getMyOperations(), conf.getTheirOperations(),
				conf.getMyOperation(), conf.getTheirOperation(), this, true);
		} else {
			return new MultiReferenceSingleConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);
		}
	}

	private Conflict createMultiRefSetSet(ConflictBucket conf) {
		return new MultiReferenceSetSetConflict(conf.getMyOperations(), conf.getTheirOperations(),
			conf.getMyOperation(), conf.getTheirOperation(), this);
	}

	private Conflict createMultiAttSetSet(ConflictBucket conf) {
		return new MultiAttributeSetSetConflict(conf.getMyOperations(), conf.getTheirOperations(),
			conf.getMyOperation(), conf.getTheirOperation(), this);
	}

	private Conflict createMultiAtt(ConflictBucket conf) {
		if (((MultiAttributeOperation) conf.getMyOperation()).isAdd()) {
			return new MultiAttributeConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
				conf.getTheirOperation(), this, true);
		} else {
			return new MultiAttributeConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);

		}
	}

	private Conflict createMultiAttSet(ConflictBucket conf) {
		if (isMultiAtt(conf.getMyOperation())) {
			return new MultiAttributeSetConflict(conf.getMyOperations(), conf.getTheirOperations(),
				conf.getMyOperation(), conf.getTheirOperation(), this, true);
		} else {
			return new MultiAttributeSetConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);
		}
	}

	private Conflict createMultiAttMove(ConflictBucket conf) {
		if (isMultiAtt(conf.getMyOperation())) {
			return new MultiAttributeMoveConflict(conf.getMyOperations(), conf.getTheirOperations(),
				conf.getMyOperation(), conf.getTheirOperation(), this, true);
		} else {
			return new MultiAttributeMoveConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);
		}
	}

	private Conflict createMultiAttMoveSet(ConflictBucket conf) {
		if (isMultiAttSet(conf.getMyOperation())) {
			return new MultiAttributeMoveSetConflict(conf.getMyOperations(), conf.getTheirOperations(),
				conf.getMyOperation(), conf.getTheirOperation(), this, true);
		} else {
			return new MultiAttributeMoveSetConflict(conf.getTheirOperations(), conf.getMyOperations(),
				conf.getTheirOperation(), conf.getMyOperation(), this, false);
		}
	}

	private Conflict createReferenceCompVSSingleMulti(ConflictBucket conf) {
		if (isCompositeRef(conf.getMyOperation())) {
			return createRefFromSub(conf, ((CompositeOperation) conf.getMyOperation()).getSubOperations(),
				Arrays.asList(conf.getTheirOperation()));
		} else {
			return createRefFromSub(conf, Arrays.asList(conf.getMyOperation()),
				((CompositeOperation) conf.getTheirOperation()).getSubOperations());
		}
	}

	private Conflict createReferenceConflict(ConflictBucket conf) {
		EList<AbstractOperation> myOperations = ((CompositeOperation) conf.getMyOperation()).getSubOperations();
		EList<AbstractOperation> theirOperations = ((CompositeOperation) conf.getTheirOperation()).getSubOperations();

		return createRefFromSub(conf, myOperations, theirOperations);
	}

	private Conflict createRefFromSub(ConflictBucket conf, List<AbstractOperation> myOperations,
		List<AbstractOperation> theirOperations) {

		for (AbstractOperation myOp : myOperations) {
			for (AbstractOperation theirOp : theirOperations) {
				if (conflictDetector.doConflict(myOp, theirOp)) {
					if (isSingleRef(myOp)) {

						return new ReferenceConflict(createSingleSingleConflict(myOp, theirOp), conf.getMyOperations(),
							conf.getTheirOperations(), conf.getMyOperation(), conf.getTheirOperation());

					} else if (isMultiRef(myOp)) {

						return new ReferenceConflict(createMultiMultiConflict(myOp, theirOp), conf.getMyOperations(),
							conf.getTheirOperations(), conf.getMyOperation(), conf.getTheirOperation());

					} else {
						return null;
					}
				}
			}
		}
		return null;
	}

	private Conflict createAttributeAttributeDecision(ConflictBucket conf) {
		return new AttributeConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
			conf.getTheirOperation(), this);
	}

	private Conflict createDiagramLayoutDecision(ConflictBucket conf) {
		return new DiagramLayoutConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
			conf.getTheirOperation(), this);
	}

	private Conflict createSingleSingleConflict(ConflictBucket conf) {
		return new SingleReferenceConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
			conf.getTheirOperation(), this);
	}

	private Conflict createSingleSingleConflict(AbstractOperation my, AbstractOperation their) {
		return new SingleReferenceConflict(set(my), set(their), my, their, this);
	}

	private <T> Set<T> set(T object) {
		Set<T> set = new LinkedHashSet<T>();
		set.add(object);
		return set;
	}

	private Conflict createMultiMultiConflict(ConflictBucket conf) {
		if (((MultiReferenceOperation) conf.getMyOperation()).isAdd()) {
			return new MultiReferenceConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
				conf.getTheirOperation(), this, true);
		} else {
			return new MultiReferenceConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
				conf.getTheirOperation(), this, false);
		}
	}

	private Conflict createMultiMultiConflict(AbstractOperation my, AbstractOperation their) {
		if (((MultiReferenceOperation) my).isAdd()) {
			return new MultiReferenceConflict(set(my), set(their), my, their, this, true);
		} else {
			return new MultiReferenceConflict(set(their), set(my), their, my, this, false);
		}
	}

	private Conflict createDeleteOtherConflict(ConflictBucket conf) {
		if (isDelete(conf.getMyOperation())) {
			return new DeletionConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
				conf.getTheirOperation(), true, this);
		} else {
			return new DeletionConflict(conf.getTheirOperations(), conf.getMyOperations(), conf.getTheirOperation(),
				conf.getMyOperation(), false, this);
		}
	}

	private Conflict createCompositeConflict(ConflictBucket conf) {
		if (isComposite(conf.getMyOperation())) {
			return new CompositeConflict(conf.getMyOperations(), conf.getTheirOperations(), conf.getMyOperation(),
				conf.getTheirOperation(), this, true);
		} else {
			return new CompositeConflict(conf.getTheirOperations(), conf.getMyOperations(), conf.getTheirOperation(),
				conf.getMyOperation(), this, false);
		}
	}

	/**
	 * FACTORY END
	 */

	/**
	 * Returns the conflicts.
	 * 
	 * @return list of conflicts.
	 */
	public ArrayList<Conflict> getConflicts() {
		return conflicts;
	}

	/**
	 * Checks whether all conflicts are resolved.
	 * 
	 * @return true if all are resolved
	 */
	public boolean isResolved() {
		boolean isResolved = true;
		for (Conflict conflict : conflicts) {
			isResolved = isResolved && conflict.isResolved();
		}
		return isResolved;
	}

	/**
	 * Get "my" accepted operations. This list will be empty, if {@link #calcResult()} hasn't been called before.
	 * 
	 * @return list of operations
	 */
	public List<AbstractOperation> getAcceptedMine() {
		return acceptedMine;
	}

	/**
	 * Get "their" accepted operations. This list will be empty, if {@link #calcResult()} hasn't been called before.
	 * 
	 * @return list of operations
	 */
	public List<AbstractOperation> getRejectedTheirs() {
		return rejectedTheirs;
	}

	/**
	 * If all conflicts are resolved this method will generate the resulting
	 * operations from the conflicts. Then call {@link #getAcceptedMine()} and {@link #getRejectedTheirs()}.
	 */
	// BEGIN COMPLEX CODE
	public void calcResult() {
		if (!isResolved()) {
			return;
		}

		Set<AbstractOperation> accceptedMineSet = new LinkedHashSet<AbstractOperation>();
		Set<AbstractOperation> rejectedTheirsSet = new LinkedHashSet<AbstractOperation>();

		for (Conflict conflict : conflicts) {
			accceptedMineSet.addAll(conflict.getAcceptedMine());
			rejectedTheirsSet.addAll(conflict.getRejectedTheirs());
		}

		// collect my accepted operations
		for (ChangePackage myChangePackage : myChangePackages) {
			for (AbstractOperation myOp : myChangePackage.getOperations()) {
				if (notInvolvedInConflict.contains(myOp)) {
					acceptedMine.add(myOp);
				} else if (accceptedMineSet.contains(myOp)) {
					acceptedMine.add(myOp);
				}
				accceptedMineSet.remove(myOp);
			}
		}

		// add all remaining operations in acceptedMineSet (they have been generated during merge)
		acceptedMine.addAll(accceptedMineSet);

		for (ChangePackage theirCP : theirChangePackages) {
			for (AbstractOperation theirOp : theirCP.getOperations()) {
				if (rejectedTheirsSet.contains(theirOp)) {
					rejectedTheirs.add(theirOp);
				}
			}
		}
	}

	// END COMPLEX CODE

	/**
	 * Returns the conflictdetector.
	 * 
	 * @return conflictdetector
	 */
	public ConflictDetector getConflictDetector() {
		return conflictDetector;
	}

	/**
	 * Flat whether branches are merged opposed to versions on the same branch.
	 * 
	 * @return true, if branches
	 */
	public boolean isBranchMerge() {
		return isBranchMerge;
	}

	/**
	 * Get the Name of an model element by modelelement id.
	 * 
	 * @param modelElementId
	 *            id of element
	 * @return name as string
	 */
	public String getModelElementName(ModelElementId modelElementId) {
		return getModelElementName(getModelElement(modelElementId));
	}

	/**
	 * Get the Name of an model element.
	 * 
	 * @param modelElement
	 *            element
	 * @return name as string
	 */
	public String getModelElementName(EObject modelElement) {
		return DecisionUtil.getModelElementName(modelElement);
	}

	/**
	 * Returns the modelelement. Therefore the project as well as creation and
	 * deletion operations are searched.
	 * 
	 * @param modelElementId
	 *            id of element.
	 * @return modelelement
	 */
	// TODO: SLOW!
	public EObject getModelElement(ModelElementId modelElementId) {
		EObject modelElement = project.getModelElement(modelElementId);
		if (modelElement == null) {
			for (ChangePackage cp : myChangePackages) {
				modelElement = searchForCreatedME(modelElementId, cp.getOperations());
				if (modelElement != null) {
					break;
				}
			}
			if (modelElement == null) {
				for (ChangePackage cp : theirChangePackages) {
					modelElement = searchForCreatedME(modelElementId, cp.getOperations());
					if (modelElement != null) {
						break;
					}
				}
			}
		}
		return modelElement;
	}

	private EObject searchForCreatedME(ModelElementId modelElementId, List<AbstractOperation> operations) {
		for (AbstractOperation operation : operations) {
			EObject result = null;
			if (operation instanceof CreateDeleteOperation) {
				result = searchCreateAndDelete((CreateDeleteOperation) operation, modelElementId);

			} else if (operation instanceof CompositeOperation) {
				EList<AbstractOperation> subOperations = ((CompositeOperation) operation).getSubOperations();
				result = searchForCreatedME(modelElementId, subOperations);
			} else {
				continue;
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	private EObject searchCreateAndDelete(CreateDeleteOperation cdo, ModelElementId modelElementId) {
		EObject modelElement = cdo.getModelElement();
		if (modelElement == null) {
			return null;
		}
		Set<EObject> containedModelElements = ModelUtil.getAllContainedModelElements(modelElement, false);
		containedModelElements.add(modelElement);

		for (EObject child : containedModelElements) {
			ModelElementId childId = ModelUtil.clone(cdo.getEObjectToIdMap().get(child));
			if (childId != null && childId.equals(modelElementId)) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Returns the name of the author for a operation in list of their
	 * operations.
	 * 
	 * @param theirOperation
	 *            operation
	 * @return name as string or ""
	 */
	public String getAuthorForOperation(AbstractOperation theirOperation) {
		for (ChangePackage cp : theirChangePackages) {
			for (AbstractOperation op : cp.getOperations()) {
				List<AbstractOperation> tmpList = new ArrayList<AbstractOperation>();
				if (op instanceof CompositeOperation) {
					tmpList.add(op);
					tmpList.addAll(((CompositeOperation) op).getSubOperations());
				} else {
					tmpList.add(op);
				}
				for (AbstractOperation ao : tmpList) {
					if (ao.equals(theirOperation)) {
						LogMessage log = cp.getLogMessage();
						if (log == null) {
							return "";
						}
						return (log.getAuthor() == null) ? "" : log.getAuthor();

					}
				}
			}
		}
		return "";
	}

	/**
	 * Return the related project.
	 * 
	 * @return project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Returns the baseVersion of the project which is updating.
	 * 
	 * @return version
	 */
	public PrimaryVersionSpec getBaseVersion() {
		return baseVersion;
	}

	/**
	 * Returns the targetVersion of the update which caused merging.
	 * 
	 * @return version
	 */
	public PrimaryVersionSpec getTargetVersion() {
		return targetVersion;
	}

	/**
	 * Gives Access to internal values. Use with care.
	 */
	// BEGIN COMPLEX CODE
	public DecisionManager.Internal Internal = this.new Internal();

	// END COMPLEX CODE

	/**
	 * This class allows access to internal values. Use with care.
	 * 
	 * @author wesendon
	 */
	public class Internal {
		/**
		 * My CP.
		 * 
		 * @return list of cp
		 */
		public List<ChangePackage> getMyChangePackages() {
			return myChangePackages;
		}

		/**
		 * Their CP.
		 * 
		 * @return list of cp
		 */
		public List<ChangePackage> getTheirChangePackages() {
			return theirChangePackages;
		}
	}

	private Integer myLeafOperationCount;

	/**
	 * Count my leaf operations.
	 * 
	 * @return the number of leaf operations
	 */
	public int countMyLeafOperations() {
		if (myLeafOperationCount == null) {
			countConflicts();
		}
		return myLeafOperationCount;
	}

	private void countConflicts() {
		int myCount = 0;
		int myLeafCount = 0;
		int theirCount = 0;
		int theirLeafCount = 0;
		for (Conflict conflict : conflicts) {
			myCount += conflict.getLeftOperations().size();
			myLeafCount += ChangePackageImpl.countLeafOperations(conflict.getMyOperations());
			theirCount += conflict.getRightOperations().size();
			theirLeafCount += ChangePackageImpl.countLeafOperations(conflict.getTheirOperations());
		}
		myOperationCount = myCount;
		myLeafOperationCount = myLeafCount;
		theirOperationCount = theirCount;
		theirLeafOperationCount = theirLeafCount;

	}

	private Integer theirLeafOperationCount;

	/**
	 * Count their leaf operations.
	 * 
	 * @return the number of leaf operations
	 */
	public int countTheirLeafOperations() {
		if (theirLeafOperationCount == null) {
			countConflicts();
		}
		return theirLeafOperationCount;
	}

	private Integer myOperationCount;

	/**
	 * Count my leaf operations.
	 * 
	 * @return the number of leaf operations
	 */
	public int countMyOperations() {
		if (myOperationCount == null) {
			countConflicts();
		}
		return myOperationCount;
	}

	private Integer theirOperationCount;

	/**
	 * Count their leaf operations.
	 * 
	 * @return the number of leaf operations
	 */
	public int countTheirOperations() {
		if (theirOperationCount == null) {
			countConflicts();
		}
		return theirOperationCount;
	}

}