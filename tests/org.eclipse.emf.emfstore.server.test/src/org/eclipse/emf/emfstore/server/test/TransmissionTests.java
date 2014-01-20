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
package org.eclipse.emf.emfstore.server.test;

import static org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil.nullProgressMonitor;
import static org.junit.Assert.fail;

import org.eclipse.emf.emfstore.client.test.common.builders.BOOL.TRUE;
import org.eclipse.emf.emfstore.client.test.common.builders.UserBuilder;
import org.eclipse.emf.emfstore.client.test.common.cases.ESTestWithLoggedInUser;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.dsl.Delete;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.After;
import org.junit.Before;

public abstract class TransmissionTests extends ESTestWithLoggedInUser {

	private static final String BAR = "bar"; //$NON-NLS-1$
	private static final String WRITER2 = "writer2"; //$NON-NLS-1$
	private static final String FOO = "foo"; //$NON-NLS-1$
	private static final String WRITER1 = "writer1"; //$NON-NLS-1$
	private static final String CHECKOUT2 = "checkout2"; //$NON-NLS-1$
	private static final String CHECKOUT1 = "checkout1"; //$NON-NLS-1$

	private ProjectSpace projectSpace1;
	private ProjectSpace projectSpace2;

	private Usersession usersession1;
	private Usersession usersession2;

	private ACOrgUnitId user1;
	private ACOrgUnitId user2;

	public Usersession getUsersession1() {
		return usersession1;
	}

	public Usersession getUsersession2() {
		return usersession2;
	}

	@Override
	@Before
	public void before() {

		super.before();
		try {
			getLocalProject().shareProject(getUsersession(), nullProgressMonitor());
			projectSpace1 = ESLocalProjectImpl.class.cast(getLocalProject().getRemoteProject()
				.checkout(CHECKOUT1, getUsersession(), nullProgressMonitor())).toInternalAPI();
			projectSpace2 = ESLocalProjectImpl.class.cast(getLocalProject().getRemoteProject()
				.checkout(CHECKOUT2, getUsersession(), nullProgressMonitor())).toInternalAPI();
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}

		final UserBuilder<TRUE, TRUE, TRUE> builder1 = UserBuilder.create()
			.withName(WRITER1)
			.withPassword(FOO)
			.onServer(getServer());
		final UserBuilder<TRUE, TRUE, TRUE> builder2 = UserBuilder.create()
			.withName(WRITER2)
			.withPassword(BAR)
			.onServer(getServer());

		try {
			user1 = Create.user(builder1);
			user2 = Create.user(builder2);
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}

		try {
			usersession1 = ESUsersessionImpl.class.cast(getServer().login(
				builder1.getUsername(),
				builder1.getPassword())).toInternalAPI();
			usersession2 = ESUsersessionImpl.class.cast(getServer().login(
				builder2.getUsername(),
				builder2.getPassword())).toInternalAPI();
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@After
	public void after() {
		try {
			Delete.user(getServer(), user1);
			Delete.user(getServer(), user2);
		} catch (final ESException ex) {
			fail(ex.getMessage());
		}
	}

	public ProjectSpace getProjectSpace1() {
		return projectSpace1;
	}

	public ProjectSpace getProjectSpace2() {
		return projectSpace2;
	}

}