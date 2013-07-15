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
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictContext;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictDescription;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.VisualConflict;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;

/**
 * Container for {@link MultiReferenceConflict} and {@link SingleReferenceConflict}.
 * 
 * @author wesendon
 */
public class ReferenceConflict extends VisualConflict {

	private final VisualConflict conflict;

	/**
	 * Default constructor.
	 * 
	 * @param conflict underlying conflict, {@link MultiReferenceConflict} or {@link SingleReferenceConflict}
	 * @param conflictBucket the conflict
	 * @param decisionManager decisionmanager
	 */
	public ReferenceConflict(boolean underlyingSingleConflict, ConflictBucket conf, DecisionManager decisionManager) {
		super(conf, decisionManager, true, false);
		if (underlyingSingleConflict) {
			this.conflict = new SingleReferenceConflict(conf, conf.getMyOperation(), conf.getTheirOperation(),
				decisionManager);
		}
		else {
			this.conflict = createMultiMultiConflict(conf, conf.getMyOperation(), conf.getTheirOperation(),
				decisionManager);
			this.setLeftIsMy(((MultiReferenceOperation) conf.getMyOperation()).isAdd());

		}
		init();
	}

	private VisualConflict createMultiMultiConflict(ConflictBucket conflictBucket, AbstractOperation my,
		AbstractOperation their,
		DecisionManager decisionManager) {
		if (((MultiReferenceOperation) my).isAdd()) {
			return new MultiReferenceConflict(conflictBucket, my, their, decisionManager, true);
		} else {
			return new MultiReferenceConflict(conflictBucket, their, my, decisionManager, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictContext initConflictContext() {
		return conflict.getConflictContext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ConflictDescription initConflictDescription(ConflictDescription desc) {
		return conflict.getConflictDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initConflictOptions(List<ConflictOption> options) {
		for (ConflictOption option : conflict.getOptions()) {
			if (option.getType() == OptionType.MyOperation) {
				option.addOperations(getLeftOperations());
			} else if (option.getType() == OptionType.TheirOperation) {
				option.addOperations(getRightOperations());
			}
			options.add(option);
		}
	}

}
