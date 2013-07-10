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

import java.util.List;

import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictContext;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil;

/**
 * Conflict {@link CompositeOperation} involved.
 * 
 * @author wesendon
 */
public class CompositeConflict extends Conflict {

	/**
	 * Default constructor.
	 * 
	 * @param composite list of operations, with leading conflicting {@link CompositeOperation}
	 * @param other list operations which conflict with composite
	 * @param leftOperation the operation representing all left operations
	 * @param rightOperation the operation representing all right operations
	 * @param decisionManager decisionmanager
	 * @param meCausing true, if composite caused by merging user
	 */
	public CompositeConflict(ConflictBucket conflictBucket, DecisionManager decisionManager,
		boolean meCausing) {
		super(conflictBucket, decisionManager, meCausing, false);
		init();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictContext initConflictContext() {
		return new ConflictContext(getDecisionManager(), getLeftOperation(), getTheirOperation());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (OperationUtil.isComposite(getMyOperation()) && OperationUtil.isComposite(getTheirOperation())) {
			description.setDescription(DecisionUtil.getDescription("compositeconflict.both", getDecisionManager()
				.isBranchMerge()));
			description.add("localcompdescription", getLeftOperation());
			description.add("incomingcompdescription", getRightOperation());
		} else if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription("compositeconflict.my", getDecisionManager()
				.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription("compositeconflict.their", getDecisionManager()
				.isBranchMerge()));
		}

		description.add("compdescription", getLeftOperation());
		description.add("opposite", getDecisionManager().getModelElement(getRightOperation().getModelElementId()));

		description.setImage("composite.gif");

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

		String composite = ((CompositeOperation) getLeftOperation()).getCompositeName();
		String other = null;
		if (getRightOperation() instanceof CompositeOperation) {
			other = ((CompositeOperation) getRightOperation()).getCompositeName();
		} else {
			other = "Change related to "
				+ DecisionUtil.getClassAndName(getDecisionManager().getModelElement(
					getRightOperation().getModelElementId()));
		}

		if (isLeftMy()) {
			myOption.setOptionLabel(composite);
			theirOption.setOptionLabel(other);
		} else {
			myOption.setOptionLabel(other);
			theirOption.setOptionLabel(composite);
		}
		options.add(myOption);
		options.add(theirOption);
	}
}
