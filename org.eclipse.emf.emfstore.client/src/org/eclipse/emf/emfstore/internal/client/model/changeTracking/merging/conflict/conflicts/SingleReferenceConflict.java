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
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.UnkownFeatureException;

/**
 * Conflict for two {@link SingleReferenceOperation}.
 * 
 * @author wesendon
 */
public class SingleReferenceConflict extends Conflict {

	/**
	 * Default constructor.
	 * 
	 * @param myOperations list of my operations
	 * @param theirOperations list of their operations
	 * @param leftOperation the operation representing all left operations
	 * @param rightOperation the operation representing all right operations
	 * @param decisionManager decisionmanager
	 */
	public SingleReferenceConflict(Set<AbstractOperation> myOperations, Set<AbstractOperation> theirOperations,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager) {
		super(myOperations, theirOperations, leftOperation, rightOperation, decisionManager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (isContainmentFeature()) {
			description.setDescription(DecisionUtil.getDescription("singlereferenceconflict.move", getDecisionManager()
				.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription("singlereferenceconflict.set", getDecisionManager()
				.isBranchMerge()));
		}

		EObject myNewValue = getDecisionManager().getModelElement(
			getMyOperation(SingleReferenceOperation.class).getNewValue());
		description.add("myvalue", (myNewValue == null) ? "(unset)" : myNewValue);
		EObject theirNewValue = getDecisionManager().getModelElement(
			getTheirOperation(SingleReferenceOperation.class).getNewValue());
		description.add("theirvalue", (theirNewValue == null) ? "(unset)" : theirNewValue);

		description.setImage("singleref.gif");

		return description;
	}

	private boolean isContainmentFeature() {
		EObject modelElement = getDecisionManager().getModelElement(getMyOperation().getModelElementId());
		if (modelElement == null) {
			return false;
		}
		try {
			if (((EReference) getMyOperation(SingleReferenceOperation.class).getFeature(modelElement)).isContainer()) {
				return true;
			}
		} catch (UnkownFeatureException e) {
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {

		// My Option
		ModelElementId newValue = getMyOperation(SingleReferenceOperation.class).getNewValue();
		ConflictOption myOption = new ConflictOption((newValue == null) ? "(unset)"
			: DecisionUtil.getClassAndName(getDecisionManager().getModelElement(newValue)), OptionType.MyOperation);
		myOption.addOperations(getMyOperations());

		// Their Option
		ModelElementId theirNewValue = getTheirOperation(SingleReferenceOperation.class).getNewValue();
		ConflictOption theirOption = new ConflictOption(DecisionUtil.getLabel(
			DecisionUtil.getClassAndName(getDecisionManager().getModelElement(theirNewValue)), "(unset)"),
			OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		options.add(myOption);
		options.add(theirOption);
	}
}