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
package org.eclipse.emf.emfstore.client.test.common.util;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;
import org.eclipse.emf.emfstore.client.test.common.TestConflictResolver;
import org.eclipse.emf.emfstore.client.test.common.dsl.CreateAPI;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESPrimaryVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.exceptions.ESUpdateRequiredException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.emf.emfstore.test.model.TestElement;

// TODO: can we have common util?
public class ProjectUtil {

	private static final String DEFAULT_NAME = "TestProject"; //$NON-NLS-1$

	public static IProgressMonitor nullProgressMonitor() {
		return new NullProgressMonitor();
	}

	public static String defaultName() {
		return DEFAULT_NAME;
	}

	public static ESLocalProject cloneProject(final ESLocalProject localProject) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				final ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).toInternalAPI();
				final Project clonedProject = ModelUtil.clone(projectSpace.getProject());
				final ProjectSpace clonedProjectSpace = ModelUtil.clone(projectSpace);
				clonedProjectSpace.setProject(clonedProject);
				return clonedProjectSpace.toAPI();
			}
		});
	}

	public static ESLocalProject clearModelElements(final ESLocalProject localProject) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {

			public ESLocalProject call() throws Exception {
				localProject.getModelElements().clear();
				return localProject;
			}
		});
	}

	public static ESLocalProject clearOperations(ESLocalProject localProject) {
		final ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).toInternalAPI();

		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				projectSpace.getLocalChangePackage().getOperations().clear();
				projectSpace.getOperationManager().clearOperations();
				projectSpace.getOperations().clear();
				return null;
			}
		});

		return localProject;
	}

	public static int getOperationSize(ESLocalProject localProject) {
		final ESLocalProjectImpl cast = ESLocalProjectImpl.class.cast(localProject);
		return RunESCommand.runWithResult(new Callable<Integer>() {
			public Integer call() throws Exception {
				return cast.toInternalAPI().getOperations().size();
			}
		});
	}

	public static void deleteRemoteProjects(ESServer server, ESUsersession usersession) throws IOException,
		FatalESException,
		ESException {
		for (final ESRemoteProject project : server.getRemoteProjects(usersession)) {
			project.delete(usersession, new NullProgressMonitor());
		}
	}

	public static void deleteLocalProjects() throws IOException, ESException {
		deleteLocalProjects(new NullProgressMonitor());
	}

	public static void rename(final ESLocalProject localProject, final String name) {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				final TestElement testElement = (TestElement) localProject.getModelElements().get(0);
				testElement.setName(name);
				return null;
			}
		});
	}

	public static void deleteLocalProjects(IProgressMonitor monitor) throws IOException, ESException {
		for (final ESLocalProject lp : ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects()) {
			lp.delete(monitor);
		}
	}

	public static ESLocalProject addElement(final ESLocalProject localProject, final EObject modelElement) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				localProject.getModelElements().add(modelElement);
				return localProject;
			}
		});
	}

	public static ESLocalProject removeModelElement(final ESLocalProject localProject, final EObject modelElement) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				localProject.getModelElements().remove(modelElement);
				return localProject;
			}
		});
	}

	public static ESLocalProject revert(final ESLocalProject localProject) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				localProject.revert();
				return localProject;
			}
		});
	}

	public static long computeChecksum(ESLocalProject localProject) throws SerializationException {
		final ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).toInternalAPI();
		final long checksum = ModelUtil.computeChecksum(projectSpace.getProject());
		System.out.println(ModelUtil.eObjectToString(projectSpace.getProject()));
		return checksum;
	}

	public static ESLocalProject update(ESLocalProject localProject, ESVersionSpec version)
		throws ChangeConflictException, ESException {
		localProject.update(version, null, nullProgressMonitor());
		return localProject;
	}

	public static ESLocalProject update(ESLocalProject localProject) throws ChangeConflictException, ESException {
		localProject.update(nullProgressMonitor());
		return localProject;
	}

	public static ESLocalProject update(ESLocalProject localProject,
		ESUpdateCallback updateCallback) throws ChangeConflictException, ESException {
		localProject.update(ESVersionSpec.FACTORY.createHEAD(), updateCallback, nullProgressMonitor());
		return localProject;
	}

	protected ESLocalProject reCheckout(final ESLocalProject localProject) {

		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {

				return null;
			}
		});

		// return new EMFStoreCommandWithResult<ProjectSpace>() {
		// @Override
		// protected ProjectSpace doRun() {
		// try {
		// ((ESWorkspaceProviderImpl) ESWorkspaceProvider.INSTANCE)
		// .setConnectionManager(getConnectionMock());
		// // TODO: TQ
		// final ESLocalProject checkout = projectSpace.toAPI().getRemoteProject().checkout(
		// "testCheckout",
		// projectSpace.getUsersession().toAPI(),
		// projectSpace.getBaseVersion().toAPI(),
		// new NullProgressMonitor());
		// return ((ESLocalProjectImpl) checkout).toInternalAPI();
		// } catch (final ESException e) {
		// throw new RuntimeException(e);
		// }
		// }
		// }.run(false);
	}

	public static ESLocalProject commit(ESLocalProject localProject) throws ESException {
		localProject.commit(nullProgressMonitor());
		return localProject;
	}

	public static ESLocalProject checkout(final ESLocalProject localProject) throws ESException {
		return RunESCommand.WithException.runWithResult(ESException.class, new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				return localProject.getRemoteProject().checkout("NONAME", localProject.getUsersession(),
					localProject.getBaseVersion(), new NullProgressMonitor());
			}
		});
	}

	public static ESLocalProject checkout(final ESLocalProject localProject, final ESPrimaryVersionSpec versionSpec) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				return localProject.getRemoteProject().checkout("NONAME",
					localProject.getUsersession(),
					versionSpec,
					new NullProgressMonitor());
			}
		});
	}

	public static ESLocalProject commitToBranch(ESLocalProject localProject, String branch)
		throws InvalidVersionSpecException, ESUpdateRequiredException, ESException {
		localProject.commitToBranch(CreateAPI.branchVersionSpec(branch),
			"", null, nullProgressMonitor());
		return localProject;
	}

	public static ESLocalProject tag(ESLocalProject localProject, ESPrimaryVersionSpec versionSpec, String branchName,
		String tag)
		throws ESException {
		final ESTagVersionSpec tagVersionSpec = CreateAPI.tagVersionSpec(branchName, tag);
		localProject.addTag(versionSpec, tagVersionSpec, nullProgressMonitor());
		return localProject;
	}

	public static ESLocalProject share(ESUsersession session, ESLocalProject localProject) throws ESException {
		localProject.shareProject(session, new NullProgressMonitor());
		return localProject;
	}

	public static ESLocalProject mergeWithBranch(final ESLocalProject trunk, final ESPrimaryVersionSpec latestOnBranch,
		final int expectedConflicts) {

		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			@SuppressWarnings("restriction")
			public ESLocalProject call() throws Exception {
				((ESLocalProjectImpl) trunk).toInternalAPI().mergeBranch(
					((ESPrimaryVersionSpecImpl) latestOnBranch).toInternalAPI(),
					new TestConflictResolver(true, expectedConflicts),
					new NullProgressMonitor());
				return trunk;
			}
		});
	}

	// public static ESLocalProject addAndCommit(ESLocalProject localProject, int times) throws ESException {
	// ESLocalProject project = localProject;
	// for (int i = 0; i < times; i++) {
	// project = commit(addModelElement(project, Create.testElement()));
	// }
	// return project;
	// }

	public static Times addAndCommit(ESLocalProject localProject) {
		return new Times(localProject);
	}

	public static ESLocalProject branch(final ESLocalProject localProject, final String branchName) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				localProject.commitToBranch(ESVersionSpec.FACTORY.createBRANCH(branchName), "LOGMESSAGE",
					ESCommitCallback.NOCALLBACK, new NullProgressMonitor());
				return localProject;
			}
		});
	}

	public static ESLocalProject stopRecording(final ESLocalProject localProject) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				final ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).toInternalAPI();
				projectSpace.getOperationManager().stopChangeRecording();
				return projectSpace.toAPI();
			}
		});
	}

	public static ESLocalProject startRecording(final ESLocalProject localProject) {
		return RunESCommand.runWithResult(new Callable<ESLocalProject>() {
			public ESLocalProject call() throws Exception {
				final ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).toInternalAPI();
				projectSpace.getOperationManager().startChangeRecording();
				return projectSpace.toAPI();
			}
		});
	}

	public static ESPrimaryVersionSpec getMergedVersion(ESLocalProject localProject) {
		final ESLocalProjectImpl projectImpl = ESLocalProjectImpl.class.cast(localProject);
		final PrimaryVersionSpec mergedVersion = projectImpl.toInternalAPI().getMergedVersion();
		if (mergedVersion == null) {
			return null;
		}
		return mergedVersion.toAPI();
	}

}
