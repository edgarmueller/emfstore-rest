/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common;

import org.eclipse.core.runtime.Assert;

/**
 * Utility class to run {@link ISafeRunnable}s.
 * If {@link CommonUtil#isTesting()} is true, a possible exception is thrown. 
 * 
 * @author Julian Sommerfeldt
 *
 */
public final class SafeRunner {
	
	private SafeRunner(){}
	
	/**
	 * Runs a {@link ISafeRunnable} and handles exceptions.
	 * 
	 * @param code The {@link ISafeRunnable} to execute.
	 */
	public static void run(ISafeRunnable code) {
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

	private static void handleException(ISafeRunnable code, Throwable e) {		
		code.handleException(e);
		if(CommonUtil.isTesting()){
			if(e instanceof RuntimeException){
				throw ((RuntimeException) e);
			} else {
				throw new RuntimeException(e);
			}
		}
	}
}