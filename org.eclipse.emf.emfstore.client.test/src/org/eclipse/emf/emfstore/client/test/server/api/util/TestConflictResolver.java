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
package org.eclipse.emf.emfstore.client.test.server.api.util;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.AbstractConflictResolver;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;

public class TestConflictResolver extends AbstractConflictResolver {

	private final int expectedConflicts;

	public TestConflictResolver(boolean isBranchMerge, int expectedConflicts) {
		super(isBranchMerge, null);
		this.expectedConflicts = expectedConflicts;
	}

	@Override
	protected boolean controlDecisionManager(DecisionManager decisionManager) {
		int counter = 0;
		for (Conflict conflict : decisionManager.getConflicts()) {
			conflict.setSolution(conflict.getOptionOfType(OptionType.MyOperation));
			counter++;
		}
		if (!decisionManager.isResolved()) {
			throw new RuntimeException("Conflicts not resolved");
		}
		if (counter > -1 && counter != expectedConflicts) {
			throw new RuntimeException("more or less conflicts then expected");
		}
		return true;
	}

}