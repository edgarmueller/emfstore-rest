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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElementContainer;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.impl.OperationRecorder;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
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

	/**
	 * Test adding an element with deleted references.
	 */
	@Test
	public void rescueElementAndDeleteIt() {

		setCompareAtEnd(false);
		// ExtensionRegistry.INSTANCE.set(ESOperationModifier.ID, new AutoOperationWrapper());

		final Project project = getProject();

		// final TestElement source = getTestElement("source");
		final TestElement target = getTestElement("target");
		final TestElement connection = getTestElement("connection");
		final TestElementContainer source = TestmodelFactory.eINSTANCE.createTestElementContainer();

		source.getElements().add(connection);
		connection.setContainer(source);
		target.getReferences().add(connection);

		project.addModelElement(source);
		ModelElementId connectionId = project.getModelElementId(connection);

		clonedProjectSpace.getOperationManager().stopChangeRecording();
		ChangePackage cp = VersioningFactory.eINSTANCE.createChangePackage();
		cp.getOperations().addAll(getProjectSpace().getOperations());
		cp.apply(clonedProjectSpace.getProject());

		// do not use commands since we only have them on client side
		connection.setContainer(null);

		List<AbstractOperation> ops = getProjectSpace().getOperations();
		assertEquals(2, ops.size());
		assertThat(ops.get(0), instanceOf(CompositeOperation.class));
		assertThat(ops.get(1), instanceOf(CreateDeleteOperation.class));

		// apply via change package, because change package does not start a command in contrast
		// to projectSpace#applyOperations
		cp = VersioningFactory.eINSTANCE.createChangePackage();
		cp.getOperations().addAll(ops);
		cp.apply(clonedProjectSpace.getProject());

		// in case the connection could not be deleted because of a changed ID, we would expect 2 model elements
		Assert.assertEquals(1, clonedProjectSpace.getProject().getModelElements().size());
	}
}
