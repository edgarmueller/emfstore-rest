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
package org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts;

// BEGIN COMPLEX CODE
//
// WORK IN PROGRESS !
//
import static org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil.getClassAndName;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceSetOperation;

public class MultiReferenceSetConflict extends Conflict {

	private boolean containmentConflict;

	/**
	 * Default constructor.
	 * 
	 * @param multiRef multireference in conflict
	 * @param multiRefSet multireference set in conflict
	 * @param decisionManager decisionmanager
	 * @param myMultiRef is my multireference
	 */
	public MultiReferenceSetConflict(Set<AbstractOperation> multiRef, Set<AbstractOperation> multiRefSet,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager,
		boolean myMultiRef) {
		super(multiRef, multiRefSet, leftOperation, rightOperation, decisionManager, myMultiRef, false);
		containmentConflict = ((MultiReferenceOperation) getLeftOperation()).isAdd()
			&& !getLeftOperation().getModelElementId().equals(getRightOperation().getModelElementId());
		init();
	}

	/**
	 * LEFT MultiRef, Right MultiRefSet
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {

		if (containmentConflict) {
			description.setDescription(DecisionUtil.getDescription("multireferencesetconflict.containment",
				getDecisionManager().isBranchMerge()));
		} else if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription("multireferencesetconflict.my", getDecisionManager()
				.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription("multireferencesetconflict.their",
				getDecisionManager().isBranchMerge()));
		}

		description.add("target", isLeftMy() ? getMyOperation(MultiReferenceOperation.class)
			.getReferencedModelElements().get(0) : getMyOperation(MultiReferenceSetOperation.class).getNewValue());
		description.add("othercontainer", getLeftOperation().getModelElementId());
		description.setImage("multiref.gif");

		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.ui.dialogs.merge.conflict.Conflict#initConflictOptions(java.util.List)
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		ConflictOption myOption = new ConflictOption("", OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		ConflictOption theirOption = new ConflictOption("", OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		if (containmentConflict) {
			EObject target = getDecisionManager().getModelElement(
				((MultiReferenceOperation) getLeftOperation()).getReferencedModelElements().get(0));

			myOption.setOptionLabel("Move " + getClassAndName(target) + "to"
				+ getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId())));
			theirOption.setOptionLabel("Move " + getClassAndName(target) + " to"
				+ getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId())));

		} else if (isLeftMy()) {
			EObject target = getDecisionManager().getModelElement(
				getMyOperation(MultiReferenceOperation.class).getReferencedModelElements().get(0));

			myOption.setOptionLabel("Remove " + DecisionUtil.getClassAndName(target));
			theirOption.setOptionLabel("Set " + DecisionUtil.getClassAndName(target));
		} else {
			EObject target = getDecisionManager().getModelElement(
				getTheirOperation(MultiReferenceOperation.class).getReferencedModelElements().get(0));

			myOption.setOptionLabel("Set " + DecisionUtil.getClassAndName(target));
			theirOption.setOptionLabel("Remove " + DecisionUtil.getClassAndName(target));
		}

		options.add(myOption);
		options.add(theirOption);

	}
}
