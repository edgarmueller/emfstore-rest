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
package org.eclipse.emf.emfstore.client.ui.dialogs.merge;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.AbstractConflictResolver;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider;
import org.eclipse.emf.emfstore.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.util.DefaultMergeLabelProvider;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * This is an alternative merge handler, using the new merge wizard.
 * 
 * @author wesendon
 */
public class MergeProjectHandler extends AbstractConflictResolver implements ConflictResolver {

	private static MergeLabelProvider labelProvider;

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

	/**
	 * Default constructor.
	 */
	public MergeProjectHandler() {
		this(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void preDecisionManagerHook() {
		WorkspaceManager.getObserverBus().register(getLabelProvider(), MergeLabelProvider.class);
	}

	@Override
	protected boolean controlDecisionManager(final DecisionManager decisionManager) {
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

		if (labelProvider == null) {
			ExtensionPoint extensionPoint = new ExtensionPoint("org.eclipse.emf.emfstore.client.ui.merge.labelprovider");
			ExtensionElement element = extensionPoint.getElementWithHighestPriority();

			if (element == null) {
				labelProvider = new DefaultMergeLabelProvider();
			} else {
				labelProvider = element.getClass("class", MergeLabelProvider.class);
			}
		}

		return labelProvider;
	}
}