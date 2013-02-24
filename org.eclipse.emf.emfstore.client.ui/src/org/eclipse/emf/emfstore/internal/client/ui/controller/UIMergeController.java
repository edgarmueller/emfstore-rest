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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.CancelOperationException;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.BranchSelectionDialog;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.common.ListUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * UIController used to merge other branches into the current projectspace.
 * 
 * @author wesendon
 */
public class UIMergeController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	/**
	 * Default constructor.
	 * 
	 * @param shell
	 *            active shell
	 * @param projectSpace
	 *            projectspace
	 */
	public UIMergeController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {
		if (!projectSpace.getOperations().isEmpty()) {
			MessageDialog
				.openError(
					getShell(),
					"Merge not possible",
					"There are pending changes. Please revert or commit first. Merging with local changes is currently not supported.");
			return null;
		}

		PrimaryVersionSpec selectedVersionSpec = branchSelection(projectSpace);

		if (selectedVersionSpec != null) {
			// TODO: monitor
			projectSpace.mergeBranch(selectedVersionSpec,
				new MergeProjectHandler(true), new NullProgressMonitor());
		}
		return null;
	}

	private PrimaryVersionSpec branchSelection(final ProjectSpace projectSpace)
		throws ESException {

		// OTS: progress monitor
		final List<BranchInfo> branches = projectSpace.getBranches();
		ListIterator<BranchInfo> iterator = branches.listIterator();
		while (iterator.hasNext()) {
			BranchInfo current = iterator.next();
			if (current.getName().equals(
				projectSpace.getBaseVersion().getBranch())) {
				iterator.remove();
			}
		}

		BranchInfo result = RunInUI.WithException
			.runWithResult(new Callable<BranchInfo>() {
				public BranchInfo call() throws Exception {

					BranchSelectionDialog dialog = new BranchSelectionDialog(
						getShell(), projectSpace.getBaseVersion(),
						ListUtil.mapToAPI(ESBranchInfo.class, branches));
					dialog.setBlockOnOpen(true);

					if (dialog.open() != Dialog.OK
						|| dialog.getResult() == null) {
						throw new CancelOperationException(
							"No Branch specified");
					}
					return dialog.getResult();

				}
			});
		return result.getHead();
	}
}