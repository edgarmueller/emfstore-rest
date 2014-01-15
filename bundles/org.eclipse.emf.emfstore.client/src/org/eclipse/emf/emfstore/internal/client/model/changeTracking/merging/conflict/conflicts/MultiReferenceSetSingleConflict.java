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

import static org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil.getClassAndName;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.VisualConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;

/**
 * @author wesendon
 */
public class MultiReferenceSetSingleConflict extends VisualConflict {

	private static final String MULTIREFERENCE_SET_SINGLECONFLICT_KEY = "multireferencesetsingleconflict"; //$NON-NLS-1$
	private static final String OTHERCONTAINER_KEY = "othercontainer"; //$NON-NLS-1$
	private static final String MULTIREF_GIF = "multiref.gif"; //$NON-NLS-1$
	private static final String TARGET_KEY = "target"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 * 
	 * @param conflictBucket the conflict
	 * @param decisionManager {@link DecisionManager}
	 * @param setLeft multi set ref is left
	 */
	public MultiReferenceSetSingleConflict(ConflictBucket conflictBucket, DecisionManager decisionManager,
		boolean setLeft) {
		super(conflictBucket, decisionManager, setLeft, true);
	}

	/**
	 * LEFT: MultiReferenceSet, RIGHT: SingleReference
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.VisualConflict.dialogs.merge.conflict.Conflict#initConflictDescription(org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.conflict.ConflictDescription)
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		description.setDescription(DecisionUtil.getDescription(MULTIREFERENCE_SET_SINGLECONFLICT_KEY,
			getDecisionManager()
				.isBranchMerge()));

		description.add(TARGET_KEY, ((SingleReferenceOperation) getRightOperation()).getNewValue());
		description.add(OTHERCONTAINER_KEY, getTheirOperation().getModelElementId());

		description.setImage(MULTIREF_GIF);
		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.VisualConflict.dialogs.merge.conflict.Conflict#initConflictOptions(java.util.List)
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		final ConflictOption myOption = new ConflictOption(StringUtils.EMPTY, OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		final ConflictOption theirOption = new ConflictOption(StringUtils.EMPTY, OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		final EObject target = getDecisionManager().getModelElement(
			((SingleReferenceOperation) getRightOperation()).getNewValue());

		myOption.setOptionLabel(MessageFormat.format(Messages.MultiReference_Move_To,
			getClassAndName(target),
			getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId()))));
		theirOption.setOptionLabel(MessageFormat.format(Messages.MultiReference_Move_To,
			getClassAndName(target),
			getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId()))));

		options.add(myOption);
		options.add(theirOption);
	}

}
