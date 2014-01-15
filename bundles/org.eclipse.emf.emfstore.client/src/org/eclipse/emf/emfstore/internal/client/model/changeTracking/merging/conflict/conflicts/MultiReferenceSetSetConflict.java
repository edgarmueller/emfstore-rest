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

// BEGIN COMPLEX CODE
//
// WORK IN PROGRESS !
//

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
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;

public class MultiReferenceSetSetConflict extends VisualConflict {

	private static final String MULTIREF_GIF = "multiref.gif"; //$NON-NLS-1$
	private static final String OTHERCONTAINER_KEY = "othercontainer"; //$NON-NLS-1$
	private static final String OVALUE_KEY = "ovalue"; //$NON-NLS-1$
	private static final String VALUE_KEY = "value"; //$NON-NLS-1$
	private static final String MULTIREFERENCE_SET_SET_CONFLICT_SET_KEY = "multireferencesetsetconflict.set"; //$NON-NLS-1$
	private static final String MULTIREFERENCE_SET_SET_CONFLICT_MOVE_KEY = "multireferencesetsetconflict.move"; //$NON-NLS-1$
	private final boolean containmentConflict;

	public MultiReferenceSetSetConflict(ConflictBucket conflictBucket, DecisionManager decisionManager) {
		super(conflictBucket, decisionManager, true, false);
		// is this rule enough?
		containmentConflict = getMyOperation().getModelElementId().equals(getTheirOperation().getModelElementId());
		init();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.VisualConflict.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		if (!containmentConflict) {
			description.setDescription(DecisionUtil.getDescription(MULTIREFERENCE_SET_SET_CONFLICT_SET_KEY,
				getDecisionManager().isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription(MULTIREFERENCE_SET_SET_CONFLICT_MOVE_KEY,
				getDecisionManager().isBranchMerge()));
		}

		description.add(VALUE_KEY, getMyOperation(MultiReferenceSetOperation.class).getNewValue());
		description.add(OVALUE_KEY, getTheirOperation(MultiReferenceSetOperation.class).getNewValue());
		description.add(OTHERCONTAINER_KEY, getTheirOperation().getModelElementId());
		description.setImage(MULTIREF_GIF);
		return description;
	}

	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		final ConflictOption myOption = new ConflictOption(StringUtils.EMPTY, OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		final ConflictOption theirOption = new ConflictOption(StringUtils.EMPTY, OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		if (!containmentConflict) {
			myOption.setOptionLabel(DecisionUtil.getClassAndName(getDecisionManager().getModelElement(
				getMyOperation(MultiReferenceSetOperation.class).getNewValue())));
			theirOption.setOptionLabel(DecisionUtil.getClassAndName(getDecisionManager().getModelElement(
				getTheirOperation(MultiReferenceSetOperation.class).getNewValue())));
		} else {
			final EObject target = getDecisionManager().getModelElement(
				getMyOperation(MultiReferenceSetOperation.class).getNewValue());

			myOption.setOptionLabel(MessageFormat.format(Messages.MultiReference_Move_To,
				getClassAndName(target),
				getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId()))));
			theirOption.setOptionLabel(MessageFormat.format(Messages.MultiReference_Move_To,
				getClassAndName(target),
				getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId()))));
		}

		options.add(myOption);
		options.add(theirOption);
	}
}