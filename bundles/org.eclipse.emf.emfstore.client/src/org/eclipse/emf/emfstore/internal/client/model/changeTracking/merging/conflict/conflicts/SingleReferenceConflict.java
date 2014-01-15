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
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.VisualConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnkownFeatureException;

/**
 * Conflict for two {@link SingleReferenceOperation}.
 * 
 * @author wesendon
 */
public class SingleReferenceConflict extends VisualConflict {

	private static final String THEIRVALUE_KEY = "theirvalue"; //$NON-NLS-1$
	private static final String SINGLEREF_GIF = "singleref.gif"; //$NON-NLS-1$
	private static final String MYVALUE_KEY = "myvalue"; //$NON-NLS-1$
	private static final String SINGLE_REFERENCECONFLICT_SET_KEY = "singlereferenceconflict.set"; //$NON-NLS-1$
	private static final String SINGLE_REFERENCECONFLICT_MOVE_KEY = "singlereferenceconflict.move"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 * 
	 * @param conflictBucket the conflict
	 * @param decisionManager {@link DecisionManager}
	 */
	public SingleReferenceConflict(ConflictBucket conflictBucket, DecisionManager decisionManager) {
		super(conflictBucket, decisionManager);
	}

	/**
	 * Construct conflict from designated left and right operation.
	 * 
	 * 
	 * @param conflictBucket the conflict
	 * @param leftOperation the left operation
	 * @param rightOperation the right operation
	 * @param decisionManager decisionmanager
	 */
	public SingleReferenceConflict(ConflictBucket conflictBucket, AbstractOperation leftOperation,
		AbstractOperation rightOperation,
		DecisionManager decisionManager) {
		super(conflictBucket, leftOperation, rightOperation, decisionManager, true, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (isContainmentFeature()) {
			description.setDescription(DecisionUtil.getDescription(SINGLE_REFERENCECONFLICT_MOVE_KEY,
				getDecisionManager()
					.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription(SINGLE_REFERENCECONFLICT_SET_KEY,
				getDecisionManager()
					.isBranchMerge()));
		}

		final EObject myNewValue = getDecisionManager().getModelElement(
			getMyOperation(SingleReferenceOperation.class).getNewValue());
		description.add(MYVALUE_KEY, myNewValue == null ? Messages.SingleReferenceConflict_Unset : myNewValue);
		final EObject theirNewValue = getDecisionManager().getModelElement(
			getTheirOperation(SingleReferenceOperation.class).getNewValue());
		description.add(THEIRVALUE_KEY, theirNewValue == null ? "(unset)" : theirNewValue); //$NON-NLS-1$

		description.setImage(SINGLEREF_GIF);

		return description;
	}

	private boolean isContainmentFeature() {
		final EObject modelElement = getDecisionManager().getModelElement(getMyOperation().getModelElementId());
		if (modelElement == null) {
			return false;
		}
		try {
			if (((EReference) getMyOperation(SingleReferenceOperation.class).getFeature(modelElement)).isContainer()) {
				return true;
			}
		} catch (final UnkownFeatureException e) {
			// ignore
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {

		// My Option
		final ModelElementId newValue = getMyOperation(SingleReferenceOperation.class).getNewValue();
		final ConflictOption myOption = new ConflictOption(newValue == null ? Messages.SingleReferenceConflict_Unset
			: DecisionUtil.getClassAndName(getDecisionManager().getModelElement(newValue)), OptionType.MyOperation);
		myOption.addOperations(getMyOperations());

		// Their Option
		final ModelElementId theirNewValue = getTheirOperation(SingleReferenceOperation.class).getNewValue();
		final ConflictOption theirOption = new ConflictOption(DecisionUtil.getLabel(
			DecisionUtil.getClassAndName(getDecisionManager().getModelElement(theirNewValue)),
			Messages.SingleReferenceConflict_Unset),
			OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		options.add(myOption);
		options.add(theirOption);
	}
}
