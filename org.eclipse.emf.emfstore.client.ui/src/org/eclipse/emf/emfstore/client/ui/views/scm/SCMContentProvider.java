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
package org.eclipse.emf.emfstore.client.ui.views.scm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.views.changes.ChangePackageVisualizationHelper;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider for the scm views.
 * 
 * @author Shterev
 */
public abstract class SCMContentProvider implements ITreeContentProvider {

	private static ChangePackageVisualizationHelper changePackageVisualizationHelper;
	private boolean showRootNodes = true;
	private boolean reverseNodes = true;
	private AdapterFactoryContentProvider contentProvider;
	private ProjectSpace projectSpace;

	/**
	 * Default constructor.
	 * 
	 * @param treeViewer the tree viewer. the project.
	 */
	protected SCMContentProvider(TreeViewer treeViewer) {
		contentProvider = new AdapterFactoryContentProvider(new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}

	/**
	 * Sets the flag to reverse the order of the nodes. Default value is true - i.e. the more recent operations are on
	 * top.
	 * 
	 * @param reverseNodes the new value
	 */
	public void setReverseNodes(boolean reverseNodes) {
		this.reverseNodes = reverseNodes;
	}

	/**
	 * Returns if the nodes should be reversed.
	 * 
	 * @return true if the nodes should be reversed in order
	 */
	public boolean isReverseNodes() {
		return reverseNodes;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] getChildren(Object node) {
		Object element = node;
		if (element instanceof HistoryInfo) {
			if (((HistoryInfo) element).getChangePackage() == null) {
				return new Object[0];
			} else {
				return getChildrenOfChangePackage(((HistoryInfo) element).getChangePackage());
			}
		} else if (node instanceof AbstractOperation) {
			return Arrays.asList(contentProvider.getChildren(node)).toArray();
		} else if (node instanceof ChangePackage) {
			return getChildrenOfChangePackage(((ChangePackage) element));
		} else if (element instanceof EObject) {

			// EObject me = (EObject) element;
			// show only model element that are contained in a project and have an ID
			// ModelElementId modelElementId = projectSpace.getProject().getModelElementId(me);
			// if (modelElementId != null) {
			// return getChildren(me);
			// }

			Object[] children = contentProvider.getChildren(element);
			return children;

		}

		return Arrays.asList(contentProvider.getChildren(element)).toArray();
	}

	private Object[] getChildrenOfChangePackage(ChangePackage changePackage) {
		EList<AbstractOperation> operations = changePackage.getOperations();
		if (isReverseNodes()) {
			ECollections.reverse(operations);
		}
		return operations.toArray();
	}

	/**
	 * @param visualizationHelper the visualizationHelper to set.
	 */
	public void setChangePackageVisualizationHelper(ChangePackageVisualizationHelper visualizationHelper) {
		changePackageVisualizationHelper = visualizationHelper;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasChildren(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ChangePackage) {
			return Arrays.asList((ChangePackage) inputElement).toArray();
		}
		if (!(inputElement instanceof List) || ((List) inputElement).isEmpty()) {
			return new Object[0];
		}
		List inputList = (List) inputElement;
		Object firstElement = inputList.get(0);
		if (firstElement == null) {
			return new Object[0];
		}
		if (firstElement instanceof ChangePackage) {
			if (showRootNodes) {
				return inputList.toArray();
			} else {
				ArrayList<Object> elements = new ArrayList<Object>();
				List<ChangePackage> changePackages = inputList;
				for (ChangePackage cp : changePackages) {
					elements.addAll(Arrays.asList(getChildren(cp)));
				}
				return elements.toArray();
			}
		} else if (firstElement instanceof HistoryInfo) {
			List<HistoryInfo> historyInfos = (List<HistoryInfo>) inputElement;
			if (showRootNodes) {
				return historyInfos.toArray();
			} else {
				ArrayList<Object> elements = new ArrayList<Object>();
				for (HistoryInfo hi : historyInfos) {
					if (hi.getChangePackage() != null) {
						elements.addAll(Arrays.asList(getChildren(hi)));
					}
				}
				return elements.toArray();
			}
		}
		return new Object[0];
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO: inspect & review
	public Object getParent(Object element) {
		if (element instanceof HistoryInfo) {
			return ((HistoryInfo) element).getPrimerySpec();
		} else if (element instanceof ChangePackage) {
			// TODO: use bidirectional references?
			return ((ChangePackage) element).eContainer();
		} else if (element instanceof AbstractOperation) {
			return ((ChangePackage) element).eContainer();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	/**
	 * @return if the root nodes should be shown.
	 */
	public boolean showRootNodes() {
		return showRootNodes;
	}

	/**
	 * Sets if the root nodes should be shown.
	 * 
	 * @param show the new value.
	 */
	public void setShowRootNodes(boolean show) {
		showRootNodes = show;
	}

	/**
	 * Content provider displaying the scm item in the following order: HistoryInfo > ChangePackage > Operation(s) >
	 * ModelElement(s).
	 * 
	 * @author Shterev
	 */
	public static class Detailed extends SCMContentProvider {

		/**
		 * Default constructor.
		 * 
		 * @param viewer the viewer.
		 * @param project
		 */
		public Detailed(TreeViewer viewer) {
			super(viewer);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @return an array of {@link AbstractOperation}s
		 */
		protected Object[] getChildren(ChangePackage changePackage) {
			EList<AbstractOperation> operations = changePackage.getOperations();
			if (isReverseNodes()) {
				ECollections.reverse(operations);
			}
			return operations.toArray();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @return an empty array
		 */
		protected Object[] getChildren(EObject modelElement) {
			Object[] children = super.contentProvider.getChildren(modelElement);
			return children;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return an array of {@link EObject}s
	 */
	protected Object[] getChildren(AbstractOperation op) {
		return Arrays.asList(contentProvider.getChildren(op)).toArray();
	}

	/**
	 * Content provider displaying the scm item in the following order: HistoryInfo > ChangePackage > ModelElement(s) >
	 * Operation(s).
	 * 
	 * @author Shterev
	 */
	public static class Compact extends SCMContentProvider {

		/**
		 * Default constructor.
		 * 
		 * @param viewer the viewer.
		 */
		public Compact(TreeViewer viewer) {
			super(viewer);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @return an array of {@link EObject}s
		 */
		protected Object[] getChildren(ChangePackage changePackage) {
			ArrayList<EObject> modelElements = changePackageVisualizationHelper.getModelElements(
				changePackage.getAllInvolvedModelElements(), new ArrayList<EObject>());
			return modelElements.toArray();

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @return an array of {@link AbstractOperation}s
		 */
		protected Object[] getChildren(EObject modelElement) {
			ChangePackage changePackage;
			if (getParent(modelElement) instanceof HistoryInfo) {
				HistoryInfo historyInfo = (HistoryInfo) getParent(modelElement);
				changePackage = historyInfo.getChangePackage();
			} else if (getParent(modelElement) instanceof ChangePackage) {
				changePackage = (ChangePackage) getParent(modelElement);
			} else {
				return new Object[0];
			}
			// TODO: code duplication, see getChildren method above
			List<AbstractOperation> operations = changePackage.getTouchingOperations(ModelUtil.getProject(modelElement)
				.getModelElementId(modelElement));
			if (isReverseNodes()) {
				Collections.reverse(operations);
			}
			return operations.toArray();

		}

	}

	/**
	 * Sets the ProjectSpace.
	 * 
	 * @param projectSpace the projectspace
	 */
	public void setProjectSpace(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

}
