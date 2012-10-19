/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.options.MergeTextOption;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.DecisionUtil;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AttributeOperation;

/**
 * Conflict for two attribute operations.
 * 
 * @author wesendon
 */
public class AttributeConflict extends Conflict {

	/**
	 * Default constructor.
	 * 
	 * @param myOperations myOperations, with leading {@link AttributeOperation}
	 * @param theirOperations theirOperations, with leading {@link AttributeOperation}
	 * @param leftOperation the operation representing all left operations
	 * @param rightOperation the operation representing all right operations
	 * @param decisionManager decisionmanager
	 */
	public AttributeConflict(Set<AbstractOperation> myOperations, Set<AbstractOperation> theirOperations,
		AbstractOperation leftOperation, AbstractOperation rightOperation, DecisionManager decisionManager) {
		super(myOperations, theirOperations, leftOperation, rightOperation, decisionManager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription description) {
		description.setDescription(DecisionUtil.getDescription("attributeconflict", getDecisionManager()
			.isBranchMerge()));
		description.add("myvalue", getMyOperation(AttributeOperation.class).getNewValue());
		description.add("theirvalue", getTheirOperation(AttributeOperation.class).getNewValue());
		description.setImage("attribute.gif");

		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		initOptionsWithOutMerge(options, true);
	}

	/**
	 * Allows to init options, without adding a merge text option.
	 * 
	 * @param options list of options
	 * @param withMerge true, if merge text option ({@link MergeTextOption}) should be added
	 */
	protected void initOptionsWithOutMerge(List<ConflictOption> options, boolean withMerge) {
		ConflictOption myOption = new ConflictOption(getMyOperation(AttributeOperation.class).getNewValue(),
			ConflictOption.OptionType.MyOperation);
		myOption.setDetailProvider(DecisionUtil.WIDGET_MULTILINE);
		myOption.addOperations(getMyOperations());
		options.add(myOption);

		ConflictOption theirOption = new ConflictOption(getTheirOperation(AttributeOperation.class).getNewValue(),
			ConflictOption.OptionType.TheirOperation);
		theirOption.setDetailProvider(DecisionUtil.WIDGET_MULTILINE);
		theirOption.addOperations(getTheirOperations());
		options.add(theirOption);

		if (withMerge && DecisionUtil.detailsNeeded(this)) {
			MergeTextOption mergeOption = new MergeTextOption();
			mergeOption.add(myOption);
			mergeOption.add(theirOption);
			options.add(mergeOption);
		}
	}
}
