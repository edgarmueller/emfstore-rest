/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.util;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResultAndException;

/**
 * Utility class for automatically wrapping changes against a model element
 * in a command within an EMFStore project.
 * 
 * @author emueller
 * 
 **/
public final class RunESCommand {

	/**
	 * Private constructor.
	 */
	private RunESCommand() {

	}

	/**
	 * The {@link Callable} to be executed may throw an exception.
	 */
	public static class WithException {

		/**
		 * Executes the given @link Callable} and returns the result.
		 * 
		 * @param callable
		 *            the callable to be execued
		 * @param exceptionType the type of the exception that might be thrown during execution
		 * @return the return value of the Callable
		 * 
		 * @param <E> Exception in case an error occurs during execution of the Callable
		 * @param <T> the return type of the Callable
		 * @throws E on execution failure
		 */
		public static <T, E extends Exception> T runWithResult(final Class<E> exceptionType, final Callable<T> callable)
			throws E {
			final EMFStoreCommandWithResultAndException<T, E> cmd = new EMFStoreCommandWithResultAndException<T, E>() {
				@Override
				protected T doRun() {
					try {
						return callable.call();
						// BEGIN SUPRESS CATCH EXCEPTION
					} catch (final Exception e) {
						// END SUPRESS CATCH EXCEPTION
						if (exceptionType.isInstance(e)) {
							setException(exceptionType.cast(e));
						} else if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						} else {
							throw new RuntimeException(e);
						}
					}

					return null;
				}
			};

			final T result = cmd.run(false);

			if (cmd.hasException()) {
				throw cmd.getException();
			}

			return result;
		}

		/**
		 * Executes the given {@link Callable} and returns the result.
		 * 
		 * @param callable
		 *            the callable to be executed
		 * @param exceptionType the type of the exception
		 * @param <T> the exception type
		 * @throws T in case an error occurs during execution of the callable
		 */
		public static <T extends Exception> void run(final Class<T> exceptionType, final Callable<Void> callable)
			throws T {

			final EMFStoreCommandWithException<T> cmd = new EMFStoreCommandWithException<T>() {
				@Override
				protected void doRun() {
					try {
						callable.call();
						// BEGIN SUPRESS CATCH EXCEPTION
					} catch (final Exception e) {
						// END SUPRESS CATCH EXCEPTION
						if (exceptionType.isInstance(e)) {
							setException(exceptionType.cast(e));
						} else if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						} else {
							throw new RuntimeException(e);
						}
					}
				}
			};

			cmd.run(false);

			if (cmd.hasException()) {
				throw cmd.getException();
			}
		}
	}

	/**
	 * Executes the given {@link Callable} and returns the result.
	 * 
	 * @param callable
	 *            the callable to be executed
	 */
	public static void run(final Callable<Void> callable) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					callable.call();
					// BEGIN SUPRESS CATCH EXCEPTION
				} catch (final Exception e) {
					// END SUPRESS CATCH EXCEPTION
					// ignore
				}
			}
		}.run(false);
	}

	/**
	 * Executes the given {@link Callable} and returns the result.
	 * 
	 * @param callable
	 *            the callable to be executed
	 * @return the return value of the callable
	 * 
	 * @param <T> the return type of the callable
	 */
	public static <T> T runWithResult(final Callable<T> callable) {
		return new EMFStoreCommandWithResult<T>() {
			@Override
			protected T doRun() {
				try {
					return callable.call();
					// BEGIN SUPRESS CATCH EXCEPTION
				} catch (final Exception e) {
					// END SUPRESS CATCH EXCEPTION
					// ignore
				}
				return null;
			}
		}.run(false);
	}
}