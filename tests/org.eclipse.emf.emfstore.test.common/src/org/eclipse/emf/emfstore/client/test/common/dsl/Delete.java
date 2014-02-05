/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.dsl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.test.common.TestSessionProvider2;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.AdminConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACOrgUnitId;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.test.model.TestElement;

public final class Delete {

	private Delete() {

	}

	public static void fromNonContained1ToN(final TestElement testElement, final List<TestElement> references) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getNonContained_1ToN().removeAll(references);
				return null;
			}
		});
	}

	public static void fromNonContained1ToN(final TestElement testElement, final TestElement reference) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getNonContained_1ToN().remove(reference);
				return null;
			}
		});
	}

	public static void fromNonContainedNToM(final TestElement testElement, final TestElement reference) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getNonContained_NToM().remove(reference);
				return null;
			}
		});
	}

	public static void fromNonContainedNToM(final TestElement testElement, final List<TestElement> reference) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getNonContained_NToM().removeAll(reference);
				return null;
			}
		});
	}

	public static void fromContainedElements(final TestElement testElement, final TestElement containee) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getContainedElements().remove(containee);
				return null;
			}
		});
	}

	public static void fromContainedElements(final TestElement testElement, final List<TestElement> containees) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				testElement.getContainedElements().removeAll(containees);
				return null;
			}
		});
	}

	public static void user(ESServer server, ACOrgUnitId userId) throws ESException {
		final AdminConnectionManager adminConnectionManager = ESWorkspaceProviderImpl.getInstance()
			.getAdminConnectionManager();
		final SessionId sessionId = TestSessionProvider2.getInstance().getDefaultUsersession().getSessionId();
		final ESServerImpl s = ESServerImpl.class.cast(server);
		adminConnectionManager.initConnection(s.toInternalAPI(), sessionId);
		adminConnectionManager.deleteUser(sessionId, userId);
	}

	public static void fromProject(final ESLocalProject localProject, final EObject eObject) {
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				final Project project = ((ESLocalProjectImpl) localProject).toInternalAPI().getProject();
				project.deleteModelElement(eObject);
			}
		});
	}

	public static void allRemoteProjects(ESServer server, ESUsersession session) throws ESException {
		for (final ESRemoteProject project : server.getRemoteProjects()) {
			project.delete(session, new NullProgressMonitor());
		}
	}

	public static void allLocalProjects() throws IOException, ESException {
		final ESWorkspace workspace = ESWorkspaceProvider.INSTANCE.getWorkspace();
		final List<ESLocalProject> localProjects = workspace.getLocalProjects();
		for (final ESLocalProject localProject : localProjects) {
			localProject.delete(new NullProgressMonitor());
		}
	}

}
