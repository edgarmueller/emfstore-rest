/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * mkoegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.testers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Test if the project has pending unsaved changes.
 * 
 * @author mkoegel
 * 
 */
public class ProjectIsDirtyTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, final Object expectedValue) {
		if (!(expectedValue instanceof Boolean)) {
			return false;
		}
		if (receiver instanceof EObject) {
			EObject eObject = (EObject) receiver;
			ProjectSpace projectSpace = ModelUtil.getParent(ProjectSpace.class, eObject);
			if (projectSpace == null) {
				return false;
			}
			return projectSpace.isDirty();
		}
		return false;
	}
}
