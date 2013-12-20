/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial APi and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.api.test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.exceptions.ESCertificateException;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithSharedProject;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test if {@code RunESCommand} exception handling.
 * 
 * @author emueller
 */
public class RunESCommandTest extends ESTestWithSharedProject {

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Test
	public void testRunESCommandWithResultWithException() {
		Exception exception = null;
		Player player = null;

		try {
			player = RunESCommand.WithException.runWithResult(ESCertificateException.class, new Callable<Player>() {
				@SuppressWarnings("null")
				public Player call() throws Exception {
					final Player player = null;
					player.getName();
					return player;
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}

		assertTrue(exception != null);
		assertTrue(exception instanceof RuntimeException);
		assertTrue(player == null);
		exception = null;

		try {
			RunESCommand.WithException.runWithResult(ESCertificateException.class, new Callable<Player>() {
				public Player call() throws Exception {
					throw new ESException(StringUtils.EMPTY);
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception != null);
		assertTrue(exception instanceof RuntimeException);
		assertTrue(((RuntimeException) exception).getCause() instanceof ESException);
		exception = null;

		try {
			RunESCommand.WithException.runWithResult(ESException.class, new Callable<Player>() {
				public Player call() throws Exception {
					throw new ESException(StringUtils.EMPTY);
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception != null);
		assertTrue(exception instanceof ESException);
		exception = null;
	}

	@Test
	public void testRunESCommandWithException() {
		Exception exception = null;
		final Player player = null;

		try {
			RunESCommand.WithException.run(ESCertificateException.class, new Callable<Void>() {
				@SuppressWarnings("null")
				public Void call() throws Exception {
					final Player player = null;
					player.getName();
					return null;
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception != null);
		assertTrue(exception instanceof RuntimeException);
		assertTrue(player == null);
		exception = null;

		try {
			RunESCommand.WithException.run(ESCertificateException.class, new Callable<Void>() {
				public Void call() throws Exception {
					throw new ESException(StringUtils.EMPTY);
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception != null);
		assertTrue(exception instanceof RuntimeException);
		assertTrue(((RuntimeException) exception).getCause() instanceof ESException);
		exception = null;

		try {
			RunESCommand.WithException.run(ESException.class, new Callable<Void>() {
				public Void call() throws Exception {
					throw new ESException(StringUtils.EMPTY);
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}

		assertTrue(exception != null);
		assertTrue(exception instanceof ESException);
		exception = null;
	}

	@Test
	public void testRunESCommandWithResultWithoutException() {
		Exception exception = null;

		// runtime exception
		try {
			RunESCommand.runWithResult(new Callable<Player>() {
				public Player call() throws Exception {
					final Player player = BowlingFactory.eINSTANCE.createPlayer();
					player.getName().length();
					return player;
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception == null);

		// non-runtime exception
		try {
			RunESCommand.runWithResult(new Callable<Player>() {
				public Player call() throws Exception {
					throw new ESException(StringUtils.EMPTY);
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception == null);
	}

	@Test
	public void testRunESCommandtWithoutException() {
		Exception exception = null;

		// runtime exception
		try {
			RunESCommand.run(new Callable<Void>() {
				public Void call() throws Exception {
					final Player player = BowlingFactory.eINSTANCE.createPlayer();
					player.getName().length();
					return null;
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception == null);

		// non-runtime exception
		try {
			RunESCommand.run(new Callable<Void>() {
				public Void call() throws Exception {
					throw new ESException(StringUtils.EMPTY);
				}
			});
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			exception = e;
		}
		assertTrue(exception == null);
	}
}
