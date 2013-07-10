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
package org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionRegistry;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.AbstractConflictResolver;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.DefaultOperationAuthorProvider;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.MergeLabelProvider;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util.OperationAuthorProvider;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.util.DefaultMergeLabelProvider;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * This is an alternative merge handler, using the new merge wizard.
 * 
 * @author wesendon
 */
public class MergeProjectHandler extends AbstractConflictResolver {

	private MergeLabelProvider labelProvider;
	private OperationAuthorProvider authorProvider;

	/**
	 * Default constructor.
	 * 
	 * @param isBranchMerge
	 *            specifies whether two branches are merged, rather then changes
	 *            from the same branches.
	 */
	public MergeProjectHandler(boolean isBranchMerge) {
		super(isBranchMerge);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void preDecisionManagerHook() {
		labelProvider = getLabelProvider();
		ESWorkspaceProviderImpl.getObserverBus().register(labelProvider, MergeLabelProvider.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void postDecisionManagerHook() {
		if (labelProvider != null) {
			ESWorkspaceProviderImpl.getObserverBus().unregister(labelProvider, MergeLabelProvider.class);
		}
		if (authorProvider != null) {
			ESWorkspaceProviderImpl.getObserverBus().unregister(authorProvider, OperationAuthorProvider.class);
		}
	}

	@Override
	protected boolean controlDecisionManager(final DecisionManager decisionManager, ChangeConflictSet changeConflictSet) {
		authorProvider = new DefaultOperationAuthorProvider(changeConflictSet.getLeftChanges(),
			changeConflictSet.getRightChanges());
		ESWorkspaceProviderImpl.getObserverBus().register(authorProvider, OperationAuthorProvider.class);
		return RunInUI.runWithResult(new Callable<Boolean>() {

			public Boolean call() {
				MergeWizard wizard = new MergeWizard(decisionManager);
				WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
				dialog.setPageSize(1000, 500);
				dialog.setBlockOnOpen(true);
				dialog.create();

				int open = dialog.open();

				getLabelProvider().dispose();
				return (open == Dialog.OK);
			}
		});
	}

	private MergeLabelProvider getLabelProvider() {
		return ExtensionRegistry.INSTANCE.get(
			MergeLabelProvider.ID,
			MergeLabelProvider.class, new DefaultMergeLabelProvider(), true);
	}

}
