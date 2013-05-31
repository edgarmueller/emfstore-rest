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
package org.eclipse.emf.emfstore.internal.common;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.emfstore.common";

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	private final transient LogAdapter logAdapter;
	/**
	 * The constructor.
	 */
	public Activator() {
		super();
		logAdapter = new LogAdapter();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	// BEGIN SUPRESS CATCH EXCEPTION
	public void start(final BundleContext context) throws Exception {
		// END SUPRESS CATCH EXCEPTION
		super.start(context);
		plugin = this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	// BEGIN SUPRESS CATCH EXCEPTION
	public void stop(final BundleContext context) throws Exception {
		// END SUPRESS CATCH EXCEPTION
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Log an error.
	 * 
	 * @param message the message
	 * @param exception the causing exception
	 * @param statusInt a status code
	 */
	public void log(final String message, final Exception exception, final int statusInt) {
		logAdapter.log(message, exception, statusInt);
	}

	/**
	 * Log an exception to the error log.
	 * 
	 * @param message the message
	 * @param exception the exception
	 */
	public void logException(final String message, final Exception exception) {
		logAdapter.logException(message, exception);
	}

	/**
	 * Log a warning to the error log.
	 * 
	 * @param message the message
	 * @param exception the exception
	 */
	public void logWarning(final String message, final Exception exception) {
		logAdapter.logWarning(message, exception);
	}
}