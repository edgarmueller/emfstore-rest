/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.conflictDetection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.Workspace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Abstract super class for operation tests, contains setup.
 * 
 * @author chodnick
 */
public abstract class ConflictDetectionTest extends WorkspaceTest {

	/**
	 * Clones a project space including the project.
	 * 
	 * @param ps the projectspace to clone
	 * @return the new projectspace
	 */
	public ProjectSpace cloneProjectSpace(final ProjectSpace ps) {

		final Workspace workspace = ESWorkspaceProviderImpl.getInstance().getWorkspace().toInternalAPI();
		final ProjectSpace result = new EMFStoreCommandWithResult<ProjectSpace>() {
			@Override
			protected ProjectSpace doRun() {
				final Project clonedProject = ModelUtil.clone(ps.getProject());
				return workspace.importProject(clonedProject, "clonedProject", "cloned Project");
			}
		}.run(false);
		return result;
	}

	/**
	 * Convenience to get an operation by type.
	 * 
	 * @param clazz class of operation
	 * @return operation
	 */
	protected AbstractOperation checkAndGetOperation(Class<? extends AbstractOperation> clazz) {
		assertEquals(getProjectSpace().getOperations().size(), 1);
		assertTrue(clazz.isInstance(getProjectSpace().getOperations().get(0)));
		final AbstractOperation operation = getProjectSpace().getOperations().get(0);
		clearOperations();
		assertEquals(getProjectSpace().getOperations().size(), 0);
		return operation;
	}

	/**
	 * Convenience method for conflict detection.
	 * 
	 * @param opA operation
	 * @param opB operation
	 * @return boolean
	 */
	protected boolean doConflict(AbstractOperation opA, AbstractOperation opB) {
		final ConflictDetector conflictDetector = new ConflictDetector();
		final ChangePackage changePackage1 = VersioningFactory.eINSTANCE.createChangePackage();
		changePackage1.getOperations().add(opA);
		final ChangePackage changePackage2 = VersioningFactory.eINSTANCE.createChangePackage();
		changePackage2.getOperations().add(opB);

		final ChangeConflictSet conflictSet = conflictDetector.calculateConflicts(Arrays.asList(changePackage1),
			Arrays.asList(changePackage2),
			ModelFactory.eINSTANCE.createProject());
		return conflictSet.getConflictBuckets().size() > 0;
	}

	public Set<AbstractOperation> getConflicts(List<AbstractOperation> ops1, List<AbstractOperation> ops2,
		Project project) {
		final ChangePackage changePackage1 = VersioningFactory.eINSTANCE.createChangePackage();
		changePackage1.getOperations().addAll(ops1);
		final ChangePackage changePackage2 = VersioningFactory.eINSTANCE.createChangePackage();
		changePackage2.getOperations().addAll(ops2);
		final ChangeConflictSet conflicts = new ConflictDetector().calculateConflicts(Arrays.asList(changePackage1),
			Arrays.asList(changePackage2), project);
		final LinkedHashSet<AbstractOperation> result = new LinkedHashSet<AbstractOperation>();
		for (final ConflictBucket conflictBucket : conflicts.getConflictBuckets()) {
			final Set<AbstractOperation> myOperations = conflictBucket.getMyOperations();
			result.addAll(myOperations);
		}
		return result;
	}

	public Set<AbstractOperation> getConflicts(List<AbstractOperation> ops1, List<AbstractOperation> ops2) {
		return getConflicts(ops1, ops2, ModelFactory.eINSTANCE.createProject());
	}

	public <T extends AbstractOperation> AbstractOperation myCheckAndGetOperation(
		final Class<? extends AbstractOperation> clazz) {
		return new EMFStoreCommandWithResult<AbstractOperation>() {
			@Override
			protected AbstractOperation doRun() {
				return checkAndGetOperation(clazz);
			}
		}.run(false);
	}

	public void myClearOperations() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				clearOperations();
			}
		}.run(false);
	}

}
