/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.util;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Utility class for executing {@link Callable}s within the UI Thread.
 * 
 * @author emueller
 * 
 */
public final class RunESCommand {

	private RunESCommand() {

	}

	/**
	 * The {@link Callable} to be executed may throw an exception.
	 */
	public static class WithException {

		/**
		 * Executes the given callable and returns the result.
		 * 
		 * @param callable
		 *            the callable to be execued
		 * @return the return value of the {@link Callable}
		 * @throws ESException in case an error occurs during execution of the callable
		 * 
		 * @param <T> the return type of the callable
		 */
		public static <T> T runWithResult(final Callable<T> callable) throws ESException {
			return new EMFStoreCommandWithResultAndException<T, ESException>() {
				@Override
				protected T doRun() {
					try {
						return callable.call();
					} catch (Exception e) {
						if (e instanceof ESException) {
							setExcpetion((ESException) e);
						} else if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						} else {
							throw new RuntimeException(e);
						}
					}

					return null;
				}
			}.run(false);
		}

		/**
		 * Executes the given callable and returns the result.
		 * 
		 * @param callable
		 *            the callable to be execued
		 * @throws ESException in case an error occurs during execution of the callable
		 */
		public static void run(final Callable<Void> callable) throws ESException {
			new EMFStoreCommandWithException<ESException>() {
				@Override
				protected void doRun() {
					try {
						callable.call();
					} catch (Exception e) {
						if (e instanceof ESException) {
							setExcpetion((ESException) e);
						} else {
							throw new RuntimeException(e);
						}
					}
				}
			}.run(false);
		}
	}

	/**
	 * Executes the given {@link Callable} and returns the result.
	 * 
	 * @param callable
	 *            the {@link Callable} to be executed
	 */
	public static void run(final Callable<Void> callable) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					callable.call();
				} catch (Exception e) {
					// ignore
				}
			}
		}.run(false);
	}

	/**
	 * Executes the given callable and returns the result.
	 * 
	 * @param callable
	 *            the callable to be execued
	 * @return the return value of the {@link Callable}
	 * 
	 * @param <T> the return type of the callable
	 */
	public static <T> T runWithResult(final Callable<T> callable) {
		return new EMFStoreCommandWithResult<T>() {
			@Override
			protected T doRun() {
				try {
					return callable.call();
				} catch (Exception e) {
					// ignore
				}
				return null;
			}
		}.run(false);
	}

}