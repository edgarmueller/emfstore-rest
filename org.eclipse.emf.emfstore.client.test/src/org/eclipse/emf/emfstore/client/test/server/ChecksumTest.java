package org.eclipse.emf.emfstore.client.test.server;

import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.client.model.util.ChecksumErrorHandler;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler;
import org.eclipse.emf.emfstore.client.test.server.api.CoreServerTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.junit.Test;

public class ChecksumTest extends CoreServerTest {

	@Test
	public void testOrderOfRootElementsInvariance() throws SerializationException {
		// createTestElement automatically adds the element to the project
		TestElement a = createTestElement("A");
		TestElement b = createTestElement("B");

		getProject().getModelElements().add(a);
		getProject().getModelElements().add(b);

		long computeChecksum = ModelUtil.computeChecksum(getProject());

		getProject().getModelElements().clear();

		getProject().getModelElements().add(b);
		getProject().getModelElements().add(a);

		long checksum = ModelUtil.computeChecksum(getProject());

		Assert.assertEquals(computeChecksum, checksum);
	}

	@Test
	public void testAutocorrectErrorHandlerAtCommit() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		getProject().addModelElement(testElement);
		share(getProjectSpace());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("A");
			}
		}.run(false);

		long expectedChecksum = ModelUtil.computeChecksum(getProject());
		Project clonedProject = ModelUtil.clone(getProject());

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("B");
		getProjectSpace().getOperationManager().startChangeRecording();

		Assert.assertEquals(1, getProjectSpace().getOperations().size());

		// re-checkout should be triggered
		PrimaryVersionSpec commit = commitWithoutCommand(getProjectSpace());
		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Project restoredProject = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace()).getProjectSpaces()
			.get(0).getProject();
		long computedChecksum = ModelUtil.computeChecksum(restoredProject);

		Assert.assertTrue(ModelUtil.areEqual(restoredProject, clonedProject));
		Assert.assertEquals(expectedChecksum, commit.getProjectStateChecksum());
		Assert.assertEquals(commit.getProjectStateChecksum(), computedChecksum);
	}

	@Test
	public void testChangeTrackingAfterAutocorrectErrorHandler() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		final ProjectSpace checkedOutProjectSpace = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
			.checkout(getProjectSpace().getUsersession(), ModelUtil.clone(getProjectSpace().getProjectInfo()),
				new NullProgressMonitor());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		Assert.assertEquals(0, getProjectSpace().getOperations().size());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				TestElement testElement = TestmodelFactory.eINSTANCE.createTestElement();
				testElement.setName("B");
				checkedOutProjectSpace.getProject().addModelElement(testElement);
			}
		}.run(false);
		update(checkedOutProjectSpace);
		commitWithoutCommand(checkedOutProjectSpace);

		Assert.assertEquals(0, checkedOutProjectSpace.getOperations().size());
		Assert.assertEquals(0, getProjectSpace().getOperations().size());

		// this is the operation we would like to keep
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("B");
			}
		}.run(false);

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("C");
		getProjectSpace().getOperationManager().startChangeRecording();

		Assert.assertEquals(1, getProjectSpace().getOperations().size());

		// cancel should be triggered via exception
		getProjectSpace().update(Versions.createHEAD(getProjectSpace().getBaseVersion()), new MyUpdateCallback(),
			new NullProgressMonitor());

		Assert.assertEquals(1, getProjectSpace().getOperations().size());
	}

	@Test(expected = EmfStoreException.class)
	public void testCancelErrorHandlerAtCommit() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("B");
		getProjectSpace().getOperationManager().startChangeRecording();

		// cancel should be triggered
		commitWithoutCommand(getProjectSpace());
	}

	@Test(expected = EmfStoreException.class)
	public void testCancelErrorHandlerAtUpdateAfterOneCommit() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ProjectSpace checkedOutProjectSpace = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
			.checkout(getProjectSpace().getUsersession(), getProjectSpace().getProjectInfo(), new NullProgressMonitor());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		checkedOutProjectSpace.getProject().addModelElement(createTestElement("B"));
		update(checkedOutProjectSpace);
		commitWithoutCommand(checkedOutProjectSpace);

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("B");
		getProjectSpace().getOperationManager().startChangeRecording();

		// cancel should be triggered via exception
		getProjectSpace().update(Versions.createHEAD(getProjectSpace().getBaseVersion()), new MyUpdateCallback(),
			new NullProgressMonitor());
	}

	@Test
	public void testCorrectChecksumsAtUpdate() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ProjectSpace checkedOutProjectSpace = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
			.checkout(getProjectSpace().getUsersession(), getProjectSpace().getProjectInfo(), new NullProgressMonitor());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		checkedOutProjectSpace.getProject().addModelElement(TestmodelFactory.eINSTANCE.createTestElement());
		update(checkedOutProjectSpace);
		commitWithoutCommand(checkedOutProjectSpace);

		PrimaryVersionSpec update = update(getProjectSpace());
		Assert.assertTrue(ModelUtil.areEqual(getProject(), checkedOutProjectSpace.getProject()));
		Assert.assertEquals(ModelUtil.computeChecksum(getProject()), update.getProjectStateChecksum());
	}

	@Test
	public void testCorruptChecksumsAtUpdateWithLocalOperation() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ProjectSpace checkedOutProjectSpace = ((WorkspaceBase) WorkspaceProvider.getInstance().getWorkspace())
			.checkout(getProjectSpace().getUsersession(), getProjectSpace().getProjectInfo(), new NullProgressMonitor());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		checkedOutProjectSpace.getProject().addModelElement(TestmodelFactory.eINSTANCE.createTestElement());
		update(checkedOutProjectSpace);
		commitWithoutCommand(checkedOutProjectSpace);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("B");
			}
		}.run(false);

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("C");
		getProjectSpace().getOperationManager().startChangeRecording();

		Assert.assertEquals(1, getProjectSpace().getOperations().size());

		// autocorrect should be triggered, will fail
		update(getProjectSpace());
		Assert.assertEquals(1, getProjectSpace().getOperations().size());
	}

	private class MyCommitCallback implements ICommitCallback {

		public boolean baseVersionOutOfDate(ProjectSpace projectSpace, IProgressMonitor progressMonitor) {
			return ICommitCallback.NOCALLBACK.baseVersionOutOfDate(projectSpace, progressMonitor);
		}

		public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage,
			IModelElementIdToEObjectMapping idToEObjectMapping) {
			return ICommitCallback.NOCALLBACK.inspectChanges(projectSpace, changePackage, idToEObjectMapping);
		}

		public void noLocalChanges(ProjectSpace projectSpace) {
			ICommitCallback.NOCALLBACK.noLocalChanges(projectSpace);
		}

		public boolean checksumCheckFailed(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) throws EmfStoreException {
			IChecksumErrorHandler checksumErrorHandler = Configuration.getChecksumErrorHandler();
			return checksumErrorHandler.execute(projectSpace, versionSpec, progressMonitor);
		}

	}

	private class MyUpdateCallback implements IUpdateCallback {

		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes,
			IModelElementIdToEObjectMapping idToEObjectMapping) {
			return IUpdateCallback.NOCALLBACK.inspectChanges(projectSpace, changes, idToEObjectMapping);
		}

		public void noChangesOnServer() {
			IUpdateCallback.NOCALLBACK.noChangesOnServer();
		}

		public boolean conflictOccurred(ChangeConflictException changeConflictException,
			IProgressMonitor progressMonitor) {
			return IUpdateCallback.NOCALLBACK.conflictOccurred(changeConflictException, progressMonitor);
		}

		public boolean checksumCheckFailed(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) throws EmfStoreException {
			IChecksumErrorHandler checksumErrorHandler = Configuration.getChecksumErrorHandler();
			return checksumErrorHandler.execute(projectSpace, versionSpec, progressMonitor);
		}
	}

	protected PrimaryVersionSpec commitWithoutCommand(final ProjectSpace projectSpace) throws EmfStoreException {
		return (PrimaryVersionSpec) projectSpace.commit(createEmptyLogMessage(), new MyCommitCallback(),
			new NullProgressMonitor());
	}

	protected PrimaryVersionSpec update(ProjectSpace projectSpace) throws EmfStoreException {
		return (PrimaryVersionSpec) projectSpace.update(Versions.createHEAD(), new MyUpdateCallback(),
			new NullProgressMonitor());
	}

	private LogMessage createEmptyLogMessage() {
		return VersioningFactory.eINSTANCE.createLogMessage();
	}
}
