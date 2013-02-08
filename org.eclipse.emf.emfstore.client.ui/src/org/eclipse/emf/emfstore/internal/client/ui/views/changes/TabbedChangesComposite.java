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
package org.eclipse.emf.emfstore.internal.client.ui.views.changes;

import java.util.List;

import org.eclipse.emf.emfstore.internal.client.ui.views.scm.SCMContentProvider;
import org.eclipse.emf.emfstore.internal.client.ui.views.scm.SCMLabelProvider;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * A composite that contains multiple tabs displaying the operation from a different view - e.g. grouped by model
 * element, or ungrouped.
 * 
 * @author Shterev
 * @author emueller
 */
public class TabbedChangesComposite extends Composite {

	private TabFolder folder;
	private Composite tabComposite;
	private TreeViewer tabTreeViewer;
	private SCMContentProvider contentProvider;

	/**
	 * Default constructor.
	 * 
	 * @param parent
	 *            the composite's parent
	 * @param style
	 *            the style
	 * @param changePackages
	 *            the input of change packages as a list
	 * @param project
	 *            the project
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects that is necessary to resolve
	 *            deleted EObjects contained in change packages
	 * @param showRootNodes
	 *            whether to show root nodes
	 */
	public TabbedChangesComposite(Composite parent, int style, List<ChangePackage> changePackages, Project project,
		IModelElementIdToEObjectMapping idToEObjectMapping, boolean showRootNodes) {
		super(parent, style);
		createComposite(style, project, idToEObjectMapping, showRootNodes);
		tabTreeViewer.setInput(changePackages);
	}

	/**
	 * Default constructor.
	 * 
	 * @param parent
	 *            the composite's parent
	 * @param style
	 *            the style
	 * @param project
	 *            the project
	 * @param operations
	 *            the input of operations as a list
	 * @param idToEObjectMapping
	 *            a mapping from IDs to EObjects that is necessary to resolve
	 *            deleted EObjects contained in operations
	 * @param showRootNodes
	 *            whether to show root nodes
	 */
	public TabbedChangesComposite(Composite parent, int style, Project project, List<AbstractOperation> operations,
		IModelElementIdToEObjectMapping idToEObjectMapping, boolean showRootNodes) {
		super(parent, style);
		createComposite(style, project, idToEObjectMapping, showRootNodes);
		tabTreeViewer.setInput(operations);
	}

	private void createComposite(int style, Project project, IModelElementIdToEObjectMapping idToEObjectMapping,
		boolean showRootNodes) {
		setLayout(new GridLayout());
		folder = new TabFolder(this, style);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(folder);

		tabComposite = new Composite(folder, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(tabComposite);

		tabTreeViewer = new TreeViewer(tabComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tabTreeViewer.getControl());

		contentProvider = new SCMContentProvider(idToEObjectMapping);
		contentProvider.setShowRootNodes(showRootNodes);
		SCMLabelProvider detailedLabelProvider = new SCMLabelProvider(project);
		detailedLabelProvider.setChangePackageVisualizationHelper(new ChangePackageVisualizationHelper(
			idToEObjectMapping));
		tabTreeViewer.setContentProvider(contentProvider);
		tabTreeViewer.setLabelProvider(detailedLabelProvider);
		tabTreeViewer.expandToLevel(1);

		TabItem opTab = new TabItem(folder, style);
		opTab.setText("Operations");
		opTab.setControl(tabComposite);
	}

}