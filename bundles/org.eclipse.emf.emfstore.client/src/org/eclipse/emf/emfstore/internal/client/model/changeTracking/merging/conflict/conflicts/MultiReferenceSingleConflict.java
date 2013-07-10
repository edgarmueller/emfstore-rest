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
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;

/**
 * @author wesendon
 */
public class MultiReferenceSingleConflict extends Conflict {

	/**
	 * Default constructor.
	 * 
	 * @param leftOperations multi ref
	 * @param rightOperations single ref
	 * @param leftOperation the operation representing all left operations
	 * @param rightOperation the operation representing all right operations
	 * @param decisionManager decision maanger
	 * @param multiLeft multi is lef
	 */
	public MultiReferenceSingleConflict(ConflictBucket conflictBucket, DecisionManager decisionManager,
		boolean multiLeft) {
		super(conflictBucket, decisionManager, multiLeft, true);
	}

	/**
	 * LEFT: MultiReference, RIGHT: SingleReference
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription(org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.ConflictDescription)
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		description.setDescription(DecisionUtil.getDescription("multireferencesingleconflict", getDecisionManager()
			.isBranchMerge()));

		description.add("target", ((MultiReferenceOperation) getLeftOperation()).getReferencedModelElements().get(0));
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
			((MultiReferenceOperation) getLeftOperation()).getReferencedModelElements().get(0));

		myOption.setOptionLabel("Move " + getClassAndName(target) + "to"
			+ getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId())));
		theirOption.setOptionLabel("Move " + getClassAndName(target) + " to"
			+ getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId())));

		options.add(myOption);
		options.add(theirOption);
	}

}
