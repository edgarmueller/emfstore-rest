/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts;

import static org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil.getClassAndName;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.VisualConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;

/**
 * Conflict between two {@link MultiReferenceConflict}.
 * 
 * @author wesendon
 */
public class MultiReferenceConflict extends VisualConflict {

	private boolean containmentConflict;

	/**
	 * Default constructor.
	 * 
	 * @param conflictBucket the conflict
	 * @param decisionManager decisionmanager
	 * @param meAdding true, if merging user has adding multiref
	 */
	public MultiReferenceConflict(ConflictBucket conflictBucket, DecisionManager decisionManager,
		boolean meAdding) {
		super(conflictBucket, decisionManager, meAdding, false);
		containmentConflict = getMyOperation(MultiReferenceOperation.class).isAdd()
			&& getTheirOperation(MultiReferenceOperation.class).isAdd();
		init();
	}

	/**
	 * Construct conflict from designated left and right operation.
	 * 
	 * 
	 * @param conflictBucket the conflict
	 * @param leftOperation the left operation
	 * @param rightOperation the right operation
	 * @param decisionManager decisionmanager
	 * @param meAdding true, if merging user has adding multiref
	 */
	public MultiReferenceConflict(ConflictBucket conflictBucket, AbstractOperation leftOperation,
		AbstractOperation rightOperation,
		DecisionManager decisionManager,
		boolean meAdding) {
		super(conflictBucket, leftOperation, rightOperation, decisionManager, meAdding, false);
		containmentConflict = getMyOperation(MultiReferenceOperation.class).isAdd()
			&& getTheirOperation(MultiReferenceOperation.class).isAdd();
		init();
	}

	/**
	 * LEFT: Adding RIGHT: Removing
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {

		if (containmentConflict) {
			description.setDescription(DecisionUtil.getDescription("multireferenceconflict.containment",
				getDecisionManager().isBranchMerge()));
		} else if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription("multireferenceconflict.my", getDecisionManager()
				.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription("multireferenceconflict.their", getDecisionManager()
				.isBranchMerge()));
		}
		description.add("target", getMyOperation(MultiReferenceOperation.class).getReferencedModelElements().get(0));
		description.add("othercontainer", getTheirOperation(MultiReferenceOperation.class).getModelElementId());

		description.setImage("multiref.gif");
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		ConflictOption myOption = new ConflictOption("", OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		ConflictOption theirOption = new ConflictOption("", OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		EObject target = getDecisionManager().getModelElement(
			getMyOperation(MultiReferenceOperation.class).getReferencedModelElements().get(0));

		if (containmentConflict) {
			myOption.setOptionLabel("Move " + getClassAndName(target) + "to"
				+ getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId())));
			theirOption.setOptionLabel("Move " + getClassAndName(target) + " to"
				+ getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId())));
		} else {
			myOption.setOptionLabel((isLeftMy()) ? "Add" : "Remove" + " " + getClassAndName(target));
			theirOption.setOptionLabel((!isLeftMy()) ? "Add" : "Remove" + " " + getClassAndName(target));
		}

		options.add(myOption);
		options.add(theirOption);
	}
}
