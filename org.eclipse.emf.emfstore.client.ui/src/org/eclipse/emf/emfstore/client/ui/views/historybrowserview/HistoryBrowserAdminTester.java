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
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.testers.IsAdminTester;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;

/**
 * Tests if the user has admin permissions based on a HistoryInfo node.
 * 
 * @author shterev
 */
public class HistoryBrowserAdminTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, final Object expectedValue) {
		if (receiver instanceof EObject && ModelUtil.getParent(ProjectSpace.class, (EObject) receiver) != null
			&& expectedValue instanceof Boolean) {
			final ProjectSpace projectSpace = ModelUtil.getParent(ProjectSpace.class, (EObject) receiver);
			if (projectSpace == null) {
				return false;
			}
			IsAdminTester tester = new IsAdminTester();
			return tester.test(projectSpace, property, args, expectedValue);
		}
		return false;
	}

}
