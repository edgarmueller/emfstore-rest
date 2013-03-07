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

import static org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil.getClassAndName;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;

/**
 * @author wesendon
 */
public class MultiReferenceSetSingleConflict extends Conflict {

	/**
	 * Default constructor.
	 * 
	 * @param leftOperations multi set ref
	 * @param rightOperations single ref
	 * @param leftOperation the operation representing all left operations
	 * @param rightOperation the operation representing all right operations
	 * @param decisionManager decisionmanager
	 * @param setLeft multi set ref is left
	 */
	public MultiReferenceSetSingleConflict(Set<AbstractOperation> leftOperations,
		Set<AbstractOperation> rightOperations, AbstractOperation leftOperation, AbstractOperation rightOperation,
		DecisionManager decisionManager, boolean setLeft) {
		super(leftOperations, rightOperations, leftOperation, rightOperation, decisionManager, setLeft, true);
	}

	/**
	 * LEFT: MultiReferenceSet, RIGHT: SingleReference
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription(org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.ConflictDescription)
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		description.setDescription(DecisionUtil.getDescription("multireferencesetsingleconflict", getDecisionManager()
			.isBranchMerge()));

		description.add("target", ((SingleReferenceOperation) getRightOperation()).getNewValue());
		description.add("othercontainer", getTheirOperation().getModelElementId());

		description.setImage("multiref.gif");
		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.Conflict#initConflictOptions(java.util.List)
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		ConflictOption myOption = new ConflictOption("", OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		ConflictOption theirOption = new ConflictOption("", OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		EObject target = getDecisionManager().getModelElement(
			((SingleReferenceOperation) getRightOperation()).getNewValue());

		myOption.setOptionLabel("Move " + getClassAndName(target) + "to"
			+ getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId())));
		theirOption.setOptionLabel("Move " + getClassAndName(target) + " to"
			+ getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId())));

		options.add(myOption);
		options.add(theirOption);
	}

}