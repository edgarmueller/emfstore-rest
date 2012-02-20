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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.emfstore.client.ui.views.changes.ChangePackageVisualizationHelper;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Content provider for the scm views.
 * 
 * @author Shterev
 */
public class SCMContentProvider extends AdapterFactoryContentProvider implements ITreeContentProvider {

	private static ChangePackageVisualizationHelper changePackageVisualizationHelper;
	private boolean showRootNodes = true;
	private boolean reverseNodes = true;

	/**
	 * Default constructor.
	 * 
	 * @param treeViewer
	 *            the tree viewer. the project.
	 */
	public SCMContentProvider() {
		super(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}

	/**
	 * Sets the flag to reverse the order of the nodes. Default value is true -
	 * i.e. the more recent operations are on top.
	 * 
	 * @param reverseNodes
	 *            the new value
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

	@Override
	public Object[] getElements(Object object) {
		if (object instanceof List<?> && showRootNodes) {
			return ((List<?>) object).toArray();
		} else if (object instanceof List<?>) {
			List<HistoryInfo> historyInfos = (List<HistoryInfo>) object;
			List<AbstractOperation> result = new ArrayList<AbstractOperation>(historyInfos.size());
			for (HistoryInfo info : historyInfos) {
				if (info.getChangePackage() != null) {
					List<AbstractOperation> ops = new ArrayList<AbstractOperation>(info.getChangePackage()
						.getOperations());
					Collections.reverse(ops);
					result.addAll(ops);
				}
			}
			return result.toArray();
		} else if (object instanceof EObject) {
			return new Object[] { object };
		}

		return super.getElements(object);
	}

	@Override
	public Object[] getChildren(Object object) {

		if (object instanceof HistoryInfo) {
			HistoryInfo historyInfo = (HistoryInfo) object;
			return super.getChildren(historyInfo.getChangePackage());
		}

		return super.getChildren(object);
	}

	public boolean isShowRootNodes() {
		return showRootNodes;
	}

	public void setShowRootNodes(boolean showRootNodes) {
		this.showRootNodes = showRootNodes;
	}

}
