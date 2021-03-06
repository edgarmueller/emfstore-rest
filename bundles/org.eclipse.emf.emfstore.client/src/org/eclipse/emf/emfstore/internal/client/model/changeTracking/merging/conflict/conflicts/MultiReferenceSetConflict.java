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

// BEGIN COMPLEX CODE
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
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;

public class MultiReferenceSetConflict extends VisualConflict {

	private static final String MULTIREF_GIF = "multiref.gif"; //$NON-NLS-1$
	private static final String OTHER_CONTAINER_KEY = "othercontainer"; //$NON-NLS-1$
	private static final String TARGET_KEY = "target"; //$NON-NLS-1$
	private static final String MULTIREFERENCE_SET_CONFLICT_MY_KEY = "multireferencesetconflict.my"; //$NON-NLS-1$
	private static final String MULTIREFERENCE_SET_CONFLICT_THEIR_KEY = "multireferencesetconflict.their"; //$NON-NLS-1$
	private static final String MULTIREFERENCE_SET_CONFLICT_CONTAINMENT_KEY = "multireferencesetconflict.containment"; //$NON-NLS-1$
	private final boolean containmentConflict;

	/**
	 * Default constructor.
	 * 
	 * @param decisionManager {@link DecisionManager}
	 * @param myMultiRef is my multireference
	 */
	public MultiReferenceSetConflict(ConflictBucket conf, DecisionManager decisionManager,
		boolean myMultiRef) {
		super(conf, decisionManager, myMultiRef, false);
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
	 * @see org.eclipse.emf.emfstore.internal.client.VisualConflict.dialogs.merge.conflict.Conflict#initConflictDescription()
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {

		if (containmentConflict) {
			description.setDescription(DecisionUtil.getDescription(MULTIREFERENCE_SET_CONFLICT_CONTAINMENT_KEY,
				getDecisionManager().isBranchMerge()));
		} else if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription(MULTIREFERENCE_SET_CONFLICT_MY_KEY,
				getDecisionManager()
					.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription(MULTIREFERENCE_SET_CONFLICT_THEIR_KEY,
				getDecisionManager().isBranchMerge()));
		}

		description.add(TARGET_KEY, isLeftMy() ? getMyOperation(MultiReferenceOperation.class)
			.getReferencedModelElements().get(0) : getMyOperation(MultiReferenceSetOperation.class).getNewValue());
		description.add(OTHER_CONTAINER_KEY, getLeftOperation().getModelElementId());
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

		if (containmentConflict) {
			final EObject target = getDecisionManager().getModelElement(
				((MultiReferenceOperation) getLeftOperation()).getReferencedModelElements().get(0));

			myOption.setOptionLabel(MessageFormat.format(
				Messages.MultiReference_Move_To,
				getClassAndName(target),
				getClassAndName(getDecisionManager().getModelElement(getMyOperation().getModelElementId()))));
			theirOption.setOptionLabel(MessageFormat.format(
				Messages.MultiReference_Move_To,
				getClassAndName(target),
				getClassAndName(getDecisionManager().getModelElement(getTheirOperation().getModelElementId()))));

		} else if (isLeftMy()) {
			final EObject target = getDecisionManager().getModelElement(
				getMyOperation(MultiReferenceOperation.class).getReferencedModelElements().get(0));

			myOption.setOptionLabel(Messages.MultiReferenceSetConflict_Remove + DecisionUtil.getClassAndName(target));
			theirOption.setOptionLabel(Messages.MultiReferenceSetConflict_Set + DecisionUtil.getClassAndName(target));
		} else {
			final EObject target = getDecisionManager().getModelElement(
				getTheirOperation(MultiReferenceOperation.class).getReferencedModelElements().get(0));

			myOption.setOptionLabel(Messages.MultiReferenceSetConflict_Set + DecisionUtil.getClassAndName(target));
			theirOption
				.setOptionLabel(Messages.MultiReferenceSetConflict_Remove + DecisionUtil.getClassAndName(target));
		}

		options.add(myOption);
		options.add(theirOption);

	}
}