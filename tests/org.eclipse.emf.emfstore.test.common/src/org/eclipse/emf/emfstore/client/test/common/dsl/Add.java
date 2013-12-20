/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.dsl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.test.model.TestElement;

/**
 * Utility class to ease adding elements to a project within a wrapping command.
 * 
 */
public final class Add {

	private Add() {

	}

	public static void toContainedElements(final TestElement testElement, final TestElement containee) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getContainedElements().add(containee);
				return null;
			}
		});
	}

	public static void toContainedElements(final TestElement testElement, final List<TestElement> containees) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getContainedElements().addAll(containees);
				return null;
			}
		});
	}

	public static void toContainedElements2(final TestElement testElement, final TestElement containee) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getContainedElements2().add(containee);
				return null;
			}
		});
	}

	public static void toContainedElements2(final TestElement testElement, final List<TestElement> containees) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getContainedElements2().addAll(containees);
				return null;
			}
		});
	}

	public static void toProject(final ESLocalProject localProject, final EObject eObject) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().add(eObject);
				return null;
			}
		});
	}

	public static void toNonContainedNToM(final TestElement testElement, final TestElement containee) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getNonContained_NToM().add(containee);
				return null;
			}
		});
	}

	public static void toNonContainedNToM(final TestElement testElement, final List<TestElement> containees) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getNonContained_NToM().addAll(containees);
				return null;
			}
		});
	}

	private static List<EObject> asModelElements(final EObject... eObjects) {
		return Arrays.asList(eObjects);
	}

	public static void toProject(final ESLocalProject localProject, final EObject... eObjects) {
		RunESCommand.runWithResult(new Callable<Void>() {
			public Void call() throws Exception {
				localProject.getModelElements().addAll(
					asModelElements(eObjects));
				return null;
			}
		});
	}

}
