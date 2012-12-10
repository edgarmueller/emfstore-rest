/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.dialogs.merge.ui.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.util.UIDecisionUtil;
import org.eclipse.emf.emfstore.client.ui.views.scm.SCMContentProvider;
import org.eclipse.emf.emfstore.client.ui.views.scm.SCMLabelProvider;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog showing the detail changes about other involved operations in case a conflict option contains more than one
 * operation.
 * 
 * @author emueller
 * 
 */
public class DetailsDialog extends TitleAreaDialog {

	private final DecisionManager decisionManager;
	private final List<AbstractOperation> operations;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param decisionManager
	 *            a decision manager instance
	 * @param option
	 *            the conflict option containing the detail operations to be visualized
	 */
	public DetailsDialog(Shell parentShell, DecisionManager decisionManager, ConflictOption option) {
		super(parentShell);
		this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
		this.decisionManager = decisionManager;
		ArrayList<AbstractOperation> ops = new ArrayList<AbstractOperation>();
		ops.addAll(option.getOperations());
		this.operations = ops;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Detail changes");
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		setTitle("Other involved changes");
		setMessage("There are " + (operations.size() - 1) + " other operation(s) involved.");

		Composite tabComposite = new Composite(parent, SWT.NONE);
		tabComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tabComposite.setLayout(new GridLayout(2, false));

		GridLayoutFactory.fillDefaults().applyTo(tabComposite);

		TreeViewer tabTreeViewer = new TreeViewer(tabComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tabTreeViewer.getControl());

		SCMContentProvider contentProvider = new SCMContentProvider(decisionManager.getProject());
		contentProvider.setShowRootNodes(true);

		SCMLabelProvider detailedLabelProvider = new SCMLabelProvider(decisionManager.getProject());
		detailedLabelProvider.setChangePackageVisualizationHelper(UIDecisionUtil
			.getChangePackageVisualizationHelper(decisionManager));

		tabTreeViewer.setContentProvider(contentProvider);
		tabTreeViewer.setLabelProvider(detailedLabelProvider);
		tabTreeViewer.setInput(operations);
		tabTreeViewer.expandToLevel(1);

		return tabComposite;
	}
}