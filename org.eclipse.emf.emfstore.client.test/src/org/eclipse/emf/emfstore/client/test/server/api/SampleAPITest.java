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
package org.eclipse.emf.emfstore.client.test.server.api;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

public class SampleAPITest extends CoreServerTest {

	@Test
	public void createProject() throws ESException {
		final ProjectSpace ps = getProjectSpace();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				ps.getProject().addModelElement(createTestElement("Horst"));
				try {
					ps.shareProject(new NullProgressMonitor());
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		assertEquals(1, getServerSpace().getProjects().size());
		ProjectHistory projectHistory = getServerSpace().getProjects().get(0);

		projectHistory.getVersions().get(projectHistory.getVersions().size() - 1);
		assertEquals(1, getProject().getModelElements().size());
		assertEquals("Horst", ((TestElement) getProject().getModelElements().get(0)).getName());
	}

	public void mergeTest() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					TestElement testElement = createTestElement("Horst");
					getProjectSpace().getProject().addModelElement(testElement);
					getProjectSpace().shareProject(new NullProgressMonitor());

					testElement.setName("1");
					getProjectSpace().commit();

					testElement.setName("2");
					getProjectSpace().commitToBranch(Versions.createBRANCH("test"), null, null, null);

				} catch (ESException e) {
				}
			}
		}.run(false);

	}
}