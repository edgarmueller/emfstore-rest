/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.testers;

import java.util.concurrent.Callable;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.util.RunESCommand;

/**
 * Property tester to test if a project space has local changes.
 * 
 * @author koegel
 * @author emueller
 */
public class ProjectHasLocalChangesTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

		if (receiver instanceof ESLocalProject && expectedValue instanceof Boolean) {

			final ESLocalProject localProject = (ESLocalProject) receiver;

			return RunESCommand.runWithResult(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					Boolean hasLocalChanges = new Boolean(localProject.hasUncommitedChanges());
					return hasLocalChanges.equals(expectedValue);
				}
			});
		}

		return false;
	}

}