/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts;

// BEGIN COMPLEX CODE
//
// WORK IN PROGRESS !
//

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
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;

public class MultiReferenceSetSetConflict extends Conflict {

	private boolean containmentConflict;

	public MultiReferenceSetSetConflict(Set<AbstractOperation> opsA, Set<AbstractOperation> opsB,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager) {
		super(opsA, opsB, leftOperation, rightOperation, decisionManager, true, false);
		// is this rule enough?
		containmentConflict = getMyOperation().getModelElementId().equals(getTheirOperation().getModelElementId());
		init();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (!containmentConflict) {
			description.setDescription(DecisionUtil.getDescription("multireferencesetsetconflict.set",
				getDecisionManager().isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription("multireferencesetsetconflict.move",
				getDecisionManager().isBranchMerge()));
		}

		description.add("value", getMyOperation(MultiReferenceSetOperation.class).getNewValue());
		description.add("ovalue", getTheirOperation(MultiReferenceSetOperation.class).getNewValue());
		description.add("othercontainer", getTheirOperation().getModelElementId());
		description.setImage("multiref.gif");
		return description;
	}

	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		ConflictOption myOption = new ConflictOption("", OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		ConflictOption theirOption = new ConflictOption("", OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		if (!containmentConflict) {
			myOption.setOptionLabel(DecisionUtil.getClassAndName(getDecisionManager().getModelElement(
				getMyOperation(MultiReferenceSetOperation.class).getNewValue())));
			theirOption.setOptionLabel(DecisionUtil.getClassAndName(getDecisionManager().getModelElement(
				getTheirOperation(MultiReferenceSetOperation.class).getNewValue())));
		} else {
			EObject target = getDecisionManager().getModelElement(
				getMyOperation(MultiReferenceSetOperation.class).getNewValue());

			myOption.setOptionLabel("Move " + getClassAndName(target) + "to"
				+ getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId())));
			theirOption.setOptionLabel("Move " + getClassAndName(target) + " to"
				+ getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId())));
		}

		options.add(myOption);
		options.add(theirOption);
	}
}
