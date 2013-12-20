/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.exceptions.ESServerStartFailedException;
import org.eclipse.emf.emfstore.client.test.common.util.ServerUtil;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc.XmlRpcClientManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc.XmlRpcConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidInputException;
import org.eclipse.emf.emfstore.internal.server.exceptions.UnknownSessionException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test calls the servermethods with null arguments.
 * 
 * @author wesendon
 */
public class InvalidArgumentsTest {

	private static ConnectionManager connectionManager;
	private static Map<Class<?>, Object> arguments;

	private static ESServer server;
	private static ESUsersession session;
	

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ESException in case of failure
	 * @throws IOException
	 * @throws FatalESException
	 * @throws ESServerStartFailedException
	 * @throws IllegalArgumentException
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws ESException, IOException, IllegalArgumentException,
		ESServerStartFailedException, FatalESException {

		ServerConfiguration.setTesting(true);
		CommonUtil.setTesting(true);

//		SetupHelper.addUserFileToServer(false);
		connectionManager = new XmlRpcConnectionManager() {
			@Override
			protected XmlRpcClientManager getConnectionProxy(SessionId sessionId) throws UnknownSessionException {
				if (sessionId == null && getConnectionProxyMap().size() > 0) {
					return getConnectionProxyMap().values().iterator().next();
				}
				return super.getConnectionProxy(sessionId);

			}
		};
		ESWorkspaceProviderImpl.getInstance().setConnectionManager(connectionManager);

		server = ServerUtil.startServer();
		session = server.login("super", "super");
		ESWorkspaceProviderImpl.getInstance().getAdminConnectionManager().initConnection(
			((ESServerImpl) server).toInternalAPI(), ((ESUsersessionImpl) session).toInternalAPI().getSessionId());
		initArguments();
	}
	
	@AfterClass
	public static void afterClass() {
		
	}

	// / FIXME
	@SuppressWarnings("restriction")
	protected static void initArguments() {
		arguments = new LinkedHashMap<Class<?>, Object>();
		arguments.put(boolean.class, false);
		arguments.put(String.class, new String());
		arguments.put(SessionId.class,
			ModelUtil.clone(ESUsersessionImpl.class.cast(session).toInternalAPI().getSessionId()));
		arguments.put(ProjectId.class,
			org.eclipse.emf.emfstore.internal.server.model.ModelFactory.eINSTANCE.createProjectId());
		arguments.put(PrimaryVersionSpec.class, VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		arguments.put(VersionSpec.class, VersioningFactory.eINSTANCE.createPrimaryVersionSpec());
		arguments.put(TagVersionSpec.class, VersioningFactory.eINSTANCE.createTagVersionSpec());
		arguments.put(LogMessage.class, VersioningFactory.eINSTANCE.createLogMessage());
		arguments.put(Project.class,
			org.eclipse.emf.emfstore.internal.common.model.ModelFactory.eINSTANCE.createProject());
		arguments.put(ChangePackage.class, VersioningFactory.eINSTANCE.createChangePackage());
		arguments.put(HistoryQuery.class, VersioningFactory.eINSTANCE.createPathQuery());
		arguments.put(ChangePackage.class, VersioningFactory.eINSTANCE.createChangePackage());
		arguments.put(ACOrgUnitId.class, AccesscontrolFactory.eINSTANCE.createACOrgUnitId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void deleteProjectTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("deleteProject",
				new Class[] { SessionId.class, ProjectId.class, boolean.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void createProjectTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("createEmptyProject",
				new Class[] { SessionId.class, String.class, String.class, LogMessage.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void createProject2Test() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("createProject",
				new Class[] { SessionId.class, String.class, String.class, LogMessage.class, Project.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void createVersionTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod(
				"createVersion",
				new Class[] { SessionId.class, ProjectId.class, PrimaryVersionSpec.class, ChangePackage.class,
					BranchVersionSpec.class, PrimaryVersionSpec.class, LogMessage.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void getChangesTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("getChanges",
				new Class[] { SessionId.class, ProjectId.class, VersionSpec.class, VersionSpec.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void getHistoryInfoTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("getHistoryInfo",
				new Class[] { SessionId.class, ProjectId.class, HistoryQuery.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void getProjectTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("getProject",
				new Class[] { SessionId.class, ProjectId.class, VersionSpec.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void addTagTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("addTag",
				new Class[] { SessionId.class, ProjectId.class, PrimaryVersionSpec.class, TagVersionSpec.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Test(expected = InvalidInputException.class)
	public void removeTagTest() throws ESException {
		try {
			testAllInvalidCombinations(connectionManager.getClass().getMethod("removeTag",
				new Class[] { SessionId.class, ProjectId.class, PrimaryVersionSpec.class, TagVersionSpec.class }));
		} catch (SecurityException e) {
			throw new ESException(e);
		} catch (NoSuchMethodException e) {
			throw new ESException(e);
		}
	}

	private void testAllInvalidCombinations(Method method) throws ESException {
		int parameterLength = method.getParameterTypes().length;
		Object[] parameters = new Object[parameterLength];
		int combinations = (int) (Math.round(Math.pow(2, parameterLength)) - 1);
		for (int i = 0; i < combinations; i++) {
			for (int j = 0; j < parameterLength; j++) {
				Class<?> class1 = method.getParameterTypes()[j];
				boolean argument = getArgument(j, i);
				parameters[j] = getParameter(class1, argument);
			}
			callMethod(method, parameters);
		}
	}

	protected static Object getParameter(Class<?> clazz, boolean b) {
		if (clazz.equals(boolean.class)) {
			return false;
		}
		return (b) ? arguments.get(clazz) : null;
	}

	private void callMethod(Method method, Object[] parameters) throws ESException {
		if (method.getParameterTypes().length != parameters.length) {
			throw new AssertionError("parameter length not equal");
		}
		for (int i = 0; i < parameters.length; i++) {
			System.out.print(method.getParameterTypes()[i] + " " + parameters[i] + "; ");
		}
		System.out.println("");
		try {
			// method.invoke(connectionManager, null, null, false);
			method.invoke(connectionManager, parameters);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof ESException) {
				throw (ESException) e.getCause();
			}
			Assert.assertTrue(false);
		}
		throw new AssertionError("No exception was thrown");
	}

	private boolean getArgument(int j, int i) {
		if (j == 0) {
			return (i & 1) == 1;
		}
		int position = (int) Math.pow(2, j);
		return (i & position) == position;
	}
}
