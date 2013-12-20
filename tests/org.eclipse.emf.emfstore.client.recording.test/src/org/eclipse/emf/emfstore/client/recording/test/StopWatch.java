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
package org.eclipse.emf.emfstore.client.recording.test;

public class StopWatch {

	private static final String STOPPED_TOTAL_TIME = "stopped, total time "; //$NON-NLS-1$
	private final String name;
	private long startTime;
	private boolean silent;

	public StopWatch(String name) {
		this.name = name;
		start();
	}

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public StopWatch silent() {
		silent = true;
		return this;
	}

	public void stop() {
		final long timeElapsed = System.currentTimeMillis() - startTime;
		log(STOPPED_TOTAL_TIME + timeElapsed);
	}

	private void log(String message) {
		if (silent) {
			return;
		}
		System.out.println(name + " at " + System.currentTimeMillis() + ": " + message); //$NON-NLS-1$ //$NON-NLS-2$
	}
}