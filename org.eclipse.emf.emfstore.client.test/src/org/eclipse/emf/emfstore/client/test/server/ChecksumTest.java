package org.eclipse.emf.emfstore.client.test.server;

import junit.framework.Assert;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.model.Configuration;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.ChecksumErrorHandler;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.server.api.CoreServerTest;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.junit.Test;

public class ChecksumTest extends CoreServerTest {

	@Test
	public void testOrderOfRootElementsInvariance() throws SerializationException {
		Project clonedProject = ModelUtil.clone(getProject());

		// createTestElement automatically adds the element to the project
		TestElement a = createTestElement("A");
		TestElement b = createTestElement("B");

		getProject().getModelElements().add(a);
		getProject().getModelElements().add(b);

		TestElement clonedA = ModelUtil.clone(a);
		TestElement clonedB = ModelUtil.clone(b);

		clonedProject.getModelElements().add(clonedB);
		clonedProject.getModelElements().add(clonedA);

		Assert.assertFalse(ModelUtil.eObjectToString(getProject()).equals(ModelUtil.eObjectToString(clonedProject)));
		Assert.assertTrue(ModelUtil.areEqual(getProject(), clonedProject));
	}

	@Test
	public void testAutocorrectErrorHandler() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.AUTOCORRECT);
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

		long expectedChecksum = ModelUtil.computeChecksum(getProject());

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("B");
		getProjectSpace().getOperationManager().startChangeRecording();

		// re-checkout should be triggered
		PrimaryVersionSpec commit = getProjectSpace().commit();

		Assert.assertEquals(1, WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().size());

		long computedChecksum = ModelUtil.computeChecksum(WorkspaceManager.getInstance().getCurrentWorkspace()
			.getProjectSpaces().get(0).getProject());

		Assert.assertEquals(expectedChecksum, commit.getProjectStateChecksum());
		Assert.assertEquals(commit.getProjectStateChecksum(), computedChecksum);
		Assert.assertEquals(expectedChecksum, computedChecksum);
	}

	@Test
	public void testCancelErrorHandler() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().size());

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

		long expectedChecksum = ModelUtil.computeChecksum(getProject());

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("B");
		getProjectSpace().getOperationManager().startChangeRecording();

		// cancel should be triggered
		PrimaryVersionSpec commit = getProjectSpace().commit();

		Assert.assertEquals(1, WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().size());

		long computedChecksum = ModelUtil.computeChecksum(WorkspaceManager.getInstance().getCurrentWorkspace()
			.getProjectSpaces().get(0).getProject());

		Assert.assertEquals(commit.getIdentifier(), getProjectSpace().getBaseVersion().getIdentifier());
		Assert.assertFalse(expectedChecksum == computedChecksum);
	}

	@Test
	public void testCancelErrorHandlerAfterOneCommit() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ProjectSpace checkedOutProjectSpace = WorkspaceManager
			.getInstance()
			.getCurrentWorkspace()
			.checkout(getProjectSpace().getUsersession(), getProjectSpace().getProjectInfo(), new NullProgressMonitor());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		PrimaryVersionSpec commit = getProjectSpace().commit();

		checkedOutProjectSpace.getProject().addModelElement(createTestElement("B"));
		checkedOutProjectSpace.update();
		checkedOutProjectSpace.commit();

		getProjectSpace().getOperationManager().stopChangeRecording();
		testElement.setName("B");
		getProjectSpace().getOperationManager().startChangeRecording();

		// cancel should be triggered
		PrimaryVersionSpec update = getProjectSpace().update();
		Assert.assertEquals(commit.getProjectStateChecksum(), update.getProjectStateChecksum());
	}

	@Test
	public void testCorrectChecksumsAtUpdate() throws EmfStoreException, SerializationException {

		Assert.assertEquals(1, WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces().size());

		Configuration.setChecksumFailureAction(ChecksumErrorHandler.CANCEL);
		getWorkspace().setConnectionManager(getConnectionMock());

		final TestElement testElement = createTestElement();
		share(getProjectSpace());

		ProjectSpace checkedOutProjectSpace = WorkspaceManager
			.getInstance()
			.getCurrentWorkspace()
			.checkout(getProjectSpace().getUsersession(), getProjectSpace().getProjectInfo(), new NullProgressMonitor());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProject().addModelElement(testElement);
				testElement.setName("A");
			}
		}.run(false);

		getProjectSpace().commit();

		checkedOutProjectSpace.getProject().addModelElement(TestmodelFactory.eINSTANCE.createTestElement());
		checkedOutProjectSpace.update();
		checkedOutProjectSpace.commit();

		// cancel should be triggered
		PrimaryVersionSpec update = getProjectSpace().update();
		Assert.assertTrue(ModelUtil.areEqual(getProject(), checkedOutProjectSpace.getProject()));
		Assert.assertEquals(ModelUtil.computeChecksum(getProject()), update.getProjectStateChecksum());
	}
}
