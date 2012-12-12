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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.emfstore.client.ui.common.EClassFilter;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeNode;

/**
 * Content provider for the scm views.
 * 
 * @author emueller
 */
public class SCMContentProvider extends AdapterFactoryContentProvider implements ITreeContentProvider {

	private boolean showRootNodes = true;
	private boolean reverseNodes = true;
	private Map<ChangePackage, TreeNode> virtualFilteredNodes;
	private Map<TreeNode, List<AbstractOperation>> filteredOperations;
	private IdEObjectCollection collection;

	/**
	 * Default constructor.
	 * 
	 */
	public SCMContentProvider() {
		super(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		this.virtualFilteredNodes = new LinkedHashMap<ChangePackage, TreeNode>();
		this.filteredOperations = new LinkedHashMap<TreeNode, List<AbstractOperation>>();
	}

	public SCMContentProvider(IdEObjectCollection collection) {
		this();
		this.collection = collection;
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

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object object) {

		if (object instanceof List<?> && showRootNodes) {

			List<?> list = (List<?>) object;
			List<Object> result = new ArrayList<Object>(list.size());
			result.addAll(list);
			return ((List<?>) result).toArray();

		} else if (object instanceof List<?>) {
			// valid inputs are a list of HistoryInfos as well as a list
			// of ChangePackage
			List<?> list = (List<?>) object;

			if (list.size() == 0) {
				return list.toArray();
			}

			List<Object> result = new ArrayList<Object>(list.size());

			if (isListOf(list, HistoryInfo.class)) {
				for (HistoryInfo info : (List<HistoryInfo>) list) {
					if (info.getChangePackage() != null) {
						result.addAll(getReversedOperations(info.getChangePackage()));
					}
				}
			} else {
				for (ChangePackage changePackage : (List<ChangePackage>) list) {
					result.addAll(getReversedOperations(changePackage));
				}
			}

			return result.toArray();

		} else if (object instanceof EObject) {
			return new Object[] { object };
		}

		return super.getElements(object);
	}

	private boolean eClassFilterEnabled() {
		return EClassFilter.INSTANCE.isEnabled() && collection != null;
	}

	private List<AbstractOperation> getReversedOperations(ChangePackage changePackage) {
		List<AbstractOperation> ops = new ArrayList<AbstractOperation>(changePackage.getOperations());
		Collections.reverse(ops);
		return ops;
	}

	private boolean isListOf(List<?> list, Class<? extends EObject> clazz) {
		Object firstElement = list.get(0);

		return clazz.isInstance(firstElement);
	}

	private Object[] filter(ChangePackage changePackage, Object[] input, Class<? extends EObject> clazz) {

		List<Object> result = new ArrayList<Object>(input.length);
		for (Object object : input) {

			if (clazz.isInstance(object)) {
				continue;
			}

			if (object instanceof AbstractOperation) {
				AbstractOperation operation = (AbstractOperation) object;

				if (eClassFilterEnabled() && EClassFilter.INSTANCE.involvesOnlyFilteredEClasses(collection, operation)) {

					if (!virtualFilteredNodes.containsKey(changePackage)) {
						TreeNode node = new TreeNode(changePackage);
						virtualFilteredNodes.put(changePackage, node);
						filteredOperations.put(node, new ArrayList<AbstractOperation>());
					}

					TreeNode node = virtualFilteredNodes.get(changePackage);
					List<AbstractOperation> alreadyFilteredOperations = filteredOperations.get(node);
					// filter might get called multiple times
					if (!alreadyFilteredOperations.contains(operation)) {
						alreadyFilteredOperations.add(operation);
					}
				} else {
					result.add(operation);
				}
			} else {
				result.add(object);
			}
		}

		return result.toArray();
	}

	@Override
	public boolean hasChildren(Object object) {
		return getChildren(object).length > 0;
	}

	@Override
	public Object[] getChildren(Object object) {

		if (object instanceof HistoryInfo) {
			HistoryInfo historyInfo = (HistoryInfo) object;
			return getChildren(historyInfo.getChangePackage());
		} else if (object instanceof ChangePackage) {
			Object[] filtered = filter((ChangePackage) object, super.getChildren(object), LogMessage.class);

			if (virtualFilteredNodes.containsKey(object)) {
				List<Object> result = new ArrayList<Object>(filtered.length + 1);
				result.addAll(Arrays.asList(filtered));
				result.add(virtualFilteredNodes.get(object));
				return result.toArray();
			}

			return filtered;
		} else if (object instanceof TreeNode) {
			return filteredOperations.get(object).toArray();
		} else if (object instanceof CompositeOperation) {
			return ((CompositeOperation) object).getSubOperations().toArray();
		}

		return super.getChildren(object);
	}

	/**
	 * Whether to show root nodes.
	 * 
	 * @return true, if root nodes are shown, false otherwise
	 */
	public boolean isShowRootNodes() {
		return showRootNodes;
	}

	/**
	 * Determines whether root nodes are shown.
	 * 
	 * @param showRootNodes
	 *            if true, root nodes will be shown
	 */
	public void setShowRootNodes(boolean showRootNodes) {
		this.showRootNodes = showRootNodes;
	}

}