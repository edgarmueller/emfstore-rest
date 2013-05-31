/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: groeber
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ui.ESCompare;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * Handles registered Compare types for the extension point:
 * org.eclipse.emf.emfstore.client.ui.views.historybrowsercomparator
 * 
 * @author groeber
 */
public class HistoryCompare {

	// This is the ID of the extension point
	private final static String HISTORY_COMPARE_ID = "org.eclipse.emf.emfstore.client.ui.historyBrowserComparator";

	/**
	 * This function tells you if there is a Comparator registered on the
	 * extension point
	 * 
	 * @return true if there is an extension or false if not
	 */
	public static boolean hasRegisteredExtensions() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(HISTORY_COMPARE_ID);
		return (config.length != 0);
	}

	/**
	 * This function calls {@link ESCompare#compare(EObject e1, EObject e2)}and
	 * then {@link ESCompare#display()} for all registered extensions.
	 * 
	 * @param e1
	 *            EObject one to compare
	 * @param e2
	 *            EObject two to compare
	 */
	public static void handleRegisteredExtensions(final EObject e1,
		final EObject e2) {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(HISTORY_COMPARE_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("comparator");
				if (o instanceof ESCompare) {
					ISafeRunnable runnable = new ISafeRunnable() {

						public void handleException(Throwable exception) {
							ModelUtil.logException(exception);
						}

						public void run() throws Exception {
							ESCompare extension = (ESCompare) o;
							extension.compare(e1, e2);
							extension.display();
						}
					};
					SafeRunner.run(runnable);
				}
			}
		} catch (CoreException ex) {
			String message = "Error while instantiating compare provider!";
			ModelUtil.logWarning(message, ex);
		}
	}
}
