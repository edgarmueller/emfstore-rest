/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.common;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;

/**
 * Utility class to run {@link ESSafeRunnable}s.
 * If {@link CommonUtil#isTesting()} is true, a possible exception is thrown.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public final class ESSafeRunner {

	private ESSafeRunner() {
	}

	/**
	 * Runs a {@link ESSafeRunnable} and handles exceptions.
	 * 
	 * @param code The {@link ESSafeRunnable} to execute.
	 */
	public static void run(final ESSafeRunnable code) {
		Assert.isNotNull(code);
		try {
			code.run();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (Exception e) {
			// END SUPRESS CATCH EXCEPTION
			handleException(code, e);
		} catch (LinkageError e) {
			handleException(code, e);
		} catch (AssertionError e) {
			handleException(code, e);
		}
	}

	private static void handleException(final ESSafeRunnable code, final Throwable exception) {
		code.handleException(exception);
		if (CommonUtil.isTesting()) {
			if (exception instanceof RuntimeException) {
				throw ((RuntimeException) exception);
			} else {
				throw new RuntimeException(exception);
			}
		}
	}
}
