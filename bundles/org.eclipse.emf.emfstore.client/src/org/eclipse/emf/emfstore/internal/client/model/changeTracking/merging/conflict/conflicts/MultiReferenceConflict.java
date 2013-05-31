/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
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
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;

/**
 * Conflict between two {@link MultiReferenceConflict}.
 * 
 * @author wesendon
 */
public class MultiReferenceConflict extends Conflict {

	private boolean containmentConflict;

	/**
	 * Default constructor.
	 * 
	 * @param addingOperation list of operations, with leading adding multiref operation
	 * @param removingOperation list of operations, with leading removing multiref operation
	 * @param leftOperation the operation representing all left operations
	 * @param rightOperation the operation representing all right operations
	 * @param decisionManager decisionmanager
	 * @param meAdding true, if merging user has adding multiref
	 */
	public MultiReferenceConflict(Set<AbstractOperation> addingOperation, Set<AbstractOperation> removingOperation,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager,
		boolean meAdding) {
		super(addingOperation, removingOperation, leftOperation, rightOperation, decisionManager, meAdding, false);
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
