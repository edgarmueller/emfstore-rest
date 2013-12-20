/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jsommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.recording.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.client.test.common.cases.ComparingESTest;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.EMFStoreBasicCommandStack;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test some OperationRecorder functionalities.
 * 
 * @author jsommerfeldt
 * 
 */
public class OperationRecorderTest extends ComparingESTest {

	private static final String CONNECTION2 = "connection"; //$NON-NLS-1$
	private static final String TARGET2 = "target"; //$NON-NLS-1$

	/**
	 * Check the force command setting.
	 */
	@Test(expected = RuntimeException.class)
	public void forceCommands() {
		disableCompareAtEnd();
		getProjectSpace().getOperationManager().getRecorderConfig().setForceCommands(true);
		final TestElement testElement = Create.testElement();
		getProject().getModelElements().add(testElement);
	}

	/**
	 * Test adding an element with deleted references.
	 */
	@Test
	public void addElementWithDeletedReference() {

		final Project project = getProject();

		final TestElement element1 = Create.testElement();
		final TestElement element2 = Create.testElement();

		element1.getReferences().add(element2);

		RunESCommand.run(new Callable<Void>() {

			public Void call() throws Exception {
				project.addModelElement(element2);
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

		if (ESWorkspaceProviderImpl.getInstance().getEditingDomain().getCommandStack() instanceof EMFStoreBasicCommandStack) {

			// ExtensionRegistry.INSTANCE.set(ESOperationModifier.ID, new AutoOperationWrapper());

			final Project project = getProject();

			// final TestElement source = Create.testElement("source");
			final TestElement target = Create.testElement(TARGET2);
			final TestElement connection = Create.testElement(CONNECTION2);
			final TestElement source = Create.testElement();

			source.getContainedElements().add(connection);
			connection.setContainer(source);
			target.getReferences().add(connection);

			RunESCommand.run(new Callable<Void>() {

				public Void call() throws Exception {
					project.addModelElement(source);
					return null;
				}
			});

			getClonedProjectSpace().getOperationManager().stopChangeRecording();
			ChangePackage cp = RunESCommand.runWithResult(new Callable<ChangePackage>() {

				public ChangePackage call() throws Exception {
					final ChangePackage cp = VersioningFactory.eINSTANCE.createChangePackage();
					cp.getOperations().addAll(getProjectSpace().getOperations());
					cp.apply(getClonedProjectSpace().getProject());
					return cp;
				}
			});

			// do not use commands since we only have them on client side
			// FIXME: if not wrapped in command fails with transactional editing domain, if wrapped in command assert
			// fails,
			// see comment above
			connection.setContainer(null);

			final List<AbstractOperation> ops = getProjectSpace().getOperations();
			assertEquals(2, ops.size());
			assertThat(ops.get(0), instanceOf(CompositeOperation.class));
			assertThat(ops.get(1), instanceOf(CreateDeleteOperation.class));

			// apply via change package, because change package does not start a command in contrast
			// to projectSpace#applyOperations
			cp = VersioningFactory.eINSTANCE.createChangePackage();
			cp.getOperations().addAll(ops);
			cp.apply(getClonedProjectSpace().getProject());

			// in case the connection could not be deleted because of a changed ID, we would expect 2 model elements
			Assert.assertEquals(1, getClonedProjectSpace().getProject().getModelElements().size());
		}
	}
}
