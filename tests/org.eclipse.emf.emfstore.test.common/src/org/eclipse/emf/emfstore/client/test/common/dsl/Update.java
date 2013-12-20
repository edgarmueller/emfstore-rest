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

import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.test.model.TestElement;

/**
 * Convenience class for change the attribute of a {@link TestElement}.
 * 
 * @author emuller
 * 
 */
public final class Update {

	private Update() {

	}

	public static TestElement testElement(final EStructuralFeature feature,
		final TestElement testElement, final Object newValue) {
		return RunESCommand.runWithResult(new Callable<TestElement>() {
			public TestElement call() throws Exception {
				testElement.eSet(feature, newValue);
				return testElement;
			}
		});
	}
}
