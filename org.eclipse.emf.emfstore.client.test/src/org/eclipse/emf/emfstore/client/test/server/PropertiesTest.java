package org.eclipse.emf.emfstore.client.test.server;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.ModelPackage;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.properties.PropertyManager;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.model.accesscontrol.roles.RolesPackage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PropertiesTest extends ServerTests {

	private static ProjectSpace projectSpace1;
	private static PropertyManager propertyManager1;
	private static ProjectSpace projectSpace2;
	private static PropertyManager propertyManager2;
	private static Usersession usersession1;
	private static Usersession usersession2;
	private ACOrgUnitId user1;
	private ACOrgUnitId user2;

	@Before
	public void setUpTests() throws EmfStoreException {
		user1 = setupUsers("writer1", RolesPackage.eINSTANCE.getWriterRole());
		user2 = setupUsers("writer2", RolesPackage.eINSTANCE.getWriterRole());
		usersession1 = setUpUsersession("writer1", "foo");
		usersession2 = setUpUsersession("writer2", "foo");
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				Workspace workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
				workspace.getServerInfos().add(getServerInfo());
				workspace.getUsersessions().add(usersession1);
				workspace.getUsersessions().add(usersession2);
				workspace.save();

				try {
					usersession1.logIn();
					usersession2.logIn();
					projectSpace1 = workspace.checkout(usersession1, getProjectInfo());
					projectSpace2 = workspace.checkout(usersession2, getProjectInfo());
				} catch (AccessControlException e) {
					throw new RuntimeException(e);
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

	}

	@After
	public void tearDownUsers() throws EmfStoreException {
		SetupHelper.deleteUserOnServer(user1);
		SetupHelper.deleteUserOnServer(user2);

	}

	@Test
	public void sharedPropertiesTest() throws EmfStoreException {

		propertyManager1 = projectSpace1.getPropertyManager();
		propertyManager2 = projectSpace2.getPropertyManager();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				propertyManager1.setSharedStringProperty("FirstPropKey", "test1");
				propertyManager2.setSharedStringProperty("SecondTest", "test2");

				try {
					propertyManager1.transmit();
					propertyManager2.transmit();
					propertyManager1.transmit();
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// 1. Test, ob transmit funktioniert
		Assert.assertEquals(propertyManager1.getSharedStringProperty("FirstPropKey"),
			propertyManager2.getSharedStringProperty("FirstPropKey"));

		Assert.assertEquals(propertyManager2.getSharedStringProperty("SecondTest"),
			propertyManager1.getSharedStringProperty("SecondTest"));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				propertyManager1.setSharedStringProperty("SecondTest", "test4");
				propertyManager2.setSharedStringProperty("SecondTest", "test5");

				try {
					propertyManager1.transmit();
					propertyManager2.transmit();
					propertyManager1.transmit();
				} catch (EmfStoreException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// 2. Funktioniert update
		Assert.assertEquals("test5", propertyManager1.getSharedStringProperty("SecondTest"));
	}

	@Test
	public void testLocalProperties() throws IOException {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				projectSpace1.getPropertyManager().setLocalProperty("foo",
					TestmodelFactory.eINSTANCE.createTestElement());
			}
		}.run(false);

		((ProjectSpaceImpl) projectSpace1).saveProjectSpaceOnly();
		ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(ModelPackage.eINSTANCE.getProjectSpace(),
			projectSpace1.eResource().getURI(), false);

		assertNotNull(loadedProjectSpace.getPropertyManager().getLocalProperty("foo"));
	}
}
