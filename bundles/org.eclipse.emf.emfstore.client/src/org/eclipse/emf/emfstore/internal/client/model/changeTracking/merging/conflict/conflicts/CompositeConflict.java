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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictContext;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.VisualConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.util.OperationUtil;

/**
 * Conflict {@link CompositeOperation} involved.
 * 
 * @author wesendon
 */
public class CompositeConflict extends VisualConflict {

	private static final String CHANGE_RELATED_TO = "Change related to "; //$NON-NLS-1$
	private static final String COMP_DESCRIPTION_KEY = "compdescription"; //$NON-NLS-1$
	private static final String COMPOSITE_GIF = "composite.gif"; //$NON-NLS-1$
	private static final String COMPOSITE_CONFLICT_BOTH_KEY = "compositeconflict.both"; //$NON-NLS-1$
	private static final String OPPOSITE_KEY = "opposite"; //$NON-NLS-1$
	private static final String COMPOSITE_CONFLICT_THEIR_KEY = "compositeconflict.their"; //$NON-NLS-1$
	private static final String COMPOSITE_CONFLICT_MY_KEY = "compositeconflict.my"; //$NON-NLS-1$
	private static final String INCOMING_COMP_DESCRIPTION_KEY = "incomingcompdescription"; //$NON-NLS-1$
	private static final String LOCAL_COMP_DESCRIPTION_KEY = "localcompdescription"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 * 
	 * @param conflictBucket
	 *            the conflict
	 * @param decisionManager
	 *            {@link DecisionManager}
	 * @param meCausing
	 *            true, if composite caused by merging user
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
			description.setDescription(DecisionUtil.getDescription(COMPOSITE_CONFLICT_BOTH_KEY, getDecisionManager()
				.isBranchMerge()));
			description.add(LOCAL_COMP_DESCRIPTION_KEY, getLeftOperation());
			description.add(INCOMING_COMP_DESCRIPTION_KEY, getRightOperation());
		} else if (isLeftMy()) {
			description.setDescription(DecisionUtil.getDescription(COMPOSITE_CONFLICT_MY_KEY, getDecisionManager()
				.isBranchMerge()));
		} else {
			description.setDescription(DecisionUtil.getDescription(COMPOSITE_CONFLICT_THEIR_KEY, getDecisionManager()
				.isBranchMerge()));
		}

		description.add(COMP_DESCRIPTION_KEY, getLeftOperation());
		description.add(OPPOSITE_KEY, getDecisionManager().getModelElement(getRightOperation().getModelElementId()));

		description.setImage(COMPOSITE_GIF);

		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		final ConflictOption myOption = new ConflictOption(StringUtils.EMPTY, OptionType.MyOperation);
		myOption.addOperations(getMyOperations());
		final ConflictOption theirOption = new ConflictOption(StringUtils.EMPTY, OptionType.TheirOperation);
		theirOption.addOperations(getTheirOperations());

		String composite = null;
		String other = null;
		if (getLeftOperation() instanceof CompositeOperation) {
			composite = ((CompositeOperation) getLeftOperation()).getCompositeName();
		} else {
			composite = CHANGE_RELATED_TO
				+ DecisionUtil.getClassAndName(getDecisionManager().getModelElement(
					getLeftOperation().getModelElementId()));
		}
		if (getRightOperation() instanceof CompositeOperation) {
			other = ((CompositeOperation) getRightOperation()).getCompositeName();
		} else {
			other = CHANGE_RELATED_TO
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
