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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider;
import org.eclipse.emf.emfstore.client.model.observers.ConflictResolver;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.util.DefaultMergeLabelProvider;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * This is an alternative merge handler, using the new merge wizard.
 * 
 * @author wesendon
 */
public class MergeProjectHandler implements ConflictResolver {

	private List<AbstractOperation> acceptedMine;
	private List<AbstractOperation> rejectedTheirs;

	/**
	 * Default constructor.
	 * 
	 */
	public MergeProjectHandler() {
		acceptedMine = new ArrayList<AbstractOperation>();
		rejectedTheirs = new ArrayList<AbstractOperation>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ConflictResolver#getAcceptedMine()
	 */
	public List<AbstractOperation> getAcceptedMine() {
		return acceptedMine;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ConflictResolver#getAcceptedMine()
	 */
	public List<AbstractOperation> getRejectedTheirs() {
		return rejectedTheirs;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ConflictResolver#getAcceptedMine()
	 */
	@SuppressWarnings("unchecked")
	public boolean resolveConflicts(Project project, List<ChangePackage> myChangePackages,
		List<ChangePackage> theirChangePackages, PrimaryVersionSpec base, PrimaryVersionSpec target) {

		DefaultMergeLabelProvider labelProvider = new DefaultMergeLabelProvider();
		WorkspaceManager.getObserverBus().register(labelProvider, MergeLabelProvider.class);

		DecisionManager decisionManager = new DecisionManager(project, myChangePackages, theirChangePackages, base,
			target);

		if (decisionManager.isResolved()) {
			decisionManager.calcResult();
			acceptedMine = decisionManager.getAcceptedMine();
			rejectedTheirs = decisionManager.getRejectedTheirs();
			return true;
		}

		MergeWizard wizard = new MergeWizard(decisionManager);
		WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		dialog.setPageSize(1000, 500);
		dialog.setBlockOnOpen(true);
		dialog.create();

		int open = dialog.open();
		acceptedMine = decisionManager.getAcceptedMine();
		rejectedTheirs = decisionManager.getRejectedTheirs();

		labelProvider.dispose();
		return (open == Window.OK);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ConflictResolver#getMergedResult()
	 */
	public ChangePackage getMergedResult() {
		List<AbstractOperation> mergeResult = new ArrayList<AbstractOperation>();
		for (AbstractOperation operationToReverse : getRejectedTheirs()) {
			mergeResult.add(0, operationToReverse.reverse());
		}
		mergeResult.addAll(getAcceptedMine());
		ChangePackage result = VersioningFactory.eINSTANCE.createChangePackage();
		result.getOperations().addAll(mergeResult);

		return result;
	}

	/**
	 * Adapter Until "Branch" branch is merged into master.
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observers.ConflictResolver#resolveConflicts(org.eclipse.emf.emfstore.common.model.Project,
	 *      java.util.List, org.eclipse.emf.emfstore.server.model.versioning.ChangePackage,
	 *      org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec,
	 *      org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec)
	 */
	public boolean resolveConflicts(Project project, List<ChangePackage> theirChangePackages,
		ChangePackage myChangePackage, PrimaryVersionSpec baseVersion, PrimaryVersionSpec targetVersion) {
		return resolveConflicts(project, Arrays.asList(myChangePackage), theirChangePackages, baseVersion,
			targetVersion);
	}
}
