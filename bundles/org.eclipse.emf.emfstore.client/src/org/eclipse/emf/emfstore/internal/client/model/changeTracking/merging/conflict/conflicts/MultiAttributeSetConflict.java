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

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

public class MultiAttributeSetConflict extends Conflict {

	public MultiAttributeSetConflict(Set<AbstractOperation> opsA, Set<AbstractOperation> opsB,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager,
		boolean isMyRemove) {
		super(opsA, opsB, leftOperation, rightOperation, decisionManager, isMyRemove, true);
	}

	/**
	 * LEFT: Remove, RIGHT: set
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {

		if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription("multiattributesetconflict.my", getDecisionManager()
				.isBranchMerge()));

		} else {
			description.setDescription(DecisionUtil.getDescription("multiattributesetconflict.their",
				getDecisionManager().isBranchMerge()));
		}

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

		if (isLeftMy()) {
			myOption.setOptionLabel("Remove element");
			theirOption.setOptionLabel("Change element");
		} else {
			myOption.setOptionLabel("Change element");
			theirOption.setOptionLabel("Remove element");
		}

		options.add(myOption);
		options.add(theirOption);
	}
}
