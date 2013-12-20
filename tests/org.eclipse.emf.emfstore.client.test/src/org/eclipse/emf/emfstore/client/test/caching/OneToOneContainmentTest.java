/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.caching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests the Attribute Operation.
 * 
 * @author koegel
 */
public class OneToOneContainmentTest extends ESTest {

	/**
	 * Add a one to one containment child and check for project cache update.
	 */
	@Test
	public void addIssueSolution() {

		final TestElement issue = Create.testElement();
		getProject().addModelElement(issue);

		assertTrue(getProject().contains(issue));
		assertEquals(getProject(), ModelUtil.getProject(issue));

		final TestElement solution = Create.testElement();
		issue.setContainedElement(solution);

		assertTrue(getProject().contains(solution));
		assertEquals(getProject(), ModelUtil.getProject(solution));
		assertEquals(issue, solution.getContainer());
		assertEquals(solution, issue.getContainedElement());
	}
}
