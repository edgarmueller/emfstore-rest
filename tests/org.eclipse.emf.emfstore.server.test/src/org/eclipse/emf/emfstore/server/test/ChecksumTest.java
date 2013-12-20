/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.test;

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.addElement;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.checkout;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.cloneProject;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.computeChecksum;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.getOperationSize;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.removeModelElement;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.revert;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.share;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.startRecording;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.stopRecording;
import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.update;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.test.common.TestElementFeatures;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.dsl.Add;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.client.test.common.dsl.Update;
import org.eclipse.emf.emfstore.client.util.ESModelUtil;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.ChecksumErrorHandler;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.server.ESConflictSet;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChecksumTest extends ESTestWithLoggedInUser {

	private static final String C = "C"; //$NON-NLS-1$
	private static final String SOME_COMMIT_MESSAGE = "SomeCommitMessage"; //$NON-NLS-1$
	private static final String BOOL = "BOOL"; //$NON-NLS-1$
	private static final String ATTRIBUTE2 = "attribute"; //$NON-NLS-1$
	private static final String ATTRIBUTE_NAME = "attributeName"; //$NON-NLS-1$
	private static final String VALUE2 = "value"; //$NON-NLS-1$
	private static final String A = "A"; //$NON-NLS-1$

	@Override
	@Before
	public void before() {
		org.junit.Assume.assumeTrue(transactionalEditingDomainNotInUse());
		super.before();
	}

	public boolean transactionalEditingDomainNotInUse() {
		return !ESWorkspaceProviderImpl.getInstance().getEditingDomain().getClass().getName().contains("Transactional");
	}

	@Override
	@After
	public void after() {
		super.after();
	}

	@BeforeClass
	public static void beforeClass() {
		startEMFStore();
	}

	@AfterClass
	public static void afterClass() {
		stopEMFStore();
	}

	@Test
	public void testRevert() throws SerializationException {

		final TestElement table = Create.testElement(A);
		final TestElement value = Create.testElement(VALUE2);
		final TestElement attributeName = Create.testElement(ATTRIBUTE_NAME);
		final TestElement attribute = Create.testElement(ATTRIBUTE2);

		Add.toProject(getLocalProject(), table);
		Add.toProject(getLocalProject(), value);
		Add.toProject(getLocalProject(), attributeName);
		Add.toProject(getLocalProject(), attribute);

		attribute.getContainedElements().add(value);
		table.getElementMap().put(attributeName, value);

		final long checksum = computeChecksum(getLocalProject());

		clearOperations();
		Delete.fromProject(getLocalProject(), attribute);

		removeModelElement(getLocalProject(), attribute);
		revert(getLocalProject());

		final long checksumAfterRevert = computeChecksum(getLocalProject());

		assertEquals(checksum, checksumAfterRevert);
	}

	@Test
	public void testOrderOfRootElementsInvariance() throws SerializationException {

		final TestElement a = Create.testElement(A);
		final TestElement b = Create.testElement(BOOL);

		addElement(getLocalProject(), a);
		addElement(getLocalProject(), b);
		assertEquals(2, getLocalProject().getModelElements().size());

		final long computeChecksum = computeChecksum(getLocalProject());

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				// we must clear and add again in one command
				getLocalProject().getModelElements().clear();
				getLocalProject().getModelElements().add(b);
				getLocalProject().getModelElements().add(a);
				return null;
			}
		});

		final long checksum = computeChecksum(getLocalProject());
		assertEquals(computeChecksum, checksum);
	}

	@Test
	public void testAutocorrectErrorHandlerAtCommit() throws ESException, SerializationException {

		assertEquals(1, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.getClientBehavior().setChecksumErrorHandler(ChecksumErrorHandler.AUTOCORRECT);

		final TestElement testElement = Create.testElement();
		share(getUsersession(), addElement(getLocalProject(), testElement));

		Update.testElement(TestElementFeatures.name(),
			testElement, A);

		final long expectedChecksum = computeChecksum(getLocalProject());
		final ESLocalProject clonedProject = cloneProject(getLocalProject());

		stopRecording(getLocalProject());

		Update.testElement(TestElementFeatures.name(),
			testElement, BOOL);

		startRecording(getLocalProject());

		// FIXME: ugly
		assertEquals(1,
			((ESLocalProjectImpl) getLocalProject()).toInternalAPI().getOperations().size());

		// re-checkout should be triggered
		final ESPrimaryVersionSpec commit = commitWithoutCommand(getLocalProject());
		assertEquals(1, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		final ESLocalProject restoredProject = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects().get(0);
		final long computedChecksum = computeChecksum(restoredProject);

		assertTrue(ESModelUtil.areEqual(restoredProject, clonedProject));
		assertEquals(expectedChecksum,
			ESPrimaryVersionSpecImpl.class.cast(commit).toInternalAPI().getProjectStateChecksum());
		assertEquals(computedChecksum,
			ESPrimaryVersionSpecImpl.class.cast(commit).toInternalAPI().getProjectStateChecksum());
	}

	@Test
	public void testChangeTrackingAfterAutocorrectErrorHandler() throws ESException, SerializationException {

		assertEquals(1, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.getClientBehavior().setChecksumErrorHandler(ChecksumErrorHandler.AUTOCORRECT);

		final TestElement testElement = Create.testElement();
		share(getUsersession(), getLocalProject());

		final ESLocalProject checkout = checkout(getLocalProject());
		addElement(getLocalProject(), testElement);
		Update.testElement(TestElementFeatures.name(),
			testElement, A);

		commitWithoutCommand(getLocalProject());

		assertEquals(0, getOperationSize(getLocalProject()));

		addElement(checkout,
			Update.testElement(TestElementFeatures.name(),
				Create.testElement(), BOOL));

		update(checkout);
		commitWithoutCommand(checkout);

		assertEquals(0, getOperationSize(checkout));
		assertEquals(0, getOperationSize(getLocalProject()));

		Update.testElement(TestElementFeatures.name(),
			testElement, BOOL);

		stopRecording(getLocalProject());
		Update.testElement(TestElementFeatures.name(),
			testElement, C);

		startRecording(getLocalProject());

		assertEquals(1, getOperationSize(getLocalProject()));

		// cancel should be triggered via exception
		update(getLocalProject(), new MyUpdateCallback());

		assertEquals(1, getOperationSize(getLocalProject()));
	}

	@Test(expected = ESException.class)
	public void testCancelErrorHandlerAtCommit() throws ESException, SerializationException {

		assertEquals(1, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.getClientBehavior().setChecksumErrorHandler(ChecksumErrorHandler.CANCEL);

		final TestElement testElement = Create.testElement();
		share(getUsersession(), getLocalProject());

		addElement(getLocalProject(), testElement);
		Update.testElement(TestElementFeatures.name(), testElement, A);

		stopRecording(getLocalProject());
		Update.testElement(TestElementFeatures.name(), testElement, BOOL);
		startRecording(getLocalProject());

		// cancel should be triggered
		commitWithoutCommand(getLocalProject());
	}

	@Test(expected = ESException.class)
	public void testCancelErrorHandlerAtUpdateAfterOneCommit() throws ESException, SerializationException {

		assertEquals(1, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.getClientBehavior().setChecksumErrorHandler(ChecksumErrorHandler.CANCEL);

		final TestElement testElement = Create.testElement();
		share(getUsersession(), getLocalProject());

		final ESLocalProject checkout = checkout(getLocalProject());
		addElement(getLocalProject(), testElement);
		Update.testElement(TestElementFeatures.name(), testElement, A);

		commitWithoutCommand(getLocalProject());

		addElement(checkout, Create.testElement(BOOL));

		update(checkout);

		commitWithoutCommand(checkout);
		stopRecording(getLocalProject());
		Update.testElement(TestElementFeatures.name(), testElement, BOOL);
		startRecording(getLocalProject());

		// cancel should be triggered via exception
		update(getLocalProject(), new MyUpdateCallback());
	}

	@Test
	public void testCorrectChecksumsAtUpdate() throws ESException, SerializationException {

		assertEquals(1,
			ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.getClientBehavior().setChecksumErrorHandler(ChecksumErrorHandler.CANCEL);

		final TestElement testElement = Create.testElement();
		share(getUsersession(), getLocalProject());

		final ESLocalProject checkout = checkout(getLocalProject(), getLocalProject().getBaseVersion());

		addElement(getLocalProject(), testElement);
		Update.testElement(TestElementFeatures.name(), testElement, A);

		commitWithoutCommand(getLocalProject());

		addElement(checkout, Create.testElement());

		update(checkout);
		commitWithoutCommand(checkout);

		final ESPrimaryVersionSpec baseVersion = update(getLocalProject()).getBaseVersion();
		assertTrue(ESModelUtil.areEqual(getLocalProject(), checkout));
		assertEquals(
			computeChecksum(getLocalProject()),
			ESPrimaryVersionSpecImpl.class.cast(baseVersion).toInternalAPI().getProjectStateChecksum());
	}

	@Test
	public void testCorruptChecksumsAtUpdateWithLocalOperation() throws ESException, SerializationException {

		assertEquals(1, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.getClientBehavior().setChecksumErrorHandler(ChecksumErrorHandler.AUTOCORRECT);

		final TestElement testElement = Create.testElement();
		share(getUsersession(), getLocalProject());

		final ESLocalProject checkout = checkout(getLocalProject());
		addElement(getLocalProject(), testElement);

		Update.testElement(TestElementFeatures.name(), testElement, A);

		commitWithoutCommand(getLocalProject());
		addElement(checkout, Create.testElement());

		update(checkout);
		commitWithoutCommand(checkout);

		Update.testElement(TestElementFeatures.name(), testElement, BOOL);

		stopRecording(getLocalProject());
		Update.testElement(TestElementFeatures.name(), testElement, C);
		startRecording(getLocalProject());

		assertEquals(1, getOperationSize(getLocalProject()));

		// autocorrect should be triggered, will fail
		update(getLocalProject());
		assertEquals(1, getOperationSize(getLocalProject()));
	}

	private class MyCommitCallback implements ESCommitCallback {

		public boolean baseVersionOutOfDate(ESLocalProject projectSpace, IProgressMonitor progressMonitor) {
			return ESCommitCallback.NOCALLBACK.baseVersionOutOfDate(projectSpace, progressMonitor);
		}

		public boolean inspectChanges(ESLocalProject projectSpace, ESChangePackage changePackage,
			ESModelElementIdToEObjectMapping idToEObjectMapping) {
			return ESCommitCallback.NOCALLBACK.inspectChanges(projectSpace, changePackage, idToEObjectMapping);
		}

		public void noLocalChanges(ESLocalProject projectSpace) {
			ESCommitCallback.NOCALLBACK.noLocalChanges(projectSpace);
		}

	}

	private class MyUpdateCallback implements ESUpdateCallback {

		public boolean inspectChanges(ESLocalProject projectSpace, List<ESChangePackage> changes,
			ESModelElementIdToEObjectMapping idToEObjectMapping) {
			return ESUpdateCallback.NOCALLBACK.inspectChanges(projectSpace, changes, idToEObjectMapping);
		}

		public void noChangesOnServer() {
			ESUpdateCallback.NOCALLBACK.noChangesOnServer();
		}

		public boolean conflictOccurred(ESConflictSet changeConflictException,
			IProgressMonitor progressMonitor) {
			return ESUpdateCallback.NOCALLBACK.conflictOccurred(changeConflictException, progressMonitor);
		}

	}

	protected ESPrimaryVersionSpec commitWithoutCommand(final ESLocalProject project) throws ESException {
		return project.commit(SOME_COMMIT_MESSAGE, new MyCommitCallback(),
			new NullProgressMonitor());
	}

}
