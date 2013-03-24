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
package org.eclipse.emf.emfstore.client.test.changeTracking.recording;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.impl.OperationRecorder;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test some {@link OperationRecorder} functionalities.
 * 
 * @author jsommerfeldt
 * 
 */
public class OperationRecorderTest extends WorkspaceTest {

	/**
	 * Check the force command setting.
	 */
	@SuppressWarnings("restriction")
	@Test(expected = RuntimeException.class)
	public void forceCommands() {
		this.setCompareAtEnd(false);
		getProjectSpace().getOperationManager().getRecorderConfig().setForceCommands(true);
		Project project = getProject();
		project.addModelElement(getTestElement());
	}

	/**
	 * Test adding an element with deleted references.
	 */
	@Test
	public void addElementWithDeletedReference() {
		final Project project = getProject();

		final TestElement element1 = getTestElement();
		final TestElement element2 = getTestElement();

		element1.getReferences().add(element2);

		project.addModelElement(element2);

		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				project.deleteModelElement(element2);
				project.addModelElement(element1);
				Assert.assertNotNull(((IdEObjectCollectionImpl) project).getDeletedModelElementId(element2));
				return null;
			}
		});
	}
}
