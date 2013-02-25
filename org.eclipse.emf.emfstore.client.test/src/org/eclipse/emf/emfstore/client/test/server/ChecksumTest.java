package org.eclipse.emf.emfstore.client.test.server;

import java.util.List;
import java.util.concurrent.Callable;

import junit.framework.Assert;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.client.test.server.api.CoreServerTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.ChecksumErrorHandler;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.junit.Test;

public class ChecksumTest extends CoreServerTest {

	@Test
	public void testRevert() throws SerializationException {
		// createTestElement automatically adds the element to the project
		final TestElement table = createTestElement("A");
		// final TestElement b = createTestElement("B");

		final TestElement value = createTestElement("value");
		final TestElement attributeName = createTestElement("attributeName");
		final TestElement attribute = createTestElement("attribute");
		attribute.getContainedElements().add(value);

		table.getElementMap().put(attributeName, value);
		// d.getReferences().add(attributeName);
		// attributeName.getReferences().add(b);

		// getProject().getModelElements().add(b);
		getProject().getModelElements().add(table);

		long computeChecksum = ModelUtil.computeChecksum(getProject());
		clearOperations();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				// getProjectSpace().getOperationManager().beginCompositeOperation();
				getProject().getModelElements().remove(attribute);
				// getProjectSpace().getOperationManager().endCompositeOperation();
			}
		}.run(false);

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				// getProjectSpace().getOperationManager().beginCompositeOperation();
				getProjectSpace().revert();
				// getProjectSpace().getOperationManager().endCompositeOperation();
				return null;
			}
		});

		long checksum = ModelUtil.computeChecksum(getProject());

		Assert.assertEquals(computeChecksum, checksum);
	}

	@Test
	public void testOrderOfRootElementsInvariance() throws SerializationException {
		// createTestElement automatically adds the element to the project
		final TestElement a = createTestElement("A");
		final TestElement b = createTestElement("B");

		getProject().getModelElements().add(a);
		getProject().getModelElements().add(b);

		long computeChecksum = ModelUtil.computeChecksum(getProject());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().getModelElements().clear();
				getProject().getModelElements().add(b);
				getProject().getModelElements().add(a);
			}
		}.run(false);

		long checksum = ModelUtil.computeChecksum(getProject());

		Assert.assertEquals(computeChecksum, checksum);
	}

	@Test
	public void testAutocorrectErrorHandlerAtCommit() throws ESException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.ClIENT_BEHAVIOR.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);

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

		Project restoredProject = WorkspaceProvider.getInstance().getWorkspace().getInternalAPIImpl()
			.getProjectSpaces()
			.get(0).getProject();
		long computedChecksum = ModelUtil.computeChecksum(restoredProject);

		Assert.assertTrue(ModelUtil.areEqual(restoredProject, clonedProject));
		Assert.assertEquals(expectedChecksum, commit.getProjectStateChecksum());
		Assert.assertEquals(commit.getProjectStateChecksum(), computedChecksum);
	}

	@Test
	public void testChangeTrackingAfterAutocorrectErrorHandler() throws ESException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.ClIENT_BEHAVIOR.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);
		((WorkspaceProvider) WorkspaceProvider.INSTANCE).setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ESLocalProject checkout = getProjectSpace()
			.getAPIImpl()
			.getRemoteProject()
			.checkout(
				getProjectSpace().getUsersession().getAPIImpl(),
				getProjectSpace().resolveVersionSpec(Versions.createHEAD(), new NullProgressMonitor()).getAPIImpl(),
				new NullProgressMonitor());
		final ProjectSpace checkedOutProjectSpace = ((ESLocalProjectImpl) checkout).getInternalAPIImpl();

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

	@Test(expected = ESException.class)
	public void testCancelErrorHandlerAtCommit() throws ESException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.ClIENT_BEHAVIOR.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);

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

	@Test(expected = ESException.class)
	public void testCancelErrorHandlerAtUpdateAfterOneCommit() throws ESException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.ClIENT_BEHAVIOR.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ESLocalProject checkout = getProjectSpace().getAPIImpl().getRemoteProject()
			.checkout(getProjectSpace().getUsersession().getAPIImpl(),
				getProjectSpace().resolveVersionSpec(Versions.createHEAD(), new NullProgressMonitor()).getAPIImpl(),

				new NullProgressMonitor());
		final ProjectSpace checkedOutProjectSpace = ((ESLocalProjectImpl) checkout).getInternalAPIImpl();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				checkedOutProjectSpace.getProject().addModelElement(createTestElement("B"));
			}
		}.run(false);
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
	public void testCorrectChecksumsAtUpdate() throws ESException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.ClIENT_BEHAVIOR.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ESLocalProject checkout = getProjectSpace().getAPIImpl().getRemoteProject()
			.checkout(getProjectSpace().getUsersession().getAPIImpl(),
				getProjectSpace().resolveVersionSpec(Versions.createHEAD(), new NullProgressMonitor()).getAPIImpl(),
				new NullProgressMonitor());
		final ProjectSpace checkedOutProjectSpace = ((ESLocalProjectImpl) checkout).getInternalAPIImpl();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				checkedOutProjectSpace.getProject().addModelElement(TestmodelFactory.eINSTANCE.createTestElement());
			}
		}.run(false);
		update(checkedOutProjectSpace);
		commitWithoutCommand(checkedOutProjectSpace);

		PrimaryVersionSpec update = update(getProjectSpace());
		Assert.assertTrue(ModelUtil.areEqual(getProject(), checkedOutProjectSpace.getProject()));
		Assert.assertEquals(ModelUtil.computeChecksum(getProject()), update.getProjectStateChecksum());
	}

	@Test
	public void testCorruptChecksumsAtUpdateWithLocalOperation() throws ESException, SerializationException {

		Assert.assertEquals(1, WorkspaceProvider.getInstance().getWorkspace().getLocalProjects().size());

		Configuration.ClIENT_BEHAVIOR.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ESLocalProject checkout = getProjectSpace().getAPIImpl().getRemoteProject()
			.checkout(getProjectSpace().getUsersession().getAPIImpl(),
				getProjectSpace().resolveVersionSpec(Versions.createHEAD(), new NullProgressMonitor()).getAPIImpl(),
				new NullProgressMonitor());
		final ProjectSpace checkedOutProjectSpace = ((ESLocalProjectImpl) checkout).getInternalAPIImpl();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		commitWithoutCommand(getProjectSpace());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				checkedOutProjectSpace.getProject().addModelElement(TestmodelFactory.eINSTANCE.createTestElement());
			}
		}.run(false);

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

		public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) throws ESException {
			ESChecksumErrorHandler checksumErrorHandler = Configuration.ClIENT_BEHAVIOR.getChecksumErrorHandler();
			return checksumErrorHandler.execute(projectSpace, versionSpec,
				progressMonitor);
		}

	}

	private class MyUpdateCallback implements ESUpdateCallback {

		public boolean inspectChanges(ESLocalProject projectSpace, List<ESChangePackage> changes,
			ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
			return ESUpdateCallback.NOCALLBACK.inspectChanges(projectSpace, changes, idToEObjectMapping);
		}

		public void noChangesOnServer() {
			ESUpdateCallback.NOCALLBACK.noChangesOnServer();
		}

		public boolean conflictOccurred(ESChangeConflict changeConflictException,
			IProgressMonitor progressMonitor) {
			return ESUpdateCallback.NOCALLBACK.conflictOccurred(changeConflictException, progressMonitor);
		}

		public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
			IProgressMonitor progressMonitor) throws ESException {
			ESChecksumErrorHandler checksumErrorHandler = Configuration.ClIENT_BEHAVIOR.getChecksumErrorHandler();
			return checksumErrorHandler.execute(projectSpace, versionSpec,
				progressMonitor);
		}
	}

	protected PrimaryVersionSpec commitWithoutCommand(final ProjectSpace projectSpace) throws ESException {
		return projectSpace.commit(createEmptyLogMessage(), new MyCommitCallback(),
			new NullProgressMonitor());
	}

	protected PrimaryVersionSpec update(ProjectSpace projectSpace) throws ESException {
		return projectSpace.update(Versions.createHEAD(), new MyUpdateCallback(),
			new NullProgressMonitor());
	}

	private LogMessage createEmptyLogMessage() {
		return VersioningFactory.eINSTANCE.createLogMessage();
	}
}
